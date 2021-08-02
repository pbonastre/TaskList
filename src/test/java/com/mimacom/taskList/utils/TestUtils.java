package com.mimacom.taskList.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestUtils {

    public static Date parseDate(String dateString){
        try {
            return new SimpleDateFormat("yyyyMMdd").parse(dateString);
        }catch (ParseException e) {
            return null;
        }
    }
}
