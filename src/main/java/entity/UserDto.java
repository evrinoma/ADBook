package entity;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.apache.commons.codec.binary.Hex;

public class UserDto implements Serializable {
	private String mobile = "";
	private String homePhone = "";
	private String userPassword = "";
	private String postOfficeBox = "";
	private String cn = "";
	private String sn = "";
	private String c = "";
	private String l = "";
	private String st = "";
	private String title = "";
	private String description = "";
	private String postalCode = "";
	private String physicalDeliveryOfficeName = "";
	private String telephoneNumber = "";
	private String givenName = "";
	private String distinguishedName = "";
	private String displayName = "";
	private String otherTelephone = "";
	private String co = "";
	private String department = "";
	private String company = "";
	private String streetAddress = "";
	private String wwwhomepage = "";
	private String userAccountControl = "";
	private String mail = "";
	private String userPrincipalName = "";
	private String SAMAccountName = "";
	private ArrayList<String> manager;
	private ArrayList<String> directReports;
	private String itnumber = "";
	private String info = "";
	private BufferedImage jpegPhoto;
	private String fname = "";

	private String lastName = "";
	private String middleName = "";
	private String firstName = "";

	private String companyDn = "";
	private String pathCacheImage = "";
	
	private void createUser(String displayName, String pathCacheImages) {
		this.displayName = displayName;
		this.directReports = new ArrayList<String>();
		this.manager = new ArrayList<String>();
		this.setPathCacheImage(pathCacheImages);
	}

	public UserDto(String displayName, String pathCacheImages) {
		createUser(displayName, pathCacheImages);
	}

	public UserDto(String pathCacheImages) {
		createUser(new String(""), pathCacheImages);
	}

	@Override
	public String toString() {
		return displayName;
	}

	private void setPathCacheImage(String pathCacheImage) {
		this.pathCacheImage = pathCacheImage;
	}

	private void setLastName(String lastName) {
		this.lastName = paramToString(lastName);
	}

	private void setFirstName(String firstName) {
		this.firstName = paramToString(firstName);
	}

	private void setMiddleName(String middleName) {
		this.middleName = paramToString(middleName);
	}

	private String paramToString(String param) {
		return (null == param) ? "" : param;
	}

	protected void setMobile(String mobile) {
		this.mobile = paramToString(mobile);
	}

	protected void setHomePhone(String homePhone) {
		this.homePhone = paramToString(homePhone);
	}

	protected void setUserPassword(String userPassword) {
		this.userPassword = paramToString(userPassword);
	}

	protected void setPostOfficeBox(String postOfficeBox) {
		this.postOfficeBox = paramToString(postOfficeBox);
	}

	protected void setCn(String cn) {
		if (null != cn) {
			String[] s = cn.split(" ");
			for (int i = 0; i < s.length; i++) {
				switch (i) {
				case 0:
					setLastName(s[i]);
					break;
				case 1:
					setFirstName(s[i]);
					break;
				case 2:
					setMiddleName(s[i]);
					break;
				}
			}
		}
		this.cn = cn;
	}

	protected void setSn(String sn) {
		this.sn = paramToString(sn);
	}

	protected void setC(String c) {
		this.c = paramToString(c);
	}

	protected void setL(String l) {
		this.l = paramToString(l);
	}

	protected void setSt(String st) {
		this.st = paramToString(st);
	}

	protected void setTitle(String title) {
		this.title = paramToString(title);
	}

	protected void setDescription(String description) {
		this.description = paramToString(description);
	}

	protected void setPostalCode(String postalCode) {
		this.postalCode = paramToString(postalCode);
	}

	protected void setPhysicalDeliveryOfficeName(String physicalDeliveryOfficeName) {
		this.physicalDeliveryOfficeName = paramToString(physicalDeliveryOfficeName);
	}

	protected void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = paramToString(telephoneNumber);
	}

	protected void setGivenName(String givenName) {
		this.givenName = paramToString(givenName);
	}

	protected void setDistinguishedName(String distinguishedName) {
		this.distinguishedName = paramToString(distinguishedName);
	}

	protected void setDisplayName(String displayName) {
		this.displayName = paramToString(displayName);
	}

	protected void setOtherTelephone(String otherTelephone) {
		this.otherTelephone = paramToString(otherTelephone);
	}

	protected void setCo(String co) {
		this.co = paramToString(co);
	}

	protected void setDepartment(String department) {
		this.department = paramToString(department);
	}

	protected void setCompany(String company) {
		this.company = paramToString(company);
	}

	protected void setStreetAddress(String streetAddress) {
		this.streetAddress = paramToString(streetAddress);
	}

	protected void setWwwhomepage(String wwwhomepage) {
		this.wwwhomepage = paramToString(wwwhomepage);
	}

	protected void setUserAccountControl(String userAccountControl) {
		this.userAccountControl = paramToString(userAccountControl);
	}

	protected void setMail(String mail) {
		this.mail = paramToString(mail);
	}

	protected void setUserPrincipalName(String userPrincipalName) {
		this.userPrincipalName = paramToString(userPrincipalName);
	}

	protected void setSAMAccountName(String sAMAccountName) {
		this.SAMAccountName = paramToString(sAMAccountName);
	}

	protected void addManager(String manager) {
		this.manager.add(manager);
	}

	protected void addDirectReports(String directReports) {
		this.directReports.add(directReports);
	}

	protected void setItnumber(String itnumber) {
		this.itnumber = paramToString(itnumber);
	}

	protected void setInfo(String info) {
		this.info = paramToString(info);
	}

	private void setJpegPhoto(byte[] stream) {
		try {
			this.jpegPhoto = ImageIO.read(new ByteArrayInputStream(stream));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void clearJpegPhoto() {
		this.jpegPhoto = null;
	}
	
	protected void saveJpegPhotoToCache() {
		if (null != this.getJpegPhoto()) {
			String filePath = getPathCacheImage()+getPathCompanyCacheImage()+"/";			
			new File(filePath).mkdirs();
			filePath += generateImageFileName();
			File file = new File(filePath);
			try {
				ImageIO.write(this.jpegPhoto, "jpg", file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.clearJpegPhoto();
			this.setPathCacheImage(filePath);
		} else {
			this.setPathCacheImage("");
		}
	}

	protected void setFname(String fname) {
		this.fname = paramToString(fname);
	}

	public String getPathCacheImage() {
		return pathCacheImage;
	}
	
	public boolean hasPathCacheImage() {
		return (pathCacheImage.length() != 0);
	}
	
	private String getPathCompanyCacheImage() {
		return generateCompanyPath();
	}	 

	private String generateCompanyPath() {
		String companyPath = this.translit(this.getCompanyDn()).replaceAll(" ", "").replaceAll(",", "")
				.replaceAll("=", "").replaceAll("OU", "").replaceAll("CN", "").replaceAll("DC", "");		
		companyPath = Arrays.toString(companyPath.getBytes()).replaceAll(", ", "").replaceAll("^.|.$", "");
		return new String(companyPath);
	}
	
	private String generateImageFileName() {
		String fileName = this.translit(this.getCn()).replaceAll(" ", "").replaceAll(",", "");
		fileName = Arrays.toString(fileName.getBytes()).replaceAll(", ", "").replaceAll("^.|.$", "");
		return new String(fileName + ".jpg");
	}

	public String getMobile() {
		return mobile;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public String getPostOfficeBox() {
		return postOfficeBox;
	}

	public String getCn() {
		return cn;
	}

	public String getCn(boolean isTranslit) {
		return (isTranslit) ? this.translit(getCn()) : getCn();
	}

	public String getSn() {
		return sn;
	}

	public String getC() {
		return c;
	}

	public String getL() {
		return l;
	}

	public String getSt() {
		return st;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getDescription(boolean isTranslit) {
		return (isTranslit) ? this.translit(getDescription()) : getDescription();
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getPhysicalDeliveryOfficeName() {
		return physicalDeliveryOfficeName;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public String getGivenName() {
		return givenName;
	}

	public String getDistinguishedName() {
		return distinguishedName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getOtherTelephone() {
		return otherTelephone;
	}

	public String getCo() {
		return co;
	}

	public String getDepartment() {
		return department;
	}

	public String getCompany() {
		return company;
	}

	public String getCompany(boolean isTranslit) {
		return (isTranslit) ? this.translit(getCompany()) : getCompany();
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public String getWwwhomepage() {
		return wwwhomepage;
	}

	public String getUserAccountControl() {
		return userAccountControl;
	}

	public String getMail() {
		return mail;
	}

	public String getUserPrincipalName() {
		return userPrincipalName;
	}

	public String getSAMAccountName() {
		return SAMAccountName;
	}

	public ArrayList<String> getManager() {
		return manager;
	}

	public ArrayList<String> getDirectReports() {
		return directReports;
	}

	public String getItnumber() {
		return itnumber;
	}

	public String getInfo() {
		return info;
	}

	private BufferedImage getJpegPhoto() {
		return jpegPhoto;
	}
	
	public String getJpegPhotoFromCache() {
		return getPathCacheImage() + generateImageFileName();
	}

	public String getFname() {
		return fname;
	}

	protected void setValueUserDto(String fieldName, NamingEnumeration values)
			throws IllegalAccessException, InvocationTargetException, NamingException {
		Class<?> objectClass = this.getClass();
		fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		String fieldSetName = "set" + fieldName;
		String fieldAddName = "add" + fieldName;

		for (Method method : objectClass.getDeclaredMethods()) {
			if (fieldSetName.equals(new String("setJpegPhoto"))) {
				for (; values.hasMore(); this.setJpegPhoto((byte[]) values.next()))
					;
			} else if (method.getName().equals(fieldSetName)) {
				for (; values.hasMore(); method.invoke(this, values.next()))
					;
			} else if (method.getName().equals(fieldAddName)) {
				for (; values.hasMore(); method.invoke(this, values.next()))
					;
			}
		}
	}

	public UserDto deployEntry(Attributes entry, String[] fields, String companyDn) throws NamingException {		
		for (NamingEnumeration ae = entry.getAll(); ae.hasMore();) {
			Attribute attr = (Attribute) ae.next();
			try {
				setValueUserDto(attr.getID(), attr.getAll());
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		this.setCompanyDn(companyDn);
		saveJpegPhotoToCache();

		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public String getLastName(boolean isTranslit) {
		return (isTranslit) ? this.translit(getLastName()) : getLastName();
	}

	public String getMiddleName() {
		return middleName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getFirstName(boolean isTranslit) {
		return (isTranslit) ? this.translit(getFirstName()) : getFirstName();
	}

	public boolean isLastName(String lastName) {
		return getLastName().toLowerCase().contains(lastName);
	}

	public boolean isMiddleName(String middleName) {
		return getMiddleName().toLowerCase().contains(middleName);
	}

	public boolean isFirstName(String firstName) {
		return getFirstName().toLowerCase().contains(firstName);
	}

	public boolean isTelephoneNumber(String telephoneNumber) {
		return getTelephoneNumber().toLowerCase().contains(telephoneNumber);
	}

	public boolean isDescription(String description) {
		return getDescription().toLowerCase().contains(description);
	}

	public boolean isDepartment(String department) {
		return getDepartment().toLowerCase().contains(department);
	}

	public boolean isPhysicalDeliveryOfficeName(String room) {
		return getPhysicalDeliveryOfficeName().toLowerCase().contains(room);
	}

	public String getCompanyDn() {
		return companyDn;
	}

	public void setCompanyDn(String companyDn) {
		this.companyDn = companyDn;
	}

	public String getHtmlFormatMail() {
		return new String("<html><a href=\"mailto:" + this.getMail() + "\">" + this.getMail() + "</a></html>");
	}

	public String getFullTelephone() {
		return getFullTelephone(true);
	}

	public String getFullTelephone(boolean maker) {
		return new String(
				this.getOtherTelephone() 
				+ ((0 == this.getOtherTelephone().length()) ? this.getMobile() 
				: ((0 != this.getTelephoneNumber().length()) ?((maker) ? "p*" : "доб."):""))
				+ ((0 != this.getOtherTelephone().length()) ? this.getTelephoneNumber():""));
	}

	public String getVCard(boolean isTranslit) {
		String VCard = new String();
		VCard += "BEGIN:VCARD\n";
		VCard += "VERSION:4.0\n";
		VCard += "N:" + getFirstName(isTranslit) + ";" + getLastName(isTranslit) + "\n";
		VCard += "FN:" + getCn(isTranslit) + "\n";
		VCard += "ORG:" + getCompany(isTranslit) + "\n";
		VCard += "TITLE:" + getDescription(isTranslit) + "\n";
		VCard += "TEL;WORK,VOICE:" + getOtherTelephone() + ((0 == getTelephoneNumber().length()) ? "p*" : "")
				+ getTelephoneNumber() + "\n";
		VCard += "TEL;HOME,VOICE:" + getHomePhone() + "\n";
		VCard += "EMAIL:" + getMail() + "\n";
		VCard += "URL:http://www.ite-ng.ru\n";
		VCard += "END:VCARD";

		return VCard;
	}

	private String alphabet(char ch) {
		switch (ch) {
		case 'А':
			return "A";
		case 'Б':
			return "B";
		case 'В':
			return "V";
		case 'Г':
			return "G";
		case 'Д':
			return "D";
		case 'Е':
			return "E";
		case 'Ё':
			return "JE";
		case 'Ж':
			return "ZH";
		case 'З':
			return "Z";
		case 'И':
			return "I";
		case 'Й':
			return "Y";
		case 'К':
			return "K";
		case 'Л':
			return "L";
		case 'М':
			return "M";
		case 'Н':
			return "N";
		case 'О':
			return "O";
		case 'П':
			return "P";
		case 'Р':
			return "R";
		case 'С':
			return "S";
		case 'Т':
			return "T";
		case 'У':
			return "U";
		case 'Ф':
			return "F";
		case 'Х':
			return "KH";
		case 'Ц':
			return "C";
		case 'Ч':
			return "CH";
		case 'Ш':
			return "SH";
		case 'Щ':
			return "JSH";
		case 'Ъ':
			return "HH";
		case 'Ы':
			return "IH";
		case 'Ь':
			return "JH";
		case 'Э':
			return "EH";
		case 'Ю':
			return "JU";
		case 'Я':
			return "JA";

		case 'а':
			return "a";
		case 'б':
			return "b";
		case 'в':
			return "v";
		case 'г':
			return "g";
		case 'д':
			return "d";
		case 'е':
			return "e";
		case 'ё':
			return "je";
		case 'ж':
			return "zh";
		case 'з':
			return "z";
		case 'и':
			return "i";
		case 'й':
			return "y";
		case 'к':
			return "k";
		case 'л':
			return "l";
		case 'м':
			return "m";
		case 'н':
			return "n";
		case 'о':
			return "o";
		case 'п':
			return "p";
		case 'р':
			return "r";
		case 'с':
			return "s";
		case 'т':
			return "t";
		case 'у':
			return "u";
		case 'ф':
			return "f";
		case 'х':
			return "kh";
		case 'ц':
			return "c";
		case 'ч':
			return "ch";
		case 'ш':
			return "sh";
		case 'щ':
			return "jsh";
		case 'ъ':
			return "hh";
		case 'ы':
			return "ih";
		case 'ь':
			return "jh";
		case 'э':
			return "eh";
		case 'ю':
			return "ju";
		case 'я':
			return "ja";
		default:
			return String.valueOf(ch);
		}
	}

	private String translit(String s) {
		StringBuilder sb = new StringBuilder(s.length() * 2);
		for (char ch : s.toCharArray()) {
			sb.append(alphabet(ch));
		}
		return sb.toString();
	}

	private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
		mobile = aInputStream.readUTF();
		homePhone = aInputStream.readUTF();
		userPassword = aInputStream.readUTF();
		postOfficeBox = aInputStream.readUTF();
		cn = aInputStream.readUTF();
		sn = aInputStream.readUTF();
		c = aInputStream.readUTF();
		l = aInputStream.readUTF();
		st = aInputStream.readUTF();
		title = aInputStream.readUTF();
		description = aInputStream.readUTF();
		postalCode = aInputStream.readUTF();
		physicalDeliveryOfficeName = aInputStream.readUTF();
		telephoneNumber = aInputStream.readUTF();
		givenName = aInputStream.readUTF();
		distinguishedName = aInputStream.readUTF();
		displayName = aInputStream.readUTF();
		otherTelephone = aInputStream.readUTF();
		co = aInputStream.readUTF();
		department = aInputStream.readUTF();
		company = aInputStream.readUTF();
		streetAddress = aInputStream.readUTF();
		wwwhomepage = aInputStream.readUTF();
		userAccountControl = aInputStream.readUTF();
		mail = aInputStream.readUTF();
		userPrincipalName = aInputStream.readUTF();
		SAMAccountName = aInputStream.readUTF();
		manager = (ArrayList<String>) aInputStream.readObject();
		directReports = (ArrayList<String>) aInputStream.readObject();
		itnumber = aInputStream.readUTF();
		info = aInputStream.readUTF();
		fname = aInputStream.readUTF();
		lastName = aInputStream.readUTF();
		middleName = aInputStream.readUTF();
		firstName = aInputStream.readUTF();
		companyDn = aInputStream.readUTF();
		pathCacheImage = aInputStream.readUTF();
		int size = aInputStream.readInt();
		if (0 != size) {
			jpegPhoto = ImageIO.read(aInputStream);
		}
	}

	private void writeObject(ObjectOutputStream aOutputStream) throws IOException {
		aOutputStream.writeUTF(mobile);
		aOutputStream.writeUTF(homePhone);
		aOutputStream.writeUTF(userPassword);
		aOutputStream.writeUTF(postOfficeBox);
		aOutputStream.writeUTF(cn);
		aOutputStream.writeUTF(sn);
		aOutputStream.writeUTF(c);
		aOutputStream.writeUTF(l);
		aOutputStream.writeUTF(st);
		aOutputStream.writeUTF(title);
		aOutputStream.writeUTF(description);
		aOutputStream.writeUTF(postalCode);
		aOutputStream.writeUTF(physicalDeliveryOfficeName);
		aOutputStream.writeUTF(telephoneNumber);
		aOutputStream.writeUTF(givenName);
		aOutputStream.writeUTF(distinguishedName);
		aOutputStream.writeUTF(displayName);
		aOutputStream.writeUTF(otherTelephone);
		aOutputStream.writeUTF(co);
		aOutputStream.writeUTF(department);
		aOutputStream.writeUTF(company);
		aOutputStream.writeUTF(streetAddress);
		aOutputStream.writeUTF(wwwhomepage);
		aOutputStream.writeUTF(userAccountControl);
		aOutputStream.writeUTF(mail);
		aOutputStream.writeUTF(userPrincipalName);
		aOutputStream.writeUTF(SAMAccountName);
		aOutputStream.writeObject(manager);
		aOutputStream.writeObject(directReports);
		aOutputStream.writeUTF(itnumber);
		aOutputStream.writeUTF(info);
		aOutputStream.writeUTF(fname);
		aOutputStream.writeUTF(lastName);
		aOutputStream.writeUTF(middleName);
		aOutputStream.writeUTF(firstName);		
		aOutputStream.writeUTF(companyDn);		
		aOutputStream.writeUTF(pathCacheImage);
		if (null != jpegPhoto) {
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			ImageIO.write(jpegPhoto, "jpg", buffer);
			aOutputStream.writeInt(buffer.size());
			buffer.writeTo(aOutputStream);
		} else {
			aOutputStream.writeInt(0);
		}
	}
}
