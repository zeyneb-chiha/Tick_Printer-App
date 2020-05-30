package com.example.tickp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Main3Activity extends AppCompatActivity {







    private EditText textInputEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Log.d("lifecycle", "onCreate invoked");
        final Button mClickBtn = findViewById(R.id.button4);
        textInputEmail=findViewById(R.id.editText4);

        mClickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent click = new Intent(Main3Activity.this, Main4Activity.class);
                startActivity(click);
                ValideEmail(textInputEmail.getText().toString());


            }
        });


    }
    private boolean ValideEmail(String s) {
        String EmailInput = textInputEmail.getText().toString().trim();
        if (EmailInput.isEmpty()) {
            textInputEmail.setError("Email can not be empty");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(EmailInput).matches()) {
            textInputEmail.setError("please enter valid email");
        } else {
            textInputEmail.setError(null);
            return true;
        }

        return true;
    }



}
