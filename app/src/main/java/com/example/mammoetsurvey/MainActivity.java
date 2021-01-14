package com.example.mammoetsurvey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {

    private EditText km, desc;
    private ImageView photo, scr;
    private DatabaseReference mDataBase;
    private String Mark = "Mark";
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, RouteActivity.class);
                startActivity(intent);
            }
        });
    }

    public void init(){
        add = (Button) findViewById(R.id.add);


    }


}
