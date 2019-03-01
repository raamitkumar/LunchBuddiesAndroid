package com.example.lunchbuddies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class CustomDialogbox extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_dialogbox);


        Intent bridge=new Intent(getApplicationContext(),MainActivity.class);
        Toast.makeText(getApplicationContext(), "Please Register OR Login", Toast.LENGTH_LONG).show();
        startActivity(bridge);
    }
}
