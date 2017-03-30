package berberyan.service;

import java.net.URL;

import org.springframework.context.annotation.Configuration;

import berberyan.exceptions.DataProcessingException;

@Configuration
public interface DbSynchroziner {
	public void syncData() throws DataProcessingException;
	
	public void setUrl(URL url);
	public void setTableName(String tableName);
}
