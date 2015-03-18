<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<div class="easyui-layout">
<form id="pageForm" method="post" data-options="region:'center',fit:true,border:false" style="padding:20px;">
	<table style="width:100%;height:auto;" cellpadding="5">
		<tr>
			<td><input type="hidden" name="id" /></td>
		</tr>
		<tr>
			<td><label for="code">页面代码</label></td>
			<td><input class="easyui-textbox" type="text" name="code" data-options="required:true"/></td>
		</tr>
		<tr>
			<td><label for="name">页面名称</label></td>
			<td><input class="easyui-textbox" type="text" name="name" data-options="required:true" /></td>
		</tr>
		<tr>
			<td style="width:25%;"><label for="url">URL地址</label></td>
			<td style="width:75%;"><input class="easyui-textbox" type="text" name="url" data-options="required:true" style="width:100%;"/></td>
		</tr>
	</table>
</form>
</div>
<script type="text/javascript">

</script>