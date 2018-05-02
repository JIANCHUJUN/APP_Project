package com.example.frank.group;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class TradingInterface extends AppCompatActivity {

    RadioButton buy, sale;
    Database database;
    String symbol = "";
    Company company;
    TextView price, number, companyName, totalprice;
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
        companyName = findViewById(R.id.companyName);
        totalprice = findViewById(R.id.totalPrice);
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
        companyName.setText(company.companyName);
    }
    @Override
    protected void onPause() {
        super.onPause();
        database.close();
    }

    public void back(View view){
        Intent intent = new Intent(this, TradeMode.class);
        startActivityForResult(intent, MainActivity.TRAEMODE_RESIGTER_REQUEST_CODE);
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
            if(!database.submitBuy(company.symbol,number)){
                Toast.makeText(this, "Money not Enough!", Toast.LENGTH_LONG).show();
                return;
            }
        }
        else{
            if(!database.submitSell(company.symbol,number)){
                Toast.makeText(this, "There are not enough shares!", Toast.LENGTH_LONG).show();
                return;
            }
        }
        Intent intent = new Intent(this, TradeMode.class);
        startActivityForResult(intent, MainActivity.TRAEMODE_RESIGTER_REQUEST_CODE);
    }

    public void calculate (View view){
        int number;
        try{
            number = Integer.parseInt(ticker.getText().toString());
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
        totalprice.setText(company.price*number -10 + "");
    }

    public void history(View view){
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivityForResult(intent, MainActivity.TRAEMODE_RESIGTER_REQUEST_CODE);
    }
}
