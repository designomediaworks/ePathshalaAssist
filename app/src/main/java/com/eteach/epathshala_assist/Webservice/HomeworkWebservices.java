package com.eteach.epathshala_assist.Webservice;

import android.content.Context;

import com.eteach.epathshala_assist.DBHelper.DBHandler;
import com.eteach.epathshala_assist.Utility.Constants;
import com.eteach.epathshala_assist.dataset.TBL_ATTANDENCE;
import com.eteach.epathshala_assist.dataset.TBL_HOMEWORK;

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
import java.net.ProtocolException;

/**
 * Created by shree on 31/07/2017.
 */

public class HomeworkWebservices {
    public String WebRespose ;
    private JSONArray jsonArray;
    private Context mContext;
    public static final String WEB_SERVER_ADD = "http://www.dmwerp.com/"; //Webservice NAMESPACE
    public static final String WURL = "http://www.dmwerp.com/ERPWebservices/Webservices/"; //WEB SERVICE URL PATH
    public static final String HOMEWORKS_WURL_PATH = "save_assignment.asmx";


    DBHandler dbHandler ;

    public HomeworkWebservices ( Context _context)
    {
        this.mContext = _context;
    }
    public JSONArray GetHomeworklist(String _classid , int query){
        try{

            SoapObject request = new SoapObject( WEB_SERVER_ADD,"get_assign" );
            PropertyInfo PIcid = new PropertyInfo();
            PIcid.setName( "classid" );
            PIcid.setValue( Integer.valueOf ( _classid ) );
            PIcid.setType( Integer.class );
            request.addProperty( PIcid );

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11 );
            envelope.dotNet = true;
            envelope.setOutputSoapObject( request );
            HttpTransportSE httpTransportSE = new HttpTransportSE( WURL+HOMEWORKS_WURL_PATH );
            httpTransportSE.debug = true;
            httpTransportSE.setXmlVersionTag( "<?xml version=\"1.0\" encoding=\"utf-8\"?>" );
            httpTransportSE.call( Constants.WEB_SERVER_ADD+"get_assign", envelope );
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            String resJson = response.toString();
            jsonArray = new JSONArray( resJson );

            if (query==1) {
                Addhomeworkdata(resJson,_classid);
            }
            else if (query==3)
            {
                Updatehomeworkdata(resJson,_classid);
            }

        } catch (ProtocolException e)
        {
            e.printStackTrace();
        } catch (IOException ex)
        {
            WebRespose = "Error.." + " " + ex.toString();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray ;
    }
    private void Updatehomeworkdata(String resJson , String cid){
        try {
            dbHandler = new DBHandler ( mContext );
            dbHandler.UpdateHomeworkData( new TBL_HOMEWORK ( resJson , cid ),cid);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private void Addhomeworkdata(String resJson , String cid){
        try {
            dbHandler = new DBHandler (mContext);
            dbHandler.AddHomeWorkData ( new TBL_HOMEWORK ( resJson , cid ) );
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}


