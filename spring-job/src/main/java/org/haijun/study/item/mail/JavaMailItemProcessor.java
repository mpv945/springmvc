/**
 * 
 */
package org.haijun.study.item.mail;

import org.haijun.study.model.bo.CreditBill;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-9-29下午08:15:18
 */
public class JavaMailItemProcessor implements ItemProcessor<CreditBill, MimeMessage> {

	private JavaMailSender mailSender;
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	/*发送带附件的邮件*/
	@Override
	public MimeMessage process(CreditBill item) throws Exception {

		MimeMessage message = mailSender.createMimeMessage();// 需要引入javax.mail
		MimeMessageHelper helper = new MimeMessageHelper(message, true,"GBK");

		helper.setFrom("springbatchexample@163.com");
		helper.setTo("springbatchexample@163.com");
		helper.setSubject("Credit detail " +
				new SimpleDateFormat("yyyy年MM月dd日hh时mm分ss秒").
						format(Calendar.getInstance().getTime()));
		helper.setText("Credit details: " + item.toString());

		FileSystemResource file = new FileSystemResource("C:\\log.txt");
		helper.addAttachment(file.getFilename(), file);


		return message;
	}
}
