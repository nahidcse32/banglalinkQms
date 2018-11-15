package com.sharnit.banglalinkqms.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sharnit.banglalinkqms.Adapter.ServiceType;
import com.sharnit.banglalinkqms.R;
import com.sharnit.banglalinkqms.Utils.ConnectionDetector;
import com.sharnit.banglalinkqms.Utils.DBHelper;
import com.sharnit.banglalinkqms.Utils.GenericVollyError;
import com.sharnit.banglalinkqms.Utils.SharedPreferences;
import com.sharnit.banglalinkqms.Utils.Url;
import com.sharnit.banglalinkqms.Utils.VariableValue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    ArrayList<ServiceType> serviceTypeArrayList;
    DBHelper dbHelper;
    private TextView tvLogin;
    private TextInputEditText textInputEditTextEmail, textInputEditTextPassword;
    private String email;
    private String password;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;
    private ConnectionDetector connectionDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        connectionDetector = new ConnectionDetector(this);
        sharedPreferences = new SharedPreferences(LoginActivity.this);
        progressDialog = new ProgressDialog(LoginActivity.this);
        dbHelper = new DBHelper(this);
        serviceTypeArrayList = new ArrayList<>();
        tvLogin = (TextView) findViewById(R.id.tvLogin);
        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.tietUserCode);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.tietPassword);

        if (sharedPreferences.isLoggedIn()) {

            Intent intent = new Intent(getApplicationContext(), TokenActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = textInputEditTextEmail.getText().toString();
                password = textInputEditTextPassword.getText().toString();
                if (editTextCheck()) {
                    if (connectionDetector.isConnectingToInternet()) {
                        login();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Unable internet", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });


    }

    private void login() {
        showDialog();
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("username", email);
            jsonObject.put("password", password);
            //jsonObject.put("code", sharedPreferences.getRegId());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url.LOGIN, jsonObject, responseData(), GenericVollyError.errorListener(getApplicationContext(), progressDialog));

            requestQueue.add(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private Response.Listener<JSONObject> responseData() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    progressDialog.dismiss();
                    if (response.getBoolean("success")) {
                        dbHelper.clearAndUpdateAll();

                        //   Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                        sharedPreferences.setSessionToken(response.getString("securityToken"));
                        JSONArray jsonArray = response.getJSONArray("serviceList");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            ServiceType serviceType = new ServiceType();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            serviceType.setId(jsonObject.getInt("service_type_id"));
                            serviceType.setName(jsonObject.getString("service_type_name"));

                            //VariableValue.serviceTypeArrayList.add(serviceType);
                            dbHelper.insertType(serviceType);
                        }

                        sharedPreferences.setIsLoggedIn(true);

                        Intent intent = new Intent(getApplicationContext(), TokenActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                    } else {

                        Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                        /*Intent intent = new Intent(getApplicationContext(), DisplayActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);*/

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

    private boolean editTextCheck() {
        if (email.isEmpty()) {
            textInputEditTextEmail.setError("Please enter email address");
            return false;
        } else if (password.isEmpty()) {
            textInputEditTextPassword.setError("Please enter password");
            return false;
        }

        return true;
    }
}
