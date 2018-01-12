<%@page import="org.apache.shiro.SecurityUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<script src="${pageContext.request.contextPath}/static/js/jquery.js"></script>
<form action="" method="post">
	<input type="hidden" name="appId" id="appId" value="12" />
	用户名：<input name="username" id="username" /><br/>
	密码：<input name="password" id="password" /><br/>
	<input type="button" value="登录" onclick="login('<%=SecurityUtils.getSubject().getSession(false).getId() %>')" />

	<script type="text/javascript">
		function login(sid){
			alert($('#username').val())
			$.ajax({
				url:"http://localhost:8080/authen_middleware_server/login",
				type:"POST",
				data:{
					"sid":sid,
					"username":$('#username').val(),
					"password":$('#password').val(),
					"appId":$('#appId').val()
				},
				dataType:"json",
				success:function(data){
					alert(data + "   " + data.code);
					if(data.code == 'suc'){
						alert("登陆成功");
						location.href="/app/hello";
					} 
				}
			}) 
		}
	</script>
</form>
</body>
</html>