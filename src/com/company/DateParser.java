package com.company;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

class DateParser {

    static String stringDateToWeekday(String date) throws java.text.ParseException {
        Date dt1 = stringToDate(date);
        return dateToWeekday(dt1);
    }

    private static Date stringToDate(String date) throws ParseException {
        SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd");
        return format1.parse(date);
    }

    private static String dateToString(Date dt){
        DateFormat format1=new SimpleDateFormat("yyyy-MM-dd");
        return format1.format(dt);
    }

    private static String dateToWeekday(Date dt){
        DateFormat format = new SimpleDateFormat("EE");
        return format.format(dt);
    }

    static String getDateOfPreviousWeekday(String date, String weekday) throws ParseException {
        String currentWeekday = stringDateToWeekday(date);
        Date dt = stringToDate(date);
        if(currentWeekday.equalsIgnoreCase(weekday)) return date;
        else{
            while(!currentWeekday.equalsIgnoreCase(weekday)){
                Calendar c = Calendar.getInstance();
                c.setTime(dt);
                c.add(Calendar.DATE, -1);
                dt = c.getTime();
                currentWeekday = dateToWeekday(dt);
            }

        }
        return dateToString(dt);
    }
    static int getWeekFromDate(String date) throws ParseException {
        Date dt = stringToDate(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    static int getYearFromDate(String date) throws ParseException {
        Date dt = stringToDate(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        return cal.get(Calendar.YEAR);
    }


}
