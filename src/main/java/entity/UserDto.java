package entity;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

public class UserDto {
	private String mobile;
	private String homePhone;
	private String userPassword;
	private String postOfficeBox;
	private String cn;
	private String sn;
	private String c;
	private String l;
	private String st;
	private String title;
	private String description;
	private String postalCode;
	private String physicalDeliveryOfficeName;
	private String telephoneNumber;
	private String givenName;
	private String distinguishedName;
	private String displayName;
	private String otherTelephone;
	private String co;
	private String department;
	private String company;
	private String streetAddress;
	private String wwwhomepage;
	private String userAccountControl;
	private String mail;
	private String userPrincipalName;
	private String sAMAccountName;
	private String manager;
	private ArrayList<String> directReports;
	private String itnumber;
	private String info;
	private BufferedImage jpegPhoto;
	private String fname;

	private String lastName = "";
	private String middleName = "";
	private String firstName = "";
	
	public UserDto(String displayName) {
		this.displayName = displayName;
		this.directReports = new ArrayList<String>();
	}

	@Override
	public String toString() {
		return displayName;
	}

	private void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	
	private void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	
	private void setMiddleName(String middleName)
	{
		this.middleName = middleName;
	}
	
	private String paramToString(String param) {
		return (null == param) ? "" : param;
	}
	
	protected void setMobile(String mobile) {		
		this.mobile = mobile;
	}

	protected void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	protected void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	protected void setPostOfficeBox(String postOfficeBox) {
		this.postOfficeBox = postOfficeBox;
	}
	
	protected void setCn(String cn) {		
		if (null != cn) {
			String[] s = cn.split(" ");
			for (int i=0; i<s.length; i++) {
				 switch (i) {
		            case 0:  setLastName(s[i]);
		                     break;
		            case 1:  setFirstName(s[i]);
		                     break;		                     
		            case 2:  setMiddleName(s[i]);
		                     break;
		        }
			}
		}
		this.cn = cn;
	}

	protected void setSn(String sn) {
		this.sn = sn;
	}

	protected void setC(String c) {
		this.c = c;
	}

	protected void setL(String l) {
		this.l = l;
	}

	protected void setSt(String st) {
		this.st = st;
	}

	protected void setTitle(String title) {
		this.title = title;
	}

	protected void setDescription(String description) {
		this.description = description;
	}

	protected void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	protected void setPhysicalDeliveryOfficeName(String physicalDeliveryOfficeName) {
		this.physicalDeliveryOfficeName = physicalDeliveryOfficeName;
	}

	protected void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	protected void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	protected void setDistinguishedName(String distinguishedName) {
		this.distinguishedName = distinguishedName;
	}

	protected void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	protected void setOtherTelephone(String otherTelephone) {
		this.otherTelephone = otherTelephone;
	}

	protected void setCo(String co) {
		this.co = co;
	}

	protected void setDepartment(String department) {
		this.department = department;
	}

	protected void setCompany(String company) {
		this.company = company;
	}

	protected void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	protected void setWwwhomepage(String wwwhomepage) {
		this.wwwhomepage = wwwhomepage;
	}

	protected void setUserAccountControl(String userAccountControl) {
		this.userAccountControl = userAccountControl;
	}

	protected void setMail(String mail) {
		this.mail = mail;
	}

	protected void setUserPrincipalName(String userPrincipalName) {
		this.userPrincipalName = userPrincipalName;
	}

	protected void setsAMAccountName(String sAMAccountName) {
		this.sAMAccountName = sAMAccountName;
	}

	protected void setManager(String manager) {
		this.manager = manager;
	}

	protected void addDirectReports(String directReports) {
		this.directReports.add(directReports);
	}

	protected void setItnumber(String itnumber) {
		this.itnumber = itnumber;
	}

	protected void setInfo(String info) {
		this.info = info;
	}

	protected void setJpegPhoto(byte[] stream) {
		try {
			this.jpegPhoto = ImageIO.read(new ByteArrayInputStream(stream));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void setFname(String fname) {
		this.fname = fname;
	}
	
	public String getMobile() {
		return paramToString(mobile);
	}

	public String getHomePhone() {
		return paramToString(homePhone);
	}

	public String getUserPassword() {
		return paramToString(userPassword);
	}

	public String getPostOfficeBox() {
		return paramToString(postOfficeBox);
	}

	public String getCn() {
		return paramToString(cn);
	}

	public String getSn() {
		return paramToString(sn);
	}

	public String getC() {
		return paramToString(c);
	}

	public String getL() {
		return paramToString(l);
	}

	public String getSt() {
		return paramToString(st);
	}

	public String getTitle() {
		return paramToString(title);
	}

	public String getDescription() {
		return paramToString(description);
	}

	public String getPostalCode() {
		return paramToString(postalCode);
	}

	public String getPhysicalDeliveryOfficeName() {
		return paramToString(physicalDeliveryOfficeName);
	}

	public String getTelephoneNumber() {
		return paramToString(telephoneNumber);
	}

	public String getGivenName() {
		return paramToString(givenName);
	}

	public String getDistinguishedName() {
		return paramToString(distinguishedName);
	}

	public String getDisplayName() {
		return paramToString(displayName);
	}

	public String getOtherTelephone() {
		return paramToString(otherTelephone);
	}

	public String getCo() {
		return paramToString(co);
	}

	public String getDepartment() {
		return paramToString(department);
	}

	public String getCompany() {
		return paramToString(company);
	}

	public String getStreetAddress() {
		return paramToString(streetAddress);
	}

	public String getWwwhomepage() {
		return paramToString(wwwhomepage);
	}

	public String getUserAccountControl() {
		return paramToString(userAccountControl);
	}

	public String getMail() {
		return paramToString(mail);
	}

	public String getUserPrincipalName() {
		return paramToString(userPrincipalName);
	}

	public String getsAMAccountName() {
		return paramToString(sAMAccountName);
	}

	public String getManager() {
		return paramToString(manager);
	}

	public ArrayList<String> getDirectReports() {
		return directReports;
	}

	public String getItnumber() {
		return paramToString(itnumber);
	}

	public String getInfo() {
		return paramToString(info);
	}

	public BufferedImage getJpegPhoto() {
		return jpegPhoto;
	}

	public String getFname() {
		return paramToString(fname);
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
		return paramToString(lastName);
	}

	public String getMiddleName() {
		return paramToString(middleName);
	}

	public String getFirstName() {
		return paramToString(firstName);
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
	
	public String getVCard() {
		String VCard = new String();
		VCard += "BEGIN:VCARD\n";
		VCard += "VERSION:4.0\n";
		VCard += "N:" + getFirstName() + ";" + getLastName() + "\n";
		VCard += "FN:" + getCn() + "\n";
		VCard += "ORG:" + getCompany() + "\n";
		VCard += "TITLE:" + getDescription() + "\n";
		VCard += "TEL;WORK,VOICE:" + getOtherTelephone() + ((0 == getTelephoneNumber().length()) ? "p*" : "")+ getTelephoneNumber() + "\n";
		VCard += "TEL;HOME,VOICE:" + getHomePhone() + "\n";
		VCard += "EMAIL:" + getMail() + "\n";
		VCard += "URL:http://www.ite-ng.ru\n";
		VCard += "END:VCARD";

		return VCard;
	}
}
