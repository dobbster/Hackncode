package com.example.user.solarmeasure;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mydata.db";
    private static final String TABLE_DATA = "data";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CDATA = "charge";
    private static final String COLUMN_DDATA = "discharge";
    private Context context;

    MyDBHandler(Context context1, SQLiteDatabase.CursorFactory factory) {
        super(context1, DATABASE_NAME, factory, DATABASE_VERSION);
        context = context1;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_DATA + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CDATA + " INTEGER NOT NULL, " +
                COLUMN_DDATA + " INTEGER NOT NULL " +
                ");";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
        onCreate(sqLiteDatabase);
    }

    //Add a new data to table
    void addData(int charge, int discharge) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CDATA, charge);
        values.put(COLUMN_DDATA, discharge);

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_DATA, null, values);
        db.close();
    }

    //Load data
    Vector getData(boolean charge) {
        Vector data = new Vector();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_DATA + " ORDER BY " + COLUMN_ID + " DESC;";

        Cursor c = db.rawQuery(query, null);
        for (c.moveToLast(); !c.isBeforeFirst(); c.moveToPrevious()) {
            int tdata = charge ? c.getInt(c.getColumnIndex(COLUMN_CDATA)) : c.getInt(c.getColumnIndex(COLUMN_DDATA));
            data.addElement(tdata);
        }
        db.close();
        c.close();
        return data;
    }

    void refreshTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
        String query = "CREATE TABLE " + TABLE_DATA + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CDATA + " INTEGER NOT NULL, " +
                COLUMN_DDATA + " INTEGER NOT NULL " +
                ");";
        db.execSQL(query);
    }

}
