package com.eteach.epathshala_assist.dataset;

/**
 * Created by shree on 24/07/2017.
 */

public class TBL_SYLLABUS {
    String _SyllabusTitleDATA;
    int _AdmissionNo;
    String _SyllabusTitle;
    String _SyllabusDownloadURL;
    int _pk_id;


    public
    TBL_SYLLABUS ( int cid, String webRespose ) {
        _AdmissionNo = cid;
        _SyllabusTitleDATA = webRespose;
    }

    public
    TBL_SYLLABUS ( ) {

    }

    public
    TBL_SYLLABUS ( String category, String path ) {
        _SyllabusTitle = category;
        _SyllabusDownloadURL = path;
    }

    public void set_pk_id( int _pk_id ) {
        this._pk_id = _pk_id;
    }


    public void set_ReportCardDATA (String _SyllabusDATA) {
        this._SyllabusTitleDATA = _SyllabusDATA;
    }


    public int get_pk_id() {
        return _pk_id;
    }

    public
    int get_AdmissionNo ( ) {
        return _AdmissionNo;
    }


    public String get_ReportCardDATA ( ) {
        return _SyllabusTitleDATA;
    }

    public
    String get_SyllabusTitleDATA ( ) {
        return _SyllabusTitleDATA;
    }

    public
    String get_SyllabusTitle ( ) {
        return _SyllabusTitle;
    }

    public
    String get_SyllabusDownloadURL ( ) {
        return _SyllabusDownloadURL;
    }
}

