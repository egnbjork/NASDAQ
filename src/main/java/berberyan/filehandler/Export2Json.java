package berberyan.filehandler;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import berberyan.engine.Company;

public class Export2Json {

	private static Logger logger = LogManager.getLogger(Export2Json.class);

	
	
	private static File createFile(String filePath){
		try{
			logger.debug("file will be saved to " +filePath);
			File export = new File(filePath);
			//file exists check
			if(export.exists()){
				logger.warn("file already exists in the file system");
			}
			else{
				export.createNewFile();
				logger.info("file " + filePath + " created");
			}
			return export;
		} catch (IOException e) {
			logger.error("Cannot create JSON file " + e);
		}
		return null;
	}

	public static void company2Json(List<Company> passedList, String filePath){
		ObjectMapper mapper = new ObjectMapper();
		File export = createFile(filePath);
		try{
			
			mapper.writerWithDefaultPrettyPrinter().writeValue(export, passedList);

			logger.info(passedList.size() + " items stored");
			logger.debug(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(passedList));
			logger.trace("JSON file saved to " + filePath);
		}catch (JsonGenerationException e) {
			logger.error("Cannot write JSON file " + e);
			return;
		} catch (JsonMappingException e) {
			logger.error("Cannot write JSON file " + e);
			return;
		} catch (IOException e) {
			logger.error("Cannot write JSON file " + e);
			return;
		}
	}
}
