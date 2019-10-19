<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>竞拍结果</title>
</head>

<body>
	<div>
			<!-- main begin-->
			<div>
				<h1>拍卖结束的商品</h1>
				<div>
					<c:if test="${sessionScope.admin!=null}">
						当前用户是：<span>${sessionScope.admin.username}</span>
					</c:if>
					<c:if test="${sessionScope.admin==null}">
						<input type="button" value="登录" onclick="location='${pageContext.request.contextPath}/login.jsp'"/>
					</c:if>
				</div>
			</div>
			<c:if test="${fn:length(auctionCustomList)!=0}">
				<div>
					<ul>
						<li>名称</li>
						<li>开始时间</li>
						<li>结束时间</li>
						<li>起拍价</li>
						<li>成交价</li>
						<li>买家</li>
					</ul>
					<c:forEach var="auction" items="${auctionCustomList}" varStatus="state">
						<ul>
							<li>${auction.auctionname}</li>
							<li>
							   <fmt:formatDate value="${auction.auctionstarttime}" pattern="yyyy-MM-dd hh:mm:ss"/>
							</li>
							<li>
							   <fmt:formatDate value="${auction.auctionendtime}" pattern="yyyy-MM-dd hh:mm:ss"/>
							</li>
							<li>${auction.auctionstartprice}</li>
							<li>${auction.auctionprice}</li>
							<li>${auction.username}</li>
						</ul>
					</c:forEach>
				</div>
			</c:if>
			<c:if test="${fn:length(auctionCustomList)==0}">
				<ul>
					<li>无记录</li>
				</ul>
			</c:if>

			<c:if test="${fn:length(auctionList)!=0}">
				<h1>拍卖中的商品</h1>
				<div>
					<ul>
						<li>名称</li>
						<li>开始时间</li>
						<li>结束时间</li>
						<li>起拍价</li>
						<li>出价记录</li>
					</ul>
					<c:forEach var="auctionDetail" items="${auctionList}" varStatus="state">
						<ul>
							<li>${auctionDetail.auctionname}</li>
							<li>
								<fmt:formatDate value="${auctionDetail.auctionstarttime}" pattern="yyyy-MM-dd hh:mm:ss"/>
							</li>
							<li>
								<fmt:formatDate value="${auctionDetail.auctionendtime}" pattern="yyyy-MM-dd hh:mm:ss"/>
							</li>
							<li>${auctionDetail.auctionstartprice}</li>

							<li>
								<c:forEach var="record" items="${auctionDetail.auctionrecordList}">
									<p>
									   ${record.user.username}
									   &nbsp;&nbsp;
									   ${record.auctionprice}元</p>
								</c:forEach>
							</li>
						</ul>
					</c:forEach>
				</div>
			</c:if>
			<c:if test="${fn:length(auctionList)==0}">
				<ul>
					<li>无记录</li>
				</ul>
			</c:if>
		<!-- main end-->
	 <br/>
	 <input type="button" value="返回列表"
    		onclick="location='${pageContext.request.contextPath}/auction/findAuction'" />
	</div>
</body>
</html>
