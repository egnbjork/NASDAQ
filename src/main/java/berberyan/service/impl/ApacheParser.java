package berberyan.service.impl;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import berberyan.exceptions.ParseException;
import berberyan.model.Company;
import berberyan.service.CsvParser;

public class ApacheParser implements CsvParser<Company> {
	private static final Logger LOGGER = LogManager.getLogger(ApacheParser.class); 

	@Override
	public List<Company> parse(Reader reader) throws ParseException {
		try {
			return parseRecords(CSVFormat.RFC4180.withFirstRecordAsHeader().parse(reader));
		} catch (IOException e) {
			ParseException parseException = new ParseException("Cannot parse file from stream\n" + e);
			LOGGER.warn(parseException);
			throw parseException;
		}
	}

	private List<Company> parseRecords(CSVParser records) {
		LOGGER.debug("Start parsing records");
		Integer count = 0;
		List<Company> companyList = new ArrayList<Company>();
		for (CSVRecord record: records){
			companyList.add(
					new Company.CompanyBuilder()
					.setSymbol(record.get(0))
					.setName(record.get("Name"))
					.setLastSale(record.get("LastSale"))
					.setMarketCap(record.get("MarketCap"))
					.setIpo(record.get("IPOyear"))
					.setSector(record.get("Sector"))
					.setIndustry(record.get("industry"))
					.setSummaryQuote(record.get("Summary Quote"))
					.build());
			count++;
		}

		if(companyList.isEmpty()){
			LOGGER.info("empty stream passed");
		}
		else{
			LOGGER.info(count + " entries processed");
		}

		return companyList;
	}

}
