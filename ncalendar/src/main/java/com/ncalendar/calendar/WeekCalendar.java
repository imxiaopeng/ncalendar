package com.ncalendar.calendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;

import com.ncalendar.adapter.BasePagerAdapter;
import com.ncalendar.adapter.WeekPagerAdapter;
import com.ncalendar.utils.CalendarUtil;

import org.joda.time.LocalDate;


/**
 * @author necer
 * @date 2018/9/11
 * qq群：127278900
 */
public class WeekCalendar extends BaseCalendar {

    public WeekCalendar(@NonNull Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected BasePagerAdapter getPagerAdapter(Context context, BaseCalendar baseCalendar) {
        return new WeekPagerAdapter(context, baseCalendar);
    }

    @Override
    protected int getTwoDateCount(LocalDate startDate, LocalDate endDate, int type) {
        return CalendarUtil.getIntervalWeek(startDate, endDate, type);
    }

    @Override
    protected LocalDate getIntervalDate(LocalDate localDate, int count) {
        return localDate.plusWeeks(count);
    }

}
