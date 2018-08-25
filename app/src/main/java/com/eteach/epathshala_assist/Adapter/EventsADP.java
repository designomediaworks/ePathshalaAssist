package com.eteach.epathshala_assist.Adapter;

import android.content.Context;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eteach.epathshala_assist.R;
import com.eteach.epathshala_assist.dataset.TBL_EVENTS;

import java.io.File;
import java.util.List;

/**
 * Created by shree on 08/09/2017.
 */

public class EventsADP extends RecyclerView.Adapter<EventsADP.MyViewHolder>{

    private Context context;
    private List<TBL_EVENTS> tblEventsArrayList;

    public EventsADP (FragmentActivity activity, List<TBL_EVENTS> eventarrayList) {
        this.context = activity;
        this.tblEventsArrayList = eventarrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listevents,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder (MyViewHolder holder, int position) {

            holder.textView.setText(tblEventsArrayList.get(position).get_EVENT_NAME());
            holder.textView2.setText(tblEventsArrayList.get(position).get_EVENT_TIME());
            holder.textView3.setText(tblEventsArrayList.get(position).get_EVENT_DISCREAPTION());

        File file = new File( Environment.getExternalStoragePublicDirectory (Environment.DIRECTORY_PICTURES),
                "/ePathshala/EventView/"+tblEventsArrayList.get(position).get_EVENT_NAME()+"/"+tblEventsArrayList.get(position).get_EVENT_NAME()+"_0.jpg");
        Glide.with(context).load(file).into(holder.imageView);
    }

    @Override
    public int getItemCount ( ) {
        return tblEventsArrayList.size();
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
