package com.example.frank.group;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Detail extends AppCompatActivity implements View.OnClickListener, LineChartFragment.OnFragmentInteractionListener {

    String symbol;
    Company currCompany;
    TextView companyNameT, symbolT, companyT, exchangeT,
            industryT, websiteT, CEOT, issueTypeT,sectorT,
            descriptionT;
    Button webB, backB;
    LineChartFragment lineChartFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        symbol = getIntent().getExtras().getString("symbol");

        for(int i  = 0; i < MainActivity.stockList.size(); i++ ) {
            if(MainActivity.stockList.get(i).symbol.equals(symbol)){
                currCompany =  MainActivity.stockList.get(i);
            }
        }
        companyNameT = findViewById(R.id.companyName);
        symbolT = findViewById(R.id.symbol);
        companyT = findViewById(R.id.company);
        exchangeT = findViewById(R.id.exchange);
        industryT = findViewById(R.id.industry);
        websiteT = findViewById(R.id.website);
        CEOT = findViewById(R.id.CEO);
        issueTypeT = findViewById(R.id.issueType);
        sectorT = findViewById(R.id.sector);
        descriptionT = findViewById(R.id.description);

        webB = findViewById(R.id.web);
        backB = findViewById(R.id.back);
        webB.setOnClickListener(this);
        backB.setOnClickListener(this);

        lineChartFragment = (LineChartFragment) getSupportFragmentManager().findFragmentById(R.id.lineChartContainerR);

        updateTheTextView();

    }

    private void updateTheTextView() {
        companyNameT.setText(currCompany.companyName);
        symbolT.setText(currCompany.symbol);
        companyT.setText(currCompany.companyName);
        exchangeT.setText(currCompany.exchange);
        industryT.setText(currCompany.industry);
        websiteT.setText(currCompany.website);
        CEOT.setText(currCompany.CEO);
        issueTypeT.setText(currCompany.issueType);
        sectorT.setText(currCompany.sector);
        descriptionT.setText(currCompany.description);
    }

    public void delete(View view){
        //Database database = new Database();
    }

    public void more_info(View view){

    }

    public void back(View view){
        finish();
    }

    @Override
    public void onClick(View view) {
        if(webB == view){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(currCompany.website));
            startActivity(intent);
        }
        if(backB == view){
            finish();
        }
    }
}
