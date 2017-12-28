package com.urbantracker.arjunmittal.urbantracker;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.*;
import java.io.IOException;
import java.lang.*;

/**
 * Created by jshah on 2017-02-03.
 */

public class Convert {

    public double sqft = 2687;
    public double lightEff = 60; // 60W is a common light bulb usage
    public double heatEff = 0;
    public String preferredUnit;
    public double RSI = 25;
    public double lol = 0.5;

    public Convert (int lightEff, int sqft){
        this.lightEff = lightEff;
        this.sqft = sqft;
    }

    public Convert (){

    }

    public String [] getData (String path) throws IOException
    {
        String temp = "";
        try {
            FileInputStream fis = new FileInputStream (path);
            InputStreamReader isr = new InputStreamReader(fis);

            while (isr.read () != -1) {
                temp += isr.read();
            }
            fis.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        String [] data = temp.split ("\n");

        return data;

    }
        /*int noLines = 60;
        BufferedReader reader = new BufferedReader(new FileReader (path));
        String [] data = new String [noLines];
        data [0] = reader.readLine ();
        for (int i =0; i < noLines; i++){
            data [i] = reader.readLine();
        }
        System.out.println (data);
        return data;*/
    //}

    public String incrementTime (String startTime){

        String ans = "";
        String [] sTime;
        int [] t = new int [4];


        sTime = startTime.split ("-");
        for (int i = 0; i <4; i++){
            t[i] = Integer.parseInt(sTime[i]);
        }

        if (t[3] < 23){
            t[3]+= 1;
        }
        else{
            t[3] = 0;
            if (t[1] == 12){
                if (t[2] == 31){
                    t[0] += 1;
                    t[1] = 1;
                    t[2] = 1;
                }
                else{
                    t[2] ++;
                }
            }
            else if (t[1] == 1 || t[1] == 3 || t[1] == 5|| t[1] == 7 || t[1] == 8 || t[1] == 10){
                if (t[2] < 30){
                    t[2] ++;
                }
                else {
                    t[1] ++;
                    t[2] = 1;

                }
            }
            else if (t[1] == 4 || t[1] == 6 || t[1] == 9|| t[1] == 11){
                if (t[2] < 29){
                    t[2] ++;
                }
                else {
                    t[1] ++;
                    t[2] = 1;
                }
            }
            else{
                if (t[1] == 2){
                    if ((t[0]% 4 == 0 && t[2] < 28) || (t[0]% 4 != 0 && t[2] < 27)){
                        t[2] ++;
                    }
                    else {
                        t[1] ++;
                        t[2] = 1;
                    }
                }
            }

        }

        for (int i = 0; i< 4; i++){
            ans += Integer.toString(t[i]);
            ans+= "-";
        }

        return ans.substring (0, ans.length() - 1);

    }


    public double getUsageHeat (String startTime, String endTime){
        String [] data;
        double sum = 0;
        System.out.println (startTime);
        System.out.println (endTime);


        while (startTime.equals (endTime) != true && Integer.parseInt (startTime.substring (0, startTime.indexOf ("-"))) <= Integer.parseInt (endTime.substring (0, endTime.indexOf ("-"))) ){
            try{
                data = getData (startTime);
                for (int i = 0; i < 60; i++){
                    sum += getChangeInTemp (data [i]);
                }

            }
            catch (IOException e){

                break;
            }


            startTime = incrementTime(startTime);
        }
        return 0.00000219826*lol*sum*60*SA()/RSI;
    }

    public double getUsageTotal (String r, String y, int x){
        if (x == 0) {
            return 5.6;
        }
        else if (x==1){
            return 2.4;
        }
        else if (x==2){
            return 3.2;
        }
        else if (x==3){
            return 6.1;
        }
        else if (x==4){
            return 2.6;
        }
        else {
            return 3.5;
        }
    }

    public double getUsageLight (String startTime, String endTime){
        String [] data;
        String [] temp;
        int count = 0;
        while (startTime.equals (endTime) != true && Integer.parseInt (startTime.substring (0, startTime.indexOf ("-"))) <= Integer.parseInt (endTime.substring (0, endTime.indexOf ("-"))) ){
            try{
                data = getData (startTime);
                for (int i = 0; i < data.length; i++){
                    temp = data[i].split (" ");

                    if (temp [0] == "ON"){
                        count += 1;
                    }
                }
            }
            catch (IOException e){

                break;
            }
            startTime = incrementTime(startTime);
        }
        return 0.00000219826*count*60*lightEff;
    }

    public double getUsageTotal (String startTime, String endTime){
      //  System.out.println (getUsageHeat(startTime, endTime) + getUsageLight(startTime, endTime));

        return getUsageLight(startTime, endTime)+getUsageHeat(startTime, endTime);
    }

    public double sqftToSqm (double sqft){
        return 0.092903 * sqft;
    }

    public double SA (){
        return Math.sqrt (sqftToSqm (this.sqft))*2.1336;
    }

    public void setSqft (double sqft){
        this.sqft = sqft;
    }

    public double getChangeInTemp (String data){
        String [] temp = data.split (" ");
        return Math.abs(Double.parseDouble(temp[2]) - Double.parseDouble(temp [1]));
    }


    public void setLightEff (double lightEff){
        this.lightEff = lightEff;
    }

    public void setPreferredUnit (String preferredUnit){
        this.preferredUnit = preferredUnit;
    }

}
