package berberyan.service.impl;

import java.net.URL;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import berberyan.entity.Company;
import berberyan.exceptions.DataProcessingException;
import berberyan.service.CsvParser;
import berberyan.service.DbCompanyUploader;
import berberyan.service.DbSynchroziner;
import berberyan.service.FileUploader;
import lombok.Setter;

@Service
public class NasdaqDbSync implements DbSynchroziner {
	private static final Logger LOGGER = LogManager.getLogger(NasdaqDbSync.class); 

	@Autowired
	FileUploader webUploader;

	@Autowired
	CsvParser<Company> parser;

	@Autowired
	DbCompanyUploader dbUploader;

	@Autowired
	SessionFactory sessionFactory;

	@Setter
	private URL url;
	
	@Setter
	private String tableName;

	private Session session;

	@Override
	public void syncData() throws DataProcessingException {
		session = sessionFactory.getCurrentSession();
		LOGGER.info("syncData() invoked");
		List<Company> webCompanies = parser.parse(webUploader.upload(url));
		session.beginTransaction();
		for(Company company : webCompanies)
			session.saveOrUpdate(company);
		session.getTransaction().commit();
	}
}
