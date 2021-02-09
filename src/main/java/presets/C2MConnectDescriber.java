package presets;

import entity.CompanyDto;
import entity.SettingsRecord;
import libs.AbstractConnectDescriber;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;
import java.util.Objects;

public class C2MConnectDescriber extends AbstractConnectDescriber {

    public static final String FILIAL_REMOTE_OU = "C2M_staff";

    public C2MConnectDescriber() {
        settingsRecord = new SettingsRecord(
                "c2m.local",
                "DC=c2m,DC=local",
                new String[]{"ldap://172.16.19.10"},
                "389",
                "ldap@c2m.local",
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

