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
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.SwingWorker;

import com.sun.mail.util.MailSSLSocketFactory;

import entity.CompanyDto;
import entity.UserDto;
import libs.Core;

public class MailThread extends SwingWorker<Object, String> {

	public static final boolean ACTION_AUTHORIZE = true;
	public static final boolean ACTION_SEND_MAIL = !ACTION_AUTHORIZE;
	private static final String MAIL_SERVER = "mail.ite-ng.ru";
	private static final String MAIL_PORT = "465";
	private static final String HINT_TRY_AUTH = "Пытаемся авторизоваться на сервере:" + MAIL_SERVER;
	private static final String HINT_TRY_FIND_USER = "Пытаемся найти пользователя...";
	private static final String HINT_TRY_SEND = "Пытаемся послать почту на сервер:" + MAIL_SERVER;
	private static final String HINT_DONE_SEND = "Сообщения отправлены";
	private static final String HINT_AUTH_ERROR = "incorrect password or account name";
	private static final String HINT_AUTH = "authorization successful";

	private UserDto user;
	private String username;
	private String password;
	private Session session = null;
	private boolean valid = false;
	private boolean action = ACTION_AUTHORIZE;
	private Properties propsSSL = null;
	private Core core;
	private HashMap<String, String> attachment = null;

	private String body = "";
	private String subject = "";
	private ArrayList<String> to = null;
	private ArrayList<String> toError = null;
	private String from = "";

	@Override
	protected Boolean doInBackground() throws Exception {
		boolean status = false;

		publish(HINT_TRY_AUTH);
		if (authorize()) {
			publish(HINT_TRY_FIND_USER);
			findUser();
			if (action == ACTION_SEND_MAIL) {
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

	private void sendMessages() {
		for (String mail : to) {
			if (!sendMail(mail)) {
				toError.add(mail);
			}
		}
	}

	private boolean sendMail(String to) {
		boolean status = false;
		if (this.valid) {
			try {
				session = Session.getInstance(propsSSL, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(from));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
				message.setSubject(subject);
				message.setText(body);

				// Transport.send(message);
				status = true;
			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		}
		return status;
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
		return this;
	}

	public MailThread setAction(boolean action) {
		this.action = action;
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

	public void setAttachments(HashMap<String, String> attachment) {
		this.attachment = attachment;
	}

	public void createAttachments() {
		this.attachment = new HashMap<String, String>();
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
