package berberyan.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import berberyan.entity.Company;
import berberyan.service.DbCompanyUploader;

@Service
public class DbNasdaqUploader implements DbCompanyUploader{

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public List<Company> getCompanies() {
		String hql = "from Nasdaq order by symbol";
		Session session = sessionFactory.openSession();
		@SuppressWarnings("unchecked")
		Query<Company> query = session.createQuery(hql);
		return query.list();
	}
}
