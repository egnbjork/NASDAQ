<!DOCTYPE HTML>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="en">
	<head>
		<title>NASDAQ</title>
		<meta name="author" content="Yevgen Berberyan"/>
        <meta name="description" content="Top companies of NASDAQ"/>
		<meta charset="utf-8" />
	    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    		<meta name="viewport" content="width=device-width, initial-scale=1" />
    		<link href="/resources/css/bootstrap.css" rel="stylesheet" />
    		<link href="/resources/css/main.css" rel="stylesheet" />
    		<link rel="shortcut icon" href="/resources/img/nasdaq-logo.ico" type="image/x-icon" />
	</head>
	<body>
		<div class="container">
			<header class="main_header">
				<a href="/">
					<img src="/resources/img/nasdaq-logo.png">
					<h2>NASDAQ <small>Top</small></h2>
				</a>
			</header>
			<nav>
				<ul class="list-inline text-capitalize text-center">
					<li><a href="/expensive" class="btn btn-primary btn-lg" role="button" tabindex="1">Most expensive</a></li>
					<li><a href="/biggestshare" class="btn btn-primary btn-lg" role="button" tabindex="2">biggest volume</a></li>
					<li><a href="/old" class="btn btn-primary btn-lg" role="button" tabindex="3">Oldest</a></li>
					<li><a href="/all" class="btn btn-primary btn-lg" role="button" tabindex="4">List all</a></li>
				</ul>
			</nav>
			<hr/>
			<section class="container">
				<c:choose>
					<c:when test="${companieslist == null }">
						<!-- general info -->
						<article class="well well-lg">
							<p>
								<c:out value="${countcompanies}"/> companies in <c:out value="${countsectors}"/> sectors and <c:out value="${countindustries}" /> industries
							</p>
							<ul>
								<c:forEach items="${countcompanieseachsector}" var="sector" >
									<li><c:out value="${sector.key}"/>: <fmt:formatNumber value="${sector.value/countcompanies*100}" maxFractionDigits="1"/>%</li>
								</c:forEach>
							</ul>
						</article>
					</c:when>
					<c:otherwise>
						<article class="container">
							<!-- button on main nav pannel is pressed -->
							<!-- first table, represents data from all sectors -->
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
										<td>
											<nav>
												<a target="_blank" href="${company.getSummaryQuote()}"><c:out value="${company.getSymbol()}" /></a>
											</nav>
										</td>	
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
								<!-- pagination -->
								<article class="text-center">
									<ul class="pagination">
										<c:if test="${currentpage != 0}">
											<li><a href="/all/0">First</a></li>
											<li><a href="/all/${currentpage - 1}">&lt;&lt;</a></li>
										</c:if>
										<c:choose>
										 	<c:when test="${currentpage < 9}">
										 		<c:forEach begin="0" end="9" var="i">
										 			<c:choose>
										 				<c:when test="${currentpage == i}">
										 					<li class="active" disabled><a href="/all/${currentpage}">${currentpage + 1}</a>	
										 				</c:when>
										 				<c:otherwise>
										 					<li><a href="/all/${i}">${i+1}</a></li>
										 				</c:otherwise>
										 			</c:choose>
										 		</c:forEach>
										 	</c:when>
										 	<c:when test="${(currentpage + 8) < (totalpages - 1) }">
												<c:forEach var="i" begin="0" end="9">
													<c:choose>
														<c:when test="${i == 5}">
															<li class="active" disabled><a href="/all/${currentpage}">${currentpage + 1}</a></li>
														</c:when>
														<c:otherwise>
															<li><a href="/all/${currentpage - 5 + i}">${currentpage - 4 + i}</a></li>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<c:forEach begin="${totalpages - 10}" end="${totalpages - 1}" var="i">
													<c:choose>
														<c:when test="${currentpage == i}">
															<li class="active" disabled><a href="/all/${i}"><c:out value="${i + 1}"/></a></li>
														</c:when>
														<c:otherwise>
															<li><a href="/all/${i}"><c:out value="${i + 1}"/></a></li>
														</c:otherwise>
													</c:choose>
												</c:forEach>	
											</c:otherwise>
										</c:choose>
										<c:if test="${currentpage !=  (totalpages - 1)}">
											<li><a href="/all/${currentpage + 1}">&gt;&gt;</a></li>
											<li><a href="/all/${totalpages - 1}">Last</a></li>
										</c:if>
									</ul>	
								</article>
							</c:if>
							<c:if test ="${listbysector != null}">
								<!-- tables representing data from each sector -->
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
						</article>
					</c:otherwise>
				</c:choose>
			</section>
		</div>
	</body>
</html>