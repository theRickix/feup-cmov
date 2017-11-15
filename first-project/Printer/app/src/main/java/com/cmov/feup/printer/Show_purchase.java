package com.cmov.feup.printer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.UUID;

import adapter.MyProductAdapter;
import api.ApiService;
import api.RestClient;
import data.Product;
import data.ProductList;
import data.User;
import data.UserList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Show_purchase extends AppCompatActivity {
    double totalPrice;
    private  ApiService api;
    private TextView totalPriceView;
    private MyProductAdapter adapter;
    private ListView listView;
    private TextView userview;
    private ArrayList<Product> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_purchase);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
       userview= findViewById(R.id.userview);
        api = RestClient.getApiService();
        Intent i = getIntent();
        String contents=(String) i.getSerializableExtra("contents");
        final User[] us = new User[1];
        //message.setText("Format: " + format + "\nMessage: " + contents);
        Call<UserList> c1=api.getUserFromPurchase(UUID.fromString(contents));
        c1.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                us[0] =response.body().getUsers().get(0);
                //message.setText(response.body().getUsers().get(0).getName());
                userview.setText("name: "+us[0].getName()+"\n" +
                        "email: "+us[0].getEmail()+"\n" +
                        "address: "+us[0].getAddress()+"\n" +
                        "fiscal number: "+us[0].getFiscal());
            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
               // message.setText("failed");
            }
        });
        totalPriceView = (TextView) findViewById(R.id.totalPrice);
        totalPriceView.setText("Total price: 0.00 €");
        productList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);
        Call<ProductList> call=api.getPurchase(UUID.fromString(contents));
        call.enqueue(new Callback<ProductList>() {

            @Override
            public void onResponse(Call<ProductList> call, Response<ProductList> response) {
                for(int i=0;i<response.body().getProducts().size();i++){
                productList.add(response.body().getProducts().get(i));
                   totalPrice+=Double.parseDouble(response.body().getProducts().get(i).getPrice());
                }
               // message.setText(products[0].getProducts().toString());

                adapter = new MyProductAdapter(Show_purchase.this, productList);
                listView.setAdapter(adapter);
                totalPriceView.setText("Total price: "+totalPrice);
            }

            @Override
            public void onFailure(Call<ProductList> call, Throwable t) {
               // message.setText("failed");
            }
        });
    }

}
