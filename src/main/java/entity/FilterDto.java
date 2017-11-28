package entity;

public class FilterDto {
	private String filterLastName = "";
	private String filterFirstName = "";
	private String filterMiddleName = "";
	private CompanyDto filterCompany = null;
	private String filterTelephoneNumber = "";
	private String filterDescription = "";

	public FilterDto(String lastName, String firstName, String middleName, CompanyDto company, String telephoneNumber,
			String description) {
		this.filterLastName = lastName;
		this.filterFirstName = firstName;
		this.filterMiddleName = middleName;
		this.filterCompany = company;
		this.filterTelephoneNumber = telephoneNumber;
		this.filterDescription = description;
	}

	public FilterDto(FilterDto filter) {
		this.filterLastName = filter.getFilterLastName();
		this.filterFirstName = filter.getFilterFirstName();
		this.filterMiddleName = filter.getFilterMiddleName();
		this.filterCompany = filter.getFilterCompany();
		this.filterTelephoneNumber = filter.getFilterTelephoneNumber();
		this.filterDescription = filter.getFilterDescription();
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

	public String getFilterTelephoneNumber() {
		return filterTelephoneNumber.toLowerCase();
	}

	public String getFilterDescription() {
		return filterDescription.toLowerCase();
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

	public boolean isFilterDescriptionSet() {
		return (0 != filterDescription.length());
	}
	
}
