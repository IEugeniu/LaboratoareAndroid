package com.example.laboratorul_3.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.laboratorul_3.Model.Information;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper {

    private Context ctx;
    public DataBaseHandler(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CLIENT_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_TITLE + " TEXT,"
                + Constants.KEY_LINK + " TEXT);";
        db.execSQL(CREATE_CLIENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);

        onCreate(db);
    }

    public void addInformation(Information information) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_TITLE, information.getTitle());
        values.put(Constants.KEY_LINK, information.getLink());


        db.insert(Constants.TABLE_NAME, null, values);
        Log.d("Saved", "Saved to DB");
        db.close();
    }

    public List<Information> getAllInformation() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Information> informationList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[] {Constants.KEY_TITLE,
                        Constants.KEY_LINK}, null, null, null,
                null, null);

        while (cursor.moveToNext()) {
            Information information = new Information();

            information.setTitle(cursor.getString(cursor.getColumnIndex(Constants.KEY_TITLE)));
            information.setLink(cursor.getString(cursor.getColumnIndex(Constants.KEY_LINK)));

            informationList.add(information);
        }
        return informationList;
    }

    public void deleteInformation(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.KEY_TITLE + "=?",
                new String[] {title});
        db.close();
    }
}

