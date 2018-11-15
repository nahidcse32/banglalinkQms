package com.sharnit.banglalinkqms.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sharnit.banglalinkqms.R;
import com.sharnit.banglalinkqms.Utils.GenericVollyError;
import com.sharnit.banglalinkqms.Utils.SharedPreferences;
import com.sharnit.banglalinkqms.Utils.Url;
import com.sharnit.banglalinkqms.Utils.VariableValue;

import org.json.JSONException;
import org.json.JSONObject;

public class TokenPreviewActivity extends AppCompatActivity {

    TextView textViewToken;

    Button buttonSms, buttonHome, buttonPrint;

    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token_preview);

        progressDialog = new ProgressDialog(TokenPreviewActivity.this);
        sharedPreferences = new SharedPreferences(this);

        textViewToken = (TextView) findViewById(R.id.tv_token_no);

        textViewToken.setText("# " + VariableValue.TokenNo);

        buttonSms = (Button) findViewById(R.id.btn_sms);
        buttonHome = (Button) findViewById(R.id.btn_home);
        buttonPrint = (Button) findViewById(R.id.btn_print);

        buttonSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendSms();

            }
        });
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), TokenActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }

    private void sendSms() {

        showDialog();

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("securityToken", sharedPreferences.getSessionToken());
            jsonObject.put("mobile", VariableValue.MobileNumber);
            jsonObject.put("tokenNo", VariableValue.TokenNo);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url.SEND_SMS, jsonObject, responseData(), GenericVollyError.errorListener(getApplicationContext(), progressDialog));

            requestQueue.add(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private Response.Listener<JSONObject> responseData() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    if (response.getBoolean("success")) {
                        Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void showDialog() {
        progressDialog.setMessage("Please wait.......");
        progressDialog.show();
    }
}
