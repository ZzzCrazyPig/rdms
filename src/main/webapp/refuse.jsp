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
<jsp:include page="/fontend_inc.jsp"></jsp:include>
<link href="css/backend/login.css" rel="stylesheet" type="text/css" />
<title>拒绝请求</title>
</head>
<body>
<div class="container-fluid">
	<div class="row" style="margin-top:100px;">
		<div class="col-md-6 col-md-offset-3">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3>拒绝请求</h3>
				</div>
				<div class="panel panel-body">
					<p class="text-center" style="color:red;">非法请求路径</p>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>