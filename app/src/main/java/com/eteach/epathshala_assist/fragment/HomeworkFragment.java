package com.eteach.epathshala_assist.fragment;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.eteach.epathshala_assist.Adapter.HomeworkADP2;
import com.eteach.epathshala_assist.DBHelper.DBHandler;
import com.eteach.epathshala_assist.R;
import com.eteach.epathshala_assist.Utility.Constants;
import com.eteach.epathshala_assist.Utility.utilitys;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeworkFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeworkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeworkFragment extends Fragment {
    private static ExpandableListView expandableListView;
    private static ExpandableListAdapter mAdapter;
    private OnFragmentInteractionListener mListener;
    private DBHandler dbHandler;
    private ArrayList<String> homewrok_header; // header titles
    // Child data in format of header title, child title
    private List <ArrayList <String>> homework_child_data = new ArrayList <ArrayList <String>> (  );
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View viewhomework;
    utilitys utilitys;
    public HomeworkFragment ( ) {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeworkFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeworkFragment newInstance ( String param1, String param2 ) {
        HomeworkFragment fragment = new HomeworkFragment ( );
        Bundle args = new Bundle ( );
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        utilitys =new utilitys (getActivity());
        Constants.navItemIndex = 11;
                HashMap<String,String> user = utilitys.getuserandclass();
        setheader(user.get(com.eteach.epathshala_assist.Utility.utilitys.KEY_STD_CLASS_ID));
        setChildeData(user.get(com.eteach.epathshala_assist.Utility.utilitys.KEY_STD_CLASS_ID));
    }

    @Override
    public View onCreateView ( LayoutInflater inflater, ViewGroup container,
                               Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        viewhomework = inflater.inflate ( R.layout.fragment_assignment, container, false );
        expandableListView = (ExpandableListView)viewhomework.findViewById ( R.id.homework_listview );
        HashMap <String, List <ArrayList <String>>> hashMap = new HashMap <> ();
        for ( int h = 0;h < homewrok_header.size ();h++ ) {
            hashMap.put ( homewrok_header.get ( h ),homework_child_data );
        }/*
        try {
            HomeworkADP2 homeworkADP = new HomeworkADP2 ( homewrok_header, homework_child_data, getActivity ( ),
                    (LayoutInflater) getActivity ( ).getSystemService ( Context.LAYOUT_INFLATER_SERVICE ) );
            expandableListView.setAdapter ( homeworkADP );*/
        try
        {
            mAdapter = new HomeworkADP2 (getActivity (),homewrok_header,hashMap);
            //mAdapter = new HomeworkADP3 (getActivity (),homewrok_header,hashMap);
            expandableListView.setAdapter ( mAdapter );
        }catch ( Exception  e)
        {
            e.printStackTrace ();
        }
        return viewhomework;
    }

    private void setChildeData (String _cid ) {
        try {

            dbHandler = new DBHandler ( getActivity ( ) );
            String s = "", s2 = "s";
            ArrayList<String> Subject_name_list;
            Cursor homeWorkDatalist = dbHandler.getAllHomeWorkData ( _cid );
            JSONArray jsonArray = new JSONArray ( homeWorkDatalist.getString ( 0 ) );

            for ( int i = 0 ; i < homewrok_header.size ();i++ ) {

                Subject_name_list = new ArrayList <String> (  );
                for ( int j = 0 ; j < jsonArray.length ( ) ; j++ ) {

                    JSONObject jsonObject2 = jsonArray.getJSONObject ( j );

                    s = jsonObject2.getString ( "V_SubjectName" );

                    s2 = homewrok_header.get ( i );

                    if ( s.equals ( s2 ) ) {
                        Subject_name_list.add ( jsonObject2.toString () );
                    }
                }
                homework_child_data.add ( i,Subject_name_list);
            }

        }catch ( Exception e )
        {
            e.printStackTrace ();
        }
    }

    private void setheader ( String _cid ) {
        try {
            dbHandler = new DBHandler ( getActivity ( ) );
            String s = "", s2 = "";
            homewrok_header = new ArrayList <> (  );
            Cursor homeWorkDatalist = dbHandler.getAllHomeWorkData ( _cid );
            JSONArray jsonArray = new JSONArray ( homeWorkDatalist.getString ( 0 ) );
            for ( int i = 0 ; i < jsonArray.length ( ) ; i++ ) {

                JSONObject jsonObject = jsonArray.getJSONObject ( i );

                s = jsonObject.getString ( "V_SubjectName" );

                if ( !s.equals ( s2.toString ( ) ) ) {
                    if(!homewrok_header.contains ( s ))
                    homewrok_header.add ( s );
                }
                s2 = jsonObject.getString ( "V_SubjectName" );
            }

        }catch ( Exception e )
        {
            e.printStackTrace ();
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed ( Uri uri ) {
        if ( mListener != null ) {
            mListener.onFragmentInteraction ( uri );
        }
    }

    @Override
    public void onAttach ( Context context ) {
        super.onAttach ( context );
    }

    @Override
    public void onDetach ( ) {
        super.onDetach ( );
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
        void onFragmentInteraction ( Uri uri );
    }


}
