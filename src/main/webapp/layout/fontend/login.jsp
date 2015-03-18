<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<jsp:include page="/fontend_inc.jsp"></jsp:include>
<title>RDMS登录界面</title>
</head>
<body style="background-color: #f5f5f5;">
	<div class="container-fluid">
		<div class="row" style="margin-top: 50px;">
			<div class="col-md-offset-4 col-md-4">
				<div class="panel panel-primary">
					<div class="panel-heading text-center">
						<h4>RDMS Login In</h4>
					</div>
					<div class="panel-body">
						<div id="something" class="text-center"
							style="border: 1px solid #ddd; margin-bottom: 15px;">
							<img src="./image/fontend_login_logo.jpg" width="100%" />
						</div>
						<div class="text-center"><p id="tip" style="color: red;"></p></div>
						<form class="form-horizontal">
							<div class="form-group">
								<label for="account" class="col-md-3 control-label">账号:</label>
								<div class="col-md-9">
									<input type="text" id="account" name="account"
										class="form-control" placeholder="请输入账号" />
								</div>
							</div>
							<div class="form-group">
								<label for="pwd" class="col-md-3 control-label">密码:</label>
								<div class="col-md-9">
									<input type="password" id="pwd" name="pwd" class="form-control"
										placeholder="请输入密码" />
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-12">
									<button type="button" id="loginBtn" class="btn btn-lg btn-block btn-success">Login
										In</button>
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-12">
									<button type="reset" class="btn btn-lg btn-block btn-default">Reset
										Input</button>
								</div>
							</div>
						</form>
						<div class="text-center">
							<a href="./layout/backend/login.jsp">我要到后台看看</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
$(document).ready(function(){
	// 初始化隐藏tip信息
	hideTip();
	$('#loginBtn').click(login_event);
});

$(document).keydown(function(event){
	if(event.keyCode == 13) {
		login_event();
	}
});

var login_event = function() {
	hideTip();
	var account = $.trim($('#account').val());
	var pwd = $.trim($('#pwd').val());
	if(validateInput(account, pwd)) {
		//debug("可以进行登录操作");
		hideTip();
		var appModel = new AppModel();
		var attr = {
			account : account,
			pwd : pwd
		};
		appModel.setAttr(attr);
		$.post('./fontend/login/loginAction_login', appModel, login_callback);
	}
};

var login_callback = function(data) {
	if(data.success) {
		// 登录成功
		window.location.href = './layout/fontend/home.jsp';
	} else {
		showTip("登录失败,账号或密码错误!");
	}
};

// 验证输入信息
var validateInput = function(account, pwd) {
	//debug("account =" + account + ".");
	//debug("pwd =" + pwd + ".");
	if(account.length == 0 || pwd.length == 0) {
		showTip("账号或者密码不能为空!");
		return false;
	}
	return true;
};

var hideTip = function() {
	$('#tip').hide();
};

var showTip = function(msg) {
	$('#tip').html(msg).show();
};

var debug = function(obj) {
	console.info(obj);
};
</script>
</html>