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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dikiardian.qrend.model.Event;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class HomeActivity extends AppCompatActivity {

    public static final String TAG = HomeActivity.class.getSimpleName();
    private String username;
    private int idUser;

    private ListView listEvent;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences prefs = getSharedPreferences(LoginActivity.PREFS_NAME, MODE_PRIVATE);
        username = prefs.getString("username", "");
        idUser = prefs.getInt("idUser", 0);

        listEvent = findViewById(R.id.list_event);
        progressBar = findViewById(R.id.loading_list_event);

//        get event list
        GetEventList getEventList = new GetEventList();
        getEventList.execute(idUser);

        listEvent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Event event = (Event) parent.getItemAtPosition(position);

                Intent intent = new Intent(HomeActivity.this, EventActivity.class);

                intent.putExtra("eventId", event.getEventId());
                intent.putExtra("eventName", event.getEventName());
                intent.putExtra("eventSubname", event.getEventSubname());
                startActivity(intent);
            }
        });


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
                    List<Event> eventList = new ArrayList<>();

                    for(int i = 0; i < data.length(); i++) {
                        JSONObject datum = data.getJSONObject(i);
                        eventList.add(new Event(datum.getInt("id_event"), datum.getString("event_name"), datum.getString("event_subname")));
                    }

                    ListEventAdapter adapter = new ListEventAdapter(HomeActivity.this, R.layout.event_list_layout, eventList);

                    listEvent.setAdapter(adapter);

                    listEvent.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);

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
