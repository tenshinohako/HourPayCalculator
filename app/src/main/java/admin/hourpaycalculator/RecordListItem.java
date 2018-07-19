package admin.hourpaycalculator;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * SelectSheetListViewに必要なデータを取得するクラス
 * MyListItem
 */
public class RecordListItem {

    protected int id;           // ID
    protected int year;
    protected int month;
    protected int dayOfMonth;
    protected int startHour;
    protected int startMinute;
    protected int endHour;
    protected int endMinute;
    protected int companyId;
    private DateFormat dateFormat; //= DateFormat.getDateInstance();
    private SimpleDateFormat timeFormat = new SimpleDateFormat("kk:mm");

    /**
     * MyListItem()
     *
     */
    public RecordListItem(
            int id,
            int year,
            int month,
            int dayOfMonth,
            int startHour,
            int startMinute,
            int endHour,
            int endMinute,
            int companyId) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.companyId = companyId;
    }

    public String getDateString(){
        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month);
        date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        dateFormat = DateFormat.getDateInstance();

        return dateFormat.format(date.getTime());
    }

    public String getStartTimeString(){
        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, startHour);
        startTime.set(Calendar.MINUTE, startMinute);
        timeFormat = new SimpleDateFormat("kk:mm");

        return timeFormat.format(startTime.getTime());
    }

    public String getEndTimeString(){
        Calendar endTime = Calendar.getInstance();
        endTime.set(Calendar.HOUR_OF_DAY, endHour);
        endTime.set(Calendar.MINUTE, endMinute);
        timeFormat = new SimpleDateFormat("kk:mm");

        return timeFormat.format(endTime.getTime());
    }


    /**
     * IDを取得
     * getId()
     *
     * @return id int ID
     */
    public int getId() {
        Log.d("取得したID：", String.valueOf(id));
        return id;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public int getEndHour() {
        return endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public int getCompanyId() {
        return companyId;
    }

}