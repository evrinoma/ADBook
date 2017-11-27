package forms;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;
import javax.swing.SwingWorker;
import javax.swing.tree.DefaultMutableTreeNode;

import entity.CompanyDto;
import entity.UserDto;
import libs.Companys;

public class LocalSearchThread extends SwingWorker<Object, String> {

	private Companys companys;
	private Companys filteredCompanys;
	private MainForm form;
	private boolean lock = false;
	private boolean searching = false;

	private String filterLastName = "";
	private String filterFirstName = "";
	private String filterMiddleName = "";
	private CompanyDto filterCompany = null;
	private String filterPhone = "";
	private String filterDescription = "";

	public LocalSearchThread(MainForm form) {
		this.form = form;
	}

	@Override
	protected Boolean doInBackground() throws Exception {
		while (true) {
			doSearching();
			if (isRestartSearch()) {
				stopSearch();
				doSearching();
			} else {
				return true;
			}
		}
	}

	// Can safely update the GUI from this method.
	protected void done() {
		resetLock();
		stopSearch();
		try {
			// Retrieve the return value of doInBackground.
			if ((boolean) get()) {
				form.setStatusBar("successful complite");
				
				form.top.removeAllChildren();
				form.setTreeNode(filteredCompanys.all());
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
		for (final String string : chunks) {
			form.setStatusBar(string);
		}
	}

	private boolean searchUser(List<UserDto> filteredUsers, List<UserDto> users, String lastName, String firstName,
			String middleName, String phone, String description) {
		boolean searchByLastName = (0 != lastName.length());
		boolean searchByFirstName = (0 != firstName.length());
		boolean searchByMiddleName = (0 != middleName.length());
		boolean searchByPhone = (0 != phone.length());
		boolean searchByDescription = (0 != description.length());
		boolean isNotFound = true;
		
		if ((!searchByLastName) & (!searchByFirstName) & (!searchByMiddleName) & (!searchByPhone)
				& (!searchByDescription)) {
			filteredUsers.addAll(users);
			return true;
		}

		for (UserDto user : users) {
			
			if (searchByFirstName & isNotFound) {
				if (!user.getFirstName().toLowerCase().contains(firstName)) {
					isNotFound = false;
				}
			}
			if (searchByLastName & isNotFound) {
				if (!user.getLastName().toLowerCase().contains(lastName)) {
					isNotFound = false;
				}
			}

			if (searchByMiddleName & isNotFound) {
				if (!user.getMiddleName().toLowerCase().contains(middleName)) {
					isNotFound = false;
				}
			}

			if (searchByPhone & isNotFound) {
				if (!user.getTelephoneNumber().toLowerCase().contains(phone)) {
					isNotFound = false;
				}
			}

			if (searchByDescription & isNotFound) {
				if (!user.getDescription().toLowerCase().contains(description)) {
					isNotFound = false;
				}
			}

			if (isNotFound) {
				filteredUsers.add(user);
			}

			if (isRestartSearch()) {
				form.setStatusBar("restart searching...");
				return false;
			}

			isNotFound = true;		
		}

		return true;
	}

	private boolean doSearching() {
		String filterLastName = this.filterLastName.toLowerCase();
		String filterFirstName = this.filterFirstName.toLowerCase();
		String filterMiddleName = this.filterMiddleName.toLowerCase();
		CompanyDto filterCompany = this.filterCompany;
		String filterPhone = this.filterPhone.toLowerCase();
		String filterDescription = this.filterDescription.toLowerCase();
		filteredCompanys = new Companys();
		if (filterCompany.isAllSelected()) {
			for (CompanyDto company : companys.all()) {
				List<UserDto> filteredUsers = new ArrayList<UserDto>();
				if (!searchUser(filteredUsers, company.getUsers(), filterLastName, filterFirstName, filterMiddleName,
						filterPhone, filterDescription)) {
					return false;
				}
				CompanyDto filtered = new CompanyDto(company.getName(), company.getDescription(), company.getDn());
				filtered.setUsers(filteredUsers);
				filteredCompanys.addNewCompany(filtered);
			}
		} else {
			List<UserDto> filteredUsers = new ArrayList<UserDto>();
			if (!searchUser(filteredUsers, filterCompany.getUsers(), filterLastName, filterFirstName, filterMiddleName,
					filterPhone, filterDescription)) {
				return false;
			}
			CompanyDto filtered = new CompanyDto(filterCompany.getName(), filterCompany.getDescription(),
					filterCompany.getDn());
			filtered.setUsers(filteredUsers);
			filteredCompanys.addNewCompany(filtered);
		}
		return true;
	}

	public void setLock() {
		this.lock = true;
	}

	public void resetLock() {
		this.lock = false;
	}

	public boolean isLock() {
		return this.lock;
	}

	public boolean isRestartSearch() {
		return searching;
	}

	public void stopSearch() {
		this.searching = false;
	}

	public void restartSearch() {
		this.searching = true;
	}

	public void setFilter(String lastName, String firstName, String middleName, CompanyDto company, String phone,
			String description) {
		this.filterLastName = lastName;
		this.filterFirstName = firstName;
		this.filterMiddleName = middleName;
		this.filterCompany = company;
		this.filterPhone = phone;
		this.filterDescription = description;
	}

	public Companys getCompanys() {
		return companys;
	}

	public void setCompanys(Companys companys) {
		this.companys = companys;
	}

}
