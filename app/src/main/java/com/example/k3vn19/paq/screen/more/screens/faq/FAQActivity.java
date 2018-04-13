package com.example.k3vn19.paq.screen.more.screens.faq;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

/**
 * Created by k3vn19 on 2/5/2018.
 *
 * Controller for FAQ package. This package is for the Frequently Asked Questions page.
 */

public class FAQActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FAQView mView = new FAQView(LayoutInflater.from(this), null);

        setContentView(mView.getRootView());
    }
}
