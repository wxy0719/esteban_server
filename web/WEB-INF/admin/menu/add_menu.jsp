<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglib.jsp" %>
<script type="text/javascript">
function doSub(){
	$.ajax({
		cache: true,
		type: "POST",
		url:"${basePath}/admin/tree/ifm/add",
		data:$('#treeAdd').serialize(),
		async: false,
	    error: function(request) {
	    	alert("系统错误,请刷新后重试");
	    },
	    success: function(data) {
	    	showMsgParent(data);
	    	window.parent.$('#openWin').window('close');
	    	window.parent.$('#dataTable').datagrid('reload');
	    	window.parent.$('#openWin').html("");
	    }
	});
}
</script>
<form id="treeAdd">
<input type="hidden" name="pid" value="${pid }">
<table width="100%" cellpadding="0" cellspacing="0"> 
	<tr> 
		<td height="45px" width="100px">菜单名称:</td>
		<td><input name="name" id="" data-options="required:true" style="width:280px"/></td>
	</tr>
	<tr>
		<td height="45px">同级排序:</td>
		<td>
			<input name="order" id="" data-options="required:true" style="width:280px"/>
		</td>
	</tr>
	<tr>
		<td>程序路径:</td>
		<td><textarea name="url" id="" rows="5" style="width:280px" data-options="required:true"></textarea></td>
	</tr>
	<tr>
		<td height="55px">节点状态:</td>
		<td>
			<input type="radio" value="1" checked="checked" name="status" data-options="required:true"/>启用
			<input type="radio" value="2" name="status" data-options="required:true"/>停用
		</td>
	</tr>
	<tr>
		<td height="75px" valign="top">所需权限:</td>
		<td valign="top">
			<div style="height:155px; overflow-y:auto"> 
				<table cellpadding="0" cellspacing="0"> 
					<tr>
					<c:forEach items="${listRights }" var="right" begin="0" varStatus="status"> 
						<c:if test="${status.index%4==0&&status.index!=0}">
							</tr><tr>
							<td height="35px"><input name="rightNo" type="radio" value="${right.id }"/>${right.name }</td>
						</c:if>  
						<c:if test="${status.index%4!=0||status.index==0}">
							<td height="35px"><input name="rightNo" type="radio" value="${right.id }"/>${right.name }</td>
						</c:if>				
					</c:forEach>
					</tr>
				</table>  
			</div>
		</td>
	</tr>
	<tr>
		<td colspan="2" height="55px">
			<button onclick="doSub()">提交</button>
			<button>重置</button>
		</td>
	</tr>
</table>
</form>