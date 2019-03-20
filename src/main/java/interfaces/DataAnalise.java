package interfaces;

import entity.CompanyDto;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;

public interface DataAnalise {
    CompanyDto getCompany(SearchResult searchResult, Attributes attrs) throws NamingException;
    String[] getSelectorCompanys();
    String[] getSelectorFilials();
}
