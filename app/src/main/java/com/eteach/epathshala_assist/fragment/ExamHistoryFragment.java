package com.eteach.epathshala_assist.fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eteach.epathshala_assist.Adapter.ExamHistoryADP;
import com.eteach.epathshala_assist.Adapter.SyllabusADP;
import com.eteach.epathshala_assist.Adapter.TimetableADP;
import com.eteach.epathshala_assist.DBHelper.DBHandler;
import com.eteach.epathshala_assist.R;
import com.eteach.epathshala_assist.Utility.Constants;
import com.eteach.epathshala_assist.Utility.PDF_File_Download;
import com.eteach.epathshala_assist.Utility.PDF_File_Syllabus_and_timetable_Download;
import com.eteach.epathshala_assist.Utility.utilitys;
import com.eteach.epathshala_assist.dataset.TBL_REPORTCARD;
import com.eteach.epathshala_assist.dataset.TBL_SYLLABUS;
import com.eteach.epathshala_assist.dataset.TBL_TIMETABLE;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExamHistoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1,es;
    private String mParam2;
    private RecyclerView rv_timetable,rv_syllabus,rv_reportcard;
    View viewReportCards;
    DBHandler dbHandler;
    utilitys utilitys;
    List<TBL_REPORTCARD> reportcardList =new ArrayList<>();
    ArrayList<String> ListReportCards = new ArrayList<>();
    List<TBL_SYLLABUS> syllabusList =new ArrayList<>();
    ArrayList<String> Listsyllabus = new ArrayList<>();

    List<TBL_TIMETABLE> timetableList =new ArrayList<>();
    ArrayList<String> ListTimeTable = new ArrayList<>();

    HashMap<String, String> user ;
    private OnFragmentInteractionListener mListener;
    private ExamHistoryADP examHistoryADP;
    private
    SyllabusADP syllabusADP;
    private
    TimetableADP timetableADP;
    Boolean isReportCardAvailable = true;
    Boolean isSyllabusAvailable = true;
    Boolean isTimeTableAvailable = true;


    public ExamHistoryFragment () {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExamHistoryFragment newInstance(String param1, String param2) {
        ExamHistoryFragment fragment = new ExamHistoryFragment();
        Bundle args = new Bundle ();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        utilitys = new utilitys(getActivity());
        getListReportCards( );
        getSyllabus ( );
        getTimetable ( );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewReportCards = inflater.inflate ( R.layout.fragment_examhistory,container,false );

        initReportcard();
        initsyllabus ();
        inittimetable ();
        return viewReportCards;
    }

    private
    void initReportcard ( ) {
        rv_reportcard = (RecyclerView)viewReportCards.findViewById(R.id.listreportcard);
        rv_reportcard.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if ( rv_reportcard !=null)
        {
            examHistoryADP = new ExamHistoryADP(getActivity(),reportcardList);
            rv_reportcard.setAdapter(examHistoryADP);
        }
        rv_reportcard.setLayoutManager(linearLayoutManager);
        if ( isReportCardAvailable == true ) {
            onClickReportcardView( );
        }
    }

    private
    void onClickReportcardView ( ) {
        rv_reportcard.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getActivity(),new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp (MotionEvent e) {
                    return true;
                }
            });
            @Override
            public boolean onInterceptTouchEvent (RecyclerView rv, MotionEvent e) {
                try {
                    View childview = rv.findChildViewUnder(e.getX(), e.getY());
                    int position = rv.getChildAdapterPosition(childview);
                    es = String.valueOf(reportcardList.get(position).get_AdmissionNo());
                    if (childview != null && gestureDetector.onTouchEvent(e)) {
                        Toast.makeText(getActivity(), es, Toast.LENGTH_LONG).show();
                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                                             "/ePathshala/" + es + "_" + Constants.ADMISSION_NO + ".pdf");
                        if (file.exists()) {
                            viewpdf( file );
                        } else {
                            File path = new File(Environment.getExternalStoragePublicDirectory
                                    (Environment.DIRECTORY_PICTURES), "/ePathshala/");
                            try {
                                path.createNewFile();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            new PDF_File_Download(getActivity()).execute(reportcardList.get(position).get_ReportCardDATA(), reportcardList.get(position).get_AdmissionNo() + "_" + Constants.ADMISSION_NO + ".pdf");
                            getFragmentManager().beginTransaction().detach( ExamHistoryFragment.this ).attach(ExamHistoryFragment.this ).commit();
                        }
                    }

                }catch (Exception e1)
                {
                    e1.printStackTrace();
                }return false;
            }

            @Override
            public void onTouchEvent (RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent (boolean disallowIntercept) {

            }
        });
    }

    private
    void initsyllabus ( ) {
        rv_syllabus = (RecyclerView)viewReportCards.findViewById(R.id.listsyllabus);
        rv_syllabus.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if ( rv_syllabus !=null)
        {
            syllabusADP = new SyllabusADP (getActivity(),syllabusList);
            rv_syllabus.setAdapter(syllabusADP);
        }
        rv_syllabus.setLayoutManager(linearLayoutManager);
        if ( isSyllabusAvailable == true ) {
            onClickSyllabusView( );
        }
    }

    private
    void onClickSyllabusView ( ) {
        //region click
        rv_syllabus.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getActivity(),new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp (MotionEvent e) {
                    return true;
                }
            });
            @Override
            public
            boolean onInterceptTouchEvent ( RecyclerView rv, MotionEvent e ) {
                try {
                    View childview = rv.findChildViewUnder(e.getX(), e.getY());
                    int position = rv.getChildAdapterPosition(childview);
                    es = String.valueOf(syllabusList.get(position).get_SyllabusTitle ()) + " Syllabus ";
                    if (childview != null && gestureDetector.onTouchEvent(e)) {
                        Toast.makeText(getActivity(), es, Toast.LENGTH_LONG).show();
                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                                             "/ePathshala/" + es + "_" + Constants.STUDENT_CLASS_ID + ".pdf");
                        if (file.exists()) {
                            viewpdf( file );
                        } else {
                            File path = new File(Environment.getExternalStoragePublicDirectory
                                    (Environment.DIRECTORY_PICTURES), "/ePathshala/");
                            try {
                                path.createNewFile();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            new PDF_File_Syllabus_and_timetable_Download ( getActivity()).execute( syllabusList.get( position).get_SyllabusDownloadURL (), syllabusList.get( position).get_SyllabusTitle ()+" Syllabus " + "_" + Constants.STUDENT_CLASS_ID + ".pdf");
                            getFragmentManager().beginTransaction().detach( ExamHistoryFragment.this ).attach(ExamHistoryFragment.this ).commit();
                        }
                    }

                }catch (Exception e1)
                {
                    e1.printStackTrace();
                }return false;
            }

            @Override
            public
            void onTouchEvent ( RecyclerView rv, MotionEvent e ) {

            }

            @Override
            public
            void onRequestDisallowInterceptTouchEvent ( boolean disallowIntercept ) {

            }
        });

        //endregion
    }

    private
    void inittimetable ( ) {
        rv_timetable = (RecyclerView)viewReportCards.findViewById(R.id.listtimetable);
        rv_timetable.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if ( rv_timetable !=null)
        {
            timetableADP = new TimetableADP (getActivity(),timetableList);
            rv_timetable.setAdapter(timetableADP);
        }
        rv_timetable.setLayoutManager(linearLayoutManager);
        if ( isTimeTableAvailable == true ) {
            onClickTimeTableView ( );
        }
    }

    private void onClickTimeTableView ( ) {
        rv_timetable.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getActivity(),new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp (MotionEvent e) {
                    return true;
                }
            });
            @Override
            public
            boolean onInterceptTouchEvent ( RecyclerView rv, MotionEvent e ) {
                try {
                    View childview = rv.findChildViewUnder(e.getX(), e.getY());
                    int position = rv.getChildAdapterPosition(childview);
                    es = String.valueOf(timetableList.get(position).get_AdmissionNo())+ " Timetable ";
                    if (childview != null && gestureDetector.onTouchEvent(e)) {
                        Toast.makeText(getActivity(), es , Toast.LENGTH_LONG).show();
                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                                             "/ePathshala/" + es + "_" + Constants.STUDENT_CLASS_ID + ".pdf");
                        if (file.exists()) {
                            viewpdf(file);
                        } else {
                            File path = new File(Environment.getExternalStoragePublicDirectory
                                    (Environment.DIRECTORY_PICTURES), "/ePathshala/");
                            try {
                                path.createNewFile();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            new PDF_File_Syllabus_and_timetable_Download (getActivity()).execute(timetableList.get(position).get_ReportCardDATA(), timetableList.get(position).get_AdmissionNo()  +" Timetable " + "_" + Constants.STUDENT_CLASS_ID + ".pdf");
                            getFragmentManager().beginTransaction().detach( ExamHistoryFragment.this ).attach(ExamHistoryFragment.this ).commit();
                        }
                    }

                }catch (Exception e1)
                {
                    e1.printStackTrace();
                }return false;
            }

            @Override
            public
            void onTouchEvent ( RecyclerView rv, MotionEvent e ) {

            }

            @Override
            public
            void onRequestDisallowInterceptTouchEvent ( boolean disallowIntercept ) {

            }
        });

    }

    private ArrayList <String> getListReportCards ( ) {
        dbHandler = new DBHandler( getActivity( ) );
        user = utilitys.getUserDetails( );
        try {

            String j = dbHandler.getStudentReportCard( user.get ( com.eteach.epathshala_assist.Utility.utilitys.KEY_STD_NO ) );
            if ( !j.isEmpty( ) ) {
                JSONArray jsonArray = new JSONArray( j );
                for ( int i = 0; i < jsonArray.length( ); i++ ) {
                    JSONObject jsonObject = jsonArray.getJSONObject( i );
                    reportcardList.add( new TBL_REPORTCARD( jsonObject.getString( "Category" ).toString( ),
                            jsonObject.getString( "Path" ).toString( ) ) );
                   // isReportCardAvailable = true;
                }
            } else {
                reportcardList.add( new TBL_REPORTCARD( "No Data Found",
                        "NoDATA" ) );
                isReportCardAvailable = false;
            }
        } catch ( Exception e ) {
            e.printStackTrace( );
        }
        return ListReportCards;
    }

    private ArrayList <String> getSyllabus ( ) {
        dbHandler = new DBHandler( getActivity( ) );
        user = utilitys.getuserandclass( );
        Constants.STUDENT_CLASS_ID = ( user.get ( com.eteach.epathshala_assist.Utility.utilitys.KEY_STD_CLASS_ID )) ;
        try {

            String j = dbHandler.getStudentsyllabus ( Constants.STUDENT_CLASS_ID );
            if ( !j.isEmpty( ) ) {
                JSONArray jsonArray = new JSONArray( j );
                for ( int i = 0; i < jsonArray.length( ); i++ ) {
                    JSONObject jsonObject = jsonArray.getJSONObject( i );
                    syllabusList.add( new TBL_SYLLABUS ( jsonObject.getString( "Term" ).toString( ),
                                                            jsonObject.getString( "Path" ).toString( ) ) );
                    // isReportCardAvailable = true;
                }
            } else {
                syllabusList.add( new TBL_SYLLABUS ( "No Data Found",
                                                        "NoDATA" ) );
                isSyllabusAvailable = false;
            }
        } catch ( Exception e ) {
            e.printStackTrace( );
        }
        return Listsyllabus;
    }

    private ArrayList <String> getTimetable ( ) {
        dbHandler = new DBHandler( getActivity( ) );
        user = utilitys.getuserandclass( );
        Constants.STUDENT_CLASS_ID = ( user.get ( com.eteach.epathshala_assist.Utility.utilitys.KEY_STD_CLASS_ID )) ;
        try {

            String j = dbHandler.getStudenttimetable ( Constants.STUDENT_CLASS_ID );
            if ( !j.isEmpty( ) ) {
                JSONArray jsonArray = new JSONArray( j );
                for ( int i = 0; i < jsonArray.length( ); i++ ) {
                    JSONObject jsonObject = jsonArray.getJSONObject( i );
                    timetableList.add( new TBL_TIMETABLE ( jsonObject.getString( "Term" ).toString( ),
                                                         jsonObject.getString( "Path" ).toString( ) ) );
                    // isReportCardAvailable = true;
                }
            } else {
                timetableList.add( new TBL_TIMETABLE ( "No Data Found",
                                                     "NoDATA" ) );
                isSyllabusAvailable = false;
            }
        } catch ( Exception e ) {
            e.printStackTrace( );
        }
        return ListTimeTable;
    }

    private void viewpdf2(File file)
    {
        try {
            if ( file.exists ( ) ) {

                Uri path = FileProvider.getUriForFile ( getActivity ( ), getActivity ( ).getPackageName ( ), file );
                Intent pdfintent = new Intent ( Intent.ACTION_VIEW );
                pdfintent.setDataAndType ( path, "application/pdf" );
                pdfintent.setFlags ( Intent.FLAG_ACTIVITY_NO_HISTORY );
                pdfintent.addFlags ( Intent.FLAG_GRANT_READ_URI_PERMISSION );
                Intent intent = Intent.createChooser ( pdfintent, "Open File" );
                try {
                    startActivity ( intent );
                } catch ( ActivityNotFoundException e ) {
                    Toast.makeText ( getActivity ( ), "No Application available to viwe PDF", Toast.LENGTH_LONG ).show ( );
                }
            }
        }catch ( Exception e )
        {
            e.printStackTrace ();
        }
    }

    private void viewpdf ( File file ) {
        /*File pdfFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "/ePathshala/" +es+"_"+ Constants.ADMISSION_NO + ".pdf");*/
        if (file.exists()) {

            Uri path = Uri.fromFile(file);
            Intent pdfintent = new Intent(Intent.ACTION_VIEW);
            pdfintent.setDataAndType(path, "application/pdf");
            pdfintent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            Intent intent = Intent.createChooser ( pdfintent,"Open File" );
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getActivity(), "No Application available to viwe PDF", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void RefrashLayout()
    {
        examHistoryADP.notifyDataSetChanged();
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction (Uri uri);
    }
}
