package berberyan.service.impl;

import java.net.URL;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import berberyan.entity.Company;
import berberyan.entity.impl.Nasdaq;
import berberyan.exceptions.DataProcessingException;
import berberyan.service.CsvParser;
import berberyan.service.DbCompanyUploader;
import berberyan.service.DbSynchroziner;
import berberyan.service.FileUploader;
import lombok.Setter;

public class NasdaqDbSync implements DbSynchroziner {
	private static final Logger LOGGER = LogManager.getLogger(NasdaqDbSync.class); 

	@Autowired
	FileUploader webUploader;

	@Autowired
	CsvParser<Nasdaq> parser;

	@Autowired
	DbCompanyUploader dbUploader;

	@Autowired
	SessionFactory sessionFactory;

	@Setter
	private URL url;
	
	@Setter
	private String tableName;

	private Session session;

	public NasdaqDbSync() {
		session = sessionFactory.getCurrentSession();
	}

	@Override
	//scheduled for every 6 hours
	@Scheduled(fixedDelay=6000*60*60)
	public void syncData() throws DataProcessingException {
		LOGGER.info("syncData() invoked");
		List<Nasdaq> webCompanies = parser.parse(webUploader.upload(url));
		session.beginTransaction();
		for(Company company : webCompanies)
			session.save(company);
		session.getTransaction().commit();
	}
}
