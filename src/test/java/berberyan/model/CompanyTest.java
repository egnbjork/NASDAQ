package berberyan.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class CompanyTest {

	private Company company;

	@Before
	public void setUp() {
		company = new Company.CompanyBuilder().setSymbol("PIH")
				.setName("1347 Property Insurance Holdings, Inc.")
				.setLastSale("6.0079")
				.setMarketCap("$36.21M")
				.setIpo("2014")
				.setSector("Finance")
				.setIndustry("Property-Casualty Insurers")
				.setSummaryQuote("http://www.nasdaq.com/symbol/pih")
				.build();	
	}

	@Test
	public void test_company_constructor_symbol(){
		String symbol = company.getSymbol();
		assertEquals(symbol, "PIH");
	}

	@Test
	public void test_company_constructor_name(){
		String name = company.getName();
		assertEquals(name, "1347 Property Insurance Holdings, Inc.");
	}

	@Test
	public void test_company_constructor_sector(){
		String sector = company.getSector();
		assertEquals("Finance", sector);
	}

	@Test
	public void test_company_constructor_industry(){
		String industry = company.getIndustry();
		assertEquals("Property-Casualty Insurers", industry);
	}

	@Test
	public void test_market_cap_millions(){
		company = new Company.CompanyBuilder().setSymbol("PIH")
				.setName("1347 Property Insurance Holdings, Inc.")
				.setLastSale("6.0079")
				.setMarketCap("$1M")
				.setIpo("2014")
				.setSector("Finance")
				.setIndustry("Property-Casualty Insurers")
				.setSummaryQuote("http://www.nasdaq.com/symbol/pih")
				.build();
		BigDecimal marketCap = company.getMarketCap().get();
		assertEquals(BigDecimal.valueOf(1000000), marketCap);
	}

	@Test
	public void test_market_cap_billions(){
		company = new Company.CompanyBuilder().setSymbol("PIH")
				.setName("1347 Property Insurance Holdings, Inc.")
				.setLastSale("6.0079")
				.setMarketCap("$1B")
				.setIpo("2014")
				.setSector("Finance")
				.setIndustry("Property-Casualty Insurers")
				.setSummaryQuote("http://www.nasdaq.com/symbol/pih")
				.build();
		BigDecimal marketCap = company.getMarketCap().get();
		assertEquals(BigDecimal.valueOf(1000000000), marketCap);
	}

	@Test
	public void test_market_cap_not_available(){
		company = new Company.CompanyBuilder().setSymbol("PIH")
				.setName("1347 Property Insurance Holdings, Inc.")
				.setLastSale("6.0079")
				.setMarketCap("n/a")
				.setIpo("2014")
				.setSector("Finance")
				.setIndustry("Property-Casualty Insurers")
				.setSummaryQuote("http://www.nasdaq.com/symbol/pih")
				.build();
		boolean marketCap = company.getMarketCap().isPresent();
		assertFalse(marketCap);
	}

	@Test
	public void test_market_cap_wrong(){
		company = new Company.CompanyBuilder().setSymbol("PIH")
				.setName("1347 Property Insurance Holdings, Inc.")
				.setLastSale("6.0079")
				.setMarketCap("36")
				.setIpo("2014")
				.setSector("Finance")
				.setIndustry("Property-Casualty Insurers")
				.setSummaryQuote("http://www.nasdaq.com/symbol/pih")
				.build();	
		boolean marketCap = company.getMarketCap().isPresent();
		assertFalse(marketCap);
	}

	@Test
	public void test_year(){
		int year = company.getIpo().get();
		assertEquals(2014, year);
	}

	@Test
	public void test_year_not_available(){
		company = new Company.CompanyBuilder().setSymbol("PIH")
				.setName("1347 Property Insurance Holdings, Inc.")
				.setLastSale("6.0079")
				.setMarketCap("36")
				.setIpo("n/a")
				.setSector("Finance")
				.setIndustry("Property-Casualty Insurers")
				.setSummaryQuote("http://www.nasdaq.com/symbol/pih")
				.build();	
		boolean year = company.getIpo().isPresent();
		assertFalse(year);
	}

	@Test
	public void test_year_wrong_format(){
		company = new Company.CompanyBuilder().setSymbol("PIH")
				.setName("1347 Property Insurance Holdings, Inc.")
				.setLastSale("6.0079")
				.setMarketCap("36")
				.setIpo("false")
				.setSector("Finance")
				.setIndustry("Property-Casualty Insurers")
				.setSummaryQuote("http://www.nasdaq.com/symbol/pih")
				.build();	
		boolean year = company.getIpo().isPresent();
		assertFalse(year);
	}
}
