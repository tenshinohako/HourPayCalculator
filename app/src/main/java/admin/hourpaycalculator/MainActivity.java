package admin.hourpaycalculator;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static admin.hourpaycalculator.MyDbContract.MyTable;

public class MainActivity extends AppCompatActivity {
    RegistrationModel rm;

    private static final String TAG = "MainActivity";
    // DB を操作するためのインスタンス
    private MyDbHelper mDbHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new MyDbHelper(getApplicationContext());
        SQLiteDatabase reader = mDbHelper.getReadableDatabase();
        SQLiteDatabase writer = mDbHelper.getWritableDatabase();


        /*
        ContentValues values = new ContentValues();
        values.put(MyTable.COLUMN_NAME_INT_COL, 910);
        values.put(MyTable.COLUMN_NAME_STR_COL, "TBK");
        writer.insert(MyTable.TABLE_NAME, null, values);
        */



        ArrayList<String> companyOption = new ArrayList<String>();

        String[] projection = { // SELECT する列
                MyTable._ID,
                //MyTable.COLUMN_NAME_INT_COL,
                MyTable.COLUMN_NAME_STR_COL
        };
        //String selection = MyTable.COLUMN_NAME_INT_COL + " = ?"; // WHERE 句
        //String[] selectionArgs = { "123" };


        String sortOrder = MyTable.COLUMN_NAME_STR_COL + " DESC"; // ORDER 句
        Cursor cursor = reader.query(
                MyTable.TABLE_NAME, // The table to query
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

        String[] FROM2 = { // SELECT する列
                MyTable._ID,
                //MyTable.COLUMN_NAME_INT_COL,
                MyTable.COLUMN_NAME_STR_COL
        };
        MatrixCursor c = new MatrixCursor(FROM2);
        c.addRow(new String[] {"1", "TBK" });


        /*
        while(cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(MyTable._ID));
            String str = cursor.getString(cursor.getColumnIndexOrThrow(MyTable.COLUMN_NAME_STR_COL));
            //int pay = cursor.getInt(cursor.getColumnIndexOrThrow(MyTable.COLUMN_NAME_INT_COL));
            Log.d(TAG, "id: " + String.valueOf(id) + ", str: " + str);
            companyOption.add(str);
        }*/
        //cursor.close();



        //Adapterを作成します。
        String[] from = {MyTable.COLUMN_NAME_STR_COL};
        int[] to = {R.id.company};
        SimpleCursorAdapter adapter =
                new SimpleCursorAdapter(this,R.layout.spinner,cursor,from,to);
        //ドロップダウンリストのレイアウトを設定します。
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        //スピナーにadapterを設定します。
        Spinner spinner  = (Spinner)this.findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        //スピナーのアイテムが選択された時に呼び出されるコールバックリスナーを登録します
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                Spinner spinner = (Spinner) parent;
                Cursor cursor = (Cursor)spinner.getSelectedItem();
                String companyName = cursor.getString(cursor.getColumnIndex(MyTable.COLUMN_NAME_STR_COL));
                Toast.makeText(MainActivity.this,
                        companyName,
                        Toast.LENGTH_LONG).show();
            }
            public void onNothingSelected(AdapterView parent) {
            }
        });

        cursor.close();



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
        Calendar cal = Calendar.getInstance();       //カレンダーを取得

        int year = cal.get(Calendar.YEAR);         //年を取得
        int month = cal.get(Calendar.MONTH) + 1;       //月を取得
        int date = cal.get(Calendar.DATE);         //日を取得
        int hour = cal.get(Calendar.HOUR_OF_DAY);         //時を取得
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

    public void configCompany(View view){
        Intent intent = new Intent(this, CompanyConfigActivity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        if(mDbHelper != null) {
            mDbHelper.close(); // コネクションを閉じます。
        }
        super.onDestroy();
    }

}
