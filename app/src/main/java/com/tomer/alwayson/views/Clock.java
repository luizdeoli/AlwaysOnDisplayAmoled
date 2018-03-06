package com.tomer.alwayson.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tomer.alwayson.ContextConstatns;
import com.tomer.alwayson.R;
import com.tomer.alwayson.helpers.Utils;
import com.tomerrosenfeld.customanalogclockview.CustomAnalogClock;

public class Clock extends LinearLayout implements ContextConstatns {

    private CustomAnalogClock analogClock;
    private KillableTextClock textClock;

    public Clock(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        addView(inflater.inflate(R.layout.clock, null));
    }

    public Clock(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        addView(inflater.inflate(R.layout.clock, null));
    }

    public void setStyle(Context context, int clockStyle, float textSize, int textColor, boolean showAmPm) {
        LinearLayout clockWrapper = (LinearLayout) findViewById(R.id.clock_wrapper);
        analogClock = (CustomAnalogClock) clockWrapper.findViewById(R.id.custom_analog_clock);
        ViewGroup.LayoutParams lp = clockWrapper.findViewById(R.id.custom_analog_clock).getLayoutParams();
        float clockSize = textSize < 80 ? textSize : 80;
        lp.height = (int) (clockSize * 10);
        lp.width = (int) (clockSize * 9.5);
        switch (clockStyle) {
            case DISABLED:
                removeView(clockWrapper);
                break;
            case DIGITAL_CLOCK:
                textClock = (KillableTextClock) clockWrapper.findViewById(R.id.digital_clock);
                textClock.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                textClock.setTextColor(textColor);
                if (!showAmPm)
                    textClock.setFormat12Hour("h:mm");
                if (Utils.isAndroidNewerThanN()) {
                    textClock.setTextLocale(context.getResources().getConfiguration().getLocales().get(0));
                } else {
                    textClock.setTextLocale(context.getResources().getConfiguration().locale);
                }

                Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_thin.ttf");
                textClock.setTypeface(font);

                clockWrapper.removeView(clockWrapper.findViewById(R.id.custom_analog_clock));
                break;
            case ANALOG_CLOCK:
                clockWrapper.removeView(clockWrapper.findViewById(R.id.digital_clock));
                clockWrapper.findViewById(R.id.custom_analog_clock).setLayoutParams(lp);
                analogClock.init(context, R.drawable.default_face, R.drawable.default_hour_hand, R.drawable.default_minute_hand, 225, false, false);
                break;
        }
    }

    public CustomAnalogClock getAnalogClock() {
        return analogClock;
    }

    public KillableTextClock getTextClock() {
        return textClock;
    }
}
