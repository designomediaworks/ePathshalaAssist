package com.eteach.epathshala_assist.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eteach.epathshala_assist.Adapter.EventsPhotosADP;
import com.eteach.epathshala_assist.MainActivity;
import com.eteach.epathshala_assist.R;
import com.eteach.epathshala_assist.Utility.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class DailyThoughtsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String eventpath;
    private RecyclerView mRecyclerView;
    View viewevents;
    private static final String TAG_EVENTS = "Events";
    private OnFragmentInteractionListener mListener;
    ArrayList<String> eventphotoarrayList =new ArrayList<>();
    List<File> fileList = new ArrayList<>();
    private boolean allowRefresh = false;

    public
    DailyThoughtsFragment () {
        // Required empty public constructor
    }

    public String setmParam1 (String path ) {
        eventpath = path;
        return eventpath;
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
    DailyThoughtsFragment newInstance( String param1, String param2) {
        DailyThoughtsFragment fragment = new DailyThoughtsFragment ();
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
        File eventfilepath = new File( Environment.getExternalStoragePublicDirectory (Environment.DIRECTORY_PICTURES),
                "/ePathshala/EventView/"+ eventpath);
        getEventPhotos(eventfilepath);
    }
    private List<File>  getEventPhotos(File path)
    {
        try {
        Queue<File> fileQueue = new LinkedList<>();
        fileQueue.addAll(Arrays.asList(path.listFiles()));
        while (!fileQueue.isEmpty())
        {
            File file = fileQueue.remove();
            if (file.isDirectory())
            {
                fileQueue.addAll(Arrays.asList(file.listFiles()));
            }else if (file.getName().endsWith(".jpg")){
                fileList.add(file);
            }
        }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return  fileList;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewevents = inflater.inflate ( R.layout.fragment_events_photosview,container,false );
        mRecyclerView = (RecyclerView)viewevents.findViewById(R.id.eventsphotoslistview);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if ( mRecyclerView !=null)
        {
            mRecyclerView.setAdapter(new EventsPhotosADP(getActivity(),fileList,eventpath));
        }
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //allowRefresh = true;
        return viewevents;
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
    public void onResume ( ) {
        super.onResume ( );
        if ( allowRefresh ) {
            allowRefresh = false;
            Constants.navItemIndex = 8;
            getFragmentManager ( ).beginTransaction ( ).detach ( this ).attach ( this ).commit ( );
            ((MainActivity) getActivity ()).setToolbarTitlefromfragment ( TAG_EVENTS);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction ( Uri uri );
    }

}
