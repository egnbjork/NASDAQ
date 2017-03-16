package berberyan.exceptions;

import lombok.Getter;

public class UploadException extends Exception {
	private static final long serialVersionUID = 1L;
	private static final String ERROR_MSG = "Cannot upload file";
	@Getter
	private final Exception innerException;
	
	public UploadException(String message, Exception e) {
		super(ERROR_MSG + ": " + message, e);
		innerException = e;
	}
	
	public UploadException(String message) {
		super(ERROR_MSG + ": " + message);
		innerException = null;
	}
}
