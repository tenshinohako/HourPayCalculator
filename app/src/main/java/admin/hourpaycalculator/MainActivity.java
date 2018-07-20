package admin.hourpaycalculator;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static admin.hourpaycalculator.MyDbContract.CompanyTable;

public class MainActivity extends AppCompatActivity {

    private TextView dateTV;
    private TextView startTimeTV;
    private TextView endTimeTV;
    private Button registerBt;
    private Button showBt;
    private Button companyConfigBt;
    private DateFormat dateFormat;
    private SimpleDateFormat timeFormat; //= new SimpleDateFormat("kk:mm");
    private Calendar date = Calendar.getInstance();
    private Calendar startTime = Calendar.getInstance();
    private Calendar endTime = Calendar.getInstance();
    private int companyId;
    private Intent intentShow;
    private Intent intentCompanyConfig;


    //RegistrationModel rm;

    private static final String TAG = "MainActivity";
    // DB を操作するためのインスタンス
    private CompanyDbHelper cDbHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateTV = (TextView)findViewById(R.id.textView);
        startTimeTV = (TextView)findViewById(R.id.textView3);
        endTimeTV = (TextView)findViewById(R.id.textView4);

        dateFormat = DateFormat.getDateInstance();
        Calendar now = Calendar.getInstance();
        dateTV.setText(dateFormat.format(now.getTime()));


        timeFormat = new SimpleDateFormat("kk:mm");
        startTime.set(Calendar.HOUR_OF_DAY, 0);
        startTime.set(Calendar.MINUTE, 0);
        startTimeTV.setText(timeFormat.format(startTime.getTime()));

        endTime = now;
        endTimeTV.setText(timeFormat.format(endTime.getTime()));

        registerBt = (Button) findViewById(R.id.button2);
        showBt = (Button) findViewById(R.id.button3);
        companyConfigBt = (Button) findViewById(R.id.button);

        intentShow = new Intent(MainActivity.this, RecordListView.class);
        intentCompanyConfig = new Intent(MainActivity.this, CompanyListView.class);

        // 登録ボタン押下時処理
        registerBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // キーボードを非表示
                InputMethodManager inputMethodManager =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                // DBに登録
                saveRecord();

            }
        });

        // 表示ボタン押下時処理
        showBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intentShow != null) {
                    startActivity(intentShow);      // 各画面へ遷移
                } else {
                    Toast.makeText(MainActivity.this, "ラジオボタンが選択されていません。", Toast.LENGTH_SHORT).show();
                }

            }
        });


        companyConfigBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intentCompanyConfig != null) {
                    startActivity(intentCompanyConfig);      // 各画面へ遷移
                } else {
                    Toast.makeText(MainActivity.this, "ラジオボタンが選択されていません。", Toast.LENGTH_SHORT).show();
                }

            }
        });





        cDbHelper = new CompanyDbHelper(getApplicationContext());
        SQLiteDatabase reader = cDbHelper.getReadableDatabase();
        SQLiteDatabase writer = cDbHelper.getWritableDatabase();


        /*
        ContentValues values = new ContentValues();
        values.put(MyTable.COLUMN_NAME_INT_COL, 910);
        values.put(MyTable.COLUMN_NAME_STR_COL, "TBK");
        writer.insert(MyTable.TABLE_NAME, null, values);
        */


        String[] projection = { // SELECT する列
                CompanyTable._ID,
                //MyTable.COLUMN_NAME_INT_COL,
                CompanyTable.COLUMN_NAME_COMPANY_NAME
        };
        //String selection = MyTable.COLUMN_NAME_INT_COL + " = ?"; // WHERE 句
        //String[] selectionArgs = { "123" };
        //String sortOrder = MyTable.COLUMN_NAME_STR_COL + " DESC"; // ORDER 句

        Cursor cursor = reader.query(
                CompanyTable.TABLE_NAME, // The table to query
                projection,         // The columns to return
                //selection,          // The columns for the WHERE clause
                null,
                //selectionArgs,      // The values for the WHERE clause
                null,
                null,               // don't group the rows
                null,               // don't filter by row groups
                ///sortOrder           // The sort order
                null
        );


        /*
        String[] FROM2 = { // SELECT する列
                MyTable._ID,
                //MyTable.COLUMN_NAME_INT_COL,
                MyTable.COLUMN_NAME_STR_COL
        };
        MatrixCursor c = new MatrixCursor(FROM2);
        c.addRow(new String[] {"1", "TBK" });
        */

        /*
        while(cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(MyTable._ID));
            String str = cursor.getString(cursor.getColumnIndexOrThrow(MyTable.COLUMN_NAME_STR_COL));
            //int pay = cursor.getInt(cursor.getColumnIndexOrThrow(MyTable.COLUMN_NAME_INT_COL));
            Log.d(TAG, "id: " + String.valueOf(id) + ", str: " + str);
        }
        */

        //Adapterを作成します。

        String[] from = {CompanyTable.COLUMN_NAME_COMPANY_NAME};
        int[] to = {R.id.company};
        SimpleCursorAdapter adapter =
                new SimpleCursorAdapter(this,R.layout.spinner,cursor,from,to);
        //ドロップダウンリストのレイアウトを設定します。
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        //スピナーにadapterを設定します。
        Spinner spinner  = (Spinner)this.findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        //spinner.setFocusable(false);
        //スピナーのアイテムが選択された時に呼び出されるコールバックリスナーを登録します
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent, View view, int position, long id) {

                Spinner sp = (Spinner) findViewById(R.id.spinner);
                if(!sp.isFocusable()){
                    sp.setFocusable(true);
                    return;
                }

                Spinner spinner = (Spinner) parent;
                Cursor cursor = (Cursor)spinner.getSelectedItem();
                String companyName = cursor.getString(cursor.getColumnIndex(CompanyTable.COLUMN_NAME_COMPANY_NAME));
                companyId = cursor.getInt(cursor.getColumnIndex(CompanyTable._ID));

                Toast.makeText(MainActivity.this,
                        companyName,
                        Toast.LENGTH_LONG).show();

                TextView spinnerText = (TextView)findViewById(R.id.text);
                spinnerText.setText(companyName);
                //rm.setCompanyId(companyId);
            }
            public void onNothingSelected(AdapterView parent) {
            }
        });


        /*
        // INSERT
        ContentValues values = new ContentValues();
        values.put(MyTable.COLUMN_NAME_INT_COL, 123);
        values.put(MyTable.COLUMN_NAME_STR_COL, "aaa");
        writer.insert(MyTable.TABLE_NAME, null, values);

        // SELECT
        String[] projection = { // SELECT する列
                MyTable._ID,
                MyTable.COLUMN_NAME_INT_COL,
                MyTable.COLUMN_NAME_STR_COL
        };
        String selection = MyTable.COLUMN_NAME_INT_COL + " = ?"; // WHERE 句
        String[] selectionArgs = { "123" };
        String sortOrder = MyTable.COLUMN_NAME_STR_COL + " DESC"; // ORDER 句
        Cursor cursor = reader.query(
                MyTable.TABLE_NAME, // The table to query
                projection,         // The columns to return
                selection,          // The columns for the WHERE clause
                selectionArgs,      // The values for the WHERE clause
                null,               // don't group the rows
                null,               // don't filter by row groups
                sortOrder           // The sort order
        );
        while(cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(MyTable._ID));
            String str = cursor.getString(cursor.getColumnIndexOrThrow(MyTable.COLUMN_NAME_STR_COL));
            Log.d(TAG, "id: " + String.valueOf(id) + ", str: " + str);
        }
        cursor.close();

        // DELETE
        String deleteSelection = MyTable._ID + " > ?"; // WHERE 句
        String[] deleteSelectionArgs = { "5" };
        writer.delete(MyTable.TABLE_NAME, deleteSelection, deleteSelectionArgs);

        // UPDATE
        ContentValues updateValues = new ContentValues();
        updateValues.put(MyTable.COLUMN_NAME_STR_COL, "bbb");
        String updateSelection = MyTable.COLUMN_NAME_STR_COL + " = ?";
        String[] updateSelectionArgs = { "aaa" };
        writer.update(
                MyTable.TABLE_NAME,
                updateValues,
                updateSelection,
                updateSelectionArgs);
        */

        //時刻表示するコードを追加
        /*
        Calendar cal = Calendar.getInstance();       //カレンダーを取得

        int year = cal.get(Calendar.YEAR);         //年を取得
        int month = cal.get(Calendar.MONTH) + 1;       //月を取得
        int date = cal.get(Calendar.DATE);         //日を取得
        int hour = cal.get(Calendar.HOUR_OF_DAY);         //時を取得
        int minute = cal.get(Calendar.MINUTE);    //分を取得
        //int iSecond = cal.get(Calendar.SECOND);    //分を取得
        //String strYMD = year + "年" + month+ "月" + date + "日";
        String strYMD = cal.toString();
        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText(strYMD);

        String strStartHM = "00:00";
        TextView textView3 = (TextView)findViewById(R.id.textView3);
        textView3.setText(strStartHM);

        String strEndHM = String.format("%02d", hour) + ":" + String.format("%02d", minute);
        TextView textView4 = (TextView)findViewById(R.id.textView4);
        textView4.setText(strEndHM);

        rm = new RegistrationModel(textView, textView3, textView4, year, month, date);
        */
        //ここまで
    }

    public void saveRecord(){
        if(startTime.compareTo(endTime) > 0){
            Toast.makeText(MainActivity.this, "時刻を確認してください", Toast.LENGTH_SHORT).show();
        }else {
            // DBへの登録処理
            RecordDbAdapter dbAdapter = new RecordDbAdapter(this);
            dbAdapter.openDB();                                         // DBの読み書き
            dbAdapter.saveDB(date, startTime, endTime, companyId);   // DBに登録
            dbAdapter.closeDB();                                        // DBを閉じる
        }
    }

    public void setDate(int year, int month, int dayOfMonth){
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month);
        date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    }

    public void setStartTime(int hour, int minute){
        startTime.set(Calendar.HOUR_OF_DAY, hour);
        startTime.set(Calendar.MINUTE, minute);

        startTimeTV.setText(timeFormat.format(startTime.getTime()));
    }

    public void setEndTime(int hour, int minute){
        endTime.set(Calendar.HOUR_OF_DAY, hour);
        endTime.set(Calendar.MINUTE, minute);

        endTimeTV.setText(timeFormat.format(endTime.getTime()));
    }

    public void selectDate(View view) {
        // Do something in response to button
        DatePickerDialogFragment datePicker = new DatePickerDialogFragment(this);
        datePicker.show(getSupportFragmentManager(), "datePicker");
    }

    public void setStartTime(View view) {
        // Do something in response to button
        TimePickerDialogFragment timePicker = new TimePickerDialogFragment(this, true);
        timePicker.show(getSupportFragmentManager(), "timePicker");
    }

    public void setEndTime(View view) {
        // Do something in response to button
        TimePickerDialogFragment timePicker = new TimePickerDialogFragment(this, false);
        timePicker.show(getSupportFragmentManager(), "timePicker");
    }

    public void configCompany(View view){
        Intent intent = new Intent(this, CompanyConfigActivity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void registerRecord(View view){

    }

    public void showRecord(View view){

    }

    @Override
    protected void onDestroy() {
        if(cDbHelper != null) {
            cDbHelper.close(); // コネクションを閉じます。
        }
        super.onDestroy();
    }
}
