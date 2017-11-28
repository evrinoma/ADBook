package entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;

public class CompanyDto {
	
	private static final String isAll = "*";
	
	private String description;
	private String dn;
	private String ou;
	private HashMap<String,UserDto> users;
	private List<CompanyDto> filials;

	private void createCompanyDto(String description, String ou, String dn)
	{
		users = new HashMap<String,UserDto>();
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

	public HashMap<String,UserDto> getUsers() {
		return users;
	}
	
	private HashMap<String,UserDto> sortedByCn(HashMap<String,UserDto> users) {
		List list = new LinkedList(users.entrySet());
		 Collections.sort(list, new Comparator() {
	            public int compare(Object o1, Object o2) {
	            	UserDto user1 = (UserDto) ((Map.Entry) (o1)).getValue();
	            	UserDto user2 = (UserDto) ((Map.Entry) (o2)).getValue();
	               return (user1.getCn()).compareTo(user2.getCn());
	            }
	       });
		
		 HashMap sortedHashMap = new LinkedHashMap();
	       for (Iterator it = list.iterator(); it.hasNext();) {
	              Map.Entry entry = (Map.Entry) it.next();
	              sortedHashMap.put(entry.getKey(), entry.getValue());
	       } 
	    return sortedHashMap;
	}
	
	public HashMap<String,UserDto> getUsersSortedByCn() {		
	    return sortedByCn(users);
	}
	
	public HashMap<String,UserDto> getUsersSortedByCn(HashMap<String,UserDto> users) {		
	    return sortedByCn(users);
	}
	
	public void setUsers(HashMap<String,UserDto> users) {
		this.users.putAll(users);
	}

	public void addNewUser(UserDto user) {
		users.put(user.getDistinguishedName(),user);
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
