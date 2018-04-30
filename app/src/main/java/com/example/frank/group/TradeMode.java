package com.example.frank.group;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class TradeMode extends AppCompatActivity implements View.OnClickListener, StockFragment.OnFragmentInteractionListener,FragListen {

    ImageButton switchModeButton;
    Database databaseManager;
    LinearLayout container;
    TextView cash,total;
    public static ArrayList<Company> stockList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_mode);

        switchModeButton = findViewById(R.id.changMode);
        switchModeButton.setOnClickListener(this);

        databaseManager = new Database(this);
        container = findViewById(R.id.container);
        cash = findViewById(R.id.cash);
        total = findViewById(R.id.total);
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
        updateProperty();
        updateUI();
    }

    public void updateProperty(){
        cash.setText("$ " + databaseManager.getCash());
        total.setText("$ " + databaseManager.getTotal());
    }


    /**
     * created at 5:58 p.m. 4/28/2018
     * @param view
     */
    @Override
    public void onClick(View view) {
        if(view == switchModeButton){
            Intent intent = new Intent(this, MainActivity.class);
            startActivityForResult(intent, MainActivity.TRAEMODE_RESIGTER_REQUEST_CODE);
        }
    }

    public void updateUI(){
        container.removeAllViews();
        Cursor cursor = databaseManager.sqLiteDatabase.query(DBOpenHelper.TABLE_NAME, new String[]{"*"},
                null, null, null, null, null);
        stockList = databaseManager.get(cursor);
        for (Company company: stockList){
            if (company.symbol.equals("user#info")){
                continue;
            }
            StockFragment frag = new StockFragment();
            Bundle args = new Bundle();
            args.putString("name", company.companyName);
            args.putDouble("price", company.price);
            args.putString("symbol",company.symbol);
            args.putInt("mode",1);
            frag.setArguments(args);
            getSupportFragmentManager().beginTransaction().add(R.id.container, frag).commit();
        }
    }
    private void bs(String symbol){
        Intent intent = new Intent(this, TradingInterface.class);
        intent.putExtra("symbol",symbol);
        startActivityForResult(intent, MainActivity.TRAEMODE_RESIGTER_REQUEST_CODE);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void fragText(String str) {

    }

    @Override
    public void fragButton(String str) {
        bs(str);
    }
}
