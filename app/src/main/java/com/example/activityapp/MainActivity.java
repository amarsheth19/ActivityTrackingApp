package com.example.activityapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;



public class MainActivity extends AppCompatActivity{

    Double totalMiles;
    Button homeButton;
    Button recordButton;
    Button profileButton;
    Button manualActivityButton;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ArrayList<ActivityObject> activityFragmentArrayList;



    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        homeButton = findViewById(R.id.button);
        recordButton = findViewById(R.id.button2);
        profileButton = findViewById(R.id.button3);
        manualActivityButton = findViewById(R.id.id_manualActivityButton);


        activityFragmentArrayList = new ArrayList<ActivityObject>();

        SharedPreferences sharedPref = getSharedPreferences("ActivityApp", MODE_PRIVATE);


        try {
            Log.d("SHAREDPREF", "here1");
            activityFragmentArrayList = (ArrayList<ActivityObject>) ObjectSerializer.deserialize(sharedPref.getString("ArrayList", ObjectSerializer.serialize(new ArrayList<ActivityObject>())));
            Log.d("SHAREDPREF", "here2");
        } catch (IOException e) {
            Log.d("SHAREDPREF", "here3");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            Log.d("SHAREDPREF", "here4");
            e.printStackTrace();
        }
       // activityFragmentArrayList = new ArrayList<ActivityObject>();



        new CountDownTimer(300, 1000){
            @Override
            public void onTick(long millisUntilFinished) {}
            @Override
            public void onFinish() {
                homeButton.callOnClick();
            }
        }.start();


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("TEXTTAG", "preasled");
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            Log.d("TEXTTAG", "asled");
        }

        //activityFragmentArrayList.remove(activityFragmentArrayList.size()-3);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i<activityFragmentArrayList.size();i++){


                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    //fragmentTransaction.setReorderingAllowed(true);
                    Log.d("SHAREDPREF", "here5");
                    Log.d("SHAREDPREF", "here6: " + fragmentTransaction.isEmpty());


                    LinearLayout fragContainer = (LinearLayout) findViewById(R.id.scrollViewlinearlayout);

                    //LinearLayout ll = new LinearLayout(MainActivity.this);
                    //ll.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(fragContainer.getWidth(), 250);

                    FrameLayout ll = new FrameLayout(MainActivity.this);
                    ll.setPadding(5,5,0,0);
                    ll.setLayoutParams(lp);


                    ll.setId(View.generateViewId());

                    //ll.setBackgroundColor(Color.GREEN);


                    Log.d("TEDDT", activityFragmentArrayList.get(i).getDistance()+"");
                    Log.d("TEDDT", activityFragmentArrayList.get(i).getSeconds()+"");
                    Log.d("TEDDT", activityFragmentArrayList.get(i).date);
                    Log.d("TEDDT",activityFragmentArrayList.get(i).getTime());
                    Log.d("TEDDT",""+ll.getId());


                    /*ll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            fragContainer.removeView(ll);
                        }
                    });

                     */

                    fragmentTransaction.add(ll.getId(), new ActivityFragment().newInstance(activityFragmentArrayList.get(i).getDistance()+"",activityFragmentArrayList.get(i).getSeconds()+"",activityFragmentArrayList.get(i).date,activityFragmentArrayList.get(i).getTime(),activityFragmentArrayList.get(i).isChoice()));
                    //activityFragmentArrayList.add(new ActivityObject(Double.parseDouble(distance),Integer.parseInt(time),date,postingTime));

                    //if(i==activityFragmentArrayList.size()-1) {
                    try {
                        Log.d("ACTIVITYTAG", "committing");
                        //fragmentTransaction.commitNow();
                        fragmentTransaction.commit();
                        Log.d("SHAREDPREF", "here7: " + fragmentTransaction.isEmpty());

                    } catch (Exception e) {
                        Log.d("ACTIVITYTAG", "catching");
                    }
                    // }

                    fragContainer.addView(ll,0);



                }
            }
        });



        manualActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ManualActivityintent.class);
                //intent.putExtra("KEY", "First Button Was Chosen");
                startActivityForResult(intent,5);
            }
        });

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityStartIntent.class);
                //intent.putExtra("KEY", "First Button Was Chosen");
                //startActivity(intent);
                startActivityForResult(intent,6);
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileIntent.class);
                //intent.putExtra("KEY", "First Button Was Chosen");
                /*for(int i = 0; i<activityFragmentArrayList.size();i++){
                   // Log.d("monthcheck", ""+activityObjectArrayList.get(i).getMonth());
                   // if(activityObjectArrayList.get(i).getMonth() == 6)
                   //     currentMonthArrayList.add(activityObjectArrayList.get(i));
                    Log.d("PROfILEMAINACT", activityFragmentArrayList.get(i).getSeconds()+"");
                }

                 */
                intent.putParcelableArrayListExtra("list",activityFragmentArrayList);

                startActivity(intent);


            }
        });





    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("TAGGG", "here");
        Log.d("TAGGG", ""+requestCode);




        if(resultCode==RESULT_OK && requestCode==5){
            String distance = data.getStringExtra("Distance");
            String time = data.getStringExtra("Time");
            String date = data.getStringExtra("Date");
            String postingTime = data.getStringExtra("PostingTime");
            boolean choice = data.getBooleanExtra("choice",true);



            Log.d("TAGGG", distance);
            Log.d("TAGGG", time);

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();

            LinearLayout fragContainer = (LinearLayout) findViewById(R.id.scrollViewlinearlayout);

            //LinearLayout ll = new LinearLayout(MainActivity.this);
            //ll.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(fragContainer.getWidth(), 250);

            FrameLayout ll = new FrameLayout(MainActivity.this);
            ll.setPadding(5,5,0,0);
            ll.setLayoutParams(lp);


            ll.setId(View.generateViewId());

            String currentDate = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(new Date());
            String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

            fragmentTransaction.add(ll.getId(), new ActivityFragment().newInstance(distance,time,date,postingTime,choice));
            activityFragmentArrayList.add(new ActivityObject(Double.parseDouble(distance),Integer.parseInt(time),date,postingTime,choice));


            try {
                Log.d("ACTIVITYTAG", "committing");
                fragmentTransaction.commit();
            }catch (Exception e){Log.d("ACTIVITYTAG", "catching");}

            fragContainer.addView(ll,0);



            SharedPreferences sharedPref = getSharedPreferences("ActivityApp", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("amount", 5);
            try {
                editor.putString("ArrayList", ObjectSerializer.serialize(activityFragmentArrayList));
            } catch (IOException e) {
                e.printStackTrace();
            }

            editor.apply();
        }


        if(resultCode==RESULT_OK && requestCode==6){
            double distance = data.getDoubleExtra("Distance",0);
            int time = data.getIntExtra("Time",0);
            boolean choice = data.getBooleanExtra("choice",true);


            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();

            LinearLayout fragContainer = (LinearLayout) findViewById(R.id.scrollViewlinearlayout);

            //LinearLayout ll = new LinearLayout(MainActivity.this);
            //ll.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(fragContainer.getWidth(), 250);

            FrameLayout ll = new FrameLayout(MainActivity.this);
            ll.setPadding(5,5,0,0);
            ll.setLayoutParams(lp);



            ll.setId(View.generateViewId());

            String currentDate = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(new Date());
            String currentTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());

            Log.d("Date", "date: "+ currentDate);
            Log.d("Date", "time: "+ currentTime);


            fragmentTransaction.add(ll.getId(), new ActivityFragment().newInstance(""+distance,""+time,currentDate, currentTime,choice));
            activityFragmentArrayList.add(new ActivityObject(distance,time,currentDate,currentTime,choice));
            Log.d("monthcheck-1", ""+activityFragmentArrayList.get(0).getMonth());

            try {
                Log.d("ACTIVITYTAG", "committing");
                fragmentTransaction.commit();
            }catch (Exception e){Log.d("ACTIVITYTAG", "catching");}

            fragContainer.addView(ll,0);



            SharedPreferences sharedPref = getSharedPreferences("ActivityApp", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("amount", 5);
            try {
                editor.putString("ArrayList", ObjectSerializer.serialize(activityFragmentArrayList));
            } catch (IOException e) {
                e.printStackTrace();
            }

            //ArrayList<ActivityObject> currentTasks = new ArrayList<ActivityObject>();

            /*try {
                editor.putString("TASKS", ObjectSerializer.serialize(currentTasks));
            } catch (IOException e) {
                e.printStackTrace();
            }

             */



            editor.apply();
        }





    }


}