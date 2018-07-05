package org.haijun.study.designPattern.createType.singleton.threadLocal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class SimpleDateFormatInstancePerThread {

    private static final ThreadLocal<SimpleDateFormat> dateFormatHolder = new ThreadLocal<SimpleDateFormat>() {

        @Override
        protected SimpleDateFormat initialValue() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd") {
                UUID id = UUID.randomUUID();
                @Override
                public String toString() {
                    return id.toString();
                };
            };
            System.out.println("Creating SimpleDateFormat instance " + dateFormat +" for Thread : " + Thread.currentThread().getName());
            return dateFormat;
        }
    };

    /*
     * Every time there is a call for DateFormat, ThreadLocal will return calling
     * Thread's copy of SimpleDateFormat
     */
    public static DateFormat getDateFormatter() {
        return dateFormatHolder.get();
    }

    public static void cleanup() {
        dateFormatHolder.remove();
    }

}
