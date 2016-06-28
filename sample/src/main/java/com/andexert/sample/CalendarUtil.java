package com.andexert.sample;

import java.util.Calendar;

/**
 * Created by crystalchi on 2016/6/28 0028.
 */
public class CalendarUtil {

    /**
     * 获取下一年
     * @return
     */
    public static int getNextYear(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);
        return calendar.get(Calendar.YEAR);
    }
}
