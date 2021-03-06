package berberyan.service;

import java.util.List;

import org.springframework.context.annotation.Configuration;

import berberyan.entity.Company;

@Configuration
@FunctionalInterface
public interface DbCompanyUploader {
	public List<Company> getCompanies();
}
