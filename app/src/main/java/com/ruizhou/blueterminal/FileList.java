package com.ruizhou.blueterminal;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.RequiresApi;
import android.os.Bundle;
import android.os.Build;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class FileList extends AppCompatActivity {

    private Button file1;
    private String filename1;

    private Button file2;
    private String filename2;

    private BLE_Service ble;
    private Context context;
    private String read_data;
//    private ArrayList<String> nameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);


        ble = MainActivity.ble;
        context = MainActivity.context;
        read_data = MainActivity.read_data;
//        nameList = MainActivity.nameList;

//        File anchor = new File(ble.anchorPath);
        FileInputStream fis = null;
        String file_data = null;
        try {
            fis = new FileInputStream(ble.anchorFile);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();

            String text;
            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            file_data = sb.toString();
            isr.close();
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

//        // Parse and clean read_data
//        String[] separated = read_data.split("\n");
//        for (int i = 0; i < separated.length; i++) {
//            separated[i] = separated[i].trim();
//        }
        // Parse and clean read_data
        String[] separated = file_data.split("\n");
        for (int i = 0; i < separated.length; i++) {
            separated[i] = separated[i].trim();
        }

//        filename1 = getIntent().getStringExtra("filename");
        Log.d("XAXAXAXAXAXAXA", "file_data: " + file_data);
//        filename1 = "dfile3.txt";
//        filename2 = "dfile2.txt";
        filename1 = separated[0];
        filename2 = separated[1];
//        filename1 = nameList.get(0);
//        filename2 = nameList.get(1);
        TextView dataView = (TextView) findViewById(R.id.button_file_name);
        dataView.setText(filename1);
        TextView dataView2 = (TextView) findViewById(R.id.button_file_name2);
        dataView2.setText(filename2);

        file1 = (Button) findViewById(R.id.button_file_data);
        file1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                ble.setFileListName(filename1);
                ble.setFile(context); // Delete file if it exists and create new file
                try {
                    String command = filename1 + "#";
                    ble.writeData(command);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                openFileData(filename1);

            }
        });

        file2 = (Button) findViewById(R.id.button_file_data2);
        file2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                ble.setFileListName(filename2);
                ble.setFile(context); // Delete file if it exists and create new file
                try {
                    String command = filename2 + "#";
                    ble.writeData(command);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                openFileData(filename2);

            }
        });
    }

    public void openFileData(String filename) {
        Intent intent = new Intent(this, GraphData.class);
        intent.putExtra("filename", filename);
        startActivity(intent);
    }
}