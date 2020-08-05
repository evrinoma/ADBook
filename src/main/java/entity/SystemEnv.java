package entity;

import interfaces.SettingsRecordIterator;
import presets.IteConnectDescriber;
import libs.AbstractConnectDescriber;
import presets.KpszConnectDescriber;
import presets.UrConnectDescriber;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SystemEnv implements SettingsRecordIterator {
    /**
     * версия
     */
    private String version = "05.08.20v01";
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
    /**
     * указатель на настройки
     */
    private int point = 0;
    /**
     * неправильно заданы настройки поэтому выходим
     */
    private boolean error = false;

    private ArrayList<AbstractConnectDescriber> connectionDescriber = new ArrayList<AbstractConnectDescriber>();

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
        if (null != System.getProperty("serverSocketPort")) {
            this.setServerSocketPort(Integer.parseInt(System.getProperty("serverSocketPort")));
        }

        initDefautServers();

//        if (null != System.getProperty("ldap")) {
//            if (null != System.getProperty("ldapHost")
//                    || null != System.getProperty("ldapBaseDN")
//                    || null != System.getProperty("ldapHosts")
//                    || null != System.getProperty("ldapPort")
//                    || null != System.getProperty("ldapUser")
//                    || null != System.getProperty("ldapPass")
//            ) {
//                this.connectionDescriber.add(new SettingsRecord(
//                        System.getProperty("ldapHost"),
//                        System.getProperty("ldapBaseDN"),
//                        System.getProperty("ldapHosts").split(","),
//                        System.getProperty("ldapPort"),
//                        System.getProperty("ldapUser"),
//                        System.getProperty("ldapPass")
//                ));
//            } else {
//                this.setError(true);
//            }
//        }

        createAppDirs();
    }

    private void setError(boolean isError) {
        this.error = isError;
    }

    public boolean isError() {
        return this.error;
    }

    private void initDefautServers() {
        this.connectionDescriber.add(new IteConnectDescriber());
        this.connectionDescriber.add(new UrConnectDescriber());
        this.connectionDescriber.add(new KpszConnectDescriber());
    }

    private void createAppDirs() {
        new File(getPathToApp() + DIR_CAСHE + "/" + DIR_IMAGES).mkdirs();
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
        System.out.println("serverSocketPort - ServerSocket port int value. Default value - [" + this.getServerSocketPort() + "]");
//        System.out.println("ldap - create new Ldap depends on:");
//        System.out.println("	ldapHost - ldap host name.");
//        System.out.println("	ldapBaseDN - ldap base DN.");
//        System.out.println("	ldapHosts - ldap://server0/ ,ldap://server1/.");
//        System.out.println("	ldapPort - ldap port value.");
//        System.out.println("	ldapUser - ldap user name.");
//        System.out.println("	ldapPass - ldap user pass.");

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

    private void setDebug(boolean isDebug) {
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

    private String getSkinName() {
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

    private String getPathToApp() {
        return pathToApp;
    }

    public String getPathToAppCache() {
        return getPathToApp() + DIR_CAСHE + "/";
    }

    public String getPathToAppCacheImages() {
        return getPathToAppCache() + DIR_IMAGES + "/";
    }

    private void setPathToApp(String pathToApp) {
        if (null != pathToApp && Files.isDirectory(Paths.get(pathToApp))) {
            this.pathToApp = pathToApp + "/";
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

    public int getServerSocketPort() {
        return serverSocketPort;
    }

    private void setServerSocketPort(int serverSocketPort) {
        this.serverSocketPort = serverSocketPort;
    }

    @Override
    public boolean hasNext() {
        return point != connectionDescriber.size();
    }

    @Override
    public AbstractConnectDescriber getNext() {
        return connectionDescriber.get(point++);
    }

    @Override
    public void reset() {
        point = 0;
    }
}
