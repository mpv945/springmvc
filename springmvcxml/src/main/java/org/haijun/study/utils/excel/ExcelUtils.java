package org.haijun.study.utils.excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
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
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * excel 操作最详细教程 https://www.cnblogs.com/huajiezh/p/5467821.html
 */
public class ExcelUtils {

    private final static String XLS = "xls";
    private final static String XLSX = "xlsx";

    public static void main(String[] args) {
        try {
            wirteExcel();// 写文件

            //readExcel();// 读文件
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Workbook getWorkBook(String fileName,boolean isWrite,InputStream in) throws IOException {
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
        if(fileName.endsWith(XLS)){
            if(isWrite){
                //2003
                workbook = new HSSFWorkbook();
            }else {
                workbook = new HSSFWorkbook(in);
            }

        }else if(fileName.endsWith(XLSX)){
            if(isWrite){ // 使用xml 流式处理
                // 滑动窗口的默认大小windowSize为100，是由SXSSFWorkbook.DEFAULT_WINDOW_SIZE定义。为了提供性能，可以加大。
                workbook = new SXSSFWorkbook();//SXSSF，POI3.8以后开始支持，这种方式只能写excel
            }else {
                //2007
                workbook = new XSSFWorkbook(in);
            }
        }
        return workbook;
    }
    /**
     * 写入文件
     * @return
     * @throws IOException
     */
    public static boolean wirteExcel() throws Exception{
        String fileName = "sxssf.xls";
        String path = "D:\\data\\";
        try(Workbook workbook = getWorkBook(fileName,true,null);
            FileOutputStream out = new FileOutputStream(path+fileName);) {

            DataFormat dataFormat = workbook.createDataFormat();
            CellStyle style = workbook.createCellStyle();

            Sheet sheet = workbook.createSheet("sheet1");// 创建第一个sheet页
            // workbook.setSheetName(2, "1234");//重命名工作表
            //sheet.setZoom(200);//显示比例(表示放大200%）
            //workbook.setActiveSheet(2);//设置默认工作表
            // sheet.setDisplayGridlines(false);//隐藏Excel网格线,默认值为true
            // sheet.setGridsPrinted(true);//打印时显示网格线,默认值为false
            // 第一个参数表示要冻结的列数；第二个参数表示要冻结的行数，这里只冻结列所以为0；第三个参数表示右边区域可见的首列序号，从1开始计算；
            //sheet.createFreezePane(2, 3, 15, 25);//冻结行列；第四个参数表示下边区域可见的首行序号，也是从1开始计算，这里是冻结列，所以为0；
            sheet.createFreezePane(0, 1, 0, 1);// 第一行数据冻结，下面可见从序号1开始（冻结首行）；(1, 0, 1, 0);冻结首列，竖向冻结
            // sheet.shiftRows(2, 4, 2);//把第3行到第4行向下移动两行 （可以在中间新增数据行）
            //Sheet.shiftRows(startRow, endRow, n)参数说明 startRow：需要移动的起始行；endRow：需要移动的结束行；n：移动的位置，正数表示向下移动，负数表示向上移动；
            //sheet.protectSheet("password");//设置保护密码
            // 生成下拉式菜单
            DataValidationHelper validationHelper = sheet.getDataValidationHelper();
            // 加载下拉列表内容
            String[] textlist = new String[] { "C++","Java", "C#" };
            DataValidationConstraint explicitListConstraint = validationHelper.createExplicitListConstraint(textlist);
            // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
            CellRangeAddressList regions = new CellRangeAddressList(0, 65535,0, 0);
            // 数据有效性对象
            DataValidation validation = validationHelper.createValidation(explicitListConstraint, regions);
            validation.setSuppressDropDownArrow(true);//该列全部行需要验证
            validation.createErrorBox("注意","请从下拉列表选取");//警告提示
            //错误警告框
            validation.setShowErrorBox(true);
            sheet.addValidationData(validation);

            // 时间检查
            /*Calendar date = Calendar.getInstance();
            date.add(Calendar.DATE,60);
            Date endDate = date.getTime();*/

            CellRangeAddressList dateConstraintRegions = new CellRangeAddressList(0, 65535,2, 2);
            DataValidation validation1Date = null;
            if(fileName.endsWith(XLS)){
                DVConstraint dvConstraint = DVConstraint.createDateConstraint(DVConstraint.
                        OperatorType.BETWEEN,"2018/7/25","2018/10/25","yyyy/MM/dd");
                validation1Date = validationHelper.createValidation(dvConstraint,dateConstraintRegions);
                //方案二
                //DataValidationConstraint dateConstraint  =
                        //validationHelper.createDateConstraint(DataValidationConstraint.OperatorType.BETWEEN, "2018/7/25", "2018/10/25", "yyyy/MM/dd");
            }else if(fileName.endsWith(XLSX)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                DataValidationConstraint dateConstraint  =
                        validationHelper.createDateConstraint(DataValidationConstraint.OperatorType.BETWEEN,
                                DateUtil.getExcelDate(sdf.parse("2018/7/25"))+"",
                                DateUtil.getExcelDate(sdf.parse("2018/10/25"))+"", "yyyy/MM/dd");

                validation1Date = validationHelper.createValidation(dateConstraint,dateConstraintRegions);
            }

            validation1Date.createErrorBox("警告","时间超出范围{2018/8/22至2018/10/22}");
            validation1Date.setShowErrorBox(true);
            validation1Date.setEmptyCellAllowed(true);// 忽略为空的列
            sheet.addValidationData(validation1Date);

            // dataValidationHelper.createFormulaListConstraint("A2"); 公式
            // DataValidationConstraint constraint = dataValidationHelper.createCustomConstraint("A2:A3");自定义
            // validationHelper.createIntegerConstraint(DataValidationConstraint.OperatorType.BETWEEN, "10", "100")
            // createDateConstraint(OperatorType.EQUAL,"2014/10/25", null, null); // 相等？
            // createNumericConstraint OperatorType 为 1 2 6 才能操作
            // validationHelper.createNumericConstraint(DataValidationConstraint.OperatorType.BETWEEN,ComparisonOperator.BETWEEN,"200","300");

            // 合并单元格 参数说明：合并开始的行，合并结束的行，合并开始的列，合并结束的列;合并单元格实际上是声明一个区域,合并后的内容与样式以该区域最左上角的单元格为准。
            CellRangeAddress region=new CellRangeAddress(0, 0, 0, 5);//合并列
            region=new CellRangeAddress(0, 5, 6, 6);// 合并行
            sheet.addMergedRegion(region);

            // 设置打印页
            PrintSetup printSetup = sheet.getPrintSetup();// 得到打印对象
            printSetup.setPaperSize(PrintSetup.A4_EXTRA_PAPERSIZE);// 页面大小
            //printSetup.setFitWidth((short)2);//设置页宽
            //printSetup.setFitHeight((short)4);//设置页高
            printSetup.setNotes(true);//设置打印批注
            printSetup.setLandscape(false);//true，则表示页面方向为横向；否则为纵向
            printSetup.setScale((short)80);//缩放比例80%(设置为0-

            Drawing draw = sheet.createDrawingPatriarch();
            /** 定义注释的大小和位置 http://poi.apache.org/apidocs/org/apache/poi/xssf/usermodel/XSSFComment.html
             * dx 只能取 0-1023 之间的数，dy 只能取 0-255 之 间的数。理解为是将单元格的宽和高平分成了 1023 和 255 份
             *  dx1和dy1 来确定 左上角的坐标点（col1和row1 定位的单元格）偏移的距离，dx1为距离左侧的距离。dy1为上侧的距 离
             * @param dx1  the x coordinate within the first cell. dx1 第1个单元格中x轴的偏移量
             * @param dy1  the y coordinate within the first cell. dy1 第1个单元格中y轴的偏移量
             * @param dx2  the x coordinate within the second cell. 第2个单元格中x轴的偏移量
             * @param dy2  the y coordinate within the second cell. 第2个单元格中y轴的偏移量
             *             左上角的坐标点（）,确定
             * @param col1 the column (0 based) of the first cell. 第一个单元的列（0为基础）
             * @param row1 the row (0 based) of the first cell. 第一行的行（0为基础）。
             *             右下角
             * @param col2 the column (0 based) of the second cell. 第二单元的列（0为基础）。
             * @param row2 the row (0 based) of the second cell. 第二个单元的行（0为基础）。
             */
            //draw.createPicture()
            Comment comment = null;

            RichTextString rtf = null;

            if(fileName.endsWith(XLS)){
                comment = draw.createCellComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 9, 7));
                rtf = new HSSFRichTextString("添加批注内容！");
            }else if(fileName.endsWith(XLSX)){
                comment = draw.createCellComment(new XSSFClientAnchor(0, 0, 0, 0,  4, 2,  9, 7));
                rtf = new XSSFRichTextString("添加批注内容！");
            }
            // 字体
            Font commentFormatter = workbook.createFont();
            // 字体设置样式
            //commentFormatter.setColor(IndexedColors.RED.getIndex());// 设置字体颜色
            //commentFormatter.setStrikeout(true);//设置删除线
            //commentFormatter.setUnderline(Font.U_SINGLE);//设置下划线:单下划线 U_SINGLE;双下划线 U_DOUBLE;无下划线 U_NON;会计用单下划线 U_SINGLE_ACCOUNTING;会计用双下划线 U_DOUBLE_ACCOUNTING
            commentFormatter.setTypeOffset(Font.SS_SUPER);// 上标 SS_SUPER;下标 SS_SUB;普通，默认值 SS_NONE
            commentFormatter.setFontName("宋体");
            //设置字体大小
            commentFormatter.setFontHeightInPoints((short) 9);//字号

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
                        cell.setCellValue(new Date());

                        // 单元格显示
                        //水平对齐： LEFT/FILL 左侧对齐/填充；CENTER 居中；RIGHT 右侧对齐； CENTER_SELECTION 跨列举中；JUSTIFY 两端对齐
                        style.setAlignment(HorizontalAlignment.LEFT);//水平居中
                        // 垂直对齐相关参数 TOP 靠上;CENTER 居中; BOTTOM 靠下 ;JUSTIFY 两端对齐
                        style.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
                        style.setWrapText(true);//自动换行
                        style.setIndention((short)5);//缩进
                        style.setRotation((short)60);//文本旋转，这里的取值是从-90到90，而不是0-180度。


                        // 边框设置(去掉以前老CellStyle的常量变量名的BORDER_前缀)；边框样式参考https://www.cnblogs.com/huajiezh/p/5467821.html 边框
              /*          style.setBorderTop(BorderStyle.DOTTED);//上边框
                        style.setBorderBottom(BorderStyle.THICK);//下边框
                        style.setBorderLeft(BorderStyle.DOUBLE);//左边框
                        style.setBorderRight(BorderStyle.SLANTED_DASH_DOT);//右边框
                        style.setTopBorderColor(IndexedColors.RED.index);//上边框颜色
                        style.setBottomBorderColor(IndexedColors.BLUE.index);//下边框颜色
                        style.setLeftBorderColor(IndexedColors.GREEN.index);//左边框颜色
                        style.setRightBorderColor(IndexedColors.PINK.index);//右边框颜色*/

                        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm")); //（new Date()）设置日期格式--使用Excel内嵌的格式
                        //style.setDataFormat(dataFormat.getFormat("0.00"));//（12.3456789）设置保留2位小数--使用Excel内嵌的格式
                        // style.setDataFormat(workbook.createDataFormat().getFormat("￥#,##0")); //（12345.6789） 设置货币格式--使用自定义的格式
                        // style.setDataFormat(workbook.createDataFormat().getFormat("0.00%"));// （0.123456789）设置百分比格式--使用自定义的格式
                        //style.setDataFormat(dataFormat.getFormat("[DbNum2][$-804]0"));// （12345）设置中文大写格式--使用自定义的格式
                        cell.setCellStyle(style);
                        //style.setFillForegroundColor(IndexedColors.BLUE.getIndex());//颜色
                        //style.setFillPattern(FillPatternType.SOLID_FOREGROUND);// 图案样式
                        comment.setAddress(rownum,cellnum);
                        cell.setCellComment(comment);
                        // ("sum(A1,C1)");//等价于"A1+C1"  ("sum(B1:D1)");//等价于"B1+C1+D1"
                        //cell.setCellFormula("COUNTIF(A1:F1,\">=60\")"); // 设置列的公式("IF(A1>B1,\"A1大于B1\",\"A1小于等于B1\")");("UPPER(A1)")("PROPER(B1)");
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
     * 正常读取excel文件（
     * @throws IOException
     */
    public static void readExcel() throws IOException{
        String[][] data = null;
        String fileName = "sxssf.xlsx";
        String path = "D:\\data\\";
        File excelFile = new File(path+fileName);
        try(InputStream is = new FileInputStream(excelFile);
            Workbook workbook = getWorkBook(fileName,false,is);
        ){
            // for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){ // 编号循环sheet
            Iterator<Sheet> sheets =  workbook.sheetIterator();
            if(sheets.hasNext()){
                //获得当前sheet的开始行
                Sheet firstRowNum  = workbook.getSheetAt(0);
                int rowLastIndex = firstRowNum.getLastRowNum();
                short cellLastIndex = firstRowNum.getRow(firstRowNum.getFirstRowNum()).getLastCellNum();
                data = new String[rowLastIndex+1][cellLastIndex+1];
            }
            while (sheets.hasNext()){
                Sheet sheet = sheets.next();
                // Iterator<Row> rowItem = sheet.rowIterator();// 迭代器获取全部的行数据
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                int firstRowNum = sheet.getFirstRowNum();// 获取列的长度
                //循环除了第一行的所有行（firstRowNum+1）
                for(int rowNum = firstRowNum;rowNum <= lastRowNum;rowNum++){
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                    //Iterator<Cell> cells = row.cellIterator();
                    short firstCellIndex = row.getFirstCellNum();
                    short lastCellIndex = row.getLastCellNum();
                    for(int cellNum = firstCellIndex;cellNum <= lastCellIndex;cellNum++){
                        data[rowNum][cellNum] = getCellFormatValue(row.getCell(cellNum)).toString();
                    }
                }
            }
        }
        // 通过flatMap 扁平化每一个元素
        String dataStr = Arrays.stream(data)
                .peek(p -> { System.out.println("每行数据="+ Arrays.stream(p).collect(Collectors.joining("-","[","]")));})
                .flatMap(subItem->Arrays.stream(subItem)).collect(Collectors.joining(","));
        //System.out.println(dataStr);
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
            // 列需要设置格式style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));才能识别
            System.out.println(DateUtil.isCellDateFormatted(cell));//输出：false
            cellValue = cell.getNumericCellValue();//double
        }else if (type.equals(CellType.STRING)){// 字符串
            cellValue = cell.getStringCellValue();
        }else if (type.equals(CellType.BOOLEAN)){// 布尔类型
            cellValue = cell.getCellStyle().getDataFormatString();// 获取样式
        }else if (type.equals(CellType.FORMULA)){// 公式
            System.out.println("公式计算结果："+cell.getNumericCellValue());
        }else if (type.equals(CellType.BLANK)){//空单元。没值，但有单元格样式

        }else if (type.equals(CellType.ERROR)){// 错误单元格

        }

        return cellValue;
    }


    /**
     * 判断单元格在不在合并单元格范围内，如果是，获取其合并的列数。(修改成合并字符串)，返回null 表示不是合并单元格
     */
    private static String getMergerCellRegionCol(Sheet sheet, int cellRow, int cellCol) throws Throwable {
        String retVal = null;
        int regionRowNum = 0;
        int regionCellNum = 0;
        int sheetMergerCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergerCount; i++) {
            CellRangeAddress cra = sheet.getMergedRegion(i);
            int firstRow = cra.getFirstRow(); // 合并单元格CELL起始行
            int firstCol = cra.getFirstColumn(); // 合并单元格CELL起始列
            int lastRow = cra.getLastRow(); // 合并单元格CELL结束行
            int lastCol = cra.getLastColumn(); // 合并单元格CELL结束列
            if (cellRow >= firstRow && cellRow <= lastRow) { // 判断该单元格是否是在合并单元格中
                if (cellCol >= firstCol && cellCol <= lastCol) {
                    regionCellNum = lastCol - firstCol + 1; // 得到合并的列数
                    regionRowNum = lastRow - firstRow + 1; // 得到合并的行数
//                    break;
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstCol);
                    return getCellFormatValue(fCell).toString();
                }
            }
        }
        return retVal;
    }

    /**
     * 判断指定的单元格是否是合并单元格
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    public static boolean isMergedRegion(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    return true;
                }
            }
        }
        return false;
    }
}
