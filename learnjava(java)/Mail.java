package door;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;


public class Mail {
	static String to = "xxxxxx@xxxx.co.jp";

	static String from = "xxxxxxxxx@xxxxxxx.co.jp";
	static String subject = "メール送信テスト";
	static String content = "テスト";
	static String mailHost = "xxxxx.xxxxxxxxx.co.jp";



	public static void send(){

		Log4j log = new Log4j(new Object(){}.getClass().getName());

		try{
			Transport.send(createMessage());
		}catch(Exception e){
			log.write(e,2);
		}
	}

	static MimeMessage createMessage() throws Exception {
		Properties props = new Properties();
		props.put("mail.smtp.host", mailHost);
		Session session = Session.getDefaultInstance(props, null);
		MimeMessage msg = new MimeMessage(session);

		InternetAddress[] toList = new InternetAddress[5];
		toList[0] = new InternetAddress(to);
		toList[1] = new InternetAddress(to1);
		toList[2] = new InternetAddress(to2);
		toList[3] = new InternetAddress(to3);
		toList[4] = new InternetAddress(to4);

		// あて先(To)
		msg.setRecipients(Message.RecipientType.TO, toList);

		//宛先(CC)
//		msg.setRecipients(Message.RecipientType.CC, cc);　　　これ外す

		// 発信元(From)
	    msg.setFrom(new InternetAddress(from));

	    // 題名(Subject)
	    msg.setSubject(MimeUtility.encodeText(subject, "iso-2022-jp", "B"));

	    // 本文(Content)
	    MimeMultipart naiyou = new MimeMultipart();
	    msg.setContent(naiyou);
	    MimeBodyPart honbun = new MimeBodyPart();
	    honbun.setContent(content, "text/plain; charset=\"iso-2022-jp\"");
	    naiyou.addBodyPart(honbun);

	    return msg;
	  }
}
