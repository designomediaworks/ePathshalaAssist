package com.eteach.epathshala_assist.Webservice;

import android.content.Context;

import com.eteach.epathshala_assist.DBHelper.DBHandler;
import com.eteach.epathshala_assist.dataset.TBL_FEES;

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

public class Fees_Webservices {
    public String WebRespose;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private JSONArray jsonArray;
    public static final String WEB_SERVER_ADD = "http://www.dmwerp.com/"; //Webservice NAMESPACE
    public static final String EVENT_ACTION = "GetFees";
    public static final String WURL = "http://www.dmwerp.com/ERPWebservices/Webservices/"; //WEB SERVICE URL PATH
    public static final String EVENT_WURL_PATH = "get_fees_detail.asmx";
    DBHandler dbHandler ;
    private Context mContext;
    public Fees_Webservices(Context context )
    {
        mContext = context;
    }


    public String GETALLFEES (String AddmissionNo,int query) {
        SoapObject LoginRequest = new SoapObject (WEB_SERVER_ADD, EVENT_ACTION);
        PropertyInfo PIroute = new PropertyInfo();
        PIroute.setName( "AdmNO" );
        PIroute.setValue( AddmissionNo );
        PIroute.setType( String.class );
        LoginRequest.addProperty( PIroute );
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope (SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject (LoginRequest);
        HttpTransportSE httpTransportSE = new HttpTransportSE (WURL + EVENT_WURL_PATH);
        httpTransportSE.debug = true;
        httpTransportSE.setXmlVersionTag ("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        try {
            httpTransportSE.call (WEB_SERVER_ADD + EVENT_ACTION, envelope);
            SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse ();
            WebRespose = soapPrimitive.toString ();
            if(query ==1){
            AddFess(AddmissionNo);
            }
            else if(query==3) {
                UpdateFees(AddmissionNo);
            }
        } catch (IOException e) {
            e.printStackTrace ();
        } catch (XmlPullParserException e) {
            e.printStackTrace ();
        }
        return WebRespose;
    }

    private void UpdateFees (String addmissionNo) {
        dbHandler = new DBHandler ( mContext);
        String ad = addmissionNo;
        dbHandler.updateFees( new TBL_FEES(addmissionNo, WebRespose ),addmissionNo );
    }

    private void AddFess (String addmissionNo) {
        dbHandler = new DBHandler ( mContext);
        String ad = addmissionNo;
        dbHandler.AddFeesData( new TBL_FEES(addmissionNo, WebRespose ) );
    }


}
