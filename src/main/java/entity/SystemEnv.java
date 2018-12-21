package entity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class SystemEnv {
	/**
	 * версия
	 */
	private String version = "21.12.18v01";	
	/**
	 * директория для кеша
	 */
	private static final String DIR_CAСHE = "tmp";
	/**
	 * директория для хранения картинок в кеш
	 */	
	private static final String DIR_IMAGES = "images";
	/**
	 * отладака
	 */
	private boolean isDebug = false;
	/**
	 * запуск принудильтельно
	 */
	private boolean isForce = false;
	/**
	 * сборка под под webSwing
	 */
	private boolean isWeb = false;
	/**
	 * задает тип окошек
	 */
	private String skinName = null;
	/**
	 * путь для файла cache
	 */
	private String pathToApp = null;
	/**
	 * автообновление приложение включено
	 */
	private boolean isUpdate = true;
	/**
	 * интервал автообновления в секундах
	 */
	private int timeUpdate = 20;

	private String ldapHost = "ite-ng.ru";
	private String ldapBaseDN = "OU=MSK,DC=ite-ng,DC=ru";
	private String[] ldapHosts = { "ldap://iteng6.ite-ng.ru", "ldap://iteng20.ite-ng.ru" };
	private String ldapPort = "389";
	private String ldapUser = "ldap@ite-ng.ru";
	private String ldapPass = "ldap";
	private int serverSocketPort = 8749;

	public SystemEnv() {
		if (null != System.getProperty("isDebug")) {
			this.setDebug(true);
		}
		if (null != System.getProperty("isForce")) {
			this.setForce(true);
		}
		if (null != System.getProperty("isWeb")) {
			this.setWeb(true);
		}
		if (null != System.getProperty("skin")) {
			this.setSkinName(System.getProperty("skin"));
		}
		if (null != System.getProperty("isUpdate")) {
			this.setUpdate(("true" == System.getProperty("isUpdate")));
			if (null != System.getProperty("updateTime")) {
				this.setTimeUpdate(Integer.parseInt(System.getProperty("updateTime")));
			}
		}
		this.setPathToApp(System.getProperty("pathToCache"));
		if (null != System.getProperty("ldapHost")) {
			this.setLdapHost(System.getProperty("ldapHost"));
		}
		if (null != System.getProperty("ldapBaseDN")) {
			this.setLdapBaseDN(System.getProperty("ldapBaseDN"));
		}
		if (null != System.getProperty("ldapHosts")) {
			this.setLdapHosts(System.getProperty("ldapHosts").split(","));
		}
		if (null != System.getProperty("ldapPort")) {
			this.setLdapPort(System.getProperty("ldapPort"));
		}
		if (null != System.getProperty("ldapUser")) {
			this.setLdapUser(System.getProperty("ldapUser"));
		}
		if (null != System.getProperty("ldapPass")) {
			this.setLdapPass(System.getProperty("ldapPass"));
		}
		if (null != System.getProperty("serverSocketPort")) {
			this.setServerSocketPort(Integer.parseInt(System.getProperty("serverSocketPort")));
		}
		createAppDirs();
	}
	
	private void createAppDirs(){
		new File(getPathToApp()+DIR_CAСHE+"/"+DIR_IMAGES).mkdirs();
	}		
	
	public void printHelp() {
		System.out.println("version - [" + getVersion() + "]");
		System.out.println("Run paramteres");
		System.out.println(
				"java -Xms16m -Xmx512m -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:NewSize=512k -Dparamter0 -DparamterK=value0 -DparamterN=value0,value1 -jar ADBook.jar");
		System.out.println("isDebug - addition information about memory. Default value - [" + this.isDebug() + "]");
		System.out
				.println("isForce - run ignore has started another version. Default value - [" + this.isForce() + "]");
		System.out.println("isWeb - run application compatible with webswing. Default value - [" + this.isWeb() + "]");
		System.out.println("skin - skin name 'nimbus','gtk'...etc. Default value - [" + this.getSkinName() + "]");
		System.out.println("isUpdate  - run application in autoupdate mode. Default value - [" + this.isUpdate() + "]");
		System.out
				.println("updateTime - autoupdate as int value in seconds. Default value - [" + this.timeUpdate + "]");
		System.out.println("pathToCache - set to save local path to cache file. Default value - ["
				+ this.getPathToApp() + "]");
		System.out.println("ldapHost - ldap host name. Default value - [" + this.getLdapHost() + "]");
		System.out.println("ldapBaseDN - ldap base DN. Default value - [" + this.getLdapBaseDN() + "]");
		System.out.println("ldapHosts - ldap://server0/ ,ldap://server1/. Default value - ["
				+ String.join(",", this.getLdapHosts()) + "]");
		System.out.println("ldapPort - ldap port value. Default value - [" + this.getLdapPort() + "]");
		System.out.println("ldapUser - ldap user name. Default value - [" + this.getLdapUser() + "]");
		System.out.println("ldapPass - ldap user pass. Default value - [" + this.getLdapPass() + "]");
		System.out.println(
				"serverSocketPort - ServerSocket port int value. Default value - [" + this.getServerSocketPort() + "]");
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public boolean isDebug() {
		return isDebug;
	}

	public void setDebug(boolean isDebug) {
		this.isDebug = isDebug;
	}

	public boolean isForce() {
		return isForce;
	}

	private void setForce(boolean isForce) {
		this.isForce = isForce;
	}

	public boolean isWeb() {
		return isWeb;
	}

	private void setWeb(boolean isWeb) {
		this.isWeb = isWeb;
	}

	public String getSkinName() {
		return this.skinName;
	}

	public boolean isSkin(String skinName) {
		return (skinName == this.skinName);
	}

	private void setSkinName(String skinName) {
		List<String> skins = Arrays.asList("nimbus", "gtk");
		if (skins.contains(skinName)) {
			this.skinName = skinName;
		} else {
			this.skinName = null;
		}
	}

	public String getPathToApp() {
		return pathToApp;
	}
	
	public String getPathToAppCache() {
		return getPathToApp()+DIR_CAСHE+"/";
	}
	
	public String getPathToAppCacheImages() {
		return getPathToAppCache()+DIR_IMAGES+"/";
	}

	private void setPathToApp(String pathToApp) {
		if (null != pathToApp && Files.isDirectory(Paths.get(pathToApp))) {
			this.pathToApp = pathToApp+"/";
		} else {
			try {
				this.setPathToApp(new java.io.File(".").getCanonicalPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public int getTimeUpdate() {
		return timeUpdate * 1000;
	}

	private void setTimeUpdate(int timeUpdate) {
		this.timeUpdate = timeUpdate;
	}

	public boolean isUpdate() {
		return isUpdate;
	}

	private void setUpdate(boolean isUpdate) {
		this.isUpdate = isUpdate;
	}

	public String getLdapHost() {
		return ldapHost;
	}

	private void setLdapHost(String ldapHost) {
		this.ldapHost = ldapHost;
	}

	public String getLdapBaseDN() {
		return ldapBaseDN;
	}

	private void setLdapBaseDN(String ldapBaseDN) {
		this.ldapBaseDN = ldapBaseDN;
	}

	public String[] getLdapHosts() {
		return ldapHosts;
	}

	private void setLdapHosts(String[] ldapHosts) {
		this.ldapHosts = ldapHosts;
	}

	public String getLdapPort() {
		return ldapPort;
	}

	private void setLdapPort(String ldapPort) {
		this.ldapPort = ldapPort;
	}

	public String getLdapUser() {
		return ldapUser;
	}

	private void setLdapUser(String ldapUser) {
		this.ldapUser = ldapUser;
	}

	public String getLdapPass() {
		return ldapPass;
	}

	private void setLdapPass(String ldapPass) {
		this.ldapPass = ldapPass;
	}

	public int getServerSocketPort() {
		return serverSocketPort;
	}

	private void setServerSocketPort(int serverSocketPort) {
		this.serverSocketPort = serverSocketPort;
	}
}
