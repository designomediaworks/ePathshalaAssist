package com.eteach.epathshala_assist.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eteach.epathshala_assist.R;
import com.eteach.epathshala_assist.dataset.TBL_TOPPERS;

import java.util.List;
import java.util.Random;

/**
 * Created by shree on 08/09/2017.
 */

public class ToppersADP extends RecyclerView.Adapter<ToppersADP.MyViewHolder>{

    private Context context;
    private List<TBL_TOPPERS> tbl_toppersArrayList;
    int colorcode;
    View view;

    public ToppersADP (FragmentActivity activity, List<TBL_TOPPERS> toppersList) {
        this.context = activity;
        this.tbl_toppersArrayList = toppersList;
    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
         view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listtoppers,parent,false);
        return new MyViewHolder(view);
    }
    private  void viewcolorchange()
    {
        Random random = new Random();
        colorcode = Color.argb(150,random.nextInt(151),random.nextInt(151),random.nextInt(151));
        view.setBackgroundColor(colorcode);
    }
    @Override
    public void onBindViewHolder (MyViewHolder holder, int position) {
            viewcolorchange();
            holder.textView.setText(tbl_toppersArrayList.get(position).get_STUDENT_NAME());
            holder.textView2.setText(tbl_toppersArrayList.get(position).get_CLASS());
            holder.textView3.setText(tbl_toppersArrayList.get(position).get_MARKS());
    }

    @Override
    public int getItemCount ( ) {
        return tbl_toppersArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textView,textView2,textView3;
        public MyViewHolder (View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.txt_paid_amount_summary);
            textView2 = (TextView)itemView.findViewById(R.id.txt_topperin_class);
            textView3 = (TextView)itemView.findViewById(R.id.txt_topper_rank);

        }
    }
}
