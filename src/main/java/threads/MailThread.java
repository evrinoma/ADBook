package threads;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.swing.SwingWorker;

import com.sun.mail.util.MailSSLSocketFactory;

import entity.UserDto;
import libs.Core;

public class MailThread extends SwingWorker<Object, String> {

	public static final boolean ACTION_AUTHORIZE = true;
	private static final String MAIL_SERVER = "mail.ite-ng.ru";
	private static final String MAIL_PORT = "465";
	private static final String HINT_TRY_AUTH = "Пытаемся авторизоваться на сервере:" + MAIL_SERVER;
	private static final String HINT_TRY_FIND_USER = "Пытаемся найти пользователя...";
	private static final String HINT_TRY_SEND = "Пытаемся послать почту на сервер:" + MAIL_SERVER;
	private static final String HINT_DONE_SEND = "Сообщения отправлены";
	private static final String HINT_AUTH_ERROR = "incorrect password or account name";
	private static final String HINT_AUTH = "authorization successful";
	private static final String HINT_CREATE_MAIL = "Возникли ошибки при создании почтового сообщения";
	private final Charset UTF8_CHARSET = Charset.forName("UTF-8");
	
	private String username;
	private String password;
	private Session session = null;
	private boolean valid = false;
	private boolean action = ACTION_AUTHORIZE;
	private Properties propsSSL = null;
	private Core core;
	private HashMap<String, String> attachments = null;
	private ArrayList<String> to = null;
	private ArrayList<String> toError = null;	
	private ArrayList<String> signature = null;
	
	private String body = "";
	private String subject = "";
	private String from = "";
	private String encodeFrom = "";
	private boolean notifyRead = false;
	private boolean notifyDelivery = false;
	private boolean notifyError = true; 

	@Override
	protected Boolean doInBackground() throws Exception {
		boolean status = false;

		publish(HINT_TRY_AUTH);
		if (authorize()) {
			publish(HINT_TRY_FIND_USER);
			if (action == !ACTION_AUTHORIZE) {
				publish(HINT_TRY_SEND);
				sendMessages();
			}
			status = true;
		}

		return status;
	}
	
	// Can safely update the GUI from this method.
	protected void done() {
		try {
			// Retrieve the return value of doInBackground.
			if ((boolean) get()) {
				if (action) {
					core.isMailAuthrizeSuccessful(getUserName());
					core.setStatusString(HINT_AUTH);
				} else {
					core.isMailSendedSuccessful(from, toError);
					core.setStatusString(HINT_DONE_SEND);
				}
			} else {
				core.isMailAuthrizeFail(HINT_AUTH_ERROR);
			}
			// System.out.println("Completed with status: " + status);
		} catch (InterruptedException e) {
			// This is thrown if the thread's interrupted.
		} catch (ExecutionException e) {
			// This is thrown if we throw an exception
			// from doInBackground.
		}
		core.flushing(core.TREAD_MAIL);
	}

	@Override
	// Can safely update the GUI from this method.
	protected void process(List<String> chunks) {
		// Here we receive the values that we publish().
		// They may come grouped in chunks.
		for (final String massage : chunks) {
			core.setStatusString(massage);
		}
	}

	public MailThread(Core core) {
		this.core = core;
		createAttachments();
		to = new ArrayList<String>();
		toError = new ArrayList<String>();
		propsSSL = new Properties();
		signature = new ArrayList<String>(); 
		try {
			MailSSLSocketFactory socketFactory = new MailSSLSocketFactory();
			socketFactory.setTrustedHosts(new String[] { MAIL_SERVER });
			socketFactory.setTrustAllHosts(true);
			propsSSL.put("mail.smtps.socketFactory", socketFactory);
			propsSSL.put("mail.smtp.ssl.socketFactory", socketFactory);
			propsSSL.put("mail.smtp.ssl.checkserveridentity", "true");
			propsSSL.put("mail.smtp.ssl.enable", "true");
			propsSSL.put("mail.smtp.auth", "true");
			propsSSL.put("mail.smtp.starttls.enable", "true");
			propsSSL.put("mail.smtp.EnableSSL.enable", "true");
			propsSSL.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			propsSSL.put("mail.smtp.socketFactory.fallback", "false");
			propsSSL.put("mail.smtp.host", MAIL_SERVER);
			propsSSL.put("mail.smtp.port", MAIL_PORT);
			propsSSL.put("mail.smtp.socketFactory.port", MAIL_PORT);
			propsSSL.put("mail.mime.charset","UTF-8");
		} catch (GeneralSecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void sendMessages() {		
		Multipart multipart = createMail();
		if (null != multipart) {
			for (String mail : to) {
				if (!sendMail(mail, multipart)) {
					toError.add(mail);
				}
			}
		} else {
			publish(HINT_CREATE_MAIL);
		}
	}
    
	private boolean sendMail(String to, Multipart multipart) {
		boolean status = false;
		if (this.valid) {
			try {
				session = Session.getInstance(propsSSL, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(encodeFrom));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(encodeMail(to)));
				message.setSubject(mimeDecodeUTF8(subject));
				message.setText(body);				
				message.setContent(multipart);
				addNotify(message);
				Transport.send(message);
				status = true;
			} catch (MessagingException | UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return status;
	}

	private void addNotify(Message message) throws MessagingException
	{
		if (notifyDelivery) message.addHeader("Return-Receipt-To", "DSN=1");
		if (notifyRead) message.addHeader("Disposition-Notification-To",encodeFrom);
		if (notifyError) message.addHeader("Errors-To",encodeFrom);
	}
	
	private String encodeMail(String mail) throws UnsupportedEncodingException {
		String[] splited = mail.split("<");
		return mimeDecodeUTF8(splited[0]) + "<" + splited[1];
	}		
	
	private String mimeDecodeUTF8 (String data) throws UnsupportedEncodingException {			
		return MimeUtility.encodeText(data, "UTF-8", "Q");
	}
	
	private String decodeUTF8 (String data) throws UnsupportedEncodingException {			
		return new String(data.getBytes(), UTF8_CHARSET);
	}

	private String getUserName() {
		UserDto user = core.getCompanys().findUserByMail(username);
		if (null != user) {
			return user.getCn() + " <" + user.getMail() + ">";
		} else
			return "<" + this.username + ">";
	}

	public MailThread setUsername(String username) {
		this.username = username;
		return this;
	}

	public MailThread setPassword(String password) {
		this.password = password;
		return this;
	}

	public MailThread setBody(String body) {
		this.body = body;
		return this;
	}

	public MailThread setSubject(String subject) {
		this.subject = subject;
		return this;
	}

	public MailThread setTo(ArrayList<String> to) {
		this.to = to;
		return this;
	}

	public MailThread setFrom(String from) {
		this.from = from;
		setEncodeFrom();
		return this;
	}

	private void setEncodeFrom() {
		try {
			this.encodeFrom = encodeMail(this.from);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}		
	}
	
	public MailThread setAction(boolean action) {
		this.action = action;
		return this;
	}

	public MailThread setAttachments(HashMap<String, String> attachments) {
		this.attachments = attachments;
		return this;
	}
	
	public MailThread setSignature(ArrayList<String> signature) {
		this.signature = signature;	
		return this;
	}
	
	
	public boolean isAuthorize() {
		return this.valid;
	}

	public MailThread setNotifyRead(boolean notifyRead) {
		this.notifyRead = notifyRead;
		return this;
	}

	public MailThread setNotifyDelivery(boolean notifyDelivery) {
		this.notifyDelivery = notifyDelivery;
		return this;
	}

	private boolean authorize() {
		this.valid = false;
		if (null != propsSSL) {
			Session session = Session.getInstance(propsSSL, null);
			Transport transport;
			try {
				transport = session.getTransport("smtp");
				transport.connect(MAIL_SERVER, Integer.parseInt(MAIL_PORT), username, password);
				transport.close();
				this.valid = true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return this.valid;
	}

	private Multipart createMail() {
		Multipart multipart = null;
		try {
			multipart = new MimeMultipart();
			MimeBodyPart textBodyPart = new MimeBodyPart();
			if (0!=signature.size()){
				body += "\n\n\n\n\n\n";
				for( String item :signature){
					body += item;
					body += "\n";
				}
			}
			textBodyPart.setDisposition(MimeBodyPart.INLINE);
			textBodyPart.setContent(body.replaceAll("(\r\n|\n)", "<br />"), "text/html; charset=UTF-8");
			multipart.addBodyPart(textBodyPart);
			if (null != attachments && 0 != attachments.size()) {
				for (String attachment : attachments.keySet()) {
					String filePath = attachments.get(attachment);
					if (Files.isRegularFile(Paths.get(filePath))) {
						MimeBodyPart attachmentBodyPart = new MimeBodyPart();
						DataSource source = new FileDataSource(filePath);
						attachmentBodyPart.setDataHandler(new DataHandler(source));						
						attachmentBodyPart.setFileName(mimeDecodeUTF8(attachment));						
						multipart.addBodyPart(attachmentBodyPart);
					}
				}
			}
		} catch (MessagingException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return multipart;
	}

	public void createAttachments() {
		this.attachments = new HashMap<String, String>();
	}

	public boolean isUserMailValid(String username) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(username);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}

		return result;
	}
}
