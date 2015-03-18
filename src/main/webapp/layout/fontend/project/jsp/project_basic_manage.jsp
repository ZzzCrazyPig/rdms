<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div class="container-fluid">
	<div class="row">
		<ol class="breadcrumb">
			<li>项目管理</li>
			<li class="active">基本管理</li>
		</ol>
	</div>
	<div class="row">
		<p><button id="createPjBtn" class="btn btn-primary">创建项目</button></p>
		<table id="pjTable" class="table table-bordered"></table>
	</div>
	<div class="row">
		<!-- 这里显示项目阶段信息 -->
		<div id="pjStagePanel" class="panel panel-primary" hidden="true" data-pid="">
			<div class="panel-heading">项目阶段信息</div>
			<div class="panel-body">
				<button id="createPjStageBtn" class="btn btn-primary">创建项目阶段</button>
				<table id="pjStageTable" class="table table-bordered"></table>
			</div>
		</div>
	</div>
	<div class="row">
		<div id="pjMarkPanel" class="panel panel-primary" hidden="true" data-pid="">
			<div class="panel-heading">项目里程碑信息</div>
			<div class="panel-body">
				<button id="createPjMarkBtn" class="btn btn-primary">创建项目里程碑</button>
				<table id="pjMarkTable" class="table table-bordered"></table>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="pjModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true" type="add">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title" id="modalLabel">创建项目</h3>
				</div>
				<div class="modal-body">
					<form id="pjForm" class="form-horizontal">
						<div class="form-group">
							<input type="hidden" id="id" name="id" class="form-control" />
						</div>
						<div class="form-group">
							<input type="hidden" id="createUser" name="createUser" class="form-control" value="${sessionScope.emp.id}" />
						</div>
						<div class="form-group">
							<label for="name" class="col-md-3 control-label">项目名称:</label>
							<div class="col-md-9">
								<input id="name" name="name" type="text" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<label for="detail" class="col-md-3 control-label">项目简介:</label>
							<div class="col-md-9">
								<textarea id="detail" name="detail" class="form-control" rows="5"></textarea>
							</div>
						</div>
						<div class="form-group">
							<label for="pj_startTime" class="col-md-3 control-label">开始时间: </label>
							<div class="col-md-3">
								<input id="pj_startTime" name="startTime" type="text" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<label for="preCompleteDay" class="col-md-3 control-label">预计完成天数: </label>
							<div class="col-md-2">
								<input id="preCompleteDay" name="preCompleteDay" type="text" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<label for="status" class="col-md-3 control-label">状态: </label>
							<div class="col-md-3">
								<select id="status" name="status" class="form-control">
									<option value="未开始">未开始</option>
									<option value="进行中">进行中</option>
									<option value="已完成">已完成</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="pjGrName" class="col-md-3 control-label">负责的项目组:</label>
							<div class="col-md-3">
								<input id="pjGrName" name="pjGrName" type="text" class="form-control" />
								<input id="pjGrId" name="pjGrId" type="hidden" />
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-12">
								<table id="pjGrTable" class="table table-bordered"></table>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="savePjBtn" type="button" class="btn btn-primary">保存</button>
				</div>
			</div>
		</div>
	</div> <!-- end of modal -->
	
	<div class="modal fade" id="pjStageModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel1" aria-hidden="true" type="add">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title" id="modalLabel1">创建项目阶段</h3>
				</div>
				<div class="modal-body">
					<form id="pjStageForm" class="form-horizontal">
						<div class="form-group">
							<input type="hidden" id="id" name="id" class="form-control" />
						</div>
						<div class="form-group">
							<label for="name" class="col-md-3 control-label">项目阶段名称:</label>
							<div class="col-md-9">
								<input id="name" name="name" type="text" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<label for="startTime" class="col-md-3 control-label">开始时间: </label>
							<div class="col-md-3">
								<input id="pjStage_startTime" name="startTime" type="text" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<label for="preCompleteDay" class="col-md-3 control-label">预计完成天数: </label>
							<div class="col-md-2">
								<input id="preCompleteDay" name="preCompleteDay" type="text" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<label for="status" class="col-md-3 control-label">状态: </label>
							<div class="col-md-3">
								<select id="status" name="status" class="form-control">
									<option value="未开始">未开始</option>
									<option value="进行中">进行中</option>
									<option value="已完成">已完成</option>
								</select>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="savePjStageBtn" type="button" class="btn btn-primary">保存</button>
				</div>
			</div>
		</div>
	</div> <!-- end of modal -->
	
	<div class="modal fade" id="pjMarkModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel2" aria-hidden="true" type="add">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title" id="modalLabel2">创建项目里程碑</h3>
				</div>
				<div class="modal-body">
					<form id="pjMarkForm" class="form-horizontal">
						<div class="form-group">
							<input type="hidden" id="id" name="id" class="form-control" />
						</div>
						<div class="form-group">
							<label for="content" class="col-md-3 control-label">里程碑内容:</label>
							<div class="col-md-9">
								<textarea id="content" name="content" class="form-control" rows="5"></textarea>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="savePjMarkBtn" type="button" class="btn btn-primary">保存</button>
				</div>
			</div>
		</div>
	</div> <!-- end of modal -->
	
</div>
<script src="./layout/fontend/project/js/project_basic_manage.js"></script>