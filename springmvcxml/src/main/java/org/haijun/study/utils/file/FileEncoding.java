package org.haijun.study.utils.file;

import info.monitorenter.cpdetector.io.*;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

public class FileEncoding {

    private static CodepageDetectorProxy detector;


    public static void main(String[] args) {
        System.out.println("系统默认编码= "+System.getProperty("file.encoding"));
        System.out.println("系统换行符="+IOUtils.LINE_SEPARATOR.equalsIgnoreCase(IOUtils.LINE_SEPARATOR_WINDOWS));
        System.out.println("系统路径分隔符="+File.separator);
        System.out.println("文件编码="+getFileEncode(new File("C:\\Users\\zhenx\\IdeaProjects\\springmvc\\springmvcxml\\src\\main\\java\\org\\haijun\\study\\utils\\csv\\CSVUtils.java")));

        System.out.println("网页编码="+getUrlEncode("https://blog.csdn.net/upshi/article/details/69946688"));

    }

    static {
        /*
         * detector是探测器，它把探测任务交给具体的探测实现类的实例完成。
         * cpDetector内置了一些常用的探测实现类，这些探测实现类的实例可以通过add方法 加进来，如ParsingDetector、
         * JChardetFacade、ASCIIDetector、UnicodeDetector。
         * detector按照“谁最先返回非空的探测结果，就以该结果为准”的原则返回探测到的
         * 字符集编码。使用需要用到三个第三方JAR包：antlr.jar、chardet.jar和cpdetector.jar
         * cpDetector是基于统计学原理的，不保证完全正确。
         */
        //CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
        detector = CodepageDetectorProxy.getInstance();
        /*
         * ParsingDetector可用于检查HTML、XML等文件或字符流的编码,构造方法中的参数用于
         * 指示是否显示探测过程的详细信息，为false不显示。
         */
        detector.add(new ParsingDetector(false));

        /*
         * JChardetFacade封装了由Mozilla组织提供的JChardet，它可以完成大多数文件的编码
         * 测定。所以，一般有了这个探测器就可满足大多数项目的要求，如果你还不放心，可以
         * 再多加几个探测器，比如下面的ASCIIDetector、UnicodeDetector等。
         */
        detector.add(JChardetFacade.getInstance());// 用到antlr.jar、chardet.jar

        // ASCIIDetector用于ASCII编码测定
        detector.add(ASCIIDetector.getInstance());
        // UnicodeDetector用于Unicode家族编码的测定
        detector.add(UnicodeDetector.getInstance());
    }

    /**
     * 根据文件获取文件的编码
     * @param f
     * @return
     */
    public static String getFileEncode(File f){
        Charset charset = null;

        try {
            charset = detector.detectCodepage(f.toURI().toURL());//待测的文本输入流,测量该流所需的读入字节数（detectCodepage(InputStream in, int length)）
            if (charset != null) {
                // 因为　utf-8 兼容　US-ASCII，但　US-ASCII 不兼容　UTF-8，对中文等亚州语系无法兼容。故应变成　UTF-8
                if (charset.name().equalsIgnoreCase("US-ASCII")){
                    return "UTF-8";
                }
                return charset.name();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * 根据文件获取文件的编码
     * @param urlAddr
     * @return
     */
    public static String getUrlEncode(String urlAddr){
        Charset charset = null;
        try {
            URL url = new URL(urlAddr);
            charset = detector.detectCodepage(url);//待测的文本输入流,测量该流所需的读入字节数（detectCodepage(InputStream in, int length)）
            if (charset != null) {
                // 因为　utf-8 兼容　US-ASCII，但　US-ASCII 不兼容　UTF-8，对中文等亚州语系无法兼容。故应变成　UTF-8
                if (charset.name().equalsIgnoreCase("US-ASCII")){
                    return "UTF-8";
                }
                return charset.name();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
    /*public static String getFileEncode(File f) {
        *//*
         * detector是探测器，它把探测任务交给具体的探测实现类的实例完成。
         * cpDetector内置了一些常用的探测实现类，这些探测实现类的实例可以通过add方法 加进来，如ParsingDetector、
         * JChardetFacade、ASCIIDetector、UnicodeDetector。
         * detector按照“谁最先返回非空的探测结果，就以该结果为准”的原则返回探测到的
         * 字符集编码。使用需要用到三个第三方JAR包：antlr.jar、chardet.jar和cpdetector.jar
         * cpDetector是基于统计学原理的，不保证完全正确。
         *//*
        CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();

        *//*
         * ParsingDetector可用于检查HTML、XML等文件或字符流的编码,构造方法中的参数用于
         * 指示是否显示探测过程的详细信息，为false不显示。
         *//*
        detector.add(new ParsingDetector(false));

        *//*
         * JChardetFacade封装了由Mozilla组织提供的JChardet，它可以完成大多数文件的编码
         * 测定。所以，一般有了这个探测器就可满足大多数项目的要求，如果你还不放心，可以
         * 再多加几个探测器，比如下面的ASCIIDetector、UnicodeDetector等。
         *//*
        detector.add(JChardetFacade.getInstance());// 用到antlr.jar、chardet.jar

        // ASCIIDetector用于ASCII编码测定
        detector.add(ASCIIDetector.getInstance());
        // UnicodeDetector用于Unicode家族编码的测定
        detector.add(UnicodeDetector.getInstance());

        Charset charset = null;

        try {
            charset = detector.detectCodepage(f.toURI().toURL());//待测的文本输入流,测量该流所需的读入字节数（detectCodepage(InputStream in, int length)）
            if (charset != null) {
                // 因为　utf-8 兼容　US-ASCII，但　US-ASCII 不兼容　UTF-8，对中文等亚州语系无法兼容。故应变成　UTF-8
                if (charset.name().equalsIgnoreCase("US-ASCII")){
                    return "UTF-8";
                }
                return charset.name();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }*/
}
