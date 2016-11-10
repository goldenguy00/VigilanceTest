package com.example.wilsongolden.vigilancetest;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button myButton;
    MyThread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        thread = new MyThread();

        thread.start();
        myButton = new Button(this);
        myButton.setText("Push Me");
        myButton.setBackgroundColor(getColor(R.color.black));
        myButton.setOnClickListener(new buttonListener());

        RelativeLayout mainLayout = (RelativeLayout)findViewById(R.id.activity_main);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        mainLayout.addView(myButton, layoutParams);
    }

    private class buttonListener implements View.OnClickListener {
        public void onClick(View view) {
            myButton.setBackgroundColor(getColor(R.color.white));
            thread.endThread();
        }
    }
}
