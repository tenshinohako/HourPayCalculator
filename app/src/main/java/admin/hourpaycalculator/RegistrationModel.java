package admin.hourpaycalculator;

import android.widget.TextView;

public class RegistrationModel {

    private int year;
    private int month;
    private int date;
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;
    private TextView textView;
    private TextView textView3;
    private TextView textView4;

    RegistrationModel(TextView textView, TextView textView3,  TextView textView4, int year, int month, int date){

        this.year = year;
        this.month = month;
        this.date = date;
        this.textView = textView;
        this.textView3 = textView3;
        this.textView4 = textView4;
    }

    public void setStartTime(){
        String strHM = String.format("%02d", startHour) + ":" + String.format("%02d", startMinute);
        textView3.setText(strHM);
    }

    public void setEndTime(){
        String strHM = String.format("%02d", endHour) + ":" + String.format("%02d", endMinute);
        textView4.setText(strHM);
    }

    public void setStartHour(int hour){
        this.startHour = hour;
    }

    public void setStartMinute(int minute){
        this.startMinute = minute;
    }

    public void setEndHour(int hour){
        this.endHour = hour;
    }

    public void setEndMinute(int minute){
        this.endMinute = minute;
    }

    /*
    public int getStartHour(){
        return startHour;
    }

    public int getStartMinute(){
        return startMinute;
    }

    public int getEndHour(){
        return endHour;
    }

    public int getEndMinute(){
        return endMinute;
    }
    */

    public void setYMD(){
        String strYMD = year + "年" + month + "月" + date + "日";
        textView.setText(strYMD);
    }

    public void setYear(int year){
        this.year = year;
    }

    public void setMonth(int month){
        this.month = month;
    }

    public void setDate(int date){
        this.date = date;
    }

    /*
    public int getYear(){
        return year;
    }

    public int getMonth(){
        return month;
    }

    public int getDate(){
        return date;
    }
    */
}
