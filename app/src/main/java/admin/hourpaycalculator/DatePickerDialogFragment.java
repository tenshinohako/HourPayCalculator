package admin.hourpaycalculator;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

@SuppressLint("ValidFragment")
public class DatePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    MainActivity ma;

    DatePickerDialogFragment(MainActivity mainActivity){
        this.ma = mainActivity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),this, year, month, dayOfMonth);

        return datePickerDialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //日付が選択されたときの処理
        ma.setDate(year, month + 1, dayOfMonth);
    }
}
