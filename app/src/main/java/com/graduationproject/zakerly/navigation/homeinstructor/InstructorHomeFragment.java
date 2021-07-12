package com.graduationproject.zakerly.navigation.homeinstructor;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.graduationproject.zakerly.MainActivity;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.constants.BottomNavigationConstants;
import com.graduationproject.zakerly.core.network.firebase.FireBaseAuthenticationClient;
import com.graduationproject.zakerly.databinding.FragmentInstructorHomeBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;


public class InstructorHomeFragment extends Fragment {

    private FragmentInstructorHomeBinding binding;
    private PieChart studentsPieChart, hoursPieChart;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInstructorHomeBinding.inflate(inflater, container, false);
        ((MainActivity) requireActivity()).setNavigationVisibility(true);
        ((MainActivity) requireActivity()).setSelectedPage(BottomNavigationConstants.HOME_PAGE);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        studentsPieChart = binding.studentsPieChart;
        hoursPieChart = binding.hoursPieChart;


        setHoursDataChart(getList());
        setStudentDataChart(getList());

    }

    private ArrayList<PieEntry> getList() {
        ArrayList<PieEntry> values = new ArrayList<>();
        Random r = new Random();
        values.add(new PieEntry(r.nextInt(20), "2020"));
        values.add(new PieEntry(r.nextInt(20), "2021"));
        values.add(new PieEntry(r.nextInt(20), "2019"));
        return values;
    }

    private void setStudentDataChart(ArrayList<PieEntry> values) {
        PieDataSet dataSet = new PieDataSet(values, "");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(getResources().getDimensionPixelSize(R.dimen._6ssp));
        PieData data = new PieData(dataSet);
        studentsPieChart.setData(data);
        studentsPieChart.getDescription().setEnabled(false);
        studentsPieChart.setCenterText(getString(R.string.students));
        studentsPieChart.setCenterTextSize(getResources().getDimensionPixelSize(R.dimen._7ssp));
        studentsPieChart.setNoDataText(getString(R.string.no_students_data_yet));
        studentsPieChart.animate();
    }

    private void setHoursDataChart(ArrayList<PieEntry> values) {
        PieDataSet dataSet = new PieDataSet(values, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(getResources().getDimensionPixelSize(R.dimen._6ssp));
        PieData data = new PieData(dataSet);
        hoursPieChart.setData(data);
        hoursPieChart.getDescription().setEnabled(false);
        hoursPieChart.setCenterText(getString(R.string.hours));
        hoursPieChart.setCenterTextSize(getResources().getDimensionPixelSize(R.dimen._7ssp));
        hoursPieChart.setNoDataText(getString(R.string.no_hours_data_yet));
        hoursPieChart.animate();
    }


}