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
import org.apache.poi.xssf.usermodel.*;
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

/**
 * excel 操作最详细教程 https://www.cnblogs.com/huajiezh/p/5467821.html
 */
public class ExcelUtils {

    private final static String XLS = "xls";
    private final static String XLSX = "xlsx";

    private static ThreadLocal<XSSFSheetXMLHandler.SheetContentsHandler> sheetContentsHandler = new ThreadLocal<>();

    public enum EnumTest {
        MON, TUE, WED, THU, FRI, SAT, SUN;
    }

    public static void main(String[] args) {
        try {
            wirteExcel();// 写文件
            // 读文件
            //SimpleSheetContentsHandler handler = new SimpleSheetContentsHandler(EnumTest.values(),0,true);
            //ExcelUtils.setSheetContentsHandler(handler);
            /*ExcelUtils excelUtils = new ExcelUtils();
            excelUtils.setSheetContentsHandler(handler);
            excelUtils.process();*/
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
            Drawing draw = sheet.createDrawingPatriarch();
            /** 定义注释的大小和位置 http://poi.apache.org/apidocs/org/apache/poi/xssf/usermodel/XSSFComment.html
             * @param dx1  the x coordinate within the first cell. 第一个单元格内的X坐标。
             * @param dy1  the y coordinate within the first cell. 第一个单元内的Y坐标。
             * @param dx2  the x coordinate within the second cell. 第二个单元内的X坐标。
             * @param dy2  the y coordinate within the second cell. 第二个单元内的Y坐标。
             *             左上角的坐标点（）
             * @param col1 the column (0 based) of the first cell. 第一个单元的列（0为基础）
             * @param row1 the row (0 based) of the first cell. 第一行的行（0为基础）。
             *             右下角
             * @param col2 the column (0 based) of the second cell. 第二单元的列（0为基础）。
             * @param row2 the row (0 based) of the second cell. 第二个单元的行（0为基础）。
             */
            //draw.createPicture()
            Comment comment = draw.createCellComment(new XSSFClientAnchor(255, 125, 1023, 150, 4, 2, 9, 7));
            XSSFRichTextString rtf = new XSSFRichTextString("添加批注内容！");
            Font commentFormatter = workbook.createFont();
            commentFormatter.setFontName("宋体");
            //设置字体大小
            commentFormatter.setFontHeightInPoints((short) 9);
            rtf.applyFont(commentFormatter);
            comment.setString(rtf);
            comment.setAuthor("haijun Xie");

            for(int rownum = 0; rownum < 100; rownum++){
                Row row = sheet.createRow(rownum);
                for(int cellnum = 0; cellnum < 10; cellnum++){
                    Cell cell = row.createCell(cellnum);

                    String address = new CellReference(cell).formatAsString();
                    cell.setCellValue(address);
                    if(rownum==2 && cellnum==1){
                        cell.setCellComment(comment);
                    }
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
