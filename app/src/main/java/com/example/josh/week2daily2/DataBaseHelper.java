package com.example.josh.week2daily2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String TAG = "_TAG";

    public DataBaseHelper(@NonNull Context context) {
        super(context, PictureContract.NAME, null, PictureContract.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PictureContract.CREATE_TABLE);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        
    }

    public long saveImage(byte[] bytePicture){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PictureContract.FeedEntry.COL_PICTURE,bytePicture);
        long rowId = database.insert(PictureContract.FeedEntry.TABLE_NAME,null,contentValues);
        return rowId;
    }
    public long updateImage(byte[] bytePicture, String key){
        Log.d(TAG, "updateImage: " + key);
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PictureContract.FeedEntry._ID,key);
        contentValues.put(PictureContract.FeedEntry.COL_PICTURE,bytePicture);
        long rowId = database.update(PictureContract.FeedEntry.TABLE_NAME,contentValues,PictureContract.UPDATE_KEY,new String[] {key});
        return rowId;
    }

    public byte[] getImage(String key){
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery(PictureContract.GET_KEY,new String[] {key});
        cursor.moveToFirst();
        if(cursor.getCount() == 0){
            return null;
        }
        byte[] picture = cursor.getBlob(0);
        cursor.close();
        return picture;
    }

    public DataBaseHelper dropTable(Context context){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(PictureContract.DROP_TABLE);
        context.deleteDatabase(PictureContract.NAME);
        return new DataBaseHelper(context);

    }

    public void deleteImage(String key){
        SQLiteDatabase database = getWritableDatabase();
        database.delete(PictureContract.FeedEntry.TABLE_NAME,PictureContract.UPDATE_KEY, new String[] {key});
    }
}
