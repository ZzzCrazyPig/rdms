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
			<li class="active">考勤状况</li>
		</ol>
	</div>
	<div class="row">
		<h2><span class="label label-primary"><i class="glyphicon glyphicon-search"></i> 考勤情况查询</span></h2>
	</div>
	<div class="row mt15">
		<div class="col-md-2">
			<select id="deptName" class="form-control">
				<option value="技术预研部">技术预研部</option>
				<option value="基础平台部">基础平台部</option>
			</select>
		</div>
		<div class="col-md-2">
			<input id="startDate" class="form-control" />
		</div>
		<div class="col-md-2">
			<input id="endDate" class="form-control" />
		</div>
		<div class="col-md-2">
			<button class="btn btn-primary" onclick="countSpecDeptWorkLog()">查询</button>
		</div>
	</div>
	<div class="row mt15">
		<div class="row">
			<div id="columnChart" style="margin: 0 auto"></div>
		</div>
		<div class="row">
			<div>
				<p id="warning" style="color: red;" hidden="true" class="text-center"></p>
			</div>
		</div>
	</div>
</div>
<script src="./layout/fontend/situation/js/work_attendance_situation.js"></script>