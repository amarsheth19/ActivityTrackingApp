package com.example.activityapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

public class ActivityStartIntent extends AppCompatActivity implements LocationListener {

    Button homeButton;
    Button profileButton;
    Button startButton;
    Button recordButton;
    TextView distanceandTimeTempTextView;
    LocationManager locationManager;
    Long startTime;
    Location initialLocation;
    Boolean inActivity = false;
    Boolean firstStart = true;
    Long endTime;
    Double totalDistance = 0.0;
    int seconds;
    Button finishButton;
    ConstraintLayout myLayout;
    RadioGroup radioGroup;
    boolean choice;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    FrameLayout frameLayout;
    MapsFragment mapsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_intent);

        AsyncThread asyncThread = new AsyncThread();
        asyncThread.execute();


        homeButton = findViewById(R.id.button);
        profileButton = findViewById(R.id.button3);
        startButton = findViewById(R.id.button4);
        myLayout = findViewById(R.id.id_layout);
        distanceandTimeTempTextView = findViewById(R.id.id_distanceandtimetextViewTemp);
        radioGroup = findViewById(R.id.radioGroup);
        recordButton = findViewById(R.id.button2);
        frameLayout = findViewById(R.id.id_mapFragment);

        mapsFragment = new MapsFragment();


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("TEXTTAG", "preasled");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            Log.d("TEXTTAG", "asled");
        }

        locationManager = (LocationManager) (getSystemService(LOCATION_SERVICE));
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000L, 1f, this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Log.d("INITIALTAG", "location.toString()");
            locationManager.getCurrentLocation(
                    LocationManager.GPS_PROVIDER,
                    null,
                    getApplication().getMainExecutor(),
                    new Consumer<Location>() {
                        @Override
                        public void accept(Location location) {
                            Log.d("INITIALTAG", location.toString());
                            initialLocation = location;
                        }
                    });
        }


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

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //stopService(getIntent());
                finish();
            }
        });


        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ActivityStartIntent.this.finish();
                Intent intent = new Intent(ActivityStartIntent.this, ProfileIntent.class);
                //intent.putExtra("KEY", "First Button Was Chosen");
                startActivity(intent);
                ActivityStartIntent.this.finish();
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(inActivity) {
                    endTime = System.currentTimeMillis();
                    inActivity = false;


                    startButton.setText("Resume");
                    finishButton = new Button(ActivityStartIntent.this);
                    finishButton.setId(View.generateViewId());
                    finishButton.setText("Finish");



                    ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    finishButton.setLayoutParams(lp);


                    //finishButton.setBackgroundColor(Color.BLUE);
                    myLayout.addView(finishButton);

                    ConstraintSet cs = new ConstraintSet();
                    cs.clone(myLayout);

                    cs.connect(finishButton.getId(),ConstraintSet.TOP,startButton.getId(),ConstraintSet.TOP);
                    cs.connect(finishButton.getId(),ConstraintSet.BOTTOM,startButton.getId(),ConstraintSet.BOTTOM);
                    cs.connect(finishButton.getId(),ConstraintSet.LEFT,myLayout.getId(),ConstraintSet.LEFT);
                    cs.connect(finishButton.getId(),ConstraintSet.RIGHT,myLayout.getId(),ConstraintSet.RIGHT);


                    cs.setHorizontalBias(finishButton.getId(),0.7f);
                    cs.setHorizontalBias(startButton.getId(),0.3f);
                    cs.setVerticalBias(finishButton.getId(),0f);




                    cs.applyTo(myLayout);



                    finishButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent();

                            intent.putExtra("Distance", totalDistance);
                            intent.putExtra("Time", seconds);
                            intent.putExtra("choice", choice);

                            setResult(RESULT_OK, intent);


                            finish();


                        }
                    });



                }
                else {
                    inActivity = true;
                    if(firstStart){
                        startTime = System.currentTimeMillis();
                        endTime = System.currentTimeMillis();
                        firstStart = false;
                    }


                    startButton.setText("Stop");
                    ConstraintSet cs = new ConstraintSet();
                    cs.clone(myLayout);
                    cs.setHorizontalBias(startButton.getId(),0.5f);
                    cs.applyTo(myLayout);
                    try {
                        myLayout.removeView(finishButton);
                    }catch (Exception e){}


                }





            }
        });


        /*if(finishButton != null) {

        }

         */
        new CountDownTimer(300, 1000){
            @Override
            public void onTick(long millisUntilFinished) {}
            @Override
            public void onFinish() {
                recordButton.callOnClick();
            }
        }.start();


        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();




        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                fragmentTransaction.add(R.id.id_mapFragment, mapsFragment);



                try {
                    Log.d("ACTIVITYTAG", "committing");
                    //fragmentTransaction.commitNow();
                    fragmentTransaction.commit();


                } catch (Exception e) {
                    Log.d("ACTIVITYTAG", "catching");
                }


            }




        });





    }


    @Override
    public void onLocationChanged(@NonNull Location location) {


        Double latitude = location.getLatitude();
        Double longitude = location.getLongitude();
        mapsFragment.moveMap(longitude,latitude);

        if(inActivity) {


            try {
                int count = 0;






                /*if (initialLocation == null) {
                    Log.d("TAG", "point3");
                    initialLocation = location;
                    Log.d("TAG", "point4");
                } else {

                 */







                    // timeSpentTextView.setText("Time Spent at favorite location: " + longestTime + " seconds");


                    try {
                        double temp = initialLocation.distanceTo(location);
                        double miles =  ((int)((temp/1609)*100))/((double)100);
                        totalDistance += miles;
                    }catch (Exception e) {
                        Log.d("TAG", e+"");
                    }

                    //int tempRound = (int)((totalDistance/1609)*10)/(double(10));
                    //totalDistanceTextView.setText("Total Distance: " + (((int) (100 * (totalDistance / 1609))) / (double) 100) + " miles");
                    //distanceArrayList.add((double) initialLocation.distanceTo(location));
                    initialLocation = location;
                    //startTime = endTime;
                //}




            } catch (Exception e) {
                Log.d("TAG", "exception");
            }


        }
    }


    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }



    public class AsyncThread extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {

                    if(inActivity) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {









                                seconds++;
                                int minutes = seconds/60;
                                int tempSeconds = seconds%60;
                                if(tempSeconds<10)
                                    distanceandTimeTempTextView.setText("Time: " + minutes + ":0" + tempSeconds + "\n Distance: " + totalDistance + " Miles");
                                else
                                    distanceandTimeTempTextView.setText("Time: " + minutes + ":" + tempSeconds + "\n Distance: " + totalDistance + " Miles");


                            }
                        });
                    }


                }
            },0,1000);
            return null;
        }
    }


}