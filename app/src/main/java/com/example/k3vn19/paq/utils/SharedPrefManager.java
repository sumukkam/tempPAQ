package com.example.k3vn19.paq.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.k3vn19.paq.common.ContextManager;

/**
 * Created by k3vn19 on 12/19/2017.
 *
 *
 */


public class SharedPrefManager {

    private static SharedPreferences prefs =  PreferenceManager.getDefaultSharedPreferences(ContextManager.getInstance().getApplicationContext());
    private static SharedPreferences.Editor editor = prefs.edit();

    private final static String keyForIsFirstLaunch = "isFirstLaunch";
    private final static String keyForRealDisplaySizeX = "realDisplaySizeX";
    private final static String keyForRealDisplaySizeY = "realDisplaySizeY";


    //return true and set false if first launch.
    public static  boolean loadIsFirstLaunch() {
        if(prefs.getBoolean(keyForIsFirstLaunch, true)) {
            saveIsFirstLaunch(false);
            return true;
        }
        return false;
    }

    private static void saveIsFirstLaunch(boolean val) {
        editor = prefs.edit();
        editor.putBoolean(keyForIsFirstLaunch,val);
        editor.apply();
    }


    public static int loadRealDisplaySizeX() {
        return prefs.getInt(keyForRealDisplaySizeX,0);
    }

    public static void saveRealDisplaySizeX(int size) {
        editor = prefs.edit();
        editor.putInt(keyForRealDisplaySizeX,size);
        editor.apply();
    }

    public static int loadRealDisplaySizeY() {
        return prefs.getInt(keyForRealDisplaySizeY,0);
    }

    public static void saveRealDisplaySizeY(int size) {
        editor = prefs.edit();
        editor.putInt(keyForRealDisplaySizeY,size);
        editor.apply();
    }
}
