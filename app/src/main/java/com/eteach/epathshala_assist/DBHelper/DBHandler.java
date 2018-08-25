package com.eteach.epathshala_assist.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.eteach.epathshala_assist.dataset.TBL_ATTANDENCE;
import com.eteach.epathshala_assist.dataset.TBL_EVENTS;
import com.eteach.epathshala_assist.dataset.TBL_FEES;
import com.eteach.epathshala_assist.dataset.TBL_FEES_PAYMENT_DETAILS;
import com.eteach.epathshala_assist.dataset.TBL_HOMEWORK;
import com.eteach.epathshala_assist.dataset.TBL_NOTI;
import com.eteach.epathshala_assist.dataset.TBL_REPORTCARD;
import com.eteach.epathshala_assist.dataset.TBL_STUDENT;
import com.eteach.epathshala_assist.dataset.TBL_SYLLABUS;
import com.eteach.epathshala_assist.dataset.TBL_TIMETABLE;
import com.eteach.epathshala_assist.dataset.TBL_TOPPERS;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shree on 24/07/2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    //Database Version
    private  static final int DATABASE_VER = 37;
    private static final String DATABASE_NAME = "DB_ePathshslaAssist";
    //DATA TABLE notifications
    private static final String TABLE_NOTI = "Tbl_notification";
    private static final String KEY_PK_ID = "pk_id";
    private static final String KEY_CLASSID = "titel";
    private static final String KEY_STDID = "message";
    private static final String KEY_NOTIFICATION_DATA = "Notification_Data";

    /*String CREATE_SAVENOTI_TABLE = "CREATE TABLE " + TABLE_NOTI + "(" +KEY_PK_ID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            +KEY_TITEL+" TEXT,"+KEY_MESSAGE+" TEXT,"+KEY_TIMESTAMP+" TEXT,"+KEY_NOTIFICATION_DATA+" TEXT"+")";*/
    String CREATE_SAVENOTI_TABLE = "CREATE TABLE " + TABLE_NOTI + "(" +KEY_PK_ID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            +KEY_CLASSID+" TEXT,"+KEY_STDID+" TEXT,"+KEY_NOTIFICATION_DATA+" TEXT"+")";

    //DATA Table Student Master
    private static final String TABLE_STUDENT_M  = "Tbl_Student_M";
    public static final String KEY_PK_STUDENT_ID = "pk_id";
    private static final String KEY_ADMISSION_NO = "STD_NO";
    private static final String KEY_STUDENT_DATA = "STUDENT_NAME";

    String CREATE_STUDENT_M_TABLE = "CREATE TABLE "
            + TABLE_STUDENT_M +"("
            + KEY_PK_STUDENT_ID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + KEY_ADMISSION_NO +" TEXT,"
            + KEY_STUDENT_DATA +" TEXT "+")";

    //DATA TABLE events
    private static final String TABLE_EVENTS = "Tbl_event";
    private static final String KEY_EVENT_PK_ID = "pk_id";
    private static final String KEY_EVENT_DATA = "EventName";

    String CREATE_EVENTS_TABLE = "CREATE TABLE "
            + TABLE_EVENTS +"("
            + KEY_EVENT_PK_ID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + KEY_EVENT_DATA +" TEXT "+")";

    ///DATA TABLE fees
    private static final String TABLE_FEES = "Tbl_fees";
    private static final String KEY_FEES_PK_ID = "pk_id";
    private static final String KEY_FEES_ADMISSION_NO = "STD_NO";
    private static final String KEY_FEES_DATA = "Feesdata";

    String CREATE_FEES_TABLE = "CREATE TABLE "
            + TABLE_FEES +"("
            + KEY_FEES_PK_ID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + KEY_FEES_ADMISSION_NO +" TEXT,"
            + KEY_FEES_DATA +" TEXT "+")";

    ///DATA TABLE fees payment
    private static final String TABLE_FEES_PAYMENT_DETAILS = "Tbl_fees_online_payment";
    private static final String KEY_FEES_PAYMENT_PK_ID = "pk_id";
    private static final String KEY_FEES_PAYMENT_ADMISSION_NO = "STD_NO";
    private static final String KEY_FEES_PAYMENT_DATA = "FeeSpaymentdata";

    String CREATE_FEES_PAYMENT_DETAILS_TABLE = "CREATE TABLE "
            + TABLE_FEES_PAYMENT_DETAILS +"("
            + KEY_FEES_PAYMENT_PK_ID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + KEY_FEES_PAYMENT_ADMISSION_NO +" TEXT,"
            + KEY_FEES_PAYMENT_DATA +" TEXT "+")";

    ///DATA TABLE toppers
    private static final String TABLE_TOPPERS = "Tbl_toppers";
    private static final String KEY_TOPPER_PK_ID = "pk_id";
    private static final String KEY_TOPPER_DATA = "Feesdata";

    String CREATE_TOPPERS_TABLE = "CREATE TABLE "
            + TABLE_TOPPERS +"("
            + KEY_TOPPER_PK_ID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + KEY_TOPPER_DATA +" TEXT "+")";

    //Data Table Attancdance Record
    private static final String TABLE_ATTENDANCE = "Tbl_attendance";
    private static final String KEY_ATTENDANCE_PK_ID = "pk_id";
    private static final String KEY_ATTENDANCE_STU_ID = "student_id";
    private static final String KEY_ATTENDANCE_MONTH = "at_month";
    private static final String KEY_ATTENDANCE_DATA = "attandancedata";

    String CREATE_ATTANDENCE_TABLE = "CREATE TABLE "
            + TABLE_ATTENDANCE +"("
            + KEY_ATTENDANCE_PK_ID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + KEY_ATTENDANCE_STU_ID +" TEXT,"
            + KEY_ATTENDANCE_MONTH +" TEXT,"
            + KEY_ATTENDANCE_DATA +" TEXT "+")";
    //Data Table Homework Record
    private static final String TABLE_HOMEWORKS = "Tbl_homework";
    private static final String KEY_HOMEWORKS_PK_ID = "pk_id";
    private static final String KEY_HOMEWORKS_CLASS_ID = "class_id";
    private static final String KEY_HOMEWORKS_DATA = "homework_data";

    String CREATE_HOMEWORKS_TABLE = "CREATE TABLE "
            + TABLE_HOMEWORKS +"("
            + KEY_HOMEWORKS_PK_ID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + KEY_HOMEWORKS_CLASS_ID +" TEXT,"
            + KEY_HOMEWORKS_DATA +" TEXT "+")";

    //Data Table Homework Record
    private static final String TABLE_CLASS = "Tbl_Class";
    private static final String KEY_CLASS_PK_ID = "pk_id";
    private static final String KEY_CLASS_DATA = "class_data";

    String CREATE_CLASS_TABLE = "CREATE TABLE "
            + TABLE_CLASS +"("
            + KEY_CLASS_PK_ID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + KEY_CLASS_DATA +" TEXT "+")";

    //Data Table Homework Record
    private static final String TABLE_SUBJECT = "Tbl_Subject";
    private static final String KEY_SUBJECT_PK_ID = "pk_id";
    private static final String KEY_SUBJECT_DATA = "subject_data";

    String CREATE_SUBJECT_TABLE = "CREATE TABLE "
            + TABLE_SUBJECT +"("
            + KEY_SUBJECT_PK_ID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + KEY_SUBJECT_DATA +" TEXT "+")";

    public DBHandler (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER,null);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SAVENOTI_TABLE    );
        db.execSQL ( CREATE_STUDENT_M_TABLE );
        db.execSQL ( CREATE_EVENTS_TABLE    );
        db.execSQL ( CREATE_FEES_TABLE      );
        db.execSQL ( CREATE_EXAM_REPORTCARD_TABLE );
        db.execSQL ( CREATE_TOPPERS_TABLE   );
        db.execSQL ( CREATE_ATTANDENCE_TABLE);
        db.execSQL ( CREATE_FEES_PAYMENT_DETAILS_TABLE );
        db.execSQL (CREATE_HOMEWORKS_TABLE  );
        db.execSQL (CREATE_CLASS_TABLE      );
        db.execSQL (CREATE_SUBJECT_TABLE    );
        db.execSQL ( CREATE_SYLLABUS_TABLE );
        db.execSQL ( CREATE_TABLE_TIMETABLE );
    }
    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        //do something
    }
    /**@Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_NOTI);
        onCreate(db);
    }*/
    //region Attendance
    public void AddAttandancesData(TBL_ATTANDENCE tbl_attandence)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ATTENDANCE_STU_ID,tbl_attandence.get_STDID().toLowerCase());
        values.put(KEY_ATTENDANCE_MONTH,tbl_attandence.get_ATMONTH());
        values.put(KEY_ATTENDANCE_DATA,tbl_attandence.get_ATTDATA());
        db.insert(TABLE_ATTENDANCE,null,values);
        db.close();
    }
    public void UpdateAttandanceData(TBL_ATTANDENCE tbl_attandence,String AddmissionNo)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ATTENDANCE_STU_ID,tbl_attandence.get_STDID());
        values.put(KEY_ATTENDANCE_MONTH,tbl_attandence.get_ATMONTH());
        values.put(KEY_ATTENDANCE_DATA,tbl_attandence.get_ATTDATA());
        String[] whereArgs = {AddmissionNo};
        db.update(TABLE_ATTENDANCE,values,KEY_ATTENDANCE_STU_ID+"=?",whereArgs);
        db.close();
    }
    public Cursor getStudentattandance(String AdminssionNo) // selecting single row
    {
        SQLiteDatabase db = this.getReadableDatabase ();
        String query = "SELECT "+ KEY_ATTENDANCE_MONTH+","+KEY_ATTENDANCE_DATA +" FROM "+ TABLE_ATTENDANCE +" WHERE "+ KEY_ATTENDANCE_STU_ID+ " = '" + AdminssionNo.toLowerCase()+"'";

        Cursor  cursor = db.rawQuery(query,null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public void deleteAllAttandance()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(" delete from " + TABLE_ATTENDANCE);
        db.close();
    }
    //endregion
    //region Notifications
    public void AddNoficationsData(TBL_NOTI tbl_noti)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NOTIFICATION_DATA,tbl_noti.get_NotificationData ());
        db.insert(TABLE_NOTI,null,values);
        db.close();
    }
    public List<TBL_NOTI>getAllnotificationsdata(){
        List<TBL_NOTI> messagelist = new ArrayList<TBL_NOTI>();
        String selectQuery="SELECT * FROM "+TABLE_NOTI;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                TBL_NOTI tblNoti = new TBL_NOTI();
                tblNoti.setId(Integer.parseInt(cursor.getString(0)));
                tblNoti.set_NotificationData(cursor.getString(3));
                messagelist.add(tblNoti);
            }while (cursor.moveToNext());
        }return messagelist;
    }
    public void addnotifiaction(TBL_NOTI tbl_noti)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CLASSID,tbl_noti.getTitle());
        values.put(KEY_STDID,tbl_noti.getMessage());
        values.put(KEY_NOTIFICATION_DATA,tbl_noti.get_timestamp());
        db.insert(TABLE_NOTI,null,values);
        db.close();
    }
    public Cursor getNotification(String stdid) // selecting single row
    {
        SQLiteDatabase db = this.getReadableDatabase ();
        String query = "SELECT "+ KEY_NOTIFICATION_DATA +" FROM "+ TABLE_NOTI +" WHERE "+ KEY_STDID + " = '" + stdid+"'";

        Cursor  cursor = db.rawQuery(query,null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public TBL_NOTI getTblNoti(int pk_id) // selecting single row
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NOTI,new String[]{KEY_PK_ID,KEY_CLASSID,KEY_STDID,KEY_NOTIFICATION_DATA},
                KEY_PK_ID+"=?",new String[]{String.valueOf(pk_id)},null,null,null);
        if (cursor!=null)
            cursor.moveToFirst();
        TBL_NOTI message = new TBL_NOTI(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3));
        return message;
    }
    public void updatenotifiaction (TBL_NOTI tbl_noti,String stdid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CLASSID,tbl_noti.getTitle());
        values.put(KEY_STDID,tbl_noti.getMessage());
        values.put(KEY_NOTIFICATION_DATA,tbl_noti.get_timestamp());
        String[] whereArgs = {stdid};
        db.update(TABLE_NOTI,values,KEY_STDID+"=?",whereArgs);
        db.close();
    }
    public List<TBL_NOTI>getAllnotifications()
    {
        List<TBL_NOTI> messagelist = new ArrayList<TBL_NOTI>();
        String selectQuery="SELECT * FROM "+TABLE_NOTI;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                TBL_NOTI tblNoti = new TBL_NOTI();
                tblNoti.setId(Integer.parseInt(cursor.getString(0)));
                tblNoti.setTitle(cursor.getString(1));
                tblNoti.setMessage(cursor.getString(2));
                tblNoti.set_timestamp(cursor.getString(3));
                messagelist.add(tblNoti);
            }while (cursor.moveToNext());
        }return messagelist;
    }
    public void deletesinglenotification(TBL_NOTI tblnoti)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTI,KEY_PK_ID+"=?",new String[]{String.valueOf(tblnoti.getPk_id())});
        db.close();
    }
    public void deleteAllnotifications()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(" delete from " + TABLE_NOTI);
        db.close();
    }
    //endregion
    //region Student Data
    public void AddStudentData(TBL_STUDENT tbl_student)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ADMISSION_NO,tbl_student.get_AdmissionNo ().toLowerCase());
        values.put(KEY_STUDENT_DATA,tbl_student.get_StudentData ());
        db.insert(TABLE_STUDENT_M,null,values);
        db.close();
    }
    public String getStudent(String AdminssionNo) // selecting single row
    {
        Cursor cursor = null;
        String s = "";
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT " + KEY_STUDENT_DATA + " FROM " + TABLE_STUDENT_M + " WHERE " + KEY_ADMISSION_NO.trim() + " = '" + AdminssionNo.toLowerCase() + "'";

            cursor = db.rawQuery(query, null);

            if (cursor != null ) {
                cursor.moveToFirst();
                s = cursor.getString(cursor.getColumnIndex("STUDENT_NAME"));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
    public List<TBL_STUDENT>getAllStudentData(){
        List<TBL_STUDENT> Studentlist = new ArrayList<TBL_STUDENT>();
        String selectQuery="SELECT DISTINCT * FROM "+ TABLE_STUDENT_M;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                TBL_STUDENT tbl_student = new TBL_STUDENT ();
                tbl_student.setId(Integer.parseInt(cursor.getString(0)));
                tbl_student.set_AdmissionNo (cursor.getString(1));
                tbl_student.set_StudentData (cursor.getString(2));
                Studentlist.add(tbl_student);
            }while (cursor.moveToNext());
        }return Studentlist;
    }
    public ArrayList<TBL_STUDENT>getAllStudentDataList(){
        List<TBL_STUDENT> Studentlist = new ArrayList<TBL_STUDENT>();
        String selectQuery="SELECT "+ KEY_ADMISSION_NO+ " FROM "+ TABLE_STUDENT_M;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                TBL_STUDENT tbl_student = new TBL_STUDENT ();
                tbl_student.set_AdmissionNo (cursor.getString(0));
                Studentlist.add(tbl_student);
            }while (cursor.moveToNext());
        }return ( ArrayList<TBL_STUDENT> ) Studentlist;
    }
    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(" delete from " + TABLE_STUDENT_M);
        db.close();
    }
    //endregion
    //region Events Data
    public void AddEventsData(TBL_EVENTS tbl_events)
    {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(KEY_EVENT_DATA,tbl_events.get_EventData ());
    db.insert(TABLE_EVENTS,null,values);
    db.close();
    }
    public List<TBL_EVENTS> getAllEventsData(){
    List<TBL_EVENTS> Eventlist = new ArrayList<TBL_EVENTS>();
    String selectQuery="SELECT * FROM "+ TABLE_EVENTS;
    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(selectQuery,null);
    if (cursor.moveToFirst()){
        do {
            TBL_EVENTS tbl_student = new TBL_EVENTS ();
            tbl_student.set_EventData (cursor.getString(1));
            Eventlist.add(tbl_student);
        }while (cursor.moveToNext());
    }return Eventlist;
}
    public void UpdateEventsData (TBL_EVENTS tbl_events) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EVENT_DATA,tbl_events.get_EventData ());
        String[] whereArgs = {"0"};
        db.update(TABLE_EVENTS,values,KEY_EVENT_PK_ID+"=?",whereArgs);
        db.close();
    }
    public void deleteAllEvents()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(" delete from " + TABLE_EVENTS);
        db.close();
    }
    //endregion
    //region Fees Data
    public void AddFeesData(TBL_FEES tbl_fees)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FEES_ADMISSION_NO,tbl_fees.get_AdmissionNo ().toLowerCase());
        values.put(KEY_FEES_DATA,tbl_fees.get_FeesData ());
        db.insert(TABLE_FEES,null,values);
        db.close();
    }
    public void updateFees (TBL_FEES tbl_fees,String stdid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FEES_ADMISSION_NO,tbl_fees.get_AdmissionNo ().toLowerCase());
        values.put(KEY_FEES_DATA,tbl_fees.get_FeesData ());
        String[] whereArgs = {stdid};
        db.update(TABLE_FEES,values,KEY_FEES_ADMISSION_NO+"=?",whereArgs);
        db.close();
    }
    public List<TBL_FEES> getAllFeesData(){
        List<TBL_FEES> feesList = new ArrayList<TBL_FEES>();
        String selectQuery="SELECT * FROM "+ TABLE_FEES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                TBL_FEES tbl_student = new TBL_FEES ();
                tbl_student.set_FeesDATA (cursor.getString(2));
                feesList.add(tbl_student);
            }while (cursor.moveToNext());
        }return feesList;
    }
    public Cursor getStudentFees(String AdminssionNo) // selecting single row
    {
        SQLiteDatabase db = this.getReadableDatabase ();
        String query = "SELECT "+ KEY_FEES_DATA +" FROM "+ TABLE_FEES +" WHERE "+ KEY_FEES_ADMISSION_NO + " = '" + AdminssionNo.toLowerCase()+"'";

        Cursor  cursor = db.rawQuery(query,null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public void deleteAllFees()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(" delete from " + TABLE_FEES);
        db.close();
    }
    //endregion
    //region Fees Payment Details
    public void AddFeesPaymentDetailsData(TBL_FEES_PAYMENT_DETAILS tblFeesPaymentDetails)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FEES_PAYMENT_ADMISSION_NO ,tblFeesPaymentDetails.get_AdmissionNo ().toLowerCase());
        values.put(KEY_FEES_DATA,tblFeesPaymentDetails.get_FeespaymentDATA ());
        db.insert(KEY_FEES_PAYMENT_DATA ,null,values);
        db.close();
    }
    public void updateFeesPaymentDetails (TBL_FEES_PAYMENT_DETAILS tblFeesPaymentDetails,String stdid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FEES_PAYMENT_ADMISSION_NO,tblFeesPaymentDetails.get_AdmissionNo ().toLowerCase());
        values.put(KEY_FEES_PAYMENT_DATA,tblFeesPaymentDetails.get_FeespaymentDATA ());
        String[] whereArgs = {stdid};
        db.update(TABLE_FEES_PAYMENT_DETAILS,values,KEY_FEES_PAYMENT_ADMISSION_NO+"=?",whereArgs);
        db.close();
    }
    public List<TBL_FEES_PAYMENT_DETAILS> getAllFeesPaymentDetailsData(){
        List<TBL_FEES_PAYMENT_DETAILS> feesList = new ArrayList<TBL_FEES_PAYMENT_DETAILS>();
        String selectQuery="SELECT * FROM "+ TABLE_FEES_PAYMENT_DETAILS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                TBL_FEES_PAYMENT_DETAILS tbl_student = new TBL_FEES_PAYMENT_DETAILS ();
                tbl_student.set_FeespaymentDATA (cursor.getString(2));
                feesList.add(tbl_student);
            }while (cursor.moveToNext());
        }return feesList;
    }
    public Cursor getStudentFeesPaymentDetails(String AdminssionNo) // selecting single row
    {
        SQLiteDatabase db = this.getReadableDatabase ();
        String query = "SELECT "+ KEY_FEES_PAYMENT_DATA +" FROM "+ TABLE_FEES_PAYMENT_DETAILS +" WHERE "+ KEY_FEES_PAYMENT_ADMISSION_NO + " = '" + AdminssionNo.toLowerCase()+"'";

        Cursor  cursor = db.rawQuery(query,null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public void deleteAllFeesPaymentDetails()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(" delete from " + TABLE_FEES_PAYMENT_DETAILS);
        db.close();
    }
    //endregion
    //region Report Card
    private static final String TABLE_EXAM_REPORTCARD = "Tbl_Exam_Report_Card";
    private static final String KEY_REPORTCARD_PK_ID = "pk_id";
    private static final String KEY_REPORTCARD_ADMISSION_NO = "STD_NO";
    private static final String KEY_REPORTCARD_DATA = "Exam_Report_Card_data";

    String CREATE_EXAM_REPORTCARD_TABLE = "CREATE TABLE "
            + TABLE_EXAM_REPORTCARD +"("
            + KEY_REPORTCARD_PK_ID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + KEY_REPORTCARD_ADMISSION_NO +" TEXT,"
            + KEY_REPORTCARD_DATA +" TEXT "+")";

    public void AddReportData(TBL_REPORTCARD tbl_reportcard)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_REPORTCARD_ADMISSION_NO,tbl_reportcard.get_AdmissionNo ().toLowerCase());
        values.put(KEY_REPORTCARD_DATA,tbl_reportcard.get_ReportCardDATA ());
        db.insert(TABLE_EXAM_REPORTCARD,null,values);
        db.close();
    }
    public List<TBL_REPORTCARD> getAllReportData(){
        List<TBL_REPORTCARD> reportcardList = new ArrayList<TBL_REPORTCARD>();
        String selectQuery="SELECT * FROM "+ TABLE_EXAM_REPORTCARD;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                TBL_REPORTCARD tbl_reportcard = new TBL_REPORTCARD ();
                tbl_reportcard.set_ReportCardDATA (cursor.getString(2));
                reportcardList.add(tbl_reportcard);
            }while (cursor.moveToNext());
        }return reportcardList;
    }
    public String getStudentReportCard(String AdminssionNo) // selecting single row
    {
        Cursor cursor = null;
        String s = "";
        SQLiteDatabase db = this.getReadableDatabase ();
        String query = "SELECT "+ KEY_REPORTCARD_DATA +" FROM "+ TABLE_EXAM_REPORTCARD +" WHERE "+ KEY_REPORTCARD_ADMISSION_NO + " = '" + AdminssionNo.toLowerCase()+"'";

          cursor = db.rawQuery(query,null);

        if (cursor != null) {
            cursor.moveToFirst();
            s = cursor.getString(cursor.getColumnIndex("Exam_Report_Card_data"));
        }
        return s;
    }

    public void UpdateReportData (TBL_REPORTCARD tbl_reportcard,String stdid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_REPORTCARD_ADMISSION_NO,tbl_reportcard.get_AdmissionNo ().toLowerCase());
        values.put(KEY_REPORTCARD_DATA,tbl_reportcard.get_ReportCardDATA ());
        String[] whereArgs = {stdid};
        db.update(TABLE_EXAM_REPORTCARD,values,KEY_REPORTCARD_ADMISSION_NO+"=?",whereArgs);
        db.close();
    }

    public void deleteAllReportCards()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(" delete from " + TABLE_EXAM_REPORTCARD);
        db.close();
    }
    //endregion

    //region syllabus
    private static final String TABLE_SYLLABUS = "Tbl_syllabus";
    private static final String KEY_SYLLABUS_PK_ID = "pk_id";
    private static final String KEY_SYLLABUS_CID = "cid";
    private static final String KEY_SYLLABUS_DATA = "syllbus_data";

    String CREATE_SYLLABUS_TABLE = "CREATE TABLE "
            + TABLE_SYLLABUS  +"("
            + KEY_SYLLABUS_PK_ID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + KEY_SYLLABUS_CID  +" TEXT,"
            + KEY_SYLLABUS_DATA +" TEXT "+")";

    public void AddSyllabusData(TBL_SYLLABUS tbl_syllabus)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SYLLABUS_CID,tbl_syllabus.get_AdmissionNo ());
        values.put(KEY_SYLLABUS_DATA,tbl_syllabus.get_ReportCardDATA ());
        db.insert(TABLE_SYLLABUS,null,values);
        db.close();
    }
    public List<TBL_SYLLABUS> getAllSyllabusData(){
        List<TBL_SYLLABUS> reportcardList = new ArrayList<TBL_SYLLABUS>();
        String selectQuery="SELECT * FROM "+ TABLE_SYLLABUS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                TBL_SYLLABUS tbl_syllabus = new TBL_SYLLABUS ();
                tbl_syllabus.set_ReportCardDATA (cursor.getString(2));
                reportcardList.add(tbl_syllabus);
            }while (cursor.moveToNext());
        }return reportcardList;
    }
    public String getStudentsyllabus(String cid) // selecting single row
    {
        Cursor cursor = null;
        String s = "";
        SQLiteDatabase db = this.getReadableDatabase ();
        String query = "SELECT "+ KEY_SYLLABUS_DATA +" FROM "+ TABLE_SYLLABUS +" WHERE "+ KEY_SYLLABUS_CID + " = '" + cid+"'";

        cursor = db.rawQuery(query,null);

        if (cursor != null) {
            cursor.moveToFirst();
            s = cursor.getString(cursor.getColumnIndex("syllbus_data"));
        }
        return s;
    }

    public void UpdateSyllabusData (TBL_REPORTCARD tbl_reportcard,String cid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SYLLABUS_CID,tbl_reportcard.get_AdmissionNo ().toLowerCase());
        values.put(KEY_SYLLABUS_DATA,tbl_reportcard.get_ReportCardDATA ());
        String[] whereArgs = {cid};
        db.update(TABLE_SYLLABUS,values,KEY_SYLLABUS_CID+"=?",whereArgs);
        db.close();
    }

    public void deleteAllsyllabus()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(" delete from " + TABLE_SYLLABUS);
        db.close();
    }
    //endregion

    //region timetable
    private static final String TABLE_TIMETABLE = "Tbl_timetable";
    private static final String KEY_TIMETABLE_PK_ID = "pk_id";
    private static final String KEY_TIMETABLE_CID = "cid";
    private static final String KEY_TIMETABLE_DATA = "timetable_data";

    String CREATE_TABLE_TIMETABLE = "CREATE TABLE "
            + TABLE_TIMETABLE  +"("
            + KEY_TIMETABLE_PK_ID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + KEY_TIMETABLE_CID  +" TEXT,"
            + KEY_TIMETABLE_DATA +" TEXT "+")";

    public void AddtimetableData(TBL_TIMETABLE tbl_timetable)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TIMETABLE_CID,tbl_timetable.get_AdmissionNo ().toLowerCase());
        values.put(KEY_TIMETABLE_DATA,tbl_timetable.get_ReportCardDATA ());
        db.insert(TABLE_TIMETABLE,null,values);
        db.close();
    }
    public List<TBL_TIMETABLE> getAlltimetableData(){
        List<TBL_TIMETABLE> reportcardList = new ArrayList<TBL_TIMETABLE>();
        String selectQuery="SELECT * FROM "+ TABLE_SYLLABUS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                TBL_TIMETABLE tbl_timetable = new TBL_TIMETABLE ();
                tbl_timetable.set_ReportCardDATA (cursor.getString(2));
                reportcardList.add(tbl_timetable);
            }while (cursor.moveToNext());
        }return reportcardList;
    }
    public String getStudenttimetable(String cid) // selecting single row
    {
        Cursor cursor = null;
        String s = "";
        SQLiteDatabase db = this.getReadableDatabase ();
        String query = "SELECT "+ KEY_TIMETABLE_DATA +" FROM "+ TABLE_TIMETABLE +" WHERE "+ KEY_TIMETABLE_CID + " = '" + cid.toLowerCase()+"'";

        cursor = db.rawQuery(query,null);

        if (cursor != null) {
            cursor.moveToFirst();
            s = cursor.getString(cursor.getColumnIndex("timetable_data"));
        }
        return s;
    }

    public void UpdatetimetableData ( TBL_TIMETABLE tbl_timetable, String stdid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TIMETABLE_CID,tbl_timetable.get_AdmissionNo ().toLowerCase());
        values.put(KEY_TIMETABLE_DATA,tbl_timetable.get_ReportCardDATA ());
        String[] whereArgs = {stdid};
        db.update(TABLE_TIMETABLE ,values,KEY_TIMETABLE_CID+"=?",whereArgs);
        db.close();
    }

    public void deleteAlltimetable()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(" delete from " + TABLE_TIMETABLE);
                   db.close();
    }
    //endregion

    //region Toppers Data
    public void AddToppersData(TBL_TOPPERS tbl_toppers)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TOPPER_DATA,tbl_toppers.get_ToppersData());
        db.insert(TABLE_TOPPERS,null,values);
        db.close();
    }
    public List<TBL_TOPPERS> getAlltoppersData(){
        List<TBL_TOPPERS> tbl_toppersList = new ArrayList<TBL_TOPPERS>();
        String selectQuery="SELECT * FROM "+ TABLE_TOPPERS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                TBL_TOPPERS tbl_toppers = new TBL_TOPPERS ();
                tbl_toppers.set_ToppersData (cursor.getString(1));
                tbl_toppersList.add(tbl_toppers);
            }while (cursor.moveToNext());
        }return tbl_toppersList;
    }

    public void deleteAlltopperss()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(" delete from " + TABLE_TOPPERS);
        db.close();
    }


    //endregion
    //region Homework Data
    public void AddHomeWorkData ( TBL_HOMEWORK tbl_homework ) {
        SQLiteDatabase db = this.getWritableDatabase ( );
        ContentValues values = new ContentValues ( );
        values.put ( KEY_HOMEWORKS_CLASS_ID, tbl_homework.get_classid ( ) );
        values.put ( KEY_HOMEWORKS_DATA, tbl_homework.get_data ( ) );
        db.insert ( TABLE_HOMEWORKS, null, values );
        db.close ( );
    }

    public Cursor getAllHomeWorkData ( String cid ) {
        SQLiteDatabase db = this.getReadableDatabase ();
        String query = "SELECT "+ KEY_HOMEWORKS_DATA +" FROM "+ TABLE_HOMEWORKS +" WHERE "+ KEY_HOMEWORKS_CLASS_ID + " = '" + cid+"'";
        Cursor  cursor = db.rawQuery(query,null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void UpdateHomeworkData(TBL_HOMEWORK tbl_homework,String cid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_HOMEWORKS_CLASS_ID,tbl_homework.get_classid ( ));
        values.put(KEY_HOMEWORKS_DATA, tbl_homework.get_data ( ));
        String[] whereArgs = {cid};
        db.update(TABLE_HOMEWORKS,values,KEY_HOMEWORKS_CLASS_ID+"=?",whereArgs);
        db.close();
    }

    public void deleteAllHomeworks()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(" delete from " + TABLE_HOMEWORKS);
        db.close();
    }
    //endregion
}
