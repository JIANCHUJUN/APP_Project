package com.example.frank.group;

/**
 * Created by JiachengYe on 4/25/2018.
 */

import android.content.ContentValues;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.locks.ReentrantLock;

public class StockAPI implements Response.Listener<String>, Response.ErrorListener{

    String requestURL = "https://api.iextrading.com/1.0";
    //+?
    String argument = "/stock/aapl/company";

    MainActivity mainActivity;
    RequestQueue queue;
    ReentrantLock lock;
    String price_symbol;

    public StockAPI(MainActivity ma){
        mainActivity = ma;
        queue = Volley.newRequestQueue(mainActivity);
        lock = new ReentrantLock();
    }

    public void getInfo(String symbol_name){
        String request_string = requestURL + "/stock/" + symbol_name + "/company";
        StringRequest request = new StringRequest(Request.Method.GET,
                request_string, this, this);
        queue.add(request);
    }

    public void getPrice(String symbol_name){
        String request_string = requestURL + "/stock/" + symbol_name + "/price";
        StringRequest request = new StringRequest(Request.Method.GET,
                request_string, this, this);
        lock.lock();
        price_symbol = symbol_name;
        queue.add(request);
    }


    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {
        Log.d("test",response);
        try{
            Double price = Double.parseDouble(response);
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBOpenHelper.COLUMN_PRICE, price);
            mainActivity.databaseManager.sqLiteDatabase.update("stocks", contentValues, "symbol=" + "'" + price_symbol + "'", null);//execSQL("update stocks set price="
            price_symbol = "";
            lock.unlock();
            mainActivity.updateUI();
            return;
        }
        catch (Exception e){
            e.printStackTrace();
        }

        try {
            JSONObject jsonObject = new JSONObject(response);
            String symbol = jsonObject.getString("symbol");
            String companyName =jsonObject.getString("companyName");
            String exchange =jsonObject.getString("exchange");
            String industry =jsonObject.getString("industry");
            String website =jsonObject.getString("website");
            String description =jsonObject.getString("description");
            String CEO =jsonObject.getString("CEO");
            String issueType = jsonObject.getString("issueType");
            String sector =jsonObject.getString("sector");
            Company company = new Company();
            company.companyName = companyName;
            company.CEO = CEO;
            company.description = description;
            company.exchange = exchange;
            company.industry = industry;
            company.issueType = issueType;
            company.sector = sector;
            company.symbol = symbol;
            company.website = website;
            mainActivity.saveInfo(company);
            getPrice(symbol);
            //System.out.println("hey!");
        } catch (JSONException e) {
            System.out.println("shit!");
            e.printStackTrace();
        }
    }
}
