package berberyan.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import berberyan.config.AppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfig.class)
@WebAppConfiguration
//hibernate config file removed from open repository
//ApplicationContext cannot be loaded
//@Ignore
public class TopOperationsTest {

	@Autowired
	TopOperations operations;
	
	@Test
	public void autowired_test() {
		assertNotNull(operations);
	}
}
