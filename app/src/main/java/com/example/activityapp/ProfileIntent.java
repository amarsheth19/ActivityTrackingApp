package com.example.activityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.time.YearMonth;
import java.util.ArrayList;

public class ProfileIntent extends AppCompatActivity {

    Button homeButton;
    Button activityButton;
    TextView lifeTimeDistanceTextView;
    TextView lifeTimeTimeTextView;
    TextView lifeTimeActivitiesTextView;
    GraphView graphView;
    LineGraphSeries<DataPoint> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_intent);


        homeButton = findViewById(R.id.button);
        activityButton = findViewById(R.id.button2);
        lifeTimeDistanceTextView = findViewById(R.id.id_lifeTimeDistance);
        lifeTimeTimeTextView = findViewById(R.id.id_lifeTimeTime);
        lifeTimeActivitiesTextView = findViewById(R.id.id_lifeTimeActivities);






        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //stopService(getIntent());
                finish();
            }
        });


        ArrayList<ActivityObject> activityObjectArrayList = (ArrayList<ActivityObject>) getIntent().getSerializableExtra("list");
        ArrayList<ActivityObject> currentMonthArrayList = new ArrayList<ActivityObject>();

        Log.d("monthcheck0", ""+activityObjectArrayList.get(0).getMonth());

        double totalDistance = 0;
        int totalSeconds = 0;
        int totalNumberOfActivities = 0;

        for(int i = 0; i<activityObjectArrayList.size();i++){
            totalDistance+=activityObjectArrayList.get(i).getDistance();
            totalSeconds+=activityObjectArrayList.get(i).getSeconds();
            Log.d("monthcheck", ""+activityObjectArrayList.get(i).getMonth());
            if(activityObjectArrayList.get(i).getMonth() == 6)
                currentMonthArrayList.add(activityObjectArrayList.get(i));
            Log.d("PROfILE", activityObjectArrayList.get(i).getSeconds()+"");
        }

        Log.d("PROfILE0", "current size: " + currentMonthArrayList.size());

        /*YearMonth yearMonthObject = null;
        int daysInMonth=0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            yearMonthObject = YearMonth.of(1999, 2);
             daysInMonth = yearMonthObject.lengthOfMonth();
        }

         */


        graphView = (GraphView) findViewById(R.id.graph);
        graphView.getViewport().setMaxX(31);
        //graphView.i
        graphView.getGridLabelRenderer().setNumHorizontalLabels(16);
        series = new LineGraphSeries<DataPoint>();
        double y = 0;
        for(int x = 1; x<31; x++){
            y=0;
           for(int i = 0; i<currentMonthArrayList.size();i++){
               Log.d("PROFILELOOP1", Integer.parseInt(currentMonthArrayList.get(i).getDate().substring(3, 5))+"");
               Log.d("PROFILELOOP2",x+"");
               if (Integer.parseInt(currentMonthArrayList.get(i).getDate().substring(3, 5)) == x)
                    y += currentMonthArrayList.get(i).getDistance();
            }
            series.appendData(new DataPoint(x,y),true,500);
        }
        graphView.addSeries(series);
        graphView.setTitle("June 2023");
        graphView.setTitleColor(R.color.purple_200);
        graphView.setTitleTextSize(50);

        int hours = totalSeconds/3600;
        int minutes = totalSeconds/60;
        int seconds = totalSeconds%60;
        String firstSeparation = ":";
        String secondSeparation = ":";

        if(minutes<10)
            firstSeparation = ":0";
        if(seconds<10)
            secondSeparation = ":0";


        lifeTimeTimeTextView.setText("Total Activity Time: " + hours + firstSeparation + minutes + secondSeparation + seconds);
        lifeTimeDistanceTextView.setText("Total Distance Traveled: " + totalDistance +" miles");
        lifeTimeActivitiesTextView.setText("Total Number of Activities: " + activityObjectArrayList.size());




        activityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // ProfileIntent.this.finish();
                Intent intent = new Intent(ProfileIntent.this, ActivityStartIntent.class);
                //intent.putExtra("KEY", "First Button Was Chosen");
                startActivity(intent);
                ProfileIntent.this.finish();
            }
        });




    }
}