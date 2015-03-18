<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
		<ol class="breadcrumb">
			<li>我的地盘</li>
			<li class="active">个人考勤</li>
		</ol>
		</div>
	</div>
	<div class="row">
		<div class="col-md-3">
			<div class="panel panel-primary">
				<div class="panel-heading">日历选择器</div>
				<div class="panel-body text-center">
					<div id="calendar"></div>
				</div>
			</div>
		</div>
		<div class="col-md-9">
			<div id="logChart" style="border: 1px solid #ddd;"></div>
		</div>
	</div>
	<div class="row" style="margin-top: 15px;">
	<div class="col-md-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				工作日志
				<div style="position: absolute; right: 20px; top: 5px; bottom: 5px;">
					<a id="addLogBtn" class="btn btn-link"><span class="glyphicon glyphicon-plus"></span> 添加</a>
					<a id="editLogBtn" class="btn btn-link"><span class="glyphicon glyphicon-edit"></span> 修改</a>
					<a id="delLogBtn" class="btn btn-link"><span class="glyphicon glyphicon-remove"></span> 删除</a>
				</div>
			</div>
			<div id="workLogArea" class="panel-body" style="min-height: 300px;">
				
			</div>
		</div>
	</div>
	</div>
	<div class="modal fade" id="logModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true" type="add">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title" id="modalLabel">新增工作日志</h3>
				</div>
				<div class="modal-body">
					<form id="logForm" class="form-horizontal">
						<div class="form-group">
							<input type="hidden" id="id" name="id" class="form-control" />
						</div>
						<div class="form-group">
							<input type="hidden" id="eid" name="eid" class="form-control" value="${sessionScope.emp.id}" />
						</div>
						<div class="form-group">
							<label for="workTimes" class="col-md-2 control-label">工作时长:</label>
							<div class="col-md-3">
								<input id="workTimes" name="workTimes" type="text" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<label for="content" class="col-md-2 control-label">工作内容:</label>
							<div class="col-md-10">
								<textarea id="content" name="content" class="form-control" rows="5"></textarea>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="saveLogBtn" type="button" class="btn btn-primary">保存</button>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="./layout/fontend/personal/js/personal_work_attendance.js"></script>