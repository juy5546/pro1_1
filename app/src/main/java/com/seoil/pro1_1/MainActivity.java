package com.seoil.pro1_1;


import android.app.TabActivity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

    myDBHelper myHelper;
    EditText edtcustNO,edtcustID,edtcustName,edtcustMobile,edtcustAddr,edtcustAge,
            edtcustfaCat,edtfaCompany,edtcustbadFood,edtcustNoResult, edtcustNameResult,edtcustMobileResult;
    Button custInit, custInsert, custSelect;
    SQLiteDatabase sqlDB1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("주문음식 관리 DB");

        edtcustNO = (EditText) findViewById(R.id.edtcustNo);
        edtcustID = (EditText) findViewById(R.id.edtcustID);
        edtcustName = (EditText) findViewById(R.id.edtcustName);
        edtcustMobile = (EditText) findViewById(R.id.edtcustMobile);
        edtcustAddr = (EditText) findViewById(R.id.edtcustAddr);
        edtcustAge = (EditText) findViewById(R.id.edtcustAge);
        edtcustfaCat = (EditText) findViewById(R.id.edtcustfaCat);
        edtfaCompany = (EditText) findViewById(R.id.edtfaCompany);
        edtcustbadFood = (EditText) findViewById(R.id.edtcustbadFood);
        edtcustNoResult = (EditText) findViewById(R.id.edtcustNoResult);
        edtcustNameResult = (EditText) findViewById(R.id.edtcustNameResult);
        edtcustMobileResult = (EditText) findViewById(R.id.edtcustMobileResult);

        custInit = (Button) findViewById(R.id.custInit);
        custInsert = (Button) findViewById(R.id.custInsert);
        custSelect = (Button) findViewById(R.id.custSelect);

        myHelper = new myDBHelper(this);
        custInit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sqlDB1 = myHelper.getWritableDatabase();
                myHelper.onUpgrade(sqlDB1, 1, 2); // 인수는 아무거나 입력하면 됨.
                sqlDB1.close();
            }
        });

        custInsert.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sqlDB1 = myHelper.getWritableDatabase();
                sqlDB1.execSQL("INSERT INTO customer01 VALUES ( '"
                        + edtcustNO.getText().toString() + "' , "
                        + edtcustName.getText().toString() + ");");
                sqlDB1.close();
                Toast.makeText(getApplicationContext(), "입력됨",
                        Toast.LENGTH_SHORT).show();
            }
        });

        custSelect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sqlDB1 = myHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB1.rawQuery("SELECT * FROM customer01;", null);

                String strNumbers = "고객번호" + "\r\n" + "--------" + "\r\n";
                String strNames = "고객이름" + "\r\n" + "--------" + "\r\n";
             //   String strMobile = "전화번호" + "\r\n" + "--------" + "\r\n";

                while (cursor.moveToNext()) {
                    strNumbers += cursor.getString(0) + "\r\n";
                    strNames += cursor.getString(1) + "\r\n";
                 //   strMobile += cursor.getString(2) + "\r\n";
                }

                edtcustNoResult.setText(strNumbers);
                edtcustNameResult.setText(strNames);

                cursor.close();
                sqlDB1.close();
            }
        });


        TabHost tabHost = getTabHost();

        TabSpec tabSpecCustomer = tabHost.newTabSpec("CUSTOMER").setIndicator("고객정보");
        tabSpecCustomer.setContent(R.id.tabCustomer);
        tabHost.addTab(tabSpecCustomer);

        TabSpec tabSpecProdFood = tabHost.newTabSpec("PRODFOOD")
                .setIndicator("음식정보");
        tabSpecProdFood.setContent(R.id.tabProdFood);
        tabHost.addTab(tabSpecProdFood);

        TabSpec tabSpecOrder = tabHost.newTabSpec("ORDER").setIndicator("주문정보");
        tabSpecOrder.setContent(R.id.tabOrder);
        tabHost.addTab(tabSpecOrder);

        tabHost.setCurrentTab(0);
    }

    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context) {
            super(context, "db_juy", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE  customer01 ( edtcustNO INTEGER PRIMARY KEY, edtcustName CHAR(20));");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS customer01");
            onCreate(db);
        }
    }
}
