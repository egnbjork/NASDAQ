<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Ten Oldest Companies By Sector</title>
		<link rel="stylesheet" href="/resources/css/main.css">
	</head>
	<body>
		<form method="get" action="/">
			<input type="submit" value="Back">
		</form>
		<h2><c:out value="${topname}"/></h2>
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
				<c:forEach var="company" items="${topcompanieslist}">
					<tr>
						<td><a href="${company.getSummaryQuote()}"><c:out value="${company.getSymbol()}" /></a></td>
						<td><c:out value="${company.getName()}" /></td>
						<td><c:out value="${company.lastSaleAsString()}" /></td>
						<td><c:out value="${company.marketCapAsString()}" /></td>
						<td><c:out value="${company.ipoAsString()}" /></td>
						<td><c:out value="${company.getSector()}" /></td>
						<td><c:out value="${company.getIndustry()}" /></td>
					</tr>
				</c:forEach>
			</table>
		<c:forEach var="sector" items="${toplist}">
			<h4>${sector.key}</h4>
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
				<c:forEach var="company" items="${sector.value}">
					<tr>
						<td><a href="${company.getSummaryQuote()}"><c:out value="${company.getSymbol()}" /></a></td>
						<td><c:out value="${company.getName()}" /></td>
						<td><c:out value="${company.lastSaleAsString()}" /></td>
						<td><c:out value="${company.marketCapAsString()}" /></td>
						<td><c:out value="${company.ipoAsString()}" /></td>
						<td><c:out value="${company.getSector()}" /></td>
						<td><c:out value="${company.getIndustry()}" /></td>
					</tr>
				</c:forEach>
			</table>
		</c:forEach>
	</body>
</html>