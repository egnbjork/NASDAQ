package berberyan.model.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import berberyan.model.Company;

public class NasdaqTest {

	private Company company;

	@Before
	public void setUp() {
		company = new Nasdaq.CompanyBuilder().setSymbol("PIH")
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
		company = new Nasdaq.CompanyBuilder().setSymbol("PIH")
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
		company = new Nasdaq.CompanyBuilder().setSymbol("PIH")
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
		company = new Nasdaq.CompanyBuilder().setSymbol("PIH")
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
		company = new Nasdaq.CompanyBuilder().setSymbol("PIH")
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
		company = new Nasdaq.CompanyBuilder().setSymbol("PIH")
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
		company = new Nasdaq.CompanyBuilder().setSymbol("PIH")
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

	@Test
	public void lastSaleAsString_NullTest() {
		company = new Nasdaq.CompanyBuilder().setSymbol("PIH")
				.setName("1347 Property Insurance Holdings, Inc.")
				.setLastSale("n/a")
				.setMarketCap("36")
				.setIpo("false")
				.setSector("Finance")
				.setIndustry("Property-Casualty Insurers")
				.setSummaryQuote("http://www.nasdaq.com/symbol/pih")
				.build();	

		String expected = "n/a";
		String actual = company.lastSaleAsString();

		assertEquals(expected, actual);
	}

	@Test
	public void lastSaleAsString_NotNullTest() {
		company = new Nasdaq.CompanyBuilder().setSymbol("PIH")
				.setName("1347 Property Insurance Holdings, Inc.")
				.setLastSale("6.0079")
				.setMarketCap("36")
				.setIpo("false")
				.setSector("Finance")
				.setIndustry("Property-Casualty Insurers")
				.setSummaryQuote("http://www.nasdaq.com/symbol/pih")
				.build();	

		String expected = "6.0079";
		String actual = company.lastSaleAsString();

		assertEquals(expected, actual);
	}

	@Test
	public void ipoAsString_NullTest() {
		company = new Nasdaq.CompanyBuilder().setSymbol("PIH")
				.setName("1347 Property Insurance Holdings, Inc.")
				.setLastSale("6.0079")
				.setMarketCap("36")
				.setIpo("false")
				.setSector("Finance")
				.setIndustry("Property-Casualty Insurers")
				.setSummaryQuote("http://www.nasdaq.com/symbol/pih")
				.build();	

		String expected = "n/a";
		String actual = company.ipoAsString();

		assertEquals(expected, actual);
	}

	@Test
	public void ipoAsString_NotNullTest() {
		company = new Nasdaq.CompanyBuilder().setSymbol("PIH")
				.setName("1347 Property Insurance Holdings, Inc.")
				.setLastSale("6.0079")
				.setMarketCap("36")
				.setIpo("1996")
				.setSector("Finance")
				.setIndustry("Property-Casualty Insurers")
				.setSummaryQuote("http://www.nasdaq.com/symbol/pih")
				.build();	

		String expected = "1996";
		String actual = company.ipoAsString();

		assertEquals(expected, actual);
	}

	@Test
	public void marketCapAsString_NullTest() {
		company = new Nasdaq.CompanyBuilder().setSymbol("PIH")
				.setName("1347 Property Insurance Holdings, Inc.")
				.setLastSale("6.0079")
				.setMarketCap("n/a")
				.setIpo("false")
				.setSector("Finance")
				.setIndustry("Property-Casualty Insurers")
				.setSummaryQuote("http://www.nasdaq.com/symbol/pih")
				.build();	

		String expected = "n/a";
		String actual = company.marketCapAsString();

		assertEquals(expected, actual);
	}

	@Test
	public void marketCapAsString_WrongInput() {
		company = new Nasdaq.CompanyBuilder().setSymbol("PIH")
				.setName("1347 Property Insurance Holdings, Inc.")
				.setLastSale("6.0079")
				.setMarketCap("36")
				.setIpo("false")
				.setSector("Finance")
				.setIndustry("Property-Casualty Insurers")
				.setSummaryQuote("http://www.nasdaq.com/symbol/pih")
				.build();	

		String expected = "n/a";
		String actual = company.marketCapAsString();

		assertEquals(expected, actual);
	}	

	@Test
	public void marketCapAsString_MillionTest() {
		company = new Nasdaq.CompanyBuilder().setSymbol("PIH")
				.setName("1347 Property Insurance Holdings, Inc.")
				.setLastSale("6.0079")
				.setMarketCap("$36M")
				.setIpo("false")
				.setSector("Finance")
				.setIndustry("Property-Casualty Insurers")
				.setSummaryQuote("http://www.nasdaq.com/symbol/pih")
				.build();	

		String expected = "$36M";
		String actual = company.marketCapAsString();

		assertEquals(expected, actual);
	}

	@Test
	public void marketCapAsString_BillionTest() {
		company = new Nasdaq.CompanyBuilder().setSymbol("PIH")
				.setName("1347 Property Insurance Holdings, Inc.")
				.setLastSale("6.0079")
				.setMarketCap("$36B")
				.setIpo("false")
				.setSector("Finance")
				.setIndustry("Property-Casualty Insurers")
				.setSummaryQuote("http://www.nasdaq.com/symbol/pih")
				.build();	

		String expected = "$36B";
		String actual = company.marketCapAsString();

		assertEquals(expected, actual);
	}

	@Test
	public void getSharesAmount_test() {
		BigDecimal expected = BigDecimal.valueOf(6_027_064);
		BigDecimal actual = null;
		if(company.getSharesAmount().isPresent()) {
			actual = company.getSharesAmount().get();
		}
		assertEquals(expected, actual);
	}
}
