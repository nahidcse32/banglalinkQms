package com.sharnit.banglalinkqms.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sharnit.banglalinkqms.Adapter.ServiceType;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASENAME = "qms";
    public static final String TABLE_SERVICE = "service";
    public static final int DB_VERSION = 1;
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String CREATE_TABLE_SERVICE_TYPE = "CREATE TABLE IF NOT EXISTS " + TABLE_SERVICE + " ( " + ID + " INTEGER , " + NAME + " VARCHAR);";
    Context context;

    public DBHelper(Context context) {
        super(context, DATABASENAME, null, DB_VERSION);
        // TODO Auto-generated constructor stub
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SERVICE_TYPE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVICE);
        onCreate(db);
    }

    public void clearAndUpdateAll() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVICE);
        onCreate(db);
    }

    public int insertType(ServiceType data) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, data.getId());
        values.put(NAME, data.getName());

        int ins = (int) db.insert(TABLE_SERVICE, null, values);

        return ins;
    }

    public ArrayList<ServiceType> getAllType() {


        ArrayList<ServiceType> list = new ArrayList<ServiceType>();
        ServiceType data = null;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SERVICE, null);

        if (cursor.moveToFirst()) {

            do {
                data = new ServiceType();
                data.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID))));
                data.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                list.add(data);
                Log.e("a", data.getName());
            }
            while (cursor.moveToNext());
        }
        return list;
    }


}
