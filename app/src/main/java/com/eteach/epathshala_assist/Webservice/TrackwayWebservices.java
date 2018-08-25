package com.eteach.epathshala_assist.Webservice;

import android.content.Context;

import com.eteach.epathshala_assist.DBHelper.DBHandler;
import com.eteach.epathshala_assist.Utility.Constants;

import org.json.JSONArray;
import org.json.JSONException;
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

public class TrackwayWebservices {
    public String DefaultRes = "[{\"d_latitude\":21.108512,\"d_longitude\":793133067,\"dt_datetime\":\"17:48:45\",\"v_deviceid\":\"911582955976126\",\"v_route\":\"route1\"}]";
    public String WebRespose ;
    private JSONArray jsonArray;
    private Context mContext;
    public static final String WEB_SERVER_ADD = "http://www.dmwerp.com/"; //Webservice NAMESPACE
    //public static final String EVENT_ACTION = "GetFees";
    public static final String WURL = "http://www.dmwerp.com/ERPWebservices/Webservices/"; //WEB SERVICE URL PATH
    public static final String TRACKWAY_WURL_PATH = "get_pickuppoints.asmx";


    public static String TRACKWAY_DATA = "";

    DBHandler dbHandler ;

    public TrackwayWebservices (Context _context)
    {
        this.mContext = _context;
    }
    public JSONArray showroutelist(String _route){
        try{

            SoapObject request = new SoapObject( WEB_SERVER_ADD,"get_pnts" );
            PropertyInfo PIroute = new PropertyInfo();
            PIroute.setName( "route" );
            PIroute.setValue( _route );
            PIroute.setType( String.class );
            request.addProperty( PIroute );

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11 );
            envelope.dotNet = true;
            envelope.setOutputSoapObject( request );
            HttpTransportSE httpTransportSE = new HttpTransportSE( WURL+TRACKWAY_WURL_PATH );
            httpTransportSE.debug = true;
            httpTransportSE.setXmlVersionTag( "<?xml version=\"1.0\" encoding=\"utf-8\"?>" );
            httpTransportSE.call( Constants.WEB_SERVER_ADD+"get_pnts", envelope );
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            String resJson = response.toString();

                jsonArray = new JSONArray ( resJson );

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
    public JSONArray getLastKnownBusLocation(String _route){
        try{

            SoapObject request = new SoapObject( WEB_SERVER_ADD,"get_pnts_Last" );
            PropertyInfo PIroute = new PropertyInfo();
            PIroute.setName( "route" );
            PIroute.setValue( _route );
            PIroute.setType( String.class );
            request.addProperty( PIroute );

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11 );
            envelope.dotNet = true;
            envelope.setOutputSoapObject( request );
            HttpTransportSE httpTransportSE = new HttpTransportSE( WURL+TRACKWAY_WURL_PATH );
            httpTransportSE.debug = true;
            httpTransportSE.setXmlVersionTag( "<?xml version=\"1.0\" encoding=\"utf-8\"?>" );
            httpTransportSE.call( Constants.WEB_SERVER_ADD+"get_pnts_Last", envelope );
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            String resJson = response.toString();
            if ( resJson.equals ( "[]" )  )
            {
                jsonArray = new JSONArray ( DefaultRes );
            }else {
                jsonArray = new JSONArray ( resJson );
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
}


