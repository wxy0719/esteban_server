<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/taglib.jsp" %>
<html lang="en">
<head>
<title>河马山庄</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
</head>

<body>
	<div class="container" style="margin-top:-10px">    
	<DIV class="gg_full wrapfix"> 
		<DIV class=gg_fbtn> 
			<A style="DISPLAY: none" class=gg_freplay title=重播  href="#"></A>
			<A class=gg_fclose title=关闭  href="#"></A>
		</DIV>
		<DIV class=gg_fcon></DIV>
	</DIV>
	
	<!-- 滚动图片区域 --> 
	<div class="page-header" ></div>
    <div class="focus">
	   <div class="focus_body">
		    <!--焦点图 begin -->  
			<div class="scroll">
			      <a href="javascript:void(0);" class="arr_left" id="FS_arr_left_01">左移动</a>
			      <a href="javascript:void(0);" class="arr_right" id="FS_arr_right_01">右移动</a>
			      <div class="scroll_cont" id="FS_Cont_01">
			      	 <c:forEach items="${ads }" var="ad" >
			        	 <div class="box">
					      	<a href="${ad.url }" target="_blank"><img alt="${ad.des }" src="${ad.imgPath }" /></a>
					     </div>
			      	 </c:forEach>
			      </div>
			      <div id="FS_numList_01" class="numList"></div>
			      <div class="scroll_txt">
			      		<c:forEach items="${ads }" var="ad" varStatus="ind"> 
			      			<div id="txt0${ind.index+1 }" class="scroll_info" <c:if test="${ind.index!=0 }">style="display:none"</c:if>>
					            <div class="txtbg"></div>
					            <div class="txtcontent">
					        	<h2><a href="${ad.url }" target="_blank">${ad.title }</a></h2>
					            <p>${ad.des }</p>
					            <p class="des">${ad.imgDetail }<a href="${ad.url }" target="_blank">[详细]</a></p>
					            <div class="btn"><a href="${ad.url }" target="_blank">播放</a></div>
					           </div>
					        </div>
			      		</c:forEach>
			      </div>
		     </div>
	   	  </div>
	   </div>
	   <script type="text/javascript" src="${basePath }/include/js/imgScoll.js"></script>
	   <!-- 文字新闻区域 -->
	   <div class="page-header newsDiv-Left">
	   		<h1 class="titleContent"><span>江湖</span>大事件</h1>
	   </div>
	   <div class="page-header newsDiv-right">
	   		<h1 class="titleContent"><span>庄内</span>公告</h1>
	   </div>
	   <div class="newsDiv-Left">
	   		<table class="table table-bordered table-striped table-hover" style="width: 540px">
			    <tbody>
			    <c:forEach items="${JHDSJ }" var="dsj">
			      <tr>
			        <td>
			        	<c:if test="${fn:length(dsj.title)>'25'}">  
			        		<a href="front/article/${dsj.id }" target="_blank">${fn:substring(dsj.title,0,20)}...</a>
			            </c:if>  
			            <c:if test="${fn:length(dsj.title)<='25'}">  
			                <a href="front/article/${dsj.id }" target="_blank">${dsj.title }</a>
			            </c:if>
			        </td>
			        <td width="150px">${dsj.publictime }</td>
			      </tr>
			    </c:forEach>
			    </tbody>
			  </table>
	   </div>
	   <div class="newsDiv-right">
	   		<table class="table table-bordered table-striped table-hover" >
			    <tbody>
			      <c:forEach items="${ZNGG }" var="gg">
			      <tr>
			        <td>
			        	<c:if test="${fn:length(gg.title)>'25'}">  
			        		<a href="front/article/${gg.id }" target="_blank">${fn:substring(gg.title,0,20)}...</a>
			            </c:if>  
			            <c:if test="${fn:length(gg.title)<='25'}">  
			                <a href="front/article/${gg.id }" target="_blank">${gg.title }</a>
			            </c:if>
			        <td width="150px">${gg.publictime }</td>
			      </tr>
			      </c:forEach>
			    </tbody>
			  </table>
	   </div>	
  </div>
  <div class="container" style="margin-top:-20px">
  	   <div class="page-header">
	   		<h1 class="titleContent"><span>天下</span>奇闻</h1>
	   </div>
	   	<table class="table table-bordered table-striped table-hover">
		    <tbody>
		      <c:forEach items="${TXQW }" var="qw">
			      <tr>
			        <td>
			        	<c:if test="${fn:length(qw.title)>'100'}">  
			        		<a href="front/article/${qw.id }" target="_blank">${fn:substring(qw.title,0,100)}...</a>
			            </c:if>  
			            <c:if test="${fn:length(qw.title)<='100'}">  
			                <a href="front/article/${qw.id }" target="_blank">${qw.title }</a>
			            </c:if>
			        <td width="150px">${qw.publictime }</td>
			      </tr>
		      </c:forEach>
		    </tbody>
		  </table>
  </div>
  <div class="container"> 
  	<table width="100%">
  		<tr>
  			<td width="19%"><a class="btn btn-info btn-large" href="front/wenku/listWenKu" target="_blank" style="width:65%">藏经阁</a></td>
  			<td width="19%"><a class="btn btn-large" href="#" style="width:65%">山庄留言</a></td>
  			<td width="19%"><a class="btn btn-primary btn-large" href="#" style="width:65%">疑难解答</a></td>
  			<td width="19%"><a class="btn btn-inverse  btn-large" href="front/villa/listVilla" target="_blank" style="width:65%">山庄介绍</a></td>
  			<td width="19%"><a class="btn btn-success btn-large" href="#" style="width:65%">招聘管家</a></td>
  		</tr>
  	</table>
  </div>
</body>
</html>