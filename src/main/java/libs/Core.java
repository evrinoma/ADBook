package libs;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
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
import forms.MainForm;

public class Core {
	
	private static final String HINT_EMPTY = "";
	
	private Companys companys = null;
	private LocalSearchThread localSearch = null;
	private LdapSearchThread ldapSearch = null;
	private LoadThread Load = null;

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
			logo = getScaledImage( logo,
			 		(int)( logo.getWidth() * scale),
			 		(int)( logo.getHeight() * scale) );        
         graphics.drawImage( logo,
        		 image.getWidth()/2 - logo.getWidth()/2,
        		 image.getHeight()/2 - logo.getHeight()/2,
         		image.getWidth()/2 + logo.getWidth()/2,
         		image.getHeight()/2 + logo.getHeight()/2,
         		0, 0, logo.getWidth(), logo.getHeight(), null);
         
			}
		} catch (IOException | WriterException e) {
			e.printStackTrace();
		}
		
         return new ImageIcon(image);
	}

	
	private BufferedImage getScaledImage(BufferedImage image, int width, int height) throws IOException {
		int imageWidth  = image.getWidth();
	    int imageHeight = image.getHeight();

	    double scaleX = (double)width/imageWidth;
	    double scaleY = (double)height/imageHeight;
	    AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
	    AffineTransformOp bilinearScaleOp = new AffineTransformOp(
	    		scaleTransform, AffineTransformOp.TYPE_BILINEAR);

	    return bilinearScaleOp.filter(
	        image,
	        new BufferedImage(width, height, image.getType()));
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
		if (null == Load  || Load.isDone()) {
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
	public void isLocalCacheSuccessful(Companys loadCompanys)
	{
		dataLoad(loadCompanys);
	}
	
	public void isLocalCacheFail()
	{
		ldapSearch();
	}
	
	/**
	 * выгрузка данных из ldap
	 */
	public void ldapSearch() {
		if (null == ldapSearch  || ldapSearch.isDone()) {
			ldapSearch = new LdapSearchThread(this);
			ldapSearch.execute();
		} else {		
			ldapSearch.execute();
		}
	}
	
	/**
	 * выгрузка из ldap успешна
	 * @param loadedCompanys
	 */
	private void dataLoad(Companys companys)
	{
		this.companys = companys;
		form.setCompanySelector();
		form.setTreeNode(getCompanys().all(),true);
		form.removeTreePreload();
		form.setStatusBar(HINT_EMPTY);
	}
	

	/**
	 * выгрузка из ldap успешна
	 * @param loadedCompanys
	 */
	public void isLdapSearchSuccessful(Companys ldapCompanys)
	{
		dataLoad(ldapCompanys);
		localWriteCache();
	}
	
	
	/**
	 * печать в строку состояния из потоков
	 * @param massage
	 */
	public void setStatusString(String message)
	{
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
	 * @param filteredCompanys
	 */
	public void isLocalSearchSuccessful(Companys filteredCompanys)
	{
		form.getTopTree().removeAllChildren();
		form.setTreeNode(filteredCompanys.all(), false);
	}

	/**
	 * метод возвращает список руководителей пользователя
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
	
	
	public void getCopyDataToBuffer(HashMap<String, UserDto> users)
	{		
		String mails = "";
		
		for( Entry<String, UserDto> entity :users.entrySet())
		{
			UserDto user = entity.getValue();
			mails += user.getMail();
			mails +=";";
		}
		
		StringSelection stringSelection = new StringSelection(mails);		
		Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
		clpbrd.setContents(stringSelection, null);
		
	}
}
