package com.eteach.epathshala_assist.Webservice;

import android.content.Context;

import com.eteach.epathshala_assist.DBHelper.DBHandler;
import com.eteach.epathshala_assist.Utility.Constants;
import com.eteach.epathshala_assist.dataset.TBL_NOTI;

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

public class NotificationsWebservices {
    public String WebRespose ;
    private JSONArray jsonArray;
    private Context mContext;
    public static final String WEB_SERVER_ADD = "http://www.dmwerp.com/"; //Webservice NAMESPACE
    //public static final String EVENT_ACTION = "GetFees";
    public static final String WURL = "http://www.dmwerp.com/ERPWebservices/Webservices/"; //WEB SERVICE URL PATH
    public static final String NOTI_WURL_PATH = "get_notification.asmx";
    public static String NOTIS_DATA = "";

    DBHandler dbHandler ;

    public NotificationsWebservices (Context _context)
    {
        this.mContext = _context;
    }


    public String GETALLNOTIFICATIONS(String classid, String stdid,int query)
    {
        SoapObject DownloadNotiRequest = new SoapObject( WEB_SERVER_ADD,"GetNotify");
        PropertyInfo PI_classid = new PropertyInfo();
        PI_classid.setName("classid");
        PI_classid.setValue(Integer.valueOf(classid));
        PI_classid.setType(Integer.class);
        DownloadNotiRequest.addProperty(PI_classid);

        PropertyInfo PI_student_id = new PropertyInfo();
        PI_student_id.setName("stdid");
        PI_student_id.setValue(Integer.valueOf(stdid));
        PI_student_id.setType(Integer.class);
        DownloadNotiRequest.addProperty(PI_student_id);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(DownloadNotiRequest);
        HttpTransportSE httpTransportSE = new HttpTransportSE(WURL + NOTI_WURL_PATH);
        httpTransportSE.debug = true;
        httpTransportSE.setXmlVersionTag( "<?xml version=\"1.0\" encoding=\"utf-8\"?>" );
        try {
            httpTransportSE.call(Constants.WEB_SERVER_ADD+"GetNotify",envelope);
            SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse();
            WebRespose = soapPrimitive.toString();
            NOTIS_DATA = WebRespose;

            if(query == 1) {
                AddNotifiaction(classid, stdid);
            }else if(query ==3) {
            UpdateNotifiaction(classid,stdid);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return WebRespose;
    }

    private void UpdateNotifiaction (String classid, String stdid) {
        dbHandler = new DBHandler ( mContext);
        dbHandler.updatenotifiaction ( new TBL_NOTI( classid,stdid,WebRespose ),stdid );
    }

    private void AddNotifiaction (String classid,String stdid ) {
        dbHandler = new DBHandler ( mContext);
        dbHandler.addnotifiaction ( new TBL_NOTI( classid,stdid,WebRespose ) );
    }
}


