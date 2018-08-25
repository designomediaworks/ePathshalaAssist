package com.eteach.epathshala_assist.dataset;

/**
 * Created by shree on 24/07/2017.
 */

public class TBL_FEES {
    String _FeesDATA;
    String _AdmissionNo;
    String _FeesTitle;
    String _FeesCategory ;
    String _FeeSubCategory;
    String _Feesperiod ;
    String _Feespaymode;
    String _FeestotalAmt;
    String _FeestotalDisc;
    String _FeestotalpaidAmt;
    String _FeestotalBal;
    String _Fees_paid_status;
    String _FeesPaymentDate ;
    int _pk_id;
    String _Customer_ID;

    public TBL_FEES (String FeesTitle, String FeeCategory, String FeeSubCategory, String Feesperiod, String Feespaymode, String FeestotalAmt, String FeestotalDisc, String FeestotalpaidAmt, String FeestotalBal,String Fees_paid_status, String FeesPaymentDate,String Customer_ID)
    {
        this._FeesTitle=FeesTitle;
        this._FeesCategory=FeeCategory  ;
        this._FeeSubCategory =FeeSubCategory;
        this._Feesperiod=Feesperiod;
        this._Feespaymode=Feespaymode;
        this._FeestotalAmt=FeestotalAmt;
        this._FeestotalDisc=FeestotalDisc;
        this._FeestotalpaidAmt=FeestotalpaidAmt;
        this._FeestotalBal=FeestotalBal;
        this._Fees_paid_status=Fees_paid_status;
        this._FeesPaymentDate=FeesPaymentDate;
        this._Customer_ID=Customer_ID;
    }
    public TBL_FEES (String addmissionNo, String FeesDATA)
    {
        _AdmissionNo = addmissionNo;
        _FeesDATA = FeesDATA;
    }

    public TBL_FEES(int pk_id , String FeesTitle , String FeeCategory,
                    String FeeSubCategory,
                    String Feesperiod ,
                    String Feespaymode,
                    String FeestotalAmt,
                    String FeestotalDisc,
                    String FeestotalpaidAmt,
                    String FeestotalBal,
                    String FeesPaymentDate ,
                    String Customer_ID )

    {
        _FeesTitle=FeesTitle;
        this._FeesCategory=FeeCategory  ;
        this._FeeSubCategory =FeeSubCategory;
        this._Feesperiod=Feesperiod;
        this._Feespaymode=Feespaymode;
        this._FeestotalAmt=FeestotalAmt;
        this._FeestotalDisc=FeestotalDisc;
        this._FeestotalpaidAmt=FeestotalpaidAmt;
        this._FeestotalBal=FeestotalBal;
        this._FeesPaymentDate=FeesPaymentDate;
        this._Customer_ID=Customer_ID;
    }
    public TBL_FEES(String FeesTitle ,
                    String FeeSubCategory,
                    String Feesperiod ,
                    String Feespaymode,
                    String FeestotalAmt,
                    String FeestotalDisc,
                    String FeestotalpaidAmt,
                    String FeestotalBal,
                    String FeesPaymentDate )
    {
        this._FeesCategory  =FeesTitle;
        this._FeeSubCategory =FeeSubCategory;
        this._Feesperiod=Feesperiod;
        this._Feespaymode=Feespaymode;
        this._FeestotalAmt=FeestotalAmt;
        this._FeestotalDisc=FeestotalDisc;
        this._FeestotalpaidAmt=FeestotalpaidAmt;
        this._FeestotalBal=FeestotalBal;
        this._FeesPaymentDate=FeesPaymentDate;
    }

    public TBL_FEES ( ) {

    }

    public void set_pk_id( int _pk_id ) {
        this._pk_id = _pk_id;
    }
    public void set_FeesDATA( String feesDATA ) {
        this._FeesDATA = feesDATA;
    }

    public void set_AdmissionNo (String _AdmissionNo) {
        this._AdmissionNo = _AdmissionNo;
    }

    public void set_FeesTitle(String feesTitle)
    {
        this._FeesTitle=feesTitle;
    }
    public void set_FeesCategory(String feesCategory)
    {
        this._FeesCategory=feesCategory;
    }
    public void set_FeeSubCategory(String feeSubCategory)
    {
        this._FeeSubCategory=feeSubCategory;
    }
    public void set_Feesperiod(String feesperiod)
    {
        this._Feesperiod=feesperiod;
    }
    public void set_Feespaymode(String feespaymode)
    {
        this._Feespaymode=feespaymode;
    }
    public void set_FeestotalAmt(String feestotalAmt)
    {
        this._FeestotalAmt=feestotalAmt;
    }
    public void set_FeestotalDisc(String feestotalDisc)
    {
        this._FeestotalDisc=feestotalDisc;
    }
    public void set_FeestotalpaidAmt(String feestotalpaidAmt)
    {
        this._FeestotalpaidAmt=feestotalpaidAmt;
    }
    public void set_FeestotalBal(String feestotalBal)
    {
        this._FeestotalBal=feestotalBal;
    }
    public void set_FeesPaymentDate(String feesPaymentDate)
    {
        this._FeesPaymentDate=feesPaymentDate;
    }

    public void set_Customer_ID (String _Customer_ID) {
        this._Customer_ID = _Customer_ID;
    }

    public int get_pk_id() {
        return _pk_id;
    }
    public String get_FeesData() {
        return _FeesDATA;
    }

    public String get_AdmissionNo ( ) {
        return _AdmissionNo;
    }

    public String get_FeesTitle() {
        return _FeesTitle;
    }

    public String get_FeesCategory() {
        return _FeesCategory;
    }

    public String get_FeeSubCategory() {
        return _FeeSubCategory;
    }

    public String get_Feesperiod() {
        return _Feesperiod;
    }

    public String get_Feespaymode() {
        return _Feespaymode;
    }

    public String get_FeesPaymentDate() {
        return _FeesPaymentDate;
    }

    public String get_FeestotalAmt() {
        return _FeestotalAmt;
    }

    public String get_FeestotalBal() {
        return _FeestotalBal;
    }

    public String get_FeestotalDisc() {
        return _FeestotalDisc;
    }

    public String get_FeestotalpaidAmt() {
        return _FeestotalpaidAmt;
    }

    public String get_Fees_paid_status ( ) {
        return _Fees_paid_status;
    }

    public String get_Customer_ID ( )
    {
        return _Customer_ID;
    }
}

