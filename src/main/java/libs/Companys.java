package libs;

import java.util.ArrayList;
import java.util.List;

import entity.CompanyDto;

public class Companys {
	private List<CompanyDto> companys;
	
	public void addNewCompany(String description, String ou, String dn) {
		companys.add(new CompanyDto(description, ou, dn));
	}
	
	public void addNewCompany(CompanyDto company) {
		companys.add(company);
	}

	public Companys() {		
		companys = new ArrayList<CompanyDto>();
	}
	
	public List<CompanyDto> all() {
		return companys;
	}

	public CompanyDto getLastInsertCompany()
	{		
		return (CompanyDto) companys.get(companys.size()-1); 
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
}
