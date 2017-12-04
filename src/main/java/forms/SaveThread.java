package forms;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import libs.Core;


public class SaveThread extends SwingWorker<Object, String> {

	private Core core;
	
	public SaveThread(Core core)
	{
		this.core = core;
	}
	
	@Override
	protected Boolean doInBackground() throws Exception {		
		return true;
	}	
	
	// Can safely update the GUI from this method.
	protected void done() {
		boolean status;
		try {
			// Retrieve the return value of doInBackground.
			status = (boolean) get();
			
			//System.out.println("Completed with status: " + status);
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
}
