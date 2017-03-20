package berberyan.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	public Map<String, List<Company>> oldest(List<Company> companies, int howMany) {
		List<String> sectors = companies.stream()
				.map(Company::getSector)
				.distinct()
				.collect(Collectors.toList());

		Map<String, List<Company>> oldest = new HashMap<>();

		for(String sector : sectors) {
			List<Company> topOldest = oldestInSector(companies, sector, howMany);
			oldest.put(sector, topOldest);
		}
		return oldest;
	}

	private List<Company> oldestInSector(List<Company> companies, String sector, int howMany) {
		return companies.stream()
				.filter(n->n.getSector().equals(sector))
				.filter(n -> n.getIpo().isPresent())
				.sorted(TopNasdaqOperations::sortByYear)
				.limit(howMany)
				.collect(Collectors.toList());
	}

	private static int sortByYear(Company c1, Company c2) {
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
}
