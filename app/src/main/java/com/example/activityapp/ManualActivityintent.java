package com.example.activityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ManualActivityintent extends AppCompatActivity {

    EditText DistanceEditText;
    EditText timeEditText;
    Button finishButton;
    EditText dateEditText;
    EditText postingTimeEditText;
    RadioGroup radioGroup;
    boolean choice;
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_activityintent);


        DistanceEditText = findViewById(R.id.id_editTextDistance);
        timeEditText = findViewById(R.id.id_editTextTime);
        finishButton = findViewById(R.id.id_finishManual);
        dateEditText = findViewById(R.id.id_editTextDate);
        postingTimeEditText = findViewById(R.id.id_editTextPostingTime);
        radioGroup = findViewById(R.id.radioGroup);
        spinner = findViewById(R.id.spinner);


        String currentDate = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(new Date());
        dateEditText.setText(currentDate);
        String currentTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
        postingTimeEditText.setText(currentTime.substring(0,5));


        String[] items = new String[2];
        if(currentTime.substring(6,8).equals("AM"))
            items = new String[]{"AM", "PM"};
        else
            items = new String[]{"PM", "AM"};
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //spinner.setSelection(0);
        radioGroup.check(R.id.radioButton);
        choice = true;

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioButton)
                    choice = true;
                if(checkedId == R.id.radioButton2)
                    choice = false;
            }
        });


        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();

                int hours = Integer.parseInt(timeEditText.getText().toString().substring(0,2));
                int minutes = Integer.parseInt(timeEditText.getText().toString().substring(3,5));
                int seconds = Integer.parseInt(timeEditText.getText().toString().substring(6));


                int totalTime = seconds+(60*minutes)+(3600*hours);

                intent.putExtra("Distance", DistanceEditText.getText().toString());
                intent.putExtra("Time", totalTime+"");
                intent.putExtra("Date", dateEditText.getText().toString());
                intent.putExtra("PostingTime", postingTimeEditText.getText().toString() + " " +spinner.getSelectedItem().toString());
                intent.putExtra("choice", choice);

                setResult(RESULT_OK, intent);

                Log.d("TAGGG2", "jere");
                Log.d("TAGGG2", DistanceEditText.getText().toString());
                Log.d("TAGGG2", timeEditText.getText().toString());



                finish();
            }
        });


    }
}