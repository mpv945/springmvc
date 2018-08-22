package org.haijun.study.utils.word;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import fr.opensagres.poi.xwpf.converter.core.ImageManager;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLConverter;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

public class Word2Html {

    public static void main(String[] args) {
        try {
            convert();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void convert() throws Exception{
        String filepath = "D:\\data\\";
        String docfileName = "idea.docx";
        String picturesPath = filepath+"image\\";

        String fileOutName = filepath + "/" + docfileName + ".html";

        String filePdfName = filepath + "/" + docfileName + ".pdf";

        String filePdfName2 = filepath + "/"  + "new.pdf";

        //File picturesDir = new File(picturesPath);
        String content = null;

        InputStream in = new FileInputStream(new File(filepath+docfileName));
        //web 项目读取方式
        //XWPFDocument document = new XWPFDocument( Word2Html.class.getResourceAsStream( fileInName ) );
        XWPFDocument document = new XWPFDocument(in);

        // docx转html （解析XHTML配置，（这里设置setImageManager来设置图片存放的目录)
        XHTMLOptions options = XHTMLOptions.create();
        // Extract image
        options.setImageManager( new ImageManager( new File(filepath), "images" ) );
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XHTMLConverter.getInstance().convert( document, baos, options );
       /* Options options1 = Options.getFrom(DocumentKind.DOCX);
        options1.setProperty("___URIResolver",picturesPath);
        XWPF2XHTMLConverter.getInstance().convert( new FileInputStream(filepath+docfileName),
                new FileOutputStream(fileOutName),
                options1);*/
        content = baos.toString();
        baos.close();
        document.close();
        in.close();

        // Jsoup 把<br/> 格式成 <brL>
       /* Document doc = Jsoup.parse(content);
        // 打开docx结尾的word转化的html文件，发现body中有一个或多个div包裹内层标签
        // 这些div设置了style的width值，width过大导致转换的pdf空白。
        // 用的方法最简单粗暴，在jsoup格式化完成后找到style然后替换为空。
        Element body =  doc.body();
        String style = doc.body().attr("style");
        if(!StringUtils.isEmpty(style) && style.indexOf("width") >= 0){
            body.attr("style","");
        }
        Elements divs = doc.select("div");
        for(int i=0;i<divs.size();i++){
            Element div = divs.get(i);
            style = div.attr("style");
            if(!StringUtils.isEmpty(style) && style.indexOf("width") >= 0){
                div.attr("style","");
            }
        }
        content = doc.html();*/
        FileOutputStream fos = new FileOutputStream(new File(fileOutName));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos,"UTF-8"));
        bw.write(content);
        bw.close();
        fos.close();

        // docx转pdf
        File outFile = new File(filePdfName);
        try (OutputStream out = new FileOutputStream(outFile)) {
            PdfOptions pdfOptions = PdfOptions.getDefault();//PdfOptions.create();
            PdfConverter.getInstance().convert(document, out, pdfOptions);
        }

        XMLWorkerFontProvider myfont = new XMLWorkerFontProvider(){
            @Override
            public Font getFont(String fontname, String encoding, boolean embedded,
                                float size, int style, BaseColor color) {
                BaseFont bf = null;
                try {
                    bf = BaseFont.createFont("D:\\data\\simsunb.ttf", //C:/Windows/Fonts 拷贝到别的磁盘引用
                            BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Font font = new Font(bf,size, style, color);
                font.setColor(color);
                //return super.getFont(fontname, encoding, embedded, size, style, color);
                return font;
            }
        };
        com.itextpdf.text.Document document1 = new com.itextpdf.text.Document();
        PdfWriter writer = PdfWriter.getInstance(document1,new FileOutputStream(filePdfName2));
        document1.open();
        URL url = new URL("https://www.cnblogs.com/shuilangyizu/p/6595588.html");
        XMLWorkerHelper.getInstance().parseXHtml(writer,document1,
                new FileInputStream(fileOutName),
                //url.openStream(),
                //XMLWorkerHelper.class.getResourceAsStream("/dd.css"),
                Charset.forName("UTF-8"),
                myfont);
        document1.close();
        writer.close();
    }

    // itext7学习笔记 https://blog.csdn.net/u012397189/article/details/77540464（有好几章）
}
