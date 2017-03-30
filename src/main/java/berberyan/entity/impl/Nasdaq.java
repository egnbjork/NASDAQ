package berberyan.entity.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import berberyan.entity.Company;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="companies")
@NoArgsConstructor
public class Nasdaq implements Company{
	private static final Logger LOGGER = LogManager.getLogger(Nasdaq.class); 

	@Id
	@Getter
	private String symbol;

	@Getter
	private String name;

	@Column(name="last_sale")
	private BigDecimal lastSale;

	@Getter
	@Column(name="sector")
	private String sector;

	@Getter
	@Column(name="industry")
	private String industry;

	@Getter
	@Column(name="summary_quote")
	private String summaryQuote;

	@Column(name="market_cap")
	private BigDecimal marketCap;

	@Column(name="ipo_year")
	private Integer ipo;
	
	@Column
	private String stock_exch="Nasdaq";
	
	private static final String NOT_AVAILABLE = "n/a";
	private static final BigDecimal BILLION = BigDecimal.valueOf((long)1_000_000_000); 
	private static final BigDecimal MILLION = BigDecimal.valueOf((long)1_000_000); 

	public Nasdaq(CompanyBuilder builder){
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

	@Override
	public String lastSaleAsString() {
		return getLastSale().map(BigDecimal::toString).orElse(NOT_AVAILABLE);
	}

	@Override
	public String ipoAsString() {
		return getIpo().map(String::valueOf).orElse(NOT_AVAILABLE);
	}

	@Override
	public String marketCapAsString() {
		if(marketCap == null) {
			return NOT_AVAILABLE;
		}

		String mcString = "$";
		if(marketCap.compareTo(BILLION) >= 0) {
			mcString += marketCap.divide(BILLION).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros() + "B"; 
		}
		else if(marketCap.compareTo(MILLION) >= 0) {
			mcString += marketCap.divide(MILLION).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros() + "M";
		}
		else {
			mcString += marketCap;
		}
		return mcString;
	}

	@Override
	public Optional<BigDecimal> getSharesAmount() {
		if (this.getMarketCap().isPresent() && this.getLastSale().isPresent()) {
			return Optional.of(this.getMarketCap().get()
					.divide(this.getLastSale().get(), 0, RoundingMode.HALF_UP));
		}
		return Optional.empty();
	}

	public Optional<BigDecimal> getLastSale() {
		if(lastSale == null) {
			return Optional.empty();
		}
		return Optional.of(lastSale);
	}

	public Optional<Integer> getIpo() {
		if(ipo == null) {
			return Optional.empty();
		}
		return Optional.of(ipo);
	}

	public Optional<BigDecimal> getMarketCap() {
		if(marketCap == null) {
			return Optional.empty();
		}
		return Optional.of(marketCap);
	}

	@Column(name="stock_exch")
	public String getStickExchange() {
		return "Nasdaq";
	}
	@NoArgsConstructor
	public static class CompanyBuilder {
		private String symbol;

		private String name;

		private String sector;

		private String industry;

		private BigDecimal lastSale;

		private BigDecimal marketCap;

		private Integer ipo;

		private String summaryQuote;

		public CompanyBuilder(Nasdaq company) {
			this.symbol = company.getSymbol();
			this.name = company.getName();
			this.sector = company.getSector();
			this.industry = company.getIndustry();
			Optional<BigDecimal> sale = company.getLastSale();
			if(sale.isPresent()) {
				this.lastSale = sale.get();
			}
			Optional<BigDecimal> mc = company.getMarketCap();
			if(mc.isPresent()) {
				this.marketCap = mc.get();
			}
			Optional<Integer> ipo = company.getIpo();
			if(ipo.isPresent()) {
				this.ipo = ipo.get();
			}
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
				this.lastSale = new BigDecimal(lastSale);
			} catch (NumberFormatException e) {
				//not a number
			}
			return this;
		}

		public CompanyBuilder setMarketCap(String marketCap) {
			if(marketCap == null ||
					"n/a".equals(marketCap)){
				//equals to null
			}
			//million
			else if(marketCap.endsWith("M")){
				LOGGER.debug("marketCap is millions");
				this.marketCap = BigDecimal.valueOf((long) (Double.parseDouble(marketCap
						.substring(1, marketCap.length()-1)) * 1_000_000));
			}
			//billion
			else if(marketCap.endsWith("B")){
				LOGGER.debug("marketCap is billions");
				this.marketCap = BigDecimal.valueOf((long) (Double.parseDouble(marketCap
						.substring(1, marketCap.length()-1)) * 1_000_000_000));
			}
			return this;
		}

		public CompanyBuilder setIpo(String ipo) {
			try{
				this.ipo = Integer.parseInt(ipo);
			} catch(NumberFormatException e){
				//value is not a number
			}
			return this;
		}

		public CompanyBuilder setSummaryQuote(String summaryQuote) {
			this.summaryQuote = summaryQuote;
			return this;
		}

		public Nasdaq build() {
			return new Nasdaq(this);
		}
	}
}
