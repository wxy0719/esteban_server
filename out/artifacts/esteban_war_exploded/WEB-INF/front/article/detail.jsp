<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>${article.title }</title>
</head>
<body>  
	<div style="margin-top:20px;">
		<div style="float: left;width: 800px;"> 
			<div align="left" style="width: 100%;padding-left:12px;"><strong style="font-size:25px;">${article.title }</strong></div>
			<div align="left" style="width: 100%;padding-left:12px;font-size:12px;color:#999;margin-top: 20px">${article.publictime }</div>
			<div align="left" style="width: 100%;padding-left:12px;font-size:12px;color:#999;margin-top: 3px">来源: ${article.author }</div>
			<div class="page-header" style="margin-top:5px;"></div>  
			<div style="width: 100%;padding-top: 10px;padding-left:12px;">
				${article.content }
			</div> 
			<div></div>  
			<div></div>
		</div>  
		<div style="float: right;width: 220px;border-left:1px #eee solid;">
			
		</div>
	</div>   
</body>  
</html>   