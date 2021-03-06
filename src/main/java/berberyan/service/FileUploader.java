package berberyan.service;

import java.io.Reader;
import java.net.URL;

import org.springframework.context.annotation.Configuration;

import berberyan.exceptions.CompanyUploadException;

@FunctionalInterface
@Configuration
public interface FileUploader {
	Reader upload(URL url) throws CompanyUploadException;
}
