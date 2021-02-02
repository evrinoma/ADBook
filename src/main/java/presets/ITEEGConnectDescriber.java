package presets;

import entity.CompanyDto;
import entity.SettingsRecord;
import libs.AbstractConnectDescriber;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;
import java.util.Objects;

public class ITEEGConnectDescriber extends AbstractConnectDescriber {

    public ITEEGConnectDescriber() {
        settingsRecord = new SettingsRecord(
                "pdc.corp.ite-eg.ru",
                "DC=corp,DC=ite-eg,DC=ru",
                new String[]{"ldap://pdc.corp.ite-eg.ru"},
                "389",
                "ldap@corp.ite-eg.ru",
                "ldapldap");

        selectorCompanys = new String[]{"ou", "description", "dn"};
        selectorFilials  = selectorCompanys;
    }

    @Override
    public CompanyDto getCompany(SearchResult searchResult, Attributes attrs) throws NamingException {
        Attribute ou = attrs.get("ou");
        companyDto = null;
        if (null != ou) {
            Attribute description = attrs.get("description");
            if (description!=null) {
                companyDto = new CompanyDto((String) description.get(), (String) ou.get(),
                        (String) searchResult.getName() + "," + settingsRecord.getBaseDN());
            } else {
                throw new NamingException("Connection Describer Attribute not set");
            }
        }
        return companyDto;
    }

    @Override
    public boolean isRemote() { return true; }

    @Override
    public boolean isBranch() { return false; }
}

