package libs;

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
	private String ldapHost = null;
	private String ldapBaseDN = null;
	private String[] ldapHosts = null;
	private String ldapPort = null;
	private String ldapUser = null;
	private String ldapPass = null;
	private static final String LDAP_VERSION = "3";
	private static final String LDAP_AUTH_METHOD = "simple";
	private static final String LDAP_TIMEOUT = "20000";

	private boolean connect = false;
	private LdapContext ctx = null;
	private String sort = "cn";

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
		env.put(Context.PROVIDER_URL, ldapHost + ":" + ldapPort);
		env.put(Context.SECURITY_AUTHENTICATION, LDAP_AUTH_METHOD);
		env.put(Context.SECURITY_PRINCIPAL, ldapUser);
		env.put(Context.SECURITY_CREDENTIALS, ldapPass);
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
			for (String ldapHost : ldapHosts) {
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
     * Please refer to the method for more details.
     *
	 * @param ldapHost
	 * @param ldapBaseDN
	 * @param ldapHosts
	 * @param ldapPort
	 * @param ldapUser
	 * @param ldapPass
	 */
	public Ldap(String ldapHost, String ldapBaseDN, String[] ldapHosts, String ldapPort, String ldapUser,
			String ldapPass) {
		this.ldapHost = ldapHost;
		this.ldapBaseDN = ldapBaseDN;
		this.ldapHosts = ldapHosts;
		this.ldapPort = ldapPort;
		this.ldapUser = ldapUser;
		this.ldapPass = ldapPass;
		getConnect();
	}

	private NamingEnumeration<?> getSearch(SearchControls ctls, String dn, String filter, String[] selector) {
		NamingEnumeration<?> answer = null;
		try {
			ctx.setRequestControls(new Control[] { new SortControl(sort, Control.CRITICAL) });
			answer = ctx.search(dn, filter, ctls);
		} catch (NamingException | IOException e) {
			e.printStackTrace();
		}

		return answer;
	}

	public String[] getDefaultSelectFields() {
		String[] selectFields = { "mobile", "homePhone", "userPassword", "postOfficeBox", "cn", "sn", "c", "l", "st",
				"title", "description", "postalcode", "physicaldeliveryofficename", "telephonenumber", "givenname",
				"distinguishedname", "displayname", "othertelephone", "co", "department", "company", "streetaddress",
				"wwwhomepage", "useraccountcontrol", "mail", "userprincipalname", "samaccountname", "manager",
				"directreports", "itnumber", "info", "jpegphoto", "fname", };

		return selectFields;
	}

	private NamingEnumeration<?> getLdapList(String dn, String filter, String[] selector) {
		SearchControls ctls = new SearchControls();
		ctls.setReturningAttributes(selector);
		ctls.setSearchScope(SearchControls.ONELEVEL_SCOPE);

		return getSearch(ctls, dn, filter, selector);
	}

	private NamingEnumeration<?> getLdapList(String filter, String[] selector) {
		return getLdapList(ldapBaseDN, filter, selector);
	}

	private NamingEnumeration<?> getLdapSearch(String dn, String filter, String[] selector) {

		SearchControls ctls = new SearchControls();
		ctls.setReturningAttributes(selector);
		ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);

		return getSearch(ctls, dn, filter, selector);
	}

	private NamingEnumeration<?> getLdapSearch(String filter, String[] selector) {
		return getLdapSearch(ldapBaseDN, filter, selector);
	}

	public NamingEnumeration<?> getLdapCompanys() {
		String companyFilter = "(&(objectclass=organizationalUnit))";
		String[] companySelector = { "ou", "description", "dn" };

		return getLdapList(companyFilter, companySelector);
	}

	public NamingEnumeration<?> getLdapFilials(String dn) {
		String filialFilter = "(&(objectclass=organizationalUnit))";
		String[] filialSelector = { "ou", "description", "dn" };

		return getLdapList(dn, filialFilter, filialSelector);
	}

	public NamingEnumeration<?> getLdapUsers(String companyDn) {
		String userFilter = "(&(objectclass=organizationalperson)(!(!(mail=*)))(!(telephonenumber=null)))";

		return getLdapSearch(companyDn, userFilter, getDefaultSelectFields());
	}

}
