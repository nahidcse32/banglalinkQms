package com.sharnit.banglalinkqms.Service;

import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sharnit.banglalinkqms.Activity.DisplayActivity;
import com.sharnit.banglalinkqms.Model.Display;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Nahid6 on 1/24/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    ArrayList<Display> displayArrayList;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            displayArrayList = new ArrayList<>();
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                JSONObject data = json.getJSONObject("data");
                String type = data.getString("type");
                if (type.equalsIgnoreCase("display")){
                    JSONArray jsonArray = data.getJSONArray("dis_data");
                    for (int i =0; i< jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Display display = new Display(jsonObject.getString("token"), jsonObject.getString("counter"));
                        displayArrayList.add(display);
                    }
                }
                String message = data.getString("message");
                Intent intent = new Intent(getApplicationContext(), DisplayActivity.class);
                intent.putParcelableArrayListExtra("dis_data", displayArrayList);
                intent.putExtra("message", message);
                startActivity(intent);

                //sendPushNotification(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    //this method will display the notification
    //We are passing the JSONObject that is received from
    //firebase cloud messaging
    private void sendPushNotification(JSONObject json) {
        //optionally we can display the json into log
        Log.e(TAG, "Notification JSON " + json.toString());
        try {
            //getting the json data
            JSONObject data = json.getJSONObject("data");

            //parsing json data
            String type = data.getString("type");
            if (type.equals("display")) {


                String title = data.getString("title");
                String message = data.getString("message");
                String date = data.getString("date");

                //creating MyNotificationManager object
                MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());

                //creating an intent for the notification

                Intent intent = new Intent(getApplicationContext(), DisplayActivity.class);
                //intent.putExtra("key",imageUrl);

                //if there is no image
                //displaying small notification
                mNotificationManager.showSmallNotification(title, message, intent);

            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }
}