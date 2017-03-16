package berberyan.controller;

import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import berberyan.exceptions.ParseException;
import berberyan.exceptions.UploadException;
import berberyan.model.Company;
import berberyan.service.CsvParser;
import berberyan.service.FileUploader;
import berberyan.service.impl.ApacheParser;
import berberyan.service.impl.WebUploader;

@Controller
public class IndexController {
	private static final Logger LOGGER = LogManager.getLogger(IndexController.class); 

	@GetMapping("/")
	public String index(Model model, HttpSession session) throws MalformedURLException, ParseException, UploadException {
		LOGGER.debug("index() invoked");
		FileUploader uploader = new WebUploader();
		CsvParser<Company> parser = new ApacheParser();
		URL url = null;
		url = new URL("http://www.nasdaq.com/screening/companies-by-name.aspx?letter=0&exchange=nasdaq&render=download");
		Reader reader = uploader.upload(url);
		parser.parse(reader);
		List<Company> nasdaq = parser.parse(uploader.upload(url));
		model.addAttribute("nasdaq", nasdaq);
		return "index";
	}
}