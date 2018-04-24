package com.example.dikiardian.qrend;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();
    public static final String PREFS_NAME = "qrendUserState";

    private EditText usernameField;
    private EditText passwordField;
    private Button loginButton;
    private ProgressBar loadingLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameField = findViewById(R.id.username);
        passwordField = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        loadingLogin = findViewById(R.id.loadingLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingLogin.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.INVISIBLE);
                login(usernameField.getText().toString(), passwordField.getText().toString());
            }
        });

        //check if logged in
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int loginStatus= prefs.getInt("loginStatus", 0);

        if (loginStatus == 1) {
            //move to MainActivity
            Context context = LoginActivity.this;
            Intent intent = new Intent(context, HomeActivity.class);
            context.startActivity(intent);
            finish();
        }

    }

    private void login(String username, String password) {
        SignInUser signInUser = new SignInUser();
        signInUser.execute(username,password);
    }

    private class SignInUser extends AsyncTask<String,Void,String> {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                String status = jsonObject.getString("status");
                if (Objects.equals(status, "1")) {
                    String message = jsonObject.getString("msg");
                    Log.d(TAG, message);
                    JSONObject data = jsonObject.getJSONObject("data");
                    String username = data.getString("username");
                    int idUser = data.getInt("id_user");

                    //save user state
                    SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("username", username);
                    editor.putInt("idUser", idUser);
                    editor.putInt("loginStatus", 1);
                    editor.apply();

                    //reset view visibility
                    loadingLogin.setVisibility(View.INVISIBLE);
                    loginButton.setVisibility(View.VISIBLE);

                    //move to MainActivity
                    Context context = LoginActivity.this;
                    Intent intent = new Intent(context, HomeActivity.class);
//                    intent.putExtra("user", user);
                    context.startActivity(intent);
                    finish();
                } else {
                    //status == 0
                    String message = jsonObject.getString("msg");
                    Log.d(TAG, message);

                    //reset view visibility
                    loadingLogin.setVisibility(View.INVISIBLE);
                    loginButton.setVisibility(View.VISIBLE);

                    //show message
                    Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                    toast.show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return NetworkUtils.signInUser(params[0], params[1]);
        }
    }
}
