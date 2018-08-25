package com.eteach.epathshala_assist.Adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eteach.epathshala_assist.MainActivity;
import com.eteach.epathshala_assist.R;
import com.eteach.epathshala_assist.Utility.utilitys;
import com.eteach.epathshala_assist.dataset.TBL_STUDENTS_CHANGE_ACC;
import com.eteach.epathshala_assist.fragment.HomescreenFragment;

import java.io.File;
import java.util.List;

/**
 * Created by shree on 08/09/2017.
 */

public class changeAccountADP extends RecyclerView.Adapter<changeAccountADP.MyViewHolder>{

    private Context context;
    private utilitys utilitys;
    private List <TBL_STUDENTS_CHANGE_ACC> tblStudnetList;
    public  String AdmissionNo = "", studentname = "",studentclass="",studentdob="",studentAdd="",studentMobileno="",profile_download_path="",mHouse="";
    private int stdid, classid;
    public changeAccountADP ( FragmentActivity activity, List <TBL_STUDENTS_CHANGE_ACC> _tblStudnetList) {
        this.context = activity;
        this.tblStudnetList = _tblStudnetList;

    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listchangeaccounts,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public
    void onBindViewHolder ( MyViewHolder holder , final int position ) {
        final String mSchoolName = "epathshala";
        AdmissionNo = tblStudnetList.get ( position ).getAdmissionNo ();
        studentname = tblStudnetList.get ( position).getStudentname ();
        studentclass = tblStudnetList.get ( position ).getStudentclass ();
        studentAdd = tblStudnetList.get ( position ).getStudentAdd ();
        studentdob = tblStudnetList.get(position).getStudentdob ();
        studentMobileno = tblStudnetList.get ( position ).getStudentMobileno ();
        profile_download_path = tblStudnetList.get ( position ).getProfile_download_path ();
        stdid = tblStudnetList.get(position).getStdid ();
        classid=tblStudnetList.get ( position ).getClassid ();
        mHouse=tblStudnetList.get (position).getmHouse ();
        try {
            if ( studentclass.equals ( "NURSERY" ) ) {
                holder.tv_class.setText ( "Class: " + studentclass.substring ( 0 , 3 ) );
            } else {
                holder.tv_class.setText ( "Class: " + studentclass );
            }
            holder.tv_admissionno.setText ( AdmissionNo.toUpperCase () );
            holder.tv_dob.setText ( "DOB: " + studentdob );
            holder.tv_studentname.setText ( studentname.toUpperCase ());
            holder.tv_address.setText ( studentAdd );
            holder.tv_mobileno.setText ("Mobile No.:" + studentMobileno );
            File StudentPhotofile = new File ( Environment.getExternalStoragePublicDirectory ( Environment.DIRECTORY_PICTURES )
                                                       + "/ePathshala/" + AdmissionNo + ".jpg" );
            Uri StudentPhotouri = Uri.fromFile ( StudentPhotofile );
            Glide.with ( context ).load ( StudentPhotouri ).into ( holder.iv_student_photo_round );
            holder.btn_change_account.setOnClickListener ( new View.OnClickListener ( ) {
                @Override
                public
                void onClick ( View v ) {
                    utilitys = new utilitys ( context.getApplicationContext ( ) );
                    utilitys.createLoginSession ( mSchoolName,
                                                  tblStudnetList.get ( position ).getAdmissionNo (),
                                                  String.valueOf ( tblStudnetList.get( position).getStdid () ) ,
                                                  String.valueOf ( tblStudnetList.get ( position ).getClassid () ),
                                                  tblStudnetList.get (position).getmHouse () );

                    HomescreenFragment homescreenFragment = new HomescreenFragment ( );
                    ((MainActivity)context).getSupportFragmentManager ( ).beginTransaction ( )
                            .replace ( R.id.frame, homescreenFragment, "Home" )
                            .addToBackStack ( null )
                            .commit ( );
                }
            } );
        } catch ( Exception e ) {
            e.printStackTrace ( );
        }
    }

    @Override
    public int getItemCount ( ) {
        return tblStudnetList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_admissionno,tv_studentname,tv_class,tv_dob,tv_address,tv_mobileno;
        public ImageView iv_student_photo_round;
        public Button btn_change_account;
        public MyViewHolder (View itemView) {
            super(itemView);
                 tv_admissionno = ( TextView ) itemView.findViewById ( R.id.tv_admissionno );
                 tv_studentname = ( TextView ) itemView.findViewById ( R.id.tv_studentname );
                 tv_class = ( TextView ) itemView.findViewById ( R.id.tv_class );
                 tv_dob = ( TextView ) itemView.findViewById ( R.id.tv_DOB );
                 tv_address = ( TextView ) itemView.findViewById ( R.id.tv_studentAdd );
                 tv_mobileno = ( TextView ) itemView.findViewById ( R.id.tv_studentMobileno );
                iv_student_photo_round = (ImageView) itemView.findViewById ( R.id.iv_student_photo_round );
                btn_change_account = (Button)itemView.findViewById ( R.id.btn_change_account );
        }
    }
}
