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
public class MailItemProcessor implements ItemProcessor<CreditBill, SimpleMailMessage> {

	private JavaMailSender mailSender;
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public SimpleMailMessage process(CreditBill item) throws Exception {
		SimpleMailMessage msg = new SimpleMailMessage();
	    msg.setFrom("springbatchexample@163.com");//邮件的发件人
	    msg.setTo("springbatchexample@163.com");// 邮件接收人
	    msg.setSubject("Credit detail " + 
	    		new SimpleDateFormat("yyyy年MM月dd日hh时mm分ss秒").
	    		format(Calendar.getInstance().getTime()));//邮件主题信息
	    msg.setText("Credit details: " + item.toString());// 邮件的正文

		return msg;
	}

}
