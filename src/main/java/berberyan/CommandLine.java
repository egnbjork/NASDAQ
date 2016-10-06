package berberyan;

import java.io.File;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandLine {
	private static Logger logger = LogManager.getLogger(Nasdaq.class);
	
	public static void main(String[] args) {
		new Cli(args).parse();
	}

	public static void fileProcessing(String[] args){
		if (args.length < 2){
			logger.error("wrong arguments");
			return;
		}
		String csv = args[0];
		String json = args[1];
		
		csvJsonConvert(csv,json);
	}
	
	private static void csvJsonConvert(String importPath, String exportPath){
		//check if file exists
		File importCsv = new File(importPath);
		if(!importCsv.exists()){
			logger.error("file " + importPath + " does not exist");
			return;
		}
		
		File exportJson = new File(exportPath);
		if(exportJson.exists()){
			logger.error("file " + exportPath + " already exists\nchoose other filename");
			return;
		}
		List<Company> companies = ApacheParseCsv.parseFile(importPath);
		Export2Json.company2Json(companies, exportPath);
		logger.info(importPath + " converted to " + exportPath);
	}
	
}
