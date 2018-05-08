<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/taglib.jsp" %>
<html>
<head>
<title>用户登陆</title>
</head>
<body>
	<div class="container" style="text-align:center;margin-top:20px;">
		<form action="${basePath}/login" method="post">
		<div>
			<label>登陆帐号：</label>
			<input type="text" name="userId" value="">
		</div>
	
		<div>
			<label>登陆密码：</label>
			<input type="password" name="passwd" value="">
		</div>
		
		<div>
			<label>验证码：</label>
			<input type="password" name="passwd" value="">
		</div>
		
		<div>
			<input type="submit" value=" 登陆 "/>
		</div>
		</form>
	</div>

</body>
</html>