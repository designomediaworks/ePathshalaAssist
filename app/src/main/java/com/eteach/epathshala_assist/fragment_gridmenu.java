package com.eteach.epathshala_assist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by shree on 02/08/2017.
 */

public class fragment_gridmenu extends BaseAdapter {
    private Context mcontext;
    private final String [] web;
    private final int[] imgaeid;

    public fragment_gridmenu (Context mcontext, String[] web, int[] imagid) {
        this.mcontext = mcontext;
        this.imgaeid=imagid;
        this.web = web;
    }

    @Override
    public int getCount() {
        return web.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater =    (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            grid = new View(mcontext);
            grid = inflater.inflate( R.layout.fragment_home_screen_menu, null);
            //TextView textView = (TextView) grid.findViewById(R.id.grid_text);
            ImageView imageView = (ImageView) grid.findViewById(R.id.grid_image);
            //textView.setText(web[position]);
            imageView.setImageResource(imgaeid[position]);
        }else {
            grid = (View) convertView;
        }


        return grid;
    }
}
