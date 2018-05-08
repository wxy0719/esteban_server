<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
</head>
<body>  
	<div style="margin-top:20px;">  
		<div style="float: left;width: 780px;"> 
			<div align="left" style="width: 100%;padding-left:12px;"><strong style="font-size:25px;">庄内藏书</strong></div>
			<div class="page-header" style="margin-top:5px;"></div>  
			<div style="width: 100%;padding-top: 10px;padding-left:12px;" align="center">
				<table width="100%"> 
					<c:forEach items="${list }" var="wenku">
					<tr>  
						<td width="600px" align="left" style="font-size:14px;border-bottom:1px dotted #eee;padding-left:15px" height="30px">
							<a href="${basePath }/front/wenku/${wenku.id }" target="_blank">${wenku.title }</a>
						</td>
						<td>${wenku.createTime }</td>
					</tr>
					</c:forEach>
				</table>
			</div> 
		</div>  
		<div style="float: right;width: 250px;border-left:1px #eee solid;">我是小广告</div>
	</div>   
</body>  
</html>   