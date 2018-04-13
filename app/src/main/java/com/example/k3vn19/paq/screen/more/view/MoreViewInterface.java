package com.example.k3vn19.paq.screen.more.view;

import com.example.k3vn19.paq.common.ViewBaseInterface;

/**
 * Created by k3vn19 on 2/5/2018.
 *
 */

public interface MoreViewInterface extends ViewBaseInterface {

    interface MoreViewListener{
        void goToAbout();
        void goToConnectDevice();
        void goToFAQ();
        void goToReport();
    }
}
