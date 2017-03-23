package berberyan.service.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import berberyan.model.Company;
import berberyan.service.TopOperations;

@Service
public class TopNasdaqOperations implements TopOperations {
	private static final Logger LOGGER = LogManager.getLogger(TopNasdaqOperations.class); 

	@Override
	public Map<String, List<Company>> getOldest(List<Company> companies, int howMany) {
		LOGGER.debug("getOldest() invoked");
		LOGGER.debug("total companies passed: " + companies.size());
		List<String> sectors = getAllSectors(companies);

		Map<String, List<Company>> oldest = new TreeMap<>();

		for(String sector : sectors) {
			List<Company> topOldest = companies.stream()
					.filter(n->n.getSector().equals(sector))
					.filter(n -> n.getIpo().isPresent())
					.sorted(TopNasdaqOperations::sortByYear)
					.limit(howMany)
					.collect(Collectors.toList());
			if(!topOldest.isEmpty()) {
				oldest.put(sector, topOldest);
			}
			LOGGER.debug(topOldest.size() + " found companies in sector " + sector);
		}

		LOGGER.debug(oldest.size() + "sectors");
		return oldest;
	}

	@Override
	public Map<String, List<Company>> getMostExpensive(List<Company> companies, int howMany) {
		List<String> sectors = getAllSectors(companies);		
		Map<String, List<Company>> mostExpensive = new TreeMap<>();

		for(String sector : sectors) {
			List<Company> topExpensive = companies.stream()
					.filter(n->n.getSector().equals(sector))
					.filter(n->n.getMarketCap().isPresent())
					.sorted(TopNasdaqOperations::sortByMarketCap)
					.limit(howMany)
					.collect(Collectors.toList());
			mostExpensive.put(sector, topExpensive);
		}
		return mostExpensive;
	}

	@Override
	public Map<String, List<Company>> getBiggestVolume(List<Company> companies, int howMany) {
		List<String> sectors = getAllSectors(companies);
		Map<String, List<Company>> biggestVolume = new TreeMap<>();

		for(String sector: sectors) {
			List<Company> topExpensive = companies.stream()
					.filter(n->n.getSector().equals(sector))
					.sorted(TopNasdaqOperations::sortByShareAmount)
					.limit(howMany)
					.collect(Collectors.toList());

			biggestVolume.put(sector, topExpensive);
		}
		return biggestVolume;
	}

	@Override
	public List<Company> getOldestFromList(List<Company> companies, int howMany) {
		return companies.stream()
				.filter(n->n.getIpo().isPresent())
				.sorted(TopNasdaqOperations::sortByYear)
				.limit(howMany)
				.collect(Collectors.toList());
	}

	@Override
	public List<Company> getMostExpensiveFromList(List<Company> companies, int howMany) {
		return companies.stream()
				.filter(n->n.getMarketCap().isPresent())
				.sorted(TopNasdaqOperations::sortByMarketCap)
				.limit(howMany)
				.collect(Collectors.toList());
	}

	@Override
	public List<Company> getBiggestVolumeFromList(List<Company> companies, int howMany) {
		return companies.stream()
				.sorted(TopNasdaqOperations::sortByShareAmount)
				.limit(howMany)
				.collect(Collectors.toList());

	}

	@Override
	public int countCompanies(List<Company> companies) {
		return companies.size();
	}

	@Override
	public int countSectors(List<Company> companies) {
		return (int) companies.stream()
				.map(Company::getSector)
				.distinct()
				.count();
	}

	@Override
	public Map<String, Integer> countCompaniesEachSector(List<Company> companies) {
		Map<String, Integer> companiesInSector = new TreeMap<>();
		List<String> sectors = companies.stream()
				.map(Company::getSector)
				.collect(Collectors.toList());

		for(String sector : sectors) {
			int count = (int) companies.stream()
					.filter(n->n.getSector().equals(sector))
					.count();

			companiesInSector.put(sector, count);
		}
		return companiesInSector;
	}

	@Override
	public int countIndustries(List<Company> companies) {
		return (int) companies.stream()
				.map(Company::getIndustry)
				.distinct()
				.count();
	}
	
	private List<String> getAllSectors(List<Company> companies) {
		return companies.stream()
				.map(Company::getSector)
				.distinct()
				.collect(Collectors.toList());

	}

	private static int sortByYear(Company c1, Company c2) {
		LOGGER.debug("sortByYear() invoked");
		LOGGER.debug(c1.getSymbol() + " is first company");
		LOGGER.debug(c2.getSymbol() + " is second company");
		if (c1.getIpo().isPresent() && c2.getIpo().isPresent()) {
			return c1.getIpo().get().compareTo(c2.getIpo().get());
		} else if (c1.getIpo().isPresent()) {
			return 1;
		} else if (c2.getIpo().isPresent()) {
			return -1;
		} 
		return 0;
	}

	private static int sortByMarketCap(Company c1, Company c2) {
		if (c1.getMarketCap().isPresent() && c2.getMarketCap().isPresent()) {
			return c2.getMarketCap().get().compareTo(c1.getMarketCap().get());
		} else if (c1.getMarketCap().isPresent()) {
			return 1;
		} else if (c2.getMarketCap().isPresent()) {
			return -1;
		} 
		return 0;
	}

	public static int sortByShareAmount(Company c1, Company c2) {
		if (c1.getSharesAmount().isPresent() && c2.getSharesAmount().isPresent()) {
			return c2.getSharesAmount().get().compareTo(c1.getSharesAmount().get());
		} else if (c1.getSharesAmount().isPresent()) {
			return -1;
		} else if (c2.getSharesAmount().isPresent()) {
			return 1;
		} 
		return 0;
	}
}
