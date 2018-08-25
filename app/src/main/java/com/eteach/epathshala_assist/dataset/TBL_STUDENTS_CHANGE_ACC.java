package com.eteach.epathshala_assist.dataset;

public
class TBL_STUDENTS_CHANGE_ACC {

    private String AdmissionNo;
    private String studentname;
    private String studentclass;
    private String studentdob;
    private String studentAdd;
    private String studentMobileno;
    private String profile_download_path;
    private String mHouse;
    private int stdid;
    private int  classid;


    public
    TBL_STUDENTS_CHANGE_ACC ( String admissionNo , String studentname,String studentclass,String studentdob, String studentAdd, String studentMobileno,String profile_download_path ,int stdid,int classid,String mHouse) {
        this.AdmissionNo = admissionNo;
        this.studentname = studentname;
        this.studentclass = studentclass;
        this.studentdob = studentdob;
        this.studentAdd = studentAdd;
        this.studentMobileno = studentMobileno;
        this.profile_download_path = profile_download_path;
        this.stdid=stdid;
        this.classid=classid;
        this.mHouse=mHouse;
    }

    public
    String getmHouse ( ) {
        return mHouse;
    }

    public
    int getStdid ( ) {
        return stdid;
    }

    public
    int getClassid ( ) {
        return classid;
    }

    public
    String getAdmissionNo ( ) {
        return AdmissionNo;
    }

    public
    String getStudentname ( ) {
        return studentname;
    }

    public
    String getStudentclass ( ) {
        return studentclass;
    }

    public
    String getStudentdob ( ) {
        return studentdob;
    }

    public
    String getStudentAdd ( ) {
        return studentAdd;
    }

    public
    String getStudentMobileno ( ) {
        return studentMobileno;
    }

    public
    String getProfile_download_path ( ) {
        return profile_download_path;
    }
}
