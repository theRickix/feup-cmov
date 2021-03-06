package cmov.feup.shoppay;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.scottyab.aescrypt.AESCrypt;
import com.twinkle94.monthyearpicker.picker.YearMonthPickerDialog;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import api.ApiService;
import api.RestClient;
import data.CardType;
import data.Purchase;
import data.User;
import data.UserList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    // UI references.
    private EditText passwordView;
    private EditText emailView;
    private View mProgressView;
    private View mLoginFormView;
    private Activity act;
    private User user;
    private Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences mPrefs = getSharedPreferences("Login",MODE_PRIVATE);

        if (mPrefs.contains("UserDetails")) {

            try {

                automaticLogin(mPrefs);
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
        }

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        act = this;
        // Set up the login form.passwordView = (EditText) findViewById(R.id.password);
        populateAutoComplete();
        emailView = (EditText) findViewById(R.id.email);
        passwordView = (EditText) findViewById(R.id.password);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    attemptLogin();
                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Button register = (Button) findViewById(R.id.register_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(act, RegisterActivity.class);
                startActivity(intent);
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

    }

    private void automaticLogin(SharedPreferences mPrefs) throws GeneralSecurityException {

        Gson gson = new Gson();
        String json = mPrefs.getString("UserDetails", "");
        User user1 = gson.fromJson(json, User.class);

        final ProgressDialog dialog;
        /**
         * Progress Dialog for User Interaction
         */
        dialog = new ProgressDialog(LoginActivity.this);
        dialog.setTitle(getString(R.string.string_getting_json_title));
        dialog.setMessage(getString(R.string.string_logging_json_message));
        dialog.show();

        ApiService api = RestClient.getApiService();
        Call<UserList> call = api.login(user1.getEmail(), user1.getPassword());
        call.enqueue(new Callback<UserList>() {

            public void onResponse(Call<UserList> call, Response<UserList> response) {

                if (response.isSuccessful()) {
                    user = new User(response.body().getUsers().get(0));
                    Log.i("Teste", user.toString());

                    Intent intent = new Intent(act, HomeActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                    finish();

                }

            }


            @Override
            public void onFailure(Call<UserList> call, Throwable t) {

                //Toast.makeText(act, "Login failed!", Toast.LENGTH_LONG).show();
            }
        });

    }

    /*private void automaticLogin() {
        String s=loadFile();
        if(s!=null) {

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
                                        Intent intent = new Intent(act, HomeActivity.class);
                                        intent.putExtra("user", user[0]);
                                        startActivity(intent);
                                        finish();
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
    }*/

    private void populateAutoComplete() {
        getLoaderManager().initLoader(0, null, this);
    }


    private void attemptLogin() throws GeneralSecurityException, InterruptedException {

        // Reset errors.
        emailView.setError(null);
        passwordView.setError(null);

        // Store values at the time of the login attempt.
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        if (TextUtils.isEmpty(email)) {
            emailView.setError(getString(R.string.error_field_required));
            focusView = emailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailView.setError(getString(R.string.error_invalid_email));
            focusView = emailView;
            cancel = true;
        } else if (TextUtils.isEmpty(password)) {
            passwordView.setError(getString(R.string.error_field_required));
            focusView = passwordView;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user register attempt.
            showProgress(true);

            ApiService api = RestClient.getApiService();
            Call<UserList> call = api.login(email, AESCrypt.encrypt(password, "gjNEd34DK"));
            call.enqueue(new Callback<UserList>() {

                public void onResponse(Call<UserList> call, Response<UserList> response) {

                    if (response.isSuccessful()) {
                        user = new User(response.body().getUsers().get(0));
                        Log.i("Teste", user.toString());

                        ApiService api = RestClient.getApiService();
                        Call<User> call2 = api.updateUserPublicKey(user.getId(),user);
                        call2.enqueue(new Callback<User>() {

                            public void onResponse(Call<User> call, Response<User> response) {

                                if(response.isSuccessful()) {
                                    Intent intent = new Intent(act, HomeActivity.class);
                                    intent.putExtra("user", user);
                                    startActivity(intent);
                                    finish();
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

            showProgress(false);
            /*mAuthTask = new UserLoginTask(email,AESCrypt.encrypt(password,"gjNEd34DK"));
            mAuthTask.execute((Void) null);*/
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        //addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }
/*
    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(RegisterActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        nameView.setAdapter(adapter);
    }
*/

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

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }
}