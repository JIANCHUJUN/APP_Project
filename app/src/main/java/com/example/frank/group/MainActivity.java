package com.example.frank.group;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class MainActivity extends AppCompatActivity implements StockFragment.OnFragmentInteractionListener, View.OnClickListener, FragListen {

    StockAPI stockAPI;
    Database databaseManager;
    EditText ticker;
    LinearLayout container;
    ImageButton switchMode;
    static final int RESIGTER_REQUEST_CODE = 1;
    public static ArrayList<Company> stockList;
    ReentrantLock lock;
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        switchMode = findViewById(R.id.changMode);
        stockAPI = new StockAPI(this);
        //stockAPI.getInfo("aapl");
        databaseManager = new Database(this);
        ticker = findViewById(R.id.ticker);
        container = findViewById(R.id.container);
        stockList = new ArrayList<>();
        switchMode.setOnClickListener(this);
        lock = new ReentrantLock();
        timer = new Timer();


    }

    @Override
    protected void onPause() {
        super.onPause();
        databaseManager.close();
        if (timer != null){
            timer.cancel(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        databaseManager.open();
        databaseManager.initUserInfo(7777);
        updateUI();
        //refresh();
        if(timer != null){
            timer.execute();
        }

    }

    private void refresh(){
        if (stockList!=null){
            for(Company company:stockList){
                stockAPI.getInfo(company.symbol);
            }
        }
    }

    public void add(View view){
        if(ticker.getText().toString() == ""){
            refresh();
            return;
        }
        for (Company company: stockList){
            if (company.symbol.equals(ticker.getText().toString())){
                refresh();
                updateUI();
                return;
            }
        }
        stockAPI.getInfo(ticker.getText().toString());
    }

    public void updateUI(){
        lock.lock();
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
            frag.setArguments(args);
            getSupportFragmentManager().beginTransaction().add(R.id.container, frag).commit();
        }
        lock.unlock();
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

    public void delete(String symbol) {
        databaseManager.sqLiteDatabase.delete("stocks","symbol='"+symbol+"'",null);
        updateUI();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    static int TRAEMODE_RESIGTER_REQUEST_CODE = 3;
    /**
     * update at 5:38, 4/28/2018, fangzh
     * @param view
     */
    @Override
    public void onClick(View view) {
        if(view == switchMode){
            Intent intent = new Intent(this, TradeMode.class);
            startActivityForResult(intent, MainActivity.TRAEMODE_RESIGTER_REQUEST_CODE);
        }
    }

    private class Timer extends AsyncTask<Integer, String, Void>
    {
        @Override
        protected Void doInBackground(Integer... integers) {
            while(!isCancelled()){
                try{
                    Thread.sleep(1000*60);
                }catch (Exception e) {
                    e.printStackTrace();
                }
                refresh();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }
    }

    @Override
    public void fragText(String str) {
        detail(str);
    }

    @Override
    public void fragButton(String str) {
        delete(str);
    }
}
