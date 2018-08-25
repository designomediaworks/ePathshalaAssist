package com.eteach.epathshala_assist.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eteach.epathshala_assist.R;

/**
 * Created by shree on 08/09/2017.
 */

public class AssignmentADP_unused extends RecyclerView.Adapter<AssignmentADP_unused.MyViewHolder>{

    private Context context;


    public
    AssignmentADP_unused ( Context _context)
    {
            this.context = _context;

    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listevents,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder (MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount ( ) {
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textView,textView2,textView3;
        public ImageView imageView;
        public MyViewHolder (View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.txt_event_name);
            textView2 = (TextView)itemView.findViewById(R.id.txt_event_date);
            textView3 = (TextView)itemView.findViewById(R.id.txt_event_disc);
            imageView = (ImageView)itemView.findViewById(R.id.iv_event_thumb);
        }
    }
}
