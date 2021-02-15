package com.ruizhou.blueterminal.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ruizhou.blueterminal.BLE_Service;
import com.ruizhou.blueterminal.R;

import java.io.UnsupportedEncodingException;

public class DetailActivity extends AppCompatActivity {

    public static String read_data;

    private TextView responseView;
    private EditText cmdView;
    private Button submitBut;
    private Button graphBut;
    private Button receiveBut;
    private Button dumpBut;

    private BLE_Service ble;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setUpUI();
        responseView.setMovementMethod(new ScrollingMovementMethod());
        ble = MainActivity.ble;

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null){
                    // user is signed in
                }else{
                    //user is signed out
                    
                }
            }
        };

        submitBut.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                String content = cmdView.getText().toString();
                try {
                    ble.writeData(content);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        receiveBut.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onClick(View view) {
                ble.readData();
                responseView.setText(ble.response.toString());
            }
        });

        graphBut.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
//                ble.setFileListName("filelist.txt");
//                ble.setFile(context); // Delete file if it exists and create new file
//                try {
//                    ble.writeData("NAMES#");
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
                ble.readData();
                read_data = ble.response.toString();
                openList("test.txt");
            }
        });
        dumpBut.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                ble.setFileListName(ble.anchorName);
//                ble.setFile(context); // Delete file if it exists and create new file
                try {
                    ble.writeData("NAMES#");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
//                openList("data.txt");

            }
        });

    }
    private void setUpUI(){
        responseView = (TextView)findViewById(R.id.resultView);
        cmdView = (EditText)findViewById(R.id.cmdInput);
        submitBut = (Button)findViewById(R.id.submitBut);
        graphBut = (Button)findViewById(R.id.graphBut);
        receiveBut = (Button)findViewById(R.id.receiveBut);
        dumpBut = (Button)findViewById(R.id.dump);
    }

    public void openList(String filename) {
        Intent intent = new Intent(this, FileList.class);
        intent.putExtra("filename", filename);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}