package com.example.mainactivity.launcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.example.mainactivity.MainActivity;
import com.example.mainactivity.R;

public class LauncherActivity extends AppCompatActivity {
    public static final String FIRST_LAUNCHER="first launcher";
    private final long waitTime=1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_launcher);
        start();
    }
    public void start(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (isFirstLauncher()){
                    intent=new Intent(LauncherActivity.this, MainActivity.class);
                }else{
                    intent=new Intent(LauncherActivity.this,FirstLaunchActivity.class);
                }
                startActivity(intent);
                finish();
            }
        },waitTime);
    }

    /*
    * false:第一次启动，true:第二次启动，这里不能用Activity的getPreferences方法，
    * 因为需要多个Activity使用一个SharedPreference对象，所以调用getSharedPreferences方法。
    * @return
    */

    public boolean isFirstLauncher(){
        SharedPreferences sp=getSharedPreferences("Launcher", Context.MODE_PRIVATE);
        return sp.getBoolean(FIRST_LAUNCHER,false);
    }

}