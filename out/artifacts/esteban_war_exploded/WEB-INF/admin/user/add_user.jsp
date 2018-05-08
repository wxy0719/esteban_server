<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglib.jsp" %>
<%@ include file="/inc/jsInclude.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增用户</title>
<script type="text/javascript">

function doSub(){
	var rights="";
	<c:forEach items="${listRightsType }" var="rightType" begin="0"> 
		$('input[name="rights_${rightType.typeCode}"]:checked').each(function() {
			rights+=$(this).val()+",";
        });
	</c:forEach>
	$("#rights").val(rights);
}

function checkRights(name){
	var flag=$("#flag_"+name).val();
	if(flag==1){
		$('input[name="rights_'+name+'"]').each(function() {
			$(this).prop("checked",true);
	    });
		$("#flag_"+name).val("2");
	}else{
		$('input[name="rights_'+name+'"]').each(function() {
			$(this).prop("checked",false);
    	});
		$("#flag_"+name).val("1");
	}
}

</script>
</head>
<body>
	<form enctype="multipart/form-data" id="userAdd" action="${basePath}/admin/user/ifm/add" method="post" target="hidden_frame" >
	<input type="hidden" name="rights" id="rights" />
	<div id="tabs" class="easyui-tabs" style="height:400px;" fit=true border=false data-options="" >
		<div title="用户信息" style="padding:20px;height:400px;" fit=true>
			<table width="90%">
				<tr>
					<td height="45px" width="150px">用户名:</td>
					<td><input name="name" style="width:280px"  data-options="required:true"/></td>
				</tr>
				<tr>
					<td height="45px" width="150px">用户昵称:</td>
					<td><input name="nickName" style="width:280px"  data-options="required:true"/></td>
				</tr>
				<tr>
					<td>用户描述:</td>
					<td><textarea name="des" rows="5" style="width:280px" data-options="required:true"></textarea></td>
				</tr>
				<tr>
					<td height="45px" width="150px">邮箱:</td>
					<td><input name="email" style="width:280px"  data-options="required:true"/></td>
				</tr>
				<tr>
					<td height="45px" width="150px">QQ号:</td>
					<td><input name="qq" style="width:280px"  data-options="required:true"/></td>
				</tr>
				<tr>
					<td height="45px" width="150px">年龄:</td>
					<td><input name="age" style="width:280px"  data-options="required:true"/></td>
				</tr>
				<tr>
					<td height="45px" width="150px">工作:</td>
					<td><input name="job" style="width:280px"  data-options="required:true"/></td>
				</tr>
				<tr>
					<td height="45px" width="150px">地址:</td>
					<td><input name="addr" style="width:280px"  data-options="required:true"/></td>
				</tr>
				<tr>
					<td height="45px" width="150px">手机号:</td>
					<td><input name="mobile" style="width:280px"  data-options="required:true"/></td>
				</tr>
				<tr>
					<td height="45px" width="150px">性别:</td>
					<td>
						<input type="radio" value="1" checked="checked" name="sex" data-options="required:true"/>男
						<input type="radio" value="2" name="sex" data-options="required:true"/>女
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
					<td height="45px">用户头像</td>
					<td>
						<input type="file" name="file"/>
					</td>
				</tr>
				<tr>
					<td colspan="2" height="45px">
						<button onclick="doSub()">提交</button>
						<button>重置</button>
					</td>
				</tr>
			</table>	
		</div>
		<div title="授予权限" style="padding:10px">
		<table> 
			<c:forEach items="${listRightsType }" var="rightType" begin="0"> 
			<c:set var="status" value="0"></c:set>
			<tr>
				<td width="50px" valign="top" style="padding-top:16px"><a onclick="checkRights('${rightType.typeCode }')" href="javascript:void(0);">${rightType.name }</a><input type="hidden" id="flag_${rightType.typeCode }" value="1"/></td>
				<td valign="top">
					<table cellpadding="0" cellspacing="0">  
						<tr>
						<c:forEach items="${listRights }" var="right">
							<c:if test="${right.type eq rightType.id }">
								<c:if test="${status!=4}">
									<td width="90px" height="40px"><input name="rights_${rightType.typeCode }" type="checkbox" value="${right.id }"/>${right.name }</td>
								</c:if>	
								<c:if test="${status==4}">
									</tr><tr>
									<td width="90px" height="40px"><input name="rights_${rightType.typeCode }" type="checkbox" value="${right.id }"/>${right.name }</td>
								</c:if> 
								<c:if test="${status==4}">
									<c:set var="status" value="0"></c:set>
								</c:if>
								<c:if test="${status!=4}">
									<c:set var="status" value="${status+1 }"></c:set>
								</c:if>
							</c:if>
						</c:forEach>
						</tr>
					</table>
				</td>
			</tr>
			</c:forEach>
		</table>
		</div>
		<div title="角色设置" style="padding:30px">
		<table> 
			<tr>
			<c:forEach items="${listRole }" var="role" begin="0" varStatus="status"> 
				<c:if test="${status.index%5==0&&status.index!=0}">
					</tr><tr>
					<td><input name="role" type="radio" value="${role.id }"/>${role.name }</td>
				</c:if> 
				<c:if test="${status.index%5!=0||status.index==0}">
					<td><input name="role" type="radio" value="${role.id }"/>${role.name }</td>
				</c:if>				
			</c:forEach>
			</tr>
		</table>
		</div>
	</div>
	<iframe name='hidden_frame' id="hidden_frame" style='display:none'></iframe>
	</form>
</body>
</html>