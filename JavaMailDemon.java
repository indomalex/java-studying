package com.sunsea.testBaiduTaobao;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;


public class JavaMailDemon {
	
	public static String smtpHost = "smtp.qq.com";
	public static String smtpPort = "465";
	public static String subject = "邮件名称";
	public static String baseInfo = "邮件基本内容";
	public static String fromAddr = "15356365@qq.com";
	public static String pwd = "mtqjoqyowhuycaja";
	public static String toAddr = "13594387943@139.com";
	public static String filePath = System.getProperty("user.dir") + "\\piggy.jpg";
	
	
	public void sendMailLite() throws Exception{
		//配置session要用到的属性
		Properties props = new Properties();
		//必写
		props.setProperty("mail.transport.protocol", "smtp");
		//smtp地址
        props.setProperty("mail.smtp.host", smtpHost);
        //SSL配置，一般服务器都要要求否则报错
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);
        
		//得到session
		Session session = Session.getInstance(props);
		
		//设置message
		MimeMessage message =new MimeMessage(session);
		message.setSubject(subject);
		message.setText(baseInfo);
		//一般要设置发送者否则报错。邮件服务器会验证发送者和登录者必须为同一账号
		Address fromMail[] = {new InternetAddress(fromAddr)};
		message.addFrom(fromMail);
		
		//得到transport
		Transport transport = session.getTransport();
		//设置smtp服务器连接地址和登录账号。账号要求与message.addFrom的一致
		transport.connect(fromAddr, pwd);
		//向toAddr邮箱发送message
		Address toMail[] = {new InternetAddress(toAddr)};
		transport.sendMessage(message, toMail);
		
		transport.close();
	}
	
	public void sendMailComplex() throws Exception{
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", smtpHost);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);
        
		Session session = Session.getInstance(props);
		
		
		//构建message:
		//1.设置邮件相关信息
		MimeMessage message =new MimeMessage(session);
		message.setSubject(subject);
		Address fromMail[] = {new InternetAddress(fromAddr)};
		message.addFrom(fromMail);
		//添加接收者
		message.addRecipient(RecipientType.TO, new InternetAddress(toAddr));
		//添加抄送者
		message.addRecipient(RecipientType.CC, new InternetAddress(fromAddr));
		
		//2.编辑邮件主体内容
		//添加文本信息。注意：此文本信息会被覆盖。一个内容体只允许容纳一套图片和内容说明+若干附件。
		MimeBodyPart mainInfo = new MimeBodyPart();
		mainInfo.setText("这是主内容，不会显示出来", "UTF-8");
		
		//添加图片和说明
		MimeBodyPart image = new MimeBodyPart();
		DataHandler dh = new DataHandler(new FileDataSource(filePath));
		image.setDataHandler(dh);
		image.setContentID("pic_for_link");
		MimeBodyPart intro = new MimeBodyPart();
		intro.setContent("这是一张图片<br/><img src='cid:pic_for_link'/>", "text/html;charset=UTF-8");
		MimeMultipart image_intro = new MimeMultipart();
		image_intro.addBodyPart(image);
		image_intro.addBodyPart(intro);
		image_intro.setSubType("related");
		//转换为MimeBodyPart，为了汇总时的需要
		MimeBodyPart imageIntro = new MimeBodyPart();
		imageIntro.setContent(image_intro);
		
		//添加附件及其名称
		MimeBodyPart atachFile = new MimeBodyPart();
		//以下这两种添加附件的方法都可:
		atachFile.attachFile(filePath);
		//atachFile.setDataHandler(dh);
		atachFile.setFileName(MimeUtility.encodeText(dh.getName()));
		
		//组合成一个总MimeMultipart
		MimeMultipart whole = new MimeMultipart();
		whole.addBodyPart(mainInfo);
		whole.addBodyPart(imageIntro);
		whole.addBodyPart(atachFile);
		whole.setSubType("mixed");
		
		//3.设置到message上
		message.setContent(whole);
		message.saveChanges();
		
		
		//得到transport
		Transport transport = session.getTransport();
		//设置smtp服务器连接地址和登录账号。账号要求与message.addFrom的一致
		transport.connect(fromAddr, pwd);
		//向toAddr邮箱发送message
		Address toMail[] = {new InternetAddress(toAddr)};
		transport.sendMessage(message, toMail);
		
		transport.close();
	}

	public static void main(String[] args){
		
		
		try {
			//new JavaMailDemon().sendMailLite();
			new JavaMailDemon().sendMailComplex();
			System.out.println("发送完毕");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("邮件发送失败。");
		}
		
	}
}
