<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div class="container-fluid">
	<div class="row">
		<ol class="breadcrumb">
			<li>BUG管理</li>
			<li class="active">BUG总览</li>
		</ol>
	</div>
	<div class="row">
		<div id="bugTable_toolbar">
			<div class="btn-group" role="group">
  				<button id="createBugBtn" class="btn btn-primary">创建BUG</button>
			</div>
		</div>
		<table id="bugTable" class="table table-bordered"></table>
	</div>
	
	<div class="modal fade" id="bugModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true" type="add">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title" id="modalLabel">创建BUG</h3>
				</div>
				<div class="modal-body">
					<form id="bugForm" class="form-horizontal">
						<div class="form-group">
							<input type="hidden" id="id" name="id" class="form-control" />
						</div>
						<div class="form-group">
							<label for="name" class="col-md-3 control-label">BUG编号:</label>
							<div class="col-md-9">
								<input id="name" name="name" type="text" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<label for="title" class="col-md-3 control-label">BUG标题:</label>
							<div class="col-md-9">
								<input id="title" name="title" type="text" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<label for="detail" class="col-md-3 control-label">BUG描述:</label>
							<div class="col-md-9">
								<textarea id="detail" name="detail" class="form-control" rows="5"></textarea>
							</div>
						</div>
						<div class="form-group">
							<label for="level" class="col-md-3 control-label">BUG级别:</label>
							<div class="col-md-9">
								<select id="level" name="level" class="form-control">
									<option value="一般">一般</option>
									<option value="严重">严重</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="status" class="col-md-3 control-label">BUG状态:</label>
							<div class="col-md-9">
								<input id="status" name="status" type="text" class="form-control" value="待DEV解决" />
							</div>
						</div>
						<div class="form-group">
							<label for="preResolveDay" class="col-md-3 control-label">预计解决天数:</label>
							<div class="col-md-9">
								<input id="preResolveDay" name="preResolveDay" type="text" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<label for="pjName" class="col-md-3 control-label">所属项目:</label>
							<div class="col-md-4">
								<input id="pid" name="pid" type="hidden" class="form-control" />
								<input id="pjName" name="pjName" type="text" class="form-control" />
							</div>
							<div class="col-md-5">
								<p style="color: red;">(注: 从下面的列表中选择)</p>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-12">
								<table id="pjTable" class="table table-bordered"></table>
							</div>
						</div>
						<div class="form-group">
							<label for="qaName" class="col-md-3 control-label">QA:</label>
							<div class="col-md-4">
								<input id="qaId" name="qaId" type="hidden" value="${sessionScope.emp.id}" class="form-control" />
								<input id="qaName" name="qaName" type="text" value="${sessionScope.emp.name}" class="form-control" disabled="disabled" />
							</div>
						</div>
						<div class="form-group">
							<label for="devName" class="col-md-3 control-label">指定DEV:</label>
							<div class="col-md-4">
								<input id="devId" name="devId" type="hidden" class="form-control" />
								<input id="devName" name="devName" type="text" class="form-control" />
							</div>
							<div class="col-md-5">
								<p style="color: red;">(注: 从下面的列表中选择)</p>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-12">
								<table id="empTable" class="table table-bordered"></table>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="saveBugBtn" type="button" class="btn btn-primary">保存</button>
				</div>
			</div>
		</div>
	</div> <!-- end of modal -->
	
	<div class="modal fade" id="bugTraceModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel1" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title" id="modalLabel1">创建BUG跟踪</h3>
				</div>
				<div class="modal-body">
					<form id="bugTraceForm" class="form-horizontal">
						<div class="form-group">
							<input type="hidden" id="id" name="id" class="form-control" />
							<input type="hidden" id="bid" name="bid" class="form-control" />
						</div>
						<div class="form-group">
							<label for="title" class="col-md-3 control-label">BUG跟踪标题:</label>
							<div class="col-md-9">
								<input id="title" name="title" type="text" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<label for="detail" class="col-md-3 control-label">BUG跟踪描述:</label>
							<div class="col-md-9">
								<textarea id="detail" name="detail" class="form-control" rows="5"></textarea>
							</div>
						</div>
						<div class="form-group">
							<label for="type" class="col-md-3 control-label">BUG跟踪类型:</label>
							<div class="col-md-9">
								<select id="type" name="type" class="form-control">
									<option value="DEV_TRACE" selected="selected">DEV跟踪</option>
									<option value="QA_TRACE">QA跟踪</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="fromWhoName" class="col-md-3 control-label">跟踪来源:</label>
							<div class="col-md-5">
								<input id="fromWhoId" name="fromWhoId" type="hidden" class="form-control" />
								<input id="fromWhoName" name="fromWhoName" type="text" class="form-control" disabled="disabled" />
							</div>
						</div>
						<div class="form-group">
							<label for="toWhoName" class="col-md-3 control-label">跟踪去处:</label>
							<div class="col-md-5">
								<input id="toWhoId" name="toWhoId" type="hidden" class="form-control" />
								<input id="toWhoName" name="toWhoName" type="text" class="form-control" disabled="disabled" />
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="saveBugTraceBtn" type="button" class="btn btn-primary">保存</button>
				</div>
			</div>
		</div>
	</div> <!-- end of modal -->
	
	<div class="modal fade" id="lookBugTraceModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel2" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title" id="modalLabel2">查看BUG跟踪信息</h3>
				</div>
				<div class="modal-body">
					<table id="bugTraceTable" class="table table-bordered"></table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="saveBugTraceBtn" type="button" class="btn btn-primary">保存</button>
				</div>
			</div>
		</div>
	</div> <!-- end of modal -->
	
</div>
<script src="./layout/fontend/bug/js/bug_look.js"></script>