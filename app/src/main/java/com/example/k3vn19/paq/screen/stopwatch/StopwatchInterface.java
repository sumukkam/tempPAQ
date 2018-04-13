package com.example.k3vn19.paq.screen.stopwatch;

import com.example.k3vn19.paq.common.ViewBaseInterface;

/**
 * Created by k3vn19 on 3/5/2018.
 *
 * Interface for stopwatch listener.
 */

public interface StopwatchInterface extends ViewBaseInterface {

    interface StopwatchListener{
        void startPressed();
        void resetPressed();
    }
}
