package com.example.activityapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActivityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivityFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;
    private boolean mParam5;
    TextView distanceTextView;
    TextView timeTextView;
    TextView dateTextView;
    TextView timePostingTextView;
    TextView paceTextView;


    public ActivityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActivityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActivityFragment newInstance(String param1, String param2, String param3, String param4, boolean param5) {
        ActivityFragment fragment = new ActivityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        args.putBoolean(ARG_PARAM5, param5);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);
            mParam5 = getArguments().getBoolean(ARG_PARAM5);


        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_activity, container, false);

        distanceTextView = (TextView) view.findViewById(R.id.id_distanceTextView);
        timeTextView = (TextView) view.findViewById(R.id.id_timeTextView);
        dateTextView = (TextView) view.findViewById(R.id.id_dateTextView);
        timePostingTextView = (TextView) view.findViewById(R.id.id_timePostingTextView);
        paceTextView = (TextView) view.findViewById(R.id.id_paceTextView);



        if(mParam5) {
            double totalDistance = Double.parseDouble(mParam1);
            int totalSeconds = Integer.parseInt(mParam2);
            double pace = totalSeconds/totalDistance;
            pace/=60;
            int paceMin = (int)pace;
            int paceSec = (int)((pace%1)*60);
            if(paceSec<10)
                paceTextView.setText(paceMin + ":0" + paceSec + "/Mile");
            else
                paceTextView.setText(paceMin + ":" + paceSec + "/Mile");
            if(paceMin>100 || totalSeconds==0 || totalDistance<0.01)
                paceTextView.setText("");
        }
        else {
            double totalDistance = Double.parseDouble(mParam1);
            int totalSeconds = Integer.parseInt(mParam2);
            double pace = totalDistance/totalSeconds;
            pace*=3600;
            double paceFinal = (((int)(pace*10))/((double)10));
            paceTextView.setText(paceFinal + " MPH");
            if(paceFinal>100 || totalSeconds==0 || totalDistance<0.01)
                paceTextView.setText("");
        }


        int seconds = 0;
        try {
            seconds = Integer.parseInt(mParam2);
        }catch (Exception ignored){}
        int minutes = seconds/60;
        seconds = seconds%60;

        distanceTextView.setText("Distance: "+mParam1+" miles");

        if(seconds<10)
            timeTextView.setText("Activity Time: "+minutes+":0"+seconds);
        else
            timeTextView.setText("Activity Time: "+minutes+":"+seconds);


        //Date currentTime = Calendar.getInstance().getTime();
        //String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        //String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        //Log.d("TIME", currentDate);

        if(mParam3.equals("MM-dd-yyyy"))
            mParam3 = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(new Date());
        if(mParam4.equals("HH:MM AM/PM"))
            mParam4 = "12:00 AM";

        dateTextView.setText(mParam3);
        timePostingTextView.setText(mParam4);


        return view;
    }
}