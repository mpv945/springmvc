package org.haijun.study.utils.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class CSVUtils {

    /**
     * CSVParser（解析）
     * @param filePath
     * @return
     * @throws IOException
     */
    public static CSVParser getCSVParser(String filePath) throws IOException {
        CSVFormat format = CSVFormat.DEFAULT.withHeader();
        InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath), "UTF-8");
        return new CSVParser(isr, format);
    }

    /**
     * CSVPrinter（写csv）
     * @param filePath
     * @return
     * @throws IOException
     */
    public static CSVPrinter getCSVPrinter(String filePath) throws IOException {
        CSVFormat format = CSVFormat.DEFAULT.withRecordSeparator("\n");
        // 获取换行符
        String huanhang = IOUtils.LINE_SEPARATOR;
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8");
        return new CSVPrinter(osw, format);
    }

    public static void main(String[] args) {
        // 跨平台空格;File.pathSeparator指的是分隔多个路径字符串的分隔符
        String s = File.separator;
        try(CSVParser parser = CSVUtils.getCSVParser("D://demo01.csv");

            CSVPrinter printer=CSVUtils.getCSVPrinter("D://demo02.csv");){ // implements AutoClosable 实现了这个接口的close()方法都可以在try-with-resources结构中使用
            Iterator<CSVRecord> iterator = parser.iterator();
            printer.printRecord(parser.getHeaderMap().keySet());//写CSV第一行
            while(iterator.hasNext()) {
                // 集合对象方式
                //List<String[]> data = ...
                //data.add(new String[]{ "A", "B", "C" });
                // 可以使用object 二维数组的参数
                //String[][] data = new String[3][]
                //data[0] = String[]{ "A", "B", "C" };
                printer.printRecord(iterator.next());// 迭代器方式
            }
            // commons.io
            //IOUtils.closeQuietly(parser);
            //IOUtils.closeQuietly(printer);
        }catch (IOException e){
            System.out.println("读写csv失败");
        }
    }

    /**
     * 解析csv文件
     * @throws IOException
     */
    public void csvParser(String path) throws IOException{
        try(//Reader in = new FileReader("path/to/file.csv");

            Reader in = new InputStreamReader(new FileInputStream(path),"UTF-8");// 设置编码
        ){
            //CSVParser parser = CSVParser.parse(new File(""),StandardCharsets.UTF_8,CSVFormat.DEFAULT);
            //parser.getRecords();

            //Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);// 默认自动带有Header信息
            //Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);// EXCEL和RFC4180为编码方式，withFirstRecordAsHeader表示默认第一行为header的方式读取
            // Iterable<CSVRecord> records = CSVFormat.RFC4180.withHeader("ID", "CustomerNo", "Name").parse(in);//指定头部信息字段名称，下面record就可以record.get("ID");获取每列的值
            Iterable<CSVRecord> records = CSVFormat.RFC4180.withHeader(Headers.class).parse(in);//使用枚举类型类定义Header标签
            for (CSVRecord record : records) {
                String columnOne = record.get(0);// 下标方式读取
                // String id = record.get("ID");
                String customerNo = record.get("CustomerNo");
            }
        }

    }

    public enum Headers {
        ID, CustomerNo, Name
    }
}
