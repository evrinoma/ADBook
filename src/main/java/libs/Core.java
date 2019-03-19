package libs;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.channels.FileLock;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import entity.*;
import threads.LdapSearchThread;
import threads.LoadThread;
import threads.LocalSearchThread;
import threads.MailThread;
import forms.MainForm;
import threads.SaveThread;
import threads.ServerSocketThread;

public class Core {

	private static final String HINT_EMPTY = "";
	public static final int TREAD_LOCAL_SEARCH = 0;
	public static final int TREAD_LDAP_SEARCH = 1;
	public static final int TREAD_LOAD = 2;
	public static final int TREAD_SAVE_SEARCH = 3;
	public static final int TREAD_MAIL = 4;

	RandomAccessFile fileLockAccess = null;
	FileLock lock = null;
	private Companys companys = null;
	private UserDto user = null;
	private LocalSearchThread localSearch = null;
	private LdapSearchThread ldapSearch = null;
	private LoadThread Load = null;
	private SaveThread saveSearch = null;
	private MailThread mail = null;

	private MainForm form = null;
	private HashMap<String, String> attachment = null;

	private boolean close = false;

	private ServerSocketThread serverSocket;

	private SystemEnv environment = null;

	public String[] toStringArray(List<String> list) {
		return list.toArray(new String[list.size()]);
	}

	public void setMainForm(MainForm form) {
		this.form = form;
	}

	public Core() {
		companys = new Companys();
		this.environment = new SystemEnv();
		this.environment.printHelp();
		clearMailAttachmet();
	}

	public Companys getCompanys() {
		return companys;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public SystemEnv getSystemEnv() {
		return this.environment;
	}

	/**
	 * метод создает картинку qr-code с данными
	 * 
	 * @param vcard
	 * @param width
	 * @param height
	 * @return
	 */
	public ImageIcon createQrCodeWithLogo(URL url, String vcard, int width, int height) {
		Hashtable<EncodeHintType, String> hintMap = new Hashtable<EncodeHintType, String>();
		hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");

		QRCodeWriter qrCodeWriter = new QRCodeWriter();

		int matrixWidth = width;
		int matrixHeight = height;

		BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
		image.createGraphics();
		Graphics2D graphics = (Graphics2D) image.getGraphics();
		BitMatrix bitMatrix;

		try {
			bitMatrix = qrCodeWriter.encode(vcard, BarcodeFormat.QR_CODE, matrixWidth, matrixHeight, hintMap);

			graphics.setColor(Color.white);
			graphics.fillRect(0, 0, matrixWidth, matrixWidth);

			Color mainColor = new Color(51, 102, 153);
			graphics.setColor(mainColor);

			for (int i = 0; i < matrixWidth; i++) {
				for (int j = 0; j < matrixWidth; j++) {
					if (bitMatrix.get(i, j)) {
						graphics.fillRect(i, j, 1, 1);
					}
				}
			}

			if (null != url) {

				BufferedImage logo = ImageIO.read(url);

				double scale = ((logo.getWidth() / image.getWidth()) > 0.3) ? 0.3 : 1;
				logo = getScaledImage(logo, (int) (logo.getWidth() * scale), (int) (logo.getHeight() * scale));
				graphics.drawImage(logo, image.getWidth() / 2 - logo.getWidth() / 2,
						image.getHeight() / 2 - logo.getHeight() / 2, image.getWidth() / 2 + logo.getWidth() / 2,
						image.getHeight() / 2 + logo.getHeight() / 2, 0, 0, logo.getWidth(), logo.getHeight(), null);

			}
		} catch (IOException | WriterException e) {
			e.printStackTrace();
		}

		return new ImageIcon(image);
	}

	private BufferedImage getScaledImage(BufferedImage image, int width, int height) throws IOException {
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();

		double scaleX = (double) width / imageWidth;
		double scaleY = (double) height / imageHeight;
		AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
		AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);

		return bilinearScaleOp.filter(image, new BufferedImage(width, height, image.getType()));
	}

	/**
	 * загрузка данных в приложение
	 */
	public void loadData() {
		loadData(true);
	}

	public void loadData(boolean fromCache) {
		if (fromCache) {
			localReadCache();
		} else {
			ldapSearch();
		}
	}

	private void garbageCollector() {
		int mb = 1024 * 1024;

		// Getting the runtime reference from system
		Runtime runtime = Runtime.getRuntime();

		System.out.println("##### Heap utilization statistics [MB] #####");

		// Print used memory
		System.out.println("Used Memory:" + (runtime.totalMemory() - runtime.freeMemory()) / mb);

		// Print free memory
		System.out.println("Free Memory:" + runtime.freeMemory() / mb);

		// Print total available memory
		System.out.println("Total Memory:" + runtime.totalMemory() / mb);

		// Print Maximum available memory
		System.out.println("Max Memory:" + runtime.maxMemory() / mb);
	}

	/**
	 * загрузка локального кеша
	 */
	public void localCache(boolean operation) {
		if (null == Load || Load.isDone()) {
			Load = new LoadThread(this);
			Load.setDirection(operation).execute();
		} else {
			Load.execute();
		}
	}

	private void localWriteCache() {
		localCache(!LoadThread.READ);
	}

	private void localReadCache() {
		localCache(LoadThread.READ);
	}

	/**
	 * 
	 * @param loadCompanys
	 */
	public void isLocalCacheSuccessful(Companys loadCompanys) {
		dataLoad(loadCompanys);
	}

	public void isLocalCacheReadFail() {
		ldapSearch();
	}

	public void flushing(int key) {
		if (this.getSystemEnv().isDebug()) {
			garbageCollector();
		}
		switch (key) {
		case TREAD_LOCAL_SEARCH:
			if (localSearch instanceof LocalSearchThread) {
				localSearch.destory();
			}
			localSearch = null;
			break;
		case TREAD_LDAP_SEARCH:
			ldapSearch = null;
			break;
		case TREAD_LOAD:
			Load = null;
			break;
		case TREAD_SAVE_SEARCH:
			saveSearch = null;
			break;
		case TREAD_MAIL:
			if (mail instanceof MailThread) {
				mail.destory();
			}
			mail = null;
			break;
		}
		System.gc();
	}
	
	public void flushing() {
		flushing(-1);
	}

	/**
	 * выгрузка данных из ldap
	 */
	private void ldapSearch() {
		if (null == ldapSearch || ldapSearch.isDone()) {
			while (getSystemEnv().hasNext()) {
				ldapSearch = null;
				ldapSearch = new LdapSearchThread(this, getSystemEnv().getNext());
				ldapSearch.execute();
			}
		} else {
			ldapSearch.execute();
		}
	}

	/**
	 * выгрузка из ldap успешна
	 * 
	 * @param companys
	 */
	private void dataLoad(Companys companys) {
		this.companys = companys;
		form.setCompanySelector();
		form.setTreeNode(getCompanys().all(), true);
		form.removeTreePreload();
		form.setStatusBar(HINT_EMPTY);
		form.setLoginField();
	}

	/**
	 * выгрузка из ldap успешна
	 * 
	 * @param ldapCompanys
	 */
	public void isLdapSearchSuccessful(Companys ldapCompanys) {
		dataLoad(ldapCompanys);
		localWriteCache();
	}

	/**
	 * сохранение в xls
	 * 
	 * @param file
	 */
	public void saveToFile(String file) {
		if (null == saveSearch || saveSearch.isDone()) {
			saveSearch = new SaveThread(this);
			saveSearch.setFileName(file);
			saveSearch.execute();
		}
	}

	/**
	 * печать в строку состояния из потоков
	 * 
	 * @param message
	 */
	public void setStatusString(String message) {
		form.setStatusBar(message);
	}

	/**
	 * фильтр по параметрам заданным в фильтре
	 *
	 * @param lastName
	 * @param firstName
	 * @param middleName
	 * @param company
	 * @param filial
	 * @param department
	 * @param phone
	 * @param pesonPosition
	 * @param room
	 */
	public void localSearch(String lastName, String firstName, String middleName, CompanyDto company, CompanyDto filial,
			String department, String phone, String pesonPosition, String room) {
		if (null == localSearch || localSearch.isDone()) {

			localSearch = new LocalSearchThread(this);

			localSearch.setFilter(lastName, firstName, middleName, company, filial, department, phone, pesonPosition,
					room);
			localSearch.setCompanys(companys);

			localSearch.setLock();
			localSearch.execute();
		} else {
			localSearch.setFilter(lastName, firstName, middleName, company, filial, department, phone, pesonPosition,
					room);
			localSearch.setCompanys(companys);
			localSearch.restartSearch();
		}
	}

	/**
	 * локальный поиск успешный
	 * 
	 * @param filteredCompanys
	 */
	public void isLocalSearchSuccessful(Companys filteredCompanys) {
		form.getTopTree().removeAllChildren();
		if (null != filteredCompanys) {
			form.setTreeNode(filteredCompanys.all(), false);
		}
	}

	/**
	 * метод возвращает список руководителей пользователя
	 * 
	 * @param user
	 * @return
	 */
	public String getUserManger(UserDto user) {
		String managerToString = "";
		for (String distinguishedName : user.getManager()) {
			UserDto manager = companys.findUserByDistinguishedName(distinguishedName);
			if (null != manager) {
				managerToString += manager.getCn() + "\n";
			}
		}

		return managerToString;
	}

	/**
	 * метод возвращет список подчиненных с главным узлом в виде пользователя
	 * 
	 * @param user
	 * @return
	 */
	public HashMap<Integer, ArrayList<LevelNode>> getUserDependency(UserDto user) {
		Nodes nodesManager = new Nodes(this);
		return nodesManager.getLevels(user);
	}

	public String toStringUserMails(HashMap<String, UserDto> users) {
		String mails = "";
		for (Entry<String, UserDto> entity : users.entrySet()) {
			UserDto user = entity.getValue();
			mails += user.getMail();
			mails += ",";
		}

		return mails.substring(0, mails.length() - 1);
	}

	public void putStringToClipboard(String data) {
		StringSelection stringSelection = new StringSelection(data);
		Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
		clpbrd.setContents(stringSelection, null);
	}

	public void authorizeOnMail(String username, String password) {
		if (null == mail || mail.isDone()) {
			mail = new MailThread(this);
			if (mail.isUserMailValid(username)) {
				mail.setUsername(username).setPassword(password);
				mail.execute();
			} else {
				isMailAuthrizeFail("username is invalid");
			}
		} else {
			mail.execute();
		}
	}

	public void isMailAuthrizeSuccessful(String authUser) {
		form.removeMessagePreload();
		form.showMessageEditorPanel(authUser);
		form.repaint();
	}

	public void isMailAuthrizeFail(String status) {
		setStatusString(status);
		form.removeMessagePreload();
		form.repaint();
	}

	public void isMailSendedSuccessful(String authUser, ArrayList<String> emails) {
		form.removeMessagePreload();
		form.showMessageEditorPanel(authUser);
		form.clearMessagesByNotify();
		form.repaint();
	}

	/**
	 * добавление вложений к письму
	 * 
	 * @param fileName
	 * @param pathToAttachment
	 * @return
	 */
	public HashMap<String, String> addMailAttachmet(String fileName, String pathToAttachment) {
		if (null == attachment) {
			this.attachment = new HashMap<String, String>();
		}
		if (null == attachment.get(fileName)) {
			attachment.put(fileName, pathToAttachment);
		}
		return attachment;
	}

	/**
	 * очистка вложений
	 */
	public void clearMailAttachmet() {
		this.attachment = null;
	}

	/**
	 * проверка правильности почтового ящика
	 * 
	 * @param username
	 * @return
	 */
	public boolean isMailValid(String username) {
		MailThread mail = new MailThread(this);

		return mail.isUserMailValid(username);
	}

	public void sendMessage(String from, ArrayList<String> to, String subject, String body, String username,
			String password, boolean withSignature, boolean notifyDelivery, boolean notifyRead) {
		if (null == mail || mail.isDone()) {
			mail = new MailThread(this);
			mail.setAction(!MailThread.ACTION_AUTHORIZE).setUsername(username).setPassword(password).setFrom(from)
					.setTo(to).setSubject(subject).setBody(body).setAttachments(attachment)
					.setNotifyDelivery(notifyDelivery).setNotifyRead(notifyRead);
			if (withSignature) {
				mail.setSignature(signature(companys.findUserByMail(username)));
			}
			mail.execute();
		} else {
			mail.execute();
		}
	}

	public ArrayList<String> signature(UserDto user) {
		ArrayList<String> signature = new ArrayList<String>();

		if (null != user) {
			signature.add("--------------------------------------");
			signature.add("С уважением, " + user.getCn());
			signature.add("<br><br>");
			signature.add(user.getDescription());
			signature.add("<br>");
			signature.add(user.getDepartment());
			signature.add("<br>");
			signature.add(user.getCompany());
			signature.add("<br>");
			signature.add("телефон:" + user.getFullTelephone(false));
			signature.add("<br>");
			signature.add("моб.:" + user.getMobile());
			signature.add("<br>");
			signature.add("<i>email:" + user.getMail() + "</i>");
		}

		return signature;
	}

	public void close() {
		close = true;
	}

	public boolean isClose() {
		return close;
	}

	public void runServerSocket() {
		if (null == serverSocket || serverSocket.isDone()) {
			serverSocket = new ServerSocketThread(this);
			serverSocket.execute();
		}
	}

	public boolean sendServerSocket() {
		serverSocket = new ServerSocketThread(this);
		boolean status = serverSocket.startClient();
		serverSocket = null;

		return status;
	}

	public void expandWindow() {
		form.removeTray();
	}

	public void callByTimer() {
		isOutdated();
	}

	/**
	 * проверка на актуальность списка компаний
	 */
	private void isOutdated() {
		if (null != this.companys & this.companys.isOutDate()) {
			form.addTreePreloader();
			loadData(false);
		}
	}
}
