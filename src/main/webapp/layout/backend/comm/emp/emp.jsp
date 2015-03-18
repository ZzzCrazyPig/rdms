<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
<table id="empTable">
</table>
<div id="emp_toolBar">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="emp_add()">添加</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="emp_edit()">编辑</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="emp_delete()">删除</a>
	<input id="emp_searchbox" style="width:300px;"/>
</div>
<div id="emp_searchMenu" style="width:120px;">
	<div data-options="name:'usr',iconCls:'icon-edit'">账号</div>
	<div data-options="name:'name',selected:true">姓名</div>
</div>
<div id="empDialog"></div>
<script type="text/javascript">

var rowIndex = -1;

var emp_search = function(value, name) {
	var param = {};
	param[name] = value;
	var str = JSON.stringify(param);
	$("#empTable").datagrid('load',{
		attr : str
	});
};

var emp_edit_callback = function(data) {
	var success = data.success;
	var row = data.row;
	if(success) { // update thr row in the datagrid
		$('#empTable').datagrid('updateRow', {
			index: rowIndex,
			row: row
		});
		// close the dialog
		appDialog.closeDialog('empDialog');
	}
	
	// show message
	var msg = data.msg;
	appMessager.show('温馨提示', msg, 2000);
};

var emp_add_callback = function(data) {
	var success = data.success;
	if(success) {
		var row = data.row;
		$('#empTable').datagrid('insertRow', {
			index : 0,
			row: row
		});
		// close the dialog
		appDialog.closeDialog('empDialog');
	}
	
	// show message
	var msg = data.msg;
	appMessager.show('温馨提示', msg, 2000);
};

var emp_delete_callback = function(data) {
	var success = data.success;
	if(success) {
		var row = data.row;
		// get the delete id array
		var ids = row.split(',');
		for(var i = 0; i < ids.length; i++) {
			var id = ids[i];
			// get the row index
			var index = $('#empTable').datagrid('getRowIndex', id);
			// delete row from the datagrid
			$('#empTable').datagrid('deleteRow', index);
		}
	}
	var msg = data.msg;
	appMessager.show('温馨提示', msg, 2000);
};

var emp_edit = function() {
	var row = $("#empTable").datagrid('getSelected');
	if(row == null) {
		appMessager.alert('温馨提示', '请先选中要编辑的行');
		return ;
	}
	rowIndex = $('#empTable').datagrid('getRowIndex', row);
	var buttons = [{
		text: '更新',
		iconCls: 'icon-ok',
		handler: function(){
			var attr = serializeForm('empForm');
			console.info(attr);
			var appModel = new AppModel();
			appModel.setAttr(attr);
			$.post('./backend/comm/employeeAction_update', appModel, emp_edit_callback);
		}
	}, {
		text: '取消',
		iconCls: 'icon-cancel',
		handler: function(){
			appDialog.closeDialog('empDialog');
		}
	}];
	appDialog.createEditDialog('empDialog', './layout/backend/comm/emp/empForm.jsp', buttons, 400, 400, true, '更新员工信息', 'empForm', row);
};

var emp_add = function() {
	var buttons = [{
		text: '添加',
		iconCls: 'icon-ok',
		handler: function() {
			var attr = serializeForm('empForm');
			var appModel = new AppModel();
			appModel.setAttr(attr);
			$.post('./backend/comm/employeeAction_insert', appModel, emp_add_callback);
		}
	}, {
		text: '取消',
		iconCls: 'icon-cancel',
		handler: function() {
			appDialog.closeDialog('empDialog');
		}
	}];
	appDialog.createAddDialog('empDialog', './layout/backend/comm/emp/empForm.jsp', buttons, 400, 400, true, '新增员工信息', 'empForm');
};

var emp_delete = function() {
	var rows = $("#empTable").datagrid('getSelections');
	if(rows.length > 0) {
		var appModel = new AppModel();
		var ids = [];
		for(var i = 0; i < rows.length; i++) {
			ids.push(rows[i].id);
		}
		appModel.setAttr(ids);
		$.post('./backend/comm/employeeAction_multiDelete', appModel, emp_delete_callback);
	}
};

$('#emp_searchbox').searchbox({
    searcher: emp_search,
    menu:'#emp_searchMenu',
    prompt:'Please Input Value'
});

$("#empTable").datagrid({
	title: "员工信息",
	toolbar: '#emp_toolBar',
	url: './backend/comm/employeeAction_queryByPage',
	method: 'get',
	columns:[[
	    {field:'check',title:'全选',checkbox:true},
	    {field:'id', title:'ID',hidden:true},
		{field:'account',title:'账号',sortable:true,width:100},
		{field:'name',title:'姓名',width:100},
		{field:'gender',title:'性别', width:80},
		{field:'birthDate',title:'出生日期', width:120},
		{field:'entryDate',title:'入职日期', width:120},
		{field:'email',title:'电子邮箱', width:150},
		{field:'phone',title:'联系方式', width:120},
		{field:'dept',title:'所属部门', width:100},
		{field:'position',title:'职位', width:100},
		{field:'stats',title:'状态', width:80}
	]],
	idField: 'id',
	singleSelect: false,
	fit: true,
	border: false,
	pagination: true,
	rownumbers: true,
	pageNumber: 1,
	pageSize: 10,
	pageList: [10,20,40,60,80],
	loadMsg: 'loading...'
});
</script>
