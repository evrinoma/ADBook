package libs;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entity.CompanyDto;
import entity.UserDto;

public class Companys implements Serializable {
	private List<CompanyDto> companys;
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

	public List<CompanyDto> all() {
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

	public void setCompanys(List<CompanyDto> companys) {
		this.companys = companys;
	}

	public List<String> toArrayDescriptionCompanys() {
		List<String> companysName = new ArrayList<String>();
		for (CompanyDto company : companys) {
			companysName.add(company.getDescription());
		}
		return companysName;
	}

	public List<String> toArrayDnCompanys() {
		List<String> companysDescription = new ArrayList<String>();
		for (CompanyDto company : companys) {
			companysDescription.add(company.getDn());
		}
		return companysDescription;
	}

	public List<String> toArrayOuCompanys() {
		List<String> companysDn = new ArrayList<String>();
		for (CompanyDto company : companys) {
			companysDn.add(company.getOu());
		}
		return companysDn;
	}
/*
	private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
		System.out.println("read companys");
	}

	private void writeObject(ObjectOutputStream aOutputStream) throws IOException {
		System.out.println("write companys");
	}*/

}
