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
			status = true;
			////отравляем сообщение с имя пользователя и адресом клиента, для сохрания
			////write(channel, formatToMessage("["+System.getProperty("user.name")+"]["+channel.getLocalAddress().toString()+"]", TYPE_USER));
			Package packet = new Package(System.getProperty("user.name"), Package.TYPE_USER);
			//отравляем сообщение с имя пользователя, для сохрания
			write(channel, packet.getQuery());
			//ожидаем ответ о возможности подлючения пользователя
			System.out.println("Read...["+ packet.getQuery()+"]");
			packet = new Package(read(channel));
			switch (packet.getMessage()) {
				//если придет TYPE_CONNECT, то запускаем приложение и сохраняем соединение
				case Package.TYPE_CONNECT :
					status = false;
					System.out.println("Client save connection");
					break;
				//если придет TYPE_EXIT, то отправляем уведомление о всплытии окна
				case Package.TYPE_EXPAND :
					packet = new Package(Package.TYPE_EXPAND, Package.TYPE_EXPAND);
					write(channel, packet.getQuery());
					System.out.println("Read...["+ packet.getQuery()+"]");
					packet = new Package(read(channel));
					System.out.println("Client already connected");
					break;
			}
			channel.close();

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
		int numRead = channel.read(buffer);
		return this.byteBufferToString(buffer,numRead);
	}
}
