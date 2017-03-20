package berberyan.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.Reader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import berberyan.config.AppConfig;
import berberyan.exceptions.ParseException;
import berberyan.model.Company;
import berberyan.service.impl.NasdaqParser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfig.class)
@WebAppConfiguration
public class CsvParserTest {

	@Autowired
	CsvParser<Company> parser;

	@Mock
	Reader reader;

	@Test
	public void autowired_test() {
		assertNotNull(parser);
	}

	@Test
	@SuppressWarnings("rawtypes")
	public void apacheParser_wired_test() {
		Class expected = NasdaqParser.class;
		Class actual = parser.getClass();

		assertEquals(expected, actual);
	}

	@Test (expected = ParseException.class)
	public void parseRecords_StreamNull() throws ParseException {
		parser.parse(null);
	}

	@SuppressWarnings("unchecked")
	@Test (expected = ParseException.class)
	public void parseRecords_IOException() throws ParseException {
		when(parser.parse(reader)).thenThrow(IOException.class);
		parser.parse(reader);
	}	
}