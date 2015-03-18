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
			<li>svn项目管理</li>
			<li class="active">svn项目管理</li>
		</ol>
	</div>
	<div id="statsvnArea" class="row" hidden="true">
		<div id="statsvnPanel" class="panel panel-primary">
			<div class="panel-heading">统计结果</div>
			<div class="panel-body">
				<!-- <iframe id="statsvn-frame" src="./statsvn/result/index.html" runat="server" 
					frameborder="no" border="0" scrolling="no" allowtransparency="yes"
					width="100%" height="100%"
					onload="autoSuitIframeHeight()"></iframe> -->
			</div>
		</div>
	</div>
	<div class="row">
		<div id="svnPjTable_toolbar">
			<button id="createSvnPjBtn" class="btn btn-primary">添加SVN项目</button>
		</div>
		<table id="svnPjTable" class="table table-bordered"></table>
	</div>
	
	<div id="cover" class="cover"></div>
	
	<div class="modal fade" id="svnPjModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true" type="add">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title" id="modalLabel">创建SVN项目</h3>
				</div>
				<div class="modal-body">
					<form id="svnPjForm" class="form-horizontal">
						<div class="form-group">
							<input type="hidden" id="id" name="id" class="form-control" />
						</div>
						<div class="form-group">
							<label for="url" class="col-md-3 control-label">url地址:</label>
							<div class="col-md-9">
								<input id="url" name="url" type="text" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<label for="detail" class="col-md-3 control-label">svn项目简介:</label>
							<div class="col-md-9">
								<textarea id="detail" name="detail" class="form-control" rows="5"></textarea>
							</div>
						</div>
						<div class="form-group">
							<label for="pjName" class="col-md-3 control-label">所属项目:</label>
							<div class="col-md-9">
								<input id="pjName" name="pjName" type="text" class="form-control" />
								<input id="pid" name="pid" type="hidden" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-12">
								<table id="pjTable" class="table table-bordered"></table>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="saveSvnPjBtn" type="button" class="btn btn-primary">保存</button>
				</div>
			</div>
		</div>
	</div> <!-- end of modal -->
	
</div>
<script src="./layout/fontend/svn/js/svn_project_manage.js"></script>