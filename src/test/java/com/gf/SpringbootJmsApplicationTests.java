package com.gf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootJmsApplicationTests{

	private final static Logger logger = LoggerFactory.getLogger( SpringbootJmsApplicationTests.class );

	@Test
	public void contextLoads() {
	}

	@Autowired
	private JavaMailSenderImpl mailSender;

	@Test
	public void sendTextMail(){
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		//设置收件人 ，寄件人
		simpleMailMessage.setTo( new String[]{"xxxx@qq.com"} );
		simpleMailMessage.setFrom( "xxxx@163.com" );
		simpleMailMessage.setSubject( "Spring Boot Mail 邮件测试【文本】" );
		simpleMailMessage.setText( "这是一段简单文本" );
		// 发送邮件
		mailSender.send( simpleMailMessage );

		logger.info( "邮件已经发送" );
	}

	@Test
	public void sendHtmlMail() throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		//multipart模式
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper( mimeMessage );
		mimeMessageHelper.setTo( "xxxx@qq.com" );
		mimeMessageHelper.setFrom( "xxxx@163.com" );
		mimeMessageHelper.setSubject( "Spring Boot Mail 邮件测试【HTML】" );

		StringBuilder sb = new StringBuilder();
		sb.append( "<html><head></head>" );
		sb.append( "<body><h1>spring 邮件测试</h1><p>hello! this is spring mail test .</p></body>" );
		sb.append( "</html>" );

		//启动html
		mimeMessageHelper.setText(sb.toString() , true);
		//发送邮件
		mailSender.send( mimeMessage );

		logger.info( "邮件已经发送" );

	}

	@Test
	public void sendAttachedImageMail() throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		//multipart模式
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage ,true);
		mimeMessageHelper.setTo( "xxxx@qq.com" );
		mimeMessageHelper.setFrom( "xxxx@163.com" );
		mimeMessageHelper.setSubject( "Spring Boot Mail 邮件测试【图片】" );

		StringBuilder sb = new StringBuilder();
		sb.append( "<html><head></head>" );
		sb.append( "<body><h1>spring 邮件测试</h1><p>hello! this is spring mail test .</p>" );
		// cid为固定写法，imageId指定一个标识
		sb.append("<img src='cid:imageId'/></body>");
		sb.append("</html>");

		//启动html
		mimeMessageHelper.setText(sb.toString() , true);

		//设置imageId
		FileSystemResource img = new FileSystemResource( "/Users/huanchu/Desktop/1.JPG" );
		mimeMessageHelper.addInline( "imageId" , img );

		//发送邮件
		mailSender.send( mimeMessage );

		logger.info( "邮件已经发送" );

	}

	@Test
	public void sendAttendedFileMail() throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		//启动multipart模式
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper( mimeMessage , true , "UTF-8");
		mimeMessageHelper.setTo( "xxxx@qq.com" );
		mimeMessageHelper.setFrom( "xxxx@163.com");
		mimeMessageHelper.setSubject( "Spring Boot Mail 邮件测试【附件】" );

		StringBuilder sb = new StringBuilder();
		sb.append( "<html><head></head>" );
		sb.append( "<body><h1>spring 邮件测试</h1><p>hello! this is spring mail test .</p>" );

		//启动html
		mimeMessageHelper.setText(sb.toString() , true);

		//设置附件
		FileSystemResource file = new FileSystemResource( "/Users/huanchu/Desktop/1.JPG" );
		mimeMessageHelper.addAttachment( "image.jpg"  , file);

		//发送邮件
		mailSender.send( mimeMessage );

		logger.info( "邮件已经发送" );


	}

}
