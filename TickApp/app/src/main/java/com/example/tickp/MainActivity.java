package com.example.tickp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    protected Button mButton1;
    protected Button mButton2;
    protected ImageView mHealth;
    private Object v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("lifecycle", "onCreate invoked");


        mButton1 = findViewById(R.id.button);
        mButton2 = findViewById(R.id.button2);
        mHealth = findViewById(R.id.imageView);

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, Main3Activity.class);
                startActivity(i);

            }

        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent c = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(c);


            }
        });


    }

}



