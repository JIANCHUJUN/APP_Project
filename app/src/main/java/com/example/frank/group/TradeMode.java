package com.example.frank.group;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class TradeMode extends AppCompatActivity implements View.OnClickListener {

    ImageButton switchModeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_mode);

        switchModeButton = findViewById(R.id.changMode);
        switchModeButton.setOnClickListener(this);

    }

    /**
     * created at 5:58 p.m. 4/28/2018
     * @param view
     */
    @Override
    public void onClick(View view) {
        if(view == switchModeButton){
           finish();
        }
    }
}
