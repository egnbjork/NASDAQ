package berberyan.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import berberyan.exceptions.CompanyParseException;
import berberyan.exceptions.CompanyUploadException;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class); 

	@ExceptionHandler(CompanyUploadException.class)
	public String uploadException() {
		LOGGER.error("error while downloading data");
		return "error";
	}

	@ExceptionHandler(CompanyParseException.class)
	public String parseException() {
		LOGGER.error("error while parsing data");
		return "error";
	}
}
