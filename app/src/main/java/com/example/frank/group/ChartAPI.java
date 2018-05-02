package com.example.frank.group;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.frank.group.Detail;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ChartAPI implements Response.Listener<String>, Response.ErrorListener {
    //https://api.iextrading.com/1.0/stock/aapl/chart/1m
    private final String APIPath = "https://api.iextrading.com/1.0/stock/";
    private ArrayList<ChartHistory> stockHistoryList;

    Detail detailActivity;
    RequestQueue queue;

    public ChartAPI(Detail detail){
        detailActivity = detail;
        queue = Volley.newRequestQueue(detailActivity);
        stockHistoryList = new ArrayList<>();
    }

    public void getInfo(String query){
        stockHistoryList.clear();
        String req = APIPath + query + "/chart/1m";
        StringRequest request = new StringRequest(Request.Method.GET,req,this,this);
        queue.add(request);
    }



    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(detailActivity, "There is something wrong", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<ChartHistory> getHisList(){
        return stockHistoryList;
    }

    boolean ran;
    @Override
    public void onResponse(String response) {

        if(!ran) {
            Log.d("test", response);
            try {
                //JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = new JSONArray(response);
                for (int i = jsonArray.length() - 8; i < jsonArray.length(); i++) {
                    String day = jsonArray.getJSONObject(i).getString("date");
                    double closedP = jsonArray.getJSONObject(i).getDouble("close");
                    ChartHistory tempHis = new ChartHistory(day, closedP);
                    stockHistoryList.add(tempHis);
                }
                detailActivity.updataLineChart(stockHistoryList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ran = true;
        }


    }

    public class ChartHistory{
        String dateOfStock;
        double closePrice;

        public ChartHistory(String day, double closePrice){
            dateOfStock = day;
            this.closePrice = closePrice;
        }


    }

}
