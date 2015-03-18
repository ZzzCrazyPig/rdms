<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div id="logo">
	<img src="./image/logo.png" height="70px" />
</div>
<div id="header-options">
	<a href="javascript:void(0)" class="easyui-splitbutton"
		data-options="menu: '#theme_menu',iconCls:'icon-refresh'" title="切换主题">切换主题</a>
	<a href="#" class="easyui-splitbutton"
		data-options="menu:'#sys_menu', iconCls:'icon-house'" title="退出">系统选项</a>
	<div id="sys_menu">
		<div id="exit" title="退出系统" data-options="iconCls:'icon-arrow-out'"
			onclick="exit_system()">退出系统</div>
	</div>
	<div id="theme_menu">
		<div data-options="iconCls:'icon-add'"
			onclick="change_theme('default')">Default</div>
		<div data-options="iconCls:'icon-add'"
			onclick="change_theme('bootstrap')">Bootstrap</div>
		<div data-options="iconCls:'icon-add'" onclick="change_theme('black')">Black</div>
		<div data-options="iconCls:'icon-add'"
			onclick="change_theme('cupertino')">Cupertino</div>
		<div data-options="iconCls:'icon-add'"
			onclick="change_theme('dark-hive')">Dark-hive</div>
		<div data-options="iconCls:'icon-add'"
			onclick="change_theme('pepper-grinder')">Papper-grinder</div>
		<div data-options="iconCls:'icon-add'" onclick="change_theme('sunny')">Sunny</div>
	</div>
</div>
<script type="text/javascript">
var exit_system = function() {
	window.location.href = './layout/backend/login.jsp';
};
</script>