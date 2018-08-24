package org.haijun.study.utils.pdf.freemarker;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.Map;

public class FreeMarkToHtml {


    /**
     * 通过路径 和文件名称后去魔板
     *
     * @param templateFilePath
     * @param templateFileName
     * @return
     */
    public static Template getTemplate(String templateFilePath, String templateFileName) {
        try {
            Configuration cfg = new Configuration();
            cfg.setClassicCompatible(true);
            //cfg.setClassForTemplateLoading(Template.class, "/template"); // 加载模板路径
            TemplateLoader templateLoader = new FileTemplateLoader(new File(
                    templateFilePath));
            cfg.setTemplateLoader(templateLoader);
            return cfg.getTemplate(templateFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param templateFilePath
     * @param templateFileName 加载的模板文件名
     * @param replaceData
     * @param outFile          生成指定文件
     * @return 成功，返回文件名；失败，返回null。
     */
    public static String freemarkToHtml(String templateFilePath, String templateFileName, Map<String, Object> replaceData,
                                        String outFile) {

        String path = null;
        Writer out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(outFile), "UTF-8"));
            Template temp = getTemplate(templateFilePath, templateFileName);
            temp.setEncoding("UTF-8");
            temp.process(replaceData, out);
            path = outFile;

        } catch (IOException e) {
            e.printStackTrace();
            path = null;
        } catch (TemplateException e) {
            e.printStackTrace();
            path = null;
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return path;
    }

    /**
     * 根据数据渲染模板路径下的模板，返回页面信息，以字符串返回
     * @param templateFilePath
     * @param templateFileName
     * @param variables
     * @return
     * @throws Exception
     */
    public static String generate(String templateFilePath, String templateFileName, Map<String, Object> variables)
            throws Exception {
        Template tp = getTemplate(templateFilePath, templateFileName);
        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);
        tp.setEncoding("UTF-8");
        tp.process(variables, writer);
        String htmlStr = stringWriter.toString();
        writer.flush();
        writer.close();
        return htmlStr;
    }
}