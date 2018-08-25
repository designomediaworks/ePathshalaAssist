package com.eteach.epathshala_assist.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.eteach.epathshala_assist.R;

import java.util.ArrayList;

/**
 * Created by shree on 06-03-2018.
 */

public class HomeworkADP_unused extends BaseExpandableListAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Object> childtems;
    private ArrayList<String> parentItems, child;
    public
    HomeworkADP_unused ( ArrayList <String> homewrok_header, ArrayList <Object> homework_child_data, Context _context, LayoutInflater _inflater ) {
        this.context = _context;
        this.inflater = _inflater;
        this.parentItems = homewrok_header;
        this.childtems = homework_child_data;
    }

    @Override
    public int getGroupCount ( ) {
        return parentItems.size ();
    }

    @Override
    public int getChildrenCount ( int groupPosition ) {
        return ((ArrayList<String>) childtems.get ( groupPosition )).size ();
    }

    @Override
    public Object getGroup ( int groupPosition ) {
        return null;
    }

    @Override
    public Object getChild ( int groupPosition, int childPosition ) {
        return null;
    }

    @Override
    public long getGroupId ( int groupPosition ) {
        return 0;
    }

    @Override
    public long getChildId ( int groupPosition, int childPosition ) {
        return 0;
    }

    @Override
    public boolean hasStableIds ( ) {
        return false;
    }

    @Override
    public View getGroupView ( int groupPosition, boolean isExpanded, View convertView, ViewGroup parent ) {
        String h = parentItems.get ( groupPosition );
        if ( convertView == null )
        {
            convertView = inflater.inflate ( R.layout.header ,null);
        }
        return convertView;
    }

    @Override
    public View getChildView ( int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent ) {
       child =(ArrayList<String>) childtems.get ( groupPosition );
       String c = child.get ( childPosition );
       if ( convertView == null )
       {
           convertView = inflater.inflate ( R.layout.childes ,null);
       }
        return convertView;
    }

    @Override
    public boolean isChildSelectable ( int groupPosition, int childPosition ) {
        return false;
    }
}
