package com.eteach.epathshala_assist.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.eteach.epathshala_assist.MainActivity;
import com.eteach.epathshala_assist.R;
import com.eteach.epathshala_assist.Utility.Constants;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class eVotingFinalFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private View viewVoting;
    private
    Button btn_house_vote;

    private
    ImageButton btn_pre_vote,ib_bg;
    String red_house_url = "https://evote.pollgateway.com/PG/vote.aspx?eid=30548bbb-87ff-4630-a63f-ded1f5109eed";
    String green_house_url = "https://evote.pollgateway.com/PG/vote.aspx?eid=885dd6ca-cd2d-444b-acae-8e7c9f0e7f1f";
    String yellow_house_url = "https://evote.pollgateway.com/PG/vote.aspx?eid=4c2ab5aa-97de-4186-875f-434bfabfc192";
    String blue_house_url = "https://evote.pollgateway.com/PG/vote.aspx?eid=a878fee8-7901-4438-9b79-63842b197b08";
    String vc_url = "https://evote.pollgateway.com/PG/vote.aspx?eid=fee892c1-be67-4372-b258-b087394b1fcf";

    private OnFragmentInteractionListener mListener;

    public
    eVotingFinalFragment () {
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
    public static
    eVotingFinalFragment newInstance( String param1, String param2) {
        eVotingFinalFragment fragment = new eVotingFinalFragment ();
        Bundle args = new Bundle ();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Constants.navItemIndex = 21;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewVoting = inflater.inflate ( R.layout.fragment_voting,container,false );

        btn_house_vote = (Button)viewVoting.findViewById ( R.id.btn_house_vote );
        btn_pre_vote=(ImageButton ) viewVoting.findViewById ( R.id.btn_pre_vote );
        ib_bg=(ImageButton ) viewVoting.findViewById ( R.id.ib_bg );
        Glide.with ( viewVoting ).load ( R.drawable.pre_icon).into ( btn_pre_vote );
        Glide.with ( viewVoting ).load ( R.drawable.evoting_bg).into ( ib_bg );
        String house = Constants.STUDENT_HOUSE;
        try {
            if ( house.equals ( "RED") ) {
                Constants.STUDENT_HOUSE_VOTING_URL = red_house_url;
                btn_house_vote.setBackgroundColor ( Color.RED );
                btn_house_vote.setText ( house );
            }
            else if (house .equals ( "YELLOW"))
            {
                Constants.STUDENT_HOUSE_VOTING_URL = yellow_house_url;
                btn_house_vote.setBackgroundColor ( Color.YELLOW );
                btn_house_vote.setText ( house );
            }
            else if (house.equals ( "GREEN"))
            {
                Constants.STUDENT_HOUSE_VOTING_URL = green_house_url;
                btn_house_vote.setBackgroundColor ( Color.GREEN );
                btn_house_vote.setText ( house );
            }
            else if (house.equals ( "BLUE"))
            {
                Constants.STUDENT_HOUSE_VOTING_URL = blue_house_url;
                btn_house_vote.setBackgroundColor ( Color.BLUE );
                btn_house_vote.setText ( house );
            }
                MainActivity.fab.hide();
            btn_house_vote.setOnClickListener ( this );
            btn_pre_vote.setOnClickListener ( this );
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return viewVoting;
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

    @Override
    public
    void onClick ( View v ) {
        switch ( v.getId ())
        {
            case R.id.btn_house_vote:
                try {
                    android.support.v4.app.Fragment fragment;
                    fragment = new eVotingFragment ();
                    Constants.navItemIndex = 21;
                    android.support.v4.app.FragmentTransaction fragmentTransaction = ((MainActivity) getActivity () ).getSupportFragmentManager ().beginTransaction ();
                    fragmentTransaction.replace ( R.id.frame, fragment);
                    fragmentTransaction.addToBackStack (null);
                    fragmentTransaction.commit ();
                }catch ( Exception e )
                {
                    e.printStackTrace ();
                }
                break;
            case R.id.btn_pre_vote:
                Constants.STUDENT_HOUSE_VOTING_URL = vc_url;
                android.support.v4.app.Fragment fragment;
                fragment = new eVotingFragment ();
                Constants.navItemIndex = 22;
                android.support.v4.app.FragmentTransaction fragmentTransaction = ((MainActivity) getActivity () ).getSupportFragmentManager ().beginTransaction ();
                fragmentTransaction.replace ( R.id.frame, fragment);
                fragmentTransaction.addToBackStack (null);
                fragmentTransaction.commit ();
                break;
        }
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
