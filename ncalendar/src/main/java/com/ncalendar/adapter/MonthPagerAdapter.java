package com.ncalendar.adapter;

import android.content.Context;

import com.ncalendar.calendar.BaseCalendar;
import com.ncalendar.enumeration.CalendarType;

import org.joda.time.LocalDate;

/**
 * @author necer
 * @date 2018/9/11
 * qq群：127278900
 */
public class MonthPagerAdapter extends BasePagerAdapter {

    public MonthPagerAdapter(Context context, BaseCalendar baseCalendar) {
        super(context, baseCalendar);
    }

    @Override
    protected LocalDate getPageInitializeDate(int position) {
        return getInitializeDate().plusMonths(position - getPageCurrIndex());
    }

    @Override
    protected CalendarType getCalendarType() {
        return CalendarType.MONTH;
    }
}
