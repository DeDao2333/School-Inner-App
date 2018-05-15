package com.example.java.algorithm.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.java.algorithm.R;
import com.example.java.algorithm.Test.TestData;
import com.example.java.algorithm.javabean.team;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class TeamEditAc extends EditPageBaseAc {

    @BindView(R.id.tv_team_title)
    TextInputEditText mTvTeamTitle;
    @BindView(R.id.tv_team_content)
    TextInputEditText mTvTeamContent;
    @BindView(R.id.tv_team_place)
    TextInputEditText mTvTeamPlace;
    @BindView(R.id.tv_team_curPeo)
    TextInputEditText mTvTeamCurPeo;
    @BindView(R.id.tv_team_maxPeo)
    TextInputEditText mTvTeamMaxPeo;
    @BindView(R.id.bt_team_apply)
    Button mBtTeamApply;
    @BindView(R.id.tv_team_startTime)
    TextView mTvTeamStartTime;

    private Calendar mCalendar;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private BmobDate date;
    private TimePickerDialog timePickerDialog;
    private DatePickerDialog datePickerDialog;

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    mYear = year;
                    mMonth = month;
                    mDay = dayOfMonth;

                }
            };

    private TimePickerDialog.OnTimeSetListener mOnTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    mCalendar.set(mYear, mMonth, mDay, hourOfDay, minute);
                    date = new BmobDate(mCalendar.getTime());
                    mTvTeamStartTime.setText(date.getDate());

                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_edit);
        ButterKnife.bind(this);

        mCalendar = Calendar.getInstance();

        mBtTeamApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upLoadData();
                finish();
            }
        });

        mTvTeamStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime();

            }
        });

    }

    public void upLoadData() {
        team team = new team();
        team.setTime_start(date);
        team.setUsername(TestData.getTestUser());
        team.setContent(mTvTeamContent.getText().toString());
        team.setCur_peo(mTvTeamCurPeo.getText().toString());
        team.setMax_peo(mTvTeamMaxPeo.getText().toString());
        team.setPlace(mTvTeamPlace.getText().toString());
        team.setTitle(mTvTeamTitle.getText().toString());
        team.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                Toast.makeText(TeamEditAc.this, "upload succeed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setTime() {
        timePickerDialog = new TimePickerDialog(
                this,
                mOnTimeSetListener,
                mCalendar.get(Calendar.HOUR_OF_DAY),
                mCalendar.get(Calendar.MINUTE),
                true);
        timePickerDialog.show();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            datePickerDialog = new DatePickerDialog(
                    this, mDateSetListener,
                    mCalendar.get(Calendar.YEAR),
                    mCalendar.get(Calendar.MONTH),
                    mCalendar.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.show();
        }

    }

}
