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
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.core.FirestoreClient;
import com.graduationproject.zakerly.MainActivity;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.adapters.ItemSearchAdapter;
import com.graduationproject.zakerly.core.models.Instructor;
import com.graduationproject.zakerly.core.models.ItemSearchModel;
import com.graduationproject.zakerly.core.network.firebase.FirebaseDataBaseClient;
import com.graduationproject.zakerly.databinding.FragmentSearchBinding;
import com.graduationproject.zakerly.navigation.instructorAccountPage.InstructorPageRepository;
import com.graduationproject.zakerly.navigation.instructorAccountPage.InstructorPageViewModel;
import com.graduationproject.zakerly.navigation.instructorAccountPage.InstructorPageViewModelFactory;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

   FirebaseUser fUser;
   DatabaseReference ref;
    public static final String TAG = "searchFragment";
    private FragmentSearchBinding binding;

    private ItemSearchAdapter adapter;
    private RecyclerView mRecyclerView;
    CardView cardSearch;
    EditText etSearch;
    TextView txtSearch ;
    ImageView icFilter;
    ArrayList<ItemSearchModel> items ;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       binding = FragmentSearchBinding.inflate(inflater,container,false);
        ((MainActivity) requireActivity()).setNavigationVisibility(true);
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

            }

            @Override
            public void afterTextChanged(Editable editable) {
                txtSearch.setVisibility(View.GONE);
                 search(editable.toString());
            }
        });
        binding.icFilter.setOnClickListener(view -> showBottomSheet());

    }

    private void search(String str) {
    fUser = FirebaseAuth.getInstance().getCurrentUser();
    ref = FirebaseDatabase.getInstance().getReference().child("users");
    ref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                Log.d("Test = ",dataSnapshot.getValue().toString());
            }
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
    }
    private void showBottomSheet(){
        BottomSheetDialog dialogFragment = new BottomSheetDialog(requireContext());
        dialogFragment.setContentView(R.layout.fragment_filter_dialog);
        dialogFragment.show();
    }
}