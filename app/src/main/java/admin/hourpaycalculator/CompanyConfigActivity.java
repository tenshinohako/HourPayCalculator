package admin.hourpaycalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CompanyConfigActivity extends AppCompatActivity {
    //private CompanyListView cListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_config);

        Intent intent = getIntent();
        //cListView = (CompanyListView)intent.getSerializableExtra("ListView");
    }

    public void registerCompany(View view){
        // Do something in response to button
        EditText editText = (EditText) findViewById(R.id.editText);
        String name = editText.getText().toString();
        EditText editText2 = (EditText) findViewById(R.id.editText2);
        int hourPay = Integer.parseInt(editText2.getText().toString());
        if(false){
            Toast.makeText(CompanyConfigActivity.this, "時刻を確認してください", Toast.LENGTH_SHORT).show();
        }else {
            // DBへの登録処理
            CompanyDbAdapter dbAdapter = new CompanyDbAdapter(this);
            dbAdapter.openDB();                                         // DBの読み書き
            dbAdapter.saveDB(name, hourPay);   // DBに登録
            dbAdapter.closeDB();                                        // DBを閉じる

            //cListView.loadMyList();
            super.finish();
        }
    }

}
