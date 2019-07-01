package threads;

import java.util.concurrent.ExecutionException;
import java.util.List;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;
import javax.swing.SwingWorker;

import entity.CompanyDto;
import entity.UserDto;
import libs.Companys;
import libs.Core;
import libs.Ldap;
import libs.AbstractConnectDescriber;

/**
 * @author nikolns
 */
public class LdapSearchThread extends SwingWorker<Object, String> {

    private static final String HINT_LDAP_OPEN = "Connecting to LDAP Server";
    private static final String HINT_LDAP_CLOSE = "Close connections";
    private static final String HINT_LDAP_USERS = "Try to get all Users from LDAP Server";
    private static final String HINT_LDAP_COMPANYS = "Try to get all Companys from LDAP Server";

    private Companys companys = null;

    private Ldap ldap = null;

    private Core core = null;

    private AbstractConnectDescriber connectDescriber = null;

    public LdapSearchThread(Core core, AbstractConnectDescriber connectDescriber) {
        this.core = core;
        this.companys = new Companys();
        this.connectDescriber = connectDescriber;
    }

    @Override
    protected Boolean doInBackground() throws Exception {

        try {
            publish(HINT_LDAP_OPEN);
            openLdapConnection();
        } catch (Exception e) {
            throw new RuntimeException();
        }

        publish(HINT_LDAP_COMPANYS);
        getLdapCompanys();
        publish(HINT_LDAP_USERS);
        getLdapUsers();
        publish(HINT_LDAP_CLOSE);
        closeLdapConnection();

        return true;
    }

    private void openLdapConnection() throws Exception {
        ldap = new Ldap(connectDescriber.getSettings(), connectDescriber.getSelectorCompanys(), connectDescriber.getSelectorFilials());
        if (!ldap.isConnect()) {
            throw new Exception();
        }
    }

    private void getLdapCompanys() {
        addCompany(ldap.getLdapCompanys());
    }

    private void getLdapUsers() {
        getLdapUsers("cn");
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
                    UserDto user = new UserDto(core.getSystemEnv().getPathToAppCacheImages());
                    user.deployEntry(attrs, ldap.getDefaultSelectFields(), company.getDn(), company.getOu());
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

    private void addCompany(NamingEnumeration<?> companys, boolean isFilial) {
        Attributes attrs;
        if (null != companys) {
            while (companys.hasMoreElements()) {
                SearchResult sr;
                try {
                    sr = (SearchResult) companys.next();
                    attrs = sr.getAttributes();
                    if (null != attrs) {
                        CompanyDto companyDto = connectDescriber.getCompany(sr, attrs);
                        if (null != companyDto) {
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
    @Override
    protected void done() {
        boolean status;
        try {
            // Retrieve the return value of doInBackground.
            status = (boolean) get();
            if (status) {
                companys.generateDate();
                String connectionName = connectDescriber.getConnectionName();
                if (connectDescriber.isRemouteFilials()) {
                    core.addLdapSearchSuccessful(companys, connectionName);
                } else {
                    core.addLdapSearchSuccessful(companys);
                }
                core.removeLdapSearchStack(connectionName).linkCompanys();
            }
            // System.out.println("Completed with status: " + status);
        } catch (InterruptedException e) {
            // This is thrown if the thread's interrupted.
        } catch (ExecutionException e) {
            // This is thrown if we throw an exception
            // from doInBackground.
          core.localCache(LoadThread.READ);
        }
        core.flushing(Core.TREAD_LDAP_SEARCH);
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
