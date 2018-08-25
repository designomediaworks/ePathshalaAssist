package com.eteach.epathshala_assist.Adapter;

import android.content.Context;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eteach.epathshala_assist.R;
import com.eteach.epathshala_assist.Utility.Constants;
import com.eteach.epathshala_assist.dataset.TBL_REPORTCARD;
import com.eteach.epathshala_assist.dataset.TBL_TIMETABLE;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * Created by shree on 08/09/2017.
 */

public class TimetableADP extends RecyclerView.Adapter<TimetableADP.MyViewHolder>{

    private Context context;
    List<TBL_TIMETABLE> timetableslList = Collections.emptyList();

    public
    TimetableADP ( Context _context, List<TBL_TIMETABLE> _datatimetable)
    {
            this.context = _context;
            this.timetableslList = _datatimetable;
    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listtimetable,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder (MyViewHolder holder, int position) {

        holder.txt_timetable_name.setText(timetableslList.get(position).get_AdmissionNo());
         holder.file = new File(Environment.getExternalStoragePublicDirectory (Environment.DIRECTORY_PICTURES),
                "/ePathshala/"+timetableslList.get(position).get_AdmissionNo()+ " Timetable " +"_"+ Constants.STUDENT_CLASS_ID+".pdf");
        if (holder.file.exists())
        holder.iv_download_timetable.setImageResource(R.drawable.ic_cloud_done_black_24dp);
    }

    @Override
    public int getItemCount ( ) {
        return timetableslList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
    public TextView txt_timetable_name;
        public ImageView iv_download_timetable;
        public File file;

        public MyViewHolder (View itemView) {
            super(itemView);
            txt_timetable_name = (TextView)itemView.findViewById(R.id.txt_timetable_name);
            iv_download_timetable = (ImageView) itemView.findViewById(R.id.iv_download_timetable);
        }
    }
}
