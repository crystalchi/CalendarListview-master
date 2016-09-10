/***********************************************************************************
 * The MIT License (MIT)

 * Copyright (c) 2014 Robin Chutaux

 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 ***********************************************************************************/
package com.andexert.calendarlistview.library;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.view.MotionEvent;
import android.view.View;

import java.security.InvalidParameterException;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

class SimpleMonthView extends View
{

    public static final String VIEW_PARAMS_HEIGHT = "height";
    public static final String VIEW_PARAMS_MONTH = "month";
    public static final String VIEW_PARAMS_YEAR = "year";
    public static final String VIEW_PARAMS_SELECTED_BEGIN_DAY = "selected_begin_day";
    public static final String VIEW_PARAMS_SELECTED_LAST_DAY = "selected_last_day";
    public static final String VIEW_PARAMS_SELECTED_BEGIN_MONTH = "selected_begin_month";
    public static final String VIEW_PARAMS_SELECTED_LAST_MONTH = "selected_last_month";
    public static final String VIEW_PARAMS_SELECTED_BEGIN_YEAR = "selected_begin_year";
    public static final String VIEW_PARAMS_SELECTED_LAST_YEAR = "selected_last_year";
    public static final String VIEW_PARAMS_WEEK_START = "week_start";

    private static final int SELECTED_CIRCLE_ALPHA = 128;
    protected static int DEFAULT_HEIGHT = 32;
    protected static final int DEFAULT_NUM_ROWS = 6;
	protected static int DAY_SELECTED_CIRCLE_SIZE;
	protected static int DAY_SEPARATOR_WIDTH = 1;
	protected static int MINI_DAY_NUMBER_TEXT_SIZE;
	protected static int MIN_HEIGHT = 10;
	protected static int MONTH_DAY_LABEL_TEXT_SIZE;
	protected static int MONTH_HEADER_SIZE;
	protected static int MONTH_LABEL_TEXT_SIZE;
    protected static int MONTH_LABEL_MINI_TEXT_SIZE;

    protected static int MONTH_TEXT_SIZE_FOR_LABEL;

    protected int mPadding = 0;

    private String mDayOfWeekTypeface;
    private String mMonthTitleTypeface;

    protected Paint mMonthDayLabelPaint;
    protected Paint mMonthNumPaint;
    protected Paint mMonthTitleBGPaint;
    protected Paint mMonthTitlePaint;
    protected Paint mSelectedCirclePaint;
    protected Paint mMonthTextPaint;
    protected Paint mWhitePaint;
    protected Paint mLinePaint;

    protected int mCurrentDayTextColor;
    protected int mMonthTextColor;
    protected int mDayTextColor;
    protected int mDayNumColor;
    protected int mMonthTitleBGColor;
    protected int mPreviousDayColor;
    protected int mSelectedDaysColor;
    protected int mColor114;
    protected int mColorAbaaaa;
    protected int mColorWhite;

    protected int mColorCccccc;

    private final StringBuilder mStringBuilder;

    protected boolean mHasToday = false;
    protected boolean mIsPrev = false;
    protected int mSelectedBeginDay = -1;
    protected int mSelectedLastDay = -1;
    protected int mSelectedBeginMonth = -1;
    protected int mSelectedLastMonth = -1;
    protected int mSelectedBeginYear = -1;
    protected int mSelectedLastYear = -1;
    protected int mToday = -1;
    protected int mWeekStart = 1;
    protected int mNumDays = 7;
    protected int mNumCells = mNumDays;
    private int mDayOfWeekStart = 0;
    protected int mMonth;
    protected Boolean mDrawRect;
    protected int mRowHeight = DEFAULT_HEIGHT;
    protected int mWidth;
    protected int mYear;
    final Time today;

	private final Calendar mCalendar;
	private final Calendar mDayLabelCalendar;
    private final Boolean isPrevDayEnabled;

    private int mNumRows = DEFAULT_NUM_ROWS;

	private DateFormatSymbols mDateFormatSymbols = new DateFormatSymbols();

    private OnDayClickListener mOnDayClickListener;

    private Paint mPaint;
    private Paint mGrayPaint;
    private Context mContext;
    private Map<String, CalendarDutyUnitOutputs> map = new HashMap<String, CalendarDutyUnitOutputs>();

    public SimpleMonthView(Context context, TypedArray typedArray)
    {
        super(context);
        mContext = context;
        Resources resources = context.getResources();
        mDayLabelCalendar = Calendar.getInstance();
        mCalendar = Calendar.getInstance();
        today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        mDayOfWeekTypeface = resources.getString(R.string.sans_serif);
        mMonthTitleTypeface = resources.getString(R.string.sans_serif);
        mCurrentDayTextColor = typedArray.getColor(R.styleable.DayPickerView_colorCurrentDay, resources.getColor(R.color.normal_day));
        mMonthTextColor = typedArray.getColor(R.styleable.DayPickerView_colorMonthName, resources.getColor(R.color.normal_day));
        mDayTextColor = typedArray.getColor(R.styleable.DayPickerView_colorDayName, resources.getColor(R.color.color_575757));
        mDayNumColor = typedArray.getColor(R.styleable.DayPickerView_colorNormalDay, resources.getColor(R.color.normal_day));
        mPreviousDayColor = typedArray.getColor(R.styleable.DayPickerView_colorPreviousDay, resources.getColor(R.color.normal_day));
        mSelectedDaysColor = typedArray.getColor(R.styleable.DayPickerView_colorSelectedDayBackground, resources.getColor(R.color.color_f56c00));
        mColor114 = typedArray.getColor(R.styleable.DayPickerView_color114, resources.getColor(R.color.color_38a8ef));
        mMonthTitleBGColor = typedArray.getColor(R.styleable.DayPickerView_colorSelectedDayText, resources.getColor(R.color.selected_day_text));
        mColorAbaaaa = typedArray.getColor(R.styleable.DayPickerView_colorAbaaaa, resources.getColor(R.color.color_abaaaa));
        mColorWhite = typedArray.getColor(R.styleable.DayPickerView_colorWhite, resources.getColor(R.color.color_white));
        mColorCccccc = typedArray.getColor(R.styleable.DayPickerView_colorCCCCCC, resources.getColor(R.color.color_cccccc));
        mDrawRect = typedArray.getBoolean(R.styleable.DayPickerView_drawRoundRect, false);


        mStringBuilder = new StringBuilder(50);

        MINI_DAY_NUMBER_TEXT_SIZE = typedArray.getDimensionPixelSize(R.styleable.DayPickerView_textSizeDay, resources.getDimensionPixelSize(R.dimen.text_size_day));
        MONTH_LABEL_TEXT_SIZE = typedArray.getDimensionPixelSize(R.styleable.DayPickerView_textSizeMonth, resources.getDimensionPixelSize(R.dimen.text_size_month));
        MONTH_LABEL_MINI_TEXT_SIZE = typedArray.getDimensionPixelSize(R.styleable.DayPickerView_textMiniSizeMonth, resources.getDimensionPixelOffset(R.dimen.text_mini_size_for_day));
        MONTH_DAY_LABEL_TEXT_SIZE = typedArray.getDimensionPixelSize(R.styleable.DayPickerView_textSizeDayName, resources.getDimensionPixelSize(R.dimen.text_size_day_name));
        MONTH_HEADER_SIZE = typedArray.getDimensionPixelOffset(R.styleable.DayPickerView_headerMonthHeight, resources.getDimensionPixelOffset(R.dimen.header_month_height));
        DAY_SELECTED_CIRCLE_SIZE = typedArray.getDimensionPixelSize(R.styleable.DayPickerView_selectedDayRadius, resources.getDimensionPixelOffset(R.dimen.selected_day_radius));

        MONTH_TEXT_SIZE_FOR_LABEL = typedArray.getDimensionPixelSize(R.styleable.DayPickerView_textSizeLabel, resources.getDimensionPixelSize(R.dimen.text_size_for_label));

        mRowHeight = ((typedArray.getDimensionPixelSize(R.styleable.DayPickerView_calendarHeight, resources.getDimensionPixelOffset(R.dimen.calendar_height)) - MONTH_HEADER_SIZE) / 6);

        isPrevDayEnabled = typedArray.getBoolean(R.styleable.DayPickerView_enablePreviousDay, true);

        initView();

    }

    private int calculateNumRows() {
        int offset = findDayOffset();
        int dividend = (offset + mNumCells) / mNumDays;
        int remainder = (offset + mNumCells) % mNumDays;
        return (dividend + (remainder > 0 ? 1 : 0));
	}

	private void drawMonthDayLabels(Canvas canvas) {
        /*int y = MONTH_HEADER_SIZE - (MONTH_DAY_LABEL_TEXT_SIZE / 2);*/
        int y = (MONTH_HEADER_SIZE + MINI_DAY_NUMBER_TEXT_SIZE) / 2 + (MONTH_HEADER_SIZE + MINI_DAY_NUMBER_TEXT_SIZE) / 2 / 2;
        int dayWidthHalf = (getMeasuredWidth() - mPadding * 2) / (mNumDays * 2);

        canvas.drawLine(0, y - MONTH_LABEL_TEXT_SIZE, mWidth, y - MONTH_LABEL_TEXT_SIZE, mLinePaint);
        for (int i = 0; i < mNumDays; i++) {
            int calendarDay = (i + mWeekStart) % mNumDays;
            int x = (2 * i + 1) * dayWidthHalf + mPadding;
            mDayLabelCalendar.set(Calendar.DAY_OF_WEEK, calendarDay);
            canvas.drawText(mDateFormatSymbols.getShortWeekdays()[mDayLabelCalendar.get(Calendar.DAY_OF_WEEK)].toUpperCase(Locale.getDefault()), x, y, mMonthDayLabelPaint);
        }
        canvas.drawLine(0, y + MONTH_LABEL_TEXT_SIZE / 2, mWidth, y + MONTH_LABEL_TEXT_SIZE / 2, mLinePaint);
	}

	private void drawMonthTitle(Canvas canvas) {
        int x = (mWidth + 2 * mPadding) / 2;
        /*int y = (MONTH_HEADER_SIZE - MONTH_DAY_LABEL_TEXT_SIZE) / 2 + (MONTH_LABEL_TEXT_SIZE / 3);*/
        int y = (MONTH_HEADER_SIZE + MINI_DAY_NUMBER_TEXT_SIZE) / 2;
        StringBuilder stringBuilder = new StringBuilder(getMonthAndYearString().toLowerCase());
        stringBuilder.setCharAt(0, Character.toUpperCase(stringBuilder.charAt(0)));
        canvas.drawText(stringBuilder.toString(), x, y, mMonthTextPaint);
	}

	private int findDayOffset() {
        return (mDayOfWeekStart < mWeekStart ? (mDayOfWeekStart + mNumDays) : mDayOfWeekStart)
                - mWeekStart;
	}

	private String getMonthAndYearString() {
        int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_NO_MONTH_DAY;
        mStringBuilder.setLength(0);
        long millis = mCalendar.getTimeInMillis();
        return DateUtils.formatDateRange(getContext(), millis, millis, flags);
    }

	private void onDayClick(SimpleMonthAdapter.CalendarDay calendarDay) {
		if (mOnDayClickListener != null && (isPrevDayEnabled || !((calendarDay.month == today.month) && (calendarDay.year == today.year) && calendarDay.day < today.monthDay))) {
			mOnDayClickListener.onDayClick(this, calendarDay);
        }
	}

	private boolean sameDay(int monthDay, Time time) {
		return (mYear == time.year) && (mMonth == time.month) && (monthDay == time.monthDay);
	}

    private boolean prevDay(int monthDay, Time time) {
        return ((mYear < time.year)) || (mYear == time.year && mMonth < time.month) || ( mMonth == time.month && monthDay < time.monthDay);
    }

	protected void drawMonthNums(Canvas canvas) {
		/*int y = (mRowHeight + MINI_DAY_NUMBER_TEXT_SIZE) / 2 - DAY_SEPARATOR_WIDTH + MONTH_HEADER_SIZE;*/
        int y = mRowHeight / 2 + MONTH_HEADER_SIZE;

		int paddingDay = (mWidth - 2 * mPadding) / (2 * mNumDays);
		int dayOffset = findDayOffset();
		int day = 1;


		while (day <= mNumCells) {
			int x = paddingDay * (1 + dayOffset * 2) + mPadding;
            StringBuffer sb = new StringBuffer(128);
            sb.append(mYear).append("-").append("0").append(mMonth + 1).append("-").append(day < 10 ? "0" + day : day);
            if(map.containsKey(sb.toString())){
                CalendarDutyUnitOutputs cdo = map.get(sb.toString());
                if(cdo.getDutyStatus() == 2){
                    canvas.drawText(String.format("%d", day), x, y, mMonthNumPaint);
                    canvas.drawText("约满", x, y + MONTH_DAY_LABEL_TEXT_SIZE, mMonthDayLabelPaint);
                }else{
                    if(cdo.getWo114Map().get("wo") != null && cdo.getWo114Map().get("114") != null){
                        canvas.drawCircle(x, y - MINI_DAY_NUMBER_TEXT_SIZE / 3, DAY_SELECTED_CIRCLE_SIZE, mSelectedCirclePaint);
                        canvas.drawText(String.format("%d", day), x, y, mWhitePaint);
                    }
                    if(cdo.getWo114Map().get("wo") != null && cdo.getWo114Map().get("114") == 0){
                        canvas.drawCircle(x, y - MINI_DAY_NUMBER_TEXT_SIZE / 3, DAY_SELECTED_CIRCLE_SIZE, mSelectedCirclePaint);
                        canvas.drawText(String.format("%d", day), x, y, mWhitePaint);
                    }
                    if(cdo.getWo114Map().get("114") != null && cdo.getWo114Map().get("wo") == 0){
                        canvas.drawCircle(x, y - MINI_DAY_NUMBER_TEXT_SIZE / 3, DAY_SELECTED_CIRCLE_SIZE, mPaint);
                        canvas.drawText(String.format("%d", day), x, y, mWhitePaint);
                    }
                }
            }else{
                if (mHasToday && (mToday == day)) {
                    canvas.drawText("今天", x, y, mGrayPaint);
                }else{
                    canvas.drawText(String.format("%d", day), x, y, mMonthNumPaint);
                }
            }


			dayOffset++;
			if (dayOffset == mNumDays) {
				dayOffset = 0;
				y += mRowHeight;
			}
			day++;
		}
	}

	public SimpleMonthAdapter.CalendarDay getDayFromLocation(float x, float y) {
		int padding = mPadding;
		if ((x < padding) || (x > mWidth - mPadding)) {
			return null;
		}

		int yDay = (int) (y - MONTH_HEADER_SIZE) / mRowHeight;
		int day = 1 + ((int) ((x - padding) * mNumDays / (mWidth - padding - mPadding)) - findDayOffset()) + yDay * mNumDays;

        if (mMonth > 11 || mMonth < 0 || CalendarUtils.getDaysInMonth(mMonth, mYear) < day || day < 1)
            return null;

		return new SimpleMonthAdapter.CalendarDay(mYear, mMonth, day);
	}

	protected void initView() {
        mMonthTitlePaint = new Paint();
        mMonthTitlePaint.setFakeBoldText(false);
        mMonthTitlePaint.setAntiAlias(true);
        mMonthTitlePaint.setTextSize(MINI_DAY_NUMBER_TEXT_SIZE);
        mMonthTitlePaint.setColor(mDayTextColor);
        mMonthTitlePaint.setTextAlign(Align.CENTER);
        mMonthTitlePaint.setStyle(Style.FILL);


        mPaint = new Paint();
        mPaint.setFakeBoldText(true);
        mPaint.setAntiAlias(true);
        mPaint.setColor(mColor114);
        mPaint.setTextAlign(Align.CENTER);
        mPaint.setStyle(Style.FILL);
        //mPaint.setAlpha(SELECTED_CIRCLE_ALPHA);


        mMonthTitleBGPaint = new Paint();
        mMonthTitleBGPaint.setFakeBoldText(true);
        mMonthTitleBGPaint.setAntiAlias(true);
        mMonthTitleBGPaint.setColor(mMonthTitleBGColor);
        mMonthTitleBGPaint.setTextAlign(Align.CENTER);
        mMonthTitleBGPaint.setStyle(Style.FILL);

        mSelectedCirclePaint = new Paint();
        mSelectedCirclePaint.setFakeBoldText(true);
        mSelectedCirclePaint.setAntiAlias(true);
        mSelectedCirclePaint.setColor(mSelectedDaysColor);
        mSelectedCirclePaint.setTextAlign(Align.CENTER);
        mSelectedCirclePaint.setStyle(Style.FILL);
        //mSelectedCirclePaint.setAlpha(SELECTED_CIRCLE_ALPHA);

        mMonthTextPaint = new Paint();
        mMonthTextPaint.setTextSize(MINI_DAY_NUMBER_TEXT_SIZE);
        mMonthTextPaint.setFakeBoldText(true);
        mMonthTextPaint.setAntiAlias(true);
        mMonthTextPaint.setColor(mSelectedDaysColor);
        mMonthTextPaint.setTextAlign(Align.CENTER);
        mMonthTextPaint.setStyle(Style.FILL);

        mMonthDayLabelPaint = new Paint();
        mMonthDayLabelPaint.setAntiAlias(true);
        mMonthDayLabelPaint.setTextSize(/*MONTH_DAY_LABEL_TEXT_SIZE*/MONTH_TEXT_SIZE_FOR_LABEL);
        mMonthDayLabelPaint.setColor(mDayTextColor);
        mMonthDayLabelPaint.setStyle(Style.FILL);
        mMonthDayLabelPaint.setTextAlign(Align.CENTER);
        mMonthDayLabelPaint.setFakeBoldText(true);

        mMonthNumPaint = new Paint();
        mMonthNumPaint.setAntiAlias(true);
        mMonthNumPaint.setTextSize(MINI_DAY_NUMBER_TEXT_SIZE);
        mMonthNumPaint.setStyle(Style.FILL);
        mMonthNumPaint.setTextAlign(Align.CENTER);
        mMonthNumPaint.setFakeBoldText(true);
        mMonthNumPaint.setColor(mDayTextColor);

        mGrayPaint = new Paint();
        mGrayPaint.setAntiAlias(true);
        mGrayPaint.setTextSize(MINI_DAY_NUMBER_TEXT_SIZE);
        mGrayPaint.setStyle(Style.FILL);
        mGrayPaint.setTextAlign(Align.CENTER);
        mGrayPaint.setFakeBoldText(false);
        mGrayPaint.setColor(mColorAbaaaa);

        mWhitePaint = new Paint();
        mWhitePaint.setAntiAlias(true);
        mWhitePaint.setTextSize(MINI_DAY_NUMBER_TEXT_SIZE);
        mWhitePaint.setStyle(Style.FILL);
        mWhitePaint.setTextAlign(Align.CENTER);
        mWhitePaint.setFakeBoldText(false);
        mWhitePaint.setColor(mColorWhite);


        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setTextSize(MINI_DAY_NUMBER_TEXT_SIZE);
        mLinePaint.setStyle(Style.FILL);
        mLinePaint.setTextAlign(Align.CENTER);
        mLinePaint.setFakeBoldText(false);
        mLinePaint.setColor(mColorCccccc);
	}

	protected void onDraw(Canvas canvas) {
		drawMonthTitle(canvas);
		drawMonthDayLabels(canvas);
		drawMonthNums(canvas);
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), mRowHeight * mNumRows + MONTH_HEADER_SIZE);
	}

	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mWidth = w;
	}

	public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            SimpleMonthAdapter.CalendarDay calendarDay = getDayFromLocation(event.getX(), event.getY());
            if (calendarDay != null) {
                onDayClick(calendarDay);
            }
        }
        return true;
	}

	public void reuse() {
        mNumRows = DEFAULT_NUM_ROWS;
		requestLayout();
	}

	public void setMonthParams(HashMap<String, Integer> params, Map<String, CalendarDutyUnitOutputs> paramMap) {
        this.map = paramMap;
        if (!params.containsKey(VIEW_PARAMS_MONTH) && !params.containsKey(VIEW_PARAMS_YEAR)) {
            throw new InvalidParameterException("You must specify month and year for this view");
        }
		setTag(params);

        if (params.containsKey(VIEW_PARAMS_HEIGHT)) {
            mRowHeight = params.get(VIEW_PARAMS_HEIGHT);
            if (mRowHeight < MIN_HEIGHT) {
                mRowHeight = MIN_HEIGHT;
            }
        }
        if (params.containsKey(VIEW_PARAMS_SELECTED_BEGIN_DAY)) {
            mSelectedBeginDay = params.get(VIEW_PARAMS_SELECTED_BEGIN_DAY);
        }
        if (params.containsKey(VIEW_PARAMS_SELECTED_LAST_DAY)) {
            mSelectedLastDay = params.get(VIEW_PARAMS_SELECTED_LAST_DAY);
        }
        if (params.containsKey(VIEW_PARAMS_SELECTED_BEGIN_MONTH)) {
            mSelectedBeginMonth = params.get(VIEW_PARAMS_SELECTED_BEGIN_MONTH);
        }
        if (params.containsKey(VIEW_PARAMS_SELECTED_LAST_MONTH)) {
            mSelectedLastMonth = params.get(VIEW_PARAMS_SELECTED_LAST_MONTH);
        }
        if (params.containsKey(VIEW_PARAMS_SELECTED_BEGIN_YEAR)) {
            mSelectedBeginYear = params.get(VIEW_PARAMS_SELECTED_BEGIN_YEAR);
        }
        if (params.containsKey(VIEW_PARAMS_SELECTED_LAST_YEAR)) {
            mSelectedLastYear = params.get(VIEW_PARAMS_SELECTED_LAST_YEAR);
        }

        mMonth = params.get(VIEW_PARAMS_MONTH);
        mYear = params.get(VIEW_PARAMS_YEAR);

        mHasToday = false;
        mToday = -1;

		mCalendar.set(Calendar.MONTH, mMonth);
		mCalendar.set(Calendar.YEAR, mYear);
		mCalendar.set(Calendar.DAY_OF_MONTH, 1);
		mDayOfWeekStart = mCalendar.get(Calendar.DAY_OF_WEEK);

        if (params.containsKey(VIEW_PARAMS_WEEK_START)) {
            mWeekStart = params.get(VIEW_PARAMS_WEEK_START);
        } else {
            mWeekStart = mCalendar.getFirstDayOfWeek();
        }

        mNumCells = /*CalendarUtils.getDaysInMonth(mMonth, mYear)*/mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); //月天数;
        for (int i = 0; i < mNumCells; i++) {
            final int day = i + 1;
            if (sameDay(day, today)) {
                mHasToday = true;
                mToday = day;
            }

            mIsPrev = prevDay(day, today);
        }

        mNumRows = calculateNumRows();
	}

	public void setOnDayClickListener(OnDayClickListener onDayClickListener) {
		mOnDayClickListener = onDayClickListener;
	}

	public static abstract interface OnDayClickListener {
		public abstract void onDayClick(SimpleMonthView simpleMonthView, SimpleMonthAdapter.CalendarDay calendarDay);
	}


    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     *            （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     *            （DisplayMetrics类中属性density）
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}