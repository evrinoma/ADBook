package threads.socket;

import libs.Core;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ClientSocketThread extends AbstractSocketThread {

	public ClientSocketThread(Core core) {
		super(core);
	}

	@Override
	protected Boolean doInBackground() throws Exception {
		while (true) {
			if (core.isClose()) {
				return true;
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

	public boolean startClient() {
		boolean status = false;
		try {
			InetSocketAddress hostAddress = new InetSocketAddress(SERVER, core.getSystemEnv().getServerSocketPort());
			SocketChannel channel = SocketChannel.open(hostAddress);

			System.out.println("Client... started");
			String response = getTypeMessage(read(channel));
			System.out.println(response);
			if (response.contains(TYPE_USER)) {
				write(channel, formatToMessage(System.getProperty("user.name"), TYPE_USER));
				String query  = read(channel);
				response = getTypeMessage(query);
				if (query.contains(TYPE_EXIT))
				{
					write(channel, formatToMessage(TYPE_EXPAND, TYPE_CMD));
					query  = read(channel);
					response = getTypeMessage(query);
				}
			}

			channel.close();
			status = true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return status;
	}

	protected void write(SocketChannel channel, String message) throws IOException  {
		byte[] query = message.getBytes();
		ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
		System.arraycopy(query, 0, buffer.array(), 0, query.length);
		channel.write(buffer);
		buffer.clear();
	}

	protected String read(SocketChannel channel) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
		int k = channel.read(buffer);
		return this.byteBufferToString(buffer,k);
	}
}
