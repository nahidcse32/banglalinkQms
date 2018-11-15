package com.sharnit.banglalinkqms.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sharnit.banglalinkqms.Adapter.TokenAdapter;
import com.sharnit.banglalinkqms.Model.Token;
import com.sharnit.banglalinkqms.R;
import com.sharnit.banglalinkqms.Utils.GenericVollyError;
import com.sharnit.banglalinkqms.Utils.SharedPreferences;
import com.sharnit.banglalinkqms.Utils.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TokenListActivity extends AppCompatActivity {

    ListView listView;

    ArrayList<Token> tokenArrayList;

    TokenAdapter tokenAdapter;

    SharedPreferences sharedPreferences;

    ProgressDialog progressDialog;

    Button buttonHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token_list);

        progressDialog = new ProgressDialog(TokenListActivity.this);
        sharedPreferences = new SharedPreferences(this);
        listView = (ListView) findViewById(R.id.lv_token);
        tokenArrayList = new ArrayList<>();
        buttonHome = (Button)findViewById(R.id.btn_home);


        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TokenActivity.class);
                startActivity(intent);
                finish();

            }
        });
        getData();

    }

    private void getData() {

        showDialog();

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("securityToken", sharedPreferences.getSessionToken());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url.GET_TOKEN_LIST, jsonObject, responseData(), GenericVollyError.errorListener(getApplicationContext(), progressDialog));

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

                        JSONArray jsonArray = response.getJSONArray("tokenList");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            Token token = new Token();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            token.setToken_id(jsonObject.getInt("token_id"));
                            token.setToken_no(jsonObject.getInt("token_no"));
                            token.setBranch_id(jsonObject.getInt("branch_id"));
                            token.setBranch_name(jsonObject.getString("branch_name"));
                            token.setToken_no_formated(jsonObject.getString("token_no_formated"));
                            token.setCreation_time(jsonObject.getString("creation_time"));
                            token.setService_date(jsonObject.getString("service_date"));
                            token.setService_status_id(jsonObject.getInt("service_status_id"));
                            token.setService_status(jsonObject.getString("service_status"));
                            token.setContact_no(jsonObject.getString("contact_no"));
                            token.setCounter_no(jsonObject.getString("counter_no"));

                            tokenArrayList.add(token);

                        }

                        tokenAdapter = new TokenAdapter(getApplicationContext(), tokenArrayList);

                        listView.setAdapter(tokenAdapter);

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
