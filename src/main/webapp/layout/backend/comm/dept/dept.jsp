<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
<table id="deptTable">
</table>
<div id="dept_toolBar">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="dept_add()">添加</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="dept_edit()">编辑</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="dept_delete()">删除</a>
	<input id="dept_searchbox" style="width:300px;"/>
</div>
<div id="dept_searchMenu" style="width:120px;">
	<div data-options="name:'name',iconCls:'icon-edit'">部门名</div>
	<div data-options="name:'deptCode',selected:true">部门代码</div>
</div>
<div id="deptDialog"></div>
<script type="text/javascript">

var rowIndex = -1;

var dept_search = function(value, name) {
	var param = {};
	param[name] = value;
	var str = JSON.stringify(param);
	$("#deptTable").datagrid('load',{
		attr : str
	});
};

var dept_edit_callback = function(data) {
	var success = data.success;
	var row = data.row;
	if(success) { // update thr row in the datagrid
		$('#deptTable').datagrid('updateRow', {
			index: rowIndex,
			row: row
		});
		// close the dialog
		appDialog.closeDialog('deptDialog');
	}
	
	// show message
	var msg = data.msg;
	appMessager.show('温馨提示', msg, 2000);
};

var dept_add_callback = function(data) {
	var success = data.success;
	if(success) {
		var row = data.row;
		$('#deptTable').datagrid('insertRow', {
			index : 0,
			row: row
		});
		// close the dialog
		appDialog.closeDialog('deptDialog');
	}
	
	// show message
	var msg = data.msg;
	appMessager.show('温馨提示', msg, 2000);
};

var dept_delete_callback = function(data) {
	var success = data.success;
	if(success) {
		var row = data.row;
		// get the delete id array
		var ids = row.split(',');
		for(var i = 0; i < ids.length; i++) {
			var id = ids[i];
			// get the row index
			var index = $('#deptTable').datagrid('getRowIndex', id);
			// delete row from the datagrid
			$('#deptTable').datagrid('deleteRow', index);
		}
	}
	var msg = data.msg;
	appMessager.show('温馨提示', msg, 2000);
};

var dept_edit = function() {
	var row = $("#deptTable").datagrid('getSelected');
	if(row == null) {
		appMessager.alert('温馨提示', '请先选中要编辑的行');
		return ;
	}
	rowIndex = $('#deptTable').datagrid('getRowIndex', row);
	var buttons = [{
		text: '更新',
		iconCls: 'icon-ok',
		handler: function(){
			var attr = serializeForm('deptForm');
			var appModel = new AppModel();
			appModel.setAttr(attr);
			$.post('./backend/comm/departmentAction_update', appModel,dept_edit_callback);
		}
	}, {
		text: '取消',
		iconCls: 'icon-cancel',
		handler: function(){
			appDialog.closeDialog('deptDialog');
		}
	}];
	appDialog.createEditDialog('deptDialog', './layout/backend/comm/dept/deptForm.jsp', buttons, 400, 400, true, '更新部门信息', 'deptForm', row);
};

var dept_add = function() {
	var buttons = [{
		text: '添加',
		iconCls: 'icon-ok',
		handler: function() {
			if($('#deptCode').validatebox('isValid')) {
				var attr = serializeForm('deptForm');
				var appModel = new AppModel();
				appModel.setAttr(attr);
				$.post('./backend/comm/departmentAction_insert', appModel, dept_add_callback);
			} else {
				appMessager.alert('警告','输入项不符合要求');
			}
		}
	}, {
		text: '取消',
		iconCls: 'icon-cancel',
		handler: function() {
			appDialog.closeDialog('deptDialog');
		}
	}];
	appDialog.createAddDialog('deptDialog', './layout/backend/comm/dept/deptForm.jsp', buttons, 400, 400, true, '新增部门信息', 'deptForm');
};

var dept_delete = function() {
	var rows = $("#deptTable").datagrid('getSelections');
	if(rows.length > 0) {
		var appModel = new AppModel();
		var ids = [];
		for(var i = 0; i < rows.length; i++) {
			ids.push(rows[i].id);
		}
		appModel.setAttr(ids);
		$.post('./backend/comm/departmentAction_multiDelete', appModel, dept_delete_callback);
	}
};

$('#dept_searchbox').searchbox({
    searcher: dept_search,
    menu:'#dept_searchMenu',
    prompt:'Please Input Value'
});

$("#deptTable").datagrid({
	title: "部门信息",
	toolbar: '#dept_toolBar',
	url: './backend/comm/departmentAction_queryByPage',
	method: 'get',
	columns:[[
	    {field:'check',title:'全选',checkbox:true},
	    {field:'id', title:'ID',hidden:true},
		{field:'name',title:'部门名',sortable:true,width:100},
		/*{field:'pwd',title:'密码',width:150},*/
		{field:'deptCode',title:'部门代码',width:100},
		{field:'memTotal',title:'部门人数', width:80}
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
