package org.haijun.study.tools.springFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 自定义转换器
 */
public class MyDateFormatter implements Formatter<Date> {

    @Autowired
    private MessageSource messageSource;

    /**
     * 解析接口，根据Locale信息解析字符串到T类型的对象；
     * @param text
     * @param locale
     * @return
     * @throws ParseException
     */
    @Override
    public Date parse(final String text, final Locale locale) throws ParseException {
        final SimpleDateFormat dateFormat = createDateFormat(locale);
        return dateFormat.parse(text);
    }

    /**
     * 格式化显示接口，将T类型的对象根据Locale信息以某种格式进行打印显示（即返回字符串形式）；
     * @param object
     * @param locale
     * @return
     */
    @Override
    @Deprecated//方法失效
    public String print(final Date object, final Locale locale) {
        final SimpleDateFormat dateFormat = createDateFormat(locale);
        return dateFormat.format(object);
    }

    private SimpleDateFormat createDateFormat(final Locale locale) {
        final String format = this.messageSource.getMessage("date.format", null, locale);
        final SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false);
        return dateFormat;
    }
}
