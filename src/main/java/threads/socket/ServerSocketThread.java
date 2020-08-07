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

	private HashMap<SocketChannel, HashMap> dataMapper = null;
	private Selector selector;
	private ArrayList clients = null;
	private String uid = null;

	public ServerSocketThread(Core core) {
		super(core);
		dataMapper = new HashMap<>();
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
			Iterator<SelectionKey> keys = this.selector.selectedKeys().iterator();
			while (keys.hasNext()) {
				SelectionKey key = (SelectionKey) keys.next();
				keys.remove();
				if (!key.isValid()) {
					continue;
				}
				if (key.isAcceptable()) {
					this.accept(key);
				} else if (key.isReadable()) {
					System.out.println("isReadable...");

					Package packet = this.read(key);
					if (!packet.isEmpty()) {
						packet = this.action(packet);
						if (packet.isFlush()) {
							key.cancel();
						}
						this.write(key, packet.getQuery());
					}
				}
				else if (key.isWritable()) {
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
		this.uid = System.getProperty("user.name");
	}

	protected void accept(SelectionKey key) throws IOException {
		ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
		SocketChannel channel = serverChannel.accept();
		channel.configureBlocking(false);
		Socket socket = channel.socket();
		SocketAddress remoteAddr = socket.getRemoteSocketAddress();
		System.out.println("Connected to: " + remoteAddr);
		channel.register(this.selector, SelectionKey.OP_READ);
		HashMap map = new HashMap<String,ArrayList>();
		map.put(socket.getRemoteSocketAddress().toString(),new ArrayList());
		dataMapper.put(channel, map);
	}

	// read from the threads.socket channel
	protected Package read(SelectionKey key) throws IOException {
		SocketChannel channel = (SocketChannel) key.channel();
		Package packet = new Package(this.pureRead(channel));
		if (packet.isEmpty()) {
			key.cancel();
		}

		return packet;
	}

	protected String pureRead(SocketChannel channel) throws IOException {
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

	protected Package action(Package packet)
	{
		switch (packet.getType()) {
			case Package.TYPE_USER :
				if (!this.uid.equals(packet.getMessage()) && !this.clients.contains(packet.getMessage()))
				{
					return new Package(Package.TYPE_CONNECT, Package.TYPE_CMD);
				}
				return new Package(Package.TYPE_EXPAND, Package.TYPE_CMD);
//			case Package.TYPE_CONNECT :
//				return new Package(Package.TYPE_FLUSH, Package.TYPE_FLUSH);
			case Package.TYPE_EXPAND :
				core.expandWindow();
				return new Package(Package.TYPE_FLUSH, Package.TYPE_FLUSH);
			default:
				return new Package(Package.TYPE_FLUSH, Package.TYPE_FLUSH);
		}
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
}
