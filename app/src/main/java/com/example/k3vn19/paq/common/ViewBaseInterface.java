package com.example.k3vn19.paq.common;

import android.view.View;

/**
 * Created by k3vn19 on 1/29/2018.
 *
 * Purpose - Base interface for all further interfaces in Screen.
 */

public interface ViewBaseInterface {
    /**
     * Get the root Android View which is used internally by this MVC View for presenting data
     * to the user.
     * The returned Android View might be used by an MVC Controller in order to query or alter the
     * properties of either the root Android View itself, or any of its child Android View's.
     * @return root Android View of this MVC View
     */
    View getRootView();

}
