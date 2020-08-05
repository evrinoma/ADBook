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

    public boolean isRemoteBranch() {
        return this.isRemote() && this.isBranch();
    }

    public boolean isRemote() {
        return false;
    }

    public boolean isBranch() {
        return false;
    }

    protected String getCompanyDN() {
        return null;
    }

    protected String getBranchDN() {
        return null;
    }

    public String getConnectionName() {
        return this.getClass().getName();
    }

    protected String getRemoteBranchDN() {
        return null;
    }
}
