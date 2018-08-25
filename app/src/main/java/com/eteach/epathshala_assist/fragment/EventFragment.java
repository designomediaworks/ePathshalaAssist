package com.eteach.epathshala_assist.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.eteach.epathshala_assist.Adapter.EventsADP;
import com.eteach.epathshala_assist.DBHelper.DBHandler;
import com.eteach.epathshala_assist.MainActivity;
import com.eteach.epathshala_assist.R;
import com.eteach.epathshala_assist.Utility.Constants;
import com.eteach.epathshala_assist.dataset.TBL_EVENTS;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class EventFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG_EVENTS = "Events";
    ArrayList<String> ListEvents = new ArrayList<> (  ) ;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    View viewevents;
    Button btn_youtube,btn_gallery,btn_magzine;
    DBHandler dbHandler;
    private OnFragmentInteractionListener mListener;
    List<TBL_EVENTS> eventarrayList =new ArrayList<>();
    private Boolean allowRefresh = false;

    public EventFragment () {
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
    public static EventFragment newInstance(String param1, String param2) {
        EventFragment fragment = new EventFragment();
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
        getEventsList();
    }

    private ArrayList<String> getEventsList ( ) {
        dbHandler = new DBHandler(getActivity());
    try
        {
        if (ListEvents.size()==0)
        {
            List<TBL_EVENTS> tbl_eventsList = dbHandler.getAllEventsData();
            for (TBL_EVENTS tblEvents : tbl_eventsList)
            {
                ListEvents.add(tblEvents.get_EventData());
            }
        }
        String  j = ListEvents.get(0);
            JSONArray jsonArray = new JSONArray(j);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                eventarrayList.add(new TBL_EVENTS(jsonObject.getString("EventName").toString(),
                        jsonObject.getString("EventDate").toString(),
                        jsonObject.getString("Description").toString()));
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return ListEvents;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewevents = inflater.inflate ( R.layout.fragment_event,container,false );
        btn_youtube = (Button) viewevents.findViewById( R.id.ib_youtube );
        btn_gallery = (Button) viewevents.findViewById( R.id.ib_gallery );
        btn_magzine = (Button) viewevents.findViewById( R.id.ib_magzin );
        mRecyclerView = (RecyclerView)viewevents.findViewById(R.id.eventlistview);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if ( mRecyclerView !=null)
        {
            mRecyclerView.setAdapter(new EventsADP(getActivity(),eventarrayList));
        }
        mRecyclerView.setLayoutManager(linearLayoutManager);
        onViewClick();
        onClick_youtube();
        onClick_magzine();
        onClick_gallery();
        return viewevents;
    }

    private void onClick_gallery ( ) {
        btn_gallery.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                try {
                    if ( new Constants ( ).isNetworkConnected ( getActivity ( ) ) ) {
                        allowRefresh = true;
                        onlinegalleryFragment onlinegalleryFragment = new onlinegalleryFragment ( );
                        getActivity ( ).getSupportFragmentManager ( ).beginTransaction ( )
                                .replace ( R.id.frame, onlinegalleryFragment, "Online Gallery" )
                                .addToBackStack ( TAG_EVENTS )
                                .commit ( );
                        ((MainActivity) getActivity ()).setToolbarTitlefromfragment ("Events Gallery");
                    } else {
                        new SweetAlertDialog ( getContext ( ), SweetAlertDialog.ERROR_TYPE )
                                .setTitleText ( "Something went wrong!" )
                                .setContentText ( "Please Connect to the Internet" )
                                .setConfirmText ( "Ok!" )
                                .showCancelButton ( true )
                                .setCancelClickListener ( new SweetAlertDialog.OnSweetClickListener ( ) {
                                    @Override
                                    public void onClick ( SweetAlertDialog sweetAlertDialog ) {
                                        sweetAlertDialog.cancel ( );
                                    }
                                } ).show ( );
                    }
                } catch ( Exception e )
                {
                    e.printStackTrace ( );
                }

            }
        } );
    }

    private void onClick_magzine ( ) {
        btn_magzine.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick ( View v ) {
                try {
                    if ( new Constants ( ).isNetworkConnected ( getActivity ( ) ) ) {
                        allowRefresh = true;
                        magzineFragment magzineFragment = new magzineFragment ();
                        getActivity ().getSupportFragmentManager ().beginTransaction ()
                                .replace ( R.id.frame,magzineFragment,"ePathshala eMagazine" )
                                .addToBackStack ( TAG_EVENTS )
                                .commit ();
                        ((MainActivity) getActivity ()).setToolbarTitlefromfragment ("ePathshala eMagazine");
                    } else {
                        new SweetAlertDialog ( getContext ( ), SweetAlertDialog.ERROR_TYPE )
                                .setTitleText ( "Something went wrong!" )
                                .setContentText ( "Please Connect to the Internet" )
                                .setConfirmText ( "Ok!" )
                                .showCancelButton ( true )
                                .setCancelClickListener ( new SweetAlertDialog.OnSweetClickListener ( ) {
                                    @Override
                                    public void onClick ( SweetAlertDialog sweetAlertDialog ) {
                                        sweetAlertDialog.cancel ( );
                                    }
                                } ).show ( );
                    }
                } catch ( Exception e )
                {
                    e.printStackTrace ( );
                }

            }
        } );
    }

    private void onClick_youtube ( ) {
        btn_youtube.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick ( View v ) {

                try {
                    if ( new Constants ( ).isNetworkConnected ( getActivity ( ) ) ) {
                        allowRefresh = true;

                        youtubeFragment youtubeFragment = new youtubeFragment ();
                        getActivity ().getSupportFragmentManager ().beginTransaction ()
                                .replace ( R.id.frame,youtubeFragment,"Youtube Channel" )
                                .addToBackStack ( TAG_EVENTS )
                                .commit ();
                        ((MainActivity) getActivity ()).setToolbarTitlefromfragment ("ePathshala Youtube");
                    } else {
                        new SweetAlertDialog ( getContext ( ), SweetAlertDialog.ERROR_TYPE )
                                .setTitleText ( "Something went wrong!" )
                                .setContentText ( "Please Connect to the Internet" )
                                .setConfirmText ( "Ok!" )
                                .showCancelButton ( true )
                                .setCancelClickListener ( new SweetAlertDialog.OnSweetClickListener ( ) {
                                    @Override
                                    public void onClick ( SweetAlertDialog sweetAlertDialog ) {
                                        sweetAlertDialog.cancel ( );
                                    }
                                } ).show ( );
                    }
                } catch ( Exception e )
                {
                    e.printStackTrace ( );
                }

            }
        } );
    }

    public void onViewClick() {

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getActivity(),new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp (MotionEvent e) {
                    //Toast.makeText(getActivity(),"U single clicked",Toast.LENGTH_LONG).show();
                    return true;
                }
            });

            @Override
            public boolean onInterceptTouchEvent (RecyclerView rv, MotionEvent e) {
                try
                {
                View childview = rv.findChildViewUnder(e.getX(),e.getY());
                int position = rv.getChildAdapterPosition(childview);
                String  es = String.valueOf(eventarrayList.get(position).get_EVENT_NAME());
                if (childview != null && gestureDetector.onTouchEvent(e))
                {
                Toast.makeText(getActivity(),es,Toast.LENGTH_LONG).show();
                    Constants.navItemIndex = 11;
                    EventPhotosFragment eventPhotosFragment = new EventPhotosFragment ();
                    getActivity ().getSupportFragmentManager ().beginTransaction ()
                            .replace ( R.id.frame,eventPhotosFragment,"events Photos" )
                            .addToBackStack ( TAG_EVENTS )
                            .commit ();
                        eventPhotosFragment.setmParam1(es);
                }
                }catch (Exception e1)
                {
                    e1.printStackTrace();
                }
                    return false;
            }

            @Override
            public void onTouchEvent (RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent (boolean disallowIntercept) {

            }
        });
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
