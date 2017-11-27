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
 *
 */
public class Ldap {
	public static final String LDAP_HOST = "ite-ng.ru";
	public static final String LDAP_BASE_DN = "OU=MSK,DC=ite-ng,DC=ru";
	public static final String[] LDAP_HOSTS = { "ldap://iteng13.ite-ng.ru", "ldap://iteng20.ite-ng.ru" };
	public static final String LDAP_PORT = "389";
	public static final String LDAP_USER = "ldap@ite-ng.ru";
	public static final String LDAP_PASS = "ldap";
	public static final String LDAP_VERSION = "3";
	public static final String LDAP_AUTH_METHOD = "simple";

	private boolean connect = false;
	private Hashtable<String, String> env = null;
	public LdapContext ctx = null;

	private String sort = "cn";

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	private Hashtable<String, String> getSettingsHashtable(String ldapHost) {
		Hashtable<String, String> env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, ldapHost + ":" + LDAP_PORT);
		env.put(Context.SECURITY_AUTHENTICATION, LDAP_AUTH_METHOD);
		env.put(Context.SECURITY_PRINCIPAL, LDAP_USER);
		env.put(Context.SECURITY_CREDENTIALS, LDAP_PASS);
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
			for (String ldapHost : LDAP_HOSTS) {
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
	}

	public boolean isConnect() {
		return this.connect;
	}

	public Ldap() {
		getConnect();
	}

	private NamingEnumeration getSearch(SearchControls ctls, String dn, String filter, String[] selector) {
		NamingEnumeration answer = null;
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
		return getLdapList(LDAP_BASE_DN, filter, selector);
	}

	private NamingEnumeration<?> getLdapSearch(String dn, String filter, String[] selector) {

		SearchControls ctls = new SearchControls();
		ctls.setReturningAttributes(selector);
		ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);

		return getSearch(ctls, dn, filter, selector);
	}

	private NamingEnumeration<?> getLdapSearch(String filter, String[] selector) {
		return getLdapSearch(LDAP_BASE_DN, filter, selector);
	}

	public NamingEnumeration<?> getLdapCompanys() {
		String companyFilter = "(&(objectclass=organizationalUnit))";
		String[] companySelector = { "ou", "description", "dn" };

		return getLdapList(companyFilter, companySelector);
	}
	
	public NamingEnumeration<?> getLdapUsers(String companyDn) {
		String userFilter = "(&(objectclass=organizationalperson)(!(!(mail=*)))(!(telephonenumber=null)))";		

		return getLdapSearch(companyDn,userFilter, getDefaultSelectFields());
	}
	
}
