package berberyan.service.impl;

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
import berberyan.exceptions.CompanyParseException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfig.class)
@WebAppConfiguration
public class NasdaqParserTest {

	@Autowired
	NasdaqParser parser;
	@Mock
	Reader reader;
	
	@Test (expected = CompanyParseException.class)
	public void parseRecords_StreamNull() throws CompanyParseException {
		parser.parse(null);
	}
	
	@SuppressWarnings("unchecked")
	@Test (expected = CompanyParseException.class)
	public void parseRecords_IOException() throws CompanyParseException {
		when(parser.parse(reader)).thenThrow(IOException.class);
		parser.parse(reader);
	}
}
