package berberyan.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import berberyan.exceptions.CompanyUploadException;
import berberyan.service.FileUploader;

@Service
public class WebUploader implements FileUploader {
	private static final Logger LOGGER = LogManager.getLogger(WebUploader.class); 
	Reader reader;

	@Override
	public Reader upload(URL url) throws CompanyUploadException {
		try {
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			LOGGER.info("file uploaded successfully");
			return reader;
		} catch (IOException e) {
			try {
				if(reader != null) {
					reader.close();
				}
			} catch (IOException e1) {
				LOGGER.error("cannot close reader");
				throw new CompanyUploadException("Cannot close reader", e1);
			}
			CompanyUploadException uploadException = new CompanyUploadException("Cannot upload file from url\n" + e);
			LOGGER.error(uploadException);
			throw uploadException;
		}
	}
}
