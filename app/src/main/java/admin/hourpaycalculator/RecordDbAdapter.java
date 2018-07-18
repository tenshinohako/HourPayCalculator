package admin.hourpaycalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Calendar;

import static admin.hourpaycalculator.MyDbContract.RecordTable;

public class RecordDbAdapter {
    private SQLiteDatabase db = null;           // SQLiteDatabase
    private RecordDbHelper rDbHelper = null;           // DBHelper
    protected Context context;                  // Context

    public RecordDbAdapter(Context context) {
        this.context = context;
        rDbHelper = new RecordDbHelper(this.context);
    }

    /**
     * DBの読み書き
     * openDB()
     *
     * @return this 自身のオブジェクト
     */
    public RecordDbAdapter openDB() {
        db = rDbHelper.getWritableDatabase();        // DBの読み書き
        return this;
    }

    public RecordDbHelper getrDbHelper() {
        return rDbHelper;
    }

    /**
     * DBの読み込み 今回は未使用
     * readDB()
     *
     * @return this 自身のオブジェクト
     */
    RecordDbAdapter readDB() {
        db = rDbHelper.getReadableDatabase();        // DBの読み込み
        return this;
    }

    /**
     * DBを閉じる
     * closeDB()
     */
    public void closeDB() {
        db.close();     // DBを閉じる
        db = null;
    }

    /**
     * DBのレコードへ登録
     * saveDB()
     *
     */
    public void saveDB(Calendar date, Calendar startTime, Calendar endTime, int companyId){
        db.beginTransaction();          // トランザクション開始

        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int dayOfMonth = date.get(Calendar.DAY_OF_MONTH);
        int startHour = startTime.get(Calendar.HOUR_OF_DAY);
        int startMinute = startTime.get(Calendar.MINUTE);
        int endHour = endTime.get(Calendar.HOUR_OF_DAY);
        int endMinute = endTime.get(Calendar.MINUTE);

        try {
            ContentValues values = new ContentValues();     // ContentValuesでデータを設定していく
            values.put(RecordTable.COLUMN_NAME_YEAR, year);
            values.put(RecordTable.COLUMN_NAME_MONTH, month);
            values.put(RecordTable.COLUMN_NAME_DAY_OF_MONTH, dayOfMonth);
            values.put(RecordTable.COLUMN_NAME_START_HOUR, startHour);
            values.put(RecordTable.COLUMN_NAME_START_MINUTE, startMinute);
            values.put(RecordTable.COLUMN_NAME_END_HOUR, endHour);
            values.put(RecordTable.COLUMN_NAME_END_MINUTE, endMinute);
            values.put(RecordTable.COLUMN_NAME_COMPANY_ID, companyId);

            // insertメソッド データ登録
            // 第1引数：DBのテーブル名
            // 第2引数：更新する条件式
            // 第3引数：ContentValues
            db.insert(RecordTable.TABLE_NAME, null, values);      // レコードへ登録

            db.setTransactionSuccessful();      // トランザクションへコミット
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();                // トランザクションの終了
        }
    }

    /**
     * DBのデータを取得
     * getDB()
     *
     * @param columns String[] 取得するカラム名 nullの場合は全カラムを取得
     * @return DBのデータ
     */
    public Cursor getDB(String[] columns) {

        // queryメソッド DBのデータを取得
        // 第1引数：DBのテーブル名
        // 第2引数：取得するカラム名
        // 第3引数：選択条件(WHERE句)
        // 第4引数：第3引数のWHERE句において?を使用した場合に使用
        // 第5引数：集計条件(GROUP BY句)
        // 第6引数：選択条件(HAVING句)
        // 第7引数：ソート条件(ODERBY句)
        return db.query(RecordTable.TABLE_NAME, columns, null, null, null, null, null);
    }

    /**
     * DBの検索したデータを取得
     * searchDB()
     *
     * @param columns String[] 取得するカラム名 nullの場合は全カラムを取得
     * @param column  String 選択条件に使うカラム名
     * @param name    String[]
     * @return DBの検索したデータ
     */
    public Cursor searchDB(String[] columns, String column, String[] name) {
        return db.query(RecordTable.TABLE_NAME, columns, column + " like ?", name, null, null, null);
    }

    /**
     * DBのレコードを全削除
     * allDelete()
     */
    public void allDelete() {

        db.beginTransaction();                      // トランザクション開始
        try {
            // deleteメソッド DBのレコードを削除
            // 第1引数：テーブル名
            // 第2引数：削除する条件式 nullの場合は全レコードを削除
            // 第3引数：第2引数で?を使用した場合に使用
            db.delete(RecordTable.TABLE_NAME, null, null);        // DBのレコードを全削除
            db.setTransactionSuccessful();          // トランザクションへコミット
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();                    // トランザクションの終了
        }
    }

    /**
     * DBのレコードの単一削除
     * selectDelete()
     *
     * @param position String
     */
    public void selectDelete(String position) {

        db.beginTransaction();                      // トランザクション開始
        try {
            db.delete(RecordTable.TABLE_NAME, RecordTable._ID + "=?", new String[]{position});
            db.setTransactionSuccessful();          // トランザクションへコミット
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();                    // トランザクションの終了
        }
    }

}
