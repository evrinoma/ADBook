package libs;

import entity.SettingsRecord;

import java.io.IOException;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.SortControl;
import javax.naming.ldap.Control;

/**
 * @author nikolns
 */
public class Ldap {

    private static final String LDAP_VERSION = "3";
    private static final String LDAP_AUTH_METHOD = "simple";
    private static final String LDAP_TIMEOUT = "20000";

    private SettingsRecord settings = null;

    private boolean connect = false;
    private LdapContext ctx = null;
    private String sort = "cn";

    private String[] filialSelector = null;
    private String[] companySelector = null;

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    private Hashtable<String, String> getSettingsHashtable(String ldapHost) {
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put("com.sun.jndi.ldap.connect.timeout", LDAP_TIMEOUT);
        env.put(Context.PROVIDER_URL, ldapHost + ":" + this.settings.getPort());
        env.put(Context.SECURITY_AUTHENTICATION, LDAP_AUTH_METHOD);
        env.put(Context.SECURITY_PRINCIPAL, this.settings.getUser());
        env.put(Context.SECURITY_CREDENTIALS, this.settings.getPass());
        env.put("java.naming.ldap.version", LDAP_VERSION);

        return env;
    }

    private void connectToLdap(String ldapHost) {
        try {
            ctx = new InitialLdapContext(this.getSettingsHashtable(ldapHost), null);

            this.connect = true;
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    private void getConnect() {
        if (!isConnect()) {
            for (String ldapHost : this.settings.getHosts()) {
                this.connectToLdap(ldapHost);
                if (isConnect())
                    break;
            }
        }
    }

    public void closeConnect() {
        try {
            ctx.close();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        this.connect = false;
    }

    public boolean isConnect() {
        return this.connect;
    }

    /**
     * @param settings
     */
    public Ldap(SettingsRecord settings, String[] companySelector, String[] filialSelector) {
        this.settings = settings;
        this.companySelector = companySelector;
        this.filialSelector = filialSelector;
        getConnect();
    }


    private NamingEnumeration<?> getSearch(SearchControls ctls, String dn, String filter) {
        NamingEnumeration<?> answer = null;
        try {
            ctx.setRequestControls(new Control[]{new SortControl(sort, Control.CRITICAL)});
            answer = ctx.search(dn, filter, ctls);
        } catch (NamingException | IOException e) {
            e.printStackTrace();
        }

        return answer;
    }

    public String[] getDefaultSelectFields() {
        String[] selectFields = {"mobile", "homePhone", "userPassword", "postOfficeBox", "cn", "sn", "c", "l", "st",
                "title", "description", "postalcode", "physicaldeliveryofficename", "telephonenumber", "givenname",
                "distinguishedname", "displayname", "othertelephone", "co", "department", "company", "streetaddress",
                "wwwhomepage", "useraccountcontrol", "mail", "userprincipalname", "samaccountname", "manager",
                "directreports", "itnumber", "info", "jpegphoto", "fname",};

        return selectFields;
    }

    private NamingEnumeration<?> getLdapList(String dn, String filter, String[] selector) {
        NamingEnumeration<?> answer = null;
        if (null != selector) {
            SearchControls ctls = new SearchControls();
            ctls.setReturningAttributes(selector);
            ctls.setSearchScope(SearchControls.ONELEVEL_SCOPE);

            answer = getSearch(ctls, dn, filter);
        }

        return answer;
    }

    private NamingEnumeration<?> getLdapList(String filter, String[] selector) {
        return getLdapList(this.settings.getBaseDN(), filter, selector);
    }

    private NamingEnumeration<?> getLdapSearch(String dn, String filter, String[] selector) {
        NamingEnumeration<?> answer = null;
        if (null != selector) {
            SearchControls ctls = new SearchControls();
            ctls.setReturningAttributes(selector);
            ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);

            answer = getSearch(ctls, dn, filter);
        }

        return answer;
    }

    private NamingEnumeration<?> getLdapSearch(String filter, String[] selector) {
        return getLdapSearch(this.settings.getBaseDN(), filter, selector);
    }

    public NamingEnumeration<?> getLdapCompanys() {
        String companyFilter = "(&(objectclass=organizationalUnit))";

        return getLdapList(companyFilter, companySelector);
    }

    public NamingEnumeration<?> getLdapFilials(String dn) {
        String filialFilter = "(&(objectclass=organizationalUnit))";

        return getLdapList(dn, filialFilter, filialSelector);
    }

    public NamingEnumeration<?> getLdapUsers(String companyDn) {
        String userFilter = "(&(objectclass=organizationalperson)(!(!(mail=*)))(!(telephonenumber=null)))";

        return getLdapSearch(companyDn, userFilter, getDefaultSelectFields());
    }

}
