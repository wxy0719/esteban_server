<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>${wenku.title }</title>
<style type="text/css" media="screen"> 
	html, body	{ height:100%; }
	body { margin:0; padding:0; overflow:auto; }   
	#flashContent { display:none;width:900px; }
</style> 
<script type="text/javascript" src="${basePath }/include/js/flexpaper/flexpaper_flash.js"></script>
</head>
<body>  
	<div style="margin-top:20px;">  
		<div style="float: left;width: 800px;" id="dl"> 
			<div style="width: 100%;" id="dt">
				<div align="left" style="width: 100%;padding-left:12px;"><strong style="font-size:25px;">${wenku.title }</strong></div>
				<div align="left" style="width: 100%;padding-left:12px;font-size:12px;color:#999;margin-top: 20px">${wenku.createTime }</div>
				<div align="left" style="width: 100%;padding-left:12px;font-size:12px;color:#999;margin-top: 3px">来源: ${wenku.author }</div>
				<div class="page-header" style="margin-top:5px;"></div>  
			</div> 
			<div style="width: 100%;padding-top: 10px;padding-left:12px;" align="center" id="paper">
		        <a id="viewerPlaceHolder" style="width:660px;height:480px;display:block"></a>
		        <script type="text/javascript">
					var fp = new FlexPaperViewer(	 
							 '${basePath }/include/js/flexpaper/FlexPaperViewer',
							 'viewerPlaceHolder', { config : {
							 SwfFile : escape('${basePath}/${wenku.docPath }'),
							 Scale : 0.6, 
							 ZoomTransition : 'easeOut',
							 ZoomTime : 0.5,
							 ZoomInterval : 0.2,
							 FitPageOnLoad : true,
							 FitWidthOnLoad : true,
							 PrintEnabled : true,
							 FullScreenAsMaxWindow : false,
							 ProgressiveLoading : false,
							 MinZoomSize : 0.2,
							 MaxZoomSize : 5,
							 SearchMatchAll : false,
							 InitViewMode : 'Portrait',
							 ViewModeToolsVisible : true,
							 ZoomToolsVisible : true,
							 NavToolsVisible : true,
							 CursorToolsVisible : true,
							 SearchToolsVisible : true,
	  						 localeChain: 'zh_CN'
							 }});
		        </script>
			</div> 
		</div>  
		<div style="float: right;width: 220px;" id="dr">我是小广告</div>
	</div>   
</body>  
</html>   