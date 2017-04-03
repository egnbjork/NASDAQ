package berberyan.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import berberyan.config.AppConfig;
import berberyan.service.impl.WebUploader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfig.class)
@WebAppConfiguration
//hibernate config file removed from open repository
//ApplicationContext cannot be loaded
@Ignore
public class FileUploaderTest {

	@Autowired
	FileUploader uploader;
	
	@Test
	public void uploader_autowiredTest() {
		assertNotNull(uploader);
	}
	
	@Test
	@SuppressWarnings("rawtypes")
	public void uploader_webUploaderWiringTest() {
		Class expected = WebUploader.class;
		Class actual = uploader.getClass();
		
		assertEquals(expected, actual);
	}
}
