package com.example.k3vn19.paq.common;

import android.app.Application;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.example.k3vn19.paq.utils.SharedPrefManager;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by k3vn19 on 12/21/2017.
 *
 */

public class UtilCommon extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        ContextManager.onCreateApplication(getApplicationContext());
        JodaTimeAndroid.init(this);
        getDisplaySize();
    }


    private void getDisplaySize() {
        WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
        if(wm != null) {
            Display disp = wm.getDefaultDisplay();

            Point realSize = new Point();
            disp.getRealSize(realSize);
            SharedPrefManager.saveRealDisplaySizeX(realSize.x);
            SharedPrefManager.saveRealDisplaySizeY(realSize.y);
        }
    }
}
