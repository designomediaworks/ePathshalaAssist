package com.eteach.epathshala_assist.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eteach.epathshala_assist.R;
import com.eteach.epathshala_assist.Utility.Constants;
import com.eteach.epathshala_assist.Utility.homeworkDownloader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shree on 06-03-2018.
 */

public class HomeworkADP2 extends BaseExpandableListAdapter {
    private Context _context;
    private List<String> header; // header titles
    // Child data in format of header title, child title
    private HashMap <String, List <ArrayList <String>>> child;

    public HomeworkADP2 ( Context mContext, ArrayList<String> homewrok_header, HashMap <String, List <ArrayList <String>>> hashMap ) {
        this._context = mContext;
        this.header = homewrok_header;
        this.child = hashMap;
    }

    @Override
    public int getGroupCount ( ) {
        int count = header.size ();
        return count;
    }

    @Override
    public int getChildrenCount ( int groupPosition ) {
        int count = child.get ( header.get ( groupPosition ) ).get ( groupPosition ).size ();
        return count;
    }

    @Override
    public Object getGroup ( int groupPosition ) {
        return header.get ( groupPosition );
    }

    @Override
    public Object getChild ( int groupPosition, int childPosition ) {
        return child.get ( header.get ( groupPosition ) ).get ( groupPosition ).get ( childPosition );
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
        String headerTitle = (String) getGroup(groupPosition);

        // Inflating header layout and setting text
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate( R.layout.header, parent, false);
        }

        TextView header_text = (TextView) convertView.findViewById(R.id.txt_header_homework_subject);
        header_text.setText(headerTitle);

        // If group is expanded then change the text into bold and change the
        // icon
        if (isExpanded) {
            header_text.setTypeface(null, Typeface.BOLD);
            header_text.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_up, 0);
        } else {
            // If group is not expanded then change the text back into normal
            // and change the icon
            header_text.setTypeface(null, Typeface.NORMAL);
            header_text.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);
        }

        return convertView;
    }

    @Override
    public View getChildView ( int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent ) {
    try {
        Constants.navItemIndex = 11;
        String childText = (String) getChild ( groupPosition, childPosition );
        // Inflating child layout and setting textview
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.childes, parent, false);
        }
        TextView txt_homework_title = (TextView) convertView.findViewById(R.id.txt_childe_homework_title);
        TextView txt_homework_Description = (TextView) convertView.findViewById(R.id.txt_childe_homework_disc);
        TextView txt_homework_SDate = (TextView) convertView.findViewById(R.id.txt_childe_homework_submission_date);
        ImageView iv_photo_path = (ImageView)convertView.findViewById ( R.id.iv_homework_photo );
        Button btn_download_view = (Button )convertView.findViewById ( R.id.btn_download_view );
            JSONObject jsonObject2 = new JSONObject ( childText );
            txt_homework_title.setText ( jsonObject2.getString ( "V_Title" ) );
            txt_homework_Description.setText ( jsonObject2.getString ( "V_Description" )  );
            txt_homework_SDate.setText ( jsonObject2.getString ("submition date"  ) );
            String url = ( jsonObject2.getString ("v_path"  ) );
            url = url.replace ( "\\","/" );
            final String imagename = jsonObject2.getString ( "submition date"  );
         Glide.with ( _context ).load (  url)
                 .thumbnail ( Glide.with ( _context ).load ( R.drawable.wedges ) )
                    .into ( iv_photo_path );
        final String finalUrl = url;
         btn_download_view.setOnClickListener ( new View.OnClickListener ( ) {
             @Override
             public
             void onClick ( View v ) {
                 File file = new File ( Environment.getExternalStoragePublicDirectory ( Environment.DIRECTORY_PICTURES ), "ePathshala" );
                 if ( ! file.exists ( ) )
                     file.mkdir ( );
                 new  homeworkDownloader.ImageDownLoader ( _context, imagename ).execute ( finalUrl );
             }
         } );
    }catch ( Exception e )
    {
        e.printStackTrace ();
    }
        return convertView;
    }

    @Override
    public boolean isChildSelectable ( int groupPosition, int childPosition ) {
        return false;
    }
}
