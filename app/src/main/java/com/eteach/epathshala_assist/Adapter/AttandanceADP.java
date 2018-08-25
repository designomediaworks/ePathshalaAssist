package com.eteach.epathshala_assist.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eteach.epathshala_assist.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by shree on 08/09/2017.
 */

public class AttandanceADP extends RecyclerView.Adapter<AttandanceADP.MyViewHolder>{

    private Context context;
    List<String> dataAttandence = Collections.emptyList();
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    private CompactCalendarView compactCalendarView ;

    public AttandanceADP (Context _context, List <String> _dataAttandence)
    {
            this.context = _context;
            this.dataAttandence = _dataAttandence;
    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attandences2,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder (final MyViewHolder holder, int position) {
        holder.calendarView.setUseThreeLetterAbbreviation(false);
        holder.calendarView.setFirstDayOfWeek(Calendar.MONDAY);
        //holder.calendarView.setDate(Long.parseLong(dataAttandence.get(position)),false,false);
        Event ev1 = new Event(Color.GREEN, Long.parseLong(dataAttandence.get(position)),"Some extra data that I want to store.");
        holder.calendarView.addEvent(ev1);
        holder.textView2.setText(dateFormatForMonth.format(holder.calendarView.getFirstDayOfCurrentMonth()));
        holder.calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick (Date dateClicked) {

            }
            @Override
            public void onMonthScroll (Date firstDayOfNewMonth) {
            holder.textView2.setText(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });
    }

    @Override
    public int getItemCount ( ) {
        return dataAttandence.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textView,textView2,textView3;
        public ImageView imageView;
        public CompactCalendarView calendarView;
        public MyViewHolder (View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.txt_event_name);
            textView2 = (TextView) itemView.findViewById(R.id.txt_dayofmonth);
            calendarView = (CompactCalendarView) itemView.findViewById(R.id.compactCalendarView);
        }
    }
}

