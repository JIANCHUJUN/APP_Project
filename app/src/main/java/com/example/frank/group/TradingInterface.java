package com.example.frank.group;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class TradingInterface extends AppCompatActivity {

    RadioButton buy, sale;
    Database database;
    String symbol = "";
    Company company;
    TextView price, number;
    EditText ticker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trading_interface);
        database = new Database(this);
        buy = findViewById(R.id.buy);
        sale = findViewById(R.id.sale);
        price = findViewById(R.id.price);
        number = findViewById(R.id.number);
        ticker = findViewById(R.id.ticker);
        buy.setChecked(true);
        company = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        database.open();
        symbol = getIntent().getExtras().getString("symbol");
        Cursor cursor = database.sqLiteDatabase.query(DBOpenHelper.TABLE_NAME, new String[]{"*"},
                "symbol='" + symbol + "'", null, null, null, null);
        company = database.get(cursor).get(0);
        price.setText("$ "+ String.valueOf(company.price));
        number.setText(String.valueOf(company.number));
    }
    @Override
    protected void onPause() {
        super.onPause();
        database.close();
    }

    public void back(View view){
        finish();
    }

    public void submit(View view) {
        int number;
        try{
            number = Integer.parseInt(ticker.getText().toString());
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
        if(buy.isChecked()){
            database.submitBuy(company.symbol,number);
        }
        else{
            database.submitSell(company.symbol,number);
        }
        Intent intent = new Intent(this, TradeMode.class);
        startActivityForResult(intent, MainActivity.TRAEMODE_RESIGTER_REQUEST_CODE);
    }
}
