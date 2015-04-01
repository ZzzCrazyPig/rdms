<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div class="container-fluid">
	<div class="row">
		<ol class="breadcrumb">
			<li>研发概况</li>
			<li class="active">项目概况</li>
		</ol>
	</div>
	<div class="row">
		<h2><span class="label label-success"><i class="glyphicon glyphicon-list"></i> 项目分布情况</span></h2>
		<div class="col-md-8">
			<div id="columnChart" style="min-width: 310px; height: 400px; max-width: 600px; margin: 0 auto"></div>
		</div>
		<div class="col-md-4">
			<div id="pieChart" style="min-width: 310px; height: 400px; max-width: 600px; margin: 0 auto"></div>
		</div>
		<!-- <div class="col-md-1">
			<p class="text-right"><button class="btn btn-primary" onclick="countEachDeptPj()">刷新</button></p>
		</div> -->
	</div>
	<div class="row mt15">
		<h2><span class="label label-primary"><i class="glyphicon glyphicon-search"></i> 项目进度查询</span></h2>
		<div class="col-md-3 mt15">
			<div class="input-group">
				<span class="input-group-addon" id="deptName-addon">选择部门</span>
				<select id="deptName" class="form-control" aria-describedby="deptName-addon">
				</select>
			</div>
		</div>
		<div class="col-md-2 mt15">
			<button class="btn btn-primary" onclick="showPjProgress()">查询</button>
		</div>
	</div>
	<div class="row mt15">
		<div id="barChart"></div>
		<!-- <div id="pjProgressPanel" class="panel panel-primary">
			<div class="panel-heading">查询项目进度</div>
			<div class="panel-body">
				<div class="container-fluid">
					<div class="row">
						<div id="barChart"></div>
					</div>
				</div>
			</div>
			<div class="panel-footer text-right">
				<button class="btn btn-success" onclick="collapsePanel('pjProgressPanel')">折叠</button>
			</div>
		</div> -->
	</div>
	<div class="row">
		<h2><span class="label label-warning"><i class="glyphicon glyphicon-tags"></i> 项目基本信息</span></h2>
		<table id="pjTable" class="table table-bordered"></table>
	</div>
</div>
<script src="./layout/fontend/situation/js/project_situation.js"></script>