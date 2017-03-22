package berberyan.exceptions;

public abstract class DataProcessingException extends Exception {
	private static final long serialVersionUID = 1L;

	public DataProcessingException(String message, Exception e) {
		super(message, e);
	}
	
	public DataProcessingException(String message) {
		super(message);
	}
}
