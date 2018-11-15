package com.sharnit.banglalinkqms.Service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.sharnit.banglalinkqms.Utils.SharedPreferences;

/**
 * Created by Nahid6 on 1/24/2017.
 */

public class FirebaseIDService extends FirebaseInstanceIdService {
    private static final String TAG = "FirebaseIDService";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.\
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e("Refreshed token: ", "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);
     }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.

        SharedPreferences sharedPreferences = new SharedPreferences(this);
        sharedPreferences.setRegId(token);
    }
}
