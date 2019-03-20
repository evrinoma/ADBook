package interfaces;

import libs.AbstractConnectDescriber;

public interface SettingsRecordIterator {
    boolean hasNext();

    AbstractConnectDescriber getNext();

    void reset();
}
