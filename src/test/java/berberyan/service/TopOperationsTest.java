package berberyan.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import static org.junit.Assert.assertNotNull;

import berberyan.config.AppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfig.class)
@WebAppConfiguration
public class TopOperationsTest {

	@Autowired
	TopOperations operations;
	
	@Test
	public void autowired_test() {
		assertNotNull(operations);
	}
}
