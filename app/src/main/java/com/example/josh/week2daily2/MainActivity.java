package com.example.josh.week2daily2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    String TAG = "_TAG";


    private ImageView ivPicture;
    private EditText etLookupValue;
    private TextView tvReadString;
    private EditText etWriteString;
    private TextView tvPrimaryKey;
    private DataBaseHelper dataBaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataBaseHelper = new DataBaseHelper(getApplicationContext());

        ivPicture = findViewById(R.id.ivPicture);
        etLookupValue = findViewById(R.id.etLookupValue);
        tvReadString = findViewById(R.id.tvReadString);
        etWriteString = findViewById(R.id.etWriteString);
        tvPrimaryKey = findViewById(R.id.tvPrimaryKey);

    }

    public void loadSave(View view) {

        DataBaseHelper dbHelper = new DataBaseHelper(this);
        long place;
        switch (view.getId()) {
            case R.id.btnLoad:
                byte[] pictureArray = dataBaseHelper.getImage(etLookupValue.getText().toString());
                if(pictureArray == null){
                    Toast.makeText(getApplicationContext(),"Please enter valid Key",Toast.LENGTH_SHORT).show();
                }
                else {
                    Bitmap picture = BitmapFactory.decodeByteArray(pictureArray, 0, pictureArray.length);
                    ivPicture.setImageBitmap(picture);
                    tvPrimaryKey.setText(etLookupValue.getText());
                }
                break;

            case R.id.btnSave:
                place = dataBaseHelper.saveImage(bitMaptoBytes());
                tvPrimaryKey.setText(Long.toString(place));
                break;
            case R.id.btnUpdate:
                place = dataBaseHelper.updateImage(bitMaptoBytes(),tvPrimaryKey.getText().toString());
                Log.d(TAG, "loadSave: " + Long.toString(place));
                break;
            case R.id.btnDelete:
                dataBaseHelper.deleteImage(tvPrimaryKey.getText().toString());
                break;
            case R.id.btnDrop:
                dataBaseHelper = dataBaseHelper.dropTable(getApplicationContext());
                break;
        }
    }

    private byte[] bitMaptoBytes() {
        Drawable drawable = ivPicture.getDrawable();
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        return stream.toByteArray();
    }

    public void readWrite(View view) {


    }

    //Take Picture
    public void takePicture(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,0);
    }
    //Put Picture on Image View
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        ivPicture.setImageBitmap(bitmap);
    }
}
