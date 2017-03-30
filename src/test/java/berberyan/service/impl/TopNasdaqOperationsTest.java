package berberyan.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import berberyan.entity.Company;
import berberyan.entity.impl.Nasdaq;

public class TopNasdaqOperationsTest {

	List<Company> companies;
	Map<String, List<Company>> twoOldest;
	TopNasdaqOperations operations;

	@Before
	public void setUp() {
		Company c1 = new Nasdaq.CompanyBuilder().setSymbol("YOUNG")
				.setName("1347 Property Insurance Holdings, Inc.")
				.setLastSale("6.0079")
				.setMarketCap("$26.21M")
				.setIpo("2014")
				.setSector("Finance")
				.setIndustry("Property-Casualty Insurers")
				.setSummaryQuote("http://www.nasdaq.com/symbol/pih")
				.build();

		Company c2 = new Nasdaq.CompanyBuilder().setSymbol("OLD")
				.setName("1347 Property Insurance Holdings, Inc.")
				.setLastSale("6.0079")
				.setMarketCap(".21M")
				.setIpo("1999")
				.setSector("Finance")
				.setIndustry("Property-Casualty Insurers")
				.setSummaryQuote("http://www.nasdaq.com/symbol/pih")
				.build();	

		Company c3 = new Nasdaq.CompanyBuilder().setSymbol("MED")
				.setName("1347 Property Insurance Holdings, Inc.")
				.setLastSale("1.0079")
				.setMarketCap("$36.21M")
				.setIpo("2001")
				.setSector("Finance")
				.setIndustry("Property-Casualty Insurers")
				.setSummaryQuote("http://www.nasdaq.com/symbol/pih")
				.build();	

		Company c4 = new Nasdaq.CompanyBuilder().setSymbol("NOYEAR")
				.setName("1347 Property Insurance Holdings, Inc.")
				.setLastSale("0.0079")
				.setMarketCap("$16.21M")
				.setIpo("n/a")
				.setSector("Finance")
				.setIndustry("Marketing")
				.setSummaryQuote("http://www.nasdaq.com/symbol/pih")
				.build();	

		companies = Arrays.asList(c1, c2, c3, c4);
		twoOldest = new HashMap<>();
		twoOldest.put("Finance", Arrays.asList(companies.get(1), companies.get(2)));

		operations = new TopNasdaqOperations();
	}

	@Test
	public void getOldestCompanies_count() {
		int expected = 2;
		int actual = operations.getOldest(companies, 2).get("Finance").size();

		assertEquals(expected, actual);
	}

	@Test
	public void getOldestCompanies_sectorCountTest() {
		int expected = 1;
		int actual = twoOldest.size();

		assertEquals(expected, actual);
	}

	@Test
	public void getOldestCompanies_sectorNameTest() {
		assertNotNull(twoOldest.containsKey("Finance"));
	}

	@Test
	public void getOldestCompanies_test() {
		List<Company> list = Arrays.asList(companies.get(1), companies.get(2));
		Map<String, List<Company>> expected = new HashMap<>();
		expected.put("Finance", list);

		assertEquals(expected, twoOldest);
	}

	@Test
	public void getMostExpensive_test() {
		List<Company> list = Arrays.asList(companies.get(2), companies.get(0));
		Map<String, List<Company>> expected = new HashMap<>();
		expected.put("Finance", list);
		Map<String, List<Company>> actual = operations.getMostExpensive(list, 2);

		assertEquals(expected, actual);
	}

	@Test
	public void getBiggestShareAmount_test() {
		List<Company> list = Arrays.asList(companies.get(2), companies.get(3));
		Map<String, List<Company>> expected = new HashMap<>();
		expected.put("Finance", list);
		Map<String, List<Company>> actual = operations.getMostExpensive(list, 2);

		assertEquals(expected, actual);
	}
	
	@Test
	public void countSectors_test() {
		int expected = 1;
		int actual = operations.countSectors(companies);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void howManyCompaniesInSector_test() {
		Map<String, Integer> expected = new HashMap<>();
		expected.put("Finance", Integer.valueOf(4));
		
		Map<String, Integer> actual = operations.countCompaniesEachSector(companies);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void countIndustries_test() {
		int expected = 2;
		int actual = operations.countIndustries(companies);
		
		assertEquals(expected, actual);
	}
}
