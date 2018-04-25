package threads;

import java.util.concurrent.ExecutionException;
import java.util.List;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;
import javax.swing.SwingWorker;

import entity.CompanyDto;
import entity.UserDto;
import libs.Companys;
import libs.Core;
import libs.Ldap;

public class LdapSearchThread extends SwingWorker<Object, String> {

	private Companys companys = null;

	private Ldap ldap = null;

	private static final String HINT_LDAP_OPEN = "Connecting to LDAP Server";
	private static final String HINT_LDAP_CLOSE = "Close connections";
	private static final String HINT_LDAP_USERS = "Try to get all Users from LDAP Server";
	private static final String HINT_LDAP_COMPANYS = "Try to get all Companys from LDAP Server";

	private Core core;

	public LdapSearchThread(Core core) {
		this.core = core;
		companys = new Companys();
	}

	private void flush() {
		core = null;
		companys = null;
		ldap = null;
	}

	@Override
	protected Boolean doInBackground() throws Exception {
		publish(HINT_LDAP_OPEN);
		openLdapConnection();
		publish(HINT_LDAP_COMPANYS);
		getLdapCompanys();
		publish(HINT_LDAP_USERS);
		getLdapUsers();
		publish(HINT_LDAP_CLOSE);
		closeLdapConnection();

		return true;
	}

	private void openLdapConnection() {
		ldap = new Ldap(core.getSystemEnv().getLdapHost(), core.getSystemEnv().getLdapBaseDN(),
				core.getSystemEnv().getLdapHosts(), core.getSystemEnv().getLdapPort(),
				core.getSystemEnv().getLdapUser(), core.getSystemEnv().getLdapPass());
	}

	private void getLdapCompanys() {
		if (ldap.isConnect()) {
			addCompany(ldap.getLdapCompanys());
		}
	}

	private void getLdapUsers() {
		getLdapUsers(new String("cn"));
	}

	private void closeLdapConnection() {
		if (null != ldap) {
			ldap.closeConnect();
		}
	}

	private void addUser(CompanyDto company, NamingEnumeration<?> users) {
		Attributes attrs;
		if (null != users) {
			while (users.hasMoreElements()) {
				SearchResult sr;
				try {
					sr = (SearchResult) users.next();
					attrs = sr.getAttributes();
					UserDto user = new UserDto();
					user.deployEntry(attrs, ldap.getDefaultSelectFields());
					company.addNewUser(user);
					/*
					 * for (String manager : user.getManager()) {
					 * addUserHead(user, ldap.getLdapUsers(manager)); } for
					 * (String dependent : user.getDirectReports()) {
					 * addUserDependent(user, ldap.getLdapUsers(dependent)); }
					 */
				} catch (NamingException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void getLdapUsers(String $sort) {
		if (ldap.isConnect()) {
			for (CompanyDto company : companys.all()) {
				if (company.getFilials().size() > 0) {
					for (CompanyDto filial : company.getFilials()) {
						addUser(filial, ldap.getLdapUsers("ou=" + filial.getOu() + "," + company.getDn()));
					}
				} else {
					addUser(company, ldap.getLdapUsers(company.getDn()));
				}
			}
		}
	}

	private void addCompany(NamingEnumeration<?> companys, boolean isFilial) {
		Attributes attrs;
		if (null != companys) {
			while (companys.hasMoreElements()) {
				SearchResult sr;
				try {
					sr = (SearchResult) companys.next();
					attrs = sr.getAttributes();
					if (null != attrs) {
						Attribute description = attrs.get("description");
						if (null != description) {
							Attribute ou = attrs.get("ou");
							CompanyDto companyDto = new CompanyDto((String) description.get(), (String) ou.get(),
									(String) sr.getName() + "," + core.getSystemEnv().getLdapBaseDN());
							if (!isFilial) {
								this.companys.addNewCompany(companyDto);
								addCompany(ldap.getLdapFilials(companyDto.getDn()), true);
							} else {
								CompanyDto lastCompanyDto = this.companys.getLastInsertCompany();
								lastCompanyDto.addNewFilial(companyDto);
							}
						}
					}
				} catch (NamingException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void addCompany(NamingEnumeration<?> companys) {
		addCompany(companys, false);
	}

	// Can safely update the GUI from this method.
	protected void done() {
		boolean status;
		try {
			// Retrieve the return value of doInBackground.
			status = (boolean) get();
			if (status) {
				core.isLdapSearchSuccessful(companys);
			}
			// System.out.println("Completed with status: " + status);
		} catch (InterruptedException e) {
			// This is thrown if the thread's interrupted.
		} catch (ExecutionException e) {
			// This is thrown if we throw an exception
			// from doInBackground.
		}
		flush();
	}

	@Override
	// Can safely update the GUI from this method.
	protected void process(List<String> chunks) {
		// Here we receive the values that we publish().
		// They may come grouped in chunks.
		for (final String massage : chunks) {
			core.setStatusString(massage);
		}
	}

}
