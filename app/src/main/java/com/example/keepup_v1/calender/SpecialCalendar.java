package com.example.keepup_v1.calender;

import java.util.Calendar;



public class SpecialCalendar {

    /**
     * 判断是否是闰年
     */
    public boolean isLeapYear(int year){
        if (year % 100 == 0 && year % 400 == 0){
            return true;
        }else if (year % 100 != 0 && year % 4==0){
            return true;
        }
        return false;
    }


    public int getDaysOfMonth(boolean isLeapYear,int month){
        int days=0;
        switch (month){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                days=31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                days=30;
                break;
            case 2:
                if (isLeapYear){
                    days=29;
                }else{
                    days=28;
                }
        }
        return days;
    }


    public int getWeekdayOfMonth( int mYear, int mMonth){
        Calendar cal=Calendar.getInstance();
        cal.clear();
        cal.set(mYear,mMonth,1);
        return cal.get(Calendar.DAY_OF_WEEK);
    }
}
