package berberyan.service;

import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import berberyan.exceptions.DataProcessingException;

@Service
public class ScheduledTasks {
	private static final Logger LOGGER = LogManager.getLogger(ScheduledTasks.class); 

	@Autowired
	DbSynchroziner sync;

	@Value("${companylist}")
	URL url;

	@Scheduled(fixedDelay=6000*60*60*6)
	public void syncDb() {
		try {
			sync.setUrl(url);
			sync.setTableName("companies");
			sync.syncData();
			LOGGER.info("db sync complete");
		} catch (DataProcessingException e) {
			LOGGER.error("cannont sync db", e);
		}
	}
}
