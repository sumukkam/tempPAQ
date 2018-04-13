package com.example.k3vn19.paq.common;

/**
 * Created by k3vn19 on 1/29/2018.
 *
 * Enums used for Tabs.
 */

public class Enums {

    public enum TabPosition{
        ALARMS(0),
        TIMER(1),
        STOPWATCH(2),
        MORE(3);

        private final int val;
        TabPosition(final int val){ this.val = val;}

        public int getVal(){ return this.val;}

    }
}
