package com.cmov.feup.printer;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.UUID;

import api.ApiService;
import api.RestClient;
import data.ProductList;
import data.Purchase;
import data.PurchaseList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrintActivity extends AppCompatActivity {
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    TextView message;
    ApiService api;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Button QRButton, barButton;
        api = RestClient.getApiService();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_print);
        message = (TextView) findViewById(R.id.message);
        QRButton = (Button) findViewById(R.id.scan1);
       // barButton = (Button) findViewById(R.id.scan2);
        QRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan(true);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putCharSequence("Message", message.getText());
    }

    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        message.setText(bundle.getCharSequence("Message"));
    }

    public void scan(boolean qrcode) {
        try {
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", qrcode ? "QR_CODE_MODE" : "PRODUCT_MODE");
            startActivityForResult(intent, 0);
        }
        catch (ActivityNotFoundException anfe) {
            showDialog(this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                act.startActivity(intent);
            }
        });
        downloadDialog.setNegativeButton(buttonNo, null);
        return downloadDialog.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                String format = data.getStringExtra("SCAN_RESULT_FORMAT");

                //message.setText("Format: " + format + "\nMessage: " + contents);

                Call<PurchaseList> call=api.getPurchase(UUID.fromString(contents));
                call.enqueue(new Callback<PurchaseList>() {

                    @Override
                    public void onResponse(Call<PurchaseList> call, Response<PurchaseList> response) {

                    }

                    @Override
                    public void onFailure(Call<PurchaseList> call, Throwable t) {

                    }
                });
            }
        }
    }
}