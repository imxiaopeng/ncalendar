package com.ncalendar;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ncalendar.adapter.YearAdapter;
import com.ncalendar.calendar.BaseCalendar;
import com.ncalendar.calendar.MonthCalendar;
import com.ncalendar.dialog.NDialog;
import com.ncalendar.enumeration.CheckModel;
import com.ncalendar.enumeration.DateChangeBehavior;
import com.ncalendar.listener.OnCalendarChangedListener;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * NCalendar
 *
 * @author Created by cxp on 2020/9/15.
 */
public class NCalendar {
    private static NCalendar nCalendar;
    private Context mContext;

    private NCalendar(Context context) {
        this.mContext = context;
    }

    public static NCalendar newInstance(Context context) {
        nCalendar = new NCalendar(context);
        return nCalendar;
    }

    public interface OnInitListener {
        void onInit(MonthCalendar monthCalendar);
    }

    public interface OnDateSelectedListener {
        void onDateSelect(Date date, String dateStr);
    }

    /**
     * @param listener     点击日期回调
     * @param initListener 初始化回调
     */
    public void show(OnDateSelectedListener listener, OnInitListener initListener) {
        NDialog dialog = new NDialog(mContext, R.style.dialogStyle) {
            @Override
            protected void onTouchOutside(Dialog d) {
                Log.e("--", "点击了外部");
                d.dismiss();
            }
        };
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View contentView = LayoutInflater.from(mContext.getApplicationContext()).inflate(R.layout.content_view_calendar, null);
        dialog.setContentView(contentView);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams p = window.getAttributes();
//        final float scale = mContext.getResources().getDisplayMetrics().density;
//        int margin=(int) (60 * scale + 0.5f);
//        p.width = mContext.getResources().getDisplayMetrics().widthPixels-margin;
//        p.height = WindowManager.LayoutParams.WRAP_CONTENT;
        p.gravity = Gravity.CENTER;
        final Drawable d = mContext.getApplicationContext().getResources().getDrawable(R.drawable.loading_bg);
        window.setBackgroundDrawable(d);
        window.setAttributes(p);
        TextView tvCancle = (TextView) dialog.findViewById(R.id.tv_cancle);
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        TextView tvConfirm = (TextView) dialog.findViewById(R.id.tv_confirm);
        final TextView tv_result = dialog.findViewById(R.id.tv_result);
        TextView tvGo = dialog.findViewById(R.id.tv_today);
        final MonthCalendar calendarView = dialog.findViewById(R.id.calendar_view);
        calendarView.setDateInterval("1970-01-01", "2099-12-31");
        //默认选中今天
        calendarView.setCheckMode(CheckModel.SINGLE_DEFAULT_CHECKED);
        if (initListener != null) {
            initListener.onInit(calendarView);
        }
        tvGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.toToday();
            }
        });
        calendarView.setOnCalendarChangedListener(new OnCalendarChangedListener() {
            @Override
            public void onCalendarChange(BaseCalendar baseCalendar, int year, int month, LocalDate localDate, DateChangeBehavior dateChangeBehavior) {
                if (localDate != null) {
                    int dayOfMonth = localDate.getDayOfMonth();
                    int dayOfWeek = localDate.getDayOfWeek();
                    tv_result.setText(format(year, month, dayOfMonth, dayOfWeek));
                    tvConfirm.setTag(String.format("%s-%s-%s", year, month < 10 ? "0" + month : month, dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth));
                }
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calendarView.getCurrPagerCheckDateList() != null) {
                    listener.onDateSelect(calendarView.getCurrPagerCheckDateList().get(0).toDate(), String.valueOf(tvConfirm.getTag()));
                    dialog.cancel();
                }
            }
        });
        tv_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<Integer> list = new ArrayList<>();
                for (int i = 1970; i < 2099; i++) {
                    list.add(i);
                }
                final NDialog yearDialog = new NDialog(mContext, R.style.dialogStyle) {
                    @Override
                    protected void onTouchOutside(Dialog dialog) {
                        dialog.dismiss();
                    }
                };
                yearDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                View contentView = LayoutInflater.from(v.getContext()).inflate(R.layout.layout_recyclerview_year, null);
                yearDialog.setContentView(contentView);
                Window window = yearDialog.getWindow();
                WindowManager.LayoutParams p = window.getAttributes();
               /* final float scale = mContext.getResources().getDisplayMetrics().density;
                int width=(int) (200 * scale + 0.5f);
                p.width = width;
                p.height = (int) (250 * scale + 0.5f);*/
                p.gravity = Gravity.CENTER;
                window.setBackgroundDrawable(d);
                window.setAttributes(p);
                RecyclerView listView = contentView.findViewById(R.id.root);
                listView.setLayoutManager(new LinearLayoutManager(v.getContext()));
                int year = Calendar.getInstance().get(Calendar.YEAR);
                int position = year - 1970 - 2;
                listView.scrollToPosition(position);
                YearAdapter yearAdapter = new YearAdapter(v.getContext(), list);
                listView.setAdapter(yearAdapter);
                yearAdapter.setOnItemClickLisitenter(new YearAdapter.onItemClickLisitenter() {
                    @Override
                    public void onItemClick(View v, int position) {
                        yearDialog.dismiss();
                        if (calendarView.getCurrPagerCheckDateList() != null) {
                            LocalDate localDate = calendarView.getCurrPagerCheckDateList().get(0);
                            calendarView.jumpDate(list.get(position), localDate.getMonthOfYear(), 1);
                        }
                    }
                });
                yearDialog.setCanceledOnTouchOutside(true);
                yearDialog.setCancelable(true);
                yearDialog.show();
            }
        });
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    public static String week(int i) {
        String week = "星期";
        if (i == 1) {
            week += "一";
        }
        if (i == 2) {
            week += "二";
        }
        if (i == 3) {
            week += "三";
        }
        if (i == 4) {
            week += "四";
        }
        if (i == 5) {
            week += "五";
        }
        if (i == 6) {
            week += "六";
        }
        if (i == 7) {
            week += "日";
        }
        return week;
    }

    public static String format(int year, int month, int dayOfMonth, int dayOfWeek) {
        String result = String.format("%s年%s月%s日 %s", year, month, dayOfMonth, NCalendar.week(dayOfWeek));
        return result;
    }
}
