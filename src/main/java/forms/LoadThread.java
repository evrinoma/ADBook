package forms;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import libs.Companys;
import libs.Core;

public class LoadThread extends SwingWorker<Object, String> {

	public static final boolean READ = true;
	public static final boolean WRITE = !READ;
	private static final String FILE_CASHE = "/opt/DISK/Develop/Java/Eclipse/EEProjects/browser/src/main/resources/cashe";

	private Core core = null;
	private Companys writeStream = null;
	private Companys readStream = null;
	private boolean direction = READ;

	public LoadThread(Core core) {
		this.core = core;
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

	// Can safely update the GUI from this method.
	protected void done() {
		try {
			// Retrieve the return value of doInBackground.
			if ((boolean) get()) {
				if (direction) {
					core.isLocalCacheSuccessful(readStream);
					core.setStatusString("successful read");
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
	private boolean saveCache() {

		if (null != writeStream) {
			try {
				FileOutputStream fileOut = new FileOutputStream(FILE_CASHE);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(writeStream);
				out.close();
				fileOut.close();
				return true;
			} catch (IOException i) {
				i.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * загрузка из кеша
	 * 
	 * @return
	 */
	private boolean loadCache() {
		readStream = new Companys();
		try {
			FileInputStream fileIn = new FileInputStream(FILE_CASHE);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			readStream = (Companys) in.readObject();
			in.close();
			fileIn.close();
			return true;
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		return false;
	}
}
