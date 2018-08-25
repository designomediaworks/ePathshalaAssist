package com.eteach.epathshala_assist.dataset;

/**
 * Created by shree on 24/07/2017.
 */

public class TBL_TOPPERS {
    private int _pk_id ;
    private String _ToppersData;
    public  String _STUDENT_NAME;
    public  String _CLASS;
    public  String _MARKS;
    public  String _RANK;
    public TBL_TOPPERS ()
    {

    }

    public TBL_TOPPERS (int pk_id , String ToppersData )
    {
        this._pk_id=pk_id;
        this._ToppersData=ToppersData;
    }
    public TBL_TOPPERS (String ToppersData)
    {this._ToppersData=ToppersData;}

    public TBL_TOPPERS (String STUDENT_NAME, String CLASS, String MARKS, String RANK)
    {
        this._STUDENT_NAME=STUDENT_NAME;
        this._CLASS=CLASS;
        this._MARKS=MARKS;
        this._RANK=RANK;
    }

    ////////////////////////////////
    public void setId(int pk_id)
    {this._pk_id= pk_id;}
    public void set_ToppersData(String ToppersData)
    {this._ToppersData=ToppersData;}
    public void set_STUDENT_NAME(String STUDENT_NAME)
    {this._STUDENT_NAME=STUDENT_NAME;}
    public void set_CLASS(String CLASS)
    {this._CLASS=CLASS;}
    public void setMARKS(String MARKS)
    {this._MARKS=MARKS;}

    public void set_RANK (String _RANK) {
        this._RANK = _RANK;
    }
    ////////////////////////////

    public String get_ToppersData()
    {return _ToppersData;}

    public String get_STUDENT_NAME ( ) {
        return _STUDENT_NAME;
    }

    public String get_CLASS ( ) {
        return _CLASS;
    }

    public String get_MARKS ( ) {
        return _MARKS;
    }

    public String get_RANK ( ) {
        return _RANK;
    }
}
