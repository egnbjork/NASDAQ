package berberyan.exceptions;

public class DataSyncException extends DataProcessingException {
	private static final long serialVersionUID = 1L;
	private static final String ERROR_MSG = "Error while uploading file to database";
	
	public DataSyncException(String message) {
		super(ERROR_MSG + ": " + message);
	}
	
	public DataSyncException(String message, Exception e) {
		super(ERROR_MSG + ": " + message, e);
	}
}
