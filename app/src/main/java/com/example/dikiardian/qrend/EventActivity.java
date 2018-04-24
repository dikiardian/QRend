package com.example.dikiardian.qrend;

import android.app.ActionBar;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class EventActivity extends AppCompatActivity {

    public static final String TAG = EventActivity.class.getSimpleName();

    private RadioGroup radioGroup;
    private Button mulaiButton;
    private TextView durasiLabel;
    private Spinner durasi;

    private int idUser;
    private int idEvent;
    private int isManual = 1;
    private String code;
    private String eventName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Intent intent = getIntent();
        idEvent = intent.getIntExtra("eventId", 0);
        eventName = intent.getStringExtra("eventName");

        setTitle(eventName);

        SharedPreferences prefs = getSharedPreferences(LoginActivity.PREFS_NAME, MODE_PRIVATE);
        idUser = prefs.getInt("idUser", 0);

        radioGroup = findViewById(R.id.radioGroupMetode);
        durasiLabel = findViewById(R.id.durasi_label);
        durasi = findViewById(R.id.durasi);
        mulaiButton = findViewById(R.id.buttonMulai);

        String[] arraySpinner = new String[] {
            "5 Menit", "30 Menit", "1 Jam", "2 Jam"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        durasi.setAdapter(adapter);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
               if (checkedId == R.id.radioButtonManual) {
                   durasi.setVisibility(View.INVISIBLE);
                   durasiLabel.setVisibility(View.INVISIBLE);
                   isManual = 1;
               } else {
                   durasi.setVisibility(View.VISIBLE);
                   durasiLabel.setVisibility(View.VISIBLE);
                   isManual = 0;
               }
            }
        });

        mulaiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mulai(idUser, idEvent, isManual, durasi.getSelectedItemPosition());
            }
        });

    }

    void mulai(int idUser, int idEvent, int isManual, int idDuration) {
        MulaiEvent mulaiEvent = new MulaiEvent();
        mulaiEvent.execute(idUser, idEvent, isManual, idDuration);
    }

    private class MulaiEvent extends AsyncTask<Integer,Void,String> {
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

                    //generate code

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                    Date now = new Date();
                    String strDate = sdf.format(now);

                    code = "" + idUser + idEvent + strDate;
                    GenerateCode generateCode = new GenerateCode();
                    generateCode.execute(""+idUser, ""+idEvent, code, "1");

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
            return NetworkUtils.startEvent(params[0], params[1], params[2], params[3]);
        }
    }

    private class GenerateCode extends AsyncTask<String,Void,String> {
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

                    Context context = EventActivity.this;
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("code", code);
                    intent.putExtra("eventName", eventName);
                    intent.putExtra("eventId", idEvent);

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
            return NetworkUtils.generateCode(params[0], params[1], params[2], params[3]);
        }
    }
}
