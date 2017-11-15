package cmov.feup.shoppay;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.scottyab.aescrypt.AESCrypt;
import com.twinkle94.monthyearpicker.picker.YearMonthPickerDialog;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import api.ApiService;
import api.RestClient;
import data.CardType;
import data.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserRegisterTask mAuthTask = null;

    // UI references.
    private EditText nameView;
    private EditText addressView;
    private EditText passwordView;
    private Spinner s;
    private EditText ccNumberView;
    private EditText ccExpirationView;
    private EditText emailView;
    private EditText postalCodeView;
    private View mProgressView;
    private View mLoginFormView;
    EditText fiscalNumberView;
    private Activity act;

    private int expiryMonth,expiryYear;
    private Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        act = this;

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Set up the login form.
        nameView = (EditText) findViewById(R.id.name);
        passwordView = (EditText) findViewById(R.id.password);
        populateAutoComplete();
        s= (Spinner)findViewById(R.id.credit_card_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.card_types_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        s.setAdapter(adapter);

        fiscalNumberView = (EditText) findViewById(R.id.fiscal_number);
        ccNumberView = (EditText) findViewById(R.id.credit_card_number);
        ccExpirationView = (EditText) findViewById(R.id.credit_card_expiration);
        emailView = (EditText) findViewById(R.id.email);
        postalCodeView = (EditText) findViewById(R.id.postal_code);

        //create Picker Dialog
        final YearMonthPickerDialog yearMonthPickerDialog = new YearMonthPickerDialog(this, new YearMonthPickerDialog.OnDateSetListener() {
            @Override
            public void onYearMonthSet(int year, int month) {
                expiryMonth = month;
                expiryYear = year;
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);

                SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM/yyyy");

                ccExpirationView.setText(dateFormat.format(calendar.getTime()));
            }
        });

        //Assign Picker to Expiration Date
        ccExpirationView.setInputType(InputType.TYPE_NULL);
        ccExpirationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yearMonthPickerDialog.show();
            }
        });
        ccExpirationView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    yearMonthPickerDialog.show();
                }
            }
        });

        addressView = (EditText) findViewById(R.id.address);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    attemptRegister();
                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                }
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void populateAutoComplete() {
        getLoaderManager().initLoader(0, null, this);
    }


    private void attemptRegister() throws GeneralSecurityException {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        nameView.setError(null);
        addressView.setError(null);

        // Store values at the time of the login attempt.
        String name = nameView.getText().toString();
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();
        String address = addressView.getText().toString();
        String postalCode = postalCodeView.getText().toString();
        String fiscalNumber = fiscalNumberView.getText().toString();
        String ccNumber = ccNumberView.getText().toString();
        String ccType = s.getSelectedItem().toString();

        boolean cancel = false;
        View focusView = null;


        if (TextUtils.isEmpty(name)) {
            nameView.setError(getString(R.string.error_field_required));
            focusView = nameView;
            cancel = true;
        }
        else if (TextUtils.isEmpty(email)) {
            emailView.setError(getString(R.string.error_field_required));
            focusView = emailView;
            cancel = true;
        }
        else if (!isEmailValid(email)) {
            emailView.setError(getString(R.string.error_invalid_email));
            focusView = emailView;
            cancel = true;
        }
        else if (TextUtils.isEmpty(password)) {
            passwordView.setError(getString(R.string.error_field_required));
            focusView = passwordView;
            cancel = true;
        }
        else if (TextUtils.isEmpty(address)) {
            addressView.setError(getString(R.string.error_field_required));
            focusView = addressView;
            cancel = true;
        }
        else if (TextUtils.isEmpty(postalCode)) {
            postalCodeView.setError(getString(R.string.error_field_required));
            focusView = postalCodeView;
            cancel = true;
        }
        else if (TextUtils.isEmpty(fiscalNumber)) {
            fiscalNumberView.setError(getString(R.string.error_field_required));
            focusView = fiscalNumberView;
            cancel = true;
        }
        else if (TextUtils.isEmpty(ccNumber)) {
            ccNumberView.setError(getString(R.string.error_field_required));
            focusView = ccNumberView;
            cancel = true;
        }
        else if (expiryMonth==0 || expiryYear==0) {
            ccExpirationView.setError(getString(R.string.error_field_required));
            focusView = ccExpirationView;
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
            User user = new User(name,email,AESCrypt.encrypt(password,"gjNEd34DK"),address,postalCode,fiscalNumber, CardType.valueOf(ccType),ccNumber,expiryMonth,expiryYear);

            Log.i("Teste",user.toString());

            mAuthTask = new UserRegisterTask(user);
            mAuthTask.execute((Void) null);
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

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }



    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final User mUser;

        UserRegisterTask(User user) {
            mUser = user;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            final boolean[] bool = {true};
            ApiService api = RestClient.getApiService();
            Call<User> call = api.register(mUser);
            call.enqueue(new Callback<User>() {

                public void onResponse(Call<User> call, Response<User> response) {

                    if(response.isSuccessful())
                        bool[0] = true;
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    bool[0] = false;
                }
            });

            return bool[0];
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
                //save private informations for quick launch 
                saveMetaFile(mUser);
                saveCache(mUser);
                Intent intent = new Intent(act, HomeActivity.class);
                intent.putExtra("user", mUser);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(act, "Registration error.", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
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
    public void saveCache(User user){
        String FILENAME="cache.txt";
        FileOutputStream fos=null;
        try {
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write((user.getEmail()+ " "+ user.getPassword()).getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public void saveMetaFile(User user){
        String FILENAME = "metadata";


        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(user.getPrivate_key().getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

