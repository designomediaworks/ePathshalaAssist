package com.eteach.epathshala_assist.Webservice;

import android.content.Context;

import com.eteach.epathshala_assist.DBHelper.DBHandler;
import com.eteach.epathshala_assist.dataset.TBL_ATTANDENCE;

import org.json.JSONArray;
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
 * Created by shree on 31/07/2017.
 */

public class AttendanceWebservices {
    public String WebRespose,attdate;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private JSONArray jsonArray;
    public static final String WEB_SERVER_ADD = "http://www.dmwerp.com/"; //Webservice NAMESPACE
    public static final String GETATTANDANCE_ACTION = "get_attendance";
    public static final String WURL = "http://www.dmwerp.com/ERPWebservices/Webservices/"; //WEB SERVICE URL PATH
    public static final String GETATTANDANCE_WURL_PATH = "assist_attendance.asmx";
    DBHandler dbHandler;
    private Context mContext;

    public AttendanceWebservices (Context context) {
        mContext = context;
    }


    public String GETALLATTENDANCE (String AddmissionNo,String date,int query) {
        SoapObject LoginRequest = new SoapObject(WEB_SERVER_ADD, GETATTANDANCE_ACTION);
        PropertyInfo PISTD = new PropertyInfo();
        PISTD.setName("stdno");
        PISTD.setValue(AddmissionNo);
        PISTD.setType(String.class);
        LoginRequest.addProperty(PISTD);

        PropertyInfo PIDATE = new PropertyInfo();
        PIDATE.setName("dt_date");
        PIDATE.setValue(date);
        PIDATE.setType(String.class);
        LoginRequest.addProperty(PIDATE);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(LoginRequest);
        HttpTransportSE httpTransportSE = new HttpTransportSE(WURL + GETATTANDANCE_WURL_PATH);
        httpTransportSE.debug = true;
        httpTransportSE.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        try {
            httpTransportSE.call(WEB_SERVER_ADD + GETATTANDANCE_ACTION, envelope);
            SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse();
            WebRespose = soapPrimitive.toString();
            if (query==1) {
                addatttandacedata(AddmissionNo);
            }
            else if (query==3)
            {
                updateatttandacedata(AddmissionNo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return WebRespose;
    }

    private void addatttandacedata (String AddmissionNo ) {
        try {
        dbHandler = new DBHandler(mContext);
        JSONArray jsonArray = new JSONArray(WebRespose);
        for (int i = 0; i<jsonArray.length(); i++)
        {
            JSONObject j = jsonArray.getJSONObject(i);
            attdate = j.getString("Date").toString();
        }
        dbHandler.AddAttandancesData(new TBL_ATTANDENCE(AddmissionNo,attdate, WebRespose));
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private void updateatttandacedata(String AddmissionNo){
        try {
            dbHandler = new DBHandler(mContext);
            JSONArray jsonArray = new JSONArray(WebRespose);
            for (int i = 0; i<jsonArray.length(); i++)
            {
                JSONObject j = jsonArray.getJSONObject(i);
                attdate = j.getString("Date").toString();
            }
            dbHandler.UpdateAttandanceData(new TBL_ATTANDENCE(AddmissionNo,attdate, WebRespose),AddmissionNo);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
