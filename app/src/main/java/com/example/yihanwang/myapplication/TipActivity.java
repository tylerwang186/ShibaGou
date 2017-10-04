package com.example.yihanwang.myapplication;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Kaley on 29/9/17.
 */

public class TipActivity extends Activity {
    private ImageView map;
    private TextView tip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tip_activity_1);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        getSupportActionBar().setHomeButtonEnabled(false);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(), "teen.ttf");

        tip = (TextView) findViewById(R.id.tip);
        tip.setText("➤ Clicking on a photo you just took to go to the comparison page \n\n ➤ Swiping up the photo you just took to remove it");
        tip.setTypeface(font);
        //map = (ImageView) findViewById(R.id.map);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .6), (int) (height * .4));
    }
}
