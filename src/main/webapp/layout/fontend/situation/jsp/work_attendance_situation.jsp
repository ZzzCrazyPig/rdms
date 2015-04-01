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
		<div class="col-md-3">
			<div class="input-group">
				<span class="input-group-addon" id="deptName-addon">选择部门</span>
				<select id="deptName" class="form-control" aria-describedby="deptName-addon">
				</select>
			</div>
		</div>
		<div class="col-md-3">
			<div class="input-group">
				<span class="input-group-addon" id="startDate-addon">开始日期</span>
				<input id="startDate" class="form-control" aria-describedby="startDate-addon" />
			</div>
		</div>
		<div class="col-md-3">
			<div class="input-group">
				<span class="input-group-addon" id="endDate-addon">结束日期</span>
				<input id="endDate" class="form-control" aria-describedby="endDate-addon" />
			</div>
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
	<div class="notifications bottom-right" id="notifyMsg"></div>
</div>
<script src="./layout/fontend/situation/js/work_attendance_situation.js"></script>