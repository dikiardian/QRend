package com.example.dikiardian.qrend;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = EventActivity.class.getSimpleName();

    ImageView imageView;
    ProgressBar progressBar;
    TextView generating;
    Button stopButton;
    TextView scanUntuk;

    public final static int QRcodeWidth = 500 ;
    int idEvent;
    String eventName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.loadingCode);
        generating = findViewById(R.id.generating);
        scanUntuk = findViewById(R.id.scan_untuk);
        stopButton = findViewById(R.id.buttonStop);

        Intent intent = getIntent();
        String code = intent.getStringExtra("code");
        idEvent = intent.getIntExtra("eventId",0);
        eventName = intent.getStringExtra("eventName");

        setTitle(eventName);

        CodeToBitmap codeToBitmap = new CodeToBitmap();
        codeToBitmap.execute(code);

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StopEvent stopEvent = new StopEvent();
                stopEvent.execute(idEvent);
            }
        });
    }

    private class CodeToBitmap extends AsyncTask<String,Void,Bitmap> {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            } else {
                Log.d(TAG, "Gagal generate code");
            }
            progressBar.setVisibility(View.INVISIBLE);
            generating.setVisibility(View.INVISIBLE);
            scanUntuk.setVisibility(View.VISIBLE);
            stopButton.setVisibility(View.VISIBLE);

        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            try {
                bitmap = TextToImageEncode(params[0]);
            } catch (WriterException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
    }


    public Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.QRCodeBlackColor):getResources().getColor(R.color.QRCodeWhiteColor);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }


    private class StopEvent extends AsyncTask<Integer,Void,String> {
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

                    //move to HasilActivity
                    Context context = MainActivity.this;
                    Intent intent = new Intent(context, HasilActivity.class);
                    intent.putExtra("eventId", idEvent);
                    intent.putExtra("eventName", eventName);

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
        protected String doInBackground(Integer... params) {
            return NetworkUtils.stopEvent(params[0]);
        }
    }

}