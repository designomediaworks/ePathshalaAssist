package com.eteach.epathshala_assist.dataset;

/**
 * Created by shree on 24/07/2017.
 */

public class TBL_EVENTS {
    private int _pk_id ;
    private String _EventData;
    public  String _EVENT_NAME;
    public  String _EVENT_TIME;
    public  String _EVENT_DISCREAPTION;
    public  String _EVENT_Thumb_Photo;
    public TBL_EVENTS ()
    {
        
    }

    public TBL_EVENTS (int pk_id , String EventData )
    {
        this._pk_id=pk_id;
        this._EventData=EventData;
    }
    public TBL_EVENTS ( String EventData)
    {this._EventData=EventData;}

    public TBL_EVENTS ( String EVENT_NAME, String EVENT_TIME, String EVENT_DISCREAPTION)
    {
        this._EVENT_NAME=EVENT_NAME;
        this._EVENT_TIME=EVENT_TIME;
        this._EVENT_DISCREAPTION=EVENT_DISCREAPTION;
    }

    ////////////////////////////////
    public void setId(int pk_id)
    {this._pk_id= pk_id;}
    public void set_EventData(String EventData)
    {this._EventData=EventData;}
    public void set_EVENT_NAME(String EVENT_NAME)
    {this._EVENT_NAME=EVENT_NAME;}
    public void set__EVENT_TIME(String EVENT_TIME)
    {this._EVENT_TIME=EVENT_TIME;}
    public void set__EVENT_DISCREAPTION(String EVENT_DISCREAPTION)
    {this._EVENT_DISCREAPTION=EVENT_DISCREAPTION;}

    public void set_EVENT_Thumb_Photo (String _EVENT_Thumb_Photo) {
        this._EVENT_Thumb_Photo = _EVENT_Thumb_Photo;
    }
    ////////////////////////////

    public String get_EventData()
    {return _EventData;}

    public String get_EVENT_NAME ( ) {
        return _EVENT_NAME;
    }

    public String get_EVENT_TIME ( ) {
        return _EVENT_TIME;
    }

    public String get_EVENT_DISCREAPTION ( ) {
        return _EVENT_DISCREAPTION;
    }

    public String get_EVENT_Thumb_Photo ( ) {
        return _EVENT_Thumb_Photo;
    }
}
