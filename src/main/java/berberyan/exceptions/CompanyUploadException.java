package berberyan.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@ResponseStatus(value=HttpStatus.SERVICE_UNAVAILABLE, reason="Failed to download data")
public class CompanyUploadException extends DataProcessingException {
	private static final long serialVersionUID = 1L;
	private static final String ERROR_MSG = "Cannot upload file";
	@Getter
	private final Exception innerException;
	
	public CompanyUploadException(String message, Exception e) {
		super(ERROR_MSG + ": " + message, e);
		innerException = e;
	}
	
	public CompanyUploadException(String message) {
		super(ERROR_MSG + ": " + message);
		innerException = null;
	}
}
