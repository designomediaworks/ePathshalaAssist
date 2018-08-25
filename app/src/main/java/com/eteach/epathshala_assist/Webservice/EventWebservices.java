package com.eteach.epathshala_assist.Webservice;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.eteach.epathshala_assist.DBHelper.DBHandler;
import com.eteach.epathshala_assist.Utility.Constants;
import com.eteach.epathshala_assist.Utility.ImagesDownload;
import com.eteach.epathshala_assist.Utility.utilitys;
import com.eteach.epathshala_assist.dataset.TBL_EVENTS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by shree on 31/07/2017.
 */

public class EventWebservices {
    public String WebRespose ;
    private JSONArray jsonArray;
    private Context mContext;

    DBHandler dbHandler ;
    utilitys utilitys ;
    String IMG;
    public EventWebservices(Context _context)
    {
        this.mContext = _context;
    }
    public String GETALLEVENTS(int query )
    {
        SoapObject LoginRequest = new SoapObject( Constants.WEB_SERVER_ADD,Constants.EVENT_ACTION);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(LoginRequest);
        HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.WURL+Constants.EVENT_WURL_PATH);
        httpTransportSE.debug = true;
        httpTransportSE.setXmlVersionTag( "<?xml version=\"1.0\" encoding=\"utf-8\"?>" );
        try {
            httpTransportSE.call(Constants.WEB_SERVER_ADD+Constants.EVENT_ACTION,envelope);
            SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse();
            WebRespose = soapPrimitive.toString();
            Constants.EVENT_DATA = WebRespose;
           if (query==1) {
               AddEvents();
           }else if (query==3) {
               UpdateEvents();
           }

            JSONArray jsonArray = new JSONArray(WebRespose);

            for (int i = 0; i<jsonArray.length(); i++)
            {
                JSONObject j = jsonArray.getJSONObject(i);
                Constants.EVENT_NAME = j.getString("EventName").toString();
                Constants.EVENT_DISCREAPTION = j.getString("Description").toString();
                Constants.EVENT_TIME=j.getString("EventDate").toString();
                IMG = j.getString("MainUrl").toString();
                String THUMB_IMG = j.getString("ThumbUrl").toString();
                createfolder(Constants.EVENT_NAME);
            }
            //int i = WebRespose.length();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return WebRespose;
    }

    private void UpdateEvents ( ) {
        dbHandler = new DBHandler ( mContext);
        dbHandler.UpdateEventsData ( new TBL_EVENTS ( WebRespose ) );
    }

    private void AddEvents ( ) {
        dbHandler = new DBHandler ( mContext);
        dbHandler.AddEventsData ( new TBL_EVENTS ( WebRespose ) );
    }

    private void createfolder (String eventName) {
        try {
        File file = new File ( Environment.getExternalStoragePublicDirectory (Environment.DIRECTORY_PICTURES),
                "/ePathshala/EventView/" + eventName);

            if (!file.mkdirs ()) {
                file.mkdirs ();
                saveThumbImage ( IMG );
            } else {
                Log.e ("StorageError","Directory not Created");
            }
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }
    public void saveThumbImage  (String uri)
    {
        try {
            String [] urilist = uri.split (",");
            ArrayList<String> uriArraylist = new ArrayList<String > ( Arrays.asList (urilist));
            for (int i = 0; i < uriArraylist.size (); i++) {
                Uri url = Uri.parse (uriArraylist.get (i));
                new ImagesDownload ( mContext ).execute ( String.valueOf ( url ), Constants.EVENT_NAME,Constants.EVENT_NAME+"_"+i);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}


