<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<div class="easyui-layout">
<form id="roleForm" method="post" data-options="region:'center',fit:true,border:false" style="padding:20px;">
	<table style="width:100%;height:auto;" cellpadding="5">
		<tr>
			<td><input type="hidden" name="id" /></td>
		</tr>
		<tr>
			<td><label for="name">角色名</label></td>
			<td><input class="easyui-textbox" type="text" name="name" data-options="required:true" /></td>
		</tr>
		<tr>
			<td><label for="detail">角色描述</label></td>
			<td><input class="easyui-textbox" type="text" name="detail" data-options="required:true, multiline:true" style="height: 60px;" /></td>
		</tr>
	</table>
</form>
</div>