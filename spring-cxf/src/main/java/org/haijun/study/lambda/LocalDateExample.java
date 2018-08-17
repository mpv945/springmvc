package org.haijun.study.lambda;

import java.time.*;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.HijrahChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.*;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

public class LocalDateExample {

    // https://my.oschina.net/lizaizhong/blog/1814267 和 https://www.jianshu.com/p/19bd58b30660
    public static void main(String[] args) {
        /*Instant：瞬时实例。
        LocalDate：本地日期，不包含具体时间 例如：2014-01-14 可以用来记录生日、纪念日、加盟日等。
        LocalTime：本地时间，不包含日期。
        LocalDateTime：组合了日期和时间，但不包含时差和时区信息。
        ZonedDateTime：最完整的日期时间，包含时区和相对UTC或格林威治的时差。
        新API还引入了ZoneOffSet和ZoneId类，使得解决时区问题更为简便。解析、格式化时间的DateTimeFormatter类也全部重新设 计。*/
        // 格式化时间
        //LocalDateTime.now().format(DateTimeFormatter.ofPattern(timeFormat));
        // 本月第几周和本年第几周
        /*Calendar cal = Calendar.getInstance();
        System.out.println("Current week of month: " + cal.get(Calendar.WEEK_OF_MONTH));
        System.out.println("Current week of year: " + cal.get(Calendar.WEEK_OF_YEAR));*/
        // 详情使用https://www.cnblogs.com/IcanFixIt/p/8539007.html
        //String 类型转localDate 注意DateTimeFormatter 有很多自定义的格式
        //LocalDate beginDateTime = LocalDate.parse("2018-06-09", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        final HijrahChronology c = HijrahChronology.INSTANCE;
        ChronoLocalDateTime d = c.localDateTime(LocalDateTime.now());
        //Arrays.stream(Locale.getAvailableLocales()).forEach(System.out::println);
        System.out.println(Locale.SIMPLIFIED_CHINESE.toLanguageTag());
        //System.out.println(ChineseCalendar.getInstance());
        //Chronology.getAvailableChronologies().forEach(System.out::println);
        System.out.println(d.format(DateTimeFormatter.ofPattern("y-M-d")));
        LocalDate beginDateTime = LocalDate.of(2018, 9, 6); // equals判断相等
        System.out.println(beginDateTime.isBefore(LocalDate.now()));
        ZonedDateTime sfds = ZonedDateTime.now();
        System.out.println(sfds.format(DateTimeFormatter.ofPattern("yy/Y/u年M/L月一年第D天一月第d天q季度Q季度G年份一年第W周/一月第w/F周" +
                "星期E/e/c上下午:a小时h/K二十四小时k/H分钟m秒s毫秒S纳秒n时区z/O/X/x/Z")));// V时区id
        System.out.println(sfds.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
        int year = beginDateTime.getYear();int month = beginDateTime.getMonthValue();int day1 = beginDateTime.getDayOfMonth();
        System.out.printf("Year : %d Month : %d day : %d t %n", year, month, day1);
        System.out.println(
                beginDateTime + "是当月第" +
                        beginDateTime.get(ChronoField.ALIGNED_WEEK_OF_MONTH) + "周的" +
                        beginDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.CHINA));
        System.out.println("当年第"+beginDateTime.get(ChronoField.ALIGNED_WEEK_OF_YEAR)+"周");
        System.out.println("是否是闰年："+beginDateTime.isLeapYear());
        //开始周
        TemporalAdjuster FIRST_OF_WEEK = TemporalAdjusters.ofDateAdjuster(localDate -> localDate.minusDays(localDate.getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue()));
        LocalDate startFirstWeek = beginDateTime.with(FIRST_OF_WEEK);  //开始周开始日期
        TemporalAdjuster LAST_OF_WEEK = TemporalAdjusters.ofDateAdjuster(localDate -> localDate.plusDays(DayOfWeek.SUNDAY.getValue() - localDate.getDayOfWeek().getValue()));
        LocalDate endFirstWeek = beginDateTime.with(LAST_OF_WEEK);     //开始周结束日期
        System.out.println(startFirstWeek+"~"+endFirstWeek);

        // 判断生日
        LocalDate today12 = LocalDate.now();// 今天
        LocalDate date1 = LocalDate.of(2014, 8, 15); //当天为8月15日
        System.out.println( "年份是否相等" + today12.equals(date1));
        MonthDay birthday = MonthDay.of(date1.getMonth(), date1.getDayOfMonth());// 生日
        System.out.println("是否是生日："+MonthDay.from(today12).equals(birthday));
        LocalDate nextWeek = today12.plus(1, ChronoUnit.WEEKS);// 增加一周（加7天）
        System.out.println("下周的时间"+nextWeek);
        LocalDate previousYear = today12.minus(1, ChronoUnit.YEARS);
        System.out.println("上一年"+previousYear);
        //判断信用卡到期可以使用YearMonth
        YearMonth currentYearMonth = YearMonth.now();
        // currentYearMonth.lengthOfMonth()在判断2月有28天还是29天时非常有用
        System.out.printf("改天数据年和月的数据= %s: 月的长度=%d天%n", currentYearMonth, currentYearMonth.lengthOfMonth());
        YearMonth creditCardExpiry = YearMonth.of(2018, Month.FEBRUARY);
        System.out.printf("信用卡过去时间= %s %n", creditCardExpiry);
        // 获取时区
        Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();// 获取世界各个地方的时区的集合
        Clock clock = Clock.systemUTC();
        System.out.println("Clock : " + clock+"   defaultClock :"+Clock.systemDefaultZone());
        // ZoneId america = ZoneId.of("Asia/Shanghai");// 创建时区
        //有两类方法isBefore()和isAfter()用于比较日期。调用isBefore()方法时，如果给定日期小于当前日期则返回
        //eventLocalDate.isBefore(LocalDate.now(clock)// 这种方式在不同时区下处理日期时会非常管用。
        ZoneOffset offset = ZoneOffset.of("+05:30");
        OffsetDateTime offsetDateTime = OffsetDateTime.of(LocalDateTime.now(), offset);//LocalDateTime.now().atOffset(offset)
        // 现在的时间信息里已经包含了时区信息了。注意：OffSetDateTime是对计算机友好的，ZoneDateTime则对人更友好。
        System.out.println("设置偏移时区的时间="+offsetDateTime+"  |转换成时间="+offsetDateTime.toLocalDateTime());
        // 计算两个日期之间的天数、周数或月数。在Java 8中可以用java.time.Period类来做计算。
        // 一月 January 二月 February 三月 March 四月 April 五月 May 六月 June 七月 July
        // 八月 August 九月 September 十月 October 十一月 November 十二月December
        LocalDate java8Release = LocalDate.of(2018, Month.MARCH, 14);
        Period periodToNextJavaRelease = Period.between(LocalDate.now(), java8Release);
        System.out.println("现在距离java8Release时间相隔"+periodToNextJavaRelease.get(ChronoUnit.MONTHS)+"月");// .get()只能取到年月日
        System.out.println("两个日期相差"+(java8Release.toEpochDay()-LocalDate.now().toEpochDay())+"天");
        System.out.println("计算相差："+ChronoUnit.WEEKS.between(LocalDate.now(),java8Release)+"周");
        // 在Java 8中获取当前的时间戳;getEpochSecond()/getNano() 获取从1970-01-01  00：00：00到当前时间的秒值/纳秒
        Instant timestamp = Instant.now();
        Date mydate = Date.from(timestamp);// 将Instant转换成
        mydate.toInstant();//则是将Date类转换成Instant类。
        System.out.println("当前时间戳=" + timestamp.toEpochMilli());
        Duration gap = Duration.ofSeconds(10);//其中.ofEpochSecond(5) 在计算机元年（1970-01-01  00：00：00）的基础上增加秒数
        Instant later = timestamp.plus(gap);
        // 通过Zoneld.systemDefault()来获取本地的默认时区ID
        ZonedDateTime zoneDateTime=timestamp.atZone(ZoneId.systemDefault());
        System.out.println("两个瞬时的时间相隔："+Duration.between(timestamp,later).get(ChronoUnit.SECONDS)+"秒");// 可以算出相差秒和纳秒数
        System.out.println("两个瞬时的时间相隔："+Duration.between(timestamp,later).toMillis()+"毫秒"+Duration.between(timestamp,later).toMinutes()+"分钟");
        // TemporalAdjuster: 时间校正器（接口）
        // 使用TemporalAdjusters自带的常量来设置日期
        System.out.println("今年最后一天"+LocalDate.now().with(TemporalAdjusters.lastDayOfYear()));
        // 采用TemporalAdjusters中的next方法来指定日期
        // 星期一MONDAY 星期二TUESDAY 星期三WEDNESDAY 星期四THURSDAY 星期五FRIDAY 星期六SATURDAY 星期天SUNDAY
        System.out.println("下一个星期天="+LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY)));
        // 采用自定义的方法来指定日期
        LocalDateTime ldt = LocalDateTime.now();
        LocalDateTime workDay = ldt.with(new TemporalAdjuster() {
            @Override
            public Temporal adjustInto(Temporal temporal) {
                LocalDateTime ld = (LocalDateTime) temporal;
                DayOfWeek dayOfWeek = ld.getDayOfWeek();
                if (dayOfWeek.equals(DayOfWeek.FRIDAY)) {
                    return ld.plusDays(3);
                } else if (dayOfWeek.equals(DayOfWeek.SATURDAY)) {
                    return ld.plusDays(2);
                }else {
                    return ld.plusDays(1);
                }
            }
        });
        System.out.println("下一个工作日"+workDay);

        //工作日的增加（默认五天制，星期六星期天休息）
        TemporalAdjuster NEXT_WEEK = TemporalAdjusters.ofDateAdjuster(
                temporal -> {
                    DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
                    int dayToAdd = 1;
                    if (dow == DayOfWeek.FRIDAY) dayToAdd = 3;
                    if (dow == DayOfWeek.SATURDAY) dayToAdd = 2;
                    return temporal.plus(dayToAdd, ChronoUnit.DAYS);
                }
        );
        System.out.println("下一个工作日"+LocalDate.now().with(NEXT_WEEK));

        if(true){
            return;
        }

        //date类型转localDate
        /*Date dates = new SimpleDateFormat(“yyyyMM”).parse(cycle);
        Instant instant = dates.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();*/

        LocalDate firstDay = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDay1 = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        System.out.println("当月第一天="+firstDay);
        System.out.println("当月最后一天="+lastDay1);

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
