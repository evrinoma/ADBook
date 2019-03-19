package entity;

public class SettingsRecord {

    private String ldapHost = null;
    private String ldapBaseDN = null;
    private String[] ldapHosts = null;
    private String ldapPort = null;
    private String ldapUser = null;
    private String ldapPass = null;


    public SettingsRecord(String ldapHost, String ldapBaseDN, String[] ldapHosts, String ldapPort, String ldapUser, String ldapPass) {
        this
            .setBaseDN(ldapBaseDN)
            .setHosts(ldapHosts)
            .setPort(ldapPort)
            .setUser(ldapUser)
            .setPass(ldapPass)
            .setHost(ldapHost);
    }

    public String getHost() {
        return ldapHost;
    }

    public SettingsRecord setHost(String ldapHost) {
        this.ldapHost = ldapHost;

        return this;
    }

    public String getBaseDN() {
        return ldapBaseDN;
    }

    public SettingsRecord setBaseDN(String ldapBaseDN) {
        this.ldapBaseDN = ldapBaseDN;

        return this;
    }

    public String[] getHosts() {
        return ldapHosts;
    }

    public SettingsRecord setHosts(String[] ldapHosts) {
        this.ldapHosts = ldapHosts;

        return this;
    }

    public String getPort() {
        return ldapPort;
    }

    public SettingsRecord setPort(String ldapPort) {
        this.ldapPort = ldapPort;

        return this;
    }

    public String getUser() {
        return ldapUser;
    }

    public SettingsRecord setUser(String ldapUser) {
        this.ldapUser = ldapUser;

        return this;
    }

    public String getPass() {
        return ldapPass;
    }

    public SettingsRecord setPass(String ldapPass) {
        this.ldapPass = ldapPass;

        return this;
    }
}
