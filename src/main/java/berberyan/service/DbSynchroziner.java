package berberyan.service;

import java.net.URL;

import berberyan.exceptions.DataProcessingException;

public interface DbSynchroziner {
	public void syncData() throws DataProcessingException;
	
	public void setUrl(URL url);
	public void setTableName(String tableName);
}
