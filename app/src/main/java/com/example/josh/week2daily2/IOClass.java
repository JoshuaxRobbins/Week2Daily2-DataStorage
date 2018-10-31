package com.example.josh.week2daily2;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class IOClass {

    public IOClass(){

    }

    public void write(String output,Context context){

        try {

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt",Context.MODE_PRIVATE));
            outputStreamWriter.write(output);
            outputStreamWriter.close();
        }catch (Exception e){

        }
    }

    public String read(Context context){
        String input = "";
        try{
            InputStream inputStream = context.openFileInput("config.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            input = bufferedReader.readLine();
            stringBuilder.append(input);

            inputStream.close();
        }catch (Exception e){

        }
        return input;
    }
}
