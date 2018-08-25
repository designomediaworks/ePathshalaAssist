package com.eteach.epathshala_assist.dataset;

/**
 * Created by shree on 24/07/2017.
 */

public class TBL_NOTI {
    private int _pk_id ;
    private String _title;
    private String _message;
    private String _timestamp;
    private String _NotificationData;
    public TBL_NOTI ()
    {

    }

    public TBL_NOTI (String NotificationData)
    {
       this._NotificationData=NotificationData;
    }

    public TBL_NOTI(int pk_id , String title , String message,String timestamp )
    {
        this._pk_id=pk_id;
        this._title=title;
        this._message=message;
        this._timestamp=timestamp;
    }
    public TBL_NOTI(String title , String message,String timestamp )
    {this._title=title;this._message=message;this._timestamp=timestamp;}
////////////////////////////////
    public void setId(int pk_id)
    {this._pk_id= pk_id;}
    public void setTitle(String title)
    {this._title=title;}
    public void setMessage(String message)
    {this._message=message;}

    public void set_timestamp (String _timestamp) {
        this._timestamp = _timestamp;
    }

    public void set_NotificationData (String _NotificationData) {
        this._NotificationData = _NotificationData;
    }

    ////////////////////////////////
    public int getPk_id()
    {return _pk_id;}

    public String getTitle()
    {return _title;}

    public String getMessage()
    {return _message;}

    public String get_timestamp ( ) {
        return _timestamp;
    }
    public String get_NotificationData ( ) {
        return _NotificationData;
    }
}
