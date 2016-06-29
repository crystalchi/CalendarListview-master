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
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

import java.util.Map;

public class DayPickerView extends RecyclerView
{
    private static final String TAG = DayPickerView.class.getSimpleName();

    protected Context mContext;
	protected SimpleMonthAdapter mAdapter;
	private DatePickerController mController;
    protected int mCurrentScrollState = 0;
	protected long mPreviousScrollPosition;
	protected int mPreviousScrollState = 0;
    private TypedArray typedArray;
    private OnScrollListener onScrollListener;
    private ShowFloatViewListener mShowFloatViewListener;
    private HideOrShowListener mHideOrShowListener;
    private int hideHeight;

    public DayPickerView(Context context)
    {
        this(context, null);
    }

    public DayPickerView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public DayPickerView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        if (!isInEditMode())
        {
            typedArray = context.obtainStyledAttributes(attrs, R.styleable.DayPickerView);
            setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            init(context);
        }
    }

    public void setController(DatePickerController mController, Map<String, CalendarDutyUnitOutputs> map, int hideHeight)
    {
        this.mController = mController;
        this.hideHeight = hideHeight;
        setUpAdapter(map);
        setAdapter(mAdapter);
    }


	public void init(Context paramContext) {
        setLayoutManager(new LinearLayoutManager(paramContext));
		mContext = paramContext;
		setUpListView();

       /* onScrollListener = new OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
                final SimpleMonthView child = (SimpleMonthView) recyclerView.getChildAt(0);
                if (child == null) {
                    return;
                }

                mPreviousScrollPosition = dy;
                mPreviousScrollState = mCurrentScrollState;
            }
        };*/
	}


	protected void setUpAdapter(Map<String, CalendarDutyUnitOutputs> map) {
		if (mAdapter == null) {
			mAdapter = new SimpleMonthAdapter(getContext(), mController, typedArray, map);
        }
		mAdapter.notifyDataSetChanged();
	}

	protected void setUpListView() {
		setVerticalScrollBarEnabled(false);
		setOnScrollListener(new ShowFloatViewListener());
		setFadingEdgeLength(0);
	}

    public SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> getSelectedDays()
    {
        return mAdapter.getSelectedDays();
    }

    protected DatePickerController getController()
    {
        return mController;
    }

    protected TypedArray getTypedArray()
    {
        return typedArray;
    }

    public class ShowFloatViewListener extends OnScrollListener{

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            final SimpleMonthView child = (SimpleMonthView) recyclerView.getChildAt(0);
            if (child == null) {
                return;
            }

            mPreviousScrollPosition = dy;
            mPreviousScrollState = mCurrentScrollState;


            //下滑显示浮动层，上滑隐藏浮动层
            Log.d(TAG, "dy is " + dy);
            mHideOrShowListener.onHide();
            mHideOrShowListener.onShow();
        }
    }


    public void setmHideOrShowListener(HideOrShowListener mHideOrShowListener) {
        this.mHideOrShowListener = mHideOrShowListener;
    }

    public interface HideOrShowListener{
        public void onHide();
        public void onShow();
    }
}