<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>NASDAQ</title>
		<link rel="stylesheet" href="/resources/css/main.css">
	</head>
	<body>
		<h2>NASDAQ</h2>
		<div>
			<a href="all">List all companies</a>
			<br />
			<a href="old">Oldest in each sector</a>
			<br />
			<a href="expensive">Most expensive in each sector</a>
			<br />
			<a href="biggestshare">With biggest volume</a>
		</div>
		<hr/>	
		<div>
			<c:choose>
				<c:when test="${companieslist == null }">
					<p>
						<c:out value="${countcompanies}"/> companies in <c:out value="${countsectors}"/> sectors and <c:out value="${countindustries}" /> industries
					</p>
					<ul>
						<c:forEach items="${countcompanieseachsector}" var="sector" >
							<li><c:out value="${sector.key}"/>: <c:out value="${sector.value}"/> companies</li>
						</c:forEach>
					</ul>
				</c:when>
				<c:otherwise>
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
						<c:forEach var="company" items="${companieslist}">
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
					<c:if test ="${listbysector != null}">
							<c:forEach var="sector" items="${listbysector}">
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
						</c:if>
				</c:otherwise>
			</c:choose>
		</div>
	</body>
</html>