package berberyan.service;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
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
			throw new ParseException("Cannot parse file from stream\n" + e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					throw new ParseException("Cannot close reader", e);
				}
			}
		}
	}

	List<T> parseRecords(CSVParser parse);
}
