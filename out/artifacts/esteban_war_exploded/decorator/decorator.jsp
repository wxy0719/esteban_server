<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>河马山庄</title>
<link href="${basePath }/include/css/bootstrap.min.css" rel="stylesheet">
<link href="${basePath }/include/css/scrollImg.css" rel="stylesheet">
<link href="${basePath }/include/css/zzsc.css" rel="stylesheet">
<link href="${basePath }/include/css/ad.css" rel="stylesheet">
<link href="${basePath }/include/css/jquery-ui.css" rel="stylesheet">
<script src="${basePath }/include/js/jquery-1.10.2.js"></script>
<script src="${basePath }/include/js/jquery-ui.js"></script>
<script src="${basePath }/include/js/jquery.validate.js"></script>
<script type="text/javascript" src="${basePath }/include/js/ad.js"></script>
<script type="text/javascript" src="${basePath }/include/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="${basePath }/include/js/zzsc.js"></script>
<sitemesh:write property='head' />
</head>
<body id="top">
	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">     
				<p class="brand">河马山庄</p>
				<div class="nav-collapse collapse" id="main-menu">
					<ul class="nav">
						<li><a class="dropdown-toggle" href="${basePath }">庄门</a></li>
						<li><a class="dropdown-toggle" href="#">前厅</a></li>
						<li class="dropdown"><a class="dropdown-toggle" href="#">大堂</a></li>
						<li class="dropdown"><a class="dropdown-toggle" href="${basePath }/admin">后庄</a></li>
					</ul>
					<form class="navbar-search pull-left" action="">
		                <input type="text" class="search-query span2" placeholder="Search">
		            </form>
					<ul class="nav pull-right">
						<li class="dropdown">
							<a href="#" class="dropdown-toggle">天字一号 <b class="caret"></b></a>
							<ul class="dropdown-menu">
								<li><a href="#">Action</a></li>
								<li><a href="#">Another action</a></li>
								<li><a href="#">Something else here</a></li>
								<li class="divider"></li>
								<li><a href="#">Separated link</a></li>
							</ul>
						</li>
						<li class="divider-vertical"></li>
						<li><a href="#">退出</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="container contentflux">   
		<sitemesh:write property="body"/>	
	</div>
	<div class="container copyright">
  	<div class="page-header"></div>
  		<table>
  			<tr>
  				<td>Copyright © 1991-2014 ESTEBAN Corporation, All Rights Reserved</td>
  			</tr>
  			<tr>
  				<td>河马大王 版权所有</td>
  			</tr>
  		</table>
  </div>
  <!-- 公告栏内容 -->
	<div class="float_layer" id="miaov_float_layer"> 
	  <h2><b>山庄公告</b>&nbsp;&nbsp;&nbsp;<b id="blink" style="color:red;">new</b> <a id="btn_min" class="min"></a> <a id="btn_close" class="close"></a> </h2>
	    <div class="wrap2">  
		    <marquee direction="up" scrollAmount="1" onmouseover="this.stop()"  onmouseout="this.start()">
		    	谁，执我之手，敛我半世癫狂；<br/>
				谁，吻我之眸，遮我半世流离；<br/> 
				谁，抚我之面，慰我半世哀伤；<br/>
				谁，携我之心，融我半世冰霜；<br/>
				谁，扶我之肩，驱我一世沉寂。<br/>
				谁，唤我之心，掩我一生凌轹。<br/>
				谁，弃我而去，留我一世独殇；<br/> 
				谁，可明我意，使我此生无憾；<br/>
				谁，可助我臂，纵横万载无双；<br/>
				谁，可倾我心，寸土恰似虚弥； <br/>
				谁，可葬吾怆，笑天地虚妄，吾心狂。<br/>
				伊，覆我之唇，祛我前世流离； <br/>
				伊，揽我之怀，除我前世轻浮。 <br/>
				执子之手，陪你痴狂千生；<br/>
				深吻子眸，伴你万世轮回。 <br/>
				执子之手，共你一世风霜；<br/>
				吻子之眸，赠你一世深情。   <br/>
				我， 牵尔玉手， 收你此生所有；<br/>
				我， 抚尔秀颈， 挡你此生风雨。  <br/>
				予，挽子青丝，挽子一世情思；<br/>
				予，执子之手，共赴一世情长；<br/>
				曾，以父之名，免你一生哀愁；<br/>
				曾，怜子之情，祝你一生平安!<br/>
			</marquee>
	    </div>   
	</div> 
  <!-- 页面拖拽后，出现返回顶部按钮 -->
	<div class="scrollToTop" id="scroll" style="display:none;">
		回到顶部
	</div>
<script type=text/javascript src="${basePath }/include/js/qpxl.js"></script>
<script type="text/javascript">
	$(function(){
		showScroll();
		function showScroll(){ 
			$(window).scroll( function() { 
				var scrollValue=$(window).scrollTop();
				scrollValue > 100 ? $('div[class=scrollToTop]').fadeIn():$('div[class=scrollToTop]').fadeOut();
			} );	
			$('#scroll').click(function(){
				$("html,body").animate({scrollTop:0},200);	
			});	
		}
	});
	
	var lightingNum=1;
	function changeColor(){ 
		var color="#f00|#0f0|#00f|#880|#808|#088|yellow|green|blue|gray";
		color=color.split("|"); 
		if(lightingNum%2==0){
			$("#blink").text("new~");
		}else{
			$("#blink").text("new");
		}
		$("#blink").css("color",color[parseInt(Math.random() * color.length)]);
		lightingNum++;
	} 
	setInterval("changeColor()",800); 
</script>
<jsp:include page="/inc/ShowMsg.jsp" flush="true"></jsp:include>	
</body>
</html>
