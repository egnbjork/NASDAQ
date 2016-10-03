package berberyan;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

public class CompanyTest {

	@Test
	public void test_company_constructor_symbol(){
		Company company = new Company("PIH","1347 Property Insurance Holdings, Inc.","6.0079","$36.21M","2014","Finance","Property-Casualty Insurers","http://www.nasdaq.com/symbol/pih");
		String symbol = company.getSymbol();
		assertEquals(symbol, "PIH");
	}
	
	@Test
	public void test_company_constructor_name(){
		Company company = new Company("PIH","1347 Property Insurance Holdings, Inc.","6.0079","$36.21M","2014","Finance","Property-Casualty Insurers","http://www.nasdaq.com/symbol/pih");
		String name = company.getName();
		assertEquals(name, "1347 Property Insurance Holdings, Inc.");
	}
	
	@Test
	public void test_company_constructor_sector(){
		Company company = new Company("PIH","1347 Property Insurance Holdings, Inc.","6.0079","$36.21M","2014","Finance","Property-Casualty Insurers","http://www.nasdaq.com/symbol/pih");
		String sector = company.getSector();
		assertEquals("Finance", sector);
	}
	
	@Test
	public void test_company_constructor_industry(){
		Company company = new Company("PIH","1347 Property Insurance Holdings, Inc.","6.0079","$36.21M","2014","Finance","Property-Casualty Insurers","http://www.nasdaq.com/symbol/pih");
		String industry = company.getIndustry();
		assertEquals("Property-Casualty Insurers", industry);
	}
	
	@Test
	public void test_market_cap_millions(){
		Company company = new Company("PIH","1347 Property Insurance Holdings, Inc.","6.0079","$1M","2014","Finance","Property-Casualty Insurers","http://www.nasdaq.com/symbol/pih");
		BigInteger marketCap = company.getMarketCap();
		assertEquals(BigInteger.valueOf(1000000), marketCap);
	}
	
	@Test
	public void test_market_cap_billions(){
		Company company = new Company("PIH","1347 Property Insurance Holdings, Inc.","6.0079","$1B","2014","Finance","Property-Casualty Insurers","http://www.nasdaq.com/symbol/pih");
		BigInteger marketCap = company.getMarketCap();
		assertEquals(BigInteger.valueOf(1000000000), marketCap);
	}
	
	@Test
	public void test_market_cap_not_available(){
		Company company = new Company("PIH","1347 Property Insurance Holdings, Inc.","6.0079","n/a","2014","Finance","Property-Casualty Insurers","http://www.nasdaq.com/symbol/pih");
		BigInteger marketCap = company.getMarketCap();
		assertEquals(BigInteger.valueOf(-1), marketCap);
	}
	
	@Test
	public void test_market_cap_wrong(){
		Company company = new Company("PIH","1347 Property Insurance Holdings, Inc.","6.0079","32","2014","Finance","Property-Casualty Insurers","http://www.nasdaq.com/symbol/pih");
		BigInteger marketCap = company.getMarketCap();
		assertEquals(BigInteger.valueOf(0), marketCap);
	}
	
	@Test
	public void test_year(){
		Company company = new Company("PIH","1347 Property Insurance Holdings, Inc.","6.0079","$36.21M","2014","Finance","Property-Casualty Insurers","http://www.nasdaq.com/symbol/pih");
		int year = company.getIpo();
		assertEquals(2014, year);
	}
	
	@Test
	public void test_year_not_available(){
		Company company = new Company("PIH","1347 Property Insurance Holdings, Inc.","6.0079","$36.21M","n/a","Finance","Property-Casualty Insurers","http://www.nasdaq.com/symbol/pih");
		int year = company.getIpo();
		assertEquals(-1, year);
	}
	
	@Test
	public void test_year_wrong_format(){
		Company company = new Company("PIH","1347 Property Insurance Holdings, Inc.","6.0079","$36.21M","e","Finance","Property-Casualty Insurers","http://www.nasdaq.com/symbol/pih");
		int year = company.getIpo();
		assertEquals(-1, year);
	}
}
