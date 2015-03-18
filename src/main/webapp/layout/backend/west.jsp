<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div id="menu" class="easyui-accordion" data-options="fit:true, border:false"></div>
<script type="text/javascript">
var click_treenode = function(node) {
	if($("#" + node.id).tree('isLeaf', node.target)) {
		// 是叶子节点
		// 判断该id的tabs是否存在,存在则定位到特定的tabs
		if($("#tabs").tabs('exists', node.text)) {
			// var $tab = $("#tabs").tabs('getTab', node.text);
			$("#tabs").tabs('select', node.text);
		} else {
			// console.info("node.href = " + JSON.stringify(node.href));
			// 这里需要根据点击不同加载不同的data
			$("#tabs").tabs('add', {
				title: node.text,
				closable: true,
				href: node.href
			});
		}
	}
};

var build_menu_callback = function(data) {
	var success = data.success;
	if(success) {
		var rows = data.rows;
		for(var i = 0; i < rows.length; i++) {
			var row = rows[i];
			console.info(JSON.stringify(row));
			var content = "";
			if(typeof(row.children) != 'undefined') {
				content = $('<ul />').attr('id', row.id).attr('class', 'easyui-tree').tree({
					onClick: click_treenode
				}).tree('loadData', row.children);
			}
			$('#menu').accordion('add', {
				title: row.text,
				content: content,
				selected: false
			});
		}
	}
};

var build_menu = function() {
	$.get('./backend/sys/menuAction_queryMenuTree', null, build_menu_callback);
};


$(document).ready(function(){
	build_menu();
});
</script>