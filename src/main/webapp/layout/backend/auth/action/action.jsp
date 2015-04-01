<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
<table id="actionTable">
</table>
<div id="action_toolBar">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="action_add()">添加</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="action_edit()">编辑</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="action_delete()">删除</a>
	<input id="action_searchbox" style="width:300px;"/>
</div>
<div id="action_searchMenu" style="width:120px;">
	<div data-options="name:'name',iconCls:'icon-edit'">动作名</div>
</div>
<div id="actionDialog"></div>
<script type="text/javascript">

var rowIndex = -1;

var action_search = function(value, name) {
	var param = {};
	param[name] = value;
	var str = JSON.stringify(param);
	$("#actionTable").datagrid('load',{
		attr : str
	});
};

var action_edit_callback = function(data) {
	var success = data.success;
	var row = data.row;
	if(success) { // update thr row in the datagrid
		$('#actionTable').datagrid('updateRow', {
			index: rowIndex,
			row: row
		});
		// close the dialog
		appDialog.closeDialog('actionDialog');
	}
	
	// show message
	var msg = data.msg;
	appMessager.show('温馨提示', msg, 2000);
};

var action_add_callback = function(data) {
	var success = data.success;
	if(success) {
		var row = data.row;
		$('#actionTable').datagrid('insertRow', {
			index : 0,
			row: row
		});
		// close the dialog
		appDialog.closeDialog('actionDialog');
	}
	
	// show message
	var msg = data.msg;
	appMessager.show('温馨提示', msg, 2000);
};

var action_delete_callback = function(data) {
	var success = data.success;
	if(success) {
		var row = data.row;
		// get the delete id array
		var ids = row.split(',');
		for(var i = 0; i < ids.length; i++) {
			var id = ids[i];
			// get the row index
			var index = $('#actionTable').datagrid('getRowIndex', id);
			// delete row from the datagrid
			$('#actionTable').datagrid('deleteRow', index);
		}
	}
	var msg = data.msg;
	appMessager.show('温馨提示', msg, 2000);
};

var action_edit = function() {
	var row = $("#actionTable").datagrid('getSelected');
	if(row == null) {
		appMessager.alert('温馨提示', '请先选中要编辑的行');
		return ;
	}
	rowIndex = $('#actionTable').datagrid('getRowIndex', row);
	var buttons = [{
		text: '更新',
		iconCls: 'icon-ok',
		handler: function(){
			var attr = serializeForm('actionForm');
			var appModel = new AppModel();
			appModel.setAttr(attr);
			$.post('./backend/auth/actionAction_update', appModel,action_edit_callback);
		}
	}, {
		text: '取消',
		iconCls: 'icon-cancel',
		handler: function(){
			appDialog.closeDialog('actionDialog');
		}
	}];
	appDialog.createEditDialog('actionDialog', './layout/backend/auth/action/actionForm.jsp', buttons, 400, 400, true, '更新动作信息', 'actionForm', row);
};

var action_add = function() {
	var buttons = [{
		text: '添加',
		iconCls: 'icon-ok',
		handler: function() {
			var attr = serializeForm('actionForm');
			var appModel = new AppModel();
			appModel.setAttr(attr);
			$.post('./backend/auth/actionAction_insert', appModel, action_add_callback);
		}
	}, {
		text: '取消',
		iconCls: 'icon-cancel',
		handler: function() {
			appDialog.closeDialog('actionDialog');
		}
	}];
	appDialog.createAddDialog('actionDialog', './layout/backend/auth/action/actionForm.jsp', buttons, 400, 400, true, '新增动作信息', 'actionForm');
};

var action_delete = function() {
	var rows = $("#actionTable").datagrid('getSelections');
	if(rows.length > 0) {
		var appModel = new AppModel();
		var ids = [];
		for(var i = 0; i < rows.length; i++) {
			ids.push(rows[i].id);
		}
		appModel.setAttr(ids);
		$.post('./backend/auth/actionAction_multiDelete', appModel, action_delete_callback);
	}
};

$('#action_searchbox').searchbox({
    searcher: action_search,
    menu:'#action_searchMenu',
    prompt:'Please Input Value'
});

$("#actionTable").datagrid({
	title: "动作信息",
	toolbar: '#action_toolBar',
	url: './backend/auth/actionAction_queryByPage',
	method: 'get',
	columns:[[
	    {field:'check',title:'全选',checkbox:true},
	    {field:'id', title:'ID',hidden:true},
		{field:'name',title:'动作名',sortable:true,width:100},
		{field:'url', title:'请求的URL地址',width: 300},
		{field:'detail',title:'动作描述',width:300}
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
