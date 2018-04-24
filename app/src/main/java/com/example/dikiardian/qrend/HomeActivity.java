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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    public static final String TAG = HomeActivity.class.getSimpleName();
    private String username;
    private int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences prefs = getSharedPreferences(LoginActivity.PREFS_NAME, MODE_PRIVATE);
        username = prefs.getString("username", "");
        idUser = prefs.getInt("idUser", 0);

        //get event list
        GetEventList getEventList = new GetEventList();
        getEventList.execute(idUser);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_menu:
                signOutUser(username);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void signOutUser(String username) {
        SignOutUser signOutUser = new SignOutUser();
        signOutUser.execute(username);
    }

    private class SignOutUser extends AsyncTask<String,Void,String> {
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

                    //save state
                    SharedPreferences.Editor editor = getSharedPreferences(LoginActivity.PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putInt("loginStatus", 0);
                    editor.apply();

                    //move to MainActivity
                    Context context = HomeActivity.this;
                    Intent intent = new Intent(context, LoginActivity.class);
//                    intent.putExtra("user", user);
                    context.startActivity(intent);
                    finish();
                } else {
                    //status == 0
                    String message = jsonObject.getString("msg");
                    Log.d(TAG, message);

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
            return NetworkUtils.signOutUser(params[0]);
        }
    }

    private class GetEventList extends AsyncTask<Integer,Void,String> {
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

                    JSONArray data = jsonObject.getJSONArray("data");

                    Log.d(TAG, data.toString());

                    //update list


                } else {
                    //status == 0
                    String message = jsonObject.getString("msg");
                    Log.d(TAG, message);

                    //show message
                    Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                    toast.show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Integer... params) {
            return NetworkUtils.getEventList(params[0]);
        }
    }
}
