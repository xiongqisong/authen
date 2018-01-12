<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	hello app
	<br />
	<shiro:guest>
		<a href="${pageContext.request.contextPath }/login/?backUrl=/app/hello">点击登录</a>
	</shiro:guest>

	<shiro:authenticated>
		欢迎<shiro:principal />登录<br /><a href="/authen_middleware_server/logout">退出</a>
		<shiro:hasRole name="admin">
			您拥有admin角色
		</shiro:hasRole>
		<shiro:hasRole name="car_admin">
			您拥有car_admin角色
		</shiro:hasRole>
		<shiro:lacksRole name="role1">
			您没有role1角色
		</shiro:lacksRole>
		<a href="${pageContext.request.contextPath }/byebye">点击试试看</a>
		
		<iframe style="width: 300px;height:800px" src="http://localhost:8080/authen_middleware_server?sid=${sid }"></iframe>
	</shiro:authenticated>
</body>
</html>