package entity;

public class FilterDto {
	private String filterLastName = "";
	private String filterFirstName = "";
	private String filterMiddleName = "";
	private CompanyDto filterCompany = null;
	private CompanyDto filterFilial = null;
	private String filterDepartment = "";	
	private String filterTelephoneNumber = "";
	private String filterPesonPosition = "";

	public FilterDto(String lastName, String firstName, String middleName, CompanyDto company, CompanyDto filial, String department, String telephoneNumber,
			String pesonPosition) {
		this.filterLastName = lastName;
		this.filterFirstName = firstName;
		this.filterMiddleName = middleName;
		this.filterCompany = company;
		this.filterFilial = filial;
		this.filterDepartment = department;
		this.filterTelephoneNumber = telephoneNumber;
		this.filterPesonPosition = pesonPosition;
	}

	public FilterDto(FilterDto filter) {
		this.filterLastName = filter.getFilterLastName();
		this.filterFirstName = filter.getFilterFirstName();
		this.filterMiddleName = filter.getFilterMiddleName();
		this.filterCompany = filter.getFilterCompany();
		this.filterFilial = filter.getFilterFilial();
		this.filterDepartment = filter.getFilterDepartment();
		this.filterTelephoneNumber = filter.getFilterTelephoneNumber();
		this.filterPesonPosition = filter.getFilterPesonPosition();
	}	
	
	public String getFilterLastName() {
		return filterLastName.toLowerCase();
	}

	public String getFilterFirstName() {
		return filterFirstName.toLowerCase();
	}

	public String getFilterMiddleName() {
		return filterMiddleName.toLowerCase();
	}

	public CompanyDto getFilterCompany() {
		return filterCompany;
	}

	public CompanyDto getFilterFilial() {
		return filterFilial;
	}

	public String getFilterDepartment() {
		return filterDepartment.toLowerCase();
	}

	public String getFilterTelephoneNumber() {
		return filterTelephoneNumber.toLowerCase();
	}

	public String getFilterPesonPosition() {
		return filterPesonPosition.toLowerCase();
	}
	
	public boolean isFilterLastNameSet() {
		return (0 != filterLastName.length());
	}

	public boolean isFilterFirstNameSet() {
		return (0 != filterFirstName.length());
	}

	public boolean isFilterMiddleNameSet() {
		return (0 != filterMiddleName.length());
	}

	public boolean isFilterPhoneSet() {
		return (0 != filterTelephoneNumber.length());
	}

	public boolean isFilterPesonPositionSet() {
		return (0 != filterPesonPosition.length());
	}
	
	public boolean isFilterDepartmentSet() {
		return (0 != filterDepartment.length());
	}
}
