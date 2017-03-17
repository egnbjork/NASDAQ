package berberyan.service;

import java.io.Reader;
import java.util.List;

import berberyan.exceptions.ParseException;

@FunctionalInterface
public interface CsvParser<T> {
	public List<T> parse(Reader reader) throws ParseException;
}
