package com.example.lunchbuddies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Homepage extends AppCompatActivity {
ImageView imageview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        imageview=findViewById(R.id.imageView2);
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bridge=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(bridge);
            }
        });
    }
}
