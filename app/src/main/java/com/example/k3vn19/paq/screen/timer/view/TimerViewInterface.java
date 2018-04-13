package com.example.k3vn19.paq.screen.timer.view;

import com.example.k3vn19.paq.common.ViewBaseInterface;

/**
 * Created by k3vn19 on 2/26/2018.
 *
 */

public interface TimerViewInterface extends ViewBaseInterface{

    interface TimerViewListener{
        void moveToInProgress(int hour, int minute);
    }
}
