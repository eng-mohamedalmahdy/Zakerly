package com.graduationproject.zakerly.authentication.splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.graduationproject.zakerly.R;

public class OnBoardingActivity extends AppCompatActivity {
    private ViewPager mSliderPager;
    private LinearLayout mDotsLayout;
    private SliderAdapter mSliderAdapter;
    private TextView[] mDots;
    private Button mNextBtn;
    private Button mBackBtn;
    private int mCurrentSlider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding_repositoryd);
        // Hooks
        mSliderPager = findViewById(R.id.view_pager_slider);
        mDotsLayout = findViewById(R.id.layout_dots);
        mNextBtn = findViewById(R.id.button_next);
        mBackBtn = findViewById(R.id.button_back);
        // Create and call adapter
        mSliderAdapter = new SliderAdapter(this);
        mSliderPager.setAdapter(mSliderAdapter);
        // call function that create dots
        addDotsIndicator(0);
        // call listener
        mSliderPager.addOnPageChangeListener(viewListener);

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSliderPager.setCurrentItem(mCurrentSlider + 1);
            }
        });
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSliderPager.setCurrentItem(mCurrentSlider - 1);
            }
        });

    }
    private void addDotsIndicator(int position){
        mDots = new TextView[3];
        mDotsLayout.removeAllViews();

        for (int i = 0; i<mDots.length;i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.black));

            mDotsLayout.addView(mDots[i]);
        }

        if (mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.purple_500));
        }
    }
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            mCurrentSlider = position;
            if(position == 0){
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(false);
                mBackBtn.setVisibility(View.INVISIBLE);

                mNextBtn.setText("next");
            }else if (position == mDots.length-1){
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.VISIBLE);

                mNextBtn.setText("finish");
                mBackBtn.setText("back");
            }else{
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.VISIBLE);

                mNextBtn.setText("next");
                mBackBtn.setText("back");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}