package com.sharnit.banglalinkqms.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

public class GenericVollyError {

    static Context context;
    static ProgressDialog progressDialog;

    public GenericVollyError(Context context, ProgressDialog progressDialog) {
        this.context = context;
        this.progressDialog = progressDialog;
    }

    public static Response.ErrorListener errorListener(final Context applicationContext, final ProgressDialog progressDialog) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                if (error instanceof NetworkError) {
                    Toast.makeText(applicationContext, "Network problem", Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(applicationContext, "Server error", Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(applicationContext, "Timeout error", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(applicationContext, "Volley error", Toast.LENGTH_LONG).show();
                }
            }
        };
    }
}
