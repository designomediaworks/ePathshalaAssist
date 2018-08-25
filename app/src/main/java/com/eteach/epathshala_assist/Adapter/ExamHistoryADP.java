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

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * Created by shree on 08/09/2017.
 */

public class ExamHistoryADP extends RecyclerView.Adapter<ExamHistoryADP.MyViewHolder>{

    private Context context;
    List<TBL_REPORTCARD> reportcardList = Collections.emptyList();

    public ExamHistoryADP (Context _context, List<TBL_REPORTCARD> _dataEvents)
    {
            this.context = _context;
            this.reportcardList = _dataEvents;
    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listexamreportcard,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder (MyViewHolder holder, int position) {

        holder.txt_report_card_title.setText(reportcardList.get(position).get_AdmissionNo());
         holder.file = new File(Environment.getExternalStoragePublicDirectory (Environment.DIRECTORY_PICTURES),
                "/ePathshala/"+reportcardList.get(position).get_AdmissionNo()+"_"+ Constants.ADMISSION_NO+".pdf");
        if (holder.file.exists())
        holder.iv_download_reportcard.setImageResource(R.drawable.ic_cloud_done_black_24dp);
    }

    @Override
    public int getItemCount ( ) {
        return reportcardList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
    public TextView txt_report_card_title;
        public ImageView iv_download_reportcard;
        public File file;

        public MyViewHolder (View itemView) {
            super(itemView);
            txt_report_card_title = (TextView)itemView.findViewById(R.id.txt_exam_name);
            iv_download_reportcard = (ImageView) itemView.findViewById(R.id.iv_download_reportcard);
        }
    }
}
