package com.eteach.epathshala_assist.fragment;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.eteach.epathshala_assist.Adapter.Fees_online_payment_history_ADP;
import com.eteach.epathshala_assist.DBHelper.DBHandler;
import com.eteach.epathshala_assist.R;
import com.eteach.epathshala_assist.Utility.utilitys;
import com.eteach.epathshala_assist.dataset.TBL_FEES_PAYMENT_DETAILS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link //SettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link //SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeesonlinepaymentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<String> ListFees = new ArrayList<> (  ) ;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;
    private OnFragmentInteractionListener mListener;
    View viewevents;
    Button btn_Fees_Structure;
    DBHandler dbHandler;
    utilitys utility;
    List<TBL_FEES_PAYMENT_DETAILS> fees_payment_details_list = new ArrayList<>();
    Dialog dialogfees_structure;

    public FeesonlinepaymentFragment () {
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
    public static FeesonlinepaymentFragment newInstance(String param1, String param2) {
        FeesonlinepaymentFragment fragment = new FeesonlinepaymentFragment();
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
        getFeespaymentList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewevents = inflater.inflate ( R.layout.fragment_fees_online_payment,container,false );
        mRecyclerView = (RecyclerView)viewevents.findViewById(R.id.feesonlinepaymentlistview);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if ( mRecyclerView !=null)
        {
            mRecyclerView.setAdapter(new Fees_online_payment_history_ADP(getActivity(),fees_payment_details_list,getContext()));
        }
        mRecyclerView.setLayoutManager(linearLayoutManager);
        showfeesStructure();
        return viewevents;
    }

    private void showfeesStructure ( ) {
        btn_Fees_Structure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                try {
                    dialogfees_structure = new Dialog ( getActivity() );
                    dialogfees_structure.setTitle ( "Fees Structure" );
                LayoutInflater layoutInflater = (LayoutInflater ) getActivity().getApplicationContext().getSystemService ( Context.LAYOUT_INFLATER_SERVICE );
                viewevents = layoutInflater.inflate ( R.layout.listfeesstructure,null,false );
                    dialogfees_structure.setContentView ( viewevents );
                    dialogfees_structure.setCancelable ( true );
                    dialogfees_structure.show ();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    private ArrayList<String> getFeespaymentList ( ) {
        dbHandler = new DBHandler(getActivity());
        utility = new utilitys ( getActivity());
        String j = "";
        try {
        HashMap<String, String> user = utility.getUserDetails ( );
            Cursor tbl_feesList = dbHandler.getStudentFeesPaymentDetails(user.get ( utilitys.KEY_STD_NO ));
            for(int i = 0 ; i < tbl_feesList.getCount() ; i++) {
                j = tbl_feesList.getString(i);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return ListFees ;
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction (Uri uri);
    }
}
