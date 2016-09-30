package berberyan;

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
import org.junit.Test;

public class Export2JsonTest {
	private static final Logger logger = LogManager.getLogger(Export2JsonTest.class);

	private List<Company> passedList;
	private String fileName;
	private String filePath;
	File export;
	
	@Before
	public void SetUp(){
		passedList = new ArrayList<Company>();
		passedList.add(new Company("PIH", //symbol
				"1347 Property Insurance Holdings, Inc.", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"$36.21M", //market cap
				"2014")); //ipo year
		fileName = "JacksonExportTest.json";
		filePath = "src/test/";
		tearDown();
	}

	@After
	public void tearDown(){
		export = new File(filePath, fileName);
		if(export.exists()){
			logger.trace("file was deleted");
			export.delete();
		}
	}
	
	@Test
	public void test_jackson_json_file_created(){
		Export2Json.company2Json(passedList, fileName, filePath);
		assertTrue(export.exists());
		
	}
	
	@Test
	public void test_jackson_json_file_export() throws IOException{
		Export2Json.company2Json(passedList, fileName, filePath);
		File example = new File(filePath,"json_company_verified.json");
		boolean compareFiles = FileUtils.contentEquals(export,example);
		assertTrue(compareFiles);
	}
	
	@Test
	public void test_jackson_json_empty_file_export(){
		List <Company> emptyList = new ArrayList<Company>();
		Export2Json.company2Json(emptyList, fileName, filePath);
		boolean isEmpty = export.length() == 3 ? true : false;
		logger.trace(export.length());
		assertTrue(isEmpty);
	}
}
