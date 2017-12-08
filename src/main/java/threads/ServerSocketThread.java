package threads;

import java.io.IOException;
import java.net.InetSocketAddress;
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

import javax.swing.SwingWorker;

import libs.Core;

public class ServerSocketThread extends SwingWorker<Object, String> {

	public static final int PORT = 8749;
	public static final String SERVER = "localhost";

	private Core core;
	private InetSocketAddress listenAddress;
	private HashMap<SocketChannel, List> dataMapper;
	private Selector selector;

	public ServerSocketThread(Core core) {
		this.core = core;
		listenAddress = new InetSocketAddress(SERVER, PORT);
		dataMapper = new HashMap<SocketChannel, List>();
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
					this.read(key);
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
		serverChannel.socket().bind(listenAddress);
		serverChannel.register(this.selector, SelectionKey.OP_ACCEPT);
		System.out.println("Server started...");
	}

	private void accept(SelectionKey key) throws IOException {
		ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
		SocketChannel channel = serverChannel.accept();
		channel.configureBlocking(false);
		Socket socket = channel.socket();
		SocketAddress remoteAddr = socket.getRemoteSocketAddress();
		System.out.println("Connected to: " + remoteAddr);
		dataMapper.put(channel, new ArrayList());
		channel.register(this.selector, SelectionKey.OP_READ);
	}

	// read from the socket channel
	private void read(SelectionKey key) throws IOException {
		SocketChannel channel = (SocketChannel) key.channel();
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		int numRead = -1;
		numRead = channel.read(buffer);
		if (numRead == -1) {
			this.dataMapper.remove(channel);
			Socket socket = channel.socket();
			SocketAddress remoteAddr = socket.getRemoteSocketAddress();
			System.out.println("Connection closed by client: " + remoteAddr);
			channel.close();
			key.cancel();
			return;
		}

		byte[] data = new byte[numRead];
		System.arraycopy(buffer.array(), 0, data, 0, numRead);
		messageParser(new String(data));		
	}
	
	private void messageParser(String receive)
	{
		if (receive.equals("expandWindow")) {
			core.expandWindow();
		}
	}

	public boolean startClient() {
		boolean status = false;
		try {
			InetSocketAddress hostAddress = new InetSocketAddress(SERVER, PORT);
			SocketChannel client = SocketChannel.open(hostAddress);

			System.out.println("Client... started");
			// Send messages to server
			String[] messages = new String[] { "expandWindow" };

			for (int i = 0; i < messages.length; i++) {
				byte[] message = new String(messages[i]).getBytes();
				ByteBuffer buffer = ByteBuffer.wrap(message);
				client.write(buffer);				
				buffer.clear();
			}
			client.close();
			status = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return status;
	}

}
