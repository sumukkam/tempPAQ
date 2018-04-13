package com.example.k3vn19.paq.screen.main.view;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.k3vn19.paq.R;
import com.example.k3vn19.paq.common.ContextManager;
import com.example.k3vn19.paq.common.Enums;
import com.example.k3vn19.paq.screen.main.CustomViewPager;
import com.example.k3vn19.paq.screen.main.MainViewPageAdapter;
import com.example.k3vn19.paq.screen.more.screens.bluetoothFinal.BlunoLibrary;


/**
 * Created by k3vn19 on 1/29/2018.
 *
 * View for Main.
 */

public class MainView implements MainViewInterface, ViewPager.OnPageChangeListener {

    private View mRootView;
    private MainViewInterface.MainViewListener mListener;
    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    private Toolbar toolbar;
    private View tabAlarms;
    private View tabMore;
    private View tabStopwatch;
    private View tabTimer;
    private TextView addAlarm;



    public MainView(LayoutInflater inflater, ViewGroup container, MainViewListener listener){
        mRootView = inflater.inflate(R.layout.view_main, container, false);
        this.mListener = listener;


        initialize(inflater);
    }

    /**
     * Purpose - Initialize all the member variables.
     * @param inflater - Inflater to inflate view_main.xml.
     */
    private void initialize(LayoutInflater inflater){

        tabLayout = (TabLayout)mRootView.findViewById(R.id.tab_layout);
        viewPager = (CustomViewPager)mRootView.findViewById(R.id.view_pager);
        toolbar = (Toolbar)mRootView.findViewById(R.id.toolbar);

        //Textview "button"
        addAlarm = (TextView)mRootView.findViewById(R.id.main_add_alarm_icon);
        addAlarm.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                createAlarm();
            }
        });

        tabAlarms = inflater.inflate(R.layout.tab, null);
        tabMore = inflater.inflate(R.layout.tab, null);
        tabStopwatch = inflater.inflate(R.layout.tab, null);
        tabTimer = inflater.inflate(R.layout.tab, null);
    }

    /**
     * Purpose - Sets up tabs to link with corresponding fragments.
     * @param fragments -
     * @param tabTitles -
     * @param defaultIndex -
     * @param fm -
     */
    public void setUpTabs(Fragment[] fragments, String[] tabTitles, int defaultIndex, FragmentManager fm) {
        MainViewPageAdapter viewPagerAdapter = new MainViewPageAdapter(fm, fragments, tabTitles);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(this);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setBackgroundColor(ContextCompat.getColor(ContextManager.getInstance().getApplicationContext(),R.color.primaryGrey));

        tabLayout.getTabAt(Enums.TabPosition.ALARMS.getVal()).setCustomView(tabAlarms);
        tabLayout.getTabAt(Enums.TabPosition.MORE.getVal()).setCustomView(tabMore);
        tabLayout.getTabAt(Enums.TabPosition.STOPWATCH.getVal()).setCustomView(tabStopwatch);
        tabLayout.getTabAt(Enums.TabPosition.TIMER.getVal()).setCustomView(tabTimer);

        ((ImageView) tabAlarms.findViewById(R.id.tab_icon)).setImageResource(R.drawable.alarm_yellow);
        ((ImageView) tabMore.findViewById(R.id.tab_icon)).setImageResource(R.drawable.more_white);
        ((ImageView) tabStopwatch.findViewById(R.id.tab_icon)).setImageResource(R.drawable.stopwatch_white);
        ((ImageView) tabTimer.findViewById(R.id.tab_icon)).setImageResource(R.drawable.timer_white);

        tabLayout.getTabAt(defaultIndex).select();
    }

    @Override
    public View getRootView() {
        return mRootView;
    }

    public Toolbar getToolbar() {return toolbar;}


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    /**
     * Purpose - When a new page is seleced in the tabs, change color of tab image and if page
     *           selected isn't "Alarms" hide the "add alarm" button in the toolbar.
     */
    @Override
    public void onPageSelected(int position) {

        Enums.TabPosition positionEnum = Enums.TabPosition.ALARMS;

        //prevents transition animation between page
        viewPager.setCurrentItem(position,false);


        if(position == Enums.TabPosition.ALARMS.getVal()) {
            ((ImageView) tabAlarms.findViewById(R.id.tab_icon)).setImageResource(R.drawable.alarm_yellow);
            ((ImageView) tabMore.findViewById(R.id.tab_icon)).setImageResource(R.drawable.more_white);
            ((ImageView) tabStopwatch.findViewById(R.id.tab_icon)).setImageResource(R.drawable.stopwatch_white);
            ((ImageView) tabTimer.findViewById(R.id.tab_icon)).setImageResource(R.drawable.timer_white);

            positionEnum = Enums.TabPosition.ALARMS;
            addAlarm.setVisibility(View.VISIBLE); //display add alarm button since on "Alarms" tab

        } else if(position == Enums.TabPosition.MORE.getVal()) {
            ((ImageView) tabAlarms.findViewById(R.id.tab_icon)).setImageResource(R.drawable.alarm_white);
            ((ImageView) tabMore.findViewById(R.id.tab_icon)).setImageResource(R.drawable.more_yellow);
            ((ImageView) tabStopwatch.findViewById(R.id.tab_icon)).setImageResource(R.drawable.stopwatch_white);
            ((ImageView) tabTimer.findViewById(R.id.tab_icon)).setImageResource(R.drawable.timer_white);

            positionEnum = Enums.TabPosition.MORE;
            addAlarm.setVisibility(View.INVISIBLE); //hide add alarm button


        } else if(position == Enums.TabPosition.STOPWATCH.getVal()) {
            ((ImageView) tabAlarms.findViewById(R.id.tab_icon)).setImageResource(R.drawable.alarm_white);
            ((ImageView) tabMore.findViewById(R.id.tab_icon)).setImageResource(R.drawable.more_white);
            ((ImageView) tabStopwatch.findViewById(R.id.tab_icon)).setImageResource(R.drawable.stopwatch_yellow);
            ((ImageView) tabTimer.findViewById(R.id.tab_icon)).setImageResource(R.drawable.timer_white);

            positionEnum = Enums.TabPosition.STOPWATCH;
            addAlarm.setVisibility(View.INVISIBLE); //hide add alarm button


        } else if(position == Enums.TabPosition.TIMER.getVal()) {
            ((ImageView) tabAlarms.findViewById(R.id.tab_icon)).setImageResource(R.drawable.alarm_white);
            ((ImageView) tabMore.findViewById(R.id.tab_icon)).setImageResource(R.drawable.more_white);
            ((ImageView) tabStopwatch.findViewById(R.id.tab_icon)).setImageResource(R.drawable.stopwatch_white);
            ((ImageView) tabTimer.findViewById(R.id.tab_icon)).setImageResource(R.drawable.timer_yellow);

            positionEnum = Enums.TabPosition.TIMER;
            addAlarm.setVisibility(View.INVISIBLE);  //hide the add alarm button


        }

        mListener.tabChanged(positionEnum);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * Purpose - Set the text of the Toolbar Title
     * @param title - Title to set the ToolBar with
     */
    public void setToolbarTitle(String title) {
        ((TextView)toolbar.findViewById(R.id.toolbar_title_textview)).setText(title);
    }

    /**
     * Purpose - When TextView "Button" is pressed this method is called, which calls the listener's
     *           create new alarm method to handle the logic in the controller, MainActivity.
     */
    private void createAlarm(){
        mListener.createNewAlarm();
    }


} //end of class
