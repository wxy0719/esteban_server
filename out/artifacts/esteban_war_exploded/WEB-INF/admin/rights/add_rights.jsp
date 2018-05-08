<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglib.jsp" %>
<%@ include file="/inc/jsInclude.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增权限</title>
<script type="text/javascript">
function doSub(){
	$.ajax({
		cache: true,
		type: "POST",
		url:"${basePath}/admin/rights/ifm/add",
		data:$('#rightsAdd').serialize(),
		async: false,
	    error: function(request) {
	    	alert("系统错误,请刷新后重试");
	    },
	    success: function(data) {
	    	showMsgParent(data);
	    	window.parent.$('#openWin').window('close');
	    	window.parent.$('#dataTable').datagrid('reload');
	    }
	});
}

</script>
</head>
<body>
	<form id="rightsAdd" action="" method="post">
	<div id="tabs" class="easyui-tabs" style="height:400px;" fit=true border=false data-options="" >
		<div title="权限信息" style="padding:30px;height:400px;" fit=true>
			<table width="90%">
				<tr>
					<td height="45px" width="150px">权限名:</td>
					<td><input name="name" id="roleName" data-options="required:true"/></td>
				</tr>
				<tr>
					<td height="45px" width="150px">权限编码:</td>
					<td><input name="sortName" id="sortName" data-options="required:true"/></td>
				</tr>
				<tr>
					<td>权限描述:</td>
					<td><textarea name="des" id="roleDes" rows="5" style="width:280px" data-options="required:true"></textarea></td>
				</tr>
				<tr>
					<td height="40px">权限类别:</td>
					<td>
						<select name="type">
							<c:forEach items="${listRightsType }" var="type">
								<option value="${type.id }">${type.name }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td height="45px">状态:</td>
					<td>
						<input type="radio" value="1" checked="checked" name="status" data-options="required:true"/>启用
						<input type="radio" value="2" name="status" data-options="required:true"/>停用
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<button onclick="doSub()">提交</button>
						<button>重置</button>
					</td>
				</tr>
			</table>	
		</div>
	</div>
	</form>
</body>
</html>