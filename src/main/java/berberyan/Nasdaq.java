package berberyan;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Nasdaq {
	private static Logger logger = LogManager.getLogger(Nasdaq.class);

	public static Map<String,Integer> countIndustries(String importPath){
		List<Company>companies = ApacheParseCsv.parseFile(importPath);
		List<String>sectors = extractSector(companies);
		Map<String,Integer> industriesCount= new LinkedHashMap<String,Integer>();
		for (String sector : sectors){
			Integer count = (int) companies
					.stream()
					.filter(n->n.getSector().equals(sector))
					.map(n->n.getIndustry())
					.distinct()
					.count();
			industriesCount.put(sector, count);
		}

		return industriesCount.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
				.collect(Collectors
						.toMap(
								Map.Entry::getKey,
								Map.Entry::getValue,
								(a,b)->a,
								LinkedHashMap::new));
	}

	public static void tenBiggestShareAmount(String importPath, String exportPath){
		List<Company>companies = ApacheParseCsv.parseFile(importPath);
		List<Company>biggestShare = companies
				.stream()
				.sorted(Nasdaq::sortBySharesAmount)
				.limit(10)
				.collect(Collectors.toList());
		for (Company company : biggestShare){
			logger.debug(company.getName() + " " + getSharesAmount(company));
		}
		Export2Json.company2Json(biggestShare, exportPath);
	}

	public static Map<String,Integer> CompanySectorRate(String importPath){
		Map<String,Integer> companies = countCompaniesBySector(importPath);
		Integer countTotal = companies.values().stream().reduce(0,(a,b)->a+b);
		logger.debug("total " + countTotal + " companies counted");
		return companies
				.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByValue())
				.collect(Collectors
						.toMap(
								k -> k.getKey(),
								v -> countTotal/v.getValue(),
								(a,b)->a,
								LinkedHashMap::new));
	}

	private static Map<String,Integer> countCompaniesBySector(String importPath){
		List<Company> companies = ApacheParseCsv.parseFile(importPath);
		List<String> sectors = extractSector(companies);
		Map<String,Integer> countBySector = new HashMap<String,Integer>();
		for(String sector : sectors){
			Integer count = (int) companies
					.stream()
					.filter(n->n.getSector().equals(sector))
					.count();
			countBySector.put(sector, count);
		}

		return countBySector;
	}

	public static void tenMostExpensiveBySector(String importPath, String exportPath) {
		List<Company> companies = ApacheParseCsv.parseFile(importPath);
		List<String> sectors = extractSector(companies);
		List<Company> tenExpensive = new ArrayList<Company>();
		for(String sector : sectors){
			tenExpensive.addAll(
					companies.stream()
					.filter(n->n.getSector().equals(sector))
					.filter(n-> n.getMarketCap().signum() == 1)
					.sorted(Nasdaq::sortByMarketCap)
					.limit(10)
					.collect(Collectors.toList()));
		}
		Export2Json.company2Json(tenExpensive, exportPath);

	}

	public static void tenOldestBySector(String importPath, String exportPath){
		List<Company> companies = ApacheParseCsv.parseFile(importPath);
		List<String> sectors = extractSector(companies);

		List<Company> tenOldest = new ArrayList<Company>();;
		for (String sector : sectors){
			logger.trace("sector " + sector);
			tenOldest.addAll(tenOldestCompaniesInSector(sector,companies));
		}
		Export2Json.company2Json(tenOldest, exportPath);
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

		Integer lastOldestYear = getYoungestYear(companies, numberOfCompanies);
		logger.debug("year of oldest company is " + lastOldestYear);

		return companies
				.stream()
				.filter(n->n.getIpo()>0)
				.sorted(Nasdaq::sortByYear)
				.filter(n -> n.getIpo() <= lastOldestYear)
				.collect(Collectors.toList());
	}

	private static int sortByYear(Company c1, Company c2){
		return c1.getIpo().compareTo(c2.getIpo());
	}

	private static int sortByMarketCap(Company c1, Company c2){
		return c2.getMarketCap().compareTo(c1.getMarketCap());
	}

	private static int sortBySharesAmount(Company c1, Company c2){

		BigDecimal c1Shares = getSharesAmount(c1);

		BigDecimal c2Shares = getSharesAmount(c2);

		return c2Shares.compareTo(c1Shares);

	}

	private static BigDecimal getSharesAmount(Company company){
		return new BigDecimal(company.getMarketCap())
				.divide(company.getLastSale(), RoundingMode.CEILING);
	}
	
	public static Integer getYoungestYear(List<Company> companies, Integer limit){
		return companies
				.stream()
				.sorted(Nasdaq::sortByYear)
				.map(n->n.getIpo())
				.filter(n -> n > 0)  //n/a statements
				.collect(Collectors.toList())
				.get(limit-1);
	}
	
	//for cli class
	public static void tenBiggestShareAmount(String importPath){
		List<Company>companies = ApacheParseCsv.parseFile(importPath);
		companies
				.stream()
				.sorted(Nasdaq::sortBySharesAmount)
				.limit(10)
				.forEach(n -> System.out.println(n.getSymbol() + " " + n.getName() + " " + 
					new DecimalFormat("#,###").format(getSharesAmount(n))));
		
	}

	//for cli class
	public static void tenOldestBySector(String importPath){
		List<Company> companies = ApacheParseCsv.parseFile(importPath);
		List<String> sectors = extractSector(companies);

		for (String sector : sectors){
			System.out.println("\nSECTOR " + sector);
			tenOldestCompaniesInSector(sector,companies)
			.stream()
			.forEach(n->System.out.println(n.getSymbol() + " " + n.getName() + " " + n.getIpo()));;
		}
	}
	
	//for cli class
	public static void tenMostExpensiveBySector(String importPath) {
		List<Company> companies = ApacheParseCsv.parseFile(importPath);
		List<String> sectors = extractSector(companies);
		for(String sector : sectors){
			System.out.println("\n SECTOR " + sector);
				companies.stream()
					.filter(n->n.getSector().equals(sector))
					.filter(n-> n.getMarketCap().signum() == 1)
					.sorted(Nasdaq::sortByMarketCap)
					.limit(10)
					.forEach(n->
					System.out.println(n.getSymbol() + " " + n.getName() + " " + 
							new DecimalFormat("$#,###").format(n.getMarketCap()))
					);
		}
		
	}
}
