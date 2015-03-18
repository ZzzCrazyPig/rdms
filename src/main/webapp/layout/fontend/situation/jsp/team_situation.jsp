<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<div class="container-fluid">
	<div class="row">
		<ol class="breadcrumb">
			<li>研发概况</li>
			<li class="active">团队概况</li>
		</ol>
	</div>
	<div class="row">
		<h2><span class="label label-success"><i class="glyphicon glyphicon-list"></i> 项目组情况</span></h2>
		<div class="col-md-8">
			<div id="columnChart" style="min-width: 310px; height: 400px; max-width: 600px; margin: 0 auto"></div>
		</div>
		<div class="col-md-4">
			<div id="pieChart" style="min-width: 310px; height: 400px; max-width: 600px; margin: 0 auto"></div>
		</div>
	</div>
	<div class="row">
		<h2><span class="label label-primary"><i class="glyphicon glyphicon-list-alt"></i> 项目组信息</span></h2>
		<table id="pjGrTable" class="table table-bordered"></table>
	</div>
	
	<div class="modal fade" id="pjModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel1" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title" id="modalLabel1">所负责项目</h3>
				</div>
				<div class="modal-body">
					<div class="container-fluid">
						<div class="row">
							<table id="pjTable" class="table table-bordered"></table>
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
<script src="./layout/fontend/situation/js/team_situation.js"></script>