package com.example.k3vn19.paq.screen.timer.screens.timerInProgress.view;

import com.example.k3vn19.paq.common.ViewBaseInterface;

/**
 * Created by k3vn19 on 2/26/2018.
 *
 * Listener for TimerInProgress MVC.
 */

public interface TimerIPViewInterface extends ViewBaseInterface {

    interface TimerIPViewListener {
        void cancelTimer();
    }
}
