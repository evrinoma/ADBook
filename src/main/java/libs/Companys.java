package libs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import entity.CompanyDto;
import entity.UserDto;

public class Companys implements Serializable {
	private ArrayList<CompanyDto> companys;
	private HashMap<String, UserDto> users;

	public void addNewCompany(String description, String ou, String dn) {
		companys.add(new CompanyDto(description, ou, dn));
	}

	public void addNewCompany(CompanyDto company) {
		companys.add(company);
	}

	public Companys() {
		companys = new ArrayList<CompanyDto>();
		users = new HashMap<String, UserDto>();
	}

	public ArrayList<CompanyDto> all() {
		return companys;
	}

	public ArrayList<CompanyDto> getCompanys() {
		return companys;
	}
	
	public HashMap<String, UserDto> getUsers() {
		return users;
	}

	public void copyUser(HashMap<String, UserDto> users) {
		this.users.putAll(users);
	}

	public CompanyDto getLastInsertCompany() {
		return (CompanyDto) companys.get(companys.size() - 1);
	}

	public void setCompanys(ArrayList<CompanyDto> companys) {
		this.companys = companys;
	}

	public ArrayList<String> toArrayDescriptionCompanys() {
		ArrayList<String> companysName = new ArrayList<String>();
		for (CompanyDto company : companys) {
			companysName.add(company.getDescription());
		}
		return companysName;
	}

	public ArrayList<String> toArrayDnCompanys() {
		ArrayList<String> companysDescription = new ArrayList<String>();
		for (CompanyDto company : companys) {
			companysDescription.add(company.getDn());
		}
		return companysDescription;
	}

	public ArrayList<String> toArrayOuCompanys() {
		ArrayList<String> companysDn = new ArrayList<String>();
		for (CompanyDto company : companys) {
			companysDn.add(company.getOu());
		}
		return companysDn;
	}
}
