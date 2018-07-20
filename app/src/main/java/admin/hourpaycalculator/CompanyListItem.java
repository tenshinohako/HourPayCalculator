package admin.hourpaycalculator;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * SelectSheetListViewに必要なデータを取得するクラス
 * MyListItem
 */
public class CompanyListItem {

    protected int id;           // ID
    protected String companyName;
    protected int hourPay;
    private DateFormat dateFormat; //= DateFormat.getDateInstance();
    private SimpleDateFormat timeFormat = new SimpleDateFormat("kk:mm");

    /**
     * MyListItem()
     *
     */
    public CompanyListItem(int id, String companyName, int hourPay) {
        this.id = id;
        this.companyName = companyName;
        this.hourPay = hourPay;
    }

    public String getCompanyNameg(){
        return companyName;
    }

    public int getHourPay(){
        return hourPay;
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

}
