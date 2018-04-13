package com.example.k3vn19.paq.common;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * Created by k3vn19 on 12/19/2017.
 *
 */

//singleton class for get application class.  should not use if you want activity context.
//onCreateApplication should be called in Application class. (UtilCommon class)
public class ContextManager {

    @SuppressLint("StaticFieldLeak")
    private static ContextManager singleton = null;
    private Context applicationContext;

    static void onCreateApplication(Context applicationContext) {
        singleton = new ContextManager(applicationContext);
    }

    private ContextManager(Context applicationContext){
        this.applicationContext = applicationContext;
    }

    public static ContextManager getInstance(){
        if (singleton == null) {
            throw new RuntimeException("ContextManager should be initialized!");
        }
        return singleton;
    }

    public Context getApplicationContext() {
        return applicationContext;
    }
}
