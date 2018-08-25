package com.eteach.epathshala_assist.dataset;

/**
 * Created by shree on 01-02-2018.
 */

public class TBL_HOMEWORK {
    private int _pk_id ;



    private String _classid;
    private String _subjectid;
    private String _title;
    private String _hdate;
    private String _sdate;
    private String _disc;
    private String _data;


    public TBL_HOMEWORK ( String _data , String _cid  ) {
        this._classid=_cid;
        this._data = _data;
    }

    public TBL_HOMEWORK ( String _data ) {
        this._data = _data;
    }
    public TBL_HOMEWORK (String v_subjectName, String _classid, String _title, String _sdate, String _disc ) {
        this._subjectid = v_subjectName;
        this._classid = _classid;
        this._title = _title;
        this._sdate = _sdate;
        this._disc = _disc;
    }


    public int get_pk_id () {
        return _pk_id;
    }

    public void set_pk_id (int _pk_id) {
        this._pk_id = _pk_id;
    }

    public String get_data ( ) {
        return _data;
    }

    public void set_data ( String _data ) {
        this._data = _data;
    }

    public String get_classid () {
        return _classid;
    }

    public void set_classid (String _classid) {
        this._classid = _classid;
    }

    public String get_subjectid () {
        return _subjectid;
    }

    public void set_subjectid (String _subjectid) {
        this._subjectid = _subjectid;
    }

    public String get_title () {
        return _title;
    }

    public void set_title (String _title) {
        this._title = _title;
    }

    public String get_hdate () {
        return _hdate;
    }

    public void set_hdate (String _hdate) {
        this._hdate = _hdate;
    }

    public String get_sdate () {
        return _sdate;
    }

    public void set_sdate (String _sdate) {
        this._sdate = _sdate;
    }

    public String get_disc () {
        return _disc;
    }

    public void set_disc (String _disc) {
        this._disc = _disc;
    }
}
