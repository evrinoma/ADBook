package entity;

import java.util.ArrayList;
import java.util.List;

public class CompanyDto {
	
	private static final String isAll = "*";
	
	private String description;
	private String dn;
	private String ou;
	private List<UserDto> users;
	private List<CompanyDto> filials;

	private void createCompanyDto(String description, String ou, String dn)
	{
		users = new ArrayList<UserDto>();
		filials = new ArrayList<CompanyDto>();		
		this.description = description;
		this.ou = ou;
		this.dn = dn;
	}
	
	public CompanyDto(String description, String ou, String dn) {
		createCompanyDto(description, ou, dn);
	}	
	
	public CompanyDto(CompanyDto company) {		
		createCompanyDto(company.getDescription(), company.getOu(), company.getDn());
	}
	
	public CompanyDto() {		
		createCompanyDto("Все", isAll, isAll);
	}

	public boolean isAllSelected() {		
		return (isAll ==  this.dn && isAll == this.ou);
	}
	
	public String getOu() {
		return ou;
	}

	public String getDescription() {
		return description;
	}

	public String getDn() {
		return dn;
	}

	@Override
	public String toString() {
		return description;
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
	
	public List<CompanyDto> getFilials() {
		return filials;
	}
	
	public void setFilials(List<CompanyDto> filials) {
		this.filials = filials;
	}

	public void addNewFilial(CompanyDto filial) {
		filials.add(filial);
	}
	
	public void addNewFilial(String name, String description, String ou) {
		filials.add(new CompanyDto(name, description, ou));
	}
}
