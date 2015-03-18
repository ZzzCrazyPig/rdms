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
<title>index</title>
</head>
<body>
<div>
	<a id="insert" href="javascript:void(0)">testInsert</a>
	<a id="query" href="javascript:void(0)">testQuery</a>
	<a id="queryByPage" href="javascript:void(0)">testQueryByPage</a>
</div>
</body>
<script type="text/javascript">

var test_insert = function(){
	var model = new EmployeeModel();
	model.name = "ZzzCrazyPig";
	model.usr = "d6619309";
	model.pwd = "woshixin";
	var attr = JSON.stringify(model);
	$.get('./comm/employeeAction_insert', {
		attr : attr
	}, function(data){
		
	});
};

var test_query = function() {
	var model = new EmployeeModel();
	model.name = "ZzzCrazyPig";
	var appModel = new AppModel();
	appModel.setAttr(model);
	$.get('./comm/employeeAction_query', JSON.parse(JSON.stringify(appModel)), function(data){
	});
};

var test_queryByPage = function() {
	var appModel = new AppModel();
	var model = new EmployeeModel();
	model.name = "CrazyPig";
	appModel.offset = 0;
	appModel.limit = 5;
	appModel.order = "desc";
	appModel.orderBy = "id";
	appModel.setAttr(model);
	$.get('./comm/employeeAction_queryByPage', JSON.parse(JSON.stringify(appModel)), function(data){
		
	});
};

	$(document).ready(function(){
		$("#insert").click(function(){
			test_insert();
		});
		$("#query").click(function(){
			test_query();
		});
		$("#queryByPage").click(function(){
			test_queryByPage();
		});
	});
</script>
</html>