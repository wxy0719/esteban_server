<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script type="text/javascript" src="${basePath }/include/js/scroll/scroll.js"></script>
<link href="${basePath }/include/css/scroll/scroll.css" rel="stylesheet">
<script type="text/javascript">
var imgJson = "";
var flag = "${flag}";

window.onload = function(){
	//运行瀑布流主函数
	PBL('wrap','box');
	var index=1;
	//设置滚动加载
	window.onscroll = function(){
		//校验数据请求
		if(getCheck()&&"1"==flag){
			index++;
			getJsonStr(index);
			var data=imgJson.split(","); 
			var wrap = document.getElementById('wrap');
			alert(data);
			for(i in data){
				var path=data[i].split(":")[0];
				var txt=data[i].split(":")[1];
				//创建box 
				var box = document.createElement('div');
				box.className = 'box';
				wrap.appendChild(box);
				//创建info
				var info = document.createElement('div');
				info.className = 'info';
				box.appendChild(info);
				//创建pic
				var pic = document.createElement('div');
				pic.className = 'pic';
				info.appendChild(pic);
				//创建img
				var img = document.createElement('img');
				img.src = '${basePath}/'+path;
				img.style.height = 'auto'; 
				pic.appendChild(img);
				//创建title
				var title = document.createElement('div');
				title.className = 'title';
				info.appendChild(title);
				//创建a标记
				var a = document.createElement('a');
				a.innerHTML = txt;
				title.appendChild(a);
			}
			PBL('wrap','box');
		}
	}
}

function getJsonStr(index){
	$.ajax({
		cache: true,
		type: "POST",
		url:"${basePath}/front/villa/ifm/listVillaJSON",
		data: {"page":index},
		async: false,
	    error: function(request) {
	        alert("菜单加载失败,请刷新页面!");
	    }, 
	    success: function(data) {
	    	imgJson=data.split("||")[0];
	    	flag=data.split("||")[1];
	    }
	});
}
</script>
</head>
<body>  
	<div style="margin-top:20px;">
		<div id="wrap" style="width:1100px;">
			<c:forEach items="${list }" var="villa">
				<div class="box">
					<div class="info">
						<div class="pic"><img src="${basePath }/${villa.path}"></div>
						<div class="title"><a href="#">${villa.title }</a></div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>   
</body>  
</html>   