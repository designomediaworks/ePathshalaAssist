package com.eteach.epathshala_assist.dataset;

/**
 * Created by shree on 24/07/2017.
 */

public class TBL_REPORTCARD {
    String _ReportCardDATA;
    String _AdmissionNo;
    String _ReportCardTitle;
    String _ReportCardCategory ;
    String _ReportCardDownloadURL;
    int _pk_id;

    public TBL_REPORTCARD (String addmissionNo, String ReportCardDATA)
    {
        _AdmissionNo = addmissionNo;
        _ReportCardDATA = ReportCardDATA;
    }


    public TBL_REPORTCARD ( ) {

    }

    public void set_pk_id( int _pk_id ) {
        this._pk_id = _pk_id;
    }

    public void set_AdmissionNo (String _AdmissionNo) {
        this._AdmissionNo = _AdmissionNo;
    }

    public void set_ReportCardTitle (String _ReportCardTitle) {
        this._ReportCardTitle = _ReportCardTitle;
    }

    public void set_ReportCardCategory (String _ReportCardCategory) {
        this._ReportCardCategory = _ReportCardCategory;
    }

    public void set_ReportCardDATA (String _ReportCardDATA) {
        this._ReportCardDATA = _ReportCardDATA;
    }

    public void set_ReportCardDownloadURL (String _ReportCardDownloadURL) {
        this._ReportCardDownloadURL = _ReportCardDownloadURL;
    }

    public int get_pk_id() {
        return _pk_id;
    }

    public String get_AdmissionNo ( ) {
        return _AdmissionNo;
    }

    public String get_ReportCardCategory ( ) {
        return _ReportCardCategory;
    }

    public String get_ReportCardDATA ( ) {
        return _ReportCardDATA;
    }

    public String get_ReportCardTitle ( ) {
        return _ReportCardTitle;
    }

    public String get_ReportCardDownloadURL ( ) {
        return _ReportCardDownloadURL;
    }
}

