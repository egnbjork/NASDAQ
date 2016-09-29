package berberyan;

import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

public class Export2JSONTest {
	private static final Logger logger = LogManager.getLogger(Export2JSONTest.class);

	private List<Company> companyList;
	private String fileName;

	@Before
	public void SetUp(){
		companyList = new ArrayList<Company>();
		companyList.add(new Company("PIH", //symbol
				"1347 Property Insurance Holdings, Inc.", //name
				"Finance", //sector
				"Property-Casualty Insurers", //Industry
				"$36.21M", //market cap
				"2014")); //ipo year
		fileName = "JacksonExportTest";

	}

	@Test
	public void test_jackson_json_file_export(){
		Export2JSON.company2JSON(ApacheParseCSV.parseFile("src/test/short_list.csv"), fileName);
		assertFalse(true);
	}
}
