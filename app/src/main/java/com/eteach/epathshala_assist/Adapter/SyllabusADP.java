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
import com.eteach.epathshala_assist.dataset.TBL_SYLLABUS;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * Created by shree on 08/09/2017.
 */

public class SyllabusADP extends RecyclerView.Adapter<SyllabusADP.MyViewHolder>{

    private Context context;
    List<TBL_SYLLABUS> syllabusList = Collections.emptyList();

    public
    SyllabusADP ( Context _context, List<TBL_SYLLABUS> _dataSyllabus)
    {
            this.context = _context;
            this.syllabusList = _dataSyllabus;
    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listsyllabuscard,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder (MyViewHolder holder, int position) {

        holder.txt_syllabus_name.setText(syllabusList.get(position).get_SyllabusTitle ());
         holder.file = new File(Environment.getExternalStoragePublicDirectory (Environment.DIRECTORY_PICTURES),
                "/ePathshala/"+syllabusList.get(position).get_SyllabusTitle ()+ " Syllabus " +"_"+ Constants.STUDENT_CLASS_ID+".pdf");
        if (holder.file.exists())
        holder.iv_download_syllabus.setImageResource(R.drawable.ic_cloud_done_black_24dp);
    }

    @Override
    public int getItemCount ( ) {
        return syllabusList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
    public TextView txt_syllabus_name;
        public ImageView iv_download_syllabus;
        public File file;

        public MyViewHolder (View itemView) {
            super(itemView);
            txt_syllabus_name = (TextView)itemView.findViewById(R.id.txt_syllabus_name);
            iv_download_syllabus = (ImageView) itemView.findViewById(R.id.iv_download_syllabus);
        }
    }
}
