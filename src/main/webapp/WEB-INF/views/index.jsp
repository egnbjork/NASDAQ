<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>NASDAQ</title>
<style>
	h2 {
		text-align: center
	}
	
	table, th, td {
		font-family: Arial;
		border-collapse: collapse;
    		border: 1px solid black;
	}
</style>
</head>
<body>
	<h2>NASDAQ</h2>
	<table>
		<tr>
			<th>Symbol</th>
			<th>Name</th>
			<th>Last Sale</th>
			<th>MarketCap</th>
			<th>IPO year</th>
			<th>Sector</th>
			<th>Industry</th>
			<th>Summary Quote</th>
		</tr>
		<c:forEach var="company" items="${nasdaq}">
			<tr>
				<td>${company.getSymbol()}</td>
				<td>${company.getName()}</td>
				<td>${company.lastSaleAsString()}</td>
				<td>${company.marketCapAsString()}</td>
				<td>${company.ipoAsString()}</td>
				<td>${company.getSector()}</td>
				<td>${company.getIndustry()}</td>
				<td>${company.getSummaryQuote()}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>