package berberyan;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Export2JSON {

	private static Logger logger = LogManager.getLogger(Export2JSON.class);

	public static Integer company2JSON(List<Company> passedList, String filename){
		ObjectMapper mapper = new ObjectMapper();
		try{
			String filePath = "src/test/";
			logger.debug("file will be saved to " +filePath);

			File export = new File("src/test/", filename + ".json");

			if(export.exists()){
				logger.warn("file already exists in the file system");
			}
			else{
				export.createNewFile();
				logger.info("file " + filename + ".json created in the " + filePath);
			}
			
			mapper.writerWithDefaultPrettyPrinter().writeValue(export, passedList);
			logger.info(passedList.size() + " items stored");
			
			logger.debug(mapper.writeValueAsString(passedList));

			logger.trace("JSON file saved to " + filePath);
			return 0;
		}catch (JsonGenerationException e) {
			logger.error("Cannot write JSON file " + e);
			return 1;
		} catch (JsonMappingException e) {
			logger.error("Cannot write JSON file " + e);
			return 2;
		} catch (IOException e) {
			logger.error("Cannot write JSON file " + e);
			return 3;
		}
	}
}
