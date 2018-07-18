package admin.hourpaycalculator;

import android.provider.BaseColumns;

public final class MyDbContract {
    // コンストラクタを利用できないようにします。
    private MyDbContract() {}

    // テーブル名、列名の定数定義です。
    public static class CompanyTable implements BaseColumns {
        public static final String TABLE_NAME = "company_table";
        public static final String COLUMN_NAME_HOUR_PAY = "hour_pay";
        public static final String COLUMN_NAME_COMPANY_NAME = "name";
    }

    public static class WorkRecordTable implements BaseColumns{
        public static final String TABLE_NAME = "record_table";
        public static final String COLUMN_NAME_YEAR = "year";
        public static final String COLUMN_NAME_MONTH = "month";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_START_HOUR = "start_hour";
        public static final String COLUMN_NAME_START_MINUTE = "start_minute";
        public static final String COLUMN_NAME_END_HOUR = "end_hour";
        public static final String COLUMN_NAME_END_MINUTE = "end_minute";
        public static final String COLUMN_NAME_COMPANY_ID = "company_id";
    }
}
