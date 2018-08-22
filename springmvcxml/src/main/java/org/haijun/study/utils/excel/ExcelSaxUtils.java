package org.haijun.study.utils.excel;

import com.alibaba.fastjson.JSON;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExcelSaxUtils {

    // 处理类
    private ThreadLocal<XSSFSheetXMLHandler.SheetContentsHandler> handlerThreadLocal = new ThreadLocal<>();

    //内部类
    private static class SingletonHandler {
        private static ExcelSaxUtils instance = new ExcelSaxUtils();
    }

    /**
     * 初始化对象
     * @param handler
     * @return
     */
    public static ExcelSaxUtils getInstance(XSSFSheetXMLHandler.SheetContentsHandler handler) {
        ExcelSaxUtils init = SingletonHandler.instance;
        init.handlerThreadLocal.set(handler);
        return init;
    }

    // 构造注入处理类
    /*public ExcelSaxUtils(XSSFSheetXMLHandler.SheetContentsHandler handler) {
        handlerThreadLocal.set(handler);
    }*/

    private ExcelSaxUtils(){};


    /**
     * 处理表格数据
     * @param filename 文件路径
     * @throws Exception
     */
    public void processAllSheets(String filename) throws Exception {
        File file = new File(filename);
        if (!file.exists() || !file.canRead()) {
            throw new Exception("文件不存在或者不能读取该文件");
        }
        try(OPCPackage pkg =  OPCPackage.open(file, PackageAccess.READ);
             //OPCPackage pkg = OPCPackage.open(filename);
            ){
            XSSFReader r = new XSSFReader( pkg );
            //SharedStringsTable sst = r.getSharedStringsTable();
            StylesTable styles = r.getStylesTable();
            XSSFSheetXMLHandler.SheetContentsHandler sheetContentsHandler = handlerThreadLocal.get();
            if(sheetContentsHandler == null){
                throw new ParserConfigurationException("SheetContentsHandler 转换器未找到");
            }
            XMLReader parser = processSheet(styles,new ReadOnlySharedStringsTable(pkg),sheetContentsHandler);
            Iterator<InputStream> sheets = r.getSheetsData();
            while(sheets.hasNext()) {
                System.out.println("第一个sheet 页读取开始:\n");
                InputStream sheet = sheets.next();
                InputSource sheetSource = new InputSource(sheet);
                parser.parse(sheetSource);
                sheet.close();
            }
        }
        // 处理完回收资源
        handlerThreadLocal.remove();
    }
    // 获取SAX 读取对象
    private static XMLReader processSheet(StylesTable styles, ReadOnlySharedStringsTable strings,
                                          XSSFSheetXMLHandler.SheetContentsHandler sheetContentsHandler)
            throws SAXException, ParserConfigurationException {
        /*SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        XMLReader sheetParser = saxFactory.newSAXParser().getXMLReader();*/
        XMLReader sheetParser = SAXHelper.newXMLReader();
        ContentHandler handler1 = new XSSFSheetXMLHandler(styles, strings, sheetContentsHandler,false);
        sheetParser.setContentHandler(handler1);
        return sheetParser;
        //sheetParser.parse(new InputSource(sheetInputStream));
    }

    // 数据头部字段信息
    public enum EnumTest1 {
        MON1, TUE1, WED1, THU1, FRI1, SAT1, SUN1,POP1,GG1,OU1;
    }
    public enum EnumTest2 {
        MON2, TUE2, WED2, THU2, FRI2, SAT2, SUN2,POP2,GG2,OU2;
    }
    public enum EnumTest3 {
        MON3, TUE3, WED3, THU3, FRI3, SAT3, SUN3,POP3,GG3,OU3;
    }
    public static void main(String[] args) throws Exception {
/*        SimpleSheetContentsHandler handler = new SimpleSheetContentsHandler(EnumTest1.values(),true);
        ExcelSaxUtils example = ExcelSaxUtils.getInstance(handler);
        example.processAllSheets("D:\\data\\sxssf.xlsx");
        handler.getData().forEach(
                item -> {
                    System.out.println(JSON.toJSONString(item));
                }
        );*/

        ReadExcelThreadTest test1 = new ReadExcelThreadTest(EnumTest1.values(),"D:\\data\\sxssf.xlsx");
        ReadExcelThreadTest test2 = new ReadExcelThreadTest(EnumTest2.values(),"D:\\data\\sxssf.xlsx");
        ReadExcelThreadTest test3 = new ReadExcelThreadTest(EnumTest3.values(),"D:\\data\\sxssf.xlsx");
        /*test1.run();
        test2.run();
        test3.run();*/
        ExecutorService e = Executors.newScheduledThreadPool(3);// 模拟多线程
        Future future1 = e.submit(test1);
        Future future2 = e.submit(test2);
        Future future3 = e.submit(test3);
        List<Future> futures = new ArrayList<>();
        // future.isDone() //return true,false 无阻塞访问
        // future.get() // return 返回值，阻塞直到该线程运行结束
        futures.add(future1);
        futures.add(future2);
        futures.add(future3);
        boolean exit = false;
        while (!exit){
            exit = futures.stream().allMatch(future -> future.isDone()==true);
        }
        e.shutdown();
    }
}
