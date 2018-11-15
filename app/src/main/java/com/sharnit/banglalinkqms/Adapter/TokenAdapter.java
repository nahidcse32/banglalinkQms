package com.sharnit.banglalinkqms.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sharnit.banglalinkqms.Model.Display;
import com.sharnit.banglalinkqms.Model.Token;
import com.sharnit.banglalinkqms.R;
import com.sharnit.banglalinkqms.Utils.GenericVollyError;
import com.sharnit.banglalinkqms.Utils.SharedPreferences;
import com.sharnit.banglalinkqms.Utils.Url;
import com.sharnit.banglalinkqms.Utils.VariableValue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TokenAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Token> tokenArrayList = new ArrayList<>();
    SharedPreferences sharedPreferences;

    public TokenAdapter(Context context, ArrayList<Token> tokenArrayList) {
        this.context = context;
        this.tokenArrayList = tokenArrayList;
        sharedPreferences = new SharedPreferences(context);
    }

    @Override
    public int getCount() {
        return tokenArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return tokenArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.token_adapter, viewGroup, false);
        }

        final Token token = tokenArrayList.get(i);

        TextView textViewTokenNo = (TextView)convertView.findViewById(R.id.tv_ta_token_no);
        TextView textViewCreationTime = (TextView)convertView.findViewById(R.id.tv_ta_creation_time);
        TextView textViewMobileNo = (TextView)convertView.findViewById(R.id.tv_ta_mobile_no);
        TextView textViewStatus = (TextView)convertView.findViewById(R.id.tv_ta_status);

        Button buttonSms = (Button)convertView.findViewById(R.id.btn_ta_sms);
        Button buttonPrint = (Button)convertView.findViewById(R.id.btn_ta_print);

        textViewTokenNo.setText(token.getToken_no_formated());
        textViewCreationTime.setText(token.getCreation_time());
        textViewMobileNo.setText(token.getContact_no());
        textViewStatus.setText(token.getService_status());

        buttonSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendSms(token.getToken_no_formated(), token.getContact_no());

            }
        });

        return convertView;
    }

    private void sendSms(String token_no_formated, String contact_no) {

        //showDialog();

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("securityToken", sharedPreferences.getSessionToken());
            jsonObject.put("mobile", contact_no);
            jsonObject.put("tokenNo", token_no_formated);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url.SEND_SMS, jsonObject, responseData(), GenericVollyError.errorListener(context, null));

            requestQueue.add(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private Response.Listener<JSONObject> responseData() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //progressDialog.dismiss();
                try {
                    if (response.getBoolean("success")) {
                        Toast.makeText(context, response.getString("message"), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, response.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
