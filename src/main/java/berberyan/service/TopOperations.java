package berberyan.service;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Configuration;

import berberyan.model.Company;

@Configuration
public interface TopOperations {

	//oldest companies from each sector
	Map<String, List<Company>> getOldest(List<Company> companies, int howMany);
	
	//oldest companies from the list
	List<Company> getOldestFromList(List<Company> companies, int howMany);
	
	//ten most expensive companies from each sector
	Map<String, List<Company>> getMostExpensive(List<Company> companies, int howMany);
	
	//ten most expensive from the list
	List<Company> getMostExpensiveFromList(List<Company> companies, int howMany);
	
	//ten companies with biggest volume in each sector
	Map<String, List<Company>> getBiggestVolume(List<Company> companies, int howMany);
	
	//ten with the biggest volume
	List<Company> getBiggestVolumeFromList(List<Company> companies, int howMany);
}
