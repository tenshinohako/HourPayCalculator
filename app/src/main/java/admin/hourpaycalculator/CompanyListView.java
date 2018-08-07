package admin.hourpaycalculator;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static admin.hourpaycalculator.MyDbContract.CompanyTable;

public class CompanyListView extends AppCompatActivity {
    private CompanyDbAdapter dbAdapter;
    private CompanyBaseAdapter companyBaseAdapter;
    private List<CompanyListItem> items;
    private ListView cListView04;
    protected CompanyListItem companyListItem;

    // 参照するDBのカラム：ID,品名,産地,個数,単価の全部なのでnullを指定
    private String[] columns = null;

    CompanyListView(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_company_listview);

        // DBAdapterのコンストラクタ呼び出し
        dbAdapter = new CompanyDbAdapter(this);

        // itemsのArrayList生成
        items = new ArrayList<>();

        // MyBaseAdapterのコンストラクタ呼び出し(myBaseAdapterのオブジェクト生成)
        companyBaseAdapter = new CompanyBaseAdapter(this, items);

        // ListViewの結び付け
        cListView04 = (ListView) findViewById(R.id.listView04);

        loadMyList();   // DBを読み込む＆更新する処理

        // 行を長押しした時の処理
        cListView04.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                // アラートダイアログ表示
                AlertDialog.Builder builder = new AlertDialog.Builder(CompanyListView.this);
                builder.setTitle("削除");
                builder.setMessage("削除しますか？");
                // OKの時の処理
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // IDを取得する
                        companyListItem = items.get(position);
                        int listId = companyListItem.getId();

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
    public void loadMyList() {

        //ArrayAdapterに対してListViewのリスト(items)の更新
        items.clear();

        dbAdapter.openDB();     // DBの読み込み(読み書きの方)

        // DBのデータを取得
        Cursor c = dbAdapter.getDB(columns);

        if (c.moveToFirst()) {
            do {
                // MyListItemのコンストラクタ呼び出し(myListItemのオブジェクト生成)
                companyListItem = new CompanyListItem(
                        c.getInt(c.getColumnIndexOrThrow(CompanyTable._ID)),
                        c.getString(c.getColumnIndexOrThrow(CompanyTable.COLUMN_NAME_COMPANY_NAME)),
                        c.getInt(c.getColumnIndexOrThrow(CompanyTable.COLUMN_NAME_HOUR_PAY)));

                /*
                Log.d("取得したCursor(ID):", String.valueOf(c.getInt(0)));
                Log.d("取得したCursor(品名):", c.getString(1));
                Log.d("取得したCursor(産地):", c.getString(2));
                Log.d("取得したCursor(個数):", c.getString(3));
                Log.d("取得したCursor(単価):", c.getString(4));
                */

                items.add(companyListItem);          // 取得した要素をitemsに追加

            } while (c.moveToNext());
        }
        c.close();
        dbAdapter.closeDB();                    // DBを閉じる
        cListView04.setAdapter(companyBaseAdapter);  // ListViewにmyBaseAdapterをセット
        companyBaseAdapter.notifyDataSetChanged();   // Viewの更新

    }

    public void configureCompany(View view){
        // Do something in response to button

        Intent intent = new Intent(this, CompanyConfigActivity.class);

        //String message = editText.getText().toString();
        //intent.putExtra("ListView", this);

        startActivityForResult(intent, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        loadMyList();
    }

    /**
     * BaseAdapterを継承したクラス
     * MyBaseAdapter
     */
    public class CompanyBaseAdapter extends BaseAdapter {

        private Context context;
        private List<CompanyListItem> items;

        // 毎回findViewByIdをする事なく、高速化が出来るようするholderクラス
        private class ViewHolder {
            TextView companyName;
            TextView hourPay;
        }

        // コンストラクタの生成
        public CompanyBaseAdapter(Context context, List<CompanyListItem> items) {
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
            companyListItem = items.get(position);


            if (view == null) {
                LayoutInflater inflater =
                        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.row_company_listview, parent, false);

                TextView companyName = (TextView) view.findViewById(R.id.id_company_name);      // 品名のTextView
                TextView hourPay = (TextView) view.findViewById(R.id.id_company_hourpay);      // 品名のTextView

                // holderにviewを持たせておく
                holder = new ViewHolder();
                holder.companyName = companyName;
                holder.hourPay = hourPay;
                view.setTag(holder);

            } else {
                // 初めて表示されるときにつけておいたtagを元にviewを取得する
                holder = (ViewHolder) view.getTag();
            }

            // 取得した各データを各TextViewにセット
            holder.companyName.setText(companyListItem.getCompanyNameg());
            holder.hourPay.setText(String.valueOf(companyListItem.getHourPay()));

            return view;

        }
    }
}