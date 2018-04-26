package entity;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class SystemEnv {
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
	private String pathToCache = null;
	/**
	 * автообновление приложение включено
	 */
	private boolean isUpdate = true;
	/**
	 * интервал автообновления в секундах
	 */
	private int timeUpdate = 60;

	private String ldapHost = "ite-ng.ru";
	private String ldapBaseDN = "OU=MSK,DC=ite-ng,DC=ru";
	private String[] ldapHosts = { "ldap://iteng13.ite-ng.ru", "ldap://iteng20.ite-ng.ru" };	
	private String ldapPort = "389";
	private String ldapUser = "ldap@ite-ng.ru";
	private String ldapPass = "ldap";
	private int serverSocketPort = 8749;

	public SystemEnv() {
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
		if (null != System.getProperty("pathToCache")) {
			this.setPathToCache(System.getProperty("pathToCache"));
		}
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
	}

	public void printHelp() {
		System.out.println("Run paramteres");
		System.out.println("java -Dparamter0 -DparamterK=value0 -DparamterN=value0,value1 -jar ADBook.jar");		
		System.out.println("isForce - run ignore has started another version. Default value - ["+this.isForce()+"]");
		System.out.println("isWeb - run application compatible with webswing. Default value - ["+this.isWeb()+"]");
		System.out.println("skin - skin name 'nimbus','gtk'...etc. Default value - ["+this.getSkinName()+"]");
		System.out.println("isUpdate  - run application in autoupdate mode. Default value - ["+this.isUpdate()+"]");
		System.out.println("updateTime - autoupdate as int value in seconds. Default value - ["+this.timeUpdate+"]");
		System.out.println("pathToCache - set to save local path to cache file. Default value - ["+this.getPathToCache()+"]");
		System.out.println("ldapHost - ldap host name. Default value - ["+this.getLdapHost()+"]");
		System.out.println("ldapBaseDN - ldap base DN. Default value - ["+this.getLdapBaseDN()+"]");
		System.out.println("ldapHosts - ldap://server0/ ,ldap://server1/. Default value - ["+String.join(",", this.getLdapHosts())+"]");
		System.out.println("ldapPort - ldap port value. Default value - ["+this.getLdapPort()+"]");
		System.out.println("ldapUser - ldap user name. Default value - ["+this.getLdapUser()+"]");
		System.out.println("ldapPass - ldap user pass. Default value - ["+this.getLdapPass()+"]");
		System.out.println("serverSocketPort - ServerSocket port int value. Default value - ["+this.getServerSocketPort()+"]");
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

	public boolean hasPathToCache() {
		return (pathToCache != null);
	}

	public String getPathToCache() {
		return pathToCache;
	}

	private void setPathToCache(String pathToCache) {
		if (Files.isDirectory(Paths.get(pathToCache))) {
			this.pathToCache = pathToCache;
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
