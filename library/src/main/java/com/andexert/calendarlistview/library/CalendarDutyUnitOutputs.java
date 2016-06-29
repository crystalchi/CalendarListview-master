package com.andexert.calendarlistview.library;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/27 0027.
 */
public class CalendarDutyUnitOutputs {

    private String dutyDate;
    private int dutyStatus;
    private int remainAvailableNumber114;
    private int remainAvailableNumberWeb;
    private int week;
    private Map<String, Integer> wo114Map = new HashMap<String, Integer>();

    public String getDutyDate() {
        return dutyDate;
    }

    public void setDutyDate(String dutyDate) {
        this.dutyDate = dutyDate;
    }

    public int getDutyStatus() {
        return dutyStatus;
    }

    public void setDutyStatus(int dutyStatus) {
        this.dutyStatus = dutyStatus;
    }

    public int getRemainAvailableNumber114() {
        return remainAvailableNumber114;
    }

    public void setRemainAvailableNumber114(int remainAvailableNumber114) {
        this.remainAvailableNumber114 = remainAvailableNumber114;
    }

    public int getRemainAvailableNumberWeb() {
        return remainAvailableNumberWeb;
    }

    public void setRemainAvailableNumberWeb(int remainAvailableNumberWeb) {
        this.remainAvailableNumberWeb = remainAvailableNumberWeb;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public Map<String, Integer> getWo114Map() {
        return wo114Map;
    }

    public void setWo114Map(Map<String, Integer> wo114Map) {
        this.wo114Map = wo114Map;
    }
}
