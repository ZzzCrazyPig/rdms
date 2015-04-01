<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<div class="easyui-layout" data-options="fit: true">
	<div data-options="region: 'center', split: true" style="height: 300px;">
		<div id="empMapedTable_toolBar">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="unMapEmp()">删除</a>
		</div>
		<table id="empMapedTable"></table>
	</div>
	<div data-options="region:'south', split: true" style="height: 300px;">
		<table id="empMapTable"></table>
	</div>
</div>
<script type="text/javascript">

var unMapEmp_callback = function(data) {
	if(data.success) {
		// 更新empRoleMapTable上对应的行
		var role = data.row;
		var roleId = role.id;
		var index = $('#empRoleMapTable').datagrid('getRowIndex', roleId);
		$('#empRoleMapTable').datagrid('updateRow', {
			index: index,
			row: role
		});
		var ids = data.rows;
		console.info(ids);
		// 删除empMapedTable上选中的行
		for(var i = 0; i < ids.length; i++) {
			var id = ids[i];
			var index = $('#empMapedTable').datagrid('getRowIndex', id);
			$('#empMapedTable').datagrid('deleteRow', index);
		}
		// 重新加载empMapTable上的数据
		$('#empMapTable').datagrid('reload');
	}
	var msg = data.msg;
	appMessager.show('温馨提示', msg, 2000);
};

var unMapEmp = function() {
	var rows = $('#empMapedTable').datagrid('getSelections');
	if(rows.length > 0) {
		var appModel = new AppModel();
		var ids = [];
		for(var i = 0; i < rows.length; i++) {
			ids.push(rows[i].id);
		}
		var role = $('#empRoleMapTable').datagrid('getSelected');
		var roleId = role.id;
		appModel.setAttr({
			ids : ids,
			roleId : roleId
		});
		$.post('./backend/auth/roleAction_unMapEmp', appModel, unMapEmp_callback);
	} else {
		appMessager.show('温馨提示', '请至少选择一个要解除映射的员工', 2000);
	}
};

$(document).ready(function(){
	var row = $("#empRoleMapTable").datagrid('getSelected');
	var roleId = row.id;

	// 加载已经映射到该角色的员工
	var emps = row.emps;
	$('#empMapedTable').datagrid({
		title: '已经映射到该角色的员工列表',
		toolbar: '#empMapedTable_toolBar',
		columns: [[
			{field:'check',title:'全选',checkbox:true},
			{field:'id', title:'ID',hidden:true},
			{field:'name',title:'姓名',sortable:true,width:100},
			{field:'dept',title:'部门',width:150},
			{field:'position',title:'职位',width:200}
		]],
		data: emps,
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

	// 查找可供映射到该角色的员工
	$("#empMapTable").datagrid({
		title: "可映射到该角色的员工",
		url: './backend/auth/roleAction_queryAvailableEmp',
		method: 'get',
		queryParams: {
			attr: JSON.stringify({id: roleId})
		},
		columns:[[
		    {field:'check',title:'全选',checkbox:true},
		    {field:'id', title:'ID',hidden:true},
			{field:'name',title:'姓名',width:100},
			{field:'dept',title:'部门',width:150},
			{field:'position',title:'职位',width:200}
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
});

</script>