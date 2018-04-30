package com.example.frank.group;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JiachengYe on 3/10/2018.
 */
public class Database {
    private DBOpenHelper dbOpenHelper;
    public SQLiteDatabase sqLiteDatabase;
    

    public Database(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }

    public void open() {
        sqLiteDatabase = dbOpenHelper.getWritableDatabase();
    }

    public void close() {
        sqLiteDatabase.close();
    }

    public void insertCompanyInfo(Company company) {
        //sqLiteDatabase.delete(dbOpenHelper.TABLE_NAME, dbOpenHelper.COLUMN_SYMBOL + "=" + company.symbol,null);
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBOpenHelper.COLUMN_SYMBOL, company.symbol);
        contentValues.put(DBOpenHelper.COLUMN_CEO, company.CEO);
        contentValues.put(DBOpenHelper.COLUMN_COMPANY_NAME, company.companyName);
        contentValues.put(DBOpenHelper.COLUMN_SECTOR, company.sector);
        contentValues.put(DBOpenHelper.COLUMN_DESCRIPTION, company.description);
        contentValues.put(DBOpenHelper.COLUMN_ISSUETYPE, company.issueType);
        contentValues.put(DBOpenHelper.COLUMN_WEBSITE, company.website);
        contentValues.put(DBOpenHelper.COLUMN_EXCHANGE, company.exchange);
        contentValues.put(DBOpenHelper.COLUMN_INDUSTRY, company.industry);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_NAME, null, contentValues);
    }


    public ArrayList<Company> get(Cursor cursor){
        if (cursor != null){
            cursor.moveToFirst();
            ArrayList<Company> result = new ArrayList<>();
            while (!cursor.isAfterLast()){
                Company company = new Company();
                try{company.companyName = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_COMPANY_NAME));}catch (Exception e){};
                try{company.CEO = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_CEO));}catch (Exception e){};
                try{company.description = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_DESCRIPTION));}catch (Exception e){};
                try{company.exchange = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_EXCHANGE));}catch (Exception e){};
                try{company.industry = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_INDUSTRY));}catch (Exception e){};
                try{company.issueType = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_ISSUETYPE));}catch (Exception e){};
                try{company.sector = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_SECTOR));}catch (Exception e){};
                try{company.symbol = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_SYMBOL));}catch (Exception e){};
                try{company.website = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_WEBSITE));}catch (Exception e){};
                try{company.price = cursor.getDouble(cursor.getColumnIndex(DBOpenHelper.COLUMN_PRICE));}catch (Exception e){};
                try{company.number = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_NUMBER));}catch (Exception e){};
                result.add(company);
                cursor.moveToNext();
            }
            return result;
        }
        return null;
    }

    public void updateUserInfo(double cash){
        Cursor cursor = this.sqLiteDatabase.query(DBOpenHelper.TABLE_NAME, new String[]{"*"},
                "symbol='user#info'", null, null, null, null);
        cursor.moveToFirst();
//        if (cursor != null){
//            cursor.moveToFirst();
//            Boolean b = cursor.isAfterLast();
//            String s = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_SYMBOL));
//            Double p = cursor.getDouble(cursor.getColumnIndex(DBOpenHelper.COLUMN_PRICE));
//        }
        if (cursor.isAfterLast())
        {
            //there is not user#info
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBOpenHelper.COLUMN_SYMBOL, "user#info");
            contentValues.put(DBOpenHelper.COLUMN_PRICE, cash);
            sqLiteDatabase.insert(DBOpenHelper.TABLE_NAME, null, contentValues);
        }

    }

    public String getCash() {
        Cursor cursor = this.sqLiteDatabase.query(DBOpenHelper.TABLE_NAME, new String[]{"*"},
                "symbol='user#info'", null, null, null, null);
        cursor.moveToFirst();
        return String.valueOf(cursor.getDouble(cursor.getColumnIndex(DBOpenHelper.COLUMN_PRICE)));
    }

    public String getTotal(){
        Cursor cursor = this.sqLiteDatabase.query(DBOpenHelper.TABLE_NAME, new String[]{"*"},
                "symbol!='user#info' and number!=0", null, null, null, null);
        ArrayList<Company> cp_list = get(cursor);
        double total = 0;
        for(Company company: cp_list){
            total+=company.price*company.number;
        }
        return String.valueOf(total);

    }

    public void submitBuy(String symbol, int number){
        //get stock's information
        Cursor cursor = this.sqLiteDatabase.query(DBOpenHelper.TABLE_NAME, new String[]{"*"},
                "symbol='"+ symbol+"'", null, null, null, null);
        ArrayList<Company> cp_list = get(cursor);

        //if can find stock
        if(cp_list.size() > 0){
            Company cp = cp_list.get(0);
            cursor = this.sqLiteDatabase.query(DBOpenHelper.TABLE_NAME, new String[]{"*"},
                    "symbol='user#info'", null, null, null, null);
            Company user  = get(cursor).get(0);

            if (cp.price * number > user.price){
                //inform the money is not enough
            }
            else{
                //update stock count
                int new_number = cp.number + number;
                ContentValues contentValues = new ContentValues();
                contentValues.put(DBOpenHelper.COLUMN_NUMBER, new_number);
                this.sqLiteDatabase.update("stocks", contentValues, "symbol=" + "'" + symbol + "'", null);

                //update user's cash
                double new_cash = user.price - cp.price * number;
                contentValues = new ContentValues();
                contentValues.put(DBOpenHelper.COLUMN_PRICE, new_cash);
                this.sqLiteDatabase.update("stocks", contentValues, "symbol=" + "'user#info'", null);
            }
        }
    }

    public void submitSell(String symbol, int number){
        //get stock's information
        Cursor cursor = this.sqLiteDatabase.query(DBOpenHelper.TABLE_NAME, new String[]{"*"},
                "symbol='"+ symbol+"'", null, null, null, null);
        ArrayList<Company> cp_list = get(cursor);

        //if can find stock cp
        if(cp_list.size() > 0){
            Company cp = cp_list.get(0);
            cursor = this.sqLiteDatabase.query(DBOpenHelper.TABLE_NAME, new String[]{"*"},
                    "symbol='user#info'", null, null, null, null);
            Company user  = get(cursor).get(0);

            if (cp.number < number){
                //inform the stocks are not enough
            }
            else{
                //update stock count
                int new_number = cp.number - number;
                ContentValues contentValues = new ContentValues();
                contentValues.put(DBOpenHelper.COLUMN_NUMBER, new_number);
                this.sqLiteDatabase.update("stocks", contentValues, "symbol=" + "'" + symbol + "'", null);

                //update user's cash
                double new_cash = user.price + cp.price * number;
                contentValues = new ContentValues();
                contentValues.put(DBOpenHelper.COLUMN_PRICE, new_cash);
                this.sqLiteDatabase.update("stocks", contentValues, "symbol=" + "'user#info'", null);
            }
        }
    }


    public void deleteAll() {
        if (sqLiteDatabase.isOpen()) {
            sqLiteDatabase.execSQL("DELETE FROM " + DBOpenHelper.TABLE_NAME);
        }
    }
}
