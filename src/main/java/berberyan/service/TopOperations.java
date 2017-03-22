package berberyan.service;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Configuration;

import berberyan.model.Company;

@Configuration
public interface TopOperations {

	//oldest companies from each sector
	Map<String, List<Company>> getOldest(List<Company> companies, int howMany);
	
	//ten most expensive companies from each sector
	Map<String, List<Company>> getMostExpensive(List<Company> companies, int howMany);
	
	//ten companies with biggest volume in each sector
	Map<String, List<Company>> getBiggestVolume(List<Company> companies, int howMany);
	
	//oldest companies
	//most expensive companies
	//ten biggest companies
	//count companies in each sector
	//count all companies
	//companies per sector rate
	//count industries
}
