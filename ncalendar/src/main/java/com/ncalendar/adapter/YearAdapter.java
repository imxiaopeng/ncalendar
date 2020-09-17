package com.ncalendar.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

/**
 * @author Created by xiaoludan on 2020-09-03.
 */
public class YearAdapter extends RecyclerView.Adapter<YearAdapter.MyViewHolder> implements View.OnClickListener {

    Context context;
    List<Integer> listBeans;
    private onItemClickLisitenter onItem;

    public YearAdapter(Context context, List<Integer> list) {
        this.context = context;
        this.listBeans = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView textView = new TextView(context);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        textView.setTextColor(ContextCompat.getColor(context, android.R.color.black));
        textView.getPaint().setFakeBoldText(true);
        textView.setGravity(Gravity.CENTER);
        final float scale = context.getResources().getDisplayMetrics().density;
        int height = (int) (50 * scale + 0.5f);
        RecyclerView.LayoutParams p = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        textView.setLayoutParams(p);
        YearAdapter.MyViewHolder holder = new YearAdapter.MyViewHolder(textView);
        textView.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        if (listBeans.get(position) == year) {
            holder.textView.setTextColor(Color.parseColor("#009688"));
        } else {
            holder.textView.setTextColor(ContextCompat.getColor(context, android.R.color.black));
        }
        holder.textView.setText(String.valueOf(listBeans.get(position)));
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return listBeans.size();
    }

    @Override
    public void onClick(View v) {
        if (onItem != null) {
            onItem.onItemClick(v, (Integer) v.getTag());
        }
    }

    static
    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        MyViewHolder(View view) {
            super(view);
            textView = (TextView) view;

        }
    }

    public interface onItemClickLisitenter {
        void onItemClick(View v, int position);
    }

    public void setOnItemClickLisitenter(onItemClickLisitenter onItem) {
        this.onItem = onItem;
    }


}
