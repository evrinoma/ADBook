package forms;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JLabel;
import javax.swing.SwingWorker;

import libs.Core;

public class  LdapSearchThread extends SwingWorker<Object, String> {

	private Core core;
	private MainForm form;
	
	
	public LdapSearchThread(Core core, MainForm form)
	{
		this.core = core;
		this.form = form;
	}
	
	@Override
	protected Boolean doInBackground() throws Exception {			
		
		publish(core.getHintOpenConnection());		
		core.openConnection();
		publish(core.getHintLoadCompanys());	
		core.loadCompanys();
		publish(core.getHintLoadUsers());	
		core.loadUsers();	
		publish(core.getHintCloseConnection());			
		core.closeConnection();			
		
		return true;
	}

	// Can safely update the GUI from this method.
	protected void done() {
		boolean status;
		try {
			// Retrieve the return value of doInBackground.
			status = (boolean) get();
			if (status) {
				form.setCompanySelector();
				form.setTreeNode(core.getCompanys().all());
				form.setStatusBar(core.getHintEmpty());
			}
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
		for (final String string : chunks) {			
			form.setStatusBar(string);
		}
	}

}
