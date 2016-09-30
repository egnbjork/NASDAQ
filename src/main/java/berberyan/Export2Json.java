package berberyan;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Export2Json {

	private static Logger logger = LogManager.getLogger(Export2Json.class);
	
	public static Integer company2Json(List<Company> passedList, String fileName,
			String filePath){
		ObjectMapper mapper = new ObjectMapper();
		try{
			logger.debug("file will be saved to " +filePath + fileName);
			File export = new File(filePath, fileName);
			//file exists check
			if(export.exists()){
				logger.warn("file already exists in the file system");
			}
			else{
				export.createNewFile();
				logger.info("file " + fileName + "created in the " + filePath);
			}
			
			mapper.writerWithDefaultPrettyPrinter().writeValue(export, passedList);
		
			logger.info(passedList.size() + " items stored");
			logger.debug(mapper.writeValueAsString(passedList));
			logger.trace("JSON file saved to " + filePath +fileName);
			
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
