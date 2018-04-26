package com.example.frank.group;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements StockFragment.OnFragmentInteractionListener{

    StockAPI stockAPI;
    Database databaseManager;
    EditText ticker;
    LinearLayout container;
    static final int RESIGTER_REQUEST_CODE = 1;
    public static ArrayList<Company> stockList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stockAPI = new StockAPI(this);
        //stockAPI.getInfo("aapl");
        databaseManager = new Database(this);
        ticker = findViewById(R.id.ticker);
        container = findViewById(R.id.container);
        stockList = new ArrayList<>();
    }

    @Override
    protected void onPause() {
        super.onPause();
        databaseManager.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        databaseManager.open();
        updateUI();
    }

    public void add(View view){
        for (Company company: stockList){
            if (company.symbol.equals(ticker.getText().toString())){
                return;
            }
        }
        stockAPI.getInfo(ticker.getText().toString());
    }

    public void updateUI(){
        container.removeAllViews();
        Cursor cursor = databaseManager.sqLiteDatabase.query(DBOpenHelper.TABLE_NAME, new String[]{"*"},
                null, null, null, null, null);
        stockList = databaseManager.get(cursor);
        for (Company company: stockList){
            StockFragment frag = new StockFragment();
            Bundle args = new Bundle();
            args.putString("name", company.companyName);
            args.putString("symbol",company.symbol);
            frag.setArguments(args);
            getSupportFragmentManager().beginTransaction().add(R.id.container, frag).commit();
        }
    }

    public void saveInfo(Company company){
        //databaseManager.deleteAll();
        databaseManager.insertCompanyInfo(company);
    }

    public void detail(String symbol){
        Intent intent = new Intent(this, Detail.class);
        intent.putExtra("symbol",symbol);
        startActivityForResult(intent, MainActivity.RESIGTER_REQUEST_CODE);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}