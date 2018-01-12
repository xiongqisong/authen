<%@page import="org.apache.shiro.SecurityUtils"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>Shiro综合案例</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/layout-default-latest.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/JQuery zTree v3.5.15/css/zTreeStyle/zTreeStyle.css">
</head>
<body>

<!-- <iframe name="content" class="ui-layout-center" src="/jsp/welcome.jsp" frameborder="0" scrolling="auto"></iframe> -->
<%-- <div class="ui-layout-north">欢迎[<shiro:principal/>]学习Shiro综合案例，<a href="${pageContext.request.contextPath}/logout">退出</a></div> --%>
<!-- <div class="ui-layout-south">
    获取源码：<a href="https://github.com/zhangkaitao/shiro-example" target="_blank">https://github.com/zhangkaitao/shiro-example</a>
</div> -->
<div class="ui-layout-north"><a href="${pageContext.request.contextPath}/logout">退出</a></div>
<div class="ui-layout-west">功能菜单<br/>
    <ul id="menu" class="ztree"></ul>
</div>


<script src="${pageContext.request.contextPath}/static/js/jquery-1.11.0.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/jquery.layout-latest.min.js"></script>
<script src="${pageContext.request.contextPath}/static/JQuery zTree v3.5.15/js/jquery.ztree.all-3.5.min.js"></script>
<script>
    $(function () {
      /*   $(document).ready(function () {
            $('body').layout({ applyDemoStyles: true });
        }); */
        
        var setting = {
       		data: {
       			simpleData: {
       				enable: true
       			}
       		},
       		callback: {
//        			onClick: function(event, treeId, treeNode){
//        				parent.frames['content'].location.href = "${pageContext.request.contextPath}/organization/"+treeNode.id+"/maintain";
//        			}
       		}
        };
        var zNodes = [
			<c:forEach items="${layers }" var="layer">
				<c:forEach items="${layer.value }" var="features">
					<c:forEach items="${features.value }" var="feature">
						{
							id: "${feature.id}",
							pId: "${features.key}",
							name: "${feature.name}",
							open: true,
							url: "${feature.getRealUrl(app)}"
						},
					</c:forEach>
				</c:forEach>
			</c:forEach>          
		];
        $(document).ready(function(){
        	$.fn.zTree.init($("#menu"), setting, zNodes);
        });
    });
    
    <%-- <c:forEach items="${menus}" var="m">
		{ 
			id: "${m.id}",
		 	pId: "${m.parentId}",
		 	name: "${m.name}",
		 	open: true,
		 	url: "${m.getRealUrl(app)}?sid=<%=SecurityUtils.getSubject().getSession(false).getId()%>"
		},
	</c:forEach> --%>
</script>
</body>
</html>