package berberyan.service.impl;

import java.net.URL;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import berberyan.entity.Company;
import berberyan.exceptions.DataSyncException;
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

	@Override
	public void syncData() {
		try {
			Session session = sessionFactory.getCurrentSession();
			if( session == null )
				session = sessionFactory.openSession();
			LOGGER.info("syncData() invoked");
			List<Company> webCompanies = parser.parse(webUploader.upload(url));
			Transaction tx = session.beginTransaction();
			for(Company company : webCompanies) {
				session.merge(company);
			}
			tx.commit();
		} catch(Exception e) {
			String errorMsg = "error while sync with db";
			LOGGER.error(errorMsg, e);
			throw new DataSyncException(errorMsg, e);
		}
	}
}
