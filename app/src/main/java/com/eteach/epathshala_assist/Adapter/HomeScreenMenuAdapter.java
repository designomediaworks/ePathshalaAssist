package com.eteach.epathshala_assist.Adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public
class HomeScreenMenuAdapter extends BaseAdapter {
    private
    Context mContext;
    private final String[] gridValues ;

    public
    HomeScreenMenuAdapter ( Context mContext, String[] gridValues ) {
        this.mContext = mContext;
        this.gridValues = gridValues;
    }

    @Override
    public
    int getCount ( ) {
        return gridValues.length;
    }

    @Override
    public
    Object getItem ( int position ) {
        return null;
    }

    @Override
    public
    long getItemId ( int position ) {
        return 0;
    }

    @Override
    public
    View getView ( int position, View convertView, ViewGroup parent ) {
        return null;
    }
}
