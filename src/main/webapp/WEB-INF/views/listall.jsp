<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>NASDAQ</title>
<link rel="stylesheet" href="/resources/css/main.css">
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
		</tr>
		<c:forEach var="company" items="${nasdaq}">
			<tr>
				<td><a href ="${company.getSummaryQuote()}">${company.getSymbol()}</a></td>
				<td>${company.getName()}</td>
				<td>${company.lastSaleAsString()}</td>
				<td>${company.marketCapAsString()}</td>
				<td>${company.ipoAsString()}</td>
				<td>${company.getSector()}</td>
				<td>${company.getIndustry()}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>