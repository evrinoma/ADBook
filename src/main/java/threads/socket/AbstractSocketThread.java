package threads.socket;

import libs.Core;

import javax.swing.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public abstract class AbstractSocketThread extends SwingWorker<Object, String> {

    protected Core core;
    protected static final String TYPE_USER = "user";
    protected static final String TYPE_CMD = "cmd";
    protected static final String TYPE_RESPONSE = "response";
    protected static final String TYPE_EMPTY = "empty";
    protected static final String TYPE_EXIT = "exit";
    protected static final String TYPE_EXPAND = "expand";

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

    protected String getTypeMessage(String receive)
    {
        System.out.println("messageParser["+receive+"]");

        if (receive.startsWith(TYPE_RESPONSE +"[")  && receive.contains("]")) {
            String cmd = receive.subSequence((TYPE_RESPONSE +"[").length(), receive.indexOf("]")).toString();
            if (cmd.equals("success")) {
                return TYPE_RESPONSE;
            }
        }

        if (receive.startsWith(TYPE_CMD+"[")  && receive.contains("]")) {
            String cmd = receive.subSequence((TYPE_CMD+"[").length(), receive.indexOf("]")).toString();
            if (cmd.equals(TYPE_EXPAND)) {
                core.expandWindow();
                return TYPE_CMD;
            }
            if (cmd.equals(TYPE_USER)) {
                return TYPE_USER;
            }
        }

        return TYPE_EMPTY;
    }

    protected String formatToMessage(String message, String type)
    {
        switch (type) {
            case TYPE_USER:
                message =  TYPE_USER +"["+ message +"]";
                break;
            case TYPE_CMD :
                message =  TYPE_CMD+"["+ message +"]";
                break;
            case TYPE_RESPONSE:
                message =  TYPE_RESPONSE +"["+ message +"]";
                break;
            default:
                message = "";
        }

        return message;
    }
}
