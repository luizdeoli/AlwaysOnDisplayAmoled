package com.tomer.alwayson.views;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tomer.alwayson.ContextConstatns;
import com.tomer.alwayson.R;
import com.tomer.alwayson.helpers.Utils;

public class DateView extends LinearLayout implements ContextConstatns {
    private TextView calendarTV;
    private CalendarView calendarView;
    private int dateStyle;

    public DateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        addView(inflater.inflate(R.layout.date, null));
    }

    public void setDateStyle(int dateStyle, float textSize, int textColor) {
        LinearLayout dateWrapper = (LinearLayout) getChildAt(0);
        this.dateStyle = dateStyle;
        if (calendarTV == null)
            calendarTV = (TextView) dateWrapper.findViewById(R.id.date_tv);
        if (calendarView == null)
            calendarView = (CalendarView) dateWrapper.findViewById(R.id.date_calendar);
        switch (dateStyle) {
            case DISABLED:
                removeView(dateWrapper);
                break;
            case DATE_TEXT:
                calendarTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, (textSize / 5));
                calendarTV.setTextColor(textColor);
                dateWrapper.removeView(calendarView);
                break;
            case DATE_VIEW:
                calendarView.setOnDateChangeListener((calendarView1, i, i1, i2) -> {

                });
                dateWrapper.removeView(calendarTV);
                break;
        }
        Utils.logDebug("Calendar style is ", String.valueOf(dateStyle));
    }

    public boolean isFull() {
        return dateStyle == DATE_VIEW;
    }

    public CalendarView getCalendarView() {
        return calendarView;
    }

    public void update(String monthAndDayText) {
        if (dateStyle == 1)
            if (calendarTV != null)
                calendarTV.setText(monthAndDayText);
    }
}
