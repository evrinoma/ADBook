package interfaces;

import entity.SettingsRecord;

public interface SettingsRecordIterator {
    boolean hasNext();

    SettingsRecord getNext();

    void reset();
}
