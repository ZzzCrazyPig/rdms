<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<jsp:include page="/backend_inc.jsp"></jsp:include>
<link href="css/backend/home.css" rel="stylesheet" type="text/css" />
<title>RDMS后台管理主页</title>
<script type="text/javascript">

var tab_refresh = function() {
	var p = $("#tabs").tabs('getSelected');
	if(p != null) {
		p.panel('refresh');
	}
};

var init_footer = function() {
	$('#footer').prev().children().eq(0).css({
		'text-align' : 'center'
	});
};

var west_refresh = function() {
	$('#west').panel('refresh', './layout/backend/west.jsp');
};

$(document).ready(function(){
	
	init_theme();
	
	init_footer();

});
</script>
</head>
<body class="easyui-layout">
	<div id="header" data-options="region:'north', border:false, href:'./layout/backend/north.jsp'"></div>
	
	<div id="west" data-options="region:'west', title:'导航菜单', split:true, href:'./layout/backend/west.jsp', tools:[
				{
					iconCls:'icon-reload',
					handler:west_refresh
				}]" style="width: 200px; overflow:hidden;">
	</div>
	<div id="content" data-options="region:'center',split:true">
		<div id="tabs" class="easyui-tabs" data-options="fit:true,border:false,tools:'#tabs_tools'">
		</div>
		<div id="tabs_tools">
			<a href="javascript:void(0)" class="easyui-linkbutton" 
				data-options="iconCls:'icon-reload',plain:true" onclick="tab_refresh()">刷新</a>
		</div>
	</div>
	<div id="footer" data-options="region:'south',title:'版权所有: 华南师范大学计算机学院'">
		<!--  <div style="text-align: center;">
			<p>版权所有: 华南师范大学计算机学院</p>
		</div> -->
	</div>
</body>
</html>