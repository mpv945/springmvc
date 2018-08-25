package org.haijun.study.utils.word;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import fr.opensagres.poi.xwpf.converter.core.ImageManager;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLConverter;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLOptions;
import fr.opensagres.xdocreport.itext.extension.IPdfWriterConfiguration;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Word2Html {

    public static void main(String[] args) {
        try {
            //convert();
            convertHtmlToPdf("http://localhost:8081/index.php?db=&table=&token=%5CAUIt%27%40%7Dp%284R-8yy&lang=zh_cn","D:\\data\\flying-saucer.pdf");
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

        String sourseHtmlStr = content;
        // Jsoup 把<br/> 格式成 <brL>
       Document doc = Jsoup.parse(content); // 使用手册https://www.cnblogs.com/zhangyinhua/p/8037599.html
        // 打开docx结尾的word转化的html文件，发现body中有一个或多个div包裹内层标签
        // 这些div设置了style的width值，width过大导致转换的pdf空白。
        // 用的方法最简单粗暴，在jsoup格式化完成后找到style然后替换为空。
        Element head = doc.head();
        Element headStyle = head.selectFirst("style");
        //headStyle.remove();// 删除头部的css
        headStyle.html("<link href=\"default.css\" rel=\"stylesheet\" type=\"text/css\" />");
        // 清空body元素 标签包含style属性的值
        Element body =  doc.body();
        String style = "";
/*        style = body.attr("style");
        if(!StringUtils.isEmpty(style) && style.indexOf("width") >= 0){
            body.attr("style","");
        }*/

        // 清除包含style 属性标签的 style属性 (select 方法在Document, Element,或Elements对象中都可以使用)
        //Elements divs = body.select("[style],[class]");
        Elements divs = body.select("[style]");
        //String cssStr = headStyle.html();
        StringBuilder css = new StringBuilder(headStyle.html());
        //css.append(cssStr.substring(cssStr.indexOf("<style>"),cssStr.indexOf("</style>")));
        for(int i=0;i<divs.size();i++){
            Element div = divs.get(i);
            String tag = "."+div.tagName()+"key"+i;
            css.append(" "+tag+"{").append(div.attr("style")).append("}");
            div.addClass(tag.substring(1));
            div.removeAttr("style");//删除该标签内的的style属性
            //div.removeAttr("class");
            //style = div.attr("style");

            /*if(!StringUtils.isEmpty(style) && style.indexOf("width") >= 0){
                div.attr("style","");// 给属性赋值
            }*/
        }
        //如果提示Invalid nested tag p found, expected closing tag img 最后的提示就是该标签没有结束
        //Elements imgs = body.select("p");

       /* for(int j=0;j<divs.size();j++){
            Element parent = imgs.get(0);
            String imgHtml =  parent.html();
            if((matcher = matcher(imgHtml,"img")).find()){
                String newHtml =  matcher.replaceFirst("<img "+matcher.group(1)+" />"); // group(1) 为中间匹配的部分
                parent.html(newHtml);
            }
        }*/

        content = doc.html();

        /*content = content.replace("<p/>","");
        content = Arrays.stream(content.split("<p/>")).collect(Collectors.joining());
        System.out.println("p 开始个数="+(content.split("<p").length));
        System.out.println("p 结束个数="+(content.split("</p>").length));*/
        String tag = "img";
        //content = content.split("\\<"+tag+"([^\\>].+?)\\>");
        String imgHtml =  content;

        List<String> imgTagList = new ArrayList<>();
        Matcher matcher;
        while ((matcher = matcher(imgHtml,"img")).find()){
            MatchResult matcherRe = matcher.toMatchResult();
            String matcherStr = matcherRe.group(1);
            imgTagList.add("<img "+matcherStr+" />");
            /*imgTagList.add("<img src=\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=" +
                    "1535017979965&di=e3b478710014e7ac7a8de00af5053b3e&imgtype=0&src=http%3A%2F%2Fe.hiphotos.baidu." +
                    "com%2Fimage%2Fpic%2Fitem%2Fb151f8198618367a2e8a46ee23738bd4b31ce586.jpg\" />");*/
            imgHtml =  matcher.replaceFirst("xiehaijunA#"); // group(1) 为中间匹配的部分
        }

        content = imgHtml;
        int imgTagIndex = 0;
        String[] imgTagArr = content.split("xiehaijunA#");
        int imgTagArrSize = imgTagArr.length;
        int endIndex = imgTagArrSize-1;
        StringBuilder sb = new StringBuilder("");
        for(int i = 0 ; i < imgTagArrSize;i++ ){
            sb.append(imgTagArr[i]);
            if(i<endIndex){
                sb.append(imgTagList.get(i));
            }
        }
        content = sb.toString();
        content = Arrays.stream(content.split("<br>")).collect(Collectors.joining("<br />"));

        FileOutputStream fos = new FileOutputStream(new File(fileOutName));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos,"UTF-8"));
        //bw.write(content);
        bw.write(sourseHtmlStr);
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
               /* BaseFont bf = null;
                try {
                // 使用的windows里的宋体，可将其文件放资源文件中引入fntname = "fonts/simsun.ttc";
                    bf = BaseFont.createFont("D:\\data\\simsunb.ttf", //C:/Windows/Fonts 拷贝到别的磁盘引用;
                            BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
                            // return super.getFont(fntname, encoding, size, style);
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Font font = new Font(bf,size, style, color);
                font.setColor(color);*/
                BaseFont bfChinese = null;
                try {
                    bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                com.itextpdf.text.Font FontChinese24 = new com.itextpdf.text.Font(bfChinese, 24, com.itextpdf.text.Font.BOLD);
                //return super.getFont("D:\\data\\simsunb.ttf", encoding, embedded, size, style, color);
                return new Font(bfChinese,size, style, color);
            }
        };
        com.itextpdf.text.Document document1 = new com.itextpdf.text.Document(PageSize.A4);

        OutputStream os = new FileOutputStream(filePdfName2);
        PdfWriter writer = PdfWriter.getInstance(document1,os);
        document1.open();
        document1.addAuthor("pdf作者");
        document1.addCreator("pdf创建者");
        document1.addSubject("pdf主题");
        document1.addCreationDate();
        document1.addTitle("pdf标题,可在html中指定title");
        URL url = new URL("http://localhost:8081");
        //Document docContext = Jsoup.connect("http://example.com/").get();
        // https://darren.work/post/11样式设置
        XMLWorkerHelper.getInstance().parseXHtml(writer,document1,
                //new FileInputStream(fileOutName),// 读取html文件
                //new ByteArrayInputStream(docContext.html().getBytes("UTF-8")),//直接从html文本读取
                url.openStream(),// url读取流

                null,// 设置css
                //new ByteArrayInputStream(("<style type=\"text/css\">"+css.toString()+"</style>").getBytes("UTF-8")),
                //XMLWorkerHelper.class.getResourceAsStream("/default.css"),
                //XMLWorkerHelper.class.getResourceAsStream("/dd.css"),//设置css文件

                Charset.forName("UTF-8"),//编码
                myfont,
                "D:\\data\\");// 本地测试需要指定root目录，web的不需要
        document1.close();
        writer.close();
        os.close();
    }
    /**
     * 正则匹配字符串(如果存在<tag > 的形式字符串，表示没有结束) https://www.cnblogs.com/lzq198754/p/5780340.html
     * @param str
     * @return
     */
    private static Matcher matcher(String str,String tag) {
        //Pattern pattern = Pattern.compile("\\<{1}"+tag+"([^\\/].+?)\\>{1}", Pattern.CASE_INSENSITIVE);
        Pattern pattern = Pattern.compile("\\<{1}"+tag+"([^\\/].+?)\\>{1}");
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }

    /**
     * flying-saucer生成pdf
     * @param inputFile
     * @param outputFile
     * @return
     * @throws Exception
     */
    private static boolean convertHtmlToPdf(String inputFile, String outputFile) throws Exception {

        OutputStream os = new FileOutputStream(outputFile);
        ITextRenderer renderer = new ITextRenderer();
        String url = new File(inputFile).toURI().toURL().toString();

        renderer.setDocument(inputFile);

        renderer.layout();
        renderer.createPDF(os);

        os.flush();
        os.close();
        return true;
    }

}
