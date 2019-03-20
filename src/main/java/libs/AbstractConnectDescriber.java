package libs;

import entity.SettingsRecord;
import interfaces.DataAnalise;

public abstract class AbstractConnectDescriber implements DataAnalise {
    protected SettingsRecord settingsRecord = null;

    public SettingsRecord getSettings()
    {
        return this.settingsRecord;
    }
}
