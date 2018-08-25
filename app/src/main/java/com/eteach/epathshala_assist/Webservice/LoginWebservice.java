package com.eteach.epathshala_assist.Webservice;

import android.content.Context;
import android.webkit.WebView;

import com.eteach.epathshala_assist.DBHelper.DBHandler;
import com.eteach.epathshala_assist.Utility.Constants;
import com.eteach.epathshala_assist.Utility.utilitys;
import com.eteach.epathshala_assist.dataset.TBL_STUDENT;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by shree on 05/07/2017.
 */


public class LoginWebservice {
    public String WebRespose ;
    utilitys utilitys;
    Context _context;
    DBHandler dbHandler ;
    public LoginWebservice(Context context)
    {
        this._context=context;
    }

    public Boolean STUDENT_LOGIN(String school_id, String Admission_no)
    {
        SoapObject LoginRequest = new SoapObject( Constants.WEB_SERVER_ADD,Constants.LOGIN_ACTION);
        PropertyInfo PI_schoolid = new PropertyInfo();
        PI_schoolid.setName("school");
        PI_schoolid.setValue(school_id);
        PI_schoolid.setType(String.class);
        LoginRequest.addProperty(PI_schoolid);

        PropertyInfo PI_student_id = new PropertyInfo();
        PI_student_id.setName("stdno");
        PI_student_id.setValue(Admission_no);
        PI_student_id.setType(String.class);
        LoginRequest.addProperty(PI_student_id);

        PropertyInfo PI_student_pass = new PropertyInfo();
        PI_student_pass.setName("pass");
        PI_student_pass.setValue("-");
        PI_student_pass.setType(String.class);
        LoginRequest.addProperty(PI_student_pass);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(LoginRequest);
        HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.WURL+Constants.WURL_PATH);
        httpTransportSE.debug = true;
        httpTransportSE.setXmlVersionTag( "<?xml version=\"1.0\" encoding=\"utf-8\"?>" );
        try {
            httpTransportSE.call(Constants.WEB_SERVER_ADD+Constants.LOGIN_ACTION,envelope);
            SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse();
            WebRespose = soapPrimitive.toString();
            Constants.isEmployee = Admission_no.substring(0,1).toLowerCase();
            if (Constants.isEmployee.equals("s")) {
                Constants.STUDENT_DATA = WebRespose;//not required check before delete
                JSONArray jsonArray = new JSONArray(WebRespose);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject j = jsonArray.getJSONObject(i);
                    Constants.ADMISSION_NO = j.getString("V_AdmissionNo").toString();
                    Constants.STUDENT_ID = String.valueOf(j.getInt("Pk_Student_M"));
                    Constants.STUDENT_SESSION_ID = j.getInt("Fk_SessionId");
                    Constants.STUDENT_CLASS_ID = String.valueOf(j.getInt("Fk_ClassId"));
                    Constants.STUDENT_BUS_ROUTE = j.getString("RouteName");


                    if (Admission_no.toUpperCase().equals(Constants.ADMISSION_NO)) {
                        utilitys = new utilitys(_context);
                        utilitys.storeStudentRoute(j.getString("RouteName"));
                        utilitys.createLoginSession(school_id, Admission_no,
                                                    String.valueOf(j.getInt("Pk_Student_M")),
                                                    String.valueOf(j.getInt("Fk_ClassId"))
                                ,String.valueOf(j.getString ("V_housename")));
                        Constants.LOGIN_STATUS = true;
                        dbHandler = new DBHandler(_context);
                        dbHandler.AddStudentData(new TBL_STUDENT(Admission_no, WebRespose));
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Constants.LOGIN_STATUS;
    }

    public String Register_FCM(int session_id,int class_id,int student_id,
                               String Admission_id,String IMEI_ID,
                               String FCM_token,
                               boolean isSubscribe,int opt)
    {
        WebRespose = "";
        SoapObject FCMTokenSaveRequest = new SoapObject(Constants.WEB_SERVER_ADD,Constants.FCMTOKEN_SAVE_ACTION);
        PropertyInfo PI_FCM_token = new PropertyInfo();
        PI_FCM_token.setName("tocken");
        PI_FCM_token.setValue(FCM_token);
        PI_FCM_token.setType(String.class);
        FCMTokenSaveRequest.addProperty(PI_FCM_token);

        PropertyInfo PI_Admission_id = new PropertyInfo();
        PI_Admission_id.setName("admissionno");
        PI_Admission_id.setValue(Admission_id);
        PI_Admission_id.setType(String.class);
        FCMTokenSaveRequest.addProperty(PI_Admission_id);

        PropertyInfo PI_IMEI_ID = new PropertyInfo();
        PI_IMEI_ID.setName("imei");
        PI_IMEI_ID.setValue(IMEI_ID);
        PI_IMEI_ID.setType(String.class);
        FCMTokenSaveRequest.addProperty(PI_IMEI_ID);

        PropertyInfo PI_session_id = new PropertyInfo();
        PI_session_id.setName("session_id");
        PI_session_id.setValue(session_id);
        PI_session_id.setType(int.class);
        FCMTokenSaveRequest.addProperty(PI_session_id);

        PropertyInfo PI_class_id = new PropertyInfo();
        PI_class_id.setName("class_id");
        PI_class_id.setValue(class_id);
        PI_class_id.setType(int.class);
        FCMTokenSaveRequest.addProperty(PI_class_id);

        PropertyInfo PI_student_id = new PropertyInfo();
        PI_student_id.setName("student_id");
        PI_student_id.setValue(student_id);
        PI_student_id.setType(int.class);
        FCMTokenSaveRequest.addProperty(PI_student_id);

        PropertyInfo PI_isSubscribe = new PropertyInfo();
        PI_isSubscribe.setName("status");
        PI_isSubscribe.setValue(isSubscribe);
        PI_isSubscribe.setType(Boolean.class);
        FCMTokenSaveRequest.addProperty(PI_isSubscribe);

        PropertyInfo PI_opt_id = new PropertyInfo();//1 for save / 2 for fetch / 3 for update
        PI_opt_id.setName("opt");
        PI_opt_id.setValue(opt);
        PI_opt_id.setType(int.class);
        FCMTokenSaveRequest.addProperty(PI_opt_id);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(FCMTokenSaveRequest);
        HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.WURL+Constants.FCMTOKEN_WURL_PATH);
        httpTransportSE.debug = true;

        try {
            httpTransportSE.setXmlVersionTag( "<?xml version=\"1.0\" encoding=\"utf-8\"?>" );
            httpTransportSE.call(Constants.WEB_SERVER_ADD + Constants.FCMTOKEN_SAVE_ACTION,envelope);
            SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse();
            WebRespose = soapPrimitive.toString();
            WebRespose =  WebRespose.replace ( "\"","" );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return WebRespose;
    }
}
