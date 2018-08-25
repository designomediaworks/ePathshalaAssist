package com.eteach.epathshala_assist.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eteach.epathshala_assist.Adapter.ToppersADP;
import com.eteach.epathshala_assist.DBHelper.DBHandler;
import com.eteach.epathshala_assist.R;
import com.eteach.epathshala_assist.dataset.TBL_TOPPERS;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ToppersFragment extends Fragment {
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
    List<TBL_TOPPERS> toppersarrayList =new ArrayList<>();

    public ToppersFragment () {
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
    public static ToppersFragment newInstance(String param1, String param2) {
        ToppersFragment fragment = new ToppersFragment();
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
        getToppersList();
    }

    private ArrayList<String> getToppersList ( ) {
        dbHandler = new DBHandler(getActivity());
    try
        {
        if (ListToppers.size()==0)
        {
            List<TBL_TOPPERS> tbl_toppersList = dbHandler.getAlltoppersData();
            for (TBL_TOPPERS tbl_toppers : tbl_toppersList)
            {
                ListToppers.add(tbl_toppers.get_ToppersData());
            }
        }
        String  j = ListToppers.get(0);


            JSONArray jsonArray = new JSONArray(j);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                toppersarrayList.add(new TBL_TOPPERS(jsonObject.getString("stdname").toString(),
                        jsonObject.getString("v_classname").toString(),
                        jsonObject.getString("v_marks").toString(),
                        jsonObject.getString("v_rank").toString()));
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return ListToppers;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewtoppers = inflater.inflate ( R.layout.fragment_toppers,container,false );
        mRecyclerView = (RecyclerView)viewtoppers.findViewById(R.id.topperslistview);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if ( mRecyclerView !=null)
        {
            mRecyclerView.setAdapter(new ToppersADP(getActivity(),toppersarrayList));
        }
        mRecyclerView.setLayoutManager(linearLayoutManager);
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
        void onFragmentInteraction (Uri uri);
    }
}
