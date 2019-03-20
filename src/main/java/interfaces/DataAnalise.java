package interfaces;

import entity.CompanyDto;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;

public interface DataAnalise {
    CompanyDto getCompanys(SearchResult companys, Attributes attrs) throws NamingException;
}
