<%--
  Created by IntelliJ IDEA.
  User: 14551
  Date: 2019/9/5
  Time: 15:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>AuctionList</title>
    <script src="/static/js/jquery-1.8.3.js"></script>
</head>
<body>
<h1>拍卖品列表</h1>
<div>
    <div>
        <form id="form_query" action="${pageContext.request.contextPath}/auction/findAuction" method="post">
            <input id="page" name="pageNum" type="hidden" value="1"/>
            <label for="auctionname">名称</label>
            <input name="auctionname" type="text" value="${condition.auctionname}" id="auctionname"/>
            <label for="auctiondesc">描述</label>
            <input name="auctiondesc" type="text" value="${condition.auctiondesc}" id="auctiondesc"/>
            <label for="auctionstarttime">开始时间</label>
            <input name="auctionstarttime" type="datetime-local" value="${condition.auctionstarttime}" id="auctionstarttime"/>
            <label for="auctionendtime">结束时间</label>
            <input name="auctionendtime" type="datetime-local" value="${condition.auctionendtime}" id="auctionendtime"/>
            <label for="auctionstartprice">起拍价</label>
            <input name="auctionstartprice" value="${condition.auctionstartprice}" type="text" id="auctionstartprice"/>
            <input type="submit" value="查询"/>
        </form>
        <c:if test="${sessionScope.admin!=null}">
            <c:if test="${sessionScope.admin.userisadmin==1}">
                <input type="button" value="发布" onclick="location='${pageContext.request.contextPath}/addAuction.jsp'"/>
            </c:if>
            <input type="button" value="注销" onclick="location='${pageContext.request.contextPath}/user/logout'"/>
        </c:if>
        <c:if test="${sessionScope.admin==null}">
            <input type="button" value="登录" onclick="location='${pageContext.request.contextPath}/login.jsp'"/>
        </c:if>
        <input type="button" value="查看竞拍结果" onclick="location='${pageContext.request.contextPath}/auction/findAuctionResult'">
    </div>
    <div>
        <ul>
            <li>名称</li>
            <li>描述</li>
            <li>开始时间</li>
            <li>起拍价</li>
            <li>操作</li>
        </ul>
        <c:forEach var="auction" items="${auctionList}">
            <li><a href="${pageContext.request.contextPath}/auction/auctionDetail/${auction.auctionid}">${auction.auctionname}</a></li>
            <li><fmt:formatDate value="${auction.auctionstarttime}" pattern="yyyy-MM-dd HH:mm"/></li>
            <li><fmt:formatDate value="${auction.auctionendtime}" pattern="yyyy-MM-dd HH:mm"/> </li>
            <li>${auction.auctionstartprice}</li>
            <li>
                <c:if test="${sessionScope.admin.userisadmin==1}">
                    <a href="${pageContext.request.contextPath}/auction/findAuction_update?auctionid=${auction.auctionid}" title="修改" onclick="update();">修改</a>
                    <a href="${pageContext.request.contextPath}/auction/deleteAuction?auctionid=${auction.auctionid}" title="删除" onclick="delet();">删除</a>
                </c:if>
                <c:if test="${sessionScope.admin.userisadmin==0}">
                    <a href="${pageContext.request.contextPath}/auction/auctionDetail/${auction.auctionid}" title="竞拍">竞拍</a>
                </c:if>
            </li>
        </c:forEach>
    </div>
    <div class="page">
        【当前${pageInfo.pageNum } 页，总共${pageInfo.pages } 页，共 ${pageInfo.total }条 】
        <!--  <a href="${pageContext.request.contextPath}/auction/findAuction?pageNum=1" title="">首页</a>
	        <a href="${pageContext.request.contextPath}/auction/findAuction?pageNum=${pageInfo.prePage}" title="">上一页</a>
	        <a href="${pageContext.request.contextPath}/auction/findAuction?pageNum=${pageInfo.nextPage}" title="">下一页</a>
	        <a href="${pageContext.request.contextPath}/auction/findAuction?pageNum=${pageInfo.pages}" title="">尾页</a>
	        -->
        <a href="javascript:jumpPage(1)" title="">首页</a>
        <a href="javascript:jumpPage(${pageInfo.prePage})" title="">上一页</a>
        <a href="javascript:jumpPage(${pageInfo.nextPage})" title="">下一页</a>
        <a href="javascript:jumpPage(${pageInfo.pages})" title="">尾页</a>
    </div>
    <script>
        $("#auctionstarttime").attr("value","<fmt:formatDate value='${condition.auctionstarttime}' pattern="yyyy-MM-dd'T'HH:mm"/>");
        $("#auctionendtime").attr("value","<fmt:formatDate value='${condition.auctionendtime}' pattern="yyyy-MM-dd'T'HH:mm"/>");
        function jumpPage(pageNum){
            //提交表单前，先修改访问的页数
            document.getElementById("page").value=pageNum
            //手动提交查询表单，因为表单中有查询内容
            document.getElementById("form_query").submit();
        }
        function delet() {
            if(confirm("你真的确定要删除吗?")){
                $("#")
                return true;
            }else{
                return false;
            }
        }
        function update() {
            if(confirm("你确定要修改吗？")){
                return true;
            }else{
                return  false;
            }
        }
    </script>
</div>
</body>
</html>
