package entity;

import java.util.Arrays;
import java.util.List;

public class SystemEnv {
	private boolean isWeb = false;
	private String skinName = null;
	private String pathToCache = null;
	private int timeUpdate = 300;

	public SystemEnv() {
		if (null != System.getProperty("isWeb")) {
			this.setWeb(isWeb);
		}
		if (null != System.getProperty("skin")) {
			String property = System.getProperty("skin");
			this.setSkinName(property);
		}
		if (null != System.getProperty("updateTime")) {
			int property = Integer.parseInt(System.getProperty("updateTime"));
			this.setTimeUpdate(property);
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
		this.pathToCache = pathToCache;
	}

	public int getTimeUpdate() {
		return timeUpdate;
	}

	private void setTimeUpdate(int timeUpdate) {
		this.timeUpdate = timeUpdate;
	}
}
