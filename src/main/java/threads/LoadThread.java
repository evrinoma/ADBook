package threads;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import libs.Companys;
import libs.Core;

public class LoadThread extends SwingWorker<Object, String> {

	public static final boolean READ = true;
	public static final boolean WRITE = !READ;
	private static final String FILE_CAСHE = "cache";

	private Core core = null;
	private Companys writeStream = null;
	private Companys readStream = null;
	private boolean direction = READ;
	private String currentPath = "/"+FILE_CAСHE;
	
	private FileLock fileLock;

	public LoadThread(Core core) {	
		try {
			currentPath = new java.io.File( "." ).getCanonicalPath()+currentPath;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.core = core;
	}
	
	private void flush()
	{
		core = null;
		currentPath = null;
		writeStream = null;
		readStream = null;
	}
	
	public LoadThread setDirection(boolean direction) {
		this.direction = direction;
		return this;
	}

	public LoadThread setWriteStream(Companys writeStream) {
		this.writeStream = writeStream;
		return this;
	}

	@Override
	protected Object doInBackground() throws Exception {

		return direction ? loadCache() : saveCache();
	}

	private boolean isEmptyRead() {
		return readStream.getCompanys().isEmpty(); 
	}
	
	// Can safely update the GUI from this method.
	protected void done() {
		try {
			// Retrieve the return value of doInBackground.
			if ((boolean) get()) {
				if (direction) {
					if (!isEmptyRead()) {
					core.isLocalCacheSuccessful(readStream);
					core.setStatusString("successful read");
					} else {
						core.isLocalCacheFail();
						core.setStatusString("read is empty");
					}
				} else {
					core.setStatusString("successful save");
				}
			} else {
				core.isLocalCacheFail();
			}
			// System.out.println("Completed with status: " + status);
		} catch (InterruptedException e) {
			// This is thrown if the thread's interrupted.
		} catch (ExecutionException e) {
			// This is thrown if we throw an exception
			// from doInBackground.
		}
		flush();
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

	/**
	 * сохранение в кеш
	 */
	private boolean saveCache() throws IOException {
		boolean status = false;
		RandomAccessFile fileOut = null;
		FileChannel channel = null;
		FileLock lock = null;
		readStream = new Companys();
		
		try {
			fileOut = new RandomAccessFile(currentPath, "rw");
			channel = fileOut.getChannel();
			lock = channel.lock();

			if (lock != null) {
				FileDescriptor descriptorOut = fileOut.getFD();
				write(descriptorOut);
				status = true;
			} else {
				System.out.println("Another instance is already running saveCache");
			}
		} finally {
			if (lock != null && lock.isValid())
				lock.release();
			if (fileOut != null)
				fileOut.close();
			
			return status;	
		}	
	}

	/**
	 * загрузка из кеша
	 * 
	 * @return
	 * @throws IOException
	 */
	private boolean loadCache() throws IOException {
		boolean status = false;
		RandomAccessFile fileIn = null;
		FileChannel channel = null;
		FileLock lock = null;
		readStream = new Companys();
		
		try {
			fileIn = new RandomAccessFile(currentPath, "rw");
			channel = fileIn.getChannel();
			lock = channel.lock();

			if (lock != null) {
				FileDescriptor descriptorIn = fileIn.getFD();
				read(descriptorIn);
				status = true;
			} else {
				System.out.println("Another instance is already running loadCache");
			}
		} finally {
			if (lock != null && lock.isValid())
				lock.release();
			if (fileIn != null)
				fileIn.close();
			
			return status;
		}
	}

	private void write(FileDescriptor fileDescriptor) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(fileDescriptor);

		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		objectOutputStream.writeObject(writeStream);
		objectOutputStream.close();
	}

	private void read(FileDescriptor fileDescriptor) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(fileDescriptor);
		ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
		try {
			readStream = (Companys) objectInputStream.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		objectInputStream.close();
	}
}
