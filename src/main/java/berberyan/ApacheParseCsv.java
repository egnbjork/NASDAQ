package berberyan;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApacheParseCsv {

	private static final Logger logger = LogManager.getLogger(ApacheParseCsv.class); 

	public static List<Company> parseFile(String filePath){
		try(Reader in = new FileReader(filePath)) {

			return parseRecords(CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in));

		} catch (IOException e) {
			logger.error(e.getMessage());
			return new ArrayList<Company>();
		}
	}

	private static List<Company> parseRecords(CSVParser records){
		Integer count = 0;
		List<Company> companyList = new ArrayList<Company>();
		for (CSVRecord record: records){
			companyList.add(
					new Company(record.get(0),
							record.get("Name"),
							record.get("LastSale"),
							record.get("MarketCap"),
							record.get("IPOyear"),
							record.get("Sector"),
							record.get("industry"),
							record.get("Summary Quote")));
			count++;
		}

		if(companyList.isEmpty()){
			logger.warn("empty file passed");
		}
		else{
			logger.info(count + " entries processed");
		}
		
		return companyList;
	}
}
