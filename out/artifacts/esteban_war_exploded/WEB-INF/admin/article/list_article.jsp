<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglib.jsp" %>
<%@ include file="/inc/jsInclude.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
$(function(){
	$('#dataTable').datagrid({
		nowrap: true,
		//title: '文章信息',
		autoRowHeight: false, 
		singleSelect:true,
		striped: true,
		collapsible:true,
		idField: "f_id", 
		url:'${basePath}/admin/article/ifm/listArticleJSON',
		sortOrder: 'desc',
		remoteSort: false,
		frozenColumns:[[
            {field:'f_id',checkbox:true},
            {title:'标题',field:'f_title',width:220}
		]],
		columns:[[
			{field:'f_author',title:'作者',width:160},
			{field:'f_createTime',title:'创建时间',width:200,sortable:true,
				sorter:function(a,b){
					return (a>b?1:-1);
				}
			},{field:'f_status',title:'状态',width:120,sortable:true,
				formatter: function(value,row,index){
					if(value=="1"){
						return "<font style='color:green'>发布</font>";
					}else if(value=="2"){
						return "<font style='color:red'>编辑</font>";
					}
				}
			},
			{field:'f_isTop',title:'置顶',width:120,sortable:true,
				formatter: function(value,row,index){
					if(value=="1"){
						return "<font style='color:green'>置顶</font>";
					}
				}
			},
			{field:'f_name',title:'文章类别',width:80}
		]],
		pagination:true,
		rownumbers:true,
		toolbar:[{
			id:'btnadd',
			text:'添加文章',
			iconCls:'icon-add',
			handler:function(){
				$('#openWin').html("");
				$('#openWin').append("<iframe src='${basePath}/admin/article/ifm/toAdd' width='100%' height='99%' frameborder='no' border='0' marginwidth='0' marginheight='0' scrolling='no' allowtransparency='yes'></iframe>");
				$('#openWin').window('open');  
			}
		},{
			id:'btnedit',
			text:'修改文章',
			iconCls:'icon-edit',
			handler:function(){
				var row = $('#dataTable').datagrid('getSelected');
				if(!row){
					showMsg("请先选择一个文章");
					return;
				}
				$('#openWin').html("");
				$('#openWin').append("<iframe src='${basePath}/admin/article/ifm/toEdit?id="+row.f_id+"' width='100%' height='99%' frameborder='no' border='0' marginwidth='0' marginheight='0' scrolling='no' allowtransparency='yes'></iframe>");
				$('#openWin').window('open');  
			}
		},{
			id:'btnactive',
			text:'删除文章',
			iconCls:'icon-remove',
			handler:function(){
				var row = $('#dataTable').datagrid('getSelected');
				if(!row){
					showMsg("请先选择一个文章");
					return;
				}
				$.messager.confirm('确认', "确定要删除选中的文章?此操作不可撤销，请谨慎操作。", function (r) {
				     if (r) {
				    	 doDel(row.f_id);
				     }
				 });
			}
		},{
			id:'btnExcel',
			text:'Excel',
			iconCls:'icon-sum',
			handler:function(){
		    	doExcel();
			}
		}],
		onRowContextMenu: function (e, field) {
            e.preventDefault();
            $('#menu').menu('show', {
    			left: e.pageX,
    			top: e.pageY
    		});
        }
	});
	var p = $('#dataTable').datagrid('getPager');  
    $(p).pagination({  
        pageSize: 10,//每页显示的记录条数，默认为10  
        pageList: [5,10,15,20],//可以设置每页记录条数的列表  
        beforePageText: '第',//页数文本框前显示的汉字  
        afterPageText: '页    共 {pages} 页',  
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',  
        /*onBeforeRefresh:function(){ 
            $(this).pagination('loading'); 
            alert('before refresh'); 
            $(this).pagination('loaded'); 
        }*/ 
    });
});

function doQuery(){
	var name=$("#name").val();
	$('#dataTable').datagrid('load',{
		name: name
	});
}

function doDel(id){
	$.ajax({
		cache: true,
		type: "POST",
		url:"${basePath}/admin/article/ifm/del",
		data:{id:id},
		async: false,
	    error: function(request) {
	    	alert("系统错误,请刷新后重试");
	    },
	    success: function(data) { 
	    	showMsg(data);
	    	$('#dataTable').datagrid('reload');
	    }
	});
}

function doExcel(){
	$("#articleForm").attr("action", "${basePath}/admin/article/ifm/excel");
	$('#articleForm').submit();
}

</script> 
</head> 
<body>
<div id="openWin" class="easyui-window" title="文章管理" data-options="closed:true,modal:true,maximized:true" style="width:800px;height:500px;"></div>
<div id="menu" class="easyui-menu" style="width:150px;">
    <div id="m-refresh">新增</div>
    <div class="menu-sep"></div>
    <div id="m-refresh">修改</div>
    <div class="menu-sep"></div>
    <div id="m-closeall">停用文章</div>
    <div id="m-closeother">启用文章</div>
</div>
<div id="mainPanel" class="easyui-panel" style="padding:0 20 10 20;" border=false >
    <form id="articleForm" method="post" action="">
	    <div id="tb" style="padding:3px">
			<span>文章名:</span>
			<input class="easyui-validatebox textbox" type="text" name="name" id="name" data-options="" />
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="doQuery()">查询</a>
		</div>
		<hr style="border:0;border-bottom: 1px solid #d3d3d3; width: 100%;"> 
    </form>  
	<table class="easyui-datagrid" id="dataTable" data-options="method:'post'"></table>
</div>
</body>
</html>