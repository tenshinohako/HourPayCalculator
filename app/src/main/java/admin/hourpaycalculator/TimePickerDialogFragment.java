package admin.hourpaycalculator;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import java.util.Calendar;

@SuppressLint("ValidFragment")
public class TimePickerDialogFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    MainActivity ma;
    boolean isStart;

    TimePickerDialogFragment(MainActivity mainActivity, boolean isStart){
        this.ma = mainActivity;
        this.isStart = isStart;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), this, hour, minute, true);

        return timePickerDialog;
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        //時刻が選択されたときの処理
        if(isStart){
            ma.setStartTime(hourOfDay, minute);
        }else{
            ma.setEndTime(hourOfDay, minute);
        }
    }

}