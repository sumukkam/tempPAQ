package com.example.k3vn19.paq.screen.alarms.screens.alarmsMain.view;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.k3vn19.paq.R;
import com.example.k3vn19.paq.screen.alarms.screens.alarmsMain.AlarmsMainViewListAdapter;
import com.example.k3vn19.paq.utils.database.AlarmEntity;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by k3vn19 on 1/30/2018.
 *
 * Purpose - View for AlarmsMain, handles user input.
 */

public class AlarmsMainView implements AlarmsMainViewInterface, AdapterView.OnItemClickListener{

    private View mRootView;

    //private ListView listView;

    private SwipeMenuListView listView;

    private AlarmsMainViewListAdapter listViewAdapter;
    private AlarmsMainViewListener mListener;
    private Context context;

    //constructor
    public AlarmsMainView(LayoutInflater inflater, ViewGroup container, AlarmsMainViewListener listener,
                          Context context){
        mRootView = inflater.inflate(R.layout.view_alarms_list, container, false);
        listViewAdapter = new AlarmsMainViewListAdapter(inflater.getContext());

        this.context = context;
        this.mListener = listener;
        initialize();
    }

    /**
     * Purpose  Initialize the ListView
     */
    private void initialize(){
        listView = mRootView.findViewById(R.id.alarm_list);
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(this);


        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        mRootView.getContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(context.getResources().getColor(R.color.primaryRed)));
                // set item width
                deleteItem.setWidth(250);
                // set a icon
                deleteItem.setIcon(R.drawable.delete_icon);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Log.d("Delete clicked", "Delete. Index: " + index + " Position: " + position);
                        onSwipeDeleteClicked(position);
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }

    public void setUpListView(LinkedList<AlarmEntity> data){
        listViewAdapter.setData(data);
    }

    @Override
    public View getRootView(){
        return mRootView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
        mListener.listTap(i);
    }

    /**
     * Purpose- this method calls the model to delete alarm at given index.
     */
    public void onSwipeDeleteClicked(int i){
        mListener.deleteItem(i);
    }

    /**
     * Purpose - Call notifyDataSetChanged on the ListView
     */
    public void notifyChange(){
        listViewAdapter.notifyDataSetChanged();
    }
} //end of class
