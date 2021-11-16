package com.example.assignment1;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class Respiratory extends AppCompatActivity {
    String value;
    DB_Upload db_handler;
    private ProgressDialog Dialog;
    private final BroadcastReceiver msgReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Display the respiratory rate
            value = intent.getStringExtra("RRvalue");


            Dialog.dismiss();

            TextView RespiRateView = findViewById(R.id.RespiRateValTextView);
            RespiRateView.setText("RESPIRATORY RATE IS " + value);
            Button measure = findViewById(R.id.RespiRateBtn);
           // Button upload = findViewById(R.id.RespiRateUpData);




        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respiratory);
        Button measure = findViewById(R.id.RespiRateBtn);
        //Button upload = findViewById(R.id.RespiRateUpData);


        measure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent measureRate = new Intent(Respiratory.this, RespiratoryRateService.class);
                Dialog = new ProgressDialog(getApplicationContext());
                Dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                Dialog = ProgressDialog.show(Respiratory.this, "Measuring", "Respiratory Rate...", false, false);
                startService(measureRate);


                //}
            }
        });
        TextView RespiRateView = findViewById(R.id.RespiRateValTextView);
        db_handler = new DB_Upload();
        db_handler.create_logging_database();
        db_handler.create_logging_table();
        db_handler.isComplete();

        //if (db_handler.upload_logging_data(Integer.parseInt(value), "RespiratoryRate")) {
        Toast.makeText(Respiratory.this, "Data Uploaded", Toast.LENGTH_LONG).show();

    }
    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(msgReceiver, new IntentFilter("Respiratory Rate"));
    }
@Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(msgReceiver);
    }

}