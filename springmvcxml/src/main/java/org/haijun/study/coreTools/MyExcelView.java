package org.haijun.study.coreTools;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxStreamingView;
import org.xml.sax.ContentHandler;
import org.xml.sax.XMLReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.SAXParserFactory;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class MyExcelView extends AbstractXlsxStreamingView {

    // 参考https://blog.csdn.net/wang124454731/article/details/73850645
    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String excelName = map.get("name").toString() + ".xlsx";
        String Agent = request.getHeader("User-Agent");
        if (null != Agent) {
            Agent = Agent.toLowerCase();
            if (Agent.indexOf("firefox") != -1) {
                response.setHeader("content-disposition", String.format("attachment;filename*=utf-8'zh_cn'%s", URLEncoder.encode(excelName, "utf-8")));

            } else {
                response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(excelName, "utf-8"));
            }
        }

        response.setContentType("application/ms-excel; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        @SuppressWarnings("unchecked")
        //List<ExportMemberVo> list = (List<ExportMemberVo>) map.get("members");
                List<String> list = new ArrayList<>();
        list.add("sdfs");
        Sheet sheet = workbook.createSheet("User Detail");
        sheet.setDefaultColumnWidth(30);
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.index);
        style.setFont(font);
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("姓名");
        header.getCell(0).setCellStyle(style);
        header.createCell(1).setCellValue("性别");
        header.getCell(1).setCellStyle(style);
        header.createCell(2).setCellValue("手机号");
        header.getCell(2).setCellStyle(style);
        header.createCell(3).setCellValue("身份证号");
        header.getCell(3).setCellStyle(style);
        header.createCell(4).setCellValue("银行卡号");
        header.getCell(4).setCellStyle(style);
        int rowCount = 1;
        for (String user : list) {
            Row userRow = sheet.createRow(rowCount++);
            userRow.createCell(0).setCellValue("测试" + rowCount);
            userRow.createCell(1).setCellValue("测试" + rowCount);
            userRow.createCell(2).setCellValue("测试" + rowCount);
            userRow.createCell(3).setCellValue("测试" + rowCount);
            userRow.createCell(4).setCellValue("测试" + rowCount);
        }
    }
}
