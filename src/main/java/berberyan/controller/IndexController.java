package berberyan.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import berberyan.exceptions.ParseException;
import berberyan.exceptions.UploadException;
import berberyan.model.Company;
import berberyan.service.CsvParser;
import berberyan.service.FileUploader;

@Controller
public class IndexController {
	private static final Logger LOGGER = LogManager.getLogger(IndexController.class); 
	
	@Autowired
	FileUploader uploader;
	@Autowired
	CsvParser<Company> parser;
	@Value("${companylist}")
	URL url;

	@GetMapping("/")
	public String index(Model model) throws MalformedURLException, ParseException, UploadException {
		LOGGER.debug("index() invoked");
		List<Company> nasdaq = parser.parse(uploader.upload(url));
		model.addAttribute("nasdaq", nasdaq);
		return "index";
	}
}