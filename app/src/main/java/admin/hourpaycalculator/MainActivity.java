package admin.hourpaycalculator;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    RegistrationModel rm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //時刻表示するコードを追加
        Calendar cal = Calendar.getInstance();       //カレンダーを取得

        int year = cal.get(Calendar.YEAR);         //年を取得
        int month = cal.get(Calendar.MONTH) + 1;       //月を取得
        int date = cal.get(Calendar.DATE);         //日を取得
        int hour = cal.get(Calendar.HOUR);         //時を取得
        int minute = cal.get(Calendar.MINUTE);    //分を取得
        //int iSecond = cal.get(Calendar.SECOND);    //分を取得
        String strYMD = year + "年" + month+ "月" + date + "日";
        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText(strYMD);

        String strStartHM = "00:00";
        TextView textView3 = (TextView)findViewById(R.id.textView3);
        textView3.setText(strStartHM);

        String strEndHM = String.format("%02d", hour) + ":" + String.format("%02d", minute);
        TextView textView4 = (TextView)findViewById(R.id.textView4);
        textView4.setText(strEndHM);

        rm = new RegistrationModel(textView, textView3, textView4, year, month, date);
        //ここまで
    }

    public void selectDate(View view) {
        // Do something in response to button
        DatePickerDialogFragment datePicker = new DatePickerDialogFragment(rm);
        datePicker.show(getSupportFragmentManager(), "datePicker");
    }

    public void setStartTime(View view) {
        // Do something in response to button
        TimePickerDialogFragment timePicker = new TimePickerDialogFragment(rm, true);
        timePicker.show(getSupportFragmentManager(), "timePicker");
    }

    public void setEndTime(View view) {
        // Do something in response to button
        TimePickerDialogFragment timePicker = new TimePickerDialogFragment(rm, false);
        timePicker.show(getSupportFragmentManager(), "timePicker");
    }

}
