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
			<li class="active">项目参与</li>
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
		<h2><span class="label label-primary">参与的项目</span></h2>
		<table id="joinPjTable" class="table table-bordered"></table>
	</div>
	<div class="row">
		<h2><span class="label label-primary">我创建的项目</span></h2>
		<table id="myCreatePjTable" class="table table-bordered"></table>
	</div>
	
	<div id="cover" class="cover"></div>
	
	<div class="modal fade" id="pjStageModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title" id="modalLabel">项目阶段信息</h3>
				</div>
				<div class="modal-body">
					<div class="container-fluid">
						<div class="row">
							<table id="pjStageTable" class="table table-bordered"></table>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div> <!-- end of modal -->
	
	<div class="modal fade" id="pjMarkModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel1" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title" id="modalLabel1">项目里程碑信息</h3>
				</div>
				<div class="modal-body">
					<div class="container-fluid">
						<div class="row">
							<table id="pjMarkTable" class="table table-bordered"></table>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div> <!-- end of modal -->
	
	<div class="modal fade" id="svnPjModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel2" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title" id="modalLabel2">SVN项目信息</h3>
				</div>
				<div class="modal-body">
					<div class="container-fluid">
						<div class="row">
							<table id="svnPjTable" class="table table-bordered"></table>
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
<script src="./layout/fontend/personal/js/personal_project_situation.js"></script>