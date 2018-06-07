package org.haijun.study.lambda;

import java.time.*;
import java.time.temporal.*;
import java.util.Date;
import java.util.Locale;

public class LocalDateExample {

    // https://my.oschina.net/lizaizhong/blog/1814267 和 https://www.jianshu.com/p/19bd58b30660
    public static void main(String[] args) {


        // 本月第几周和本年第几周
        /*Calendar cal = Calendar.getInstance();
        System.out.println("Current week of month: " + cal.get(Calendar.WEEK_OF_MONTH));
        System.out.println("Current week of year: " + cal.get(Calendar.WEEK_OF_YEAR));*/


        LocalDate today4 = LocalDate.now();
        //本月多少天
        int days = today4.lengthOfMonth();
        System.out.println("days="+days);
        LocalDate today5 = LocalDate.now();
        int day= today5.get(ChronoField.DAY_OF_YEAR);
        System.out.println("今天是本年的第"+day+"天");
        day= today5.get(ChronoField.DAY_OF_MONTH);
        System.out.println("今天是本月的第"+day+"天");
        day=today5.get(ChronoField.DAY_OF_WEEK);
        System.out.println("今天是本周的第"+day+"天");

        LocalDate today2 = LocalDate.now();
        //本月的第一天
        LocalDate firstday = LocalDate.of(today2.getYear(),today2.getMonth(),1);
        //本月的最后一天
        LocalDate lastDay =today2.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println("本月的第一天"+firstday);
        System.out.println("本月的最后一天"+lastDay);

        //Current Date
        LocalDate today = LocalDate.now();
        System.out.println("Current Date=" + today);

        //Creating LocalDate by providing input arguments
        LocalDate firstDay_2014 = LocalDate.of(2014, Month.JANUARY, 1);
        System.out.println("Specific Date=" + firstDay_2014);


        //Try creating date by providing invalid inputs
        //LocalDate feb29_2014 = LocalDate.of(2014, Month.FEBRUARY, 29);
        //Exception in thread "main" java.time.DateTimeException:
        //Invalid date 'February 29' as '2014' is not a leap year

        //Current date in "Asia/Kolkata", you can get it from ZoneId javadoc
        LocalDate todayKolkata = LocalDate.now(ZoneId.of("Asia/Kolkata"));
        System.out.println("Current Date in IST=" + todayKolkata);

        //java.time.zone.ZoneRulesException: Unknown time-zone ID: IST
        //LocalDate todayIST = LocalDate.now(ZoneId.of("IST"));

        //Getting date from the base date i.e 01/01/1970
        LocalDate dateFromBase = LocalDate.ofEpochDay(365);
        System.out.println("365th day from base date= " + dateFromBase);

        LocalDate hundredDay2014 = LocalDate.ofYearDay(2014, 100);
        System.out.println("100th day of 2014=" + hundredDay2014);


        //时间
        LocalTime time = LocalTime.now();
        System.out.println("Current Time="+time);

        //Creating LocalTime by providing input arguments
        LocalTime specificTime = LocalTime.of(12,20,25,40);
        System.out.println("Specific Time of Day="+specificTime);


        //Try creating time by providing invalid inputs
        //LocalTime invalidTime = LocalTime.of(25,20);
        //Exception in thread "main" java.time.DateTimeException:
        //Invalid value for HourOfDay (valid values 0 - 23): 25

        //Current date in "Asia/Kolkata", you can get it from ZoneId javadoc
        LocalTime timeKolkata = LocalTime.now(ZoneId.of("Asia/Kolkata"));
        System.out.println("Current Time in IST="+timeKolkata);

        //java.time.zone.ZoneRulesException: Unknown time-zone ID: IST
        //LocalTime todayIST = LocalTime.now(ZoneId.of("IST"));

        //Getting date from the base date i.e 01/01/1970
        LocalTime specificSecondTime = LocalTime.ofSecondOfDay(10000);
        System.out.println("10000th second time= "+specificSecondTime);


        //带时间的日期
        LocalDateTime today1 = LocalDateTime.now();
        System.out.println("Current DateTime="+today1);

        //Current Date using LocalDate and LocalTime
        today1 = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        System.out.println("Current DateTime="+today1);

        //Creating LocalDateTime by providing input arguments
        LocalDateTime specificDate = LocalDateTime.of(2014, Month.JANUARY, 1, 10, 10, 30);
        System.out.println("Specific Date="+specificDate);


        //Try creating date by providing invalid inputs
        //LocalDateTime feb29_2014 = LocalDateTime.of(2014, Month.FEBRUARY, 28, 25,1,1);
        //Exception in thread "main" java.time.DateTimeException:
        //Invalid value for HourOfDay (valid values 0 - 23): 25


        //Current date in "Asia/Kolkata", you can get it from ZoneId javadoc
        LocalDateTime todayKolkata1 = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
        System.out.println("Current Date in IST="+todayKolkata1);

        //java.time.zone.ZoneRulesException: Unknown time-zone ID: IST
        //LocalDateTime todayIST = LocalDateTime.now(ZoneId.of("IST"));

        //Getting date from the base date i.e 01/01/1970
        LocalDateTime dateFromBase1 = LocalDateTime.ofEpochSecond(10000, 0, ZoneOffset.UTC);
        System.out.println("10000th second time from 01/01/1970= "+dateFromBase1);

    }

    //获取周第一天
    public Date getStartDayOfWeek(String date) {
        LocalDate now = LocalDate.parse(date);
        return this.getStartDayOfWeek(now);
    }

    public Date getStartDayOfWeek(TemporalAccessor date) {
        TemporalField fieldISO = WeekFields.of(Locale.CHINA).dayOfWeek();
        LocalDate localDate = LocalDate.from(date);
        localDate.with(fieldISO, 1);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    //获取周最后一天
    public Date getEndDayOfWeek(String date) {
        LocalDate localDate = LocalDate.parse(date);
        return this.getEndDayOfWeek(localDate);
    }

    public Date getEndDayOfWeek(TemporalAccessor date) {
        TemporalField fieldISO = WeekFields.of(Locale.CHINA).dayOfWeek();
        LocalDate localDate = LocalDate.from(date);
        localDate.with(fieldISO, 7);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).plusDays(1L).minusNanos(1L).toInstant());
    }
    //一天的开始
    public Date getStartOfDay(String date) {
        LocalDate localDate = LocalDate.parse(date);
        return this.getStartOfDay(localDate);
    }

    public Date getStartOfDay(TemporalAccessor date) {
        LocalDate localDate = LocalDate.from(date);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    //一天的结束
    public Date getEndOfDay(String date){
        LocalDate localDate = LocalDate.parse(date);
        return this.getEndOfDay(localDate);
    }
    public Date getEndOfDay(TemporalAccessor date) {
        LocalDate localDate = LocalDate.from(date);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).plusDays(1L).minusNanos(1L).toInstant());
    }
}
