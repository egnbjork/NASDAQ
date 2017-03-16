package berberyan.exceptions;

import lombok.Getter;

public class ParseException extends Exception {
	private static final long serialVersionUID = 1L;
	private static final String ERROR_MSG = "Cannot parse file";
	@Getter
	private final Exception innerException;

	public ParseException (String message, Exception e) {
		super(ERROR_MSG + ": " + message, e);
		innerException = e;
	}

	public ParseException (String message) {
		super(ERROR_MSG + ": " + message);
		innerException = null;
	}
}
