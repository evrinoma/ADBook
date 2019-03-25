package libs;

import entity.CompanyDto;
import entity.SettingsRecord;
import interfaces.DataAnalise;

public abstract class AbstractConnectDescriber implements DataAnalise {
    protected SettingsRecord settingsRecord = null;
    protected CompanyDto companyDto = null;
    protected String[] selectorCompanys = null;
    protected String[] selectorFilials = null;


    public SettingsRecord getSettings()
    {
        return settingsRecord;
    }

    @Override
    public String[] getSelectorCompanys() {
        return selectorCompanys;
    }

    @Override
    public String[] getSelectorFilials() {
        return selectorFilials;
    }

    public boolean isRemouteFilials() {
        return false;
    }

    protected String getCompanyDN() {
        return null;
    }

    protected String getFilialDN() {
        return null;
    }

    public String getConnectionName() {
        return this.getClass().getName();
    }

    protected String getRemoteFilialDN() {
        return null;
    }
}
