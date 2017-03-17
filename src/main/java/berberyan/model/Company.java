package berberyan.model;

import java.math.BigDecimal;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class Company {
	private static final Logger LOGGER = LogManager.getLogger(Company.class); 

	@Getter
	private String symbol;
	@Getter
	private String name;
	@Getter
	private Optional<BigDecimal> lastSale;
	@Getter
	private String sector;
	@Getter
	private String industry;
	@Getter
	private String summaryQuote;
	@Getter
	private Optional<BigDecimal> marketCap;
	@Getter
	private Optional<Integer> ipo;
	private static final String NOT_AVAILABLE = "n/a";
	private static final BigDecimal BILLION = BigDecimal.valueOf((long)1_000_000_000); 
	private static final BigDecimal MILLION = BigDecimal.valueOf((long)1_000_000); 

	public Company(CompanyBuilder builder){
		this.symbol = builder.symbol;
		this.name = builder.name;
		this.lastSale = builder.lastSale;
		this.marketCap = builder.marketCap;
		this.ipo = builder.ipo;
		this.sector = builder.sector;
		this.industry = builder.industry;
		this.summaryQuote = builder.summaryQuote;
		LOGGER.debug("new Company created");
	}

	public String getLastSaleString() {
		return lastSale.map(BigDecimal::toString).orElse(NOT_AVAILABLE);
	}

	public String getIpoString() {
		return ipo.map(String::valueOf).orElse(NOT_AVAILABLE);
	}

	public String getMarketCapString() {
		if(marketCap.isPresent()) {
			String mcString = "$";
			if(marketCap.get().compareTo(BILLION) >= 0) {
				mcString += marketCap.get().divide(BILLION) + "B"; 
			}
			else if(marketCap.get().compareTo(MILLION) >= 0) {
				mcString += marketCap.get().divide(MILLION) + "M";
			}
			else {
				mcString += marketCap.get();
			}
			return mcString;
		}
		return NOT_AVAILABLE;
	}

	@NoArgsConstructor
	public static class CompanyBuilder {
		private String symbol;
		private String name;
		private String sector;
		private String industry;
		private Optional<BigDecimal> lastSale;
		private Optional<BigDecimal> marketCap;
		private Optional<Integer> ipo;
		private String summaryQuote;

		public CompanyBuilder(Company company) {
			this.symbol = company.getSymbol();
			this.name = company.getName();
			this.sector = company.getSector();
			this.industry = company.getIndustry();
			this.lastSale = company.getLastSale();
			this.marketCap = company.getMarketCap();
			this.ipo = company.getIpo();
			this.summaryQuote = company.getSummaryQuote();
		}

		public CompanyBuilder setSymbol(String symbol) {
			this.symbol = symbol;
			return this;
		}

		public CompanyBuilder setName (String name) {
			this.name = name;
			return this;
		}

		public CompanyBuilder setSector(String sector) {
			this.sector = sector;
			return this;
		}

		public CompanyBuilder setIndustry(String industry) {
			this.industry = industry;
			return this;
		}

		public CompanyBuilder setLastSale(String lastSale){
			try{
				this.lastSale = Optional.of(new BigDecimal(lastSale));
			} catch(NumberFormatException e){
				LOGGER.debug("last sale is null");
				this.lastSale = Optional.empty();
			}
			return this;
		}

		public CompanyBuilder setMarketCap(String marketCap) {
			if(marketCap == null ||
					"n/a".equals(marketCap)){
				LOGGER.debug("marketCap is empty");
				this.marketCap = Optional.empty();
			}
			//million
			else if(marketCap.endsWith("M")){
				LOGGER.debug("marketCap is millions");
				this.marketCap = Optional.of(BigDecimal.valueOf((long) (Double.parseDouble(marketCap
						.substring(1, marketCap.length()-1)) * 1_000_000)));
			}
			//billion
			else if(marketCap.endsWith("B")){
				LOGGER.debug("marketCap is billions");
				this.marketCap = Optional.of(BigDecimal.valueOf((long) (Double.parseDouble(marketCap
						.substring(1, marketCap.length()-1)) * 1_000_000_000)));
			}
			else {
				this.marketCap = Optional.empty();
			}
			return this;
		}

		public CompanyBuilder setIpo(String ipo) {
			try{
				LOGGER.debug("year is " + ipo);
				this.ipo = Optional.of(Integer.parseInt(ipo));
			} catch(NumberFormatException e){
				LOGGER.debug("IPO year is empty");
				this.ipo = Optional.empty();
			}
			return this;
		}

		public CompanyBuilder setSummaryQuote(String summaryQuote) {
			this.summaryQuote = summaryQuote;
			return this;
		}

		public Company build() {
			return new Company(this);
		}
	}
}
