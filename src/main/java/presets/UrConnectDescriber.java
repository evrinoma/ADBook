package presets;

import entity.CompanyDto;
import entity.SettingsRecord;
import libs.AbstractConnectDescriber;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;

public class UrConnectDescriber extends AbstractConnectDescriber {

    public UrConnectDescriber() {
        settingsRecord =  new SettingsRecord(
                "ur.ite-ng.ru",
                "OU=Ekaterinburg,DC=ur,DC=ite-ng,DC=ru",
                new String[]{"ldap://ur04.ur.ite-ng.ru", "ldap://ur05.ur.ite-ng.ru"},
                "389",
                "ldap@ur.ite-ng.ru",
                "ldap");

        selectorCompanys = new String[]{"ou", "dn"};
    }

    @Override
    public CompanyDto getCompany(SearchResult searchResult, Attributes attrs) throws NamingException {
        Attribute ou = attrs.get("ou");
        companyDto = new CompanyDto("ur", (String) ou.get(),
                    (String) searchResult.getName() + "," + settingsRecord.getBaseDN());

        return companyDto;
    }

    @Override
    public String[] getSelectorFilials() {
        return null;
    }

}
