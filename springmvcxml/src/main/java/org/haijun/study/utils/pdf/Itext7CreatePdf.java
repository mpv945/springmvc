package org.haijun.study.utils.pdf;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.AffineTransform;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.pdf.annot.*;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.kernel.pdf.xobject.PdfXObject;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.property.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 * 创建pdf
 *  基本依赖的jar 说明；手动导入，通过https://github.com/itext/itext7即可，里面有上述所有的jar文件。
 * kernel 和 io: 包含低层次常用的基础的函数
 * layout: 包含高层次的函数；例如Table，list，div等对象
 * forms: 有关AcorForms操作需要的函数库
 * pdfa: 有关PDF/A（电子文档标准）的相关操作
 * pdftest: test例子中所引用的库
 *         除了这些常用的架包意外，还有一些其他可能的包：
 * barcodes: 当你想要创建bar code（条代码?）时使用
 * hyph: 当你想要文字有连字符时使用
 * font-asian: 当你想要用CJK字符时 (Chinese / Japanese / Korean)
 * sign: 当你想要使用电子签名是使用
 */
public class Itext7CreatePdf {

    private static final Color greenColor = new DeviceCmyk(0.78f, 0, 0.81f, 0.21f);
    private static final Color yellowColor = new DeviceCmyk(0, 0, 0.76f, 0.01f);
    private static final Color redColor = new DeviceCmyk(0, 0.76f, 0.86f, 0.01f);
    private static final Color blueColor = new DeviceCmyk(0.28f, 0.11f, 0, 0);
    private static final Color whithColor = new DeviceRgb(255,255,255);

    private static PdfFont helvetica = null;
    private static PdfFont helveticaBold = null;

    public static void main(String[] args) {
        example1();
    }

    // 创建实例1
    public static void example1(){
        File pdfFile = new File("D:\\data\\example1.pdf");
        // 创建PdfWriter实例，PdfWriter是一个可以写PDF文件的对象，它不需要了解它要写的pdf的实际内容是什么，PdfWriter不需要知道文档是什么，
        // 一旦文件结构完成，它就写不同的文件部分,不同的对象，构成一个有效的文档。PdfWriter的初始化参数可以是文件名或者Stream。
        try (PdfWriter writer = new PdfWriter(pdfFile);
             //PdfWriter了解它需要写什么内容，因为它监听PdfDocument的动态。PdfWriter负责管理添加的内容，并把内容分布到不同的页面上，
             // 并跟踪有关页面内容的所有信息。在第7张，我们可以发现PdfWriter可以有多重监听PdfDocument的方式。
             PdfDocument pdf = new PdfDocument(writer);
             // PdfDocument和PdfWriter创建以后，我们把PdfDocument传入Docment，并对Document对象操作
             // 初始大小16.54*11.69(单位为1用户单位，用户单位即pt,1点(pt)=1/72(英寸)inch 1英寸=25.4毫米mm):
             Document document = new Document(pdf, PageSize.A4); // 默认不设就是A4 设置文档和纸张大小；PageSize.A4.rotate()来使A4纸旋转。不加不旋转
             ) {

            /*ConverterProperties properties = new ConverterProperties();
            FontProvider fontProvider = new FontProvider();
            fontProvider.addSystemFonts();// 添加系统字体（font.addFont("/xx/xx/msyh.ttf");//你的字体文件）
            properties.setFontProvider(fontProvider);// 设置字体到配置文件*/

            //pdf.addNewPage(int index,PageSize pageSize)://在指定位置创建并插入一页，页面大小为指定的页面大小。
            //xxxxxx //当前页面的操作
            //document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));//是不是很方便！推荐大家使用这种方式来创建新的页面！
            //xxxxxx //在新的页面上操作

            // 创建Type 1字体
            // [STSong-Light : UniGB-UCS2-H] [MHei-Medium : UniCNS-UCS2-H] [MSung-Light : UniCNS-UCS2-H] [HeiseiKakuGo-W5 : UniJIS-UCS2-H]
            PdfFont f2 = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H",true);
            //pdf.addFont(f2);
            Class<?> clazz = PdfDocument.class;
            // 获得指定类的属性
            Field field = clazz.getDeclaredField("defaultFont");
            // 值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查。值为 false 则指示反射的对象应该实施 Java 语言访问检查。
            field.setAccessible(true);
            field.set(pdf,f2);
            PdfFont f3 = pdf.getDefaultFont();

            // 添加页眉和页脚以及水印
            // pdf.addEventHandler(PdfDocumentEvent.END_PAGE, new MyEventHandler());// 第一种方式
            //manipulatePdf(pdf,document); // 第二种方式

            document.setMargins(20, 20, 20, 20);// 设置上，右，下，左边距

            //大标题
            PdfFont helveticaBold = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
            Paragraph title = new Paragraph("标题").setTextAlignment(TextAlignment.CENTER).setFont(helveticaBold).setFontSize(14);
            document.add(title);
            // 创建Paragraph(段落)，包含"Hello World"字符串，并把这个短语加入Document独享中
            document.add(new Paragraph("Hello World!"));
            // 添加缩进无序段落
            PdfFont font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);// 创建字体（有中文必须使用中文字体，默认不支持中文）。有多种创建方式
            document.add(new Paragraph("iText is:").setFont(font));//添加段落
            //创建无序的对象.
            //LineSeparator.CCITTG3_1D // 行分割
            List list = new List().// 构造函数加ListNumberingType.ENGLISH_LOWER（多种可选）；表示有序
                    setSymbolIndent(12).//缩进多少个字符
                    // unicode 码 制表收藏：https://blog.csdn.net/ikscher/article/details/42195797
                    // 2713细勾，2714粗勾，2610方格 2611打勾方格；；2717 交叉；2718粗叉 2612 带交叉方格
                    // 00D7 乘法符号 2A2F向量积； 2022 点号 25CB 空小圆圈；25CF实心小圆圈；25EF 大空心圆圈
                    // 前缀设置图片Image image = Image.getInstance(IMG);image.scaleAbsolute(12, 12);image.setScaleToFitHeight(false);list.setListSymbol(new Chunk(Image.getInstance(image), 0, 0));
                    //setListSymbol("\u2022").//转换http://tool.chinaz.com/tools/unicode.aspx。每行前面的序号{\u2714 勾；\u2713 细勾
                    setFont(font);// 设置字体，等等，还有更多设置
            // 添加元素到有序的列表对象
            list.add(new ListItem("Never gonna give you up"))
                    .add(new ListItem("Never gonna let you down"))
                    .add(new ListItem("Never gonna run around and desert you"))
                    .add(new ListItem("Never gonna make you cry"))
                    .add(new ListItem("Never gonna say goodbye"))
                    .add(new ListItem("Never gonna tell a lie and hurt you"));
            document.add(list);// 添加有序段落

            PdfPage currPage =  pdf.addNewPage();
            // 添加图片 ImageDataFactory会根据路径自动分析文件的类型(包括jpg,png,gif,bmp等)并进行处理保存在pdf中
            Image fox = new Image(ImageDataFactory.create(
                    new URL("http://n.sinaimg.cn/translate/344/w700h1244/20180410/B_F_-fyzeyqa1740187.jpg")),0,20);//ImageDataFactory.
            //Image dog = new Image(ImageDataFactory.create("imgPath"));
            Rectangle mediaBox = currPage.getMediaBox();
            fox.setMaxWidth(mediaBox.getWidth()+mediaBox.getLeft());
            fox.setHeight(mediaBox.getHeight());// 设置高
            //fox.setAutoScaleHeight(true);// 设置高自动调整（依赖宽可以调）

            Paragraph p = new Paragraph("").add(fox);//.add(" jumps over the lazy ").add(dog);// 增加段落（相当于p标签）按顺序从左到右添加内容
            p.setVerticalAlignment(VerticalAlignment.TOP);
            p.setTextAlignment(TextAlignment.LEFT);
            p.setLineThrough();
            p.setUnderline();
            p.setFontColor(blueColor);
            document.add(p);

            PdfPage currPage1 =  pdf.addNewPage();
            //PdfFormXObject pageCopy = currPage1.copyAsFormXObject(pdf);//页面转对象
            Rectangle orig = currPage1.getPageSize();// 获取页面范围
            PdfCanvas canvas = new PdfCanvas(currPage1);// 在新创建页新增一个画布
            AffineTransform transformationMatrix = AffineTransform.getScaleInstance(
                    orig.getWidth() / orig.getWidth() / 2f,
                    orig.getHeight() / orig.getHeight() / 2f);
            canvas.concatMatrix(transformationMatrix);
            PdfXObject pageCopy = fox.getXObject();// 元素转对象，方便复制
            canvas.addXObject(pageCopy, 0, orig.getHeight());
            canvas.addXObject(pageCopy, orig.getWidth(), orig.getHeight());
            canvas.addXObject(pageCopy, 0, 0);
            canvas.addXObject(pageCopy, orig.getWidth(), 0);



            // 添加表格（表格个数就是列的个数，每个数字对应列的宽度(会用表总长度然后百分比划分宽度)
            PdfFont font1 = PdfFontFactory.createFont(FontConstants.HELVETICA);//细体
            PdfFont bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);//粗体
            Table table = new Table(new float[]{4, 1, 3, 4, 3, 3, 3, 3, 1});
            table.useAllAvailableWidth();//新版代替table.setWidthPercent(100);
            table.setTextAlignment(TextAlignment.CENTER)
                    .setHorizontalAlignment(HorizontalAlignment.CENTER);
            //table.setWidth(100);//表格宽度
            // csv解析
            /*BufferedReader br = new BufferedReader(new FileReader("data.csv"));//读取csv文件数据
            String line = br.readLine();// csv每行数据
            process(table, line, bold, true);
            while ((line = br.readLine()) != null) {
                process(table, line, font, false);
            }
            br.close();*/
            // 模拟数据
            String[][] data = new String[][]{//new String[]{"第一列","第二列","第三列","第四列","第五列","第六列","第七列"},
                    new String[]{"A","B","C","D","E","F","G","H","I"},new String[]{"A1","B1","C1","D1","E1","F1","G1","H1","I1"},
                    new String[]{"A2","B2","C2","D2","E2","F2","G2","H2","I2"},};
            for(String[] item : data){
                String line = Arrays.stream(item).collect(Collectors.joining(";"));
                processPlus(table,line,font1,false);
                //for(String str : item){
                    // 颜色 DeviceCmyk是按cmyk表(new DeviceRgb() 是按rgb色，new DeviceGray()灰色 ;颜色对照http://www.5tu.cn/colors/cmyk-peisebiao.html
                    /*table.addCell(new Cell().add(new Paragraph(str).setFont(font).setBorder(
                            new SolidBorder(new DeviceCmyk(0,65,50,0), 1.5f))));*/
                //}
            }
            //table.addHeaderCell(new Cell().add());//new Cell()可以设定合并行列的个数
            document.add(table);

            //创建注释
            Text text = new Text(String.format("Page页面    %d", pdf.getNumberOfPages() - 1));
            text.setFont(f2);
            PdfTextAnnotation ann = new PdfTextAnnotation(new Rectangle(20, 800, 0, 0));
            ann.setColor(greenColor)
                    .setTitle(new PdfString(text.getText()))// 字注释
                    .setContents(text.getText());//文字注释测试内容文字注释测试内容文字注释测试内容

            pdf.getFirstPage().addAnnotation(ann);//设置在页开始
            // 链接注释
            PdfLinkAnnotation annotation = new PdfLinkAnnotation(new Rectangle(100, 20))
                    .setAction(PdfAction.createURI("https://blog.csdn.net/column/details/18037.html"));
            Link link = new Link("here", annotation);
            Paragraph linkann = new Paragraph(" 对于itext7的初步学习，请点击")//对于itext7的初步学习，请点击
                    .add(link.setUnderline())
                    .add(" ewerewrwerwerw");//里面有很多教程和实例代码和解释。
            linkann.setFont(f2);
            document.add(linkann);
            // 线注释
            PdfPage page = pdf.addNewPage();// 新建一页
            PdfArray lineEndings = new PdfArray();
            lineEndings.add(new PdfName("Diamond"));
            lineEndings.add(new PdfName("Diamond"));
            PdfAnnotation lineann = new PdfLineAnnotation(
                    new Rectangle(0, 0),
                    new float[]{20, 790, page.getPageSize().getWidth() - 20, 790})
                    .setLineEndingStyles((lineEndings))
                    .setContentsAsCaption(true)
                    .setTitle(new PdfString("iText"))//线注解标题
                    .setContents("The example of line annotation")//线注解内容
                    .setColor(yellowColor);
            page.addAnnotation(lineann);

            //创建线注解
            Paragraph p2 = new Paragraph("The example of text markup annotation.");
            document.showTextAligned(p2, 20, 795, 1, TextAlignment.LEFT,
                    VerticalAlignment.MIDDLE, 0);
            PdfTextMarkupAnnotation markupann = PdfTextMarkupAnnotation.createHighLight(
                    new Rectangle(105, 790, 64, 10),
                    new float[]{169, 790, 105, 790, 169, 800, 105, 800});
                    markupann.setColor(yellowColor)
                    .setTitle(new PdfString("Hello!"))
                    .setContents(new PdfString("I'm a popup."))
                    .setTitle(new PdfString("iText"))
                    //setOpen(true)
                    .setRectangle(new PdfArray(new float[]{100, 600, 200, 100}));
            pdf.getFirstPage().addAnnotation(markupann);

            manipulatePdf(pdf,document);// 添加页眉和页脚，以及水印
            changePageSize(pdf);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * 处理csv数据转pdf表格数据
     * @param table pdf表对象
     * @param line 每行数据
     * @param font 字体
     * @param isHeader 是否是表头部（列标题）
     */
    public static void process(Table table, String line, PdfFont font, boolean isHeader) {
        StringTokenizer tokenizer = new StringTokenizer(line, ";");
        while (tokenizer.hasMoreTokens()) {
            if (isHeader) {
                // 添加为头部的列
                table.addHeaderCell(
                        new Cell().add(
                                new Paragraph(tokenizer.nextToken()).setFont(font)));
            } else {
                table.addCell(
                        new Cell().add(
                                new Paragraph(tokenizer.nextToken()).setFont(font)));
            }
        }
    }

    public static void processPlus(Table table, String line, PdfFont font, boolean isHeader) {
        StringTokenizer tokenizer = new StringTokenizer(line, ";");
        int columnNumber = 0;
        while (tokenizer.hasMoreTokens()) {
            if (isHeader) {
                Cell cell = new Cell().add(new Paragraph(tokenizer.nextToken()));
                cell.setNextRenderer(new RoundedCornersCellRenderer(cell));
                cell.setPadding(5).setBorder(null);
                table.addHeaderCell(cell);
            } else {
                columnNumber++;
                Cell cell = new Cell().add(new Paragraph(tokenizer.nextToken()));
                cell.setFont(font).setBorder(new SolidBorder(blueColor, 0.5f));
                switch (columnNumber) {
                    case 4:
                        cell.setBackgroundColor(greenColor);
                        break;
                    case 5:
                        cell.setBackgroundColor(yellowColor);
                        break;
                    case 6:
                        cell.setBackgroundColor(redColor);
                        break;
                    default:
                        cell.setBackgroundColor(blueColor);
                        break;
                }
                table.addCell(cell);
            }
        }
    }

    /**
     * 设置页眉，页脚，水印
     */
    protected static class MyEventHandler implements IEventHandler {
        @Override
        public void handleEvent(Event event) {
            try {
                helvetica = PdfFontFactory.createFont(FontConstants.HELVETICA);
                helveticaBold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
            } catch (IOException e) {
                e.printStackTrace();
            }
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdfDoc = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            int pageNumber = pdfDoc.getPageNumber(page);
            Rectangle pageSize = page.getPageSize();
            PdfCanvas pdfCanvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);

            //设置背景
            Color limeColor = new DeviceCmyk(0.208f, 0, 0.584f, 0);
            Color blueColor = new DeviceCmyk(0.445f, 0.0546f, 0, 0.0667f);
            pdfCanvas.saveState()
                    .setFillColor(pageNumber % 2 == 1 ? limeColor : blueColor)
                    .rectangle(pageSize.getLeft(), pageSize.getBottom(), pageSize.getWidth(), pageSize.getHeight())
                    .fill().restoreState();

            //添加页头和页脚
            pdfCanvas.beginText()
                    .setFontAndSize(helvetica, 9)
                    .moveText(pageSize.getWidth() / 2 - 60, pageSize.getTop() - 20)
                    .showText("页眉信息")
                    .moveText(60, -pageSize.getTop() + 30)
                    .showText(String.valueOf(pageNumber))//页脚信息
                    .endText();

            //添加水印
            Canvas canvas = new Canvas(pdfCanvas, pdfDoc, page.getPageSize());
            canvas.setFontColor(whithColor)
                    .setFontSize(60).setFont(helveticaBold);
            canvas.showTextAligned(new Paragraph("X I E H A I J U N"), 298f, 421f, pdfDoc.getPageNumber(page),
                    TextAlignment.CENTER, VerticalAlignment.MIDDLE, 45);// 45 表示水印角度；垂直位置VerticalAlignment

            pdfCanvas.release();
        }

    }

    /**
     * 另外一种方式添加页眉页脚
     * @param pdfDoc
     * @param document
     * @throws IOException
     */
    public static void manipulatePdf(PdfDocument pdfDoc, Document document) throws IOException {
        Rectangle pageSize;
        PdfCanvas canvas;
        int n = pdfDoc.getNumberOfPages();
        for (int i = 1; i <= n; i++) {
            PdfPage page = pdfDoc.getPage(i);
            pageSize = page.getPageSize();
            canvas = new PdfCanvas(page);
            //Draw header text
            canvas.beginText().setFontAndSize(PdfFontFactory.createFont(FontConstants.HELVETICA), 7)
                    .moveText(pageSize.getWidth() / 2 - 24, pageSize.getHeight() - 10)
                    .showText("I want to believe")
                    .endText();
            //Draw footer line
            canvas.setStrokeColor(blueColor)
                    .setLineWidth(.2f)
                    .moveTo(pageSize.getWidth() / 2 - 30, 20)
                    .lineTo(pageSize.getWidth() / 2 + 30, 20).stroke();
            //Draw page number
            canvas.beginText().setFontAndSize(PdfFontFactory.createFont(FontConstants.HELVETICA), 7)
                    .moveText(pageSize.getWidth() / 2 - 7, 10)
                    .showText(String.valueOf(i))
                    .showText(" of ")
                    .showText(String.valueOf(n))
                    .endText();
            //Draw watermark
            Paragraph p = new Paragraph("CONFIDENTIAL").setFontSize(60);
            canvas.saveState();
            PdfExtGState gs1 = new PdfExtGState().setFillOpacity(0.2f);
            canvas.setExtGState(gs1);
            document.showTextAligned(p,
                    pageSize.getWidth() / 2, pageSize.getHeight() / 2,
                    pdfDoc.getPageNumber(page),
                    TextAlignment.CENTER, VerticalAlignment.MIDDLE, 45);
            canvas.restoreState();
        }
    }

    /**
     * 改变页面大小和方向
     * @param pdfDoc
     */
    public static void changePageSize(PdfDocument pdfDoc){
        float margin = 72;
        for (int i = 1; i <= pdfDoc.getNumberOfPages(); i++) {
            PdfPage page = pdfDoc.getPage(i);
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
            // change rotation of the even pages（偶数页，我们把页面旋转180度(行19)）
            if (i % 2 == 0) {
                //page.setRotation(180);
            }
        }
    }

    // 放大和复制页面到新页
    public static void zoomPageAndCopyPage() throws IOException{
        //Initialize PDF document（实例演示从源pdf复制页面到目标新pdf）。我自己测试就在当前同一个pdf
        PdfDocument pdf = new PdfDocument(new PdfWriter("dest"));// 新增目标pdf
        PdfDocument origPdf = new PdfDocument(new PdfReader("src"));// 读取源pdf
        //获取源的第一个页面
        PdfPage origPage = origPdf.getPage(1);
        Rectangle orig = origPage.getPageSizeWithRotation();

        //Add A4 page（实例是在目标pdf 新增一个page）
        PdfPage page = origPdf.addNewPage(PageSize.A4.rotate());
        //Shrink original page content using transformation matrix
        PdfCanvas canvas = new PdfCanvas(page);
        AffineTransform transformationMatrix = AffineTransform.getScaleInstance(
                page.getPageSize().getWidth() / orig.getWidth(), page.getPageSize().getHeight() / orig.getHeight());
        canvas.concatMatrix(transformationMatrix);
        PdfFormXObject pageCopy = origPage.copyAsFormXObject(pdf);
        canvas.addXObject(pageCopy, 0, 0);

        //Add page with original size
        pdf.addPage(origPage.copyTo(pdf));

        //Add A2 page
        page = pdf.addNewPage(PageSize.A2.rotate());
        //Scale original page content using transformation matrix
        canvas = new PdfCanvas(page);
        transformationMatrix = AffineTransform.getScaleInstance(page.getPageSize().getWidth() / orig.getWidth(), page.getPageSize().getHeight() / orig.getHeight());
        canvas.concatMatrix(transformationMatrix);
        canvas.addXObject(pageCopy, 0, 0);
    }
}

