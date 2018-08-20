package org.haijun.study.coreTools;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * itext 官网教程https://developers.itextpdf.com/content/itext-7-examples/itext-7-converting-html-pdf/pdfhtml-responsive-design 或者https://itextpdf.com/ja
 * http://www.importnew.com/27220.html  https://www.cnblogs.com/0201zcr/p/4952174.html
 * 导出pdf
 */
@Component
public class MyPdfView extends AbstractUrlBasedView {

    public MyPdfView() {
        this.setContentType("application/pdf");
        this.setUrl("classpath:template/pdf/view.pdf");
    }

    protected boolean generatesDownloadContent() {
        return true;
    }

    protected final void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ByteArrayOutputStream baos = this.createTemporaryOutputStream();
        PdfReader reader = this.readPdfResource();
        PdfStamper stamper = new PdfStamper(reader, baos);
        this.mergePdfDocument(model, stamper, request, response);
        stamper.close();
        this.writeToResponse(response, baos);
    }

    protected PdfReader readPdfResource() throws IOException {
        String url = this.getUrl();
        Assert.state(url != null, "'url' not set");
        return new PdfReader(this.obtainApplicationContext().getResource(url).getInputStream());
    }

    /**
     * 对pdf操作
     * @param param
     * @param stamper
     * @param request
     * @param response
     * @throws Exception
     */
    protected void mergePdfDocument(Map<String, Object> param, PdfStamper stamper,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception{
        String pdfName = (String) param.get("pdfName");
        response.setHeader("Content-Type","application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename="  + pdfName+".pdf");
        AcroFields form = stamper.getAcroFields();

        // 参考https://www.programcreek.com/java-api-examples/index.php?api=com.itextpdf.text.pdf.PdfStamper
        // 该模式只支持根据表单字段的模板填充
        form.setField("currency","All in ");
        // STSong-Light使用iTextAsian.jar中的字体//new Paragraph("hellos你好").setFont(f2)
        form.addSubstitutionFont(BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED));
        stamper.setFormFlattening(true);
    }

}
