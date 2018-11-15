package com.sharnit.banglalinkqms.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sharnit.banglalinkqms.R;
import com.sharnit.banglalinkqms.Utils.DBHelper;
import com.sharnit.banglalinkqms.Utils.GenericVollyError;
import com.sharnit.banglalinkqms.Utils.SharedPreferences;
import com.sharnit.banglalinkqms.Utils.Url;
import com.sharnit.banglalinkqms.Utils.VariableName;
import com.sharnit.banglalinkqms.Utils.VariableValue;

import org.json.JSONException;
import org.json.JSONObject;


public class TokenActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    private Button buttonSkip;
    private Button buttonGo;
    private Button buttonTokenList;

    private ImageView imageViewLogout;
    private Button button1, button2, button3, button4, button5, button6, button7, button8, button9, button0, buttonClear;
    private EditText editTextNumber;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token);

        progressDialog = new ProgressDialog(TokenActivity.this);
        sharedPreferences = new SharedPreferences(this);

        dbHelper = new DBHelper(this);
        VariableValue.TokenNo = "";

        editTextNumber = (EditText) findViewById(R.id.et_mobile_number);


        buttonSkip = (Button) findViewById(R.id.btn_skip);
        buttonGo = (Button) findViewById(R.id.btn_go);
        buttonTokenList = (Button) findViewById(R.id.btn_token_list);


        imageViewLogout = (ImageView) findViewById(R.id.logout);

        button0 = (Button) findViewById(R.id.btn_zero);
        button1 = (Button) findViewById(R.id.btn_one);
        button2 = (Button) findViewById(R.id.btn_two);
        button3 = (Button) findViewById(R.id.btn_three);
        button4 = (Button) findViewById(R.id.btn_four);
        button5 = (Button) findViewById(R.id.btn_five);
        button6 = (Button) findViewById(R.id.btn_six);
        button7 = (Button) findViewById(R.id.btn_seven);
        button8 = (Button) findViewById(R.id.btn_eight);
        button9 = (Button) findViewById(R.id.btn_nine);
        buttonClear = (Button) findViewById(R.id.btn_clear);

        if (VariableValue.IsBack == 1) {
            editTextNumber.setText(VariableValue.MobileNumber);
        }
        buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextNumber.setText("");
            }
        });

        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobileNumber = editTextNumber.getText().toString();
                if (mobileNumber.length() == 11) {
                    Intent intent = new Intent(getApplicationContext(), SelectTokenOptionActivity.class);
                    intent.putExtra(VariableName.MobileNo, mobileNumber);
                    startActivity(intent);
                    finish();
                } else {
                    editTextNumber.setError(getString(R.string.invalid_bl_number));
                }

            }
        });
        buttonTokenList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), TokenListActivity.class);
                startActivity(intent);

            }
        });

        imageViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });


        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        buttonClear.setOnClickListener(this);
    }

    private void logout() {

        showDialog();

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("securityToken", sharedPreferences.getSessionToken());
           // jsonObject.put("securityToken", "");

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url.LOGOUT, jsonObject, responseData(), GenericVollyError.errorListener(getApplicationContext(), progressDialog));

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

                        sharedPreferences.setIsLoggedIn(false);
                        sharedPreferences.setSessionToken("");
                        VariableValue.serviceTypeArrayList = null;
                        dbHelper.clearAndUpdateAll();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_zero:
                setValue(0);
                break;
            case R.id.btn_one:
                setValue(1);
                break;
            case R.id.btn_two:
                setValue(2);
                break;
            case R.id.btn_three:
                setValue(3);
                break;
            case R.id.btn_four:
                setValue(4);
                break;
            case R.id.btn_five:
                setValue(5);
                break;
            case R.id.btn_six:
                setValue(6);
                break;
            case R.id.btn_seven:
                setValue(7);
                break;
            case R.id.btn_eight:
                setValue(8);
                break;
            case R.id.btn_nine:
                setValue(9);
                break;
            case R.id.btn_clear:
                setValue(-1);
                break;

            default:
                break;

        }
    }

    private void setValue(int i) {
        String value = editTextNumber.getText().toString();

        if (i == -1) {
            if (value.length() > 0) {
                value = value.substring(0, value.length() - 1);
                editTextNumber.setText(value);
            }
        } else {
            editTextNumber.setText(value + i);
        }

    }

    private void showDialog() {
        progressDialog.setMessage("Please wait.......");
        progressDialog.show();
    }
}
