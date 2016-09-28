package berberyan;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class ApacheParseCSVTest 
{
	private static final Logger logger = LogManager.getLogger(ApacheParseCSV.class); 

	@Test
	public void test_no_file_exists(){
		logger.trace("no file exists test");
		assertEquals(null, ApacheParseCSV.parseFile("wrong filename"));
	}

	@Test
	public void test_empty_file(){
		logger.trace("empty file test");
		assertEquals(new ArrayList<Company>(), ApacheParseCSV.parseFile("src/test/empty_file.csv"));
	}

	@Test
	public void test_header_only_file(){
		logger.trace("file with header only test");
		assertEquals(new ArrayList<Company>(), ApacheParseCSV.parseFile("src/test/header_only.csv"));
	}

	@Test
	public void test_big_file_processing(){
		logger.trace("big file processing test");
		ApacheParseCSV.parseFile("src/test/companylist.csv");		
	}
	
	@Test
	public void test_symbol_parsing(){
		logger.trace("symbol parsing test");
		String parsed = ApacheParseCSV.parseFile("src/test/short_list.csv").get(0).getSymbol();
		String symbol = "PIH";
		assertEquals(symbol, parsed);
	}

	@Test
	public void test_name_parsing(){
		logger.trace("name parsing test");
		String parsed = ApacheParseCSV.parseFile("src/test/short_list.csv").get(0).getName();
		String name = "1347 Property Insurance Holdings, Inc.";
		assertEquals(name, parsed);
	}

	@Test
	public void test_industry_parsing(){
		logger.trace("industry parsing test");
		String parsed = ApacheParseCSV.parseFile("src/test/short_list.csv").get(0).getIndustry();
		String industry = "Property-Casualty Insurers";
		assertEquals(industry, parsed);
	}

	@Test
	public void test_sector_parsing(){
		logger.trace("sector parsing test");
		String parsed = ApacheParseCSV.parseFile("src/test/short_list.csv").get(0).getSector();
		String sector = "Finance";
		assertEquals(sector, parsed);
	}

	@Test
	public void test_year_parsing(){
		logger.trace("ipo year parsing test");
		Integer parsed = ApacheParseCSV.parseFile("src/test/short_list.csv").get(0).getIpo();
		Integer year = 2014;
		assertEquals(year, parsed);
	}

	@Test
	public void test_year_not_available_parsing(){
		logger.trace("ipo unknown parsing test");
		Integer parsed = ApacheParseCSV.parseFile("src/test/short_list.csv").get(1).getIpo();
		Integer year = -1;
		assertEquals(year, parsed);
	}
	
	@Test
	public void test_marketCap_parsing_millions(){
		logger.trace("market cap parsing test");
		BigInteger parsed = ApacheParseCSV.parseFile("src/test/short_list.csv").get(0).getMarketCap();
		BigInteger marketCap = BigInteger.valueOf(36210000);
		assertEquals(marketCap, parsed);
	}
	
	@Test
	public void test_marketCap_parsing_billions(){
		logger.trace("market cap parsing test");
		BigInteger parsed = ApacheParseCSV.parseFile("src/test/short_list.csv").get(2).getMarketCap();
		BigInteger marketCap = BigInteger.valueOf(1750000000);
		assertEquals(marketCap, parsed);
	}

	@Test
	public void test_marketCap_not_available_parsing(){
		logger.trace("market cap n\\a parsing test");
		BigInteger parsed = ApacheParseCSV.parseFile("src/test/short_list.csv").get(1).getMarketCap();
		BigInteger marketCap = BigInteger.valueOf(-1);
		assertEquals(marketCap, parsed);
	}
}
