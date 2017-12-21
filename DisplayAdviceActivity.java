package com.example.whattowear;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.LinkedList;

public class DisplayAdviceActivity extends AppCompatActivity {

    TextView maxTempBottomView, minTempBottomView, maxTempMiddleView, minTempMiddleView, maxTempTopView, minTempTopView,totalSnowfallView;
    String maxTempMiddle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_advice);
        maxTempBottomView = (TextView) findViewById(R.id.maxtempbottom);
        minTempBottomView = (TextView) findViewById(R.id.mintempbottom);
        maxTempMiddleView = (TextView) findViewById(R.id.maxtempmiddle);
        minTempMiddleView = (TextView) findViewById(R.id.mintempmiddle);
        maxTempTopView = (TextView) findViewById(R.id.maxtemptop);
        minTempTopView = (TextView) findViewById(R.id.mintemptop);
        totalSnowfallView = (TextView) findViewById(R.id.snowfallcm);

        showWeather();
        showAdvice();
    }

    //Add advice to linked list and display in a table
    public void showAdvice() {
        int maxTempMiddleInt = (Integer.valueOf(maxTempMiddle)).intValue();
        LinkedList<String> adviceList = new LinkedList<>();
        adviceList.add("Shirt");
        adviceList.add("Ski pants");
        adviceList.add("Gloves");
        adviceList.add("Helmet");

        if (maxTempMiddleInt < 15) {
            adviceList.add("Jacket");
        }
        if (maxTempMiddleInt < 0) {
            adviceList.add("Thermo shirt");
        }
        if (maxTempMiddleInt < -5) {
            adviceList.add("Fleece shirt");
            adviceList.add("Thermo pants");
        }

        TableLayout tl = (TableLayout) findViewById(R.id.maintable);
        tl.setBackgroundResource(R.drawable.my_shape_file);

        int sizeAdviceList = adviceList.size();
        for (int i = 0; i < sizeAdviceList; i++)
        {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            TextView itemTV = new TextView(this);
            itemTV.setText(adviceList.get(i));
            itemTV.setTextColor(Color.BLACK);
            itemTV.setGravity(Gravity.CENTER_HORIZONTAL);
            itemTV.setTextSize(17);
            tr.addView(itemTV);

            tl.addView(tr, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
        }

    }

    //Show weatherdata received from previous activity
    public void showWeather() {
        Intent intent = getIntent();
        maxTempMiddle = intent.getStringExtra("maxTempMiddle");

        maxTempBottomView.setText(intent.getStringExtra("maxTempBottom")+ "°");
        minTempBottomView.setText(intent.getStringExtra("minTempBottom") + "°");
        maxTempMiddleView.setText(maxTempMiddle + "°");
        minTempMiddleView.setText(intent.getStringExtra("minTempMiddle") + "°");
        maxTempTopView.setText(intent.getStringExtra("maxTempTop") + "°");
        minTempTopView.setText(intent.getStringExtra("minTempTop") + "°");
        totalSnowfallView.setText(intent.getStringExtra("totalSnowfall") + "cm");
    }


}
