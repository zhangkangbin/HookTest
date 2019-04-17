package com.z.hooktest;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


/**
 * @author kang
 * @date 2019/4/17 0017
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestActivity.class));
            }
        });


        findViewById(R.id.hookBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, UnRegisterActivity.class));
            }
        });


        findViewById(R.id.AllSetBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //全局都跳转到  HookActivity。
                MyApp.get().getMyProxyHandler().setCls(HookActivity.class);
            }
        });


    }
}
