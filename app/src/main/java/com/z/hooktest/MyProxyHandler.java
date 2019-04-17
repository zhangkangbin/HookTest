package com.z.hooktest;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * User: zhangkb
 * Date: 2019/3/17 0017
 * Time: 下午 3:43
 * 动态代理
 *
 * @author zhangkb
 */
public class MyProxyHandler implements InvocationHandler {


    private Object objectProxy;
    private Context context;
    private Class<?> cls;

    public MyProxyHandler(Object objectProxy, Context context) {
        this.objectProxy = objectProxy;
        this.context = context;

    }
    public void setCls(Class<?> cls) {
        this.cls = cls;
    }

    /**
     * 动态代理
     * @hide
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


        if ("startActivity".equals(method.getName())&&cls!=null) {
            Log.d("mytest", "-------start-----------------做点什么？");
            // 取出在真实的Intent
            Intent intent = null;
            for (Object object1 : args) {
                if (object1 instanceof Intent) {
                    intent = (Intent) object1;
                    break;
                }

            }

            if (intent != null) {
                intent.setClassName(context.getPackageName(), cls.getName());
            }

        }

        Object result = method.invoke(objectProxy, args);
        Log.d("mytest", "--------------end----------做点什么？");
        return result;
    }
}
