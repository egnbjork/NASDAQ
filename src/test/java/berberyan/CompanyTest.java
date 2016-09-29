package berberyan;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

public class CompanyTest {

	@Test
	public void test_company_constructor_symbol(){
		Company company = new Company("PIH", //symbol
				"1347 Property Insurance Holdings, Inc.", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"$36.21M", //market cap
				"2014"); //ipo year
		String symbol = company.getSymbol();
		assertEquals(symbol, "PIH");
	}
	
	@Test
	public void test_company_constructor_name(){
		Company company = new Company("PIH", //symbol
				"1347 Property Insurance Holdings, Inc.", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"$36.21M", //market cap
				"2014"); //ipo year
		String name = company.getName();
		assertEquals(name, "1347 Property Insurance Holdings, Inc.");
	}
	
	@Test
	public void test_company_constructor_sector(){
		Company company = new Company("PIH", //symbol
				"1347 Property Insurance Holdings, Inc.", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"$36.21M", //market cap
				"2014"); //ipo year
		String sector = company.getSector();
		assertEquals(sector, "Finance");
	}
	
	@Test
	public void test_company_constructor_industry(){
		Company company = new Company("PIH", //symbol
				"1347 Property Insurance Holdings, Inc.", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"$36.21M", //market cap
				"2014"); //ipo year
		String industry = company.getIndustry();
		assertEquals(industry, "Property-Casualty Insurers");
	}
	
	@Test
	public void test_market_cap_millions(){
		Company company = new Company("PIH", //symbol
				"1347 Property Insurance Holdings, Inc.", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"$1M", //market cap
				"2014"); //ipo year
		BigInteger marketCap = company.getMarketCap();
		assertEquals(marketCap, BigInteger.valueOf(1000000));
	}
	
	@Test
	public void test_market_cap_billions(){
		Company company = new Company("PIH", //symbol
				"1347 Property Insurance Holdings, Inc.", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"$1B", //market cap
				"2014"); //ipo year
		BigInteger marketCap = company.getMarketCap();
		assertEquals(marketCap, BigInteger.valueOf(1000000000));
	}
	
	@Test
	public void test_market_cap_not_available(){
		Company company = new Company("PIH", //symbol
				"1347 Property Insurance Holdings, Inc.", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"n/a", //market cap
				"2014"); //ipo year
		BigInteger marketCap = company.getMarketCap();
		assertEquals(marketCap, BigInteger.valueOf(-1));
	}
	
	@Test
	public void test_market_cap_plain(){
		Company company = new Company("PIH", //symbol
				"1347 Property Insurance Holdings, Inc.", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"21", //market cap
				"2014"); //ipo year
		BigInteger marketCap = company.getMarketCap();
		assertEquals(marketCap, BigInteger.valueOf(0));
	}
	
	@Test
	public void test_year(){
		Company company = new Company("PIH", //symbol
				"1347 Property Insurance Holdings, Inc.", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"21M", //market cap
				"2014"); //ipo year
		int year = company.getIpo();
		assertEquals(2014, year);
	}
	
	@Test
	public void test_year_not_available(){
		Company company = new Company("PIH", //symbol
				"1347 Property Insurance Holdings, Inc.", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"21M", //market cap
				"n/a"); //ipo year
		int year = company.getIpo();
		assertEquals(-1, year);
	}
	
	@Test
	public void test_year_wrong_format(){
		Company company = new Company("PIH", //symbol
				"1347 Property Insurance Holdings, Inc.", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"21M", //market cap
				"r"); //ipo year
		int year = company.getIpo();
		assertEquals(-1, year);
	}
}
