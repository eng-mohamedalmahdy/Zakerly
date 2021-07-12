package com.graduationproject.zakerly.meetings.meeting;

import android.os.Bundle;

import com.graduationproject.zakerly.R;

import org.jitsi.meet.sdk.JitsiMeetActivity;

public class MeetingActivity extends JitsiMeetActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);

    }
}