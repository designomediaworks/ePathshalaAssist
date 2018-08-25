package com.eteach.epathshala_assist.dataset;

/**
 * Created by shree on 24/07/2017.
 */

public class TBL_ATTANDENCE {
    private int _pk_id ;
    public String _OUTTIME  ;
    public String _ATTDATE;
    public  String _STDID;
    public  String _ATMONTH;
    public  String _ATTDATA;
    public TBL_ATTANDENCE ()
    {

    }

    public TBL_ATTANDENCE (int pk_id , String ATTDATA )
    {
        this._pk_id=pk_id;
        this._ATTDATA=ATTDATA;
    }
    public TBL_ATTANDENCE (String ATTDATA)
    {this._ATTDATA=ATTDATA;}

    public TBL_ATTANDENCE ( String STDID,String ATMONTH,String ATTDATA )
    {
        this._STDID=STDID;
        this._ATMONTH=ATMONTH;
        this._ATTDATA=ATTDATA;
    }
    public TBL_ATTANDENCE ( String STDID,String ATDATE,String ATINTIME,String ATOUTTIME )
    {
        this._STDID=STDID;
        this._ATMONTH=ATDATE;
        this._ATTDATA=ATINTIME;
        this._OUTTIME = ATOUTTIME;
    }
    ////////////////////////////////

    public void set_ATTDATA (String _ATTDATA) {
        this._ATTDATA = _ATTDATA;
    }
    public void set_ATTDATE(String ATDATE)
    {
        this._ATTDATE=ATDATE;
    }

    public void set_STDID (String _STDID) {
        this._STDID = _STDID;
    }

    public void set_ATMONTH (String _ATMONTH) {
        this._ATMONTH = _ATMONTH;
    }
    public void set_pk_id (int _pk_id) {
        this._pk_id = _pk_id;
    }

    public int get_pk_id ( ) {
        return _pk_id;
    }

    public String get_ATTDATA ( ) {
        return _ATTDATA;
    }

    public String get_ATTDATE ( ) {
        return _ATTDATE;
    }

    public String get_ATMONTH ( ) {
        return _ATMONTH;
    }

    public String get_STDID ( ) {
        return _STDID;
    }
}
