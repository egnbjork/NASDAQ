package berberyan;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class Tasks {
	private static final Logger logger = LogManager.getLogger(Export2JsonTest.class);
	
	@Test
	public void test_ten_oldest_companies_from_each_sector_companyList(){
		List<List<Company>>oldestCompanies = 
				Nasdaq.tenOldestCompaniesBySector("src/test/companylist.csv");
		for (List<Company> sector: oldestCompanies){
			logger.debug(sector.get(0).getSector());
			for (Company company : sector){
				logger.debug(company.getSymbol() + " "
						+ company.getSector() + " "
						+ company.getIpo());
			}
			logger.debug("end of sector " + sector.get(0).getSector());
		}
	}
}
