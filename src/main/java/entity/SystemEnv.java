package entity;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class SystemEnv {
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
	private int timeUpdate = 3;

	private String ldapHost = "ite-ng.ru";
	private String ldapBaseDN = "OU=MSK,DC=ite-ng,DC=ru";
	private String[] ldapHosts = { "ldap://iteng13.ite-ng.ru", "ldap://iteng20.ite-ng.ru" };
	private String ldapPort = "389";
	private String ldapUser = "ldap@ite-ng.ru";
	private String ldapPass = "ldap";

	public SystemEnv() {
		if (null != System.getProperty("isWeb")) {
			this.setWeb(true);
		}
		if (null != System.getProperty("skin")) {
			String property = System.getProperty("skin");
			this.setSkinName(property);
		}
		if (null != System.getProperty("isUpdate")) {
			this.setUpdate(true);
			if (null != System.getProperty("updateTime")) {
				int property = Integer.parseInt(System.getProperty("updateTime"));
				this.setTimeUpdate(property);
			}
		}
		if (null != System.getProperty("ldapHost")) {
			String property = System.getProperty("ldapHost");
			this.setLdapHost(property);
		}
		if (null != System.getProperty("ldapBaseDN")) {
			String property = System.getProperty("ldapBaseDN");
			this.setLdapBaseDN(property);
		}
		if (null != System.getProperty("ldapHosts")) {
			String property = System.getProperty("ldapHosts");
			this.setLdapHosts(property.split(","));
		}
		if (null != System.getProperty("ldapPort")) {
			String property = System.getProperty("ldapPort");
			this.setLdapPort(property);
		}
		if (null != System.getProperty("ldapUser")) {
			String property = System.getProperty("ldapUser");
			this.setLdapUser(property);
		}
		if (null != System.getProperty("ldapPass")) {
			String property = System.getProperty("ldapPass");
			this.setLdapPass(property);
		}
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
}
