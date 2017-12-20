package libs;

import java.io.Serializable;
import java.util.ArrayList;

import entity.CompanyDto;
import entity.UserDto;

public class Companys implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ArrayList<CompanyDto> companys;

	public void addNewCompany(String description, String ou, String dn) {
		companys.add(new CompanyDto(description, ou, dn));
	}

	public void addNewCompany(CompanyDto company) {
		companys.add(company);
	}

	public Companys() {
		companys = new ArrayList<CompanyDto>();		
	}

	public ArrayList<CompanyDto> all() {
		return companys;
	}

	public ArrayList<CompanyDto> getCompanys() {
		return companys;
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

	public UserDto findUserByMail(String email) {
		UserDto search = null;
		return findEmail(companys, search, email);
	}

	public UserDto findUserBysAMAccountName(String sAMAccountName) {
		UserDto search = null;
		return findsAMAccountName(companys, search, sAMAccountName);
	}
	
	public UserDto findUserByDistinguishedName(String DistinguishedName) {
		UserDto search = null;
		return findDistinguishedName(companys, search, DistinguishedName);
	}
	
	private UserDto findEmail(ArrayList<CompanyDto> companys, UserDto search, String email)
	{
			for (CompanyDto company : companys) {
				if (0 == company.getFilials().size()) {
					if (0 != company.getUsers().size()) {
						for (UserDto user : company.getUsers().values()) {
							if (email.equals(user.getMail())) {
								return user;
							}							
						}
					}
				} else {
					search = findEmail(company.getFilials(), search, email);		
					if (null != search){
						return search;
					}
				}
			}			
			return null;		
	}
	
	private UserDto findsAMAccountName(ArrayList<CompanyDto> companys, UserDto search, String sAMAccountName)
	{
			for (CompanyDto company : companys) {
				if (0 == company.getFilials().size()) {
					if (0 != company.getUsers().size()) {
						for (UserDto user : company.getUsers().values()) {
							if (sAMAccountName.equals(user.getSAMAccountName())) {
								return user;
							}							
						}
					}
				} else {
					search = findsAMAccountName(company.getFilials(), search, sAMAccountName);		
					if (null != search){
						return search;
					}
				}
			}			
			return null;		
	}
	
	private UserDto findDistinguishedName(ArrayList<CompanyDto> companys, UserDto search, String distinguishedName)
	{
			for (CompanyDto company : companys) {
				if (0 == company.getFilials().size()) {
					if (0 != company.getUsers().size()) {
						for (UserDto user : company.getUsers().values()) {
							if (distinguishedName.equals(user.getDistinguishedName())) {
								return user;
							}							
						}
					}
				} else {
					search = findDistinguishedName(company.getFilials(), search, distinguishedName);		
					if (null != search){
						return search;
					}
				}
			}			
			return null;		
	}
}
