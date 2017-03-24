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

import berberyan.exceptions.DataProcessingException;
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
	private static final String HEADER = "topname";
	private static final String BODY_MAP = "listbysector";
	private static final String BODY_LIST = "companieslist";
	private static final String INDEX = "index";

	@GetMapping("/")
	public String index(Model model) throws DataProcessingException { 
		LOGGER.trace("index invoked");
		nasdaq = parser.parse(uploader.upload(url));
		int countCompanies = operations.countCompanies(nasdaq);
		model.addAttribute("countcompanies", countCompanies);
		int countSectors = operations.countSectors(nasdaq);
		model.addAttribute("countsectors", countSectors);
		int countIndustries = operations.countIndustries(nasdaq);
		model.addAttribute("countindustries", countIndustries);
		Map<String, Integer> countEachSector = operations.countCompaniesEachSector(nasdaq);
		model.addAttribute("countcompanieseachsector", countEachSector);
		return INDEX;
	}

	@GetMapping("/all")
	public String showAll(Model model) throws DataProcessingException { 
		LOGGER.trace("showAll() invoked");
		if(nasdaq == null) {
			index(model);
		}
		model.addAttribute(HEADER, "Nasdaq Companies");
		model.addAttribute(BODY_LIST, nasdaq);
		return INDEX;
	}

	@GetMapping("/old")
	public String getOldest(Model model) throws DataProcessingException {
		LOGGER.trace("getOldest() invoked");
		if(nasdaq == null) {
			index(model);
		}
		Map<String, List<Company>> old = operations.getOldest(nasdaq, 10);
		model.addAttribute(HEADER, "Ten Oldest Companies");
		model.addAttribute(BODY_MAP, old);

		List<Company> oldest = operations.getOldestFromList(nasdaq, 10);
		model.addAttribute(BODY_LIST, oldest);
		return INDEX;
	}

	@GetMapping("/expensive")
	public String getMostExpensive(Model model) throws DataProcessingException {
		LOGGER.trace("getMostExpensive() invoked");
		if(nasdaq == null) {
			index(model);
		}
		Map<String, List<Company>> expensive = operations.getMostExpensive(nasdaq, 10);
		model.addAttribute(HEADER, "Ten Most Expensive Companies");
		model.addAttribute(BODY_MAP, expensive);

		List<Company> mostExpensive = operations.getMostExpensiveFromList(nasdaq, 10);
		model.addAttribute(BODY_LIST, mostExpensive);
		return INDEX;
	}

	@GetMapping("/biggestshare")
	public String getBiggestVolume(Model model) throws DataProcessingException {
		LOGGER.trace("getBiggestVolume() invoked");
		if(nasdaq == null) {
			index(model);
		}
		Map<String, List<Company>> biggestVolume = operations.getBiggestVolume(nasdaq, 10);
		model.addAttribute(HEADER, "Ten Companies With Biggest Volume");
		model.addAttribute(BODY_MAP, biggestVolume);

		List<Company> biggest = operations.getBiggestVolumeFromList(nasdaq, 10);
		model.addAttribute(BODY_LIST, biggest);

		return INDEX;
	}
}