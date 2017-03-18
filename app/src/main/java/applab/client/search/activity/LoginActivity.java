package applab.client.search.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import applab.client.search.R;
import applab.client.search.model.Payload;
import applab.client.search.model.UserDetails;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.task.APIRequestListener;
import applab.client.search.task.LoginTask;
import applab.client.search.utils.ConnectionUtil;
import applab.client.search.utils.IctcCKwUtil;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity implements APIRequestListener {

    public static final String TAG = LoginActivity.class.getSimpleName();

    LoginTask mAuthTask = null;

    SharedPreferences preferences;
    DatabaseHelper databaseHelper;

    // Values for email and password at the time of the login attempt.
    private String mUsername;
    private String mPassword;

    // UI references.
    private TextInputLayout user_layout;
    private TextInputLayout password_layout;
    private EditText mUsernameView;
    private EditText mPasswordView;
    private View mLoginFormView;
    private View mLoginStatusView;
    private TextView mLoginStatusMessageView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        getIntent().setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_login);

        databaseHelper = new DatabaseHelper(getBaseContext());
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        user_layout = (TextInputLayout) findViewById(R.id.username_text_input_layout);
        password_layout = (TextInputLayout) findViewById(R.id.password_text_input_layout);

        mUsernameView = (EditText) findViewById(R.id.edit_us_name);
        mPasswordView = (EditText) findViewById(R.id.user_pwd);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(LoginActivity.this.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);

                            if(PermissionsActivity.launchMultiplePermissions(LoginActivity.this)){
                                attemptLogin();
                            }
                            return true;

                    }
                });

        mLoginFormView = findViewById(R.id.login_form);
        mLoginStatusView = findViewById(R.id.login_status);
        mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);
        findViewById(R.id.main_button_login).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {


                        if(PermissionsActivity.launchMultiplePermissions(LoginActivity.this)){

                            attemptLogin();

                        }

                    }
                });

        try {
            Bundle b = getIntent().getExtras();
            String err = b.getString("err");

            if(!err.isEmpty()){

               //mUsernameView.setError(err);
               //mUsernameView.requestFocus();
                user_layout.setError(err);
                user_layout.getEditText().requestFocus();
            }

        } catch(Exception e){ }

    }

    public void apiRequestComplete(Payload response) {
        showProgress(false);
        mAuthTask = null;

        if(response.isResult()){
            try {
                int age=0;
                String id, username, type, name, gender="", mobile="", phone="", location="",  email="";

                JSONObject user = (JSONObject) response.getResponseData().get(0);
                boolean smartExAgent = user.getString("user_type").equals("SmartEx Agent");

                if (smartExAgent) {
                    id = user.getString("userId");
                    username = mUsername;
                    type = user.getString("user_type");
                    name = user.getString("fname") + " " + user.getString("lname");

                    UserDetails u = new UserDetails();
                    u.setUserName(username);
                    u.setFullName(name);

                    try { u.setSalesForceId(user.getString("sfId")); } catch (Exception e) { u.setSalesForceId("00524000001xFMiAAM"); }
                    try { u.setOrganisation(user.getString("org")); } catch (Exception e) { }

                    databaseHelper.resetUser();
                    databaseHelper.saveUserDetail(u);
                    IctcCKwUtil.setUserDetails(LoginActivity.this, u);

                } else {
                    id = user.getString("id");
                    username = user.getString("username");
                    type = user.getString("user_type");
                    name = user.getString("name");
                    age = Integer.parseInt(user.getString("age"));
                    gender = user.getString("gender");
                    phone = user.getString("phone_number");
                    mobile = user.getString("mobile_number");
                    location = user.getString("location").trim();
                    email = user.getString("email");
                }

                ConnectionUtil.setUser(this, id, username, type, name, age, gender,mobile,phone, location, email);

                Intent i = new Intent(LoginActivity.this, StartUpActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                ConnectionUtil.refreshFarmerInfo(LoginActivity.this, i, "us=", "fdetails","Login Successful, Loading Agent Data");

                //startActivity(i);
                //this.finish();

            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                //mUsernameView.setError("Error parsing authentication response");
                //mUsernameView.requestFocus();

                user_layout.setError("Error parsing authentication response");
                user_layout.getEditText().requestFocus();
            }
        } else {
            //mUsernameView.setError(response.getResultResponse());
           // mUsernameView.requestFocus();
            user_layout.setError(response.getResultResponse());
            user_layout.getEditText().requestFocus();
        }
    }

    private void attemptLogin() {

        if (mAuthTask != null) {
            return;
        }

        // Reset errors

        user_layout.setError(null);
        password_layout.setError(null);

        // Store values at the time of the login attempt.
        mUsername = user_layout.getEditText().getText().toString().trim();
        mPassword = password_layout.getEditText().getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password.
        if (TextUtils.isEmpty(mPassword)) {
            //mPasswordView.setError(getString(R.string.error_field_required));
            password_layout.setError(getString(R.string.error_field_required));
            focusView = password_layout.getEditText();
            cancel = true;
        } else if (mPassword.length() < 2) {
            //mPasswordView.setError(getString(R.string.error_invalid_password));
            password_layout.setError(getString(R.string.error_invalid_password));
            focusView = password_layout.getEditText();
            cancel = true;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(mUsername)) {
            user_layout.setError(getString(R.string.error_field_required));
            focusView = user_layout.getEditText();
            cancel = true;
        } else if (mUsername.length() < 3) {
            user_layout.setError("Invalid username");
            focusView = user_layout.getEditText();
            cancel = true;
        }

        // Check for connection
        if (!ConnectionUtil.isNetworkConnected(this)) {
            user_layout.setError("No internet connection");
            focusView = user_layout.getEditText();
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mLoginStatusMessageView.setText("Signing in...");
            showProgress(true);

            String hash = MD5("ictchallenge" + mPassword.trim()+"2016");
            ArrayList<Object> requestData = new ArrayList<Object>();
            requestData.add(mUsername);
            requestData.add(hash);
            Payload p = new Payload(requestData);
            mAuthTask = new LoginTask();
            mAuthTask.setListener(this);
            mAuthTask.execute(p);
        }
    }

    private String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(
                    android.R.integer.config_shortAnimTime);

            mLoginStatusView.setVisibility(View.VISIBLE);
            mLoginStatusView.animate().setDuration(shortAnimTime)
                    .alpha(show ? 1 : 0)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mLoginStatusView.setVisibility(show ? View.VISIBLE
                                    : View.GONE);
                        }
                    });

            mLoginFormView.setVisibility(View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime)
                    .alpha(show ? 0 : 1)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mLoginFormView.setVisibility(show ? View.GONE
                                    : View.VISIBLE);
                        }
                    });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();

        LoginActivity.this.finish();


    }
}