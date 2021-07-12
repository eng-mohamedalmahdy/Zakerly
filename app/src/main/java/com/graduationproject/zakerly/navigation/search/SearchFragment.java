package com.graduationproject.zakerly.navigation.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.slider.RangeSlider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.graduationproject.zakerly.MainActivity;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.adapters.ItemSearchAdapter;
import com.graduationproject.zakerly.core.models.Instructor;
import com.graduationproject.zakerly.core.models.ItemSearchModel;
import com.graduationproject.zakerly.core.network.firebase.FirebaseDataBaseClient;
import com.graduationproject.zakerly.databinding.FragmentSearchBinding;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

public class SearchFragment extends Fragment {

    public static final String TAG = "searchFragment";
    private FragmentSearchBinding binding;

    private ItemSearchAdapter adapter;
    private RecyclerView mRecyclerView;
    CardView cardSearch;
    EditText etSearch;
    TextView txtSearch;
    ImageView icFilter;
    private float minRate = 0f;
    private float minPrice = 0f;
    private float maxPrice = 1000f;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        ((MainActivity) requireActivity()).setNavigationVisibility(false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initListener();

    }


    private void initListener() {
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txtSearch.setVisibility(charSequence.length() == 0 && adapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
                search(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        binding.icFilter.setOnClickListener(view -> showBottomSheet());

    }

    private void search(String str) {

        FirebaseDataBaseClient.getInstance().getAllInstructors().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ItemSearchModel> results = new ArrayList<>();
                snapshot.getChildren().forEach(instructorsDataSnapshot -> {
                    if (instructorsDataSnapshot.getValue(Instructor.class) != null) {
                        results.add(new ItemSearchModel(instructorsDataSnapshot.getValue(Instructor.class)));
                    }
                });
                List<ItemSearchModel> items = results.stream().filter(itemSearchModel -> {
                    boolean specialRes = itemSearchModel.getSpecialisations() != null &&
                            itemSearchModel.getSpecialisations()
                                    .stream()
                                    .map(specialisation -> specialisation.getEn().contains(str) || specialisation.getAr().contains(str))
                                    .reduce(false, (a, b) -> a || b);
                    boolean nameRes = itemSearchModel.getName().contains(str);
                    boolean jobRes = itemSearchModel.getJob() != null && itemSearchModel.getJob().contains(str);
                    boolean aboveRate = itemSearchModel.getRate() >= minRate;
                    boolean inPriceRange = itemSearchModel.getInstructor().getPricePerHour() >= minPrice && itemSearchModel.getInstructor().getPricePerHour() <= maxPrice;
                    return (specialRes || nameRes || jobRes) && aboveRate && inPriceRange;
                }).collect(Collectors.toList());
                adapter.setItems(new ArrayList<>(items));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initViews() {
        cardSearch = binding.cardSearch;
        etSearch = binding.etSearch;
        txtSearch = binding.txtSearchAboutTeacher;
        mRecyclerView = binding.searchItemRecyclerView;
        icFilter = binding.icFilter;
        adapter = new ItemSearchAdapter(this);
        mRecyclerView.setAdapter(adapter);
    }

    private void showBottomSheet() {
        Log.d(TAG, "showBottomSheet: Filter");
        BottomSheetDialog dialogFragment = new BottomSheetDialog(requireContext());
        dialogFragment.setContentView(R.layout.fragment_filter_dialog);
        RatingBar minRateBar = dialogFragment.findViewById(R.id.bottom_sheet_dialog_rate);
        RangeSlider slider = dialogFragment.findViewById(R.id.range_slider);

        slider.setValues(minRate, maxPrice);
        slider.setLabelFormatter(value -> {
            NumberFormat format = NumberFormat.getCurrencyInstance();
            format.setMaximumFractionDigits(0);
            format.setCurrency(Currency.getInstance("EGP"));
            return format.format(Double.valueOf(value));
        });

        minRateBar.setRating(minRate);
        dialogFragment.setOnDismissListener(dialog -> {
            minRate = minRateBar.getRating();
            minPrice = slider.getValues().get(0);
            maxPrice = slider.getValues().get(1);
            search(binding.etSearch.getText().toString());
        });
        dialogFragment.show();
    }
}