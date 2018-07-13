package admin.hourpaycalculator;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerDialogFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    RegistrationModel rm;
    boolean isStart;

    TimePickerDialogFragment(RegistrationModel rm, boolean isStart){
        this.rm = rm;
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
            rm.setStartHour(hourOfDay);
            rm.setStartMinute(minute);
            rm.setStartTime();
        }else{
            rm.setEndHour(hourOfDay);
            rm.setEndMinute(minute);
            rm.setEndTime();
        }
    }

}