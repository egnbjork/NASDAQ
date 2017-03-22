package berberyan.service;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;

import berberyan.exceptions.ParseException;

@Configuration
@FunctionalInterface
public interface CsvParser<T> {
	default List<T> parse(Reader reader) throws ParseException {
		if(reader == null) {
			throw new ParseException("Cannot parse file: reader stream is null");
		}
		try {
			return parseRecords(CSVFormat.RFC4180.withFirstRecordAsHeader().parse(reader));
		} catch (Exception e) {
			String err = "cannot parse file from reader stream" ;
			LogHolder.LOGGER.error(err, e);
			throw new ParseException(err, e);
		} finally {
				try {
					reader.close();
				} catch (IOException e) {
					LogHolder.LOGGER.error("Error while closing stream", e);
				}
		}
	}

	List<T> parseRecords(CSVParser parse);

	final class LogHolder {
		static final Logger LOGGER = LogManager.getLogger(CsvParser.class); 
		private LogHolder(){}
	}
}
