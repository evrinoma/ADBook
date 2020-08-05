package presets;

import entity.CompanyDto;
import entity.SettingsRecord;
import libs.AbstractConnectDescriber;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;
import java.util.Objects;

public class KpszConnectDescriber extends AbstractConnectDescriber {

    public static final String FILIAL_REMOTE_OU = "_KurganUsers";

    public KpszConnectDescriber() {
        settingsRecord = new SettingsRecord(
                "srv3.corp.kpsz.ru",
                "DC=corp,DC=kpsz,DC=ru",
                new String[]{"ldap://srv3.corp.kpsz.ru"},
                "389",
                "ldap@corp.kpsz.ru",
                "ldapldap");

        selectorCompanys = new String[]{"ou", "description", "dn"};
        selectorFilials  = selectorCompanys;
    }

    @Override
    public CompanyDto getCompany(SearchResult searchResult, Attributes attrs) throws NamingException {
        Attribute ou = attrs.get("ou");
        companyDto = null;
        if (null != ou) {
            if (Objects.equals(FILIAL_REMOTE_OU, (String) ou.get())) {
                Attribute description = attrs.get("description");
                companyDto = new CompanyDto((String) description.get(), (String) ou.get(),
                        (String) searchResult.getName() + "," + settingsRecord.getBaseDN());
            }
        }
        return companyDto;
    }

    @Override
    public boolean isRemote() { return true; }

    @Override
    public boolean isBranch() { return false; }
}

