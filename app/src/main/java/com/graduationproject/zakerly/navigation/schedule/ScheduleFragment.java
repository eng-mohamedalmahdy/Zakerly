package com.graduationproject.zakerly.navigation.schedule;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.graduationproject.zakerly.MainActivity;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.cache.Realm.RealmQueries;
import com.graduationproject.zakerly.core.constants.UserTypes;
import com.graduationproject.zakerly.core.models.Schedule;
import com.graduationproject.zakerly.core.models.User;
import com.graduationproject.zakerly.core.network.firebase.FireBaseAuthenticationClient;
import com.graduationproject.zakerly.core.network.firebase.FirebaseDataBaseClient;
import com.graduationproject.zakerly.core.util.Util;
import com.graduationproject.zakerly.databinding.FragmentScheduleBinding;

import java.util.ArrayList;
import java.util.Calendar;

public class ScheduleFragment extends Fragment {
    FragmentScheduleBinding binding;
    RecyclerView mRecyclerView;
    TextView mText;
    FloatingActionButton fab;

    AlertDialog.Builder builder;
    AlertDialog dialog;
    View view;
    TextView date, time, lesson, student, cancel, confirm;
    EditText entryLesson, entryStudent, entryDate, entryTime;

    DatePickerDialog.OnDateSetListener setListener;
    TimePickerDialog timePickerDialog;
    Calendar calender;
    int year, month, day, currentHour, currentMinute;
    String amPm;
    ScheduleAdapter adapter;
    ArrayList<Schedule> schedules;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentScheduleBinding.inflate(inflater, container, false);
        schedules = new ArrayList<>();
        FirebaseUser firebaseuser = FireBaseAuthenticationClient.getInstance().getCurrentUser();
        User localUser = null;
        if (firebaseuser != null) {
            localUser = new RealmQueries().getUser(firebaseuser.getUid());
        }
        if (localUser.getType().equals(UserTypes.TYPE_STUDENT))
            ((MainActivity) getActivity()).setNavigationVisibility(false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initListener();
    }


    private void openDialog() {
        builder = new AlertDialog.Builder(getActivity());
        view = getLayoutInflater().inflate(R.layout.schedule_dialog, null);
        builder.setView(view);
        dialog = builder.create();
        initDialogViews();
        dialog.show();
        initDialogListener();
        calender = Calendar.getInstance();
        year = calender.get(Calendar.YEAR);
        month = calender.get(Calendar.MONTH);
        day = calender.get(Calendar.DAY_OF_MONTH);
    }

    private void initListener() {
        binding.fabAdd.setOnClickListener(view -> openDialog());
    }

    private void initViews() {
        mRecyclerView = binding.scheduleRecyclerView;
        mText = binding.scheduleTxt;
        fab = binding.fabAdd;
        adapter = new ScheduleAdapter();
        mRecyclerView.setAdapter(adapter);
        FirebaseDataBaseClient.getInstance().getSchedules().addOnSuccessListener(dataSnapshot -> {
            schedules = new ArrayList<>();
            dataSnapshot.getChildren().forEach(schedulesDataSnapshot -> schedules.add(schedulesDataSnapshot.getValue(Schedule.class)));
            adapter.setSchedules(schedules);

            mText.setVisibility(schedules.size() == 0 ? View.VISIBLE : View.GONE);

            setUpRecyclerView();
        });

    }

    private void initDialogViews() {
        date = view.findViewById(R.id.date);
        entryDate = view.findViewById(R.id.entry_date);
        time = view.findViewById(R.id.time);
        entryTime = view.findViewById(R.id.entry_time);
        lesson = view.findViewById(R.id.lesson);
        entryLesson = view.findViewById(R.id.entry_lesson);
        student = view.findViewById(R.id.student);
        entryStudent = view.findViewById(R.id.entry_student);
        cancel = view.findViewById(R.id.cancel);
        confirm = view.findViewById(R.id.confirm);
    }

    private void initDialogListener() {

        cancel.setOnClickListener(view -> dialog.dismiss());

        entryDate.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(), android.R.style.Theme_Holo_Dialog_MinWidth, setListener, year, month, day);
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();

        });

        setListener = (datePicker, year, month, day) -> {
            month = month + 1;
            String date = day + "-" + month + "-" + year;
            entryDate.setText(date);
        };

        entryTime.setOnClickListener(view -> {
            calender = Calendar.getInstance();
            currentHour = calender.get(Calendar.HOUR_OF_DAY);
            currentMinute = calender.get(Calendar.MINUTE);
            timePickerDialog = new TimePickerDialog(getContext(), (timePicker, hour, minute) -> {
                if (hour >= 12) {
                    amPm = "PM";
                } else {
                    amPm = "AM";
                }
                entryTime.setText(String.format("%02d:%02d:00", hour, minute));

            }, currentHour, currentMinute, true);
            timePickerDialog.show();
        });

        confirm.setOnClickListener(view -> {
            mText.setVisibility(View.GONE);
            if (valid()) {
                if (schedules.size() > 0) {
                    mText.setVisibility(View.GONE);
                } else {
                    mText.setVisibility(View.VISIBLE);
                }
                FirebaseDataBaseClient.getInstance()
                        .addSchedules(new Schedule(FirebaseDataBaseClient.getInstance().getRandomKey(),
                                Util.getTimeStamp((entryDate.getText().toString() + " " + entryTime.getText().toString())),
                                "",
                                "",
                                entryStudent.getText().toString(),
                                "",
                                entryLesson.getText().toString()))
                        .addOnSuccessListener(dataSnapshot -> setUpRecyclerView());
            } else {
                Toast.makeText(getContext(), "Please Complete All Data ", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void setUpRecyclerView() {

        if (dialog != null)
            dialog.dismiss();
        adapter.notifyDataSetChanged();
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Schedule schedule = schedules.get(viewHolder.getAdapterPosition());
                FirebaseDataBaseClient.getInstance().deleteSchedules(schedule).addOnSuccessListener(unused -> {
                    schedules.remove(viewHolder.getAdapterPosition());
                    adapter.notifyDataSetChanged();
                    if (schedules.size() == 0) {
                        mText.setVisibility(View.VISIBLE);
                    }
                });
            }
        }).attachToRecyclerView(mRecyclerView);
        FirebaseDataBaseClient.getInstance().getSchedules().addOnSuccessListener(dataSnapshot -> {
            schedules = new ArrayList<>();
            dataSnapshot.getChildren().forEach(schedulesDataSnapshot -> schedules.add(schedulesDataSnapshot.getValue(Schedule.class)));
            adapter.setSchedules(schedules);
        });
    }


    private boolean valid() {
        return !entryTime.getText().toString().isEmpty() && !entryDate.getText().toString().isEmpty()
                && !entryStudent.getText().toString().isEmpty() && !entryLesson.getText().toString().isEmpty();
    }


}