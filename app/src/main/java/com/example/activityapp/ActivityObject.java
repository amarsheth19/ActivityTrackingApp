package com.example.activityapp;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ActivityObject implements Serializable, Parcelable {

    int seconds;
    double distance;
    String date;
    String time;
    int daysIntoYear;
    int month;
    boolean choice;


    public ActivityObject(double d, int s, String da, String t, boolean c){
        seconds=s;
        distance = d;
        date = da;
        time = t;
        choice = c;

        Log.d("MAINACT", s+"");


        month = Integer.parseInt(date.substring(0,2));
        Log.d("monthtag", month+"");

        SimpleDateFormat formatterOld = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
        SimpleDateFormat formatterNew = new SimpleDateFormat("DDD", Locale.getDefault());
        Date date2=null;
        String result = "";
        try {
            date2 = formatterOld.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date2 != null) {
            result = formatterNew.format(date2);
        }
        try{
            daysIntoYear = Integer.parseInt(result);
        }catch (NumberFormatException e) {
            e.printStackTrace();
        }


    }

    protected ActivityObject(Parcel in) {
        seconds = in.readInt();
        distance = in.readDouble();
        date = in.readString();
        time = in.readString();
        daysIntoYear = in.readInt();
        month = in.readInt();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            choice = in.readBoolean();
        }
    }

    public static final Creator<ActivityObject> CREATOR = new Creator<ActivityObject>() {
        @Override
        public ActivityObject createFromParcel(Parcel in) {
            return new ActivityObject(in);
        }

        @Override
        public ActivityObject[] newArray(int size) {
            return new ActivityObject[size];
        }
    };

    public int getDaysIntoYear() {
        return daysIntoYear;
    }

    public int getMonth() {
        return month;
    }

    public double getDistance() {
        return distance;
    }

    public String getDate() {
        return date;
    }

    public int getSeconds() {
        return seconds;
    }

    public String getTime() {
        return time;
    }

    public boolean isChoice() {
        return choice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(seconds);
        dest.writeDouble(distance);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeInt(daysIntoYear);
        dest.writeInt(month);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dest.writeBoolean(choice);
        }
    }
}