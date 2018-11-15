package com.sharnit.banglalinkqms.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sharnit.banglalinkqms.Adapter.GridAdapter;
import com.sharnit.banglalinkqms.Adapter.ServiceType;
import com.sharnit.banglalinkqms.R;
import com.sharnit.banglalinkqms.Utils.DBHelper;
import com.sharnit.banglalinkqms.Utils.GenericVollyError;
import com.sharnit.banglalinkqms.Utils.SharedPreferences;
import com.sharnit.banglalinkqms.Utils.Url;
import com.sharnit.banglalinkqms.Utils.VariableName;
import com.sharnit.banglalinkqms.Utils.VariableValue;

import org.json.JSONException;
import org.json.JSONObject;

public class SelectTokenOptionActivity extends AppCompatActivity {

    Button buttonBack;
    GridView gridView;
    SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_token_option);

        sharedPreferences = new SharedPreferences(getApplicationContext());
        progressDialog = new ProgressDialog(SelectTokenOptionActivity.this);

        dbHelper = new DBHelper(this);

        gridView = (GridView)findViewById(R.id.grid);

        buttonBack = (Button)findViewById(R.id.btn_back);

        VariableValue.serviceTypeArrayList = dbHelper.getAllType();
        setUpGrid();

        VariableValue.MobileNumber = getIntent().getExtras().getString(VariableName.MobileNo);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TokenActivity.class);
                VariableValue.IsBack = 1;
                startActivity(intent);

            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ServiceType serviceType = VariableValue.serviceTypeArrayList.get(i);

                generateToken(serviceType.getId());

            }
        });
    }

    private void generateToken(int id) {

        showDialog();

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("securityToken", sharedPreferences.getSessionToken());
            jsonObject.put("mobile", VariableValue.MobileNumber);
            jsonObject.put("service_type_id", id);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url.GENERATE_TOKEN, jsonObject, responseData(), GenericVollyError.errorListener(getApplicationContext(), progressDialog));

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
                        VariableValue.TokenNo = response.getString("tokenNo");
                        Intent intent = new Intent(getApplicationContext(), TokenPreviewActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void setUpGrid() {
        GridAdapter gridAdapter = new GridAdapter(getApplicationContext(), VariableValue.serviceTypeArrayList);
        gridView.setAdapter(gridAdapter);

    }

    private void showDialog() {
        progressDialog.setMessage("Please wait.......");
        progressDialog.show();
    }

}
