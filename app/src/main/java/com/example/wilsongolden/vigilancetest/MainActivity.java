package com.example.wilsongolden.vigilancetest;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button myButton, test;
    private TextView hit, miss, falseStart;
    private boolean running, missState;
    private long reactionTime;
    private int hitNum, missNum, fsNum;
    private ArrayList<Double> reactionTimeList;
    private ReactionTest react;
    public static String EXTRA_HITS = "com.example.wilsongolden.HITS";
    public static String EXTRA_MISSES = "com.example.wilsongolden.MISSES";
    public static String EXTRA_FALSE_STARTS = "com.example.wilsongolden.FALSE_STARTS";
    public static String EXTRA_REACTION_ARRAY = "com.example.wilsongolden.REACTIONS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hitNum = 0;
        missNum = 0;
        fsNum = 0;
        reactionTime = 0;
        reactionTimeList = new ArrayList<>(1);
        running = false;
        missState = false;
        react = new ReactionTest();

        test = (Button) findViewById(R.id.test);
        myButton = (Button) findViewById(R.id.button);
        myButton.setText("Press to Start");
        myButton.setOnClickListener(new buttonListener());
        hit = (TextView) findViewById(R.id.hits);
        hit.setText("Hits = " + hitNum);
        miss = (TextView) findViewById(R.id.misses);
        miss.setText("Misses = " + missNum);
        falseStart = (TextView) findViewById(R.id.falseStarts);
        falseStart.setText("False Starts = " + fsNum);
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra(EXTRA_HITS, hitNum);
        intent.putExtra(EXTRA_MISSES, missNum);
        intent.putExtra(EXTRA_FALSE_STARTS, fsNum);
        intent.putExtra(EXTRA_REACTION_ARRAY, getList());
        startActivity(intent);
    }

    private double[] getList() {
        double[] list = new double[reactionTimeList.size()];
        Iterator<Double> iterator = reactionTimeList.iterator();
        for (int i = 0; i < list.length; i++)
        {
            list[i] = iterator.next().doubleValue();
        }
        return list;
    }

    private class buttonListener implements View.OnClickListener {
        public void onClick(View view) {
            myButton.setText("");

            if(!running) {
                try {
                    react.cancel(true);
                }catch(IllegalStateException e) {}

                running = true;
                myButton.setBackgroundColor(getColor(R.color.black));
                System.out.println("Black");

                reactionTime = System.currentTimeMillis() - reactionTime;
                reactionTimeList.add((double)reactionTime / 1000);
                System.out.println(reactionTimeList.get(reactionTimeList.size() - 1));
                if(!missState) {
                    hit.setText("Hits = " + hitNum++);
                }
                new WaitTask().execute();
            }else  {
                if(System.currentTimeMillis() - reactionTime < 3000 && missState) {
                    System.out.println("Busy");
                    miss.setText("Misses = " + (--missNum));
                    missState = false;
                }else {
                    System.out.println("False Start");
                    falseStart.setText("False Starts = " + (++fsNum));
                }
            }
        }
    }

    private class WaitTask extends AsyncTask<Void, Void, Integer> {

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
            } catch (InterruptedException e) {
                System.out.println("UNEXPECTED INTERRUPTION");
            }

            System.out.println("White");
            return getColor(R.color.white);
        }

        @Override
        protected void onPostExecute(Integer color) {
            myButton.setBackgroundColor(color);
            reactionTime = System.currentTimeMillis();
            running = false;
            react = new ReactionTest();
            react.execute(reactionTime);
        }
    }

    private class ReactionTest extends AsyncTask<Long, Void, Long> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Long doInBackground(Long... time) {
            missState = false;
            try {
                Thread.sleep(3000);
            }catch(InterruptedException e) {}

            return time[0];
        }

        @Override
        protected void onPostExecute(Long time) {
            long current = System.currentTimeMillis() - time;
            System.out.println("Stopped " + current);
            miss.setText("Misses = " + (++missNum));
            missState = true;
            reactionTime = 0;
            myButton.performClick();
        }
    }
}
