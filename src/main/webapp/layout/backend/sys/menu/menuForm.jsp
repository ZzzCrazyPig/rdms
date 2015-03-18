<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<div class="easyui-layout">
<form id="menuForm" method="post" data-options="region:'center',fit:true,border:false" style="padding:20px;">
	<table style="width:100%;height:auto;" cellpadding="5">
		<tr>
			<td><input type="hidden" name="id" /></td>
		</tr>
		<tr>
			<td><label for="code">菜单代码</label></td>
			<td><input class="easyui-textbox" type="text" name="code" data-options="required:true"/></td>
		</tr>
		<tr>
			<td><label for="name">菜单名称</label></td>
			<td><input class="easyui-textbox" type="text" name="name" data-options="required:true" /></td>
		</tr>
		<tr>
			<td><label for="parentId">父菜单</label></td>
			<td><input id="parent" class="easyui-combotree" name="parentId"/></td>
		</tr>
		<tr>
			<td><label for="pageCode">页面代码</label></td>
			<td><input class="easyui-textbox" name="pageCode" /></td>
		</tr>
		<tr>
			<td><label for="sortIndex">排序位置</label></td>
			<td><input class="easyui-textbox" type="text" name="sortIndex" data-options="required:true" /></td>
		</tr>
	</table>
</form>
</div>
<script type="text/javascript">

var load_cbb_parent_callback = function(data) {
	var success = data.success;
	if(success) {
		$('#parent').combotree('loadData', data.rows);
	}
};

var load_cbb_parent = function() {
	$.get('./backend/sys/menuAction_queryComboTree', null, load_cbb_parent_callback);
};

// 加载父菜单下拉框
load_cbb_parent();

</script>