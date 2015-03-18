<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<div class="easyui-layout">
<form id="empForm" method="post" data-options="region:'center',fit:true,border:false" style="padding:20px;">
	<table style="width:100%;height:auto;" cellpadding="5">
		<tr>
			<td><input type="hidden" name="id" /></td>
		</tr>
		<tr>
			<td><label for="account">账号</label></td>
			<td><input class="easyui-textbox" type="text" name="account" data-options="required:true" /></td>
		</tr>
		<tr>
			<td><label for="name">姓名</label></td>
			<td><input class="easyui-textbox" type="text" name="name" data-options="required:true" /></td>
		</tr>
		<tr>
			<td><label for="gender">性别</label></td>
			<td>
				<select class="easyui-combobox" name="gender" style="width:100px;" data-options="required:true">
   	 				<option value="男">男</option>
    				<option value="女">女</option>
				</select>
			</td>
		</tr>
		<tr>
			<td><label for="birthDate">出生日期</label></td>
			<td><input class="easyui-datebox" type="text" name="birthDate" data-options="required:true" /></td>
		</tr>
		<tr>
			<td><label for="entryDate">入职日期</label></td>
			<td><input class="easyui-datebox" type="text" name="entryDate" data-options="required:true" /></td>
		</tr>
		<tr>
			<td><label for="email">电子邮箱</label></td>
			<td><input class="easyui-textbox" type="text" name="email" data-options="required:true" /></td>
		</tr>
		<tr>
			<td><label for="phone">联系方式</label></td>
			<td><input class="easyui-textbox" type="text" name="phone" data-options="required:true" /></td>
		</tr>
		<tr>
			<td><label for="dept">部门</label></td>
			<td>
				<input id="dept" name="dept" class="easyui-combobox" data-options="
					valueField: 'name',
					textField: 'name',
					autoHeight: 'auto'"/>
			</td>
		</tr>
		<tr>
			<td><label for="position">职位</label></td>
			<td>
				<input id="position" name="position" class="easyui-combobox" data-options="
					valueField: 'name',
					textField: 'name',
					autoHeight: 'auto'"/>
			</td>
		</tr>
		<tr>
			<td><label for="stats">状态</label></td>
			<td>
				<select class="easyui-combobox" name="stats" style="width:200px;" data-options="required:true">
   	 				<option value="在职" selected="selected">在职</option>
    				<option value="离职">离职</option>
				</select>
			</td>
		</tr>
	</table>
</form>
</div>
<script type="text/javascript">

var load_cbb_dept_callback = function(data) {
	var rows = data.rows;
	$('#dept').combobox('loadData', rows);
};

var load_cbb_pos_callback = function(data) {
	var rows = data.rows;
	$('#position').combobox('loadData', rows);
};

var load_cbb_dept = function() {
	$.get('./backend/comm/departmentAction_query', null, load_cbb_dept_callback);
};

var load_cbb_pos = function() {
	$.get('./backend/comm/positionAction_query', null, load_cbb_pos_callback);
};

// 加载部门下拉选项
load_cbb_dept();
// 加载职位下拉选项
load_cbb_pos();

</script>