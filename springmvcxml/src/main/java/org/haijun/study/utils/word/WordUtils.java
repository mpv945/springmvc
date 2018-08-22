package org.haijun.study.utils.word;

import org.apache.poi.POIXMLProperties;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordUtils {

    public static void main(String[] args) {
        try {
            wirteWord();// 写入
            //readWord();//读取
            //templateWrite();//模板占位替换写入
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入doc和docx
     * @throws Exception
     */
    public static void wirteWord() throws Exception{
        // 其核心是XWPFDocument。一个XWPFDocument代表一个docx文档，其可以用来读docx文档，也可以用来写docx文档。
        // XWPFDocument中主要包含下面这几种对象：
        // XWPFParagraph：代表一个段落。
        // XWPFRun：代表具有相同属性的一段文本。
        // XWPFTable：代表一个表格。
        // XWPFTableRow：表格的一行。
        // XWPFTableCell：表格对应的一个单元格。
        XWPFDocument doc = new XWPFDocument();// 创建Word文件

        XWPFParagraph p = doc.createParagraph();// 新建一个段落

        //String imgurl = (String)((Map<?, ?>) value).get("content");
   /*     String type = "png";
        int width = 480;
        int height = 640;
        // 给段落添加图片
        String blipId =  doc.addPictureData(new FileInputStream(new File("imgurl")),getPictureType("png"));
        CustomXWPFDocument.createPicture(blipId,doc.getNextPicNameNumber(getPictureType(type)), width, height,p);
*/
        p.setAlignment(ParagraphAlignment.CENTER);// 设置段落的对齐方式
        p.setBorderBottom(Borders.DOUBLE);//设置下边框
        p.setBorderTop(Borders.DOUBLE);//设置上边框
        p.setBorderRight(Borders.DOUBLE);//设置右边框
        p.setBorderLeft(Borders.DOUBLE);//设置左边框
        XWPFRun r = p.createRun();//创建段落文本
        r.setText("POI创建的Word段落文本");
        r.setBold(true);//设置为粗体
        r.setColor("FF0000");//设置颜色
        p = doc.createParagraph();// 新建一个段落
        r = p.createRun();
        r.setText("POI读写Excel功能强大、操作简单。测试段落占位符日期：${reportDate}");
        XWPFTable table= doc.createTable(3, 3);//创建一个表格
        table.getRow(0).getCell(0).setText("表格1 表格占位：${appleAmt}");
        table.getRow(1).getCell(1).setText("表格2 : ${bananaAmt}");
        table.getRow(2).getCell(2).setText("表格3 ; ${totalAmt}");
        FileOutputStream out = new FileOutputStream("D:\\data\\sample.docx");
        doc.write(out);
        out.close();
    }

    /**
     * 读取doc和docx
     * @throws Exception
     */
    public static void readWord() throws Exception{
        FileInputStream stream = new FileInputStream("D:\\data\\sample.doc");
        XWPFDocument doc = new XWPFDocument(stream);// 创建Word文件
        // 输出CoreProperties信息
        XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
        POIXMLProperties.CoreProperties coreProps = extractor.getCoreProperties();
        System.out.println(coreProps.getCategory());   //分类
        System.out.println(coreProps.getCreator()); //创建者
        System.out.println(coreProps.getCreated()); //创建时间
        System.out.println(coreProps.getTitle());   //标题

        List<XWPFPictureData> imgList =  doc.getAllPictures();// 获取图片
        for(XWPFPictureData img : imgList){
            System.out.println(img.getPictureType()+File.separator+img.suggestFileExtension()+File.separator+img.getFileName());
            byte[] imgData = img.getData();
            FileOutputStream fos = new FileOutputStream("D:\\data\\"+img.getFileName());
            fos.write(imgData);
            fos.close();
        }
        for(XWPFParagraph p : doc.getParagraphs())//遍历段落
        {
            System.out.println(p.getParagraphText());
        }
        for(XWPFTable table : doc.getTables())//遍历表格
        {
            CTTblPr pr = table.getCTTbl().getTblPr();
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    System.out.println(cell.getText());
                }
            }
        }
        doc.close();
        stream.close();
    }

    /**
     * 根据模板占位符替换写入
     */
    public static void templateWrite() throws Exception{
        Map<String, Object> params = new HashMap<>();
        params.put("reportDate", "2014-02-28");
        params.put("appleAmt", "100.00");
        params.put("bananaAmt", "200.00");
        params.put("totalAmt", "300.00");
        String filePath = "D:\\data\\sample.docx";
        InputStream is = new FileInputStream(filePath);
        XWPFDocument doc = new XWPFDocument(is);
        //替换段落里面的变量
        replaceInPara(doc, params);
        //替换表格里面的变量
        replaceInTable(doc, params);
        OutputStream os = new FileOutputStream("D:\\data\\templateWrite.docx");
        doc.write(os);
        os.close();
        is.close();
    }

    /**
     * 替换段落里面的变量
     * @param doc 要替换的文档
     * @param params 参数
     */
    private static void replaceInPara(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
        XWPFParagraph para;
        while (iterator.hasNext()) {
            para = iterator.next();
            replaceInPara(para, params);
        }
    }

    /**
     * 替换段落里面的变量
     * @param para 要替换的段落
     * @param params 参数
     */
    private static void replaceInPara(XWPFParagraph para, Map<String, Object> params) {
        List<XWPFRun> runs;
        Matcher matcher;
        if (matcher(para.getParagraphText()).find()) {
            runs = para.getRuns();
            for (int i=0; i<runs.size(); i++) {
                XWPFRun run = runs.get(i);
                String runText = run.toString();
                matcher = matcher(runText);
                if (matcher.find()) {
                    while ((matcher = matcher(runText)).find()) {
                        runText = matcher.replaceFirst(String.valueOf(params.get(matcher.group(1))));
                    }
                    //直接调用XWPFRun的setText()方法设置文本时，在底层会重新创建一个XWPFRun，把文本附加在当前文本后面，
                    //所以我们不能直接设值，需要先删除当前run,然后再自己手动插入一个新的run。
                    para.removeRun(i);
                    para.insertNewRun(i).setText(runText);
                }
            }
        }
    }

    /**
     * 替换表格里面的变量
     * @param doc 要替换的文档
     * @param params 参数
     */
    private static void replaceInTable(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFTable> iterator = doc.getTablesIterator();
        XWPFTable table;
        List<XWPFTableRow> rows;
        List<XWPFTableCell> cells;
        List<XWPFParagraph> paras;
        while (iterator.hasNext()) {
            table = iterator.next();
            rows = table.getRows();
            for (XWPFTableRow row : rows) {
                cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    paras = cell.getParagraphs();
                    for (XWPFParagraph para : paras) {
                        replaceInPara(para, params);
                    }
                }
            }
        }
    }

    /**
     * 正则匹配字符串
     * @param str
     * @return
     */
    private static Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }

    /***
     * 导出word 设置行宽
     * @param table
     * @param width
     */
    private  void setTableWidth(XWPFTable table,String width){
        CTTbl ttbl = table.getCTTbl();
        CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
        CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
        CTJc cTJc=tblPr.addNewJc();
        cTJc.setVal(STJc.Enum.forString("center"));
        tblWidth.setW(new BigInteger(width));
        tblWidth.setType(STTblWidth.DXA);
    }

    /***
     * 跨列合并
     * @param table
     * @param row 所合并的行
     * @param fromCell  起始列
     * @param toCell   终止列
     */
    private  void mergeCellsHorizontal(XWPFTable table, int row, int fromCell, int toCell) {
        for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
            XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
            if ( cellIndex == fromCell ) {
                // The first merged cell is set with RESTART merge value
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one, are set with CONTINUE
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
            }
        }
    }


    /***
     *  跨行合并
     * @param table
     * @param col  合并列
     * @param fromRow 起始行
     * @param toRow   终止行
     */
    private void mergeCellsVertically(XWPFTable table, int col, int fromRow, int toRow) {
        for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
            XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
            if ( rowIndex == fromRow ) {
                // The first merged cell is set with RESTART merge value
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one, are set with CONTINUE
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
            }
        }
    }

    /**
     * 根据图片类型，取得对应的图片类型代码
     * @param picType
     * @return int
     */
    private static int getPictureType(String picType){
        int res = XWPFDocument.PICTURE_TYPE_PICT;
        if(picType != null){
            if(picType.equalsIgnoreCase("png")){
                res = XWPFDocument.PICTURE_TYPE_PNG;
            }else if(picType.equalsIgnoreCase("dib")){
                res = XWPFDocument.PICTURE_TYPE_DIB;
            }else if(picType.equalsIgnoreCase("emf")){
                res = XWPFDocument.PICTURE_TYPE_EMF;
            }else if(picType.equalsIgnoreCase("jpg") || picType.equalsIgnoreCase("jpeg")){
                res = XWPFDocument.PICTURE_TYPE_JPEG;
            }else if(picType.equalsIgnoreCase("wmf")){
                res = XWPFDocument.PICTURE_TYPE_WMF;
            }
        }
        return res;
    }
}
