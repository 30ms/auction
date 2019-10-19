<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: 14551
  Date: 2019/9/10
  Time: 10:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>updateAuction</title>
    <script src="/static/js/jquery-1.8.3.js"></script>
    <script>
        var loadImageFile = (function() {
            if (window.FileReader) {
                var oPreviewImg = null, oFReader = new window.FileReader(), rFilter = /^(?:image\/bmp|image\/cis\-cod|image\/gif|image\/ief|image\/jpeg|image\/jpeg|image\/jpeg|image\/pipeg|image\/png|image\/svg\+xml|image\/tiff|image\/x\-cmu\-raster|image\/x\-cmx|image\/x\-icon|image\/x\-portable\-anymap|image\/x\-portable\-bitmap|image\/x\-portable\-graymap|image\/x\-portable\-pixmap|image\/x\-rgb|image\/x\-xbitmap|image\/x\-xpixmap|image\/x\-xwindowdump)$/i;

                oFReader.onload = function(oFREvent) {
                    if (!oPreviewImg) {
                        var newPreview = document.getElementById("imagePreview");
                        oPreviewImg = new Image();
                        oPreviewImg.style.width = (newPreview.offsetWidth)
                                .toString()
                            + "px";
                        oPreviewImg.style.height = (newPreview.offsetHeight)
                                .toString()
                            + "px";
                        if(newPreview.childNodes.length != 0){
                            newPreview.removeChild(document.getElementById("imgid"));
                        }
                        newPreview.appendChild(oPreviewImg);
                    }
                    oPreviewImg.src = oFREvent.target.result;
                };

                return function() {
                    var aFiles = document.getElementById("imageInput").files;
                    if (aFiles.length === 0) {
                        return;
                    }
                    if (!rFilter.test(aFiles[0].type)) {
                        alert("You must select a valid image file!");
                        return;
                    }
                    oFReader.readAsDataURL(aFiles[0]);
                }

            }
            if (navigator.appName === "Microsoft Internet Explorer") {
                return function() {
                    alert(document.getElementById("imageInput").value);
                    document.getElementById("imagePreview").filters
                        .item("DXImageTransform.Microsoft.AlphaImageLoader").src = document
                        .getElementById("imageInput").value;

                }
            }
        })();
    </script>
</head>
<body>
<form action="${pageContext.request.contextPath}/auction/updateAuction" enctype="multipart/form-data" method="post">
    <h1>拍卖品信息</h1>
    <input type="hidden" name="auctionid" value="${auction.auctionid}"/>
    <dl>
        <dd>
            <label>名称：</label>
            <input type="text" name="auctionname" value="${auction.auctionname}"/>
        </dd>
        <dd>
            <label>起拍价：</label>
            <input type="text" name="auctionstartprice" value="${auction.auctionstartprice}"/>
        </dd>
        <dd>
            <label>底价：</label>
            <input type="text" name="auctionupset" value="${auction.auctionupset}"/>
        </dd>
        <dd>
            <label>开始时间：</label>
            <input id="starttime" type="datetime-local" name="auctionstarttime"/>
        </dd>
        <dd>
            <label>结束时间：</label>
            <input id="endtime" type="datetime-local" name="auctionendtime"/>
        </dd>
        <dd>
            <label>拍卖品图片：</label>
            <div id="imagePreview">
                <img id="imgid" src="/static/images/${auction.auctionpic}" width="100" height="100"/>
            </div>
            <input id="imageInput" name="pic" type="file" onchange="loadImageFile();"/>
        </dd>
        <dd>
            <label>描述：</label>
            <textarea name="auctiondesc" >${auction.auctiondesc}</textarea>
        </dd>
        <dd>
            <input type="submit" value="保存"/>
            <input type="reset" value="取消" onclick="location='${pageContext.request.contextPath}/auction/findAuction'"/>
        </dd>
    </dl>
</form>
</div>
<script>
        $("#starttime").attr("value","<fmt:formatDate value='${auction.auctionstarttime}' pattern="yyyy-MM-dd'T'HH:mm"/>");
        $("#endtime").attr("value","<fmt:formatDate value='${auction.auctionendtime}' pattern="yyyy-MM-dd'T'HH:mm"/>");
</script>
</body>
</html>
