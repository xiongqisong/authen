<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="/res/add" method="post">
	名称：<input type="text" name="name"><br/>
	类型：<input type="text" name="type"><br/>
	URL：<input type="text" name="url"><br/>
	父id：<input type="text" name="parentId"><br/>
	所有父id：<input type="text" name="parentIds"><br/>
	权限：<input type="text" name="permission"><br/>
	是否可用：<input type="text" name="available"><br/>
	<input type="submit" value="提交" />
</form>
</body>
</html>