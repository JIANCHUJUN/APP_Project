package com.example.frank.group;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Setting extends AppCompatActivity {

    EditText newcash;
    Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        newcash = findViewById(R.id.newcash);
        database = new Database(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        database.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        database.open();
        newcash.setText(database.getCash());
    }

    public void submit(View view){
        Double cash;
        try{
            cash = Double.parseDouble(newcash.getText().toString());
            database.updateUserInfo(cash);
            Intent intent = new Intent(this, TradeMode.class);
            startActivityForResult(intent, MainActivity.TRAEMODE_RESIGTER_REQUEST_CODE);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void back(View view){
        Intent intent = new Intent(this, TradeMode.class);
        startActivityForResult(intent, MainActivity.TRAEMODE_RESIGTER_REQUEST_CODE);
    }

    public void clear(View view) {
        database.clear_shares();
    }

}
