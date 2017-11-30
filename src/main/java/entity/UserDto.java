package entity;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.swing.ImageIcon;

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
	private String sAMAccountName = "";
	private ArrayList<String> manager;
	private ArrayList<String> directReports;
	private String itnumber = "";
	private String info = "";
	private BufferedImage jpegPhoto;
	private String fname = "";

	private String lastName = "";
	private String middleName = "";
	private String firstName = "";

	private void createUser(String displayName) {
		this.displayName = displayName;
		this.directReports = new ArrayList<String>();
		this.manager = new ArrayList<String>();
	}

	public UserDto(String displayName) {
		createUser(displayName);
	}

	public UserDto() {
		createUser(new String(""));
	}

	@Override
	public String toString() {
		return displayName;
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

	protected void setsAMAccountName(String sAMAccountName) {
		this.sAMAccountName = paramToString(sAMAccountName);
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

	protected void setJpegPhoto(byte[] stream) {
		try {
			this.jpegPhoto = ImageIO.read(new ByteArrayInputStream(stream));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void setFname(String fname) {
		this.fname = paramToString(fname);
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

	public String getsAMAccountName() {
		return sAMAccountName;
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

	public BufferedImage getJpegPhoto() {
		return jpegPhoto;
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

	public UserDto deployEntry(Attributes entry, String[] fields) throws NamingException {
		for (NamingEnumeration ae = entry.getAll(); ae.hasMore();) {
			Attribute attr = (Attribute) ae.next();
			try {
				setValueUserDto(attr.getID(), attr.getAll());
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}

		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public String getFirstName() {
		return firstName;
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
		return  getDepartment().toLowerCase().contains(department);
	}
	
	public String getVCard() {
		String VCard = new String();
		VCard += "BEGIN:VCARD\n";
		VCard += "VERSION:4.0\n";
		VCard += "N:" + getFirstName() + ";" + getLastName() + "\n";
		VCard += "FN:" + getCn() + "\n";
		VCard += "ORG:" + getCompany() + "\n";
		VCard += "TITLE:" + getDescription() + "\n";
		VCard += "TEL;WORK,VOICE:" + getOtherTelephone() + ((0 == getTelephoneNumber().length()) ? "p*" : "")
				+ getTelephoneNumber() + "\n";
		VCard += "TEL;HOME,VOICE:" + getHomePhone() + "\n";
		VCard += "EMAIL:" + getMail() + "\n";
		VCard += "URL:http://www.ite-ng.ru\n";
		VCard += "END:VCARD";

		return VCard;
	}

	private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
		System.out.println("read userDto");
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
		aOutputStream.writeUTF(sAMAccountName);
		aOutputStream.writeObject(manager);
		aOutputStream.writeObject(directReports);
		aOutputStream.writeUTF(itnumber);
		aOutputStream.writeUTF(info);
		
		/*
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	    ImageIO.write(jpegPhoto, "jpg", buffer);
	    aOutputStream.writeInt(buffer.size());
	    buffer.writeTo(aOutputStream); 
	   */
		
		//private BufferedImage jpegPhoto);		
		aOutputStream.writeUTF(fname);
		aOutputStream.writeUTF(lastName);
		aOutputStream.writeUTF(middleName);
		aOutputStream.writeUTF(firstName);
		
	}
}
