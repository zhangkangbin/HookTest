package com.z.hooktest;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

/**
 * @author kang
 * @date 2019/4/16 0016
 */
public class MyApp extends Application {

    private static MyApp myApp;
    private MyProxyHandler myProxyHandler;
    public static MyApp get() {
        return myApp;
    }
    public MyProxyHandler getMyProxyHandler() {
        return myProxyHandler;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;


        try {
            // 第一步：获取 ActivityManager
            Class<?> ativityManagerClass = Class.forName("android.app.ActivityManager");

            //IActivityManagerSingleton
            Field declaredField = ativityManagerClass.getDeclaredField("IActivityManagerSingleton");
            declaredField.setAccessible(true);

            // IActivityManager
            Object mIActivityManagerMy = declaredField.get(null);

            Log.d("mytest", "mIActivityManagerMy==" + mIActivityManagerMy.getClass().getName());

            // 第二获取单列对象
            Class<?> singletonClass = Class.forName("android.util.Singleton");

            Field singletonIActivityManagerInstanceField = singletonClass.getDeclaredField("mInstance");
            singletonIActivityManagerInstanceField.setAccessible(true);
            // 获取单列对象
            /**
             * 单列对象
             */
            Object singletonIActivityManagerObject = singletonIActivityManagerInstanceField.get(mIActivityManagerMy);

            Log.d("mytest", "singletonIActivityManagerObject==" + singletonIActivityManagerObject.getClass().getName());

            // 使用动态代理的方式

            myProxyHandler = new MyProxyHandler(singletonIActivityManagerObject, getApplicationContext());
            Object proxy = Proxy.newProxyInstance(getClassLoader(), singletonIActivityManagerObject.getClass().getInterfaces(), myProxyHandler);

            // 第三步： 替换成代理对象
            singletonIActivityManagerInstanceField.set(mIActivityManagerMy, proxy);
            /**
             *  int result = ActivityManager.getService()
             *                 .startActivity(whoThread, who.getBasePackageName(), intent,
             *                         intent.resolveTypeIfNeeded(who.getContentResolver()),
             *                         token, target != null ? target.mEmbeddedID : null,
             *                         requestCode, 0, null, options);
             */

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start(Context context, Class<?> cls) {

        try {
            Intent intent = new Intent(context, cls);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.d("mytest", e.getLocalizedMessage());
        }

    }
}
