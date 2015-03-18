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
<link href="css/backend/login.css" rel="stylesheet" type="text/css" />
<title>RDMS后台管理登录界面</title>
</head>
<body style="background-color: #eee;">
<div style="margin-top:100px; width:100%;">
	<div style="margin: 0 auto; width:450px; height:250px;">
		<div class="easyui-panel" title="登录窗口" style="text-align:center; width:450px; height:250px; padding:20px;">
			<img src="./image/logo.png" width="250px" />
			<div>
				<form id="loginForm">
					<table style="width:100%;" cellpadding="5">
						<tr>
							<td style="width:33%; text-align:right;">账号: </td>
							<td style="width:66%; text-align:left;"><input class="easyui-textbox" type="text" name="account"
									data-options="required:true"></td>
						</tr>
						<tr>
							<td style="width:33%; text-align:right;">密码: </td>
							<td style="width:66%; text-align:left;"><input class="easyui-textbox" type="password" name="pwd"
									data-options="required:true"></td>
						</tr>
					</table>
				</form>
			</div>
			<div style="text-align:center;padding:5px">
            	<a id="loginBtn" href="javascript:void(0)" class="easyui-linkbutton">登录</a>
            	<a href="javascript:void(0)" class="easyui-linkbutton">重置</a>
            	<a id="registBtn" href="javascript:void(0)" class="easyui-linkbutton">注册</a>
            	<a href="./layout/fontend/login.jsp">到前台去看看</a>
        	</div>
		</div>
	</div>
</div>
</body>
<script type="text/javascript">

var user_add_callback = function(data){
	appMessager.show('温馨提示', data.msg, 2000);
};

var user_login_callback = function(data) {
	var success = data.success;
	if(success) {
		window.location.href = './layout/backend/home.jsp';
	} else {
		appMessager.show('温馨提示', data.msg, 2000);
	}
};

	$(document).ready(function(){
		init_theme();
		
		$('#loginBtn').click(function(){
			var attr = serializeForm('loginForm');
			var appModel = new AppModel();
			appModel.setAttr(attr);
			$.post('./backend/sys/sysLoginAction_login', appModel, user_login_callback);
		});
		
		$('#registBtn').click(function(){
			var attr = serializeForm('loginForm');
			var appModel = new AppModel();
			appModel.setAttr(attr);
			$.post('./backend/sys/userAction_insert', appModel, user_add_callback);
		});
	});
</script>
</html>