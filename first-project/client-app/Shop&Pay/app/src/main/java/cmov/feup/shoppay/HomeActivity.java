package cmov.feup.shoppay;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

import adapter.MyProductAdapter;
import api.ApiService;
import api.RestClient;
import data.Product;
import data.ProductList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.AppStatus;

public class HomeActivity extends AppCompatActivity {

    private ListView listView;
    private View parentView;

    private ArrayList<Product> productList;
    private MyProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /**
         * Array List for Binding Data from JSON to this List
         */
        productList = new ArrayList<>();
        parentView = findViewById(R.id.parentLayout);

        /**
         * Getting List
         */
        listView = (ListView) findViewById(R.id.listView);

        /**
         * Checking Internet Connection
         */
        if (AppStatus.getInstance(this).isOnline()) {



            final ProgressDialog dialog;
            /**
             * Progress Dialog for User Interaction
             */
            dialog = new ProgressDialog(HomeActivity.this);
            dialog.setTitle(getString(R.string.string_getting_json_title));
            dialog.setMessage(getString(R.string.string_getting_json_message));
            dialog.show();

            //Creating an object of our api interface
            ApiService api = RestClient.getApiService();

            /**
             * Calling JSON
             */
            Call<ProductList> call = api.getProducts();


            /**
             * Enqueue Callback will be call when get response...
             */
            call.enqueue(new Callback<ProductList>() {

                @Override
                public void onResponse(Call<ProductList> call, Response<ProductList> response) {
                    //Dismiss Dialog
                    dialog.dismiss();


                    if(response.isSuccessful()) {

                        /**
                         * Got Successfully
                         */
                        productList = response.body().getProducts();
                        Log.i("TESTE",productList.get(0).getPrice());


                        /**
                         * Binding that List to Adapter
                         */
                        adapter = new MyProductAdapter(HomeActivity.this, productList);
                        listView.setAdapter(adapter);

                    } else {
                        //Snackbar.make(parentView, R.string.string_some_thing_wrong, Snackbar.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ProductList> call, Throwable t) {
                    Log.w("MyTag", "requestFailed", t);
                }
            });

        } else {

            //Snackbar.make(parentView, R.string.string_internet_connection_not_available, Snackbar.LENGTH_LONG).show();
        }

        //OPEN BARCODE SCANNER
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        final Activity activity = this;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                //integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Scanned");
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
