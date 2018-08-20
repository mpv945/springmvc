package org.haijun.study.utils.excel;

import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;

public class ExcelUtils {


    // https://github.com/jeevatkm/excelReader/blob/master/src/main/java/com/myjeeva/poi/ExcelReader.java
    // https://blog.csdn.net/sai739295732/article/details/68489403
    // https://blog.csdn.net/ylforever/article/details/80955595
    // http://www.iteye.com/topic/624969
    // https://www.cnblogs.com/hhhshct/p/7255915.html
    private XSSFSheetXMLHandler.SheetContentsHandler sheetContentsHandler;

    /**

     * Parses the content of one sheet using the specified styles and shared-strings tables.
     *
     * @param styles a {@link StylesTable} object
     * @param sharedStringsTable a {@link ReadOnlySharedStringsTable} object
     * @param sheetInputStream a {@link InputStream} object
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    private void readSheet(StylesTable styles, ReadOnlySharedStringsTable sharedStringsTable,
                           InputStream sheetInputStream) throws IOException, ParserConfigurationException, SAXException {
        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        XMLReader sheetParser = saxFactory.newSAXParser().getXMLReader();
        ContentHandler handler = new XSSFSheetXMLHandler(styles, sharedStringsTable, sheetContentsHandler, true);
        sheetParser.setContentHandler(handler);
        sheetParser.parse(new InputSource(sheetInputStream));
    }
}
