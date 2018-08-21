package org.haijun.study.utils.excel;

import com.alibaba.fastjson.JSON;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SimpleSheetContentsHandler implements XSSFSheetXMLHandler.SheetContentsHandler {

    private List<String> cellName;

    private List<Map<String, Object>> data;

    private Map<String, String> headerData;

    private int skipRows = 0;

    private boolean isReadHeader;

    private int currentIndex;


    /**
     * type 实体类型，列字段，跳过的行数
     * @param cellName 返回数据类型
     * @param skipRows
     * @param isReadHeader
     */
    public SimpleSheetContentsHandler(Enum[] cellName, int skipRows, boolean isReadHeader){
        this.cellName =  Arrays.stream(cellName).map(emj -> emj.toString()).collect(Collectors.toList());
        this.skipRows = skipRows;
        this.isReadHeader = isReadHeader;

    }

    /**
     * 行号 (以零为基础) 的行已开始
     * @param i
     */
    @Override
    public void startRow(int i) {
        System.out.println("开始的行下标="+i);
        currentIndex = i;
    }

    /**
     * 行号 (以零为基础) 的行已结束
     * @param i
     */
    @Override
    public void endRow(int i) {
        System.out.println("结束的行下标="+i);
        currentIndex = i;
    }

    /**
     * 遇到给定格式化值 (可能为 null) 的单元格, 并且可能会出现注释 (可能为 null)。
     * @param cellReference
     * @param formattedValue
     * @param xssfComment
     */
    @Override
    public void cell(String cellReference, String formattedValue, XSSFComment xssfComment) {
        System.out.println("处理的列，当前下标="+currentIndex+"，其中s="+cellReference+",  " +
                "s1="+formattedValue +", XSSFComment="+ JSON.toJSONString(xssfComment));
    }

    /**
     * 遇到页眉或页脚
     * @param text
     * @param isHeader
     * @param tagName
     */
    @Override
    public void headerFooter(String text, boolean isHeader, String tagName) {
        System.out.println("页眉或页脚"+text+"   "+isHeader+"     "+tagName);
    }



}
