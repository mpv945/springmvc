package org.haijun.study.utils.excel;

import com.alibaba.fastjson.JSON;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class ExampleEventUserModel {

    // 处理类
    private static ThreadLocal<XSSFSheetXMLHandler.SheetContentsHandler> handlerThreadLocal = new ThreadLocal<>();

    /**
     * 构造注入处理类
     * @param handler
     */
    public ExampleEventUserModel(XSSFSheetXMLHandler.SheetContentsHandler handler) {
        handlerThreadLocal.set(handler);
    }


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
            XMLReader parser = processSheet(styles,new ReadOnlySharedStringsTable(pkg));
            Iterator<InputStream> sheets = r.getSheetsData();
            while(sheets.hasNext()) {
                System.out.println("第一个sheet 页读取开始:\n");
                InputStream sheet = sheets.next();
                InputSource sheetSource = new InputSource(sheet);
                parser.parse(sheetSource);
                sheet.close();
            }
        }

    }
    // 获取SAX 读取对象
    public XMLReader processSheet(StylesTable styles, ReadOnlySharedStringsTable strings)
            throws SAXException, ParserConfigurationException {
        /*SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        XMLReader sheetParser = saxFactory.newSAXParser().getXMLReader();*/
        XMLReader sheetParser = SAXHelper.newXMLReader();
        XSSFSheetXMLHandler.SheetContentsHandler sheetContentsHandler = handlerThreadLocal.get();
        if(sheetContentsHandler == null){
            throw new ParserConfigurationException("SheetContentsHandler 转换器未找到");
        }
        ContentHandler handler1 = new XSSFSheetXMLHandler(styles, strings, sheetContentsHandler,false);
        sheetParser.setContentHandler(handler1);
        return sheetParser;
        //sheetParser.parse(new InputSource(sheetInputStream));
    }

    // 数据头部字段信息
    public enum EnumTest1 {
        MON, TUE, WED, THU, FRI, SAT, SUN,POP,GG,OU;
    }
    public static void main(String[] args) throws Exception {
        SimpleSheetContentsHandler handler = new SimpleSheetContentsHandler(EnumTest1.values(),true);
        ExampleEventUserModel example = new ExampleEventUserModel(handler);
        example.processAllSheets("D:\\data\\sxssf.xlsx");
        handler.getData().forEach(
                item -> {
                    System.out.println(JSON.toJSONString(item));
                }
        );
    }
}
