package org.haijun.study.utils.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ExcelUtils {

    private final static String XLS = "xls";
    private final static String XLSX = "xlsx";

    private static ThreadLocal<XSSFSheetXMLHandler.SheetContentsHandler> sheetContentsHandler = new ThreadLocal<>();

    public enum EnumTest {
        MON, TUE, WED, THU, FRI, SAT, SUN;
    }

    public static void main(String[] args) {
        try {
            //wirteExcel();// 写文件
            // 读文件
            SimpleSheetContentsHandler handler = new SimpleSheetContentsHandler(EnumTest.values(),0,true);
            //ExcelUtils.setSheetContentsHandler(handler);
            ExcelUtils excelUtils = new ExcelUtils();
            excelUtils.setSheetContentsHandler(handler);
            excelUtils.process();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置xml处理器
     * @param val
     */
    public static void setSheetContentsHandler(XSSFSheetXMLHandler.SheetContentsHandler val){
        sheetContentsHandler.set(val);
    }

    private static Workbook getWorkBook(String fileName,boolean isWrite) {
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
        if(fileName.endsWith(XLS)){
            //2003
            workbook = new HSSFWorkbook();
        }else if(fileName.endsWith(XLSX)){
            if(isWrite){ // 使用xml 流式处理
                // 滑动窗口的默认大小windowSize为100，是由SXSSFWorkbook.DEFAULT_WINDOW_SIZE定义。为了提供性能，可以加大。
                workbook = new SXSSFWorkbook();//SXSSF，POI3.8以后开始支持，这种方式只能写excel
            }else {
                //2007
                workbook = new XSSFWorkbook();
            }
        }
        return workbook;
    }

    public void process() throws Exception {
        wirteExcel();
    }
    /**
     * 写入文件
     * @return
     * @throws IOException
     */
    public static boolean wirteExcel() throws IOException{
        String fileName = "sxssf.xlsx";
        String path = "D:\\data\\";
        try(Workbook workbook = getWorkBook(fileName,true);
            FileOutputStream out = new FileOutputStream(path+fileName);) {
            Sheet sheet = workbook.createSheet("sheet1");// 创建第一个sheet页
            for(int rownum = 0; rownum < 100; rownum++){
                Row row = sheet.createRow(rownum);
                for(int cellnum = 0; cellnum < 10; cellnum++){
                    Cell cell = row.createCell(cellnum);
                    String address = new CellReference(cell).formatAsString();
                    cell.setCellValue(address);
                }
            }
            workbook.write(out); // 写入到本地
        }
        /*Sheet sheet = wb.createSheet("各种类型单元格");
        sheet.createRow(0).createCell(0).setCellValue(1.1);
        sheet.createRow(1).createCell(0).setCellValue(new Date());
        sheet.createRow(2).createCell(0).setCellValue(Calendar.getInstance());
        sheet.createRow(3).createCell(0).setCellValue("字符串");
        sheet.createRow(4).createCell(0).setCellValue(true);
        sheet.createRow(5).createCell(0).setCellType(CellType.ERROR);*/
        return true;
    }

    /**
     * 读取数据
     * @return
     */
    public static void readExcel() throws Exception{
        File file = new File("D:\\data\\sxssf.xlsx");
        try(OPCPackage opcPackage = getOPCPackage(file);){
            XSSFReader xssfReader = new XSSFReader(opcPackage);
            //SharedStringsTable sst = xssfReader.getSharedStringsTable();
            StylesTable styles = xssfReader.getStylesTable();
            XMLReader parser = readSheet(styles,new ReadOnlySharedStringsTable(opcPackage));
            XSSFReader.SheetIterator worksheets = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
            for (int sheetIndex = 0; worksheets.hasNext(); sheetIndex++) {
                InputStream stream = worksheets.next();
                InputSource sheetSource = new InputSource(stream);
                parser.parse(sheetSource);
                stream.close();
            }
        }

    }

    /**

     * Parses the content of one sheet using the specified styles and shared-strings tables.
     *
     * @param styles a {@link StylesTable} object
     * @param sharedStringsTable a {@link ReadOnlySharedStringsTable} object
     //* @param sheetInputStream a {@link InputStream} object
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    private static XMLReader readSheet(StylesTable styles, ReadOnlySharedStringsTable sharedStringsTable)
            throws ParserConfigurationException, SAXException {
        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        XMLReader sheetParser = saxFactory.newSAXParser().getXMLReader();
        //DataFormatter formatter = new DataFormatter();
        //ContentHandler handler = new XSSFSheetXMLHandler(styles, sharedStringsTable, sheetContentsHandler, formatter,true);
        XSSFSheetXMLHandler.SheetContentsHandler xmlHandler = sheetContentsHandler.get();
        if(xmlHandler == null){
            throw new SAXException("没有SheetContentsHandler");
        }
        SimpleSheetContentsHandler handlertt = new SimpleSheetContentsHandler(EnumTest.values(),0,true);
        ContentHandler handler = new XSSFSheetXMLHandler(styles, sharedStringsTable, handlertt,false);
        sheetParser.setContentHandler(handler);
        //sheetParser.parse(new InputSource(sheetInputStream));
        return sheetParser;
    }

    private static OPCPackage getOPCPackage(File file) throws Exception {
        if (null == file || !file.canRead()) {
            throw new Exception("File object is null or cannot have read permission");
        }
        return OPCPackage.open(file, PackageAccess.READ);
    }

    /**
     * 读取列内容类型
     * @param cell
     * @return
     */
    public static Object getCellFormatValue(Cell cell){
        Object cellValue = "";
        if(cell == null){
            return cellValue;
        }
        //判断数据的类型
        CellType type = cell.getCellTypeEnum();
        if(type.equals(CellType._NONE)){// 未知类型，限内部使用

        }else if (type.equals(CellType.NUMERIC)){//数值类型，{整数，小数，日期}

        }else if (type.equals(CellType.STRING)){// 字符串

        }else if (type.equals(CellType.BOOLEAN)){// 布尔类型

        }else if (type.equals(CellType.FORMULA)){// 公式

        }else if (type.equals(CellType.BLANK)){//空单元。没值，但有单元格样式

        }else if (type.equals(CellType.ERROR)){// 错误单元格

        }

        return cellValue;
    }

}
