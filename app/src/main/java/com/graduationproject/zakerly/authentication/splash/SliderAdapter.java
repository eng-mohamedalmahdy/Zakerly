package com.graduationproject.zakerly.authentication.splash;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.graduationproject.zakerly.R;

public class SliderAdapter extends PagerAdapter {
    Context context;

    public SliderAdapter(Context context){
        this.context = context;
    }

    private int [] slide_images = {
            R.drawable.onboardingone,
            R.drawable.onboardingtwo,
            R.drawable.onboardingthree
    };

    public int [] slide_descriptions = {
            R.string.text_slider_description_one,
            R.string.text_slider_description_two,
            R.string.text_slider_description_three
    };

    @Override
    public int getCount() {
        return slide_images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.slide_layout,container,false);

        ImageView sliderImage = view.findViewById(R.id.image_view_slider);
        TextView sliderDescription = view.findViewById(R.id.text_view_description);

        sliderImage.setImageResource(slide_images[position]);
        sliderDescription.setText(slide_descriptions[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
