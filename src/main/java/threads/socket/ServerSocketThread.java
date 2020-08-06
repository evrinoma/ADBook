package threads.socket;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import libs.Core;

public class ServerSocketThread extends AbstractSocketThread {

	private HashMap<SocketChannel, String> dataMapper = null;
	private Selector selector;
	private ArrayList<String> clients = null;

	public ServerSocketThread(Core core) {
		super(core);
		dataMapper = new HashMap<SocketChannel, String>();
		clients = new ArrayList();
	}

	@Override
	protected Boolean doInBackground() throws Exception {
		startServer();
		while (true) {
			if (core.isClose()) {
				return true;
			}
			this.selector.select();
			Iterator keys = this.selector.selectedKeys().iterator();
			while (keys.hasNext()) {
				SelectionKey key = (SelectionKey) keys.next();
				keys.remove();
				if (!key.isValid()) {
					continue;
				}
				if (key.isAcceptable()) {
					this.accept(key);
				} else if (key.isReadable()) {
					String reading = this.read(key);
					if (reading != null) {
						String action = this.action(reading);
						this.write(key, action);
					}
				}else if (key.isWritable()) {
					System.out.println("isWritable...");
				}
			}
		}
	}

	// Can safely update the GUI from this method.
	protected void done() {
		boolean status;
		try {
			// Retrieve the return value of doInBackground.
			status = (boolean) get();
			if (status) {

			}
		} catch (InterruptedException e) {
			// This is thrown if the thread's interrupted.
		} catch (ExecutionException e) {
			// This is thrown if we throw an exception
			// from doInBackground.
		}
	}

	@Override
	// Can safely update the GUI from this method.
	protected void process(List<String> chunks) {
		// Here we receive the values that we publish().
		// They may come grouped in chunks.
		for (final String massage : chunks) {
			core.setStatusString(massage);
		}
	}

	private void startServer() throws IOException {
		this.selector = Selector.open();
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		serverChannel.configureBlocking(false);
		serverChannel.socket().bind(this.listenAddress);
		serverChannel.register(this.selector, SelectionKey.OP_ACCEPT);
		System.out.println("Server started...");
		this.clients.add(System.getProperty("user.name"));
	}

	protected void accept(SelectionKey key) throws IOException {
		ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
		SocketChannel channel = serverChannel.accept();
		channel.configureBlocking(false);
		Socket socket = channel.socket();
		SocketAddress remoteAddr = socket.getRemoteSocketAddress();
		System.out.println("Connected to: " + remoteAddr);
		channel.register(this.selector, SelectionKey.OP_READ);
		this.write(channel,formatToMessage(TYPE_USER, TYPE_CMD));
		String response = this.action(this.read(channel));
		System.out.println(response);
		dataMapper.put(channel, response);
	}

	// read from the threads.socket channel
	protected String read(SelectionKey key) throws IOException {
		SocketChannel channel = (SocketChannel) key.channel();
		String query = this.read(channel);
		if (query == null) {
			key.cancel();
		}
		return query;
	}

	protected String read(SocketChannel channel) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
		int numRead = -1;
		numRead = channel.read(buffer);
		if (numRead == -1) {
			this.dataMapper.remove(channel);
			Socket socket = channel.socket();
			SocketAddress remoteAddr = socket.getRemoteSocketAddress();
			System.out.println("Connection closed by client: " + remoteAddr);
			channel.close();
			return null;
		}

		return this.byteBufferToString(buffer,numRead);
	}

	protected String action(String message) {
		String query = getTypeMessage(message);
		if (query.equals(TYPE_USER)) {
			query = getMessage(TYPE_USER,message);
		}

		return query;
	}

	// read from the threads.socket channel
	protected void write(SelectionKey key, String message) throws IOException {
		SocketChannel channel = (SocketChannel) key.channel();
		this.write(channel,message);
	}

	protected void write(SocketChannel channel, String message) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
		buffer.clear();
		buffer.put(message.getBytes());
		buffer.flip();
		channel.write(buffer);
	}

	protected String getTypeMessage(String receive)
	{
		if (receive.startsWith(TYPE_USER +"[") && receive.contains("]")) {
			String usr = receive.subSequence((TYPE_USER +"[").length(), receive.indexOf("]")).toString();
			if (!this.clients.contains(usr)){
				this.clients.add(usr.toString());
				return TYPE_USER;
			} else {
				return TYPE_EXIT;
			}
		}

		return super.getTypeMessage(receive);
	}

}
