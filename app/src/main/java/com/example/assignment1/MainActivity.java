package com.example.assignment1;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity<videoName> extends AppCompatActivity {
    private DB_Upload db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button s = findViewById(R.id.SYMPTOMS);
        Button h = findViewById(R.id.HEART_RATE);
        Button r = findViewById(R.id.measure_respiratory_rate);
        Button u = findViewById(R.id.UPLOAD_SIGNS);

        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(MainActivity.this, Symtoms.class);
                startActivity(i1);

            }
        });
        h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent heartInt = new Intent(MainActivity.this, HeartRateActivity.class);
                startActivity(heartInt);



            }
        });
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent respiInt = new Intent(MainActivity.this, Respiratory.class);
                startActivity(respiInt);
            }
        });
        u.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        db = new DB_Upload();
                        db.create_database();
                        db.create_table();
                        db.isComplete();
                        if(true) {
                            if(db.insert_row())
                                Toast.makeText(MainActivity.this, "Data Uploaded Sucessfully", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(MainActivity.this, "Measured symptoms long ago try measuring again", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        }







