package berberyan;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class ApacheCsvJacksonJsonTest {

	@Test
	public void test_short_list_convertion() throws IOException{
		String filePath = "src/test/";
		List<Company> shortList = ApacheParseCsv.parseFile("src/test/short_list.csv");
		Export2Json.company2Json(shortList, "src/test/json_short_list.json");
		File example = new File(filePath,"json_short_list_verified.json");
		File export = new File(filePath, "json_short_list.json");
		boolean compareFiles = FileUtils.contentEquals(example,export);
		assertTrue(compareFiles);
	}
}
