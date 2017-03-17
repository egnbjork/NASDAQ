package berberyan.model;

import java.math.BigDecimal;
import java.util.Optional;

public interface Company {

	public String getSymbol();
	
	public String getName();
	
	public Optional<BigDecimal> getLastSale();
	public String lastSaleAsString();
	
	public String getSector();

	public String getIndustry();

	public String getSummaryQuote();

	public Optional<BigDecimal> getMarketCap();
	public String marketCapAsString();

	public Optional<Integer> getIpo();
	public String ipoAsString();
}
