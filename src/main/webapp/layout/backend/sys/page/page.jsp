<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
<table id="pageTable">
</table>
<div id="page_toolBar">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="page_add()">添加</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="page_edit()">编辑</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="page_delete()">删除</a>
	<input id="page_searchbox" style="width:300px;"/>
</div>
<div id="page_searchMenu" style="width:120px;">
	<div data-options="name:'code',iconCls:'icon-edit'">页面代码</div>
	<div data-options="name:'name',selected:true">页面名称</div>
</div>
<div id="pageDialog"></div>
<script type="text/javascript">

var rowIndex = -1;

var page_search = function(value, name) {
	var param = {};
	param[name] = value;
	var str = JSON.stringify(param);
	$("#pageTable").datagrid('load',{
		attr : str
	});
};

var page_edit_callback = function(data) {
	var success = data.success;
	var row = data.row;
	if(success) { // update thr row in the datagrid
		$('#pageTable').datagrid('updateRow', {
			index: rowIndex,
			row: row
		});
	}
	
	// close the dialog
	appDialog.closeDialog('pageDialog');
	
	// show message
	var msg = data.msg;
	appMessager.show('温馨提示', msg, 2000);
};

var page_add_callback = function(data) {
	var success = data.success;
	if(success) {
		var row = data.row;
		$('#pageTable').datagrid('insertRow', {
			index : 0,
			row: row
		});
	}
	
	// close the dialog
	appDialog.closeDialog('pageDialog');
	
	// show message
	var msg = data.msg;
	appMessager.show('温馨提示', msg, 2000);
};

var page_delete_callback = function(data) {
	var success = data.success;
	if(success) {
		var row = data.row;
		// get the delete id array
		var ids = row.split(',');
		for(var i = 0; i < ids.length; i++) {
			var id = ids[i];
			// get the row index
			var index = $('#pageTable').datagrid('getRowIndex', id);
			// delete row from the datagrid
			$('#pageTable').datagrid('deleteRow', index);
		}
	}
	var msg = data.msg;
	appMessager.show('温馨提示', msg, 2000);
};

var page_edit = function() {
	var row = $("#pageTable").datagrid('getSelected');
	if(row == null) {
		appMessager.alert('温馨提示', '请先选中要编辑的行');
		return ;
	}
	rowIndex = $('#pageTable').datagrid('getRowIndex', row);
	var buttons = [{
		text: '更新',
		iconCls: 'icon-ok',
		handler: function(){
			var attr = serializeForm('pageForm');
			console.info(attr);
			var appModel = new AppModel();
			appModel.setAttr(attr);
			$.post('./backend/sys/pageAction_update', appModel, page_edit_callback);
		}
	}, {
		text: '取消',
		iconCls: 'icon-cancel',
		handler: function(){
			appDialog.closeDialog('pageDialog');
		}
	}];
	appDialog.createEditDialog('pageDialog', './layout/backend/sys/page/pageForm.jsp', buttons, 400, 400, true, '更新页面信息', 'pageForm', row);
};

var page_add = function() {
	var buttons = [{
		text: '添加',
		iconCls: 'icon-ok',
		handler: function() {
			var attr = serializeForm('pageForm');
			var appModel = new AppModel();
			appModel.setAttr(attr);
			$.post('./backend/sys/pageAction_insert', appModel, page_add_callback);
		}
	}, {
		text: '取消',
		iconCls: 'icon-cancel',
		handler: function() {
			appDialog.closeDialog('pageDialog');
		}
	}];
	appDialog.createAddDialog('pageDialog', './layout/backend/sys/page/pageForm.jsp', buttons, 400, 400, true, '新增页面信息', 'pageForm');
};

var page_delete = function() {
	var rows = $("#pageTable").datagrid('getSelections');
	if(rows.length > 0) {
		var appModel = new AppModel();
		var ids = [];
		for(var i = 0; i < rows.length; i++) {
			ids.push(rows[i].id);
		}
		appModel.setAttr(ids);
		$.post('./backend/sys/pageAction_multiDelete', appModel, page_delete_callback);
	}
};

$('#page_searchbox').searchbox({
    searcher: page_search,
    menu:'#page_searchMenu',
    prompt:'Please Input Value'
});

$("#pageTable").datagrid({
	title: "页面信息",
	toolbar: '#page_toolBar',
	url: './backend/sys/pageAction_queryByPage',
	method: 'get',
	columns:[[
	    {field:'check',title:'全选',checkbox:true},
	    {field:'id', title:'ID',hidden:true},
		{field:'code',title:'页面代码',sortable:true,width:100},
		{field:'name',title:'页面名',width:100},
		{field:'url',title:'URL地址', width:300},
		{field:'createUser',title:'创建者', width:120},
		{field:'createTime',title:'创建时间', width:120}
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
