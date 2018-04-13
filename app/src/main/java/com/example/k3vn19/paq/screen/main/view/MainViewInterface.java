package com.example.k3vn19.paq.screen.main.view;

import com.example.k3vn19.paq.common.Enums;
import com.example.k3vn19.paq.common.ViewBaseInterface;

/**
 * Created by k3vn19 on 1/29/2018.
 *
 * Interface for MVC of main.
 */

public interface MainViewInterface extends ViewBaseInterface {

    interface MainViewListener{
        void tabChanged(Enums.TabPosition position);
        void createNewAlarm();
    }
}
