package berberyan.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import berberyan.entity.Company;
import berberyan.entity.impl.Nasdaq;
import berberyan.service.CsvParser;

@Service
public class NasdaqParser implements CsvParser<Company> {
	private static final Logger LOGGER = LogManager.getLogger(NasdaqParser.class); 

	@Override
	public List<Company> parseRecords(CSVParser records) {
		LOGGER.debug("parseRecords() invoked");
		int count = 0;
		List<Company> companyList = new ArrayList<>();
		for (CSVRecord record: records){
			companyList.add(
					new Nasdaq.CompanyBuilder()
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

		LOGGER.info(count + " entries processed");

		return companyList;
	}

}
