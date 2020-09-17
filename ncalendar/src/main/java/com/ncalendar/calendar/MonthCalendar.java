package com.ncalendar.calendar;

import android.content.Context;
import android.util.AttributeSet;

import com.ncalendar.adapter.BasePagerAdapter;
import com.ncalendar.adapter.MonthPagerAdapter;
import com.ncalendar.utils.CalendarUtil;

import org.joda.time.LocalDate;

/**
 * @author necer
 * @date 2018/9/11
 * qq群：127278900
 */
public class MonthCalendar extends BaseCalendar {

    public MonthCalendar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected BasePagerAdapter getPagerAdapter(Context context, BaseCalendar baseCalendar) {
        return new MonthPagerAdapter(context.getApplicationContext(), baseCalendar);
    }

    @Override
    protected int getTwoDateCount(LocalDate startDate, LocalDate endDate, int type) {
        return CalendarUtil.getIntervalMonths(startDate, endDate);
    }

    @Override
    protected LocalDate getIntervalDate(LocalDate localDate, int count) {
        return localDate.plusMonths(count);
    }

}
