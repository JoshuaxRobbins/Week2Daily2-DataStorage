package com.example.josh.week2daily2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    public static final int PICK_IMAGE = 1;

    ImageView ivPicture;
    EditText etLookupValue;
    TextView tvReadString;
    EditText etWriteString;

    private Uri selected_image_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivPicture = findViewById(R.id.ivPicture);
        etLookupValue = findViewById(R.id.etLookupValue);
        tvReadString = findViewById(R.id.tvReadString);
        etWriteString = findViewById(R.id.etWriteString);

    }

    public void loadSave(View view) {

        DataBaseHelper dbHelper = new DataBaseHelper(this);

        switch (view.getId()) {
            case R.id.btnLoad:
                byte[] pic = dbHelper.getPicture(etLookupValue.getText().toString());
                ivPicture.setImageBitmap(BitmapFactory.decodeByteArray(pic,0,1024));
                break;

            case R.id.btnSave:
                try {
                    InputStream inputStream = getContentResolver().openInputStream(selected_image_uri);
                    byte[] picture = getBytes(inputStream);
                    dbHelper.savePicture(picture);
                } catch (Exception e) {
                }
                break;
            case R.id.btnUpdate:
                break;

        }
    }

    public void getPicture(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        selected_image_uri = data.getData();
        ivPicture.setImageURI(selected_image_uri);
    }

    public byte[] getBytes(InputStream inputStream) {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        try {
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
        } catch (Exception e) {

        }
        return byteBuffer.toByteArray();
    }


    public void readWrite(View view) {
        IOClass io = new IOClass();
        switch (view.getId()){
            case R.id.btnRead:
                tvReadString.setText(io.read(this));
                break;
            case R.id.btnWrite:
                io.write(etWriteString.getText().toString(),this);
                break;
        }

    }
}
