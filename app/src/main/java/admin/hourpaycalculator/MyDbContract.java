package admin.hourpaycalculator;

import android.provider.BaseColumns;

public final class MyDbContract {
    // コンストラクタを利用できないようにします。
    private MyDbContract() {}

    // テーブル名、列名の定数定義です。
    public static class MyTable implements BaseColumns {
        public static final String TABLE_NAME = "my_table";
        public static final String COLUMN_NAME_INT_COL = "int_col";
        public static final String COLUMN_NAME_STR_COL = "str_col";
    }
}
