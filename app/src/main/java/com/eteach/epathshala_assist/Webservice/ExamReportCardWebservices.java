package com.eteach.epathshala_assist.Webservice;

import android.content.Context;

import com.eteach.epathshala_assist.DBHelper.DBHandler;
import com.eteach.epathshala_assist.dataset.TBL_REPORTCARD;

import org.json.JSONArray;
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

public class ExamReportCardWebservices {
    public String WebRespose;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private JSONArray jsonArray;
    public static final String WEB_SERVER_ADD = "http://www.dmwerp.com/"; //Webservice NAMESPACE
    public static final String REPORTCARD_ACTION = "Getreport";
    public static final String WURL = "http://www.dmwerp.com/ERPWebservices/Webservices/"; //WEB SERVICE URL PATH
    public static final String REPORTCARD_WURL_PATH = "get_reportcard.asmx";
    DBHandler dbHandler;
    private Context mContext;

    public ExamReportCardWebservices (Context context) {
        mContext = context;
    }


    public String GETALLREPORTCARD (String AddmissionNo) {
        SoapObject LoginRequest = new SoapObject(WEB_SERVER_ADD, REPORTCARD_ACTION);
        PropertyInfo PIroute = new PropertyInfo();
        PIroute.setName("admno");
        PIroute.setValue(AddmissionNo);
        PIroute.setType(String.class);
        LoginRequest.addProperty(PIroute);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(LoginRequest);
        HttpTransportSE httpTransportSE = new HttpTransportSE(WURL + REPORTCARD_WURL_PATH);
        httpTransportSE.debug = true;
        httpTransportSE.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        try {
            httpTransportSE.call(WEB_SERVER_ADD + REPORTCARD_ACTION, envelope);
            SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse();
            WebRespose = soapPrimitive.toString();
            AddReportCard(AddmissionNo);
            UpdateReportCard(AddmissionNo);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return WebRespose;
    }

    private void UpdateReportCard (String addmissionNo) {
        dbHandler = new DBHandler(mContext);
        dbHandler.UpdateReportData(new TBL_REPORTCARD(addmissionNo, WebRespose),addmissionNo);
    }

    private void AddReportCard (String addmissionNo) {
        dbHandler = new DBHandler(mContext);
        dbHandler.AddReportData(new TBL_REPORTCARD(addmissionNo, WebRespose));
    }
}
