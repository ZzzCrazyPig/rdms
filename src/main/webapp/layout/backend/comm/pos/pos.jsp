<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
<table id="posTable">
</table>
<div id="pos_toolBar">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="pos_add()">添加</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="pos_edit()">编辑</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="pos_delete()">删除</a>
	<input id="pos_searchbox" style="width:300px;"/>
</div>
<div id="pos_searchMenu" style="width:120px;">
	<div data-options="name:'name',iconCls:'icon-edit'">职位名</div>
</div>
<div id="posDialog"></div>
<script type="text/javascript">

var rowIndex = -1;

var pos_search = function(value, name) {
	var param = {};
	param[name] = value;
	var str = JSON.stringify(param);
	$("#posTable").datagrid('load',{
		attr : str
	});
};

var pos_edit_callback = function(data) {
	var success = data.success;
	var row = data.row;
	if(success) { // update thr row in the datagrid
		$('#posTable').datagrid('updateRow', {
			index: rowIndex,
			row: row
		});
		// close the dialog
		appDialog.closeDialog('posDialog');
	}
	
	// show message
	var msg = data.msg;
	appMessager.show('温馨提示', msg, 2000);
};

var pos_add_callback = function(data) {
	var success = data.success;
	if(success) {
		var row = data.row;
		$('#posTable').datagrid('insertRow', {
			index : 0,
			row: row
		});
		// close the dialog
		appDialog.closeDialog('posDialog');
	}
	
	// show message
	var msg = data.msg;
	appMessager.show('温馨提示', msg, 2000);
};

var pos_delete_callback = function(data) {
	var success = data.success;
	if(success) {
		var row = data.row;
		// get the delete id array
		var ids = row.split(',');
		for(var i = 0; i < ids.length; i++) {
			var id = ids[i];
			// get the row index
			var index = $('#posTable').datagrid('getRowIndex', id);
			// delete row from the datagrid
			$('#posTable').datagrid('deleteRow', index);
		}
	}
	var msg = data.msg;
	appMessager.show('温馨提示', msg, 2000);
};

var pos_edit = function() {
	var row = $("#posTable").datagrid('getSelected');
	if(row == null) {
		appMessager.alert('温馨提示', '请先选中要编辑的行');
		return ;
	}
	rowIndex = $('#posTable').datagrid('getRowIndex', row);
	var buttons = [{
		text: '更新',
		iconCls: 'icon-ok',
		handler: function(){
			var attr = serializeForm('posForm');
			var appModel = new AppModel();
			appModel.setAttr(attr);
			$.post('./backend/comm/positionAction_update', appModel,pos_edit_callback);
		}
	}, {
		text: '取消',
		iconCls: 'icon-cancel',
		handler: function(){
			appDialog.closeDialog('posDialog');
		}
	}];
	appDialog.createEditDialog('posDialog', './layout/backend/comm/pos/posForm.jsp', buttons, 400, 400, true, '更新职位信息', 'posForm', row);
};

var pos_add = function() {
	var buttons = [{
		text: '添加',
		iconCls: 'icon-ok',
		handler: function() {
			var attr = serializeForm('posForm');
			var appModel = new AppModel();
			appModel.setAttr(attr);
			$.post('./backend/comm/positionAction_insert', appModel, pos_add_callback);
		}
	}, {
		text: '取消',
		iconCls: 'icon-cancel',
		handler: function() {
			appDialog.closeDialog('posDialog');
		}
	}];
	appDialog.createAddDialog('posDialog', './layout/backend/comm/pos/posForm.jsp', buttons, 400, 400, true, '新增职位信息', 'posForm');
};

var pos_delete = function() {
	var rows = $("#posTable").datagrid('getSelections');
	if(rows.length > 0) {
		var appModel = new AppModel();
		var ids = [];
		for(var i = 0; i < rows.length; i++) {
			ids.push(rows[i].id);
		}
		appModel.setAttr(ids);
		$.post('./backend/comm/positionAction_multiDelete', appModel, pos_delete_callback);
	}
};

$('#pos_searchbox').searchbox({
    searcher: pos_search,
    menu:'#pos_searchMenu',
    prompt:'Please Input Value'
});

$("#posTable").datagrid({
	title: "职位信息",
	toolbar: '#pos_toolBar',
	url: './backend/comm/positionAction_queryByPage',
	method: 'get',
	columns:[[
	    {field:'check',title:'全选',checkbox:true},
	    {field:'id', title:'ID',hidden:true},
		{field:'name',title:'职位名',sortable:true,width:100},
		{field:'detail',title:'职位描述',width:300}
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
