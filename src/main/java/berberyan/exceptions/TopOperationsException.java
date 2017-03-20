package berberyan.exceptions;

import lombok.Getter;

public class TopOperationsException extends Exception {
	private static final long serialVersionUID = 1L;
	@Getter
	private final Exception innerException;

	public TopOperationsException(String message, Exception e) {
		super(message, e);
		innerException = e;
	}

	public TopOperationsException(String message) {
		super(message);
		innerException = null;
	}
}
