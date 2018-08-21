package org.haijun.study.utils.excel;

import com.alibaba.fastjson.JSON;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 基于xml 处理的内容处理（效率最高就全部解析，使用String [][] 二维数组或者List<String []>）
 */
public class SimpleSheetContentsHandler implements XSSFSheetXMLHandler.SheetContentsHandler {

    private List<String> cellName;

    private List<Map<String, Object>> data;

    private Map<String, Object> headerData;

    private boolean isReadHeader;

    private int currentIndex; // 行下标

    private Map<String, Object> itemTemp;

    private int cellsIndex = 0; // 列下标


    /**
     * type 实体类型，列字段，跳过的行数
     * @param cellName 返回数据类型
     * @param isReadHeader
     */
    public SimpleSheetContentsHandler(Enum[] cellName, boolean isReadHeader){
        data = new ArrayList<>();
        this.cellName =  Arrays.stream(cellName).map(emj -> emj.toString()).collect(Collectors.toList());
        this.isReadHeader = isReadHeader;

    }

    /**
     * 行号 (以零为基础) 的行已开始
     * @param i
     */
    @Override
    public void startRow(int i) {
        // 如果需要读取头部信息，跳过第0行开始的数据
        if(isReadHeader && i==0 ){
            headerData = new HashMap<>();
        }else {
            itemTemp = new HashMap<>();
        }
        // 标记位置
        currentIndex = i;
    }

    /**
     * 行号 (以零为基础) 的行已结束
     * @param i
     */
    @Override
    public void endRow(int i) {
        // 如果需要读取头部信息，跳过第0行开始的数据
        if(isReadHeader && i == 0 ){
            data.add(headerData); // 添加头部信息
            headerData = null;
        }else {
            data.add(itemTemp); // 每行数据读完，把item数据添加到总数据
            itemTemp = null;
        }
        cellsIndex = 0; // 列的位置
    }

    /**
     * 遇到给定格式化值 (可能为 null) 的单元格, 并且可能会出现注释 (可能为 null)。
     * @param cellReference 列的索引位置
     * @param formattedValue 列的值
     * @param xssfComment 列的备注信息
     */
    @Override
    public void cell(String cellReference, String formattedValue, XSSFComment xssfComment) {
        // 如果需要读取头部信息，跳过第0行开始的数据
        if(isReadHeader && currentIndex ==0 ){
            headerData.put(cellName.get(cellsIndex),formattedValue);
        }else {
            itemTemp.put(cellName.get(cellsIndex),formattedValue);
        }
        cellsIndex++;
    }

    /**
     * 遇到页眉或页脚
     * @param text
     * @param isHeader 是否是页面
     * @param tagName
     */
    @Override
    public void headerFooter(String text, boolean isHeader, String tagName) {
        System.out.println("页眉或页脚"+text+"   "+isHeader+"     "+tagName);
    }

    /**
     * 返回解析的数据集合
     * @return
     */
    public List<Map<String, Object>> getData(){
        return this.data;
    }

}
