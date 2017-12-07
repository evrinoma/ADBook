package forms;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.SwingWorker;

import com.sun.mail.util.MailSSLSocketFactory;

import entity.CompanyDto;
import entity.UserDto;
import libs.Core;

public class MailThread extends SwingWorker<Object, String> {

	public static final String MAIL_SERVER = "mail.ite-ng.ru";
	public static final String MAIL_PORT = "465";
	public static final boolean ACTION_AUTHORIZE = true;
	public static final boolean ACTION_SEND_MAIL = !ACTION_AUTHORIZE;
	public static final String HINT_TRY_AUTH = "Пытаемся авторизоваться на сервере:" + MAIL_SERVER;
	public static final String HINT_TRY_FIND_USER = "Пытаемся найти пользователя...";

	private UserDto user;
	private String username;
	private String password;
	private Session session = null;
	private boolean valid = false;
	private boolean action = ACTION_AUTHORIZE;
	private Properties propsSSL = null;
	private Core core;
	private HashMap<String, String> attachment = null;

	@Override
	protected Boolean doInBackground() throws Exception {
		boolean status = false;
		if (action) {
			publish(HINT_TRY_AUTH);
			if (authorize()) {
				publish(HINT_TRY_FIND_USER);
				findUser();
				status = true;
			}
		}

		return status;
	}

	// Can safely update the GUI from this method.
	protected void done() {
		try {
			// Retrieve the return value of doInBackground.
			if ((boolean) get()) {
				if (action) {
					core.isMailAuthrizeSuccessful();
					core.setStatusString("authorization successful");
				}
			} else {
				core.isMailAuthrizeFail("incorrect password or account name");
			}
			// System.out.println("Completed with status: " + status);
		} catch (InterruptedException e) {
			// This is thrown if the thread's interrupted.
		} catch (ExecutionException e) {
			// This is thrown if we throw an exception
			// from doInBackground.
		}
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
		propsSSL = new Properties();
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
		} catch (GeneralSecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void sendMail() {
		if (this.valid) {
			try {
				session = Session.getInstance(propsSSL, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});
				session.setDebug(true);
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("nikolns@ite-ng.ru"));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("nikolns@ite-ng.ru"));
				message.setSubject("Testing Subject");
				message.setText("Dear Mail Crawler," + "\n\n No spam to my email, please!");

				Transport.send(message);

				System.out.println("Done");

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public String getUserName() {
		if (null != user) {
			return user.getCn() + " <" + user.getMail() + ">";
		} else
			return this.username;
	}

	public MailThread setUsername(String username) {
		this.username = username;
		return this;
	}

	public MailThread setPassword(String password) {
		this.password = password;
		return this;
	}

	public boolean isAuthorize() {
		return this.valid;
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

	private void findUser() {
		user = core.getCompanys().findUserByMail(username);
	}

	public void addAttachment(String fileName, String pathToAttachment) {
		attachment.put(fileName, pathToAttachment);
	}

	public  HashMap<String, String> getAttachments() {
		return attachment;
	}

	public void createAttachments()
	{
		this.attachment = new HashMap<String, String>();
	}
	
}
