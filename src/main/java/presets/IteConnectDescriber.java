package presets;

import entity.CompanyDto;
import entity.SettingsRecord;
import libs.AbstractConnectDescriber;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;

public class IteConnectDescriber extends AbstractConnectDescriber {

    public IteConnectDescriber() {
        settingsRecord = new SettingsRecord(
                "ite-ng.ru",
                "OU=MSK,DC=ite-ng,DC=ru",
                new String[]{"ldap://iteng06.ite-ng.ru", "ldap://iteng20.ite-ng.ru"},
                "389",
                "ldap@ite-ng.ru",
                "ldap");
    }

    @Override
    public CompanyDto getCompanys(SearchResult companys, Attributes attrs) throws NamingException {
        CompanyDto companyDto = null;
        Attribute description = attrs.get("description");
        if (null != description) {
            Attribute ou = attrs.get("ou");
            companyDto = new CompanyDto((String) description.get(), (String) ou.get(),
                    (String) companys.getName() + "," + settingsRecord.getBaseDN());
        }
        return companyDto;
    }
}
