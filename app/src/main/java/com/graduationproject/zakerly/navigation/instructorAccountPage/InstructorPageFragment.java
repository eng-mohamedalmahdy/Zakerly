package com.graduationproject.zakerly.navigation.instructorAccountPage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.graduationproject.zakerly.MainActivity;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.databinding.FragmentInstructorPageBinding;
import com.graduationproject.zakerly.navigation.viewteacherprofile.ShowTeacherProfileFragmentArgs;
import com.graduationproject.zakerly.navigation.viewteacherprofile.ShowTeacherProfileRepository;
import com.graduationproject.zakerly.navigation.viewteacherprofile.ShowTeacherProfileViewModel;
import com.graduationproject.zakerly.navigation.viewteacherprofile.ShowTeacherProfileViewModelFactory;

public class InstructorPageFragment extends Fragment {

    private FragmentInstructorPageBinding binding;
    private InstructorPageViewModel viewModel;
    private InstructorPageAdapter adapter;
    private RecyclerView mRecyclerView;
    private TextView numOfStudent;
    ImageView  icAction,profileImage, icFavorite,icVideoCall,icNotes;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentInstructorPageBinding.inflate(inflater,container,false);
        viewModel = new InstructorPageViewModelFactory(new InstructorPageRepository()).create(InstructorPageViewModel.class);
        ((MainActivity) requireActivity()).setNavigationVisibility(false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initListeners();
    }

    private void initListeners() {
        icAction.setOnClickListener(view -> {
            popupMenue();
        });
    }


    private void initViews() {
        icAction = binding.icMoreAction;
        profileImage = binding.instructorPageImage;
        icFavorite = binding.instructorPageIcFavorite;
        icVideoCall = binding.instructorPageIcVideocall;
        icNotes = binding.instructorPageIcNote;
        numOfStudent = binding.instructorPageNumOfStudent;
    }

    private void popupMenue() {
        PopupMenu pm = new PopupMenu(getContext(), icAction);
        pm.getMenuInflater().inflate(R.menu.teacher_menu, pm.getMenu());
        pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
           @Override
           public boolean onMenuItemClick(MenuItem item) {
               switch (item.getItemId()){
                   case R.id.first:

                       return true;

                   case R.id.second:

                       return true;

                   case R.id.third:

                       return true;
               }

               return true;
           }
       });
        pm.show();
    }


}