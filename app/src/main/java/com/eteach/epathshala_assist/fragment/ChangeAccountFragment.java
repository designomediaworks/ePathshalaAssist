package com.eteach.epathshala_assist.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eteach.epathshala_assist.Adapter.ToppersADP;
import com.eteach.epathshala_assist.Adapter.changeAccountADP;
import com.eteach.epathshala_assist.DBHelper.DBHandler;
import com.eteach.epathshala_assist.R;
import com.eteach.epathshala_assist.dataset.TBL_STUDENT;
import com.eteach.epathshala_assist.dataset.TBL_STUDENTS_CHANGE_ACC;
import com.eteach.epathshala_assist.dataset.TBL_TOPPERS;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ChangeAccountFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ArrayList<String> ListToppers = new ArrayList<> (  ) ;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    View viewtoppers;
    DBHandler dbHandler;
    private OnFragmentInteractionListener mListener;
    List <TBL_STUDENTS_CHANGE_ACC> tbl_StudentList = new ArrayList <> ( );
    ArrayList <String> ListStudent = new ArrayList <> ( );
    public
    ChangeAccountFragment () {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhotosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static
    ChangeAccountFragment newInstance( String param1, String param2) {
        ChangeAccountFragment fragment = new ChangeAccountFragment ();
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
        getStudentList();
    }

    private
    ArrayList <String> getStudentList ( ) {
        try {
            dbHandler = new DBHandler ( getActivity ( ) );
            if ( ListStudent.size ( ) == 0 ) {
                List <TBL_STUDENT> tbl_studentList = dbHandler.getAllStudentData ( );
                for ( TBL_STUDENT tblStudent : tbl_studentList ) {
                    ListStudent.add ( tblStudent.get_StudentData ( ) );
                }
            }
           // String j = ListStudent.get ( 0 );
            for (int j = 0  ; j <ListStudent.size () ; j++) {
                 String data = ListStudent.get ( j );
                JSONArray jsonArray = new JSONArray ( data );
                for ( int i = 0 ; i < jsonArray.length ( ) ; i++ ) {
                    JSONObject jsonObject = jsonArray.getJSONObject ( i );
                    tbl_StudentList.add ( new TBL_STUDENTS_CHANGE_ACC ( jsonObject.getString ( "V_AdmissionNo" ).toString ( ) ,
                                                                        jsonObject.getString ( "V_S_FName" ).toString ( )
                                                                                + " " + jsonObject.getString ( "V_S_MName" ).toString ( )
                                                                                + " " + jsonObject.getString ( "V_S_LName" ).toString ( ) ,
                                                                        jsonObject.getString ( "v_classname" ).toString ( ) ,
                                                                        jsonObject.getString ( "dob" ).toString ( ) ,
                                                                        jsonObject.getString ( "V_Address1" ).toString ( ) ,
                                                                        jsonObject.getString ( "V_ContactNo" ).toString ( ) ,
                                                                        jsonObject.getString ( "imgpath" ).toString ( ) ,
                                                                        jsonObject.getInt ( "Pk_Student_M" ),
                                                                        jsonObject.getInt ( "Fk_ClassId" ),jsonObject.getString ( "V_housename" )));
                }
            }
        } catch ( Exception e ) {
            e.printStackTrace ( );
        }
        return ListStudent;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewtoppers = inflater.inflate ( R.layout.fragment_toppers,container,false );
        mRecyclerView = (RecyclerView)viewtoppers.findViewById(R.id.topperslistview);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager ( getActivity (),1 );
        /*LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);*/

        mRecyclerView.setLayoutManager ( layoutManager );
        mRecyclerView.addItemDecoration (  new GridSpacingItemDecoration ( 1,dpToPx ( 10 ),true ) );
        mRecyclerView.setItemAnimator ( new DefaultItemAnimator () );
        if ( mRecyclerView !=null)
        {
            mRecyclerView.setAdapter(new changeAccountADP ( getActivity(), tbl_StudentList));
        }
       // mRecyclerView.setLayoutManager(linearLayoutManager);
        return viewtoppers;
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
        void onFragmentInteraction ( Uri uri );
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets( Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round( TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


}
