package org.haijun.study.coreTools;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfDate;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

// 具体参考https://www.xuebuyuan.com/3249472.html
public class PdfTest {
    // 如果没有模板，就行自己生成pdf文件保存到磁盘：下面的方法可以实现
    public static void main(String[] args) {
        BaseFont bf;
        Font font = null;
        try {
            // // 中文字体
//      bf = BaseFont.createFont("font/simsun.ttc,1", //注意这里有一个,1
//            BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);//宋体文字
        bf = BaseFont.createFont("C:/Windows/Fonts/STSONG.TTF", //simsun.ttc //存在当前项目资源目录下
            BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);//华康少女文字
            font = new Font(bf,12);
        } catch (Exception  e) {
            e.printStackTrace();
        }
        // 生成一个默认属性的PDF文件
        Document document = new Document(PageSize.A4);//PageSize.A4.rectangle()
        //document.setMargins(36, 72, 108, 180);
        //document.setMarginMirroringTopBottom(true)
        try {
            // 定义一个输出流，将内容写入到filename中
            PdfWriter.getInstance(document, new FileOutputStream("D:/3.pdf")).setPageEvent(new SdkPdfPageEvent());
            // 开始写文件
            document.open();
            // //右键    文件属性 pdf 可以看到以下内容
            document.addAuthor("谢海军");
            document.addSubject("SDK附属API文档");
            document.addTitle("API文档");
            document.addKeywords("iText") ;
            //document.add(new Paragraph("上善若水",font));
            document.add(new Paragraph("善若水54354335435345",font));

            // Paragraph类：类名翻译过来是“段落”
            Paragraph to = new Paragraph("亲爱的李雷：", font);
            document.add(to);

            Paragraph hello = new Paragraph("你好！当前时间="+new PdfDate().toString(), font);
            hello.setIndentationLeft(24); // 整体缩进
            document.add(hello);

            Paragraph content = new Paragraph("我现在正在学习iText，正好写一封信给你，为了整点内容凑个换行，我这个不怎么擅长写作文的人也是拼了。", font);
            content.setFirstLineIndent(24); // 首行缩进
            document.add(content);

            Paragraph from = new Paragraph("韩梅梅\n2017年11月29日", font);
            from.setAlignment(Element.ALIGN_RIGHT); // 居右显示
            document.add(from);
            document.close();
        } catch (Exception e) {
            System.out.println("file create exception");
        }
    }
}
