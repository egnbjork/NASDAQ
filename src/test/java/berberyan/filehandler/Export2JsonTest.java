package berberyan.filehandler;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import berberyan.engine.Company;
import berberyan.filehandler.Export2Json;

public class Export2JsonTest {
	private static final Logger logger = LogManager.getLogger(Export2JsonTest.class);

	private List<Company> passedList;
	private String filePath;
	File export;
	
	@Before
	public void SetUp(){
		passedList = new ArrayList<Company>();
		passedList.add(new Company.CompanyBuilder().setSymbol("PIH")
				.setName("1347 Property Insurance Holdings, Inc.")
				.setLastSale("6.0079")
				.setMarketCap("$36.21M")
				.setIpo("2014")
				.setSector("Finance")
				.setIndustry("Property-Casualty Insurers")
				.setSummaryQuote("http://www.nasdaq.com/symbol/pih")
				.build());
		filePath = "src/test/JacksonExportTest.json";
		tearDown();
	}

	@After
	public void tearDown(){
		export = new File(filePath);
		if(export.exists()){
			logger.trace("file was deleted");
			export.delete();
		}
	}
	
	@Test
	public void test_jackson_json_file_created(){
		Export2Json.company2Json(passedList, filePath);
		assertTrue(export.exists());
		
	}
	
	@Test
	public void test_jackson_json_empty_file_export(){
		List <Company> emptyList = new ArrayList<Company>();
		Export2Json.company2Json(emptyList, filePath);
		boolean isEmpty = export.length() == 3 ? true : false;
		assertTrue(isEmpty);
	}
	
	@Test
	@Ignore
	public void test_jackson_json_file_export() throws IOException{
		Export2Json.company2Json(passedList, filePath);
		File example = new File("src/test/json_company_verified.json");
		boolean compareFiles = FileUtils.contentEquals(export,example);
		assertTrue(compareFiles);
	}
	
}
