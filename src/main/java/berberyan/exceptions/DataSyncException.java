package berberyan.exceptions;

public class DataSyncException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private static final String ERROR_MSG = "Error while uploading file to database";
	
	public DataSyncException(String message) {
		super(ERROR_MSG + ": " + message);
	}
}
