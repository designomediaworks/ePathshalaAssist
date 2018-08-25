package com.eteach.epathshala_assist.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.eteach.epathshala_assist.MainActivity;
import com.eteach.epathshala_assist.R;

import java.io.File;
import java.util.List;

/**
 * Created by shree on 08/09/2017.
 */

public class EventsPhotosADP extends RecyclerView.Adapter<EventsPhotosADP.MyViewHolder>{

    private Context _context;
    private List<File> _path;
    private String _eventFolder;

    public EventsPhotosADP (FragmentActivity activity, List<File> path, String eventpath) {
        this._context = activity;
        this._path = path;
        this._eventFolder = eventpath;
    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listeventsphotos,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder (MyViewHolder holder, int position) {
        final File file = new File( Environment.getExternalStoragePublicDirectory ( Environment.DIRECTORY_PICTURES),
                                    "/ePathshala/EventView/"+_eventFolder+"/"+_path.get(position).getName());
        Glide.with(_context).load(file).into(holder.imageView);
        ((MainActivity) _context).setToolbarTitlefromfragment ( _eventFolder);
        holder.mRelativeLayout.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public
            void onClick ( View v ) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType( Uri.fromFile ( file ) , "image/*");
                _context.startActivity(intent);
            }
        } );
    }

    @Override
    public int getItemCount ( ) {
        return _path.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public
        RelativeLayout mRelativeLayout;
        public MyViewHolder (View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.iv_event_photos);
            mRelativeLayout = (RelativeLayout)itemView.findViewById ( R.id.eventsphotoslistview );
        }
    }
}
