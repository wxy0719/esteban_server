<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglib.jsp" %>
<%@ include file="/inc/jsInclude.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改后台用户</title>
<script type="text/javascript">

function doSumm(){
	var rights="";
	<c:forEach items="${listRightsType }" var="rightType" begin="0"> 
		$('input[name="rights_${rightType.typeCode}"]:checked').each(function() {
			rights+=$(this).val()+",";
        });
	</c:forEach>
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
	<form enctype="multipart/form-data" id="userEdit" action="${basePath}/admin/user/ifm/edit" method="post" target="hidden_frame" >
	<input type="hidden" name="id" value="${user.id }"/>
	<input type="hidden" name="rights" id="rights" />
	<div id="tabs" class="easyui-tabs" style="height:400px;" fit=true border=false data-options="" >
		<div title="角色信息" style="padding:10px;height:400px;" fit=true>
			<table width="90%">
				<tr>
					<td height="45px" width="150px">用户名:</td>
					<td><input name="name" value="${user.name }" style="width:280px"  data-options="required:true"/></td>
				</tr>
				<tr>
					<td height="45px" width="150px">用户昵称:</td>
					<td><input name="nickName" value="${user.nickName }" style="width:280px"  data-options="required:true"/></td>
				</tr>
				<tr>
					<td>用户描述:</td>
					<td><textarea name="des" rows="5" style="width:280px" data-options="required:true">${user.des }</textarea></td>
				</tr>
				<tr>
					<td height="45px" width="150px">邮箱:</td>
					<td><input name="email" value="${user.email }" style="width:280px"  data-options="required:true"/></td>
				</tr>
				<tr>
					<td height="45px" width="150px">QQ号:</td>
					<td><input name="qq" value="${user.qq }" style="width:280px"  data-options="required:true"/></td>
				</tr>
				<tr>
					<td height="45px" width="150px">年龄:</td>
					<td><input name="age" value="${user.age }" style="width:280px"  data-options="required:true"/></td>
				</tr>
				<tr>
					<td height="45px" width="150px">工作:</td>
					<td><input name="job" value="${user.job }" style="width:280px"  data-options="required:true"/></td>
				</tr>
				<tr>
					<td height="45px" width="150px">地址:</td>
					<td><input name="addr" value="${user.addr }" style="width:280px"  data-options="required:true"/></td>
				</tr>
				<tr>
					<td height="45px" width="150px">手机号:</td>
					<td><input name="mobile" value="${user.mobile }" style="width:280px"  data-options="required:true"/></td>
				</tr>
				<tr>
					<td height="45px" width="150px">性别:</td>
					<td>
						<input type="radio" value="1" <c:if test='${user.sex eq 1}'>checked="checked"</c:if> name="sex" data-options="required:true"/>男
						<input type="radio" value="2" <c:if test='${user.sex eq 2}'>checked="checked"</c:if> name="sex" data-options="required:true"/>女
					</td>
				</tr>
				<tr>
					<td height="45px">状态:</td>
					<td>
						<input type="radio" value="1" <c:if test='${user.status eq 1}'>checked="checked"</c:if> name="status" data-options="required:true"/>启用
						<input type="radio" value="2" <c:if test='${user.status eq 2}'>checked="checked"</c:if> name="status" data-options="required:true"/>停用
					</td>
				</tr> 
				<tr>
					<td height="45px" rowspan="2">用户头像</td>
					<td height="80px">
						<img alt="" width="80ox" height="80px" src="${basePath }\imgUpload\userImg/${user.img }">
					</td>
				</tr>
				<tr>
					<td>
						<input type="file" name="file"/>
					</td>
				</tr>
				<tr>
					<td colspan="2" height="45px">
						<button onclick="doSumm()">提交</button>
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
							<c:set var="flag" value="1"></c:set>
							<c:if test="${right.type eq rightType.id }">
								<c:forEach items="${user.listRights }" var="operRight">
									<c:if test="${right.id eq operRight }">
										<c:set var="flag" value="2"></c:set>
									</c:if>
								</c:forEach>
								<c:if test="${status!=4}">
									<td width="90px" height="40px"><input name="rights_${rightType.typeCode }" type="checkbox" <c:if test='${flag eq 2 }'>checked="checked"</c:if> value="${right.id }"/>${right.name }</td>
								</c:if>	
								<c:if test="${status==4}">
									</tr><tr>
									<td width="90px" height="40px"><input name="rights_${rightType.typeCode }" type="checkbox" <c:if test='${flag eq 2 }'>checked="checked"</c:if> value="${right.id }"/>${right.name }</td>
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
				<c:set var="flag" value="1"></c:set>
				<c:if test="${role.id eq user.role }">
					<c:set var="flag" value="2"></c:set>
				</c:if>
				<c:if test="${status.index%5==0&&status.index!=0}">
					</tr><tr>
					<td><input name="role" type="radio" <c:if test='${flag eq 2 }'>checked="checked"</c:if> value="${role.id }"/>${role.name }</td>
				</c:if> 
				<c:if test="${status.index%5!=0||status.index==0}">
					<td><input name="role" type="radio" <c:if test='${flag eq 2 }'>checked="checked"</c:if> value="${role.id }"/>${role.name }</td>
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