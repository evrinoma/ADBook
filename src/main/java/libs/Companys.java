package libs;

import java.util.ArrayList;
import java.util.List;

import entity.CompanyDto;

public class Companys {
	private List<CompanyDto> companys;
	
	public void addNewCompany(String name, String description, String dn) {
		companys.add(new CompanyDto(name, description, dn));
	}

	public Companys() {		
		companys = new ArrayList<CompanyDto>();
	}
	
	public List<CompanyDto> all() {
		return companys;
	}

	public void setCompanys(List<CompanyDto> companys) {
		this.companys = companys;
	}

	public List<String> toArrayNamesCompanys() {
		List<String> companysName = new ArrayList<String>();
		for (CompanyDto company : companys) {
			companysName.add(company.getName());
		}
		return companysName;
	}

	public List<String> toArrayDescriptionsCompanys() {
		List<String> companysDescription = new ArrayList<String>();
		for (CompanyDto company : companys) {
			companysDescription.add(company.getDescription());
		}
		return companysDescription;
	}

	public List<String> toArrayDnsCompanys() {
		List<String> companysDn = new ArrayList<String>();
		for (CompanyDto company : companys) {
			companysDn.add(company.getDn());
		}
		return companysDn;
	}
}
