package com.eteach.epathshala_assist;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eteach.epathshala_assist.DBHelper.DBHandler;
import com.eteach.epathshala_assist.Utility.Constants;
import com.eteach.epathshala_assist.Webservice.AttendanceWebservices;
import com.eteach.epathshala_assist.Webservice.EventWebservices;
import com.eteach.epathshala_assist.Webservice.ExamReportCardWebservices;
import com.eteach.epathshala_assist.Webservice.Fees_Webservices;
import com.eteach.epathshala_assist.Webservice.HomeworkWebservices;
import com.eteach.epathshala_assist.Webservice.LoginWebservice;
import com.eteach.epathshala_assist.Webservice.NotificationsWebservices;
import com.eteach.epathshala_assist.Webservice.SyllabusWebservices;
import com.eteach.epathshala_assist.Webservice.TimeTableWebservices;
import com.eteach.epathshala_assist.Webservice.ToppersWebservices;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {
    public boolean allgranted = false;
    private EditText et_Student_id, et_School_id;
    public Button btn_login;
    public String School_id, Student_id, FCM_token;
    Constants constants = new Constants();
    private LoginWebservice loginWebservice;
    private Fees_Webservices fees_webservices;
    private ExamReportCardWebservices examReportCardWebservices;
    private ToppersWebservices toppersWebservices;
    private NotificationsWebservices notificationsWebservices;
    private AttendanceWebservices attendanceWebservices;
    SweetAlertDialog pDialog;
    private boolean sentToSettings = false;
    private SharedPreferences permissionStatus;
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    String[] permissionsRequired = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_SMS};

    AlertDialog.Builder builder;
    DBHandler dbHandler;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
       // FirebaseApp.initializeApp(this);
        FCM_token = FirebaseInstanceId.getInstance().getToken();
        et_School_id = (EditText) findViewById(R.id.input_schoolname);
        et_Student_id = (EditText) findViewById(R.id.input_stdno);
        btn_login = (Button) findViewById(R.id.btn_login);
        permissionStatus = getSharedPreferences("permissionStatus",MODE_PRIVATE);
        RequestPermission();
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "ePathshala");
        if (!file.exists())
            file.mkdir();
        LoginRequest();
        CheckFCMToken();//onBackPressed();
    }
    private void CheckFCMToken ( ) {
        try {
            if (FCM_token == null) {
                FCM_token = FirebaseInstanceId.getInstance().getToken();
            }else {

            }
        }catch (Exception e)
        {e.printStackTrace();}
    }
    @Override
    public void onRequestPermissionsResult (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_CALLBACK_CONSTANT){
            //check if all permissions are granted
            for(int i=0;i<grantResults.length;i++){
                if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }
            if(allgranted){
                //proceedAfterPermission();
            } else if(ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,permissionsRequired[2])){

                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Camera and Location permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(LoginActivity.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                Toast.makeText(getBaseContext(),"Unable to get Permission",Toast.LENGTH_LONG).show();
            }
        }
    }
    public void getDeviceImei ( ) {

        constants.mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        constants.deviceid = constants.mTelephonyManager.getDeviceId();

    }
    private void RequestPermission() {
        try {
            if (ActivityCompat.checkSelfPermission(LoginActivity.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(LoginActivity.this, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(LoginActivity.this, permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(LoginActivity.this, permissionsRequired[3]) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(LoginActivity.this, permissionsRequired[4]) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(LoginActivity.this, permissionsRequired[5]) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(LoginActivity.this, permissionsRequired[6]) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, permissionsRequired[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, permissionsRequired[1])
                        || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, permissionsRequired[2])
                        || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, permissionsRequired[3])
                        || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, permissionsRequired[4])
                        || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, permissionsRequired[5])
                        || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, permissionsRequired[6]) ) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("Need Multiple Permissions");
                    builder.setMessage("This app needs Camera and Location permissions.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick (DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(LoginActivity.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick (DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else if (permissionStatus.getBoolean(permissionsRequired[0], false)) {
                    //Previously Permission Request was cancelled with 'Dont Ask Again',
                    // Redirect to Settings after showing Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("Need Multiple Permissions");
                    builder.setMessage("This app needs Camera and Location permissions.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick (DialogInterface dialog, int which) {
                            dialog.cancel();
                            sentToSettings = true;
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                            Toast.makeText(getBaseContext(), "Go to Permissions to Grant  Camera and Location", Toast.LENGTH_LONG).show();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick (DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else {
                    //just request the permission
                    ActivityCompat.requestPermissions(LoginActivity.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                }
                SharedPreferences.Editor editor = permissionStatus.edit();
                editor.putBoolean(permissionsRequired[0], true);
                editor.commit();
            } else {
                allgranted = true;
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(LoginActivity.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
            }
        }
    }
    @Override
    protected void onPostResume ( ) {
        super.onPostResume();
        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(LoginActivity.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
            }
        }
    }
    private void makepermisson ( ) {
        try {
            ActivityCompat.requestPermissions ( this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1 );
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE},1);
        } catch (Exception e) {
            e.printStackTrace ( );
        }
    }
    private void LoginRequest ( ) {
        btn_login.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick (View v) {
                if(allgranted) {
                    ProcessLoginRequest ( );
                }
                else {
                    RequestPermission();
                }
            }
        } );
    }
    @Override
    protected void onDestroy ( ) {
        super.onDestroy ( );
        constants.LOGIN_STATUS = false;
        constants.ADMISSION_NO = null;
    }
    @Override
    public void onBackPressed ( ) {
        {
            {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                    builder = new AlertDialog.Builder(LoginActivity.this);
                }
                else
                {
                    builder = new AlertDialog.Builder(LoginActivity.this, AlertDialog.BUTTON_NEUTRAL);
                }
                builder.setTitle("Thank You");
                builder.setMessage("Thank You For Using Our Application Please Give Us Your Suggestions and Feedback ");
                builder.setNegativeButton("RATE US",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.eteach.epathshalaassist")); // Add package name of your application
                                startActivity(intent);
                                Toast.makeText(LoginActivity.this, "Thank you for your Rating",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.setPositiveButton("QUIT",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {
                                finishAffinity();
                            }
                        });

                builder.show();
            }
        }
    }
    public boolean checkstudentexist(String _AdmissionNo) {
        try {
            dbHandler = new DBHandler( getApplicationContext() );
            String j = "";
            //Cursor studentList =  dbHandler.getStudent (_AdmissionNo) ;
            //j = studentList.getString ( 0 );
            j = dbHandler.getStudent (_AdmissionNo) ;
            JSONArray jsonArray = new JSONArray(j);
            for(int i =0 ;i<jsonArray.length();i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
               String Admissionno = jsonObject.getString("V_AdmissionNo").toString();
               return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    private void initCheckLoginDetails ( ) {
        //SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(null,0);
    try {
    if (constants.isNetworkConnected(getApplicationContext())) {
        new StudentLoginTask().execute();
            /*new DownloadEvents().execute (  );
            new DownloadStudentFeesData().execute();
            new DownloadReportCardData().execute();*/
    } else {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Something went wrong!")
                .setContentText("Please Connect to the Internet")
                .setConfirmText("Ok!")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick (SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.cancel();
                    }
                }).show();
    }
    }catch (Exception e)
    {
    e.printStackTrace();
    }
    }
    private void ProcessLoginRequest ( ) {
        try {
            if (validatefields()) {
                School_id = et_School_id.getText().toString().trim();
                Student_id = et_Student_id.getText().toString().trim();
                if (checkstudentexist(Student_id)) {
                    pDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText("Student is Already Loggedin!")
                            .setContentText("ePathShala School Assist")
                            .show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run ( ) {
                            pDialog.dismiss();
                        }
                    }, 5000);
                } else {
                    initCheckLoginDetails();
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private boolean validatefields ( ) {
        if (TextUtils.isEmpty ( et_School_id.getText ( ).toString ( ).trim ( ) )) {
            et_School_id.setError ( "Please Enter School Name" );
            et_School_id.requestFocus ( );
            return false;
        }
        if (TextUtils.isEmpty ( et_Student_id.getText ( ).toString ( ).trim ( ) )) {
            et_Student_id.setError ( "Please Enter School Admission No." );
            et_Student_id.requestFocus ( );
            return false;
        }
        return true;
    }
    private class StudentLoginTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute ( ) {
            try {
                pDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.setTitleText("Cheking Login Details!")
                        .setContentText("ePathShala School Assist")
                        .show();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        @Override
        protected Boolean doInBackground (Void... params) {
            try {
                loginWebservice = new LoginWebservice ( getApplicationContext ( ) );
                constants.LOGIN_STATUS = loginWebservice.STUDENT_LOGIN ( School_id, Student_id );
                constants.ADMISSION_NO = Student_id.toUpperCase();
            } catch (Exception ex) {
                constants.error = ex.toString ( );
            }
            return constants.LOGIN_STATUS;
        }
        @Override
        protected void onPostExecute (Boolean login_status) {
            super.onPostExecute ( login_status );
            if (constants.LOGIN_STATUS == true && Constants.isEmployee.equals("s")) {
                constants.ADMISSION_NO = Student_id;
                new DownloadStudentData().execute();
                try {
                    Intent StartHomeActivity = new Intent ( LoginActivity.this, MainActivity.class );
                    new FCM_Registration().execute (  );
                    pDialog.dismissWithAnimation();
                    startActivity ( StartHomeActivity );
                    Constants.isLogin = 0;
                    finish ( );
                } catch (Exception e) {
                    e.printStackTrace ( );
                }
            } else {
                pDialog.changeAlertType(1);
                pDialog.setTitleText ( "Login Failed!" )
                        .setContentText ( "ePathShala School Assist" )
                        .show ( );
                new Handler ( ).postDelayed ( new Runnable ( ) {
                    @Override
                    public void run ( ) {
                        pDialog.dismiss ( );
                    }
                }, 5000 );
                Toast.makeText ( getBaseContext ( ), "Please Enter Valid Student Details", Toast.LENGTH_LONG ).show ( );
            }
        }

    }
    private class DownloadStudentData extends AsyncTask {
        @Override
        protected void onPreExecute ( ) {

        }

        @Override
        protected Object doInBackground (Object[] params) {
            try {
                EventWebservices eventWebservices = new EventWebservices(getApplicationContext());
                eventWebservices.GETALLEVENTS(1);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            try {
                Fees_Webservices fees_webservices = new Fees_Webservices(getApplicationContext());
                fees_webservices.GETALLFEES(Student_id, 1);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            try {
                ExamReportCardWebservices examReportCardWebservices = new ExamReportCardWebservices(getApplicationContext());
                examReportCardWebservices.GETALLREPORTCARD(Student_id);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            try {
                ToppersWebservices toppersWebservices = new ToppersWebservices(getApplicationContext());
                toppersWebservices.GETALLTOPPERS();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            try {
                NotificationsWebservices notificationsWebservices = new NotificationsWebservices(getApplicationContext());
                // notificationsWebservices.GETALLNOTIFICATIONS(user.get( utilitys.KEY_STD_CLASS_ID) ,user.get( utilitys.KEY_STD_ID));
                notificationsWebservices.GETALLNOTIFICATIONS(Constants.STUDENT_CLASS_ID ,Constants.STUDENT_ID,1);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            try{
                AttendanceWebservices  attendanceWebservices = new AttendanceWebservices(getApplicationContext());
                String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
                attendanceWebservices.GETALLATTENDANCE(Student_id,date,1);//query 3 for update data
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            try{
                HomeworkWebservices homeworkWebservices = new HomeworkWebservices (getApplicationContext());
                homeworkWebservices.GetHomeworklist(Constants.STUDENT_CLASS_ID,1);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            try{
                SyllabusWebservices syllabusWebservices = new SyllabusWebservices ( getApplicationContext());
                syllabusWebservices.GETALLSYLABUS ( Integer.parseInt ( Constants.STUDENT_CLASS_ID ), 1);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            try{
                TimeTableWebservices timeTableWebservices = new TimeTableWebservices ( getApplicationContext());
                timeTableWebservices.GETALLTIMETABLE ( Constants.STUDENT_CLASS_ID , 1);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return 0;
        }

        @Override
        protected void onPostExecute (Object o) {

        }
    }
    private class FCM_Registration extends AsyncTask<Void, Void, Boolean> {
        String result;

        @Override
        protected
        Boolean doInBackground ( Void... voids ) {
            Boolean rStatus = false;
            try {
                if (constants.deviceid==""){
                    result = loginWebservice.Register_FCM ( constants.STUDENT_SESSION_ID, Integer.valueOf(constants.STUDENT_CLASS_ID),Integer.valueOf( constants.STUDENT_ID),
                                                            Student_id,Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID) , FCM_token, true, 1 );
                    if ( result.equals ( "OK" ) )
                    {
                        return rStatus =  true;
                    }
                }
                else {
                    result = loginWebservice.Register_FCM ( constants.STUDENT_SESSION_ID, Integer.valueOf(constants.STUDENT_CLASS_ID),Integer.valueOf( constants.STUDENT_ID),
                                                            Student_id,constants.deviceid , FCM_token, true, 1 );}
                if ( result.equals ( "OK" ) )
                {
                    return rStatus = true;
                }
            } catch (Exception ex) {
                constants.error = ex.toString ( );
                Constants.FCMStoredOnServer = 0;
                return false;
            }
            return Boolean.valueOf ( result );
        }

        @Override
        protected
        void onPostExecute ( Boolean aBoolean ) {
            super.onPostExecute ( aBoolean );
            if ( aBoolean == true )
            {
                Constants.FCMStoredOnServer = 1;
            }
        }
    }
}


