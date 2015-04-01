<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<nav class="navbar navbar-primary" role="navigation">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="javascript:void(0);">RDMS 研发团队管理系统</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
     
      <ul class="nav navbar-nav navbar-right">
		<li><a id="login_user" href="javascript:void(0);" data="${sessionScope.emp.id}"><span class="glyphicon glyphicon-user"></span> ${sessionScope.emp.name}</a></li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><span class="glyphicon glyphicon-cog"></span> 系统选项 <span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu">
            <li><a id="logoutBtn" href="javascript:void(0);"><span class="glyphicon glyphicon-log-out"></span> 注销</a></li>
            <li role="presentation" class="divider"></li>
            <li><a id="changePwdBtn" href="javascript:void(0);"><span class="icon-key"></span> 修改密码</a></li>
          </ul>
        </li>
      </ul>
    </div><!-- /.navbar-collapse -->
    
    <div class="modal fade" id="changePwdModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel2" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title" id="modalLabel2">修改登陆密码</h3>
				</div>
				<div class="modal-body">
					<div class="container-fluid">
						<form id="changePwdForm" class="form-horizontal">
							<div class="form-group">
								<input type="hidden" id="eid" name="eid" value="${sessionScope.emp.id }" />
							</div>
							<div class="form-group">
								<label for="oldPwd" class="col-md-3 control-label">原始密码</label>
								<div class="col-md-9">
									<input type="password" id="oldPwd" name="oldPwd" class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label for="newPwd" class="col-md-3 control-label">新密码</label>
								<div class="col-md-9">
									<input type="password" id="newPwd" name="newPwd" class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label for="confirmPwd" class="col-md-3 control-label">确认密码</label>
								<div class="col-md-9">
									<input type="password" id="confirmPwd" name="confirmPwd" class="form-control" />
								</div>
							</div>
						</form>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" onclick="changePwd()">保存</button>
				</div>
			</div>
		</div>
	</div> <!-- end of modal -->
    
  </div><!-- /.container-fluid -->
</nav>
<script>

var changePwd = function() {
	var attr = serializeForm('changePwdForm');
	var oldPwd = attr.oldPwd;
	var newPwd = attr.newPwd;
	var confirmPwd = attr.confirmPwd;
	if($.trim(oldPwd).length == 0 || $.trim(newPwd).length == 0 || $.trim(confirmPwd).length == 0) {
		alert('所提供的字段不能为空');
		return ;
	}
	if(newPwd != confirmPwd) {
		alert('新密码与确认密码不一致');
		return ;
	}
	var appModel = new AppModel();
	appModel.setAttr(attr);
	$.post('./fontend/comm/employeeAction_changePwd', appModel, changePwd_callback);
};

var changePwd_callback = function(data) {
	if(data.success) {
		alert('修改密码成功');
		$('#changePwdModal').modal('hide');
	}
};

$(document).ready(function(){
	$('#logoutBtn').click(function(){
		$.get('./fontend/comm/exitAction_exit',null, logout_callback);
	});
	$('#changePwdBtn').click(function(){
		$('#changePwdForm')[0].reset();
		$('#changePwdModal').modal({
			show: true,
			backdrop: 'static'
		});
	});
});

var logout_callback = function(){
	window.location.href = './layout/fontend/login.jsp';
};
</script>