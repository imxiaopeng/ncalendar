package com.ncalendar.ncalendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.ncalendar.NCalendar;
import com.ncalendar.calendar.MonthCalendar;

import java.util.Date;

/**
 * 基于NCalendar封装的日历选择
 *
 * @link{ https://github.com/yannecer/NCalendar }
 */
public class TestCalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_calendar);
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void showDig(View view) {
        NCalendar.newInstance(this).show(new NCalendar.OnDateSelectedListener() {
            @Override
            public void onDateSelect(Date date, String dateStr) {
                Toast.makeText(TestCalendarActivity.this, "选择的是：" + dateStr, Toast.LENGTH_SHORT).show();
            }
        }, new NCalendar.OnInitListener() {
            @Override
            public void onInit(MonthCalendar monthCalendar) {
                //设置日期区间
                monthCalendar.setDateInterval("2020-09-09", "2020-10-10");

                //设置选中日期
                monthCalendar.jumpDate("2020-09-16");
            }
        });
    }
}