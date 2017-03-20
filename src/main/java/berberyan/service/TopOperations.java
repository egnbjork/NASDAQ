package berberyan.service;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Configuration;

import berberyan.model.Company;

@Configuration
public interface TopOperations {

	//oldest companies from each sector
	Map<String, List<Company>> oldest(List<Company> companies, int howMany);
	
	//ten most expensive companies from each sector
	
	//count companies in each sector
	
	//ten companies with biggest volume
	
	//companies per sector rate
	
	//count industries
}
