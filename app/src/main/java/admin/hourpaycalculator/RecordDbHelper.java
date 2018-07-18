package admin.hourpaycalculator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static admin.hourpaycalculator.MyDbContract.RecordTable;

public class RecordDbHelper extends SQLiteOpenHelper {

    // スキーマに変更があれば VERSION をインクリメントします。
    public static final int DATABASE_VERSION = 1;

    // SQLite ファイル名を指定します。
    public static final String DATABASE_NAME = "RecordDb.db";

    // SQLite ファイルが存在しない場合や VERSION が変更された際に実行する SQL を定義します。
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + MyDbContract.CompanyTable.TABLE_NAME + " (" +
                    RecordTable._ID + " INTEGER PRIMARY KEY," +
                    RecordTable.COLUMN_NAME_YEAR + " INTEGER," +
                    RecordTable.COLUMN_NAME_MONTH + " INTEGER," +
                    RecordTable.COLUMN_NAME_DAY_OF_MONTH + " INTEGER," +
                    RecordTable.COLUMN_NAME_START_HOUR + " INTEGER," +
                    RecordTable.COLUMN_NAME_START_MINUTE + " INTEGER," +
                    RecordTable.COLUMN_NAME_END_HOUR + " INTEGER," +
                    RecordTable.COLUMN_NAME_END_MINUTE + " INTEGER," +
                    RecordTable.COLUMN_NAME_COMPANY_ID + " INTEGER)";

    private static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + MyDbContract.CompanyTable.TABLE_NAME;

    public RecordDbHelper(Context context) {
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
