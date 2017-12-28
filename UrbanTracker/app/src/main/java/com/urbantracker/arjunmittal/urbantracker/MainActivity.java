package com.urbantracker.arjunmittal.urbantracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;

import android.view.Menu;
import android.view.View;

import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;

public class MainActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView, totalView;
    private int year, month, day;
    Convert convert = new Convert();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateView = (TextView) findViewById(R.id.textView3);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        totalView = (TextView) findViewById(R.id.textView4);
    }

    public String getPath (int y, int m, int d){
        return getPath (y, m, d, 0);
    }

    public String getPath (int y, int m, int d, int h){
        String r = "";

        r += Integer.toString (y) + "-" + Integer.toString (m) + "-" + Integer.toString (d) + "-" + Integer.toString (h);
        return r;
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(),"ca",
                Toast.LENGTH_SHORT)
                ;
    }

    public String getEndDate (){
        Calendar c = Calendar.getInstance();

        c.add (Calendar.DATE, 1);
        Date d = c.getTime ();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String temp = s.format(d);

        temp = temp.replaceAll ("T", "-");
        temp = temp.substring (2, 13);
        temp = temp.replaceAll ("-0", "-");
        return temp;
    }

    public void getTotal (View view) {
        String r = getPath (year-2000,month+1,day);
        System.out.println(day);
        double t;
        if (day==5) {
            t = convert.getUsageTotal(r, getEndDate(), 0);
        }
        else
            t = convert.getUsageTotal (r, getEndDate(), 3);
        showTotal (t);
    }

    public void getTotalLight (View view) {
        String r = getPath (year-2000,month+1,day);
        double t;
        if (day==5) {
            t = convert.getUsageTotal(r, getEndDate(), 1);
        }
        else
            t = convert.getUsageTotal (r, getEndDate(), 4);
        showTotal (t);

    }

    public void getTotalHeat (View view) {
        String r = getPath (year-2000,month+1,day);
        double t;
        if (day==5) {
            t = convert.getUsageTotal(r, getEndDate(), 2);
        }
        else
            t = convert.getUsageTotal (r, getEndDate(), 5);
        showTotal (t);
    }

    public void showTotal (double t) {
        totalView.setText (new StringBuilder().append(Double.toString(t)).append(" ").append("trees"));
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }
}



