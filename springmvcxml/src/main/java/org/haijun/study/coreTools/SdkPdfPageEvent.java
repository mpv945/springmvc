package org.haijun.study.coreTools;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SdkPdfPageEvent extends PdfPageEventHelper {

    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        // 水印(water mark)
        PdfContentByte pcb = writer.getDirectContent();
        pcb.saveState();
        BaseFont bf;
        try {
            bf = BaseFont.createFont("C:/Windows/Fonts/STSONG.TTF",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);;
            pcb.setFontAndSize(bf, 36);
        } catch (DocumentException e) {
            e.printStackTrace();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 透明度设置
        PdfGState gs = new PdfGState();
        gs.setFillOpacity(0.2f);
        pcb.setGState(gs);

        pcb.beginText();
        pcb.setTextMatrix(60, 90);
        pcb.showTextAligned(Element.ALIGN_LEFT, "XX公司有限公司", 200, 300, 45);

        pcb.endText();
        pcb.restoreState();

    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        // 页眉、页脚
        PdfContentByte pcb = writer.getDirectContent();
        try {
            pcb.setFontAndSize(BaseFont.createFont("C:/Windows/Fonts/STSONG.TTF",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED), 10);
        } catch (Exception e) {
            e.printStackTrace();
        } // 支持中文字体
        pcb.saveState();
        try {
            // pcb.addImage()方法要在pcb.beginText();pcb.endText();之外调用，
            // 否则生成的PDF打开时会报错: An error exists on this page. Acrobat may not display the page correctly. Please contact the person who created the PDF document to correct the problem.
            byte[] logoBytes = new byte[1000 * 1024]; // 此处数组大小要比logo图片大小要大, 否则图片会损坏；能够直接知道图片大小最好不过.
            //InputStream logoIs = getClass().getResourceAsStream("log/xzlogo_logo.png");
            InputStream logoIs = new FileInputStream("C:\\Users\\zhenx\\Pictures\\xzlogo_logo.png");
            if (logoIs != null) {
                int logoSize = logoIs.read(logoBytes); // 尝试了一下，此处图片复制不完全，需要专门写个方法，将InputStream转换成Byte数组，详情参考org.apache.io.IOUtils.java的toByteArray(InputStream in)方法
                if (logoSize > 0) {
                    byte[] logo = new byte[logoSize];
                    System.arraycopy(logoBytes, 0, logo, 0, logoSize);
                    Image image = Image.getInstance(logo);// 如果直接使用logoBytes，并且图片是jar包中的话，会报图片损坏异常；本地图片可直接getInstance时候使用路径。
                    image.setAbsolutePosition(document.left(), document.top(-5)); // 设置图片显示位置
                    image.scalePercent(12);                                       // 按照百分比缩放
                    pcb.addImage(image);
                }
            } else System.err.println("logo input stream is null.");
        } catch (Exception e) {
            System.err.println(e);
        }
        pcb.beginText();

// Header
        float top = document.top(-15);
        pcb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "XX开放平台API文档", document.right(), top, 0);
// Footer
        float bottom = document.bottom(-15);
        pcb.showTextAligned(PdfContentByte.ALIGN_CENTER, "第 " + writer.getPageNumber() + " 页", (document.right() + document.left()) / 2, bottom, 0);
        pcb.endText();

        pcb.restoreState();
        pcb.closePath();
        // 水印(water mark)
        pcb = writer.getDirectContent();
        pcb.saveState();
        BaseFont bf;
        try {
            bf = BaseFont.createFont("C:/Windows/Fonts/STSONG.TTF",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            pcb.setFontAndSize(bf, 36);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 透明度设置
        PdfGState gs = new PdfGState();
        gs.setFillOpacity(0.2f);
        pcb.setGState(gs);

        pcb.beginText();
        pcb.setTextMatrix(60, 90);
        pcb.showTextAligned(Element.ALIGN_LEFT, "XX公司有限公司", 200, 300, 45);

        pcb.endText();
        pcb.restoreState();

    }
}
