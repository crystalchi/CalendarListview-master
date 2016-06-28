package com.andexert.sample;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.andexert.calendarlistview.library.CalendarDutyUnitOutputs;
import com.andexert.calendarlistview.library.DatePickerController;
import com.andexert.calendarlistview.library.DayPickerView;
import com.andexert.calendarlistview.library.SimpleMonthAdapter;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity implements DatePickerController, DayPickerView.HideOrShowListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    private DayPickerView dayPickerView;

    private Map<String, CalendarDutyUnitOutputs> map = new HashMap<String, CalendarDutyUnitOutputs>();
    private List<CalendarDutyUnitOutputs> cdoList = new ArrayList<CalendarDutyUnitOutputs>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Map<String, Integer> map1 = new HashMap<String, Integer>();
        CalendarDutyUnitOutputs cdo = new CalendarDutyUnitOutputs();
        cdo.setDutyDate("2016-06-29");
        cdo.setDutyStatus(1);
        cdo.setRemainAvailableNumber114(18);
        cdo.setRemainAvailableNumberWeb(20);
        cdo.setWeek(1);
        map1.put("114", cdo.getRemainAvailableNumber114());
        map1.put("wo", cdo.getRemainAvailableNumberWeb());
        cdo.setWo114Map(map1);

        Map<String, Integer> map2 = new HashMap<String, Integer>();
        CalendarDutyUnitOutputs cdo1 = new CalendarDutyUnitOutputs();
        cdo1.setDutyDate("2016-07-10");
        cdo1.setDutyStatus(1);
        cdo1.setRemainAvailableNumber114(8);
        cdo1.setRemainAvailableNumberWeb(0);
        cdo1.setWeek(7);
        map2.put("114", cdo1.getRemainAvailableNumber114());
        map2.put("wo", cdo1.getRemainAvailableNumberWeb());
        cdo1.setWo114Map(map2);


        Map<String, Integer> map3 = new HashMap<String, Integer>();
        CalendarDutyUnitOutputs cdo2 = new CalendarDutyUnitOutputs();
        cdo2.setDutyDate("2016-06-30");
        cdo2.setDutyStatus(2);
        cdo2.setRemainAvailableNumber114(12);
        cdo2.setRemainAvailableNumberWeb(24);
        cdo2.setWeek(4);
        map3.put("114", cdo2.getRemainAvailableNumber114());
        map3.put("wo", cdo2.getRemainAvailableNumberWeb());
        cdo2.setWo114Map(map3);

        Map<String, Integer> map4 = new HashMap<String, Integer>();
        CalendarDutyUnitOutputs cdo3 = new CalendarDutyUnitOutputs();
        cdo3.setDutyDate("2016-07-30");
        cdo3.setDutyStatus(1);
        cdo3.setRemainAvailableNumber114(10);
        cdo3.setRemainAvailableNumberWeb(15);
        cdo3.setWeek(6);
        map4.put("114", cdo3.getRemainAvailableNumber114());
        map4.put("wo", cdo3.getRemainAvailableNumberWeb());
        cdo3.setWo114Map(map4);

        Map<String, Integer> map5 = new HashMap<String, Integer>();
        CalendarDutyUnitOutputs cdo4 = new CalendarDutyUnitOutputs();
        cdo4.setDutyDate("2016-08-01");
        cdo4.setDutyStatus(1);
        cdo4.setRemainAvailableNumber114(0);
        cdo4.setRemainAvailableNumberWeb(15);
        cdo4.setWeek(6);
        map5.put("114", cdo4.getRemainAvailableNumber114());
        map5.put("wo", cdo4.getRemainAvailableNumberWeb());
        cdo4.setWo114Map(map5);

        Map<String, Integer> map6 = new HashMap<String, Integer>();
        CalendarDutyUnitOutputs cdo5 = new CalendarDutyUnitOutputs();
        cdo5.setDutyDate("2016-08-02");
        cdo5.setDutyStatus(1);
        cdo5.setRemainAvailableNumber114(16);
        cdo5.setRemainAvailableNumberWeb(0);
        cdo5.setWeek(6);
        map6.put("114", cdo5.getRemainAvailableNumber114());
        map6.put("wo", cdo5.getRemainAvailableNumberWeb());
        cdo5.setWo114Map(map6);


        cdoList.add(cdo);
        cdoList.add(cdo1);
        cdoList.add(cdo2);
        cdoList.add(cdo3);
        cdoList.add(cdo4);
        cdoList.add(cdo5);

        for (CalendarDutyUnitOutputs c : cdoList) {
            map.put(c.getDutyDate(), c);
        }


        dayPickerView = (DayPickerView) findViewById(R.id.pickerView);
        dayPickerView.setController(this, map, 2);
        dayPickerView.setmHideOrShowListener(this);
    }

    @Override
    public int getMaxYear() {
        return CalendarUtil.getNextYear();
    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day) {
        StringBuffer sb = new StringBuffer(128);
        sb.append(year).append("-").append(month < 10 ? ("0" + (month + 1)) : (month + 1)).append("-").append(day < 10 ? ("0" + day) : day);
        Log.d(TAG, "seletedDay is " + sb.toString());
    }

    @Override
    public void onDateRangeSelected(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays) {

        Log.d(TAG, selectedDays.getFirst().toString() + " --> " + selectedDays.getLast().toString());
    }

    @Override
    public void onHide() {

    }

    @Override
    public void onShow() {

    }
}
