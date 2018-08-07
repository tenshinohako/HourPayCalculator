package admin.hourpaycalculator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static admin.hourpaycalculator.MyDbContract.RecordTable;
import static admin.hourpaycalculator.MyDbContract.CompanyTable;

public class RecordListView extends AppCompatActivity {
    private RecordDbAdapter dbAdapter;
    private RecordBaseAdapter recordBaseAdapter;
    private CompanyDbAdapter cdbAdapter = new CompanyDbAdapter(this);
    private List<RecordListItem> items;
    private ListView rListView03;
    protected RecordListItem recordListItem;

    // 参照するDBのカラム：ID,品名,産地,個数,単価の全部なのでnullを指定
    private String[] columns = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_record_listview);

        // DBAdapterのコンストラクタ呼び出し
        dbAdapter = new RecordDbAdapter(this);

        // itemsのArrayList生成
        items = new ArrayList<>();

        // MyBaseAdapterのコンストラクタ呼び出し(myBaseAdapterのオブジェクト生成)
        recordBaseAdapter = new RecordBaseAdapter(this, items);

        // ListViewの結び付け
        rListView03 = (ListView) findViewById(R.id.listView03);

        loadMyList();   // DBを読み込む＆更新する処理

        // 行を長押しした時の処理
        rListView03.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                // アラートダイアログ表示
                AlertDialog.Builder builder = new AlertDialog.Builder(RecordListView.this);
                builder.setTitle("削除");
                builder.setMessage("削除しますか？");
                // OKの時の処理
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // IDを取得する
                        recordListItem = items.get(position);
                        int listId = recordListItem.getId();

                        dbAdapter.openDB();     // DBの読み込み(読み書きの方)
                        dbAdapter.selectDelete(String.valueOf(listId));     // DBから取得したIDが入っているデータを削除する
                        Log.d("Long click : ", String.valueOf(listId));
                        dbAdapter.closeDB();    // DBを閉じる
                        loadMyList();
                    }
                });

                builder.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                // ダイアログの表示
                AlertDialog dialog = builder.create();
                dialog.show();

                return false;
            }
        });
    }

    /**
     * DBを読み込む＆更新する処理
     * loadMyList()
     */
    private void loadMyList() {

        //ArrayAdapterに対してListViewのリスト(items)の更新
        items.clear();

        dbAdapter.openDB();     // DBの読み込み(読み書きの方)

        // DBのデータを取得
        Cursor c = dbAdapter.getDB(columns);

        if (c.moveToFirst()) {
            do {
                // MyListItemのコンストラクタ呼び出し(myListItemのオブジェクト生成)
                recordListItem = new RecordListItem(
                        c.getInt(c.getColumnIndexOrThrow(RecordTable._ID)),
                        c.getInt(c.getColumnIndexOrThrow(RecordTable.COLUMN_NAME_YEAR)),
                        c.getInt(c.getColumnIndexOrThrow(RecordTable.COLUMN_NAME_MONTH)),
                        c.getInt(c.getColumnIndexOrThrow(RecordTable.COLUMN_NAME_DAY_OF_MONTH)),
                        c.getInt(c.getColumnIndexOrThrow(RecordTable.COLUMN_NAME_START_HOUR)),
                        c.getInt(c.getColumnIndexOrThrow(RecordTable.COLUMN_NAME_START_MINUTE)),
                        c.getInt(c.getColumnIndexOrThrow(RecordTable.COLUMN_NAME_END_HOUR)),
                        c.getInt(c.getColumnIndexOrThrow(RecordTable.COLUMN_NAME_END_MINUTE)),
                        c.getInt(c.getColumnIndexOrThrow(RecordTable.COLUMN_NAME_COMPANY_ID)));

                /*
                Log.d("取得したCursor(ID):", String.valueOf(c.getInt(0)));
                Log.d("取得したCursor(品名):", c.getString(1));
                Log.d("取得したCursor(産地):", c.getString(2));
                Log.d("取得したCursor(個数):", c.getString(3));
                Log.d("取得したCursor(単価):", c.getString(4));
                */

                items.add(recordListItem);          // 取得した要素をitemsに追加

            } while (c.moveToNext());
        }
        c.close();
        dbAdapter.closeDB();                    // DBを閉じる
        rListView03.setAdapter(recordBaseAdapter);  // ListViewにmyBaseAdapterをセット
        recordBaseAdapter.notifyDataSetChanged();   // Viewの更新

    }

    /**
     * BaseAdapterを継承したクラス
     * MyBaseAdapter
     */
    public class RecordBaseAdapter extends BaseAdapter {

        private Context context;
        private List<RecordListItem> items;

        // 毎回findViewByIdをする事なく、高速化が出来るようするholderクラス
        private class ViewHolder {
            TextView companyName;
            TextView date;
            TextView startTime;
            TextView endTime;
        }

        // コンストラクタの生成
        public RecordBaseAdapter(Context context, List<RecordListItem> items) {
            this.context = context;
            this.items = items;
        }

        // Listの要素数を返す
        @Override
        public int getCount() {
            return items.size();
        }

        // indexやオブジェクトを返す
        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        // IDを他のindexに返す
        @Override
        public long getItemId(int position) {
            return position;
        }

        // 新しいデータが表示されるタイミングで呼び出される
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = convertView;
            ViewHolder holder;

            // データを取得
            recordListItem = items.get(position);


            if (view == null) {
                LayoutInflater inflater =
                        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.row_record_listview, parent, false);

                TextView companyName = (TextView) view.findViewById(R.id.id_record_company_name);      // 品名のTextView
                TextView date = (TextView) view.findViewById(R.id.id_record_date);        // 産地のTextView
                TextView startTime = (TextView) view.findViewById(R.id.id_record_start_time);        // 個数のTextView
                TextView endTime = (TextView) view.findViewById(R.id.id_record_end_time);          // 単価のTextView

                // holderにviewを持たせておく
                holder = new ViewHolder();
                holder.companyName = companyName;
                holder.date = date;
                holder.startTime = startTime;
                holder.endTime = endTime;
                view.setTag(holder);

            } else {
                // 初めて表示されるときにつけておいたtagを元にviewを取得する
                holder = (ViewHolder) view.getTag();
            }

            // 取得した各データを各TextViewにセット
            cdbAdapter.openDB();     // DBの読み込み(読み書きの方)
            Cursor c = cdbAdapter.nameById(recordListItem.getCompanyId());
            String name = c.getString(c.getColumnIndexOrThrow(CompanyTable.COLUMN_NAME_COMPANY_NAME));
            c.close();
            cdbAdapter.closeDB();    // DBを閉じる
            holder.companyName.setText(name);
            holder.date.setText(recordListItem.getDateString());
            holder.startTime.setText(recordListItem.getStartTimeString());
            holder.endTime.setText(recordListItem.getEndTimeString());

            return view;

        }
    }
}
