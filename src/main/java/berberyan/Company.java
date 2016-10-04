package berberyan;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import lombok.Data;

public @Data class Company {

	Company(String symbol, 
			String name, 
			String lastSale, 
			String marketCap,
			String ipo,
			String sector, 
			String industry,
			String summaryQuote
			){
		this.symbol = symbol;
		this.name = name;
		this.lastSale = saleConvertion(lastSale);
		this.marketCap = marketCapConvertion(marketCap);
		this.ipo = yearConvertion(ipo);
		this.sector = sector;
		this.industry = industry;
		this.summaryQuote = summaryQuote;
	}

	Company(){};

	private String symbol;
	private String name;
	private BigDecimal lastSale;
	private String sector;
	private String industry;
	private String summaryQuote;
	private BigInteger marketCap;
	private Integer ipo;

	private BigDecimal saleConvertion(String sale){
			try{
				return new BigDecimal(sale);
			} catch(NumberFormatException e){
				return BigDecimal.valueOf(-1);
			}
	}

	private BigInteger marketCapConvertion(String marketCap){
		if(marketCap.equals("n/a")){
			return BigInteger.valueOf(-1);
		}
		else if(marketCap.endsWith("M")){
			return BigInteger.valueOf((long) (Double.parseDouble(marketCap
					.substring(1,marketCap.length()-1))*1000000));
		}
		else if(marketCap.endsWith("B")){
			return BigInteger.valueOf((long) (Double.parseDouble(marketCap
					.substring(1,marketCap.length()-1))*1000000000));
		}
		return BigInteger.valueOf(0);
	}

	private Integer yearConvertion(String ipo){
		try{
			return Integer.parseInt(ipo);
		} catch(NumberFormatException e){
			return -1;
		}
	}
}
