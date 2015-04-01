<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
<table id="empRoleMapTable">
</table>
<div id="empRoleMap_toolBar">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="empRoleMap_add()">添加映射</a>
	<input id="empRoleMap_searchbox" style="width:300px;"/>
</div>
<div id="empRoleMap_searchMenu" style="width:120px;">
	<div data-options="name:'name',iconCls:'icon-edit'">角色名</div>
</div>
<div id="empRoleMapDialog"></div>
<script type="text/javascript">

var rowIndex = -1;

var empRoleMap_search = function(value, name) {
	var param = {};
	param[name] = value;
	var str = JSON.stringify(param);
	$("#empRoleMapTable").datagrid('load',{
		attr : str
	});
};

// 员工映射到角色的回调函数
var empRoleMap_add_callback = function(data) {
	var success = data.success;
	if(success) {
		// TODO 删除empMapTable中已经映射过的行
		var ids = data.rows;
		for(var i = 0; i < ids.length; i++) {
			var id = ids[i];
			// get the row index
			var index = $('#empMapTable').datagrid('getRowIndex', id);
			// delete row from the datagrid
			$('#empMapTable').datagrid('deleteRow', index);
		}
		// TODO 更新roleTable中所选中的行
		var role = data.row;
		var roleId = role.id;
		var index = $('#empRoleMapTable').datagrid('getRowIndex', roleId);
		$('#empRoleMapTable').datagrid('updateRow', {
			index: index,
			row: role
		});
		// TODO 更新empMapedTable
		var emps = role.emps;
		$('#empMapedTable').datagrid('loadData', emps);
	}
	// show message
	var msg = data.msg;
	appMessager.show('温馨提示', msg, 2000);
};

var empRoleMap_delete_callback = function(data) {
	var success = data.success;
	if(success) {
		var row = data.row;
		// get the delete id array
		var ids = row.split(',');
		for(var i = 0; i < ids.length; i++) {
			var id = ids[i];
			// get the row index
			var index = $('#empRoleMapTable').datagrid('getRowIndex', id);
			// delete row from the datagrid
			$('#empRoleMapTable').datagrid('deleteRow', index);
		}
	}
	var msg = data.msg;
	appMessager.show('温馨提示', msg, 2000);
};

// 加载可以映射到该角色的员工
var empRoleMap_add = function() {
	var row = $("#empRoleMapTable").datagrid('getSelected');
	if(row == null) {
		appMessager.show('温馨提示', '请选中需要映射的角色', 2000);
		return ;
	}
	var buttons = [{
		text: '添加',
		iconCls: 'icon-ok',
		handler: function() {
			var rows = $('#empMapTable').datagrid('getSelections');
			if(rows.length > 0) {
				var appModel = new AppModel();
				var ids = [];
				for(var i = 0; i < rows.length; i++) {
					ids.push(rows[i].id);
				}
				var selRow = $("#empRoleMapTable").datagrid('getSelected');
				var roleId = selRow.id;
				appModel.setAttr({
					ids : ids,
					roleId : roleId
				});
				$.post('./backend/auth/roleAction_mapEmp', appModel, empRoleMap_add_callback);
			} else {
				appMessager.show('温馨提示', '请选择至少一条记录', 2000);
			}
		}
	}, {
		text: '取消',
		iconCls: 'icon-cancel',
		handler: function() {
			appDialog.closeDialog('empRoleMapDialog');
		}
	}];
	appDialog.createDefaultDialog('empRoleMapDialog', './layout/backend/auth/emp_role_map/empRoleMapForm.jsp', buttons, 600, 600, true, '员工-角色映射');
};

var empRoleMap_delete = function() {
	var rows = $("#empRoleMapTable").datagrid('getSelections');
	if(rows.length > 0) {
		var appModel = new AppModel();
		var ids = [];
		for(var i = 0; i < rows.length; i++) {
			ids.push(rows[i].id);
		}
		appModel.setAttr(ids);
		$.post('./backend/auth/roleAction_multiDelete', appModel, empRoleMap_delete_callback);
	}
};

$('#empRoleMap_searchbox').searchbox({
    searcher: empRoleMap_search,
    menu:'#empRoleMap_searchMenu',
    prompt:'Please Input Value'
});

$("#empRoleMapTable").datagrid({
	title: "员工-角色映射信息",
	toolbar: '#empRoleMap_toolBar',
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
