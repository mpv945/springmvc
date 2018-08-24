package org.haijun.study.utils.pdf;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.font.FontInfo;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.font.FontSet;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import java.io.*;
import java.net.URL;
import java.util.Comparator;
import java.util.Optional;

/**
 * html 转 pdf
 */
public class Itext7HtmlToPdf {

    public static void main(String[] args) {
        try {
            simpleDome();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void simpleDome() throws Exception {

        // 指定生成的源
        URL url = new URL("http://www.runoob.com/linux/linux-install.html");

        PdfDocument doc = null;
        // 第一种 指定生成pdf路径
        try(FileOutputStream fos = new FileOutputStream(new File("D:\\data\\output.pdf"));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos,"UTF-8"));
            PdfWriter pdfWriter = new PdfWriter(fos);){

            doc = new PdfDocument(pdfWriter);

            // 第二种 指定生成pdf路径
            //doc = new PdfDocument(new PdfWriter("/xx/xx/output.pdf"));

            // 设置页面大小
            doc.setDefaultPageSize(PageSize.A4);

            // 声明转换配置
            ConverterProperties properties = new ConverterProperties();
            // 声明字体 提供解析用的字体
            FontProvider font = new FontProvider();
            font.addSystemFonts();// 添加系统字体（font.addFont("/xx/xx/msyh.ttf");//你的字体文件）
            //ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            //font.addDirectory(classLoader.getResource("fonts").getPath()); // 自定义字体路径、解决中文,可先用绝对路径测试。

            properties.setFontProvider(font);// 设置字体到配置文件
            // 获取字体列表中的第一个字体信息
            //Optional<FontInfo> fontinfo = font.getFontSet().getFonts().stream().sorted(Comparator.comparing(FontInfo::getFontName).reversed()).findFirst();// .sorted(Comparator.reverseOrder())
            Optional<FontInfo> fontinfo = font.getFontSet().getFonts().stream().filter(fontIn -> { String name = fontIn.getFontName();return name.toUpperCase().indexOf("YH")>=0;}).findFirst();
            //font.getFontSet().getFonts().forEach(System.out::println); // 循环打印加载的字体
            //Optional<FontInfo> fontinfo = font.getFontSet().get();

           /* Header headerHandler = new Header();// 页眉
            Footer footerHandler = new Footer();// 页脚
            doc.addEventHandler(PdfDocumentEvent.START_PAGE, headerHandler);
            doc.addEventHandler(PdfDocumentEvent.END_PAGE, footerHandler);

            // 水印
            WatermarkingEventHandler watermarkingEventHandler = new WatermarkingEventHandler();
            doc.addEventHandler(PdfDocumentEvent.INSERT_PAGE, watermarkingEventHandler);*/

           // 设定画布大小
            SetPageSize setPageSize = new SetPageSize();
            doc.addEventHandler(PdfDocumentEvent.START_PAGE,setPageSize);

            // 指定字体 生成页脚等
           /* doc.addEventHandler(PdfDocumentEvent.END_PAGE, new IEventHandler() {
                @Override
                public void handleEvent(Event event) {
                    PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
                    PdfDocument pdf = docEvent.getDocument();
                    PdfPage page = docEvent.getPage();
                    Rectangle pageSize = page.getPageSize();
                    PdfCanvas pdfCanvas = new PdfCanvas(
                            page.getLastContentStream(), page.getResources(), pdf);
                    Canvas canvas = new Canvas(pdfCanvas, pdf, pageSize);
                    float x = (pageSize.getLeft() + pageSize.getRight()) / 2;
                    float y = pageSize.getBottom() + 15;
                    try {
                        Paragraph p =  new Paragraph();
                        fontinfo.ifPresent(fontInfo -> p.setFont(font.getPdfFont(fontInfo)));// 指定字体
                        p.add("第"+pdf.getPageNumber(page)+"页");
                        canvas.showTextAligned(p, x, y, TextAlignment.CENTER);
                        canvas.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });*/

            HtmlConverter.convertToPdf(url.openStream(),doc,properties);// 无法灵活设置页边距等
            //changePageSize(doc);
        }
    }

    /**
     * 改变页面大小和方向
     */
    protected static class SetPageSize implements IEventHandler {
        @Override
        public void handleEvent(Event event) {
            float margin = 22;// 间距
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdf = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            // 改变每页大小
            Rectangle mediaBox = page.getMediaBox();
            Rectangle newMediaBox = new Rectangle(mediaBox.getLeft() - margin, mediaBox.getBottom() - margin,
                    mediaBox.getWidth() + margin * 2, mediaBox.getHeight() + margin * 2);
            page.setMediaBox(newMediaBox);
            // add border（我们创了页面的PdfCanvas对象，然后使用灰色画笔画出了mediaBox的边界(行14-17)）
            PdfCanvas over = new PdfCanvas(page);
            over.setStrokeColor(DeviceGray.GRAY);// 灰色
            over.rectangle(mediaBox.getLeft(), mediaBox.getBottom(), mediaBox.getWidth(), mediaBox.getHeight());
            over.stroke();

        }
    }

    // 页眉
    protected static class Header implements IEventHandler {
        protected float width = 102f;
        protected float height = 32f;
        protected float x = 42f;
        protected float y = 740f;
        float margin = 72;
        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdf = docEvent.getDocument();
            PdfPage page = docEvent.getPage();

            //PdfPage page = pdfDoc.getPage(i);
            // 改变每页大小
            Rectangle mediaBox = page.getMediaBox();
            Rectangle newMediaBox = new Rectangle(mediaBox.getLeft() - margin, mediaBox.getBottom() - margin,
                    mediaBox.getWidth() + margin * 2, mediaBox.getHeight() + margin * 2);
            page.setMediaBox(newMediaBox);
            // add border（我们创了页面的PdfCanvas对象，然后使用灰色画笔画出了mediaBox的边界(行14-17)）
            PdfCanvas over = new PdfCanvas(page);
            over.setStrokeColor(DeviceGray.GRAY);// 灰色
            over.rectangle(mediaBox.getLeft(), mediaBox.getBottom(), mediaBox.getWidth(), mediaBox.getHeight());
            over.stroke();

            Rectangle pageSize = page.getPageSize();
            PdfCanvas pdfCanvas = new PdfCanvas(
                    page.getLastContentStream(), page.getResources(), pdf);
            Canvas canvas = new Canvas(pdfCanvas, pdf, pageSize);
            ImageData image = null;
            //ClassLoader loader = Thread.currentThread().getContextClassLoader();
            //InputStream logo = loader.getResourceAsStream("imgaes/logo.jpg");

            try {
                InputStream logo = null;
                //logo = new FileInputStream("D:\\data");

                URL url = new URL("http://bpic.588ku.com/element_origin_min_pic/18/06/09/56bd69de87ef582e83118d86d3839850.jpg");
                logo = url.openStream();

                image = ImageDataFactory.create(toByteArray(logo));

            } catch (IOException e) {
                e.printStackTrace();
            }
            Image img = new Image(image);
            img.scaleAbsolute(width, height); // 图片宽高
            img.setFixedPosition(x, y); // 图片坐标 左下角(0,0)
            canvas.add(img);
        }
    }

    // 水印
    protected static class WatermarkingEventHandler implements IEventHandler {
        protected float x = 298f;
        protected float y = 421f;
        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdfDoc = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            PdfFont font = null;
            try {
                font = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
            } catch (IOException e) {
                e.printStackTrace();
            }
            PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
            new Canvas(canvas, pdfDoc, page.getPageSize())
                    .setFontColor( new DeviceCmyk(0.f, 0.0537f, 0.769f, 0.051f))
                    .setFontSize(60)
                    .setFont(font)
                    .showTextAligned(new Paragraph("W A T E R M A R K"), x, y, pdfDoc.getPageNumber(page),
                            TextAlignment.CENTER, VerticalAlignment.MIDDLE, 45);
        }
    }
    // 页脚
    protected static class Footer implements IEventHandler {
        protected PdfFormXObject placeholder; // 相对坐标系
        protected float x = 82f;
        protected float y = 50f;
        protected float imageWidth = 6f;
        protected float imageHeight = 78f;
        protected float space = 10f;
        public Footer() {
            placeholder =
                    new PdfFormXObject(new Rectangle(0, 0, 500, 78));
        }
        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdf = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            Rectangle pageSize = page.getPageSize();
            PdfCanvas pdfCanvas = new PdfCanvas(
                    page.getLastContentStream(), page.getResources(), pdf);
            pdfCanvas.addXObject(placeholder, x + space, y);
            Canvas canvas = new Canvas(pdfCanvas, pdf, pageSize);
            ImageData image = null;
            //ClassLoader loader = Thread.currentThread().getContextClassLoader();
            //InputStream buleRed = loader.getResourceAsStream("imgaes/bule_red.JPG");
            InputStream buleRed = null;
            try {
                URL url = new URL("http://bpic.588ku.com/element_origin_min_pic/18/06/09/94be22ddf545148d00a49f30e1c0670d.jpg");
                buleRed = url.openStream();
                image = ImageDataFactory.create(toByteArray(buleRed));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Image img = new Image(image);
            img.scaleAbsolute(imageWidth, imageHeight);
            img.setFixedPosition(x, y);
            canvas.add(img);
            writeInfo(pdf);
            pdfCanvas.release();
        }
        public void writeInfo(PdfDocument pdf) {
            Canvas canvas = new Canvas(placeholder, pdf);
            canvas.setFontSize(7.5f);
            PdfFont pdfFont = null;
            try {
                // 微软雅黑
                /*ClassLoader loader = Thread.currentThread().getContextClassLoader();
                InputStream msyh = loader.getResourceAsStream("fonts/msyh.ttf");
                pdfFont = PdfFontFactory.createFont(toByteArray(msyh), PdfEncodings.IDENTITY_H, false);*/
                pdfFont = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
            } catch (IOException e) {
                e.printStackTrace();
            }
            canvas.setFont(pdfFont); // 需要单独设置一下字体才能使用中文
            canvas.showTextAligned("http://www.xxxx.com",
                    0, 65, TextAlignment.LEFT);
            canvas.showTextAligned("深圳市南山区学府路东xxxxx  xxxxxx",
                    0, 50, TextAlignment.LEFT);
            canvas.showTextAligned("xxxxx Ixxxxxx,Xuefu Road Ease,Nan Shan District, Shenzhen xxxxxx",
                    0, 35, TextAlignment.LEFT);
            canvas.showTextAligned("Tel:0755-xxxxx Fax:212-xxxxxx",
                    0, 20, TextAlignment.LEFT);
        }
    }

    /**
     * 流转二进制
     * @param input
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }
}
