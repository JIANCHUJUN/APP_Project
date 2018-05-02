package com.example.frank.group;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity implements FragListen, StockFragment.OnFragmentInteractionListener{

    LinearLayout container;
    Database databaseManager;
    ArrayList<History> his_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        container = findViewById(R.id.container);
        databaseManager = new Database(this);
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
        updateHistory();
    }

    public void back(View view){
        finish();
    }

    public void updateHistory(){
        container.removeAllViews();
        Cursor cursor = databaseManager.sqLiteDatabase.query(DBOpenHelper.HISTORY_TABLE, new String[]{"*"},
                null, null, null, null, null);
        his_list = databaseManager.get_History(cursor);
        for (History history: his_list){
            StockFragment frag = new StockFragment();
            Bundle args = new Bundle();
            args.putInt("number", history.number);
            args.putDouble("price", history.price);
            args.putString("symbol",history.symbol);
            args.putString("type",history.type);
            args.putDouble("total",history.total);
            args.putInt("mode",2);
            frag.setArguments(args);
            getSupportFragmentManager().beginTransaction().add(R.id.container, frag).commit();
        }
    }

    @Override
    public void fragText(String str) {

    }

    @Override
    public void fragButton(String str) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
