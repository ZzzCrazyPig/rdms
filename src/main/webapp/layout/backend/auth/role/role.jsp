<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
<table id="roleTable">
</table>
<div id="role_toolBar">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="role_add()">添加</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="role_edit()">编辑</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="role_delete()">删除</a>
	<input id="role_searchbox" style="width:300px;"/>
</div>
<div id="role_searchMenu" style="width:120px;">
	<div data-options="name:'name',iconCls:'icon-edit'">角色名</div>
</div>
<div id="roleDialog"></div>
<script type="text/javascript">

var rowIndex = -1;

var role_search = function(value, name) {
	var param = {};
	param[name] = value;
	var str = JSON.stringify(param);
	$("#roleTable").datagrid('load',{
		attr : str
	});
};

var role_edit_callback = function(data) {
	var success = data.success;
	var row = data.row;
	if(success) { // update thr row in the datagrid
		$('#roleTable').datagrid('updateRow', {
			index: rowIndex,
			row: row
		});
		// close the dialog
		appDialog.closeDialog('roleDialog');
	}
	
	// show message
	var msg = data.msg;
	appMessager.show('温馨提示', msg, 2000);
};

var role_add_callback = function(data) {
	var success = data.success;
	if(success) {
		var row = data.row;
		$('#roleTable').datagrid('insertRow', {
			index : 0,
			row: row
		});
		// close the dialog
		appDialog.closeDialog('roleDialog');
	}
	
	// show message
	var msg = data.msg;
	appMessager.show('温馨提示', msg, 2000);
};

var role_delete_callback = function(data) {
	var success = data.success;
	if(success) {
		var row = data.row;
		// get the delete id array
		var ids = row.split(',');
		for(var i = 0; i < ids.length; i++) {
			var id = ids[i];
			// get the row index
			var index = $('#roleTable').datagrid('getRowIndex', id);
			// delete row from the datagrid
			$('#roleTable').datagrid('deleteRow', index);
		}
	}
	var msg = data.msg;
	appMessager.show('温馨提示', msg, 2000);
};

var role_edit = function() {
	var row = $("#roleTable").datagrid('getSelected');
	if(row == null) {
		appMessager.alert('温馨提示', '请先选中要编辑的行');
		return ;
	}
	rowIndex = $('#roleTable').datagrid('getRowIndex', row);
	var buttons = [{
		text: '更新',
		iconCls: 'icon-ok',
		handler: function(){
			var attr = serializeForm('roleForm');
			var appModel = new AppModel();
			appModel.setAttr(attr);
			$.post('./backend/auth/roleAction_update', appModel,role_edit_callback);
		}
	}, {
		text: '取消',
		iconCls: 'icon-cancel',
		handler: function(){
			appDialog.closeDialog('roleDialog');
		}
	}];
	appDialog.createEditDialog('roleDialog', './layout/backend/auth/role/roleForm.jsp', buttons, 400, 400, true, '更新角色信息', 'roleForm', row);
};

var role_add = function() {
	var buttons = [{
		text: '添加',
		iconCls: 'icon-ok',
		handler: function() {
			var attr = serializeForm('roleForm');
			var appModel = new AppModel();
			appModel.setAttr(attr);
			$.post('./backend/auth/roleAction_insert', appModel, role_add_callback);
		}
	}, {
		text: '取消',
		iconCls: 'icon-cancel',
		handler: function() {
			appDialog.closeDialog('roleDialog');
		}
	}];
	appDialog.createAddDialog('roleDialog', './layout/backend/auth/role/roleForm.jsp', buttons, 400, 400, true, '新增角色信息', 'roleForm');
};

var role_delete = function() {
	var rows = $("#roleTable").datagrid('getSelections');
	if(rows.length > 0) {
		var appModel = new AppModel();
		var ids = [];
		for(var i = 0; i < rows.length; i++) {
			ids.push(rows[i].id);
		}
		appModel.setAttr(ids);
		$.post('./backend/auth/roleAction_multiDelete', appModel, role_delete_callback);
	}
};

$('#role_searchbox').searchbox({
    searcher: role_search,
    menu:'#role_searchMenu',
    prompt:'Please Input Value'
});

$("#roleTable").datagrid({
	title: "角色信息",
	toolbar: '#role_toolBar',
	url: './backend/auth/roleAction_queryByPage',
	method: 'get',
	columns:[[
	    {field:'check',title:'全选',checkbox:true},
	    {field:'id', title:'ID',hidden:true},
		{field:'name',title:'角色名',sortable:true,width:100},
		{field:'detail',title:'角色描述',width:300}
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
