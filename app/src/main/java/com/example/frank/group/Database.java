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
                result.add(company);
                cursor.moveToNext();
            }
            return result;
        }
        return null;
    }


    public void deleteAll() {
        if (sqLiteDatabase.isOpen()) {
            sqLiteDatabase.execSQL("DELETE FROM " + DBOpenHelper.TABLE_NAME);
        }
    }
}
