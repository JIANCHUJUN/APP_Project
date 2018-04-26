package com.example.frank.group;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Detail extends AppCompatActivity {

    String symbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        symbol = getIntent().getExtras().getString("symbol");
    }

    public void delete(View view){
        //Database database = new Database();
    }

    public void more_info(View view){

    }

    public void back(View view){
        finish();
    }

}
