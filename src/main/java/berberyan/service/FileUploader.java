package berberyan.service;

import java.io.Reader;
import java.net.URL;

import berberyan.exceptions.UploadException;

public interface FileUploader {
	public Reader upload(URL url) throws UploadException;
}
