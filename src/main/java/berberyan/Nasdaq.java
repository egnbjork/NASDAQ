package berberyan;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Nasdaq {
	private static Logger logger = LogManager.getLogger(Export2Json.class);
	
	public static List<List<Company>> tenOldestCompaniesBySector(String path){
		List<Company> companies = ApacheParseCsv.parseFile(path);
		List<String> sectors = extractSector(companies);
		logger.info(sectors.size() + " sectors found");

		List<List<Company>> topTen = new ArrayList<List<Company>>();
		for (String sector : sectors){
			logger.trace("sector " + sector);
			topTen.add(tenOldestCompaniesInSector(sector,companies));
		}
		
		return topTen;
	}
	
	public static List<Company> tenOldestCompaniesInSector(String sector, List<Company> companies){
		List<Company> companiesInSector = companies
				.stream()
				.filter(n->n.getSector().equals(sector))
				.collect(Collectors.toList());
		logger.trace(companiesInSector.size() + " companies found in sector");
		return getOldestCompanies(10, companiesInSector);
	}
	
	public static List<String> extractSector(List<Company> companies){
		return companies
				.stream()
				.map(n->n.getSector())
				.distinct()
				.collect(Collectors.toList());
	}
	
	public static List<Company> getOldestCompanies(Integer numberOfCompanies, 
			List<Company> companies){
		//special cases
		if(numberOfCompanies == 0){
			logger.error("zero number of oldest companies to search for");
			throw new IllegalArgumentException();
		}
		else if(companies.size() == 1 || companies.isEmpty()){
			logger.warn(companies.size() + " companies were passed for searching");
			return companies;
		}
		
		Integer lastOldestYear = getOldestYear(companies, numberOfCompanies);
		logger.debug("year of oldest company is " + lastOldestYear);
		
		return companies
				.stream()
				.filter(n->n.getIpo()>0)
				.sorted(Nasdaq::sortByYear)
				.filter(n -> n.getIpo() <= lastOldestYear)
				.collect(Collectors.toList());
	}

	public static int sortByYear(Company c1, Company c2){
		return c1.getIpo().compareTo(c2.getIpo());
	}
	
	public static Integer getOldestYear(List<Company> companies, Integer limit){
		return companies
				.stream()
				.sorted(Nasdaq::sortByYear)
				.map(n->n.getIpo())
				.filter(n -> n > 0)  //n/a statements
				.collect(Collectors.toList())
				.get(limit-1);
	}
}
