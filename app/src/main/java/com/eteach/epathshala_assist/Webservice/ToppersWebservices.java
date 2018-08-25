package com.eteach.epathshala_assist.Webservice;

import android.content.Context;

import com.eteach.epathshala_assist.DBHelper.DBHandler;
import com.eteach.epathshala_assist.Utility.Constants;
import com.eteach.epathshala_assist.dataset.TBL_TOPPERS;

import org.json.JSONArray;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by shree on 31/07/2017.
 */

public class ToppersWebservices {
    public String WebRespose ;
    private JSONArray jsonArray;
    private Context mContext;
    public static final String WEB_SERVER_ADD = "http://www.dmwerp.com/"; //Webservice NAMESPACE
    //public static final String EVENT_ACTION = "GetFees";
    public static final String WURL = "http://www.dmwerp.com/ERPWebservices/Webservices/"; //WEB SERVICE URL PATH
    public static final String TOPPER_WURL_PATH = "get_toppers.asmx";
    public static String TOPPERS_DATA = "";

    DBHandler dbHandler ;

    public ToppersWebservices (Context _context)
    {
        this.mContext = _context;
    }
    public String GETALLTOPPERS()
    {
        SoapObject LoginRequest = new SoapObject( WEB_SERVER_ADD,"GetToppers");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(LoginRequest);
        HttpTransportSE httpTransportSE = new HttpTransportSE(WURL + TOPPER_WURL_PATH);
        httpTransportSE.debug = true;
        httpTransportSE.setXmlVersionTag( "<?xml version=\"1.0\" encoding=\"utf-8\"?>" );
        try {
            httpTransportSE.call(Constants.WEB_SERVER_ADD+"GetToppers",envelope);
            SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse();
            WebRespose = soapPrimitive.toString();
            TOPPERS_DATA = WebRespose;
            AddToppersData();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return WebRespose;
    }

    private void AddToppersData ( ) {
        dbHandler = new DBHandler ( mContext);
        dbHandler.AddToppersData ( new TBL_TOPPERS( WebRespose ) );
    }
}


