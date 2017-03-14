package berberyan;

import java.io.File;
import java.util.List;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandLineInterface {
	private static final Logger logger = LogManager.getLogger(CommandLineInterface.class); 
	private String[] args = null;
	private Options options = new Options();

	public CommandLineInterface(String[] args) {
		this.args = args;
		options.addOption("h", "help", false, "show help.");
		options.addOption("ci", true, "Count industries in each sector");
		options.addOption("o", true, "Ten oldest companies from each sector");
		options.addOption("e", true, "Ten most expencive companies from each sector");
		options.addOption("s", true, "Count companies in each sector (in percents)");
		options.addOption("v", true, "Ten companies with biggest volume");
	}
	public void parse() {
		CommandLineParser parser = new BasicParser();

		CommandLine cmd = null;

		try {

			cmd = parser.parse(options, args);

			if (cmd.hasOption("h")){
				help();
			}

			if (cmd.hasOption("v")) {
				logger.log(Level.INFO, "Ten companies with biggest volume");
				Nasdaq.tenBiggestShareAmount(cmd.getOptionValue("v"));
			} else if (cmd.hasOption("ci")) {
				logger.log(Level.INFO, "Count industries in each sector");
				Nasdaq.countIndustries(cmd.getOptionValue("ci"))
				.entrySet()
				.stream()
				.forEach(n->System.out.println(n.getKey() + " " + n.getValue() + "%"));
			}else if (cmd.hasOption("o")) {
				logger.log(Level.INFO, "Ten oldest companies from each sector");
				Nasdaq.tenOldestBySector(cmd.getOptionValue("o"));
			}else if (cmd.hasOption("e")) {
				logger.log(Level.INFO, "Ten most expencive companies from each sector");
				Nasdaq.tenMostExpensiveBySector(cmd.getOptionValue("e"));
			}else if (cmd.hasOption("s")) {
				logger.log(Level.INFO, "Count companies in each sector (in percents)");
				Nasdaq.CompanySectorRate(cmd.getOptionValue("s"))
				.entrySet()
				.stream()
				.forEach(n->System.out.println(n.getKey() + " " + n.getValue() + "%"));
			}else {
				fileProcessing(args);
			}
		} catch (ParseException e) {
			logger.log(Level.INFO, "Failed to parse comand line properties", e);
			help();
		}
	}
	private void help() {

		// This prints out some help

		HelpFormatter formater = new HelpFormatter();
		System.out.println("this is help");
		formater.printHelp("Main", options);
		System.exit(0);		  
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
