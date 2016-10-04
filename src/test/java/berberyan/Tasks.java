package berberyan;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class Tasks {
	private static final Logger logger = LogManager.getLogger(Tasks.class);
	
	@Test
	public void test_ten_oldest_companies_from_each_sector_companyList(){
		Nasdaq.tenOldestBySector("src/test/companylist.csv",
						"src/test/companies_by_sector.json");
	}
	
	@Test
	public void test_ten_most_expensive_companies_from_each_sector(){
		Nasdaq.tenMostExpensiveBySector("src/test/companylist.csv",
						"src/test/expensive_companies_by_sector.json");
	}
	
	@Test
	public void test_count_company_percentage_in_each_sector(){
		Map<String,Integer> countCompanies = 
				Nasdaq.CompanySectorRate("src/test/companylist.csv");
		logger.info(countCompanies);
	}
	
	@Test
	public void test_ten_companies_biggest_share_amount(){
		Nasdaq.tenBiggestShareAmount("src/test/companylist.csv",
				"src/test/ten_biggest_share_amount.json");
	}
}
