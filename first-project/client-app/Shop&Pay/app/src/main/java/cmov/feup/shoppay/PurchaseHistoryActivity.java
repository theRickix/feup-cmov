package cmov.feup.shoppay;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.DecimalFormat;
import java.util.ArrayList;

import adapter.MyProductAdapter;
import adapter.MyPurchaseAdapter;
import api.ApiService;
import api.RestClient;
import data.Product;
import data.ProductList;
import data.Purchase;
import data.PurchaseList;
import data.ResponsePurchase;
import data.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.AppStatus;

import static android.R.drawable.ic_menu_delete;

public class PurchaseHistoryActivity extends AppCompatActivity {

    private ListView listView;
    private View parentView;

    private ArrayList<Purchase> purchaseList;
    private MyPurchaseAdapter adapter;
    private Activity act;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history);
        act = this;

        Intent i = getIntent();
        user = (User)i.getSerializableExtra("user");

        /**
         * Array List for Binding Data from JSON to this List
         */
        purchaseList = new ArrayList<>();
        parentView = findViewById(R.id.parentLayout);

        getPurchaseList();

        /**
         * Getting List
         */
        listView = (ListView) findViewById(R.id.listView);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub


                Intent intent = new Intent(act, QRCodeActivity.class);
                intent.putExtra("code", purchaseList.get(pos).getValidation_token().toString());
                startActivity(intent);

                return true;
            }
        });

    }


    protected void getPurchaseList() {
        /**
         * Checking Internet Connection
         */
        if (AppStatus.getInstance(this).isOnline()) {



            final ProgressDialog dialog;
            /**
             * Progress Dialog for User Interaction
             */
            dialog = new ProgressDialog(PurchaseHistoryActivity.this);
            dialog.setTitle(getString(R.string.string_getting_json_title));
            dialog.setMessage(getString(R.string.string_history_json_message));
            dialog.show();

            //Creating an object of our api interface
            ApiService api = RestClient.getApiService();

            /**
             * Calling JSON
             */
            Call<PurchaseList> call = api.getPurchases(user.getId());


            /**
             * Enqueue Callback will be call when get response...
             */
            call.enqueue(new Callback<PurchaseList>() {

                @Override
                public void onResponse(Call<PurchaseList> call, Response<PurchaseList> response) {
                    //Dismiss Dialog
                    dialog.dismiss();


                    if(response.isSuccessful()) {

                        /**
                         * Got Successfully
                         */

                        Log.v("TESTE   ",response.body().getPurchases().get(0).toString());

                        purchaseList.add(response.body().getPurchases().get(0));
                        adapter = new MyPurchaseAdapter(PurchaseHistoryActivity.this, purchaseList);
                        listView.setAdapter(adapter);


                    } else {
                        Toast.makeText(act, "No purchases!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<PurchaseList> call, Throwable t) {
                    Log.w("MyTag", "requestFailed", t);
                }
            });

        } else {

            //Snackbar.make(parentView, R.string.string_internet_connection_not_available, Snackbar.LENGTH_LONG).show();
        }
    }

}
