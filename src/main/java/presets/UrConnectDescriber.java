package presets;

import entity.CompanyDto;
import entity.SettingsRecord;
import libs.AbstractConnectDescriber;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;
import java.util.Objects;

public class UrConnectDescriber extends AbstractConnectDescriber {

    private static final String COMPANY_DN = "OU=IPCNG,OU=MSK,DC=ite-ng,DC=ru";
    private static final String BRANCH_DN = "OU=Ekaterinburg,OU=MSK,DC=ite-ng,DC=ru";
    private static final String REMOTE_BRANCH_DN = "OU=Ekaterinburg,DC=ur,DC=ite-ng,DC=ru";
    public static final String BRANCH_REMOTE_OU = "Ekaterinburg";

    public UrConnectDescriber() {
        settingsRecord =  new SettingsRecord(
                "ur.ite-ng.ru",
                "DC=ur,DC=ite-ng,DC=ru",
                new String[]{"ldap://ur04.ur.ite-ng.ru", "ldap://ur05.ur.ite-ng.ru"},
                "389",
                "ldap@ur.ite-ng.ru",
                "ldap");

        selectorCompanys = new String[]{"ou", "dn"};
    }

    @Override
    public CompanyDto getCompany(SearchResult searchResult, Attributes attrs) throws NamingException {
        Attribute ou = attrs.get("ou");
        companyDto = null;
        if (null != ou) {
            if (Objects.equals(BRANCH_REMOTE_OU, (String) ou.get())) {
                companyDto = new CompanyDto(REMOTE_BRANCH_DN, BRANCH_REMOTE_OU, //(String) ou.get(),
                        (String) searchResult.getName() + "," + settingsRecord.getBaseDN());
            }
        }
        return companyDto;
    }

    @Override
    public String[] getSelectorFilials() {
        return null;
    }

    @Override
    public boolean isRemote() { return true; }

    @Override
    public boolean isBranch() { return true; }

    @Override
    protected String getCompanyDN() {
        return COMPANY_DN;
    }

    @Override
    protected String getBranchDN() {
        return BRANCH_DN;
    }

    @Override
    protected String getRemoteBranchDN() {
        return REMOTE_BRANCH_DN;
    }
}
