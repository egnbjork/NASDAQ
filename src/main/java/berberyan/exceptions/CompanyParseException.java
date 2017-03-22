package berberyan.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@ResponseStatus(value=HttpStatus.SERVICE_UNAVAILABLE, reason="Cannot parse data")
public class CompanyParseException extends DataProcessingException {
	private static final long serialVersionUID = 1L;
	private static final String ERROR_MSG = "Cannot parse file";
	@Getter
	private final Exception innerException;

	public CompanyParseException (String message, Exception e) {
		super(ERROR_MSG + ": " + message, e);
		innerException = e;
	}

	public CompanyParseException (String message) {
		super(ERROR_MSG + ": " + message);
		innerException = null;
	}
}
