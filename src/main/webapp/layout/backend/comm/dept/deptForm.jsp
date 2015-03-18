<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<div class="easyui-layout">
<form id="deptForm" method="post" data-options="region:'center',fit:true,border:false" style="padding:20px;">
	<table style="width:100%;height:auto;" cellpadding="5">
		<tr>
			<td><input type="hidden" name="id" /></td>
		</tr>
		<tr>
			<td><label for="name">部门名</label></td>
			<td><input class="easyui-textbox" type="text" name="name" data-options="required:true" /></td>
		</tr>
		<tr>
			<td><label for="deptCode">部门代码</label></td>
			<td><input id="deptCode" class="easyui-validatebox easyui-textbox" name="deptCode" data-options="required:true, 
				validType:'length[3,3]'" /></td>
		</tr>
		<tr>
			<td><label for="memTotal">部门人数</label></td>
			<td><input class="easyui-textbox" type="text" name="memTotal" data-options="required:true" /></td>
		</tr>
	</table>
</form>
</div>