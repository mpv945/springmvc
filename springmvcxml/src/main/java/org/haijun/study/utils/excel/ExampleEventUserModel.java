package org.haijun.study.utils.excel;

import org.apache.poi.openxml4j.opc.OPCPackage;
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
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class ExampleEventUserModel {

    private String filename;
    private XSSFSheetXMLHandler.SheetContentsHandler handler = new XSSFSheetXMLHandler.SheetContentsHandler() {
        @Override
        public void startRow(int i) {

        }

        @Override
        public void endRow(int i) {

        }

        @Override
        public void cell(String s, String s1, XSSFComment xssfComment) {

        }

        @Override
        public void headerFooter(String s, boolean b, String s1) {

        }
    };

    public ExampleEventUserModel(String filename) {
        this.filename = filename;
    }

    public XSSFSheetXMLHandler.SheetContentsHandler setHandler(XSSFSheetXMLHandler.SheetContentsHandler handler) {
        this.handler = handler;
        return this.handler;
    }

    public void processOneSheet(String filename) throws Exception {
        OPCPackage pkg = OPCPackage.open(filename);
        XSSFReader r = new XSSFReader( pkg );
        SharedStringsTable sst = r.getSharedStringsTable();
        StylesTable styles = r.getStylesTable();
        XMLReader parser = processSheet(styles,new ReadOnlySharedStringsTable(pkg));

        // To look up the Sheet Name / Sheet Order / rID,
        //  you need to process the core Workbook stream.
        // Normally it's of the form rId# or rSheet#
        InputStream sheet2 = r.getSheet("rId2");
        InputSource sheetSource = new InputSource(sheet2);
        parser.parse(sheetSource);
        sheet2.close();
    }

    public void processAllSheets(String filename) throws Exception {
        OPCPackage pkg = OPCPackage.open(filename);
        XSSFReader r = new XSSFReader( pkg );
        //SharedStringsTable sst = r.getSharedStringsTable();
        StylesTable styles = r.getStylesTable();
        XMLReader parser = processSheet(styles,new ReadOnlySharedStringsTable(pkg));

        Iterator<InputStream> sheets = r.getSheetsData();
        while(sheets.hasNext()) {
            System.out.println("Processing new sheet:\n");
            InputStream sheet = sheets.next();
            InputSource sheetSource = new InputSource(sheet);
            parser.parse(sheetSource);
            sheet.close();
            System.out.println("");
        }
    }

    public XMLReader processSheet(StylesTable styles, ReadOnlySharedStringsTable strings)
            throws SAXException, ParserConfigurationException, IOException {
        XMLReader sheetParser = SAXHelper.newXMLReader();
        ContentHandler handler1 = new XSSFSheetXMLHandler(styles, strings, handler,false);
        sheetParser.setContentHandler(handler1);
        return sheetParser;
        //sheetParser.parse(new InputSource(sheetInputStream));
    }

    public static void main(String[] args) throws Exception {
        ExampleEventUserModel example = new ExampleEventUserModel("");
        example.processOneSheet(args[0]);
        //example.processAllSheets(args[0]);
    }
}
