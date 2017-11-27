package entity;

import java.util.ArrayList;
import java.util.List;

public class CompanyDto {
	
	private static final String isAll = "*";
	
	private String name;
	private String description;
	private String dn;
	private List<UserDto> users;

	private void createCompanyDto(String name, String description, String dn)
	{
		users = new ArrayList<UserDto>();
		this.name = name;
		this.description = description;
		this.dn = dn;
	}
	
	public CompanyDto(String name, String description, String dn) {
		createCompanyDto(name, description, dn);
	}	
	
	public CompanyDto() {		
		createCompanyDto("Все", isAll, isAll);
	}

	public boolean isAllSelected() {		
		return (isAll ==  this.description && isAll == this.dn);
	}
	
	public String getDn() {
		return dn;
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

	public List<UserDto> getUsers() {
		return users;
	}
	
	public void setUsers(List<UserDto> users) {
		this.users = users;
	}

	public void addNewUser(UserDto user) {
		users.add(user);
	}
}
