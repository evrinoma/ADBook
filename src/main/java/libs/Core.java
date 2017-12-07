package libs;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.ImageIcon;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import entity.CompanyDto;
import entity.UserDto;
import entity.LevelNode;
import forms.LdapSearchThread;
import forms.LoadThread;
import forms.LocalSearchThread;
import forms.MailThread;
import forms.MainForm;
import forms.SaveThread;

public class Core {

	private static final String HINT_EMPTY = "";

	private File fileLock = null;
	RandomAccessFile fileLockAccess = null;
	FileLock lock = null;
	private Companys companys = null;
	private LocalSearchThread localSearch = null;
	private LdapSearchThread ldapSearch = null;
	private LoadThread Load = null;
	private SaveThread saveSearch = null;
	private MailThread mail = null;

	private MainForm form = null;

	public String[] toStringArray(List<String> list) {
		return list.toArray(new String[list.size()]);
	}

	public void setMainForm(MainForm form) {
		this.form = form;
	}

	public Core() {
		companys = new Companys();
	}

	public Companys getCompanys() {
		return companys;
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
		Hashtable hintMap = new Hashtable();
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

	/**
	 * загрузка локального кеша
	 */
	public void localCache(boolean operation) {
		if (null == Load || Load.isDone()) {
			Load = new LoadThread(this);
			Load.setWriteStream(this.companys).setDirection(operation).execute();
		} else {
			Load.execute();
		}
	}

	private void localWriteCache() {
		localCache(LoadThread.WRITE);
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

	public void isLocalCacheFail() {
		ldapSearch();
	}

	/**
	 * выгрузка данных из ldap
	 */
	public void ldapSearch() {
		if (null == ldapSearch || ldapSearch.isDone()) {
			ldapSearch = new LdapSearchThread(this);
			ldapSearch.execute();
		} else {
			ldapSearch.execute();
		}
	}

	/**
	 * выгрузка из ldap успешна
	 * 
	 * @param loadedCompanys
	 */
	private void dataLoad(Companys companys) {
		this.companys = companys;
		form.setCompanySelector();
		form.setTreeNode(getCompanys().all(), true);
		form.removeTreePreload();
		form.setStatusBar(HINT_EMPTY);
	}

	/**
	 * выгрузка из ldap успешна
	 * 
	 * @param loadedCompanys
	 */
	public void isLdapSearchSuccessful(Companys ldapCompanys) {
		dataLoad(ldapCompanys);
		localWriteCache();
	}

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
	 * @param massage
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
	 * @param phone
	 * @param description
	 */
	public void localSearch(String lastName, String firstName, String middleName, CompanyDto company, CompanyDto filial,
			String department, String phone, String pesonPosition) {
		if (null == localSearch || localSearch.isDone()) {

			localSearch = new LocalSearchThread(this);

			localSearch.setFilter(lastName, firstName, middleName, company, filial, department, phone, pesonPosition);
			localSearch.setCompanys(companys);

			localSearch.setLock();
			localSearch.execute();

		} else {
			localSearch.setFilter(lastName, firstName, middleName, company, filial, department, phone, pesonPosition);
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
		form.setTreeNode(filteredCompanys.all(), false);
	}

	/**
	 * метод возвращает список руководителей пользователя
	 * 
	 * @param user
	 * @return
	 */
	public String getUserManger(UserDto user) {
		String managerToString = "";
		for (String dn : user.getManager()) {
			UserDto manager = companys.getUsers().get(dn);
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
		Nodes nodesManager = new Nodes(companys.getUsers());
		return nodesManager.getLevels(user);
	}

	public String toStringUserMails(HashMap<String, UserDto> users) {
		String mails = "";
		for (Entry<String, UserDto> entity : users.entrySet()) {
			UserDto user = entity.getValue();
			mails += user.getMail();
			mails += ";";
		}
		
		return mails;
	}
	
	public void putStringToClipboard(String data)
	{
		StringSelection stringSelection = new StringSelection(data);
		Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
		clpbrd.setContents(stringSelection, null);
	}

	public boolean isRunningProcess(String Name) {
		boolean status = true;
		FileChannel channel = null;
		FileLock lock = null;

		try {
			fileLock = new File(System.getProperty("user.home"), Name + ".tmp");
			fileLockAccess = new RandomAccessFile(fileLock, "rw");
			channel = fileLockAccess.getChannel();
			lock = channel.tryLock();
			if (lock != null) {
				status = false;
			} else {
				System.out.println("Another instance is already running");
			}
		} finally {
			return status;
		}
	}

	public void removeRunningProcess() {
		try {
			if (lock != null && lock.isValid())
				lock.release();
			if (fileLockAccess != null)
				fileLockAccess.close();
			fileLock.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean isUserMailValid(String username) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(username);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}

		return result;
	}

	public void authorizeOnMail(String username, String password) {
		if (null == mail || mail.isDone()) {
			if (isUserMailValid(username)) {
				mail = new MailThread(this);
				mail.setUsername(username).setPassword(password);
				mail.execute();
			} else {
				isMailAuthrizeFail("username is invalid");
			}
		} else {
			mail.execute();
		}
	}

	public void isMailAuthrizeSuccessful() {
		form.removeMessagePreload();
		form.showMessageEditorPanel();
	}

	public void isMailAuthrizeFail(String status) {
		setStatusString(status);
		form.removeMessagePreload();

	}

	public String getMailAthorizedUser() {
		return (mail.isAuthorize()) ? mail.getUserName() : "";
	}
	
	public HashMap<String, String> addMailAttachmet(String fileName, String pathToAttachment){		
		mail.addAttachment(fileName, pathToAttachment);
		return mail.getAttachments();
	}
	
	public void clearMailAttachmet()
	{
		mail.createAttachments();
	}
}
