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
import entity.FilterDto;
import entity.UserDto;
import libs.Companys;

public class LocalSearchThread extends SwingWorker<Object, String> {

	private Companys companys;
	private Companys filteredCompanys;
	private MainForm form;
	private boolean lock = false;
	private boolean searching = false;

	private FilterDto filterDto = null;

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
				form.setTreeNode(filteredCompanys.all(), false);
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

	private boolean searchUser(List<UserDto> filteredUsers, List<UserDto> users, FilterDto filterDto) {
		boolean searchByLastName = filterDto.isFilterLastNameSet();
		boolean searchByFirstName = filterDto.isFilterFirstNameSet();
		boolean searchByMiddleName = filterDto.isFilterMiddleNameSet();
		boolean searchByPhone = filterDto.isFilterPhoneSet();
		boolean searchByDescription = filterDto.isFilterDescriptionSet();
		boolean isNotFound = true;

		if ((!searchByLastName) & (!searchByFirstName) & (!searchByMiddleName) & (!searchByPhone)
				& (!searchByDescription)) {
			filteredUsers.addAll(users);
			return true;
		}

		for (UserDto user : users) {

			if (searchByFirstName & isNotFound) {
				if (!user.isFirstName(filterDto.getFilterFirstName())) {
					isNotFound = false;
				}
			}
			if (searchByLastName & isNotFound) {
				if (!user.isLastName(filterDto.getFilterLastName())) {
					isNotFound = false;
				}
			}

			if (searchByMiddleName & isNotFound) {
				if (!user.isMiddleName(filterDto.getFilterMiddleName())) {
					isNotFound = false;
				}
			}

			if (searchByPhone & isNotFound) {
				if (!user.isTelephoneNumber(filterDto.getFilterTelephoneNumber())) {
					isNotFound = false;
				}
			}

			if (searchByDescription & isNotFound) {
				if (!user.isDescription(filterDto.getFilterDescription())) {
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

	private boolean searchCompany(CompanyDto filtered, CompanyDto company, FilterDto filterDto) {
		if (!company.getFilials().isEmpty()) {
			for (CompanyDto filial : company.getFilials()) {
				if (!filterDto.getFilterFilial().isAllSelected()) {
					if (filial.getDn() != filterDto.getFilterFilial().getDn())
						continue;
				}
				List<UserDto> filteredUsers = new ArrayList<UserDto>();
				if (!searchUser(filteredUsers, filial.getUsers(), filterDto)) {
					return false;
				}
				if (!filteredUsers.isEmpty()) {
					CompanyDto filteredFilial = new CompanyDto(filial);
					filteredFilial.setUsers(filteredUsers);
					filtered.addNewFilial(filteredFilial);
				}

				if (isRestartSearch()) {
					form.setStatusBar("restart searching...");
					return false;
				}
			}
		} else {
			List<UserDto> filteredUsers = new ArrayList<UserDto>();
			if (!searchUser(filteredUsers, company.getUsers(), filterDto)) {
				return false;
			}
			filtered.setUsers(filteredUsers);
		}

		return true;
	}

	private boolean doSearching() {
		FilterDto savefilterDto = new FilterDto(filterDto);

		filteredCompanys = new Companys();
		if (savefilterDto.getFilterCompany().isAllSelected()) {
			for (CompanyDto company : companys.all()) {
				CompanyDto filtered = new CompanyDto(company);
				if (!searchCompany(filtered, company, savefilterDto)) {
					return false;
				}
				filteredCompanys.addNewCompany(filtered);
			}
		} else {
			CompanyDto company = savefilterDto.getFilterCompany();
			CompanyDto filtered = new CompanyDto(company);
			if (!searchCompany(filtered, company, savefilterDto)) {
				return false;
			}
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

	public void setFilter(String lastName, String firstName, String middleName, CompanyDto company, CompanyDto filial,
			String telephoneNumber, String description) {
		filterDto = new FilterDto(lastName, firstName, middleName, company, filial, telephoneNumber, description);
	}

	public Companys getCompanys() {
		return companys;
	}

	public void setCompanys(Companys companys) {
		this.companys = companys;
	}

}
