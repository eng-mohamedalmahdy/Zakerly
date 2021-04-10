package com.graduationproject.zakerly.intro.onboarding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.ViewPager;

import android.content.ContextWrapper;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.base.BaseFragment;
import com.graduationproject.zakerly.core.cache.DataStoreManger;
import com.graduationproject.zakerly.databinding.FragmentOnBoardingBinding;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;


public class OnBoardingFragment extends BaseFragment {

    private FragmentOnBoardingBinding binding;
    private ViewPager mSliderPager;
    private LinearLayout mDotsLayout;
    private SliderAdapter mSliderAdapter;
    private TextView[] mDots;
    private TextView mNextBtn;
    private TextView mBackBtn;
    private int mCurrentSlider;
    private CompositeDisposable disposables;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentOnBoardingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Hooks
        mSliderPager = binding.viewPagerSlider;
        mDotsLayout = binding.layoutDots;
        mNextBtn = binding.buttonNext;
        mBackBtn = binding.buttonBack;
        disposables = new CompositeDisposable();

        // Create and call adapter
        mSliderAdapter = new SliderAdapter(getContext());
        mSliderPager.setAdapter(mSliderAdapter);
        // call function that create dots
        addDotsIndicator(0);
        // call listener
        mSliderPager.addOnPageChangeListener(viewPagerListener);

        mNextBtn.setOnClickListener(v -> {
            if (mCurrentSlider == mDots.length - 1) {
                DataStoreManger manger = DataStoreManger.getInstance(getContext());
                Disposable isfFirstLaunchDisposable=manger.setIsFirstLaunch(true).subscribe();
                disposables.add(isfFirstLaunchDisposable);
                NavHostFragment.findNavController(OnBoardingFragment.this).navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToLogInFragment());

            } else {
                mSliderPager.setCurrentItem(mCurrentSlider + 1);
            }
        });
        mBackBtn.setOnClickListener(v -> mSliderPager.setCurrentItem(mCurrentSlider - 1));
    }


    private void addDotsIndicator(int position) {
        mDots = new TextView[3];
        mDotsLayout.removeAllViews();

        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(getContext());
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(new ContextWrapper(getContext()).getColor(R.color.black));
            mDotsLayout.addView(mDots[i]);
        }

        if (mDots.length > 0) {
            mDots[position].setTextColor(new ContextWrapper(getContext()).getColor(R.color.purple_500));
        }
    }

    ViewPager.OnPageChangeListener viewPagerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            mCurrentSlider = position;
            if (position == 0) {
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(false);
                mBackBtn.setVisibility(View.INVISIBLE);
                mNextBtn.setText(getText(R.string.next));

            } else if (position == mDots.length - 1) {
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.VISIBLE);
                mNextBtn.setText(getText(R.string.done));
                mBackBtn.setText(R.string.back);
            } else {
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.VISIBLE);
                mNextBtn.setText(getText(R.string.next));
                mBackBtn.setText(getText(R.string.back));
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onPause() {
        super.onPause();
        disposables.dispose();
    }

    @Override
    public void onStop() {
        super.onStop();
        disposables.dispose();
    }
}