package com.eteach.epathshala_assist.dataset;

/**
 * Created by shree on 24/07/2017.
 */

public class TBL_STUDENT {
    private int _pk_id ;
    private String _AdmissionNo;
    private String _StudentData;
    public TBL_STUDENT () {}

    public TBL_STUDENT (int pk_id , String AdmissionNo , String StudentData )
    {
        this._pk_id=pk_id;
        this._AdmissionNo=AdmissionNo;
        this._StudentData=StudentData;
    }
    public TBL_STUDENT ( String AdmissionNo , String StudentData)
    {this._AdmissionNo=AdmissionNo;this._StudentData=StudentData;}
////////////////////////////////
    public void setId(int pk_id)
    {this._pk_id= pk_id;}
    public void set_AdmissionNo(String AdmissionNo)
    {this._AdmissionNo=AdmissionNo;}
    public void set_StudentData(String StudentData)
    {this._StudentData=StudentData;}
////////////////////////////////
    public int getPk_id()
    {return _pk_id;}

    public String get_AdmissionNo()
    {return _AdmissionNo;}

    public String get_StudentData()
    {return _StudentData;}
}
