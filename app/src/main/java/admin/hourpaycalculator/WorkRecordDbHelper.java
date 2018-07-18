package admin.hourpaycalculator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static admin.hourpaycalculator.MyDbContract.WorkRecordTable;

public class WorkRecordDbHelper extends SQLiteOpenHelper {
    // スキーマに変更があれば VERSION をインクリメントします。
    public static final int DATABASE_VERSION = 1;

    // SQLite ファイル名を指定します。
    public static final String DATABASE_NAME = "WorkRecordDb.db";

    // SQLite ファイルが存在しない場合や VERSION が変更された際に実行する SQL を定義します。
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + WorkRecordTable.TABLE_NAME + " (" +
                    WorkRecordTable._ID + " INTEGER PRIMARY KEY," +
                    WorkRecordTable.COLUMN_NAME_YEAR + " INTEGER," +
                    WorkRecordTable.COLUMN_NAME_MONTH + " INTEGER," +
                    WorkRecordTable.COLUMN_NAME_DATE + " INTEGER," +
                    WorkRecordTable.COLUMN_NAME_START_HOUR + " INTEGER," +
                    WorkRecordTable.COLUMN_NAME_START_MINUTE + " INTEGER," +
                    WorkRecordTable.COLUMN_NAME_END_HOUR + " INTEGER," +
                    WorkRecordTable.COLUMN_NAME_END_MINUTE + " INTEGER," +
                    WorkRecordTable.COLUMN_NAME_COMPANY_ID + " INTEGER)";

    private static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + WorkRecordTable.TABLE_NAME;

    public WorkRecordDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { // SQLite ファイルが存在しない場合
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // VERSION が上がった場合に実行されます。本サンプルでは単純に DROP して CREATE し直します。
        db.execSQL(SQL_DROP_TABLE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // VERSION が下がった場合に実行されます。本サンプルでは単純に DROP して CREATE し直します。
        onUpgrade(db, oldVersion, newVersion);
    }
}
