package berberyan.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import berberyan.config.AppConfig;
import berberyan.config.AppInitializer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class, AppInitializer.class})
@WebAppConfiguration
public class IndexControllerTest {
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}	
	
	@Test
	public void index_test() throws Exception {
		mockMvc.perform(get("/"))
		.andExpect(status().isOk())
		.andExpect(view().name("index"));
	}
	
	@Test
	public void listall_test() throws Exception {
		mockMvc.perform(get("/all"))
		.andExpect(status().isOk())
		.andExpect(view().name("index"));
	}
	
	@Test
	public void oldest_test() throws Exception {
		mockMvc.perform(get("/old"))
		.andExpect(status().isOk())
		.andExpect(view().name("index"));
	}
	
	@Test
	public void expensive_test() throws Exception {
		mockMvc.perform(get("/expensive"))
		.andExpect(status().isOk())
		.andExpect(view().name("index"));
	}
	
	@Test
	public void biggestVolume_test() throws Exception {
		mockMvc.perform(get("/biggestshare"))
		.andExpect(status().isOk())
		.andExpect(view().name("index"));
	}
}
