<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>竞拍详情</title>
</head>
<body>
<div>
<%--    main begin--%>
    <h1>拍卖品详细信息</h1>
    <div>
        <c:if test="${sessionScope.admin!=null}">
            <div><a href="${pageContext.request.contextPath}/user/logout">注销</a></div>
        </c:if>
        <c:if test="${sessionScope.admin==null}">
            <div><a href="${pageContext.request.contextPath}/login.jsp">登录</a> </div>
        </c:if>
    </div>
    <div>
        <ul>
            <li>名称：</li>
            <li>${auctionDetail.auctionname}</li>
        </ul>
        <ul>
            <li>描述：</li>
            <li>${auctionDetail.auctiondesc}</li>
        </ul>
        <ul>
            <li>开始时间：</li>
            <li><fmt:formatDate value="${auctionDetail.auctionstarttime}" pattern="yyyy-MM-dd hh:mm"/></li>
        </ul>
        <ul>
            <li>结束时间：</li>
            <li><fmt:formatDate value="${auctionDetail.auctionendtime}" pattern="yyyy-MM-dd hh:mm"/></li>
        </ul>
        <ul>
            <li>起拍价：</li>
            <li>${auctionDetail.auctionstartprice}</li>
        </ul>
    </div>
    <div><img src="${pageContext.request.contextPath}/static/images/${auctionDetail.auctionpic}"width="270" height="185"/></div>

    <form action="${pageContext.request.contextPath}/auction/saveAuctionRecord/${auctionDetail.auctionid}" method="post">
        <div>
            <label>出价：</label>
            <input type="text" name="auctionPrice"/>
            <input type="submit" value="竞拍"/>
        </div>
    </form>

    <div>
        <input type="submit" value="刷新" onclick="location='${pageContext.request.contextPath}/auction/auctionDetail/${auctionDetail.auctionid}'"/>
        <input type="button" value="返回列表" onclick="location='${pageContext.request.contextPath}/auction/findAuction'"/>
    </div>

    <div>
        <h3>出价记录</h3>
        <div>
            <ul>
                <li>竞拍时间</li>
                <li>竞拍价格</li>
                <li>竞拍人</li>
            </ul>

<%--            迭代输出竞拍记录--%>
            <c:if test="${fn:length(auctionDetail.auctionrecordList)>0}">
                <c:forEach var="auctionRecord" items="${auctionDetail.auctionrecordList}">
                    <ul>
                        <li>
                            <fmt:formatDate value="${auctionRecord.auctiontime}" pattern="yyyy-MM-dd hh:mm:ss"/>
                        </li>
                        <li>
                            ${auctionRecord.auctionprice}
                        </li>
                        <li>
                            ${auctionRecord.user.username}
                        </li>
                    </ul>
                </c:forEach>
            </c:if>
            <c:if test="${fn:length(auctionDetail.auctionrecordList)==0}">
                <ul>
                    <li>无竞拍记录</li>
                </ul>
            </c:if>
<%--            迭代输出竞拍记录 end--%>
        </div>
    </div>
<%--    main end--%>
</div>
</body>
</html>
