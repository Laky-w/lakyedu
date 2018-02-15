package com.laky.edu.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String formatDate(String formatStr, Date date){
        SimpleDateFormat time=new SimpleDateFormat(formatStr);
        return time.format( date );
    }
}
