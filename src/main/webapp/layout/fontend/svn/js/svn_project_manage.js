// 调整iframe高度以适应页面大小
function autoSuitIframeHeight() {
	var ifm = document.getElementById("statsvn-frame");
	var subWeb = document.frames ? document.frames["statsvn-frame"].document
			: ifm.contentDocument;
	if (ifm != null && subWeb != null) {
		ifm.height = subWeb.body.scrollHeight;
	}
};

// 打开modal dialog以实现编辑svnPj信息的功能
function editSvnPj(e, value, row, index) {
	$('#svnPjModal').attr('type', 'update');
	fillForm('svnPjForm', row);
	$('#svnPjModal').modal({
		show: true,
		backdrop: 'static'
	});
};

// 删除svnPj信息
function deleteSvnPj(e, value, row, index) {
	var id = row.id;
	var appModel = new AppModel();
	appModel.setAttr({
		id: id
	});
	$.get('./fontend/svn/svnProjectAction_delete', appModel, function(data){
		if(data.success) {
			var values = [];
			values.push(id);
			$('#svnPjTable').bootstrapTable('remove', {
				field: 'id',
				values: values
			});
		}
	});
};

// 显示遮罩层
function showMark() {
	$('#body').css('overflow', 'hidden');
	$('#cover').show();
};

// 隐藏遮罩层
function hideMark() {
	$('#body').css('overflow', 'auto');
	$('#cover').hide();
}

// 调用statsvn实现svn项目统计
function statSvnPj(e, value, row, index) {
	var appModel = new AppModel();
	appModel.setAttr(row);
	// 显示遮罩层
	showMark();
	$.post('./fontend/svn/svnProjectAction_statsvn', appModel, function(data){
		if(data.success) {
			var $panelBody = $('#statsvnPanel').find('.panel-body').eq(0);
			$panelBody.children().remove();
			var $iframe = $('<iframe id="statsvn-frame" runat="server" frameborder="no" border="0" scrolling="no" allowtransparency="yes" width="100%" height="100%" onload="autoSuitIframeHeight()"></iframe>');
			var iframeSrc = data.row.iframeSrc;
			$iframe.attr('src', iframeSrc);
			$panelBody.append($iframe);
			$('#statsvnArea').show();
		}
		hideMark();
	});
	
};

// 异步添加svnPj的回调函数
function addSvnPj_callback(data) {
	if(data.success) {
		var row = data.row;
		$('#svnPjTable').bootstrapTable('append', row);
		$('#svnPjModal').modal('hide');
	}
};

// 异步更新svnPj的回调函数
function updateSvnPj_callback(data) {
	if(data.success) {
		$('#svnPjTable').bootstrapTable('refresh', {slient: true});
		$('#svnPjModal').modal('hide');
	}
};

$(document).ready(function(){
	
	$('#createSvnPjBtn').click(function(){
		$('#svnPjForm')[0].reset();
		$('#svnPjModal').attr('type', 'add');
		$('#svnPjModal').modal({
			show: true,
			backdrop: 'static'
		});
	});
	
	$('#saveSvnPjBtn').click(function(){
		var attr = serializeForm('svnPjForm');
		var appModel = new AppModel();
		appModel.setAttr(attr);
		var type = $('#svnPjModal').attr('type');
		if(type == "add") {
			$.post('./fontend/svn/svnProjectAction_insert', appModel, addSvnPj_callback);
		} else {
			$.post('./fontend/svn/svnProjectAction_update', appModel, updateSvnPj_callback);
		}
	});
	
	$('#pjTable').bootstrapTable({
		method: 'get',
		url: './fontend/comm/projectAction_queryByPage',
		columns: [{
			field: 'id',
			title: 'ID',
			visible: false
		}, {
			field: 'checked',
			title: '选择',
			radio: true
		}, {
			field: 'name',
			title: '项目名称'
		}],
		responseHandler: function(res) {
			return res;
		},
		cache: false,
		pagination: true,
		sidePagination: 'server',
		pageSize: 5,
		pageList: [5,10,15,20],
		search: true,
		showRefresh: true,
		onCheck: function(row) {
			var pid = row.id;
			var pjName = row.name;
			$('#pid').val(pid);
			$('#pjName').val(pjName);
		}
	});
	
	$('#svnPjTable').bootstrapTable({
		method: 'get',
		url: './fontend/svn/svnProjectAction_queryByPage',
		columns: [{
			field: 'operator',
			title: '操作',
			formatter: function(value, row, index) {
				return [
				        '<a class="btn btn-default btn-sm edSvnPj mr10" href="javascript:void(0)" title="编辑SVN项目">',
				            '<span class="glyphicon glyphicon-edit"></span>',
				        '</a>',
				        '<a class="btn btn-default btn-sm rmSvnPj mr10" href="javascript:void(0)" title="删除SVN项目">',
				            '<span class="glyphicon glyphicon-remove"></span>',
				        '</a>',
				        '<a class="btn btn-default btn-sm statSvnPj mr10" href="javascript:void(0)" title="SVN代码统计">',
			            	'<span class="glyphicon glyphicon-eye-open"></span>',
			        	'</a>'
			    ].join('');
			},
			events: {
		        'click .edSvnPj': editSvnPj,
		        'click .rmSvnPj': deleteSvnPj,
		        'click .statSvnPj': statSvnPj
			}
		}, {
			field: 'pjName',
			title: '所属项目'
		}, {
			field: 'url',
			title: '访问地址'
		}, {
			field: 'detail',
			title: '描述信息'
		}],
		responseHandler: function(res) {
			return res;
		},
		cache: false,
		pagination: true,
		sidePagination: 'server',
		pageSize: 5,
		pageList: [5,10,15,20],
		toolbar: '#svnPjTable_toolbar',
		search: true,
		showColumns: true,
		showRefresh: true
	});
});