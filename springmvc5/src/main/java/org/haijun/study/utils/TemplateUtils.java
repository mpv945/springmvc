package org.haijun.study.utils;

import org.apache.commons.jexl3.*;

import java.util.Map;

/**
 * 字符串表达式占位符解析
 *
 * @author tanwei
 * @date 26/01/2018
 */

public class TemplateUtils {

    public static String parse(String expression, Map<String, Object> data) {
        //log.info("解析模板:expression:{},data:{}", expression, data);
        try {
            JexlContext jexlContext = new MapContext(data);
            JexlEngine jexl = new JexlBuilder().create();
            JxltEngine jxlt = jexl.createJxltEngine();
            JxltEngine.Expression expr = jxlt.createExpression(expression);
            return expr.evaluate(jexlContext).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return expression;
    }

}
