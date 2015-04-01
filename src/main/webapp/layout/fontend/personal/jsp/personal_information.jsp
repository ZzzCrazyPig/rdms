<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div class="container-fluid">
	<div class="row">
			<ol class="breadcrumb">
				<li>我的地盘</li>
				<li class="active">个人资料</li>
			</ol>
	</div>
	<div class="row">
		<div class="col-md-5">
			<div class="row">
				<h4>更改个人头像</h4>
				<form id="empPicForm" class="form-horizontal"
					enctype="multipart/form-data">
					<div class="form-group">
						<div class="col-md-12">
							<input id="empPic" name="empPic" class="file" type="file">
						</div>
					</div>
				</form>
			</div>
			<div class="row">
				<form class="form-horizontal">
					<div class="form-group">
						<label for="account" class="col-md-3 control-label">账号:</label>
						<div class="col-md-9">
							<input id="account" name="account" type="text"
								class="form-control" value="${sessionScope.emp.account}"
								disabled="disabled" />
						</div>
					</div>
					<div class="form-group">
						<label for="name" class="col-md-3 control-label">姓名:</label>
						<div class="col-md-9">
							<input id="name" name="name" type="text" class="form-control"
								value="${sessionScope.emp.name}" disabled="disabled" />
						</div>
					</div>
					<div class="form-group">
						<label for="dept" class="col-md-3 control-label">部门:</label>
						<div class="col-md-9">
							<input id="dept" name="dept" type="text" class="form-control"
								value="${sessionScope.emp.dept}" disabled="disabled" />
						</div>
					</div>
					<div class="form-group">
						<label for="position" class="col-md-3 control-label">职位:</label>
						<div class="col-md-9">
							<input id="position" name="position" type="text"
								class="form-control" value="${sessionScope.emp.position}"
								disabled="disabled" />
						</div>
					</div>
				</form>
			</div>
			<div class="row">
				<form id="empForm" class="form-horizontal">
					<div class="form-group">
						<label for="birthDate" class="col-md-3 control-label">出生日期</label>
						<div class="col-md-9">
							<input id="birthDate" name="birthDate" type="text"
								class="form-control" value="${sessionScope.emp.birthDate}" />
						</div>
					</div>
					<div class="form-group">
						<label for="phone" class="col-md-3 control-label">联系方式</label>
						<div class="col-md-9">
							<input id="phone" name="phone" type="text" class="form-control"
								value="${sessionScope.emp.phone }" />
						</div>
					</div>
					<div class="form-group">
						<label for="email" class="col-md-3 control-label">电子邮箱</label>
						<div class="col-md-9">
							<input id="email" name="email" type="text" class="form-control"
								value="${sessionScope.emp.email }" />
						</div>
					</div>
				</form>
				<p class="text-center">
					<button class="btn btn-primary" onclick="updateEmpInfo('empForm')">更改信息</button>
				</p>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="notifications bottom-right" id="notifyMsg"></div>
	</div>
</div>
<script src="./layout/fontend/personal/js/personal_information.js"></script>