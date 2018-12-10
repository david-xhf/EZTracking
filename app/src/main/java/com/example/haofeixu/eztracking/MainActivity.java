package com.example.haofeixu.eztracking;

import org.json.JSONObject;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Spinner;
import android.widget.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.StrictMode;
import android.util.Log;

import org.json.*;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyApp";
    TextView name3;
    TextView name31;
    TextView name4;
    TextView name41;
    TextView name5;
    TextView name51;
    TextView name6;
    TextView name61;
    TextView name7;
    TextView name71;
    TextView name8;
    TextView name81;
    TextView name9;
    TextView name91;
    TextView name10;
    TextView name11;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        initView();
    }

    // 初始化View
    private void initView() {
        Button track = (Button) findViewById(R.id.track);
    }


    //控件View的点击事件
    public void onClick(View v) {
        EditText input = (EditText) findViewById(R.id.editText);
        String number = input.getText().toString();
        Spinner spinner = (Spinner)findViewById(R.id.carrier);
        String type = spinner.getSelectedItem().toString();
        String carrier;
        if (type.equals("ZTOEx")) {
            carrier = "zto";
        } else if (type.equals("USPS")) {
            carrier = "usps";
        } else if (type.equals("Fedex")) {
            carrier = "fedex";
        } else if (type.equals("DHL")) {
            carrier = "DHL_EN";
        } else if (type.equals("TNT")) {
            carrier = "tnt";
        } else if (type.equals("AMZL")) {
            carrier = "AMAZON";
        } else if (type.equals("YTOEx")) {
            carrier = "yto";
        } else if (type.equals("STOEx")) {
            carrier = "sto";
        } else if (type.equals("UPS")) {
            carrier = "ups";
        } else {
            carrier = "sf";
        }
        name3 = findViewById(R.id.textView3);
        name31 = findViewById(R.id.textView31);
        name4 = findViewById(R.id.textView4);
        name41 = findViewById(R.id.textView41);
        name5 = findViewById(R.id.textView5);
        name51 = findViewById(R.id.textView51);
        name6 = findViewById(R.id.textView6);
        name61 = findViewById(R.id.textView61);
        name7 = findViewById(R.id.textView7);
        name71 = findViewById(R.id.textView71);
        name8 = findViewById(R.id.textView8);
        name81 = findViewById(R.id.textView81);
        name9 = findViewById(R.id.textView9);
        name91 = findViewById(R.id.textView91);
        name10 = findViewById(R.id.textView10);
        name11 = findViewById(R.id.textView11);
        switch (v.getId()) {
            case R.id.track:
                try {
                    String[] text = {number, carrier};
                    MyTask jsonParse = new MyTask();
                    jsonParse.execute(text);
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    break;
                } catch (Exception e) {
                    name3.setText("Error: " + type);
                }

            default:
                break;
        }
    }

    private String Response(String number, String type) throws Exception {
        String urlSend = "http://wdexpress.market.alicloudapi.com" + "/gxali" + "?n=" + number + "&t=" + type;
        URL url = new URL(urlSend);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestProperty("Authorization", "APPCODE " + "c0015187dd284b2c9c7f010f6d81faa5");
        int httpCode = httpURLConnection.getResponseCode();
        String json = read(httpURLConnection.getInputStream());
        return json;
    }

    private static String read(InputStream is) throws IOException {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        while ((line = br.readLine()) != null) {
            line = new String(line.getBytes(), "utf-8");
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    private class MyTask extends AsyncTask<String, Integer, String> {
        String response;
        @Override
        protected String doInBackground(String[] strings) {
            try {
                response = Response(strings[0], strings[1]);
                System.out.println(strings[1]);
            } catch (Exception e) {
                name3.setText("Error: " + strings[1]);
            }
            return null;
        }

        //onPostExecute方法用于在执行完后台任务后更新UI,显示结果
        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG, "onPostExecute(Result result) called");
            String[] text;
            try {
                JSONObject dataJson = new JSONObject(response);
                JSONArray data = dataJson.getJSONArray("Traces");
                JSONObject info = data.getJSONObject(0);
                JSONObject info1 = data.getJSONObject(1);
                JSONObject info2 = data.getJSONObject(2);
                JSONObject info3 = data.getJSONObject(3);
                JSONObject info4 = data.getJSONObject(4);
                JSONObject info5 = data.getJSONObject(5);
                //JSONObject info6 = data.getJSONObject(6);
                //JSONObject info7 = data.getJSONObject(7);
                String station = info.getString("AcceptStation");
                String time = info.getString("AcceptTime");
                String station1 = info1.getString("AcceptStation");
                String time1 = info1.getString("AcceptTime");
                String station2 = info2.getString("AcceptStation");
                String time2 = info2.getString("AcceptTime");
                String station3 = info3.getString("AcceptStation");
                String time3 = info3.getString("AcceptTime");
                String station4 = info4.getString("AcceptStation");
                String time4 = info4.getString("AcceptTime");
                String station5 = info5.getString("AcceptStation");
                String time5 = info5.getString("AcceptTime");
//                String station6 = info6.getString("AcceptStation");
//                String time6 = info6.getString("AcceptTime");
//                String station7 = info7.getString("AcceptStation");
//                String time7 = info7.getString("AcceptTime");
                name3.setText(time);
                name31.setText(station);
                name4.setText(time1);
                name41.setText(station1);
                name5.setText(time2);
                name51.setText(station2);
                name6.setText(time3);
                name61.setText(station3);
                name7.setText(time4);
                name71.setText(station4);
                name8.setText(time5);
                name81.setText(station5);
//                name9.setText(time6);
//                name91.setText(station6);
//                name10.setText(time7);
//                name11.setText(station7);
            } catch (Exception e) {
                //System.out.println(response.toString());
                name11.setText(e.toString());
                name10.setText("Error:");
            }
        }
    }
}
