package cmov.feup.shoppay;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.DecimalFormat;
import java.util.ArrayList;

import adapter.MyProductAdapter;
import api.ApiService;
import api.RestClient;
import data.Product;
import data.ProductList;
import data.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.AppStatus;

import static android.R.drawable.ic_menu_delete;
import static java.lang.System.in;

public class HomeActivity extends AppCompatActivity {

    private ListView listView;
    private View parentView;

    private ArrayList<Product> productList;
    private MyProductAdapter adapter;
    private Activity act;
    private TextView totalPriceView;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        act = this;

        Intent i = getIntent();
        user = (User)i.getSerializableExtra("user");

        Toast.makeText(act, "Logged in as "+user.getName(), Toast.LENGTH_LONG).show();

        /**
         * Array List for Binding Data from JSON to this List
         */
        productList = new ArrayList<>();
        parentView = findViewById(R.id.parentLayout);

        totalPriceView = (TextView) findViewById(R.id.totalPrice);
        totalPriceView.setText("Total price: 0.00 €");
        /**
         * Getting List
         */
        listView = (ListView) findViewById(R.id.listView);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub

                Log.v("long clicked","pos: " + pos);


                AlertDialog diaBox = deleteDialog(pos);
                diaBox.show();

                return true;
            }
        });

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
                Log.i("Teste",this.toString());
                //Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();

                this.getProductByBarcode(result.getContents());
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    protected void getProductByBarcode(final String barcode) {
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
            Call<ProductList> call = api.getProductByBarcode(barcode);


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
                        Log.i("TESTE",response.body().getProducts().get(0).getCategory());

                        boolean found = false;
                        for(Product p: productList) {
                            if(barcode.equals(p.getBarcode()))
                                found = true;
                        }

                        if(!found) {
                            productList.add(response.body().getProducts().get(0));
                            adapter = new MyProductAdapter(HomeActivity.this, productList);
                            listView.setAdapter(adapter);
                            updateTotalPrice();

                        }
                        else {
                            Toast.makeText(act, "Product already in cart!", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(act, "Product not found!", Toast.LENGTH_LONG).show();
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
    }

    //Delete products from cart
    private AlertDialog deleteDialog(final int pos) {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to delete "+productList.get(pos).getModel())
                .setIcon(ic_menu_delete)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        productList.remove(pos);
                        adapter.notifyDataSetChanged();
                        updateTotalPrice();
                        dialog.dismiss();
                    }

                })

                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;

    }

    private void updateTotalPrice() {
        DecimalFormat df = new DecimalFormat("#.00");
        double totalPrice=0;
        for(Product p: productList) {
            totalPrice+=Double.parseDouble(p.getPrice());
        }
        totalPriceView.setText("Total price: "+df.format(totalPrice)+" €");
    }


}
