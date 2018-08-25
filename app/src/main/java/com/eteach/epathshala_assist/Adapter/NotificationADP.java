package com.eteach.epathshala_assist.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eteach.epathshala_assist.R;
import com.eteach.epathshala_assist.dataset.TBL_NOTI;

import java.util.List;

/**
 * Created by shree on 08/09/2017.
 */

public class NotificationADP extends RecyclerView.Adapter<NotificationADP.MyViewHolder>{

    private Context context;
    private List<TBL_NOTI> tblNotiList;

    public NotificationADP (FragmentActivity activity, List<TBL_NOTI> _tblNotiList)
    {
            this.context = activity;
            this.tblNotiList = _tblNotiList;
    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listnotification,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder (MyViewHolder holder, int position) {

        try {
            holder.txtnotificationtitle.setText(tblNotiList.get(position).getTitle());
            holder.txtnotification.setText(tblNotiList.get(position).getMessage());
            holder.txttimestamp.setText(tblNotiList.get(position).get_timestamp());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount ( ) {
        return tblNotiList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView txtnotificationtitle,txtnotification,txttimestamp;
        public MyViewHolder (View itemView) {
            super(itemView);
            txtnotificationtitle = (TextView) itemView.findViewById(R.id.txt_paid_amount_summary);
            txtnotification = (TextView)itemView.findViewById(R.id.txt_topperin_class);
            txttimestamp = (TextView)itemView.findViewById(R.id.txt_notification_timestamp);
        }
    }
}
