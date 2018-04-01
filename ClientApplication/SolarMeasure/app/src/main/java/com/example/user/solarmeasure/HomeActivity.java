package com.example.user.solarmeasure;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;
import java.util.Random;

import me.itangqi.waveloadingview.WaveLoadingView;

public class HomeActivity extends AppCompatActivity {

    WaveLoadingView mWaveLoadingView;
    TextView tv_charge, tv_discharge;
    private LottieAnimationView animationView1, animationView2;
    CardView cv_charge, cv_discharge;
    int progress = 0;
    int reserve = 200;
    int capacity, uid;
    String name;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        name = prefs.getString("name", null);
        uid = prefs.getInt("uid", 0);
        capacity = prefs.getInt("capacity", 0);

        mWaveLoadingView = findViewById(R.id.waveLoadingView);
        animationView1 = findViewById(R.id.animation_view1);
        animationView2 = findViewById(R.id.animation_view2);
        tv_charge = findViewById(R.id.tv_charge);
        tv_discharge = findViewById(R.id.tv_discharge);
        cv_charge = findViewById(R.id.cv_charge);
        cv_discharge = findViewById(R.id.cv_discharge);
        final Random rand = new Random();
        dbHandler = new MyDBHandler(HomeActivity.this, null);

        progress = (reserve * 100) / capacity;
        mWaveLoadingView.setProgressValue(progress);
        mWaveLoadingView.setAnimDuration(3000);

        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f).setDuration(2000); //Slowing down the animation
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                animationView1.setProgress((Float) valueAnimator.getAnimatedValue());
                animationView2.setProgress((Float) valueAnimator.getAnimatedValue());
            }
        });
        animator.start();

        new Thread(new Runnable() { //Creating thread to run the updates
            @Override
            public void run() {
                while(true){ //Loop forever
                    new getInfo().execute(30 + rand.nextInt(71), 50 + rand.nextInt(61)); //Creating random sensor data
                    SystemClock.sleep(2000);
                }
            }
        }).start();

        cv_charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, GraphActivity.class);
                i.putExtra("charge", true);
                startActivity(i);
            }
        });

        cv_discharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, GraphActivity.class);
                i.putExtra("charge", false);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        for(int i = 0; i < menu.size(); i++){
            Drawable drawable = menu.getItem(i).getIcon();
            if(drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    class getInfo extends AsyncTask<Integer, Void, String> {
        float mCharge, mDischarge;
        @Override
        protected String doInBackground(Integer... values) {
            try {
                mCharge = values[0];
                mDischarge = values[1];
                return new NetworkManager().getDataFromURL(new URL("http://192.168.43.36/powerGrid/collect.php?id="+uid+"&uinfo="+ name +"&charge="+values[0]+"&discharge="+ values[1]+"&reserve="+reserve+"&capacity="+capacity));
            } catch (IOException e) {
                return "NAN";
            }
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(!s.equals("NAN")) {
                Gson parser = new Gson();
                //Toast.makeText(HomeActivity.this, s, Toast.LENGTH_SHORT).show();
                System.out.println(s);
                PwrInfo info = parser.fromJson(s, PwrInfo.class);
                //String[] x = s.split("\\,");
                float charge;
                float discharge = mDischarge;
                if (reserve >= capacity) {
                    charge = 0;
                } else {
                    charge = mCharge + info.redirected;
                }
                reserve = reserve + (int) charge - (int) discharge;
                if (reserve >= capacity) {
                    reserve = capacity;
                } else if (reserve < 0) reserve = 0;
                tv_charge.setText(String.valueOf(charge));
                tv_discharge.setText(String.valueOf(discharge));
                progress = (reserve * 100) / capacity;
                Log.i("HMMM", String.valueOf(progress));
                mWaveLoadingView.setProgressValue(progress);
                mWaveLoadingView.setCenterTitle(String.valueOf(progress) + "%");
                dbHandler.addData((int) charge, (int) discharge);
            }
        }
    }

    class PwrInfo {
        int id;
        String uinfo;
        float redirected;
    }
}
