package com.eteach.epathshala_assist.fcmservice;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.eteach.epathshala_assist.DBHelper.DBHandler;
import com.eteach.epathshala_assist.MainActivity;
import com.eteach.epathshala_assist.Utility.Constants;
import com.eteach.epathshala_assist.Utility.ImagesDownload;
import com.eteach.epathshala_assist.Utility.notification_utilitys;
import com.eteach.epathshala_assist.Webservice.NotificationsWebservices;
import com.eteach.epathshala_assist.dataset.TBL_NOTI;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

/**
 * Created by shree on 20/07/2017.
 */

    public class FCMRegistrationIntentService extends FirebaseMessagingService {
    public static final String Reg_succes = "Reg Done!";
    public static final String Reg_Error = "Reg Error";
    public static final String TAG = FCMRegistrationIntentService.class.getSimpleName();
    private notification_utilitys notificationutils;
    DBHandler dbHandler = new DBHandler(this);


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e(TAG,"From:"+remoteMessage.getFrom());
        if (remoteMessage == null)
            return;
        if (remoteMessage.getNotification() != null)
        {
            Log.e(TAG,"Notification Body:"+remoteMessage.getNotification());
            //handleNotification(remoteMessage.getNotification().getBody());
        }
        if (remoteMessage.getData().size()>0)
        {

            Log.e(TAG,"Data Playlod:"+remoteMessage.getData().toString());
            try {

                JSONObject jsonObject = new JSONObject(remoteMessage.getData().toString());
                if (jsonObject.has( "data" ))
                {
                    handleDataThoghtsMessage( jsonObject );
                }else {
                    handleDataMessage( jsonObject);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }catch ( Exception e )
            {
                e.printStackTrace();
            }
        }
    }

    private void handleDataMessage(JSONObject jsonObject) {

        Log.e(TAG, "push Json:" + jsonObject.toString());
        try {
            JSONObject data = jsonObject.getJSONObject("message");
            String title = data.getString("Title");
            String message = data.getString("Message");
            String timestamp = data.getString("timestamp");

            try {

                dbHandler.AddNoficationsData(new TBL_NOTI(jsonObject.toString()));

                NotificationsWebservices notificationsWebservices = new NotificationsWebservices( getApplicationContext());

                notificationsWebservices.GETALLNOTIFICATIONS(Constants.STUDENT_CLASS_ID ,Constants.STUDENT_ID,3);

            }catch (StackOverflowError e)
            {
                Log.e("DB Error:",e.toString());
            }

            Log.d("All Message:","message");
            List<TBL_NOTI> messageList = dbHandler.getAllnotifications();
            for (TBL_NOTI tblNoti: messageList ){
                String DBLOG = "pk_id"+tblNoti.getPk_id()+",Title"+tblNoti.getTitle()+",Message:"+tblNoti.getMessage();
                Log.d("Notificatoion:",DBLOG);
            }
            if (!notificationutils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Constants.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
                // play notification sound
                showNotificationMessage(getApplicationContext(), title, message, timestamp, pushNotification);
                notification_utilitys notificationUtils = new notification_utilitys(getApplicationContext());
                notificationUtils.playNotificationSound();
            } else {
                // app is in background, show the notification in notification tray
                Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                resultIntent.putExtra("message", message);
                showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                // check for image attachment
                // image is present, show notification with image
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleDataThoghtsMessage ( JSONObject jsonObject ) {
        try {
            JSONObject data = jsonObject.getJSONObject("data");
            String imageurl = data.getString("image");
            String message = data.getString("Message");
            String title = data.getString("Title");
            String timestamp = data.getString("timestamp");
            new ImagesDownload(getApplicationContext ()).execute ( String.valueOf ( imageurl ),title,timestamp,"2" );

            if (!notificationutils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Constants.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
                // play notification sound
                //showNotificationMessage(getApplicationContext(), title, message, imageurl, pushNotification);
                showNotificationMessageWithBigImage(getApplicationContext( ), title, message, timestamp, pushNotification,imageurl  );
                notification_utilitys notificationUtils = new notification_utilitys(getApplicationContext());
                notificationUtils.playNotificationSound();
            } else {
                // app is in background, show the notification in notification tray
                Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                resultIntent.putExtra("message", message);
                if ( TextUtils.isEmpty( imageurl ) ) {
                    showNotificationMessage( getApplicationContext( ), title, message, imageurl, resultIntent );
                }else {
                    // check for image attachment
                    // image is present, show notification with image
                    showNotificationMessageWithBigImage( getApplicationContext( ), title, message, timestamp, resultIntent,imageurl  );
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showNotificationMessageWithBigImage(Context applicationContext, String title, String message, String timestamp, Intent resultIntent, String imageURL) {
        notificationutils = new notification_utilitys(applicationContext);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationutils.showNotificationMessage(title, message, timestamp, resultIntent,imageURL);
    }

    private void showNotificationMessage(Context applicationContext, String title, String message, String timestamp, Intent resultIntent) {
        notificationutils = new notification_utilitys(applicationContext);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationutils.showNotificationMessage(title, message, timestamp, resultIntent,null);

    }

    private void handleNotification(String body) {
        if (!notificationutils.isAppIsInBackground(getApplicationContext())){
            Intent pushNotification = new Intent(Constants.PUSH_NOTIFICATION);
            pushNotification.putExtra("message",body);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
            notification_utilitys notificationutils = new notification_utilitys(getApplicationContext());
            notificationutils.playNotificationSound();
        }else
        {
            //
        }
    }
}

