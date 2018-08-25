package com.eteach.epathshala_assist.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.eteach.epathshala_assist.MainActivity;
import com.eteach.epathshala_assist.R;
import com.eteach.epathshala_assist.Utility.Constants;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class eVotingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    View viewyoutube;
    private OnFragmentInteractionListener mListener;
    WebView webView ;
    public
    eVotingFragment () {
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
    eVotingFragment newInstance( String param1, String param2) {
        eVotingFragment fragment = new eVotingFragment ();
        Bundle args = new Bundle ();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Constants.navItemIndex = 22;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewyoutube = inflater.inflate ( R.layout.fragment_magzine,container,false );
        webView=(WebView)viewyoutube.findViewById(R.id.wv_magzine);
        try {
            final SweetAlertDialog sweetAlertDialog = new SweetAlertDialog( getActivity() ,SweetAlertDialog.PROGRESS_TYPE);
            sweetAlertDialog
                    .setTitleText( "eVoting panel Loading...." )
                    .setContentText( "ePathShala School Assist" )
                    .show();

            sweetAlertDialog.setCancelable( false );
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient()
            {
                @Override
                public void onPageStarted ( WebView view, String url, Bitmap favicon ) {
                    super.onPageStarted( view, url, favicon );
                    sweetAlertDialog.show();
                }
                @Override
                public void onPageFinished ( WebView view, String url ) {
                    super.onPageFinished( view, url );
                    sweetAlertDialog.dismissWithAnimation();
                }
            });
            String eMagzine = Constants.STUDENT_HOUSE_VOTING_URL;
            webView.loadUrl(eMagzine);

                MainActivity.fab.hide();

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return viewyoutube;
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
}
