package com.example.assignment1;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;


public class Symtoms extends AppCompatActivity {
    private TextView st;
    private Context sc;
    private HashMap<String,Float> hash;
    private String text;
    private String rat_val;
    private float rating;
    private DB_Upload db_handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symtoms);
        sc = getApplicationContext();
        
        String[] arraySpinner = new String[]{
                "Nausea", "Headache", "Diarrhea", "Soar Throat", "Fever", "Muscle Ache", "Loss of Smell or Taste", "Cough", "Shortness of Breath", "Feeling Tired"
        };
        hash = new HashMap<String, Float>();
        for(int i = 0; i < arraySpinner.length; i++){
            hash.put(arraySpinner[i], (float) 0.0);
        }

        Spinner s = (Spinner) findViewById(R.id.spinner);

        TextView st = (TextView)findViewById(R.id.text_view);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItemText = (String) adapterView.getItemAtPosition(i);
                st.setText("Selected : "+selectedItemText);



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(sc,"No selection",Toast.LENGTH_LONG).show();
            }
        });
        Button getRating = findViewById(R.id.save);
        final RatingBar ratingBar = findViewById(R.id.rating);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rat_val = String.valueOf(ratingBar.getRating());
                rating=ratingBar.getRating();
                Toast.makeText(Symtoms.this,rat_val,Toast.LENGTH_LONG).show();
                text=s.getSelectedItem().toString();
                hash.put(text,rating);
            }
        });

        getRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Symtoms.this,"Uploading",Toast.LENGTH_LONG).show();
                //TextView RespiRateView = (TextView) findViewById(R.id.RespiRateValTextView);
                db_handler = new DB_Upload();
                db_handler.create_logging_database();
                db_handler.create_logging_table();
                db_handler.isComplete();
                boolean status = true;

                for(int i = 0; i < arraySpinner.length; i++){
                    status = status ^ db_handler.upload_logging_data(hash.get(arraySpinner[i]), arraySpinner[i]);
                }

            }
        });

//        upload.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                TextView RespiRateView = (TextView) findViewById(R.id.RespiRateValTextView);
//                db_handler = new DB_Upload();
//                db_handler.create_logging_database();
//                db_handler.create_logging_table();
//                boolean status = true;
//
//                for(int i = 0; i < symptomsList.length; i++){
//                    status = status ^ db_handler.upload_logging_data(hash.get(symptomsList[i]), symptomsList[i]);
//                }
//                if(!status){
//                    Log.d("Failed","Data Upload Failed");
//                    Toast.makeText(SymptomLoggingActivity.this, "Log Failed", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
    }
}
