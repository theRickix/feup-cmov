package cmov.feup.shoppay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.scottyab.aescrypt.AESCrypt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;

import api.ApiService;
import api.RestClient;
import data.User;
import data.UserList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Choosin extends AppCompatActivity {

    Intent intent;
    ImageButton login;
    ImageButton register;
    Activity act;
    private Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosin);

        act=this;

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        String s=loadFile();
        if(s==null) {


            login = (ImageButton) findViewById(R.id.login);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   intent = new Intent(act, LoginActivity.class);
                    startActivity(intent);
                }
            });
            register = (ImageButton) findViewById(R.id.register);
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(act, RegisterActivity.class);
                    startActivity(intent);
                }
            });
        }
        else{

            String read;
            try {
                final User[] user = new User[1];
                read=getStringFromFile("cache.txt");
                ApiService api = RestClient.getApiService();
                Call<UserList> call = api.login(read.split(" ")[0], AESCrypt.encrypt(read.split(" ")[1], "gjNEd34DK"));
                call.enqueue(new Callback<UserList>() {

                    public void onResponse(Call<UserList> call, Response<UserList> response) {

                        if (response.isSuccessful()) {
                            user[0] = new User(response.body().getUsers().get(0));
                            Log.i("Teste", user[0].toString());

                            ApiService api = RestClient.getApiService();
                            Call<User> call2 = api.updateUserPublicKey(user[0].getId(), user[0]);
                            call2.enqueue(new Callback<User>() {

                                public void onResponse(Call<User> call, Response<User> response) {

                                    if(response.isSuccessful()) {
                                        intent = new Intent(act, HomeActivity.class);
                                        intent.putExtra("user", user[0]);
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {
                                    Toast.makeText(act, "Login failed!", Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                        else
                            Toast.makeText(act, "Login failed!", Toast.LENGTH_LONG).show();

                    }


                    @Override
                    public void onFailure(Call<UserList> call, Throwable t) {

                        Toast.makeText(act, "Login failed!", Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
    public String loadFile(){
        String FILENAME = "metadata";
        FileInputStream fin=null;
        try{
            fin=openFileInput(FILENAME);
        }catch (FileNotFoundException e){

            return null;
        }

        byte []a=new byte[368];
        try {
            fin.read(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return a.toString();

    }
    //reading from text, functions offered by android are making my brain burn ahead of time -jp
    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public static String getStringFromFile (String filename) throws Exception {
        //File fl = new File(filePath);
        FileInputStream fin = new FileInputStream(filename);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return ret;
    }
}
