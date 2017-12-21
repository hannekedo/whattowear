package com.example.whattowear;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.Button;

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
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    TextView mTxtError;
    String maxTempBottom, minTempBottom, maxTempMiddle, minTempMiddle, maxTempTop, minTempTop, totalSnowfall, url, urlInput;

    private ProgressBar spinner;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTxtError = (TextView) findViewById(R.id.error);
        spinner=(ProgressBar)findViewById(R.id.progressBar1);
        button = (Button)findViewById(R.id.button1);
        spinner.setVisibility(View.GONE);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        mTxtError.setVisibility(View.GONE);
    }

    //Called when the user clicks the Send button. Check input.
    public void sendMessage (View view) {
        EditText editText = (EditText) findViewById(R.id.enter_location);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        urlInput = editText.getText().toString();
        if (editText.getText().length() == 0|| Pattern.matches("[a-zA-Z][a-zA-Z ]*", urlInput)==false) {
            editText.setError("Please enter a valid location");
        }
        else {
            button.setEnabled(false);
            spinner.setVisibility(View.VISIBLE);
            LoadWeatherData weatherData = new LoadWeatherData();
            weatherData.loadWeatherData(urlInput);
        }

    }

}
