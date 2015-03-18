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
			<li class="active">团队参与</li>
		</ol>
	</div>
	
	<div class="row">
		<h2 style="padding-left:0px;"><span class="label label-warning">我创建的项目组</span></h2>
		<div id="myCreatePjGroup_toolbar">
			<button id="createPjGroupBtn" class="btn btn-default">创建项目组</button>
		</div>
		<table id="myCreatePjGroup" class="table table-bordered"></table>
	</div>
	<div class="row">
		<h2><span class="label label-success">我参与的项目组</span></h2>
		<table id="myJoinPjGroup" class="table table-bordered"></table>
	</div>
	
	<div class="modal fade" id="pjGroupModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true" type="add">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title" id="modalLabel">新增项目组</h3>
				</div>
				<div class="modal-body">
					<form id="pjGroupForm" class="form-horizontal">
						<div class="form-group">
							<input type="hidden" id="id" name="id" class="form-control" />
						</div>
						<div class="form-group">
							<input type="hidden" id="createUser" name="createUser" class="form-control" value="${sessionScope.emp.id}" />
						</div>
						<div class="form-group">
							<label for="name" class="col-md-3 control-label">项目组名称:</label>
							<div class="col-md-9">
								<input id="name" name="name" type="text" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<label for="detail" class="col-md-3 control-label">项目组简介:</label>
							<div class="col-md-9">
								<textarea id="detail" name="detail" class="form-control" rows="5"></textarea>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="savePjGroupBtn" type="button" class="btn btn-primary">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="addPjGrMemModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title" id="modalLabel">添加项目组成员</h3>
				</div>
				<div class="modal-body">
					<div class="container-fluid">
						<div class="row">
							<input id="pjGrId" type="hidden" />
						</div>
						<div class="row">
							<h3>已有成员</h3>
							<table id="pjGrMemsTable" class="table table-bordered"></table>
						</div>
						<div class="row">
							<h3>选择新成员</h3>
							<table id="empTable" class="table table-bordered"></table>
						</div>
						<div class="row">
							<h3>待添加成员</h3>
							<table id="newPjGrMemsTable" class="table table-bordered"></table>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="addPjGrMemBtn" type="button" class="btn btn-primary">保存</button>
				</div>
			</div>
		</div>
	</div> <!-- end of modal -->
	
	<div class="modal fade" id="managePjGrMemsModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel1" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title" id="modalLabel1">管理项目组成员</h3>
				</div>
				<div class="modal-body">
					<div class="container-fluid">
						<div class="row">
							<table id="managePjGrMemsTable" class="table table-bordered"></table>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div> <!-- end of modal -->
	
	<div class="modal fade" id="lookPjGrMemsModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel2" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title" id="modalLabel2">查看项目组成员</h3>
				</div>
				<div class="modal-body">
					<div class="container-fluid">
						<div class="row">
							<table id="lookPjGrMemsTable" class="table table-bordered"></table>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div> <!-- end of modal -->
</div>
<script src="./layout/fontend/personal/js/personal_team_situation.js"></script>