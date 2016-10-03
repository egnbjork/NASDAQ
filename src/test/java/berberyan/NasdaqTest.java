package berberyan;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class NasdaqTest {
	private static final Logger logger = LogManager.getLogger(Export2JsonTest.class);
	
	@Test
	public void test_extract_sectors_from_empty_list(){
		List<Company>empty = new ArrayList<Company>();
		List<String>processed = Nasdaq.extractSector(empty);
		assertEquals(new ArrayList<String>(), processed);
	}
	
	@Test
	public void test_extract_sectors_from_one_company(){
		List<Company>companies = new ArrayList<Company>();
		companies.add(new Company("PIH","1347 Property Insurance Holdings, Inc.","6.0079","$36.21M","2014","Finance","Property-Casualty Insurers","http://www.nasdaq.com/symbol/pih"));
		List<String>processed = Nasdaq.extractSector(companies);
		List<String> expected = new ArrayList();
		expected.add("Finance");
		
		assertEquals(expected, processed);
	}
	
	@Test
	public void test_extract_sectors_from_two_company_different_sector(){
		List<Company>companies = new ArrayList<Company>();
		companies.add(new Company("PIH","1347 Property Insurance Holdings, Inc.","6.0079","$36.21M","2014","Finance","Property-Casualty Insurers","http://www.nasdaq.com/symbol/pih"));
		companies.add(new Company("FLWS","1-800 FLOWERS.COM, Inc.","9.27","n/a","n/a","Consumer Services","Other Specialty Stores","http://www.nasdaq.com/symbol/flws"));
		List<String>processed = Nasdaq.extractSector(companies);
		List<String>expected = new ArrayList();
		expected.add("Finance");
		expected.add("Consumer Services");
		assertEquals(expected, processed);
	}
	
	@Test
	public void test_extract_sectors_from_two_companies_same_sector(){
		List<Company>companies = new ArrayList<Company>();
		companies.add(new Company("PIH","1347 Property Insurance Holdings, Inc.","6.0079","$36.21M","2014","Finance","Property-Casualty Insurers","http://www.nasdaq.com/symbol/pih"));
		companies.add(new Company("FCCY","1st Constitution Bancorp (NJ)","13.525","$107.56M","n/a","Finance","Savings Institutions","http://www.nasdaq.com/symbol/fccy"));
		List<String>processed = Nasdaq.extractSector(companies);
		List<String> expected = new ArrayList();
		expected.add("Finance");
		
		assertEquals(expected, processed);
	}
	
	@Test
	public void test_extract_oldest_company_from_empty_list(){
		List<Company>empty = new ArrayList<Company>();
		List<Company>found = Nasdaq.getOldestCompanies(1,empty);
		List<Company>expected = new ArrayList<Company>();
		assertEquals(expected, found);
	}
	
	@Test
	public void test_extract_oldest_company_from_list_of_one(){
		List<Company>companies = new ArrayList<Company>();
		companies.add(new Company("FCCY","1st Constitution Bancorp (NJ)","13.525","$107.56M","n/a","Finance","Savings Institutions","http://www.nasdaq.com/symbol/fccy"));
		List<Company>found = Nasdaq.getOldestCompanies(1,companies);
		List<Company>expected = new ArrayList<Company>();
		expected.add(companies.get(0));
		assertEquals(expected, found);
	}
	
	@Test
	public void test_extract_oldest_company_from_list_of_two_with_diff_year(){
		List<Company>companies = new ArrayList<Company>();
		companies.add(new Company("FCCY","1st Constitution Bancorp (NJ)","13.525","$107.56M","2014","Finance","Savings Institutions","http://www.nasdaq.com/symbol/fccy"));
		companies.add(new Company("PIH","1347 Property Insurance Holdings, Inc.","6.0079","$36.21M","2012","Finance","Property-Casualty Insurers","http://www.nasdaq.com/symbol/pih"));
		List<Company>found = Nasdaq.getOldestCompanies(1,companies);
		List<Company>expected = new ArrayList<Company>();
		expected.add(companies.get(1));
		assertEquals(expected, found);
	}
	
	@Test
	public void test_extract_oldest_company_from_list_of_two_with_same_year(){
		List<Company>companies = new ArrayList<Company>();
		companies.add(new Company("PIH","1347 Property Insurance Holdings, Inc.","6.0079","$36.21M","2014","Finance","Property-Casualty Insurers","http://www.nasdaq.com/symbol/pih"));
		companies.add(new Company("FLWS","1-800 FLOWERS.COM, Inc.","9.27","$602.65M","2014","Consumer Services","Other Specialty Stores","http://www.nasdaq.com/symbol/flws"));
		List<Company>found = Nasdaq.getOldestCompanies(1,companies);
		List<Company>expected = new ArrayList<Company>();
		expected.add(companies.get(0));
		expected.add(companies.get(1));
		assertEquals(expected, found);
	}
	
	@Test
	public void test_extract_oldest_company_from_list_of_three_all_diff_year(){
		List<Company>companies = new ArrayList<Company>();
		companies.add(new Company("FLWS","1-800 FLOWERS.COM, Inc.","9.27","$602.65M","1999","Consumer Services","Other Specialty Stores","http://www.nasdaq.com/symbol/flws"));
		companies.add(new Company("FLWS","1-800 FLOWERS.COM, Inc.","9.27","$602.65M","1993","Consumer Services","Other Specialty Stores","http://www.nasdaq.com/symbol/flws"));
		companies.add(new Company("VNET","21Vianet Group, Inc.","8.51","$968.86M","2011","Technology","Computer Software: Programming, Data Processing","http://www.nasdaq.com/symbol/vnet"));
		List<Company>found = Nasdaq.getOldestCompanies(1,companies);
		List<Company>expected = new ArrayList<Company>();
		expected.add(companies.get(1));
		assertEquals(expected, found);
	}
	
	@Test
	public void test_extract_oldest_company_from_list_of_three_two_diff_year_one_na(){
		List<Company>companies = new ArrayList<Company>();
		companies.add(new Company("FLWS","1-800 FLOWERS.COM, Inc.","9.27","$602.65M","1999","Consumer Services","Other Specialty Stores","http://www.nasdaq.com/symbol/flws"));
		companies.add(new Company("FLWS","1-800 FLOWERS.COM, Inc.","9.27","$602.65M","n/a","Consumer Services","Other Specialty Stores","http://www.nasdaq.com/symbol/flws"));
		companies.add(new Company("VNET","21Vianet Group, Inc.","8.51","$968.86M","2011","Technology","Computer Software: Programming, Data Processing","http://www.nasdaq.com/symbol/vnet"));
		List<Company>found = Nasdaq.getOldestCompanies(1,companies);
		List<Company>expected = new ArrayList<Company>();
		expected.add(companies.get(0));
		assertEquals(expected, found);
	}
	
	@Test
	public void test_extract_oldest_company_from_list_of_three_same_year_two_of_them(){
		List<Company>companies = new ArrayList<Company>();
		companies.add(new Company("PIH","1347 Property Insurance Holdings, Inc.","6.0079","$36.21M","2014","Finance","Property-Casualty Insurers","http://www.nasdaq.com/symbol/pih"));
		companies.add(new Company("TWOU","2U, Inc.","37.41","$1.75B","2014","Technology","Computer Software: Prepackaged Software","http://www.nasdaq.com/symbol/twou"));
		companies.add(new Company("CAFD","8point3 Energy Partners LP","14.8","$1.05B","2015","Public Utilities","Electric Utilities: Central","http://www.nasdaq.com/symbol/cafd"));
		List<Company>found = Nasdaq.getOldestCompanies(1,companies);
		List<Company>expected = new ArrayList<Company>();
		expected.add(companies.get(0));
		expected.add(companies.get(1));
		assertEquals(expected, found);
	}
	
	@Test
	public void test_extract_oldest_company_from_list_of_three_same_year_all_of_them(){
		List<Company>companies = new ArrayList<Company>();
		companies.add(new Company("PIH","1347 Property Insurance Holdings, Inc.","6.0079","$36.21M","2014","Finance","Property-Casualty Insurers","http://www.nasdaq.com/symbol/pih"));
		companies.add(new Company("TWOU","2U, Inc.","37.41","$1.75B","2014","Technology","Computer Software: Prepackaged Software","http://www.nasdaq.com/symbol/twou"));
		companies.add(new Company("CAFD","8point3 Energy Partners LP","14.8","$1.05B","2014","Public Utilities","Electric Utilities: Central","http://www.nasdaq.com/symbol/cafd"));
		
		List<Company>found = Nasdaq.getOldestCompanies(1,companies);
		List<Company>expected = new ArrayList<Company>();
		expected.add(companies.get(0));
		expected.add(companies.get(1));
		expected.add(companies.get(2));
		assertEquals(expected, found);
	}
	
	@Test
	public void test_extract_two_oldest_companies_from_list_of_three_diff_year(){
		List<Company>companies = new ArrayList<Company>();
		companies.add(new Company("PIH","1347 Property Insurance Holdings, Inc.","6.0079","$36.21M","2014","Finance","Property-Casualty Insurers","http://www.nasdaq.com/symbol/pih"));
		companies.add(new Company("TWOU","2U, Inc.","37.41","$1.75B","2013","Technology","Computer Software: Prepackaged Software","http://www.nasdaq.com/symbol/twou"));
		companies.add(new Company("CAFD","8point3 Energy Partners LP","14.8","$1.05B","2012","Public Utilities","Electric Utilities: Central","http://www.nasdaq.com/symbol/cafd"));
		
		List<Company>found = Nasdaq.getOldestCompanies(2,companies);
		List<Company>expected = new ArrayList<Company>();
		expected.add(companies.get(2));
		expected.add(companies.get(1));
		assertEquals(expected, found);
	}
	
	@Test
	public void test_extract_two_oldest_companies_from_list_of_three_same_year_two_of_them(){
		List<Company>companies = new ArrayList<Company>();
		companies.add(new Company("PIH","1347 Property Insurance Holdings, Inc.","6.0079","$36.21M","2014","Finance","Property-Casualty Insurers","http://www.nasdaq.com/symbol/pih"));
		companies.add(new Company("TWOU","2U, Inc.","37.41","$1.75B","2014","Technology","Computer Software: Prepackaged Software","http://www.nasdaq.com/symbol/twou"));
		companies.add(new Company("CAFD","8point3 Energy Partners LP","14.8","$1.05B","2015","Public Utilities","Electric Utilities: Central","http://www.nasdaq.com/symbol/cafd"));
		
		List<Company>found = Nasdaq.getOldestCompanies(2,companies);
		List<Company>expected = new ArrayList<Company>();
		expected.add(companies.get(1));
		expected.add(companies.get(2));
		assertEquals(expected, found);
	}
	
	@Test
	public void test_extract_two_oldest_companies_from_list_of_three_same_year_all_of_them(){
		List<Company>companies = new ArrayList<Company>();
		companies.add(new Company("PIH","1347 Property Insurance Holdings, Inc.","6.0079","$36.21M","2014","Finance","Property-Casualty Insurers","http://www.nasdaq.com/symbol/pih"));
		companies.add(new Company("TWOU","2U, Inc.","37.41","$1.75B","2014","Technology","Computer Software: Prepackaged Software","http://www.nasdaq.com/symbol/twou"));
		companies.add(new Company("CAFD","8point3 Energy Partners LP","14.8","$1.05B","2014","Public Utilities","Electric Utilities: Central","http://www.nasdaq.com/symbol/cafd"));
		
		List<Company>found = Nasdaq.getOldestCompanies(2,companies);
		List<Company>expected = new ArrayList<Company>();
		expected.add(companies.get(0));
		expected.add(companies.get(1));
		expected.add(companies.get(2));
		assertEquals(expected, found);
	}
	
	@Test
	public void test_ten_oldest_companies_from_empty_list(){
		List<Company>actual = Nasdaq
				.tenOldestCompaniesInSector("Technology", "src/test/empty_file.csv");
		List<Company>expected = new ArrayList<Company>();
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_ten_oldest_companies_from_empty_list_with_header_only(){
		List<Company>actual = Nasdaq
				.tenOldestCompaniesInSector("Technology", "src/test/header_only.csv");
		List<Company>expected = new ArrayList<Company>();
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_ten_oldest_companies_from_list_of_three_companies(){
		List<Company>actual = Nasdaq
				.tenOldestCompaniesInSector("Finance", "src/test/short_list.csv");
		List<Company>expected = new ArrayList<Company>();
		expected.add(new Company("PIH","1347 Property Insurance Holdings, Inc.","6.0079","$36.21M","2014","Finance","Property-Casualty Insurers","http://www.nasdaq.com/symbol/pih"));
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_ten_oldest_companies_from_sector_companyList(){
		List<Company>actual = Nasdaq
				.tenOldestCompaniesInSector("Finance", "src/test/companylist.csv");
		List<Company>expected = new ArrayList<Company>();
		expected.add(new Company("STFC","State Auto Financial Corporation","24.12","$1.01B","1991","Finance","Property-Casualty Insurers","http://www.nasdaq.com/symbol/stfc"));	
		expected.add(new Company("WRLD","World Acceptance Corporation","48.95","$430.29M","1991","Finance","Finance: Consumer Services","http://www.nasdaq.com/symbol/wrld"));	
		expected.add(new Company("GSBC","Great Southern Bancorp, Inc.","41.79","$581.36M","1989","Finance","Major Banks","http://www.nasdaq.com/symbol/gsbc"));
		expected.add(new Company("CNBKA","Century Bancorp, Inc.","45.425","$252.92M","1987","Finance","Major Banks","http://www.nasdaq.com/symbol/cnbka"));
		expected.add(new Company("FBNC","First Bancorp","20.25","$406.78M","1987","Finance","	Major Banks","http://www.nasdaq.com/symbol/fbnc"));	
		expected.add(new Company("DGICB","Donegal Group, Inc.","18.5","$492.04M","1986","Finance","Property-Casualty Insurers","http://www.nasdaq.com/symbol/dgicb"));
		expected.add(new Company("TROW","T. Rowe Price Group, Inc.","65.93","$16.39B","1986","Finance","Investment Bankers/Brokers/Service","http://www.nasdaq.com/symbol/trow"));	
		expected.add(new Company("NAVG","The Navigators Group, Inc.","96.9","$1.41B","1986","Finance","Property-Casualty Insurers","http://www.nasdaq.com/symbol/navg"));
		expected.add(new Company("VALU","Value Line, Inc.","16","$155.55M","1983","Finance","Investment Managers","http://www.nasdaq.com/symbol/valu"));
		expected.add(new Company("EMCI","EMC Insurance Group Inc.","28.08","$591.3M","1982","Finance","Property-Casualty Insurers","http://www.nasdaq.com/symbol/emci"));
		expected.add(new Company("SEIC","SEI Investments Company","46.11","$7.44B","1981","Finance","Investment Bankers/Brokers/Service","http://www.nasdaq.com/symbol/seic"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_ten_oldest_companies_from_each_sector_companyList(){
		List<List<Company>>oldestCompanies = 
				Nasdaq.tenOldestCompaniesBySector("src/test/companylist.csv");
		for (List<Company> sector: oldestCompanies){
			logger.debug(sector.get(0).getSector());
			for (Company company : sector){
				logger.trace(company.getSymbol() + " "
						+ company.getSector() + " "
						+ company.getIpo());
			}
			logger.debug("end of sector " + sector.get(0).getSector());
		}
	}
	
}
