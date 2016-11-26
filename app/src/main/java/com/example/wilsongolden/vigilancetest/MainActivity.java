package com.example.wilsongolden.vigilancetest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button myButton;
    TextView hit, miss, falseStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myButton = (Button) findViewById(R.id.button);
        myButton.setOnClickListener(new buttonListener());
        hit = (TextView) findViewById(R.id.hits);
        miss = (TextView) findViewById(R.id.misses);
        falseStart = (TextView) findViewById(R.id.falseStarts);
    }

    private class buttonListener implements View.OnClickListener {
        public void onClick(View view) {
            myButton.setBackgroundColor(getColor(R.color.black));
            new waitTask().execute();
        }
    }

    private class waitTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Integer doInBackground(Void... voids) {
            Random rand = new Random();
            int random = 1 + rand.nextInt(9);
            System.out.println(random);

            try {
                Thread.sleep(random * 1000);
            } catch (Exception e) {
                System.out.println("False Start");
            }

            System.out.println("White");
            return getColor(R.color.white);
        }

        @Override
        protected void onPostExecute(Integer result) {
            myButton.setBackgroundColor(result);
            System.out.println(result);
        }
    }
}
