package threads.socket;

import libs.Core;

import javax.swing.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public abstract class AbstractSocketThread extends SwingWorker<Object, String> {

    protected Core core;
    protected static final String SERVER = "localhost";
    protected static final int BUFFER_SIZE = 1023;
    protected InetSocketAddress listenAddress;

    public AbstractSocketThread(Core core) {
        this.core = core;
        this.listenAddress = new InetSocketAddress(SERVER, core.getSystemEnv().getServerSocketPort());
    }

    protected String byteBufferToString(ByteBuffer buffer, int numRead)
    {
        if (numRead > 0) {
            byte[] data = new byte[numRead];
            System.arraycopy(buffer.array(), 0, data, 0, numRead);

            String stream = new String(data);
            System.out.println("Data received:[" + stream + "]");

            return stream;
        }

        return "";
    }

    protected String getMessage(String type, String receive)
    {
        return receive.subSequence((type +"[").length(), receive.indexOf("]")).toString();
    }
}
