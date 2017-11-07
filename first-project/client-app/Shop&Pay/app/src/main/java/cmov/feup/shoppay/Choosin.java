package cmov.feup.shoppay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Choosin extends AppCompatActivity {

    Intent intent;
    ImageButton login;
    ImageButton register;
    Activity act;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosin);


        String s=loadFile();
        if(s.equals(null)) {


            login = (ImageButton) findViewById(R.id.login);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // intent = new Intent(act, LoginActivity.class);
                }
            });
            register = (ImageButton) findViewById(R.id.register);
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(act, RegisterActivity.class);
                }
            });
        }
        else{
            intent=new Intent(act,HomeActivity.class);
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

}
