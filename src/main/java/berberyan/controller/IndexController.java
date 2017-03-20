package berberyan.controller;

import java.net.URL;
import java.util.List;
import java.util.Map;

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
import berberyan.service.TopOperations;

@Controller
public class IndexController {
	private static final Logger LOGGER = LogManager.getLogger(IndexController.class); 

	@Autowired
	FileUploader uploader;
	@Autowired
	CsvParser<Company> parser;
	@Autowired
	TopOperations operations;
	@Value("${companylist}")
	URL url;
	List<Company> nasdaq;
	private static final String ERROR = "error";

	@GetMapping("/")
	public String index(Model model) { 
		try {
			nasdaq = parser.parse(uploader.upload(url));
		} catch (ParseException | UploadException e) {
			LOGGER.error("Cannot parse data", e);
			return ERROR;
		}
		return "index";
	}

	@GetMapping("/all")
	public String showAll(Model model) { 
		if(nasdaq == null) {
			return ERROR;
		}
		model.addAttribute("nasdaq", nasdaq);
		return "listall";
	}

	@GetMapping("/old")
	public String getOldest(Model model) {
		if(nasdaq == null) {
			return ERROR;
		}
		Map<String, List<Company>> old = operations.oldest(nasdaq, 10);
		model.addAttribute("old", old);
		return "oldest";
	}
}