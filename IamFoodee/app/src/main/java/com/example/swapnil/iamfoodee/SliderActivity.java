package com.example.swapnil.iamfoodee;

/**
 * Created by swapnil on 7/4/18.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SliderActivity extends AppCompatActivity {

    SharedPreferences mPrefs;
    final String welcomeScreenShownPref = "welcomeScreenShown";


    private ViewPager mslideviewpager;
    private LinearLayout mDotLayout;


    private TextView[] mDots;

    private sliderAdapter sliderAdapter;

    private Button nextbtn;
    private Button prevbtn;

    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_slide);

        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // second argument is the default to use if the preference can't be found
        Boolean welcomeScreenShown = mPrefs.getBoolean(welcomeScreenShownPref, false);


        if (welcomeScreenShown) {
//            Toast.makeText(this, "I was here", Toast.LENGTH_SHORT).show();
            // here you can launch another activity if you like
            // the code below will display a popup

//            String whatsNewTitle ="he";
//            String whatsNewText ="gg";
//            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(whatsNewTitle).setMessage(whatsNewText).setPositiveButton(
//                    "ok", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    }).show();

            Intent homeIntent = new Intent(SliderActivity.this, MainActivity.class);
            startActivity(homeIntent);

//            SharedPreferences.Editor editor = mPrefs.edit();
//            editor.putBoolean(welcomeScreenShownPref, true);
//            editor.commit(); // Very important to save the preference
        }
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(welcomeScreenShownPref, true);
        editor.commit(); // Very important to save the preference




        mslideviewpager =(ViewPager) findViewById(R.id.slideViewpager);
        mDotLayout =(LinearLayout) findViewById(R.id.dotsLayout);

        nextbtn=(Button)findViewById(R.id.nxtBtn);
        prevbtn=(Button)findViewById(R.id.prevBtn);


        sliderAdapter = new sliderAdapter(this);
        mslideviewpager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        mslideviewpager.addOnPageChangeListener(viewListener);

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int currenrPage = mslideviewpager.getCurrentItem()+1;
                if (currenrPage <mDots.length) {

                    mslideviewpager.setCurrentItem(currenrPage);

                } else {
                    Intent intent = new Intent(SliderActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        });

        prevbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mslideviewpager.setCurrentItem(mCurrentPage -1);
            }
        });
    }


    public void addDotsIndicator(int position){

        mDots= new TextView[3];
        mDotLayout.removeAllViews();

        for(int i=0;i<mDots.length;i++){
            mDots[i]=new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTranparentWhite));

            mDotLayout.addView(mDots[i]);
        }

        if(mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorWhite ));
        }


    }

    ViewPager.OnPageChangeListener viewListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int i) {

            addDotsIndicator(i);
            mCurrentPage=i;

            if(i==0){

                nextbtn.setEnabled(true);
                prevbtn.setEnabled(false);
                prevbtn.setVisibility(View.INVISIBLE);

                nextbtn.setText("Next");
                prevbtn.setText("");
            }

            else if(i == mDots.length - 1){
                nextbtn.setEnabled(true);
                prevbtn.setEnabled(true);
                prevbtn.setVisibility(View.VISIBLE);

                nextbtn.setText("Finsih");
                prevbtn.setText("Back");
            }

            else
            {
                nextbtn.setEnabled(true);
                prevbtn.setEnabled(true);
                prevbtn.setVisibility(View.VISIBLE);

                nextbtn.setText("Next");
                prevbtn.setText("Back");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };










}
