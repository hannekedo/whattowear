package com.example.whattowear;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LoadWeatherData {

    TextView mTxtError;
    String maxTempBottom, minTempBottom, maxTempMiddle, minTempMiddle, maxTempTop, minTempTop, totalSnowfall, url, urlInput;

    private ProgressBar spinner;
    Button button;

    //Getting the weather data through API
    public void loadWeatherData(String urlInput) {
        String urlInputEncoded = urlInput.replaceAll(" ", "%20");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = df.format(Calendar.getInstance().getTime());
        System.out.print(currentDate);
        url="http://api.worldweatheronline.com/premium/v1/ski.ashx?key=xxxx&q="+urlInputEncoded+"&format=json&num_of_days=1&date="+currentDate;

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Get JSON Objects
                            JSONObject weatherData = response.getJSONObject("data").getJSONArray("weather").getJSONObject(0);
                            JSONObject bottomTemp = weatherData.getJSONArray("bottom").getJSONObject(0);
                            JSONObject middleTemp = weatherData.getJSONArray("mid").getJSONObject(0);
                            JSONObject topTemp = weatherData.getJSONArray("top").getJSONObject(0);

                            maxTempBottom = bottomTemp.getString("maxtempC");
                            minTempBottom = bottomTemp.getString("mintempC");
                            maxTempMiddle = middleTemp.getString("maxtempC");
                            minTempMiddle = middleTemp.getString("mintempC");
                            maxTempTop = topTemp.getString("maxtempC");
                            minTempTop = topTemp.getString("mintempC");
                            totalSnowfall = weatherData.getString("totalSnowfall_cm");

                            button.setEnabled(true);
                            spinner.setVisibility(View.GONE);
                            sendWeatherData();
                        }
                        catch (Exception e) {
                            txtError(e);
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        txtError(error);
                    }
                });

        // Add the request to the RequestQueue.
        queue.add(request);
    }

    //Error handling
    private void txtError(Exception e) {
        mTxtError.setVisibility(View.VISIBLE);
        spinner.setVisibility(View.GONE);
        button.setEnabled(true);
        e.printStackTrace();
    }

    //When weather data is retrieved, send to next activity
    public void sendWeatherData() {
        Intent intent = new Intent(this, DisplayAdviceActivity.class);
        intent.putExtra("maxTempBottom", maxTempBottom);
        intent.putExtra("minTempBottom", minTempBottom);
        intent.putExtra("maxTempMiddle", maxTempMiddle);
        intent.putExtra("minTempMiddle", minTempMiddle);
        intent.putExtra("maxTempTop", maxTempTop);
        intent.putExtra("minTempTop", minTempTop);
        Intent totalSnowfall = intent.putExtra("totalSnowfall", this.totalSnowfall);
        startActivity(intent);

    }
}
