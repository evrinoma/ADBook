package threads;


import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import entity.CompanyDto;
import entity.FilterDto;
import entity.UserDto;
import libs.Companys;
import libs.Core;

public class LocalSearchThread extends SwingWorker<Object, String> {

	private Companys companys;
	private Companys filteredCompanys;
	private boolean lock = false;
	private boolean searching = false;
	private Core core = null;

	private FilterDto filterDto = null;

	public LocalSearchThread(Core core) {
		this.core = core;
	}

	@Override
	protected Boolean doInBackground() throws Exception {
		while (true) {
			return doSearching();		
		}
	}
	
	private void flush()
	{
		core = null;
		filteredCompanys = null;
		companys = null;
		filterDto = null;
	}
	
	// Can safely update the GUI from this method.
	protected void done() {
		resetLock();
		stopSearch();
		try {
			// Retrieve the return value of doInBackground.
			if ((boolean) get()) {
				core.isLocalSearchSuccessful(filteredCompanys);
				core.setStatusString("successful complite");
				
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

	private boolean searchUser(HashMap<String, UserDto> filteredUsers, HashMap<String, UserDto> users,
			FilterDto filterDto) {
		boolean searchByLastName = filterDto.isFilterLastNameSet();
		boolean searchByFirstName = filterDto.isFilterFirstNameSet();
		boolean searchByMiddleName = filterDto.isFilterMiddleNameSet();
		boolean searchByDepartment = filterDto.isFilterDepartmentSet();
		boolean searchByPhone = filterDto.isFilterPhoneSet();
		boolean searchByPesonPosition = filterDto.isFilterPesonPositionSet();
		boolean searchByRoom = filterDto.isFilterRoomSet();
		boolean isNotFound = true;

		if ((!searchByLastName) & (!searchByFirstName) & (!searchByMiddleName) & (!searchByPhone)
				& (!searchByPesonPosition) & (!searchByDepartment) & (!searchByRoom)) {
			filteredUsers.putAll(users);
			return true;
		}

		for (UserDto user : users.values()) {

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

			if (searchByPesonPosition & isNotFound) {
				if (!user.isDescription(filterDto.getFilterPesonPosition())) {
					isNotFound = false;
				}
			}

			if (searchByDepartment & isNotFound) {
				if (!user.isDepartment(filterDto.getFilterDepartment())) {
					isNotFound = false;
				}
			}

			if (searchByRoom & isNotFound) {
				if (!user.isPhysicalDeliveryOfficeName(filterDto.getFilterRoom())) {
					isNotFound = false;
				}
			}
			
			if (isNotFound) {
				filteredUsers.put(user.getDistinguishedName(), user);
			}

			if (isRestartSearch()) {
				core.setStatusString("restart searching...");
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
				HashMap<String, UserDto> filteredUsers = new HashMap<String, UserDto>();
				if (!searchUser(filteredUsers, filial.getUsers(), filterDto)) {
					return false;
				}
				if (!filteredUsers.isEmpty()) {
					CompanyDto filteredFilial = new CompanyDto(filial);
					filteredFilial.setUsers(filteredUsers);
					filtered.addNewFilial(filteredFilial);
				}

				if (isRestartSearch()) {
					core.setStatusString("restart searching...");
					return false;
				}
			}
		} else {
			HashMap<String, UserDto> filteredUsers = new HashMap<String, UserDto>();
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
			String department, String telephoneNumber, String pesonPosition, String room) {
		filterDto = new FilterDto(lastName, firstName, middleName, company, filial, department, telephoneNumber,
				pesonPosition, room);
	}

	public Companys getCompanys() {
		return companys;
	}

	public void setCompanys(Companys companys) {
		this.companys = companys;
	}

}
