<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
	<head>
		<meta charset="utf-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    		<meta name="viewport" content="width=device-width, initial-scale=1">
    		<link href="/resources/css/bootstrap.css" rel="stylesheet">
		<title>NASDAQ</title>
	</head>
	<body>
		<div class="header">
			<a href="/">
				<img src="/resources/img/nasdaq-logo.png">
			</a>
			<h2 class="page-header">NASDAQ <small>Top</small></h2>
		</div>
		<ul class="list-inline text-capitalize text-center">
			<li><a href="/expensive" class="btn btn-primary btn-lg" role="button">Most expensive</a></li>
			<li><a href="/biggestshare" class="btn btn-primary btn-lg" role="button">biggest volume</a></li>
			<li><a href="/old" class="btn btn-primary btn-lg" role="button">Oldest</a></li>
			<li><a href="/all" class="btn btn-primary btn-lg" role="button">List all</a></li>
		</ul>
		<hr/>
		<div class="container">
			<c:choose>
				<c:when test="${companieslist == null }">
					<div class="well well-lg">
						<p>
							<c:out value="${countcompanies}"/> companies in <c:out value="${countsectors}"/> sectors and <c:out value="${countindustries}" /> industries
						</p>
						<ul>
							<c:forEach items="${countcompanieseachsector}" var="sector" >
								<li><c:out value="${sector.key}"/>: <fmt:formatNumber value="${sector.value/countcompanies*100}" maxFractionDigits="1"/>%</li>
							</c:forEach>
						</ul>
					</div>
				</c:when>
				<c:otherwise>
					<div class="container">
						<h3 class="page-header"><c:out value="${topname}"/></h3>
						<table class="table table-striped table-bordered table-hover table-condensed">
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
						<c:if test="${totalpages != null}">
							<div class="text-center">
								<ul class="pagination">
									<c:if test="${currentpage != 0}">
										<li><a href="/all/0">First</a></li>
									</c:if>
									<c:choose>
										<c:when test="${currentpage > (totalpages - 10)}">
											<li><a href="/all/${totalpages - 11}">&lt;&lt;</a></li>
										</c:when>
										<c:when test="${currentpage > 0}">
											<li><a href="/all/${currentpage - 1}">&lt;&lt;</a></li>
										</c:when>
									</c:choose>
										<li class="currentpage"><a href="/all/${currentpage}">${currentpage + 1}</a></li>
									<c:choose>
									 	<c:when test="${(currentpage + 8) < (totalpages - 1) }">
											<c:forEach begin="1" end="9" var="i">
												<li><a href="/all/${currentpage + i}">${currentpage + 1 + i}</a></li>
											</c:forEach>
											<li><a href="/all/${currentpage + 8}">&gt;&gt;</a></li>
										</c:when>
										<c:otherwise>
											<c:forEach begin="${totalpages-10}" end="${totalpages}" var="i">
												<li><a href="/all/${i}"><c:out value="${i}"/></a></li>
											</c:forEach>	
										</c:otherwise>
									</c:choose>
									<c:if test="${currentpage < (totalpages - 10)}">
										<li><a href="/all/${totalpages}">Last</a></li>
									</c:if>
								</ul>	
							</div>
						</c:if>
						<c:if test ="${listbysector != null}">
							<c:forEach var="sector" items="${listbysector}">
								<h4 class="page-header">${sector.key}</h4>
								<table class="table table-striped table-bordered table-hover table-condensed">
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
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</body>
</html>