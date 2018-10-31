package com.example.josh.week2daily2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.ContactsContract;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String dbName = "pictureDB";
    public static final int version = 1;
    public static String key = "1";

    public static final String CREATE_PICTURES_TABLE =
            "CREATE TABLE " + PictureContract.FeedEntry.TABLE_NAME + "(" +
                    PictureContract.FeedEntry.KEY + " INTEGER PRIMARY KEY, " +
                    PictureContract.FeedEntry.PICTURE +  " BLOB)";

    public static final String FIND_PICTURE =
            "SELECT picture FROM " + PictureContract.FeedEntry.TABLE_NAME +
                    "WHERE " + PictureContract.FeedEntry.KEY + " = " + key;



    public DataBaseHelper(Context context) {
        super(context, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PICTURES_TABLE);
    }

    public void savePicture(byte[] picture ){
        SQLiteDatabase db = this.getWritableDatabase();
        String insertSQL = "INSERT INTO PICTURETABLE (keys,picture) VALUES(?,?)";
        SQLiteStatement insert = db.compileStatement(insertSQL);
        insert.clearBindings();
        insert.bindBlob(2,picture);
        insert.executeInsert();
        db.close();

    }

    public byte[] getPicture(String key){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT picture FROM picturetable WHERE " +
            "keys = ?",new String[] {key});
        cursor.moveToFirst();
        return cursor.getBlob(2);


    }












    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
