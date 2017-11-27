package libs;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Hashtable;
import java.util.List;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;
import javax.swing.ImageIcon;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import entity.CompanyDto;
import entity.UserDto;

public class Core {

	private static final String HINT_LDAP_OPEN = "Connecting to LDAP Server";
	private static final String HINT_LDAP_CLOSE = "Close connections";
	private static final String HINT_LDAP_USERS = "Try to get all Users from LDAP Server";
	private static final String HINT_LDAP_COMPANYS = "Try to get all Companys from LDAP Server";
	private static final String HINT_EMPTY = "";

	private Ldap ldap = null;	
	private Companys companys = null;
	
	public String[] toStringArray(List<String> list) {
		return list.toArray(new String[list.size()]);
	}

	private Core getLdapUsers(String $sort) {
		if (ldap.isConnect()) {
			NamingEnumeration<?> users = null;
			for (CompanyDto company : companys.all()) {
				users = ldap.getLdapUsers(company.getDn());
				Attributes attrs;
				if (null != users) {
					while (users.hasMoreElements()) {
						SearchResult sr;
						try {
							sr = (SearchResult) users.next();
							attrs = sr.getAttributes();
							UserDto user = new UserDto(new String(""));
							company.addNewUser(user.deployEntry(attrs, ldap.getDefaultSelectFields()));
						} catch (NamingException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		return this;
	}

	private Core getLdapUsers() {
		return getLdapUsers(new String("cn"));		
	}
	
	private Core getLdapCompanys() {
		if (ldap.isConnect()) {
			NamingEnumeration<?> companys = ldap.getLdapCompanys();
			Attributes attrs;
			if (null != companys) {
				while (companys.hasMoreElements()) {
					SearchResult sr;
					try {
						sr = (SearchResult) companys.next();
						attrs = sr.getAttributes();
						if (null != attrs) {
							Attribute description = attrs.get("description");
							if (null != description) {
								Attribute ou = attrs.get("ou");
								this.companys.addNewCompany((String) description.get(), (String) ou.get(),
										(String) sr.getName() + "," + Ldap.LDAP_BASE_DN);
							}
						}
					} catch (NamingException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return this;
	}
	
	private Core openLdapConnection() {
		ldap = new Ldap();
		
		return this;
	}
	
	private Core closeLdapConnection() {
		if (null != ldap){ 
			ldap.closeConnect();
		}	
		
		return this;
	}

	public Core() {	
		companys = new Companys();
	}	
	
	public Companys getCompanys() {
		return companys;
	}
	
	public String getHintLoadCompanys() {
		return HINT_LDAP_COMPANYS;
	}
	
	public String getHintLoadUsers() {
		return HINT_LDAP_USERS;
	}
	
	public String getHintOpenConnection() {
		return HINT_LDAP_OPEN;
	}
	
	public String getHintCloseConnection() {
		return HINT_LDAP_CLOSE;
	}
	
	public String getHintEmpty() {
		return HINT_EMPTY;
	}
	
	public void loadCompanys() {
		getLdapCompanys();	
	}
	
	public void loadUsers() {
		getLdapUsers();	
	}
		
	public void openConnection() {		
		openLdapConnection();		
	}

	public void closeConnection() {		
		closeLdapConnection();		
	}
	
	public ImageIcon createQrCode(String vcard, int width, int height) {
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
			
		} catch (WriterException e) {
			e.printStackTrace();
		}
		
		return new ImageIcon(image);
	}
}
