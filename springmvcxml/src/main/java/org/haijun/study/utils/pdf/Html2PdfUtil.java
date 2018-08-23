package org.haijun.study.utils.pdf;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.BlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.font.FontProvider;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * 暂时无效果
 */
public class Html2PdfUtil {
    public static final float topMargin = 114f;
    public static final float bottomMargin = 156f;
    public static final float leftMargin = 90f;
    public static final float rightMargin = 90f;
    public static byte[] convert(String html) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        try {
            ConverterProperties props = new ConverterProperties();
            FontProvider fp = new FontProvider();
            fp.addStandardPdfFonts();
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            fp.addDirectory(classLoader.getResource("fonts").getPath());
            props.setFontProvider(fp);
            // List<IElement> iElements = HtmlConverter.convertToElements(html, props); // html文本形式
            List<IElement> iElements = HtmlConverter.convertToElements(new URL(html).openStream(), props); // 网络形式
            Document document = new Document(pdfDocument, PageSize.A4, true); // immediateFlush设置true和false都可以，false 可以使用 relayout
            document.setMargins(topMargin, rightMargin, bottomMargin, leftMargin);
            for (IElement iElement : iElements) { // 控制间距
                BlockElement blockElement = (BlockElement) iElement;
                blockElement.setMargins(1, 0, 1, 0);
                document.add(blockElement);
            }
            document.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw e;
        } finally {
            outputStream.close();
        }
    }

    public static void main(String[] args) {
        try {
            FileOutputStream fos = new FileOutputStream("D:\\data\\bianju.pdf");
            fos.write(convert("http://localhost:8081"));
            fos.close();
        }catch (Exception e){

        }

    }
}
