package com.eteach.epathshala_assist.fragment;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eteach.epathshala_assist.DBHelper.DBHandler;
import com.eteach.epathshala_assist.R;
import com.eteach.epathshala_assist.Utility.Constants;
import com.eteach.epathshala_assist.dataset.TBL_ATTANDENCE;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class AttandanceFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;
    View viewattandance;
    DBHandler dbHandler;
    private OnFragmentInteractionListener mListener;
    List <TBL_ATTANDENCE> attandenceArrayList =new ArrayList <TBL_ATTANDENCE>();
    ArrayList<String> ListAttandances = new ArrayList<> (  ) ;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    private CompactCalendarView compactCalendarView ;
    private TextView txt_intime,txt_dayofmonth;
    DateFormat format;
    long mils = System.currentTimeMillis();
    String attdate,intime,outtime;
    public AttandanceFragment () {
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
    public static AttandanceFragment newInstance(String param1, String param2) {
        AttandanceFragment fragment = new AttandanceFragment();
        Bundle args = new Bundle ();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
       getallattandance(Constants.ADMISSION_NO);
    }
    private List <String> getallattandance (String Admissionno) {
        dbHandler = new DBHandler(getActivity());
        String j = "";
        try {
            Cursor tbl_attandance = dbHandler.getStudentattandance(Admissionno);
            j = tbl_attandance.getString(1);
            JSONArray jsonArray = new JSONArray(j);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                TBL_ATTANDENCE tblAttandence = new TBL_ATTANDENCE();
                attandenceArrayList.add(new TBL_ATTANDENCE (jsonObject.getString("V_RfIdNo").toString(),
                        jsonObject.getString("InTime").toString(),
                        jsonObject.getString("OutTime").toString(),
                jsonObject.getString("Date").toString()));
                 attdate = jsonObject.getString("Date").toString();
                 intime = jsonObject.getString("InTime").toString();
                 outtime = jsonObject.getString("OutTime").toString();
                covertdateStringtoLong(attdate,intime,outtime);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return ListAttandances ;
    }
    private void covertdateStringtoLong (String attdate, String intime, String outtime) {
        try {
            format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = format.parse(attdate);
        long dl = date.getTime();
        addattedance(dl,intime,outtime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void covertdateLongtoString (String attdate ) {
        try {
            format = new SimpleDateFormat("dd/MM/yyyy");
            Date date = format.parse(attdate);
            long dl = date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        viewattandance = inflater.inflate ( R.layout.fragment_attendance2,container,false );
        txt_intime = (TextView) viewattandance.findViewById(R.id.txt_attendance_status_intime);
        txt_dayofmonth = (TextView) viewattandance.findViewById(R.id.txt_dayofmonth);
        compactCalendarView = (CompactCalendarView) viewattandance.findViewById(R.id.compactCalendarView);
        compactCalendarView.setUseThreeLetterAbbreviation(false);
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        compactCalendarView.setCurrentSelectedDayIndicatorStyle(3);
        txt_dayofmonth.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
        changemonth();
        getallattandance(Constants.ADMISSION_NO);
       // loadEventsForYear(2017);
        return viewattandance;
    }
    private void changemonth ( ) {
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick (Date dateClicked) {
                try {
                    List<Event> attendance = compactCalendarView.getEvents(dateClicked);
                    long intime = attendance.get(0).getTimeInMillis();
                    txt_intime.setText(String.valueOf(attendance.get(0).getData()));
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            @Override
            public void onMonthScroll (Date firstDayOfNewMonth) {
                txt_dayofmonth.setText(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });
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
    private void addattedance(long month, String intime , String outtime) {
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DAY_OF_MONTH, 1);

        Date firstDayOfMonth = currentCalender.getTime();
            setToMidnight(currentCalender);
            /*long timeInMillis = currentCalender.getTimeInMillis();*/
            long timeInMillis =new Long( month);
            List<Event> events = getattedance(timeInMillis,1,intime,outtime);
            compactCalendarView.addEvents(events);
        }
    @NonNull
    private List<Event> getattedance(long timeInMillis, int day,String intime,String outtime) {
        String date = String.valueOf(new Date(timeInMillis));
        Date date1 = null;
        try {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        date1 = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }return Arrays.asList(new Event(Color.argb(255, 255, 255, 255), timeInMillis, "Attendance Status  : " +" \n"+"In Time:"+intime+"\n"+"Out Time:"+outtime));
    }

    private void setToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }
}
