package berberyan;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class NasdaqTest {

	@Test
	public void test_extract_sectors_from_empty_list(){
		List<Company>empty = new ArrayList<Company>();
		List<String>processed = Nasdaq.extractSector(empty);
		assertEquals(new ArrayList<String>(), processed);
	}
	
	@Test
	public void test_extract_sectors_from_one_company(){
		List<Company>companies = new ArrayList<Company>();
		companies.add(new Company("PIH", //symbol
				"1347 Property Insurance Holdings, Inc.", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"$36.21M", //market cap
				"2014") //ipo year
		);
		List<String>processed = Nasdaq.extractSector(companies);
		List<String> expected = new ArrayList();
		expected.add("Finance");
		
		assertEquals(expected, processed);
	}
	
	@Test
	public void test_extract_sectors_from_two_company_different_sector(){
		List<Company>companies = new ArrayList<Company>();
		companies.add(new Company("PIH", //symbol
				"1347 Property Insurance Holdings, Inc.", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"$36.21M", //market cap
				"2014") //ipo year
		);
		companies.add(new Company("AAP", //symbol
				"1347 Property Insurance Holdings, Inc.", //name
				"Technology", //sector
				"Property-Casualty Insurers", //Industry
				"$36.21M", //market cap
				"2014") //ipo year
		);
		List<String>processed = Nasdaq.extractSector(companies);
		List<String>expected = new ArrayList();
		expected.add("Finance");
		expected.add("Technology");
		assertEquals(expected, processed);
	}
	
	@Test
	public void test_extract_sectors_from_two_companies_same_sector(){
		List<Company>companies = new ArrayList<Company>();
		companies.add(new Company("PIH", //symbol
				"1347 Property Insurance Holdings, Inc.", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"$36.21M", //market cap
				"2014") //ipo year
		);
		
		companies.add(new Company("AAP", //symbol
				"1347 Property Insurance Holdings, Inc.", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"$36.21M", //market cap
				"2014") //ipo year
		);
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
		companies.add(new Company("PIH", //symbol
				"1347 Property Insurance Holdings, Inc.", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"$36.21M", //market cap
				"2014") //ipo year
		);
		List<Company>found = Nasdaq.getOldestCompanies(1,companies);
		List<Company>expected = new ArrayList<Company>();
		expected.add(companies.get(0));
		assertEquals(expected, found);
	}
	
	@Test
	public void test_extract_oldest_company_from_list_of_two_diff_year(){
		List<Company>companies = new ArrayList<Company>();
		companies.add(new Company("PIH", //symbol
				"1347 Property Insurance Holdings, Inc.", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"$36.21M", //market cap
				"2014") //ipo year
		);
		companies.add(new Company("AAP", //symbol
				"Apple", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"$36.21M", //market cap
				"1980") //ipo year
		);
		List<Company>found = Nasdaq.getOldestCompanies(1,companies);
		List<Company>expected = new ArrayList<Company>();
		expected.add(companies.get(1));
		assertEquals(expected, found);
	}
	
	@Test
	public void test_extract_oldest_company_from_list_of_two_same_year(){
		List<Company>companies = new ArrayList<Company>();
		companies.add(new Company("PIH", //symbol
				"1347 Property Insurance Holdings, Inc.", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"$36.21M", //market cap
				"2014") //ipo year
		);
		companies.add(new Company("AAP", //symbol
				"Apple", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"$36.21M", //market cap
				"2014") //ipo year
		);
		List<Company>found = Nasdaq.getOldestCompanies(1,companies);
		List<Company>expected = new ArrayList<Company>();
		expected.add(companies.get(0));
		expected.add(companies.get(1));
		assertEquals(expected, found);
	}
	
	@Test
	public void test_extract_oldest_company_from_list_of_three_diff_year(){
		List<Company>companies = new ArrayList<Company>();
		companies.add(new Company("PIH", //symbol
				"1347 Property Insurance Holdings, Inc.", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"$36.21M", //market cap
				"2014") //ipo year
		);
		companies.add(new Company("AAP", //symbol
				"Apple", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"$36.21M", //market cap
				"2013") //ipo year
		);
		
		companies.add(new Company("GGL", //symbol
				"Google", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"$36.21M", //market cap
				"2012") //ipo year
		);
		List<Company>found = Nasdaq.getOldestCompanies(1,companies);
		List<Company>expected = new ArrayList<Company>();
		expected.add(companies.get(2));
		assertEquals(expected, found);
	}
	
	@Test
	public void test_extract_oldest_company_from_list_of_three_same_year_two_of_them(){
		List<Company>companies = new ArrayList<Company>();
		companies.add(new Company("PIH", //symbol
				"1347 Property Insurance Holdings, Inc.", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"$36.21M", //market cap
				"2014") //ipo year
		);
		companies.add(new Company("AAP", //symbol
				"Apple", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"$36.21M", //market cap
				"2012") //ipo year
		);
		
		companies.add(new Company("GGL", //symbol
				"Google", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"$36.21M", //market cap
				"2012") //ipo year
		);
		List<Company>found = Nasdaq.getOldestCompanies(1,companies);
		List<Company>expected = new ArrayList<Company>();
		expected.add(companies.get(1));
		expected.add(companies.get(2));
		assertEquals(expected, found);
	}
	
	@Test
	public void test_extract_oldest_company_from_list_of_three_same_year_all_of_them(){
		List<Company>companies = new ArrayList<Company>();
		companies.add(new Company("PIH", //symbol
				"1347 Property Insurance Holdings, Inc.", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"$36.21M", //market cap
				"2012") //ipo year
		);
		companies.add(new Company("AAP", //symbol
				"Apple", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"$36.21M", //market cap
				"2012") //ipo year
		);
		
		companies.add(new Company("GGL", //symbol
				"Google", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"$36.21M", //market cap
				"2012") //ipo year
		);
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
		companies.add(new Company("PIH", //symbol
				"1347 Property Insurance Holdings, Inc.", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"$36.21M", //market cap
				"2014") //ipo year
		);
		companies.add(new Company("AAP", //symbol
				"Apple", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"$36.21M", //market cap
				"2013") //ipo year
		);
		
		companies.add(new Company("GGL", //symbol
				"Google", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"$36.21M", //market cap
				"2012") //ipo year
		);
		List<Company>found = Nasdaq.getOldestCompanies(2,companies);
		List<Company>expected = new ArrayList<Company>();
		expected.add(companies.get(2));
		expected.add(companies.get(1));
		assertEquals(expected, found);
	}
	
	@Test
	public void test_extract_two_oldest_companies_from_list_of_three_same_year_two_of_them(){
		List<Company>companies = new ArrayList<Company>();
		companies.add(new Company("PIH", //symbol
				"1347 Property Insurance Holdings, Inc.", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"$36.21M", //market cap
				"2014") //ipo year
		);
		companies.add(new Company("AAP", //symbol
				"Apple", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"$36.21M", //market cap
				"2012") //ipo year
		);
		
		companies.add(new Company("GGL", //symbol
				"Google", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"$36.21M", //market cap
				"2012") //ipo year
		);
		List<Company>found = Nasdaq.getOldestCompanies(2,companies);
		List<Company>expected = new ArrayList<Company>();
		expected.add(companies.get(1));
		expected.add(companies.get(2));
		assertEquals(expected, found);
	}
	
	@Test
	public void test_extract_oldest_company_from_list_of_three_same_year_all_of_them(){
		List<Company>companies = new ArrayList<Company>();
		companies.add(new Company("PIH", //symbol
				"1347 Property Insurance Holdings, Inc.", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"$36.21M", //market cap
				"2012") //ipo year
		);
		companies.add(new Company("AAP", //symbol
				"Apple", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"$36.21M", //market cap
				"2012") //ipo year
		);
		
		companies.add(new Company("GGL", //symbol
				"Google", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"$36.21M", //market cap
				"2012") //ipo year
		);
		List<Company>found = Nasdaq.getOldestCompanies(2,companies);
		List<Company>expected = new ArrayList<Company>();
		expected.add(companies.get(0));
		expected.add(companies.get(1));
		expected.add(companies.get(2));
		assertEquals(expected, found);
	}
}
