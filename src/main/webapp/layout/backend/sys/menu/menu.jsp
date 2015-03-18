<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
<table id="menuTable">
</table>
<div id="menu_toolBar">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="menu_add()">添加</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="menu_edit()">编辑</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="menu_delete()">删除</a>
	<input id="menu_searchbox" style="width:300px;"/>
</div>
<div id="menu_searchMenu" style="width:120px;">
	<div data-options="name:'code',iconCls:'icon-edit'">菜单代码</div>
	<div data-options="name:'name',selected:true">菜单名称</div>
</div>
<div id="menuDialog"></div>
<script type="text/javascript">


var menu_search = function(value, name) {
	var param = {};
	param[name] = value;
	var str = JSON.stringify(param);
	$("#menuTable").treegrid('load',{
		attr : str
	});
};

var menu_edit_callback = function(data) {
	var success = data.success;
	var row = data.row;
	if(success) { // update thr row in the treegrid
		$('#menuTable').treegrid('update', {
			id: row.id,
			row: row
		});
	}
	
	// close the dialog
	appDialog.closeDialog('menuDialog');
	
	// show message
	var msg = data.msg;
	appMessager.show('温馨提示', msg, 2000);
};

var menu_add_callback = function(data) {
	var success = data.success;
	if(success) {
		$('#menuTable').treegrid('reload');
	}
	
	// close the dialog
	appDialog.closeDialog('menuDialog');
	
	// show message
	var msg = data.msg;
	appMessager.show('温馨提示', msg, 2000);
};

var menu_delete_callback = function(data) {
	var success = data.success;
	if(success) {
		var row = data.row;
		// get the delete id array
		var ids = row.split(',');
		for(var i = 0; i < ids.length; i++) {
			var id = ids[i];
			// get the row index
			// delete row from the treegrid
			$('#menuTable').treegrid('remove', id);
		}
	}
	var msg = data.msg;
	appMessager.show('温馨提示', msg, 2000);
};

var menu_edit = function() {
	var row = $("#menuTable").treegrid('getSelected');
	if(row == null) {
		appMessager.alert('温馨提示', '请先选中要编辑的行');
		return ;
	}
	var buttons = [{
		text: '更新',
		iconCls: 'icon-ok',
		handler: function(){
			var attr = serializeForm('menuForm');
			console.info(attr);
			var appModel = new AppModel();
			appModel.setAttr(attr);
			$.post('./backend/sys/menuAction_update', appModel, menu_edit_callback);
		}
	}, {
		text: '取消',
		iconCls: 'icon-cancel',
		handler: function(){
			appDialog.closeDialog('menuDialog');
		}
	}];
	appDialog.createEditDialog('menuDialog', './layout/backend/sys/menu/menuForm.jsp', buttons, 400, 400, true, '更新菜单信息', 'menuForm', row);
};

var menu_add = function() {
	var buttons = [{
		text: '添加',
		iconCls: 'icon-ok',
		handler: function() {
			var attr = serializeForm('menuForm');
			var appModel = new AppModel();
			appModel.setAttr(attr);
			$.post('./backend/sys/menuAction_insert', appModel, menu_add_callback);
		}
	}, {
		text: '取消',
		iconCls: 'icon-cancel',
		handler: function() {
			appDialog.closeDialog('menuDialog');
		}
	}];
	appDialog.createAddDialog('menuDialog', './layout/backend/sys/menu/menuForm.jsp', buttons, 400, 400, true, '新增菜单信息', 'menuForm');
};

var menu_delete = function() {
	var rows = $("#menuTable").treegrid('getSelections');
	if(rows.length > 0) {
		var appModel = new AppModel();
		var ids = [];
		for(var i = 0; i < rows.length; i++) {
			ids.push(rows[i].id);
		}
		appModel.setAttr(ids);
		$.post('./backend/sys/menuAction_multiDelete', appModel, menu_delete_callback);
	}
};

$('#menu_searchbox').searchbox({
    searcher: menu_search,
    menu:'#menu_searchMenu',
    prompt:'Please Input Value'
});

$("#menuTable").treegrid({
	title: "菜单信息",
	toolbar: '#menu_toolBar',
	url: './backend/sys/menuAction_queryTreeByPage',
	method: 'get',
	columns:[[
	    {field:'check',title:'全选',checkbox:true},
	    {field:'id', title:'ID',hidden:true},
	    {field:'name',title:'菜单名',width:200},
		{field:'code',title:'菜单代码',sortable:true,width:100},
		{field:'sortIndex',title:'排序位置',sortable:true,width:80},
		{field:'pageCode',title:'页面代码',width:100},
		{field:'createUser',title:'创建者',width:120},
		{field:'createTime',title:'创建时间',width:120}
	]],
	idField: 'id',
	treeField: 'name',
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
