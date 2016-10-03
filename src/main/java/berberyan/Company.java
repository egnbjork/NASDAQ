package berberyan;

import java.math.BigInteger;

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
		this.lastSale = lastSale;
		this.marketCap = marketCapConvertion(marketCap);
		this.ipo = yearConvertion(ipo);
		this.sector = sector;
		this.industry = industry;
		this.summaryQuote = summaryQuote;
	}
	
	Company(){};
	
	private String symbol;
	private String name;
	private String lastSale;
	private String sector;
	private String industry;
	private String summaryQuote;
	private BigInteger marketCap;
	private Integer ipo;
	
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
