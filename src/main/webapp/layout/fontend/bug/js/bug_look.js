// 弹出modal dialog 以实现编辑bug信息的功能
function editBug(e, value, row, index) {
	var obj = $.extend(true, {}, row);
	var qa = row.qa;
	var dev = row.dev;
	var project = row.project;
	obj.pjName = project.name;
	obj.qaName = qa.name;
	obj.devName = dev.name;
	fillForm('bugForm', obj);
	$('#bugModal').attr('type', 'update');
	$('#bugModal').modal({
		show: true,
		backdrop: 'static'
	});
};

// 异步方式删除bug信息
function deleteBug(e, value, row, index) {
	var id = row.id;
};

// 弹出modal dialog 显示bug跟踪信息
function lookBugTrace(e, value, row, index) {
	/*if(!$('#bugTracePanel').is(':hidden')) {
		$('#bugTracePanel').hide();
		return ;
	}*/
	var id = row.id;
	$('#bugTraceTable').bootstrapTable('destroy');
	$('#bugTraceTable').bootstrapTable({
		method: 'get',
		url: './fontend/comm/bugTraceAction_queryByPage',
		columns: [{
			field: 'id',
			title: 'ID',
			visible: false
		}, {
			field: 'title',
			title: '跟踪标题'
		}, {
			field: 'detail',
			title: '描述'
		}, {
			field: 'type',
			title: '类型'
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
		showRefresh: true
	});
	//$('#bugTracePanel').show();
	$('#lookBugTraceModal').modal({
		show: true,
		backdrop: 'static'
	});
};

// TODO 实现结束BUG的功能
function endBug(e, value, row, index) {};

// 弹出modal dialog 以实现创建bug跟踪信息的功能
function createBugTrace(e, value, row, index) {
	$('#bugTraceForm')[0].reset();
	$('#bid').val(row.id);
	var qa = row.qa;
	var dev = row.dev;
	$('#fromWhoId').val(dev.id);
	$('#fromWhoName').val(dev.name);
	$('#toWhoId').val(qa.id);
	$('#toWhoName').val(qa.name);
	$('#bugTraceModal').modal({
		show: true,
		backdrop: 'static' // 点击屏幕遮罩层不会关闭模态对话框的选项设置
	});
};

// 异步方式添加buginfo的回调函数
function addBug_callback(data) {
	if(data.success) {
		var row = data.row;
		$('#bugTable').bootstrapTable('append', row);
		$('#bugModal').modal('hide');
	}
};

// 异步方式添加bugtrace的回调函数
function addBugTrace_callback(data) {
	$('#bugTraceModal').modal('hide');
};

$(document).ready(function(){
	
	$('#type').change(function(){
		// 交换fromWhoId toWhoId 和 fromWhoName toWhoName 的值
		var tmpWhoId = $('#fromWhoId').val();
		$('#fromWhoId').val($('#toWhoId').val());
		$('#toWhoId').val(tmpWhoId);
		var tmpWhoName = $('#fromWhoName').val();
		$('#fromWhoName').val($('#toWhoName').val());
		$('#toWhoName').val(tmpWhoName);
	});
	
	$('#createBugBtn').click(function(){
		$('#bugForm')[0].reset();
		$('#bugModal').attr('type', 'add');
		$('#bugModal').modal({
			show: true,
			backdrop: 'static'
		});
	});
	
	$('#saveBugBtn').click(function(){
		var attr = serializeForm('bugForm');
		var appModel = new AppModel();
		appModel.setAttr(attr);
		var type = $('#bugModal').attr('type');
		if(type == 'add') {
			$.post('./fontend/bug/bugInfoAction_insert', appModel, addBug_callback);
		} else {
			$.post('./fontend/bug/bugInfoAction_update', appModel, updateBug_callback);
		}
	});
	
	$('#saveBugTraceBtn').click(function(){
		var attr = serializeForm('bugTraceForm');
		var appModel = new AppModel();
		appModel.setAttr(attr);
		$.post('./fontend/bug/bugTraceAction_insert', appModel, addBugTrace_callback);
	});
	
	$('#pjTable').bootstrapTable({
		method: 'get',
		url: './fontend/comm/projectAction_queryByPage',
		columns: [{
			field: 'check',
			title: '选择',
			radio: true
		}, {
			field: 'id',
			title: 'ID',
			visible: false
		}, {
			field: 'name',
			title: '项目名称'
		}, {
			field: 'detail',
			title: '项目简介'
		}, {
			field: 'pjGrName',
			title: '负责的项目组'
		}],
		responseHandler: function(res) {
			return res;
		},
		cache: false,
		pagination: true,
		sidePagination: 'server',
		pageSize: 5,
		pageList: [5,10,15,20],
		onCheck: function(row) {
			var pid = row.id;
			var pjName = row.name;
			$('#pid').val(pid);
			$('#pjName').val(pjName);
		}
	});
	
	// 供选择的员工列表
	$('#empTable').bootstrapTable({
		method: 'get',
		url: './fontend/comm/employeeAction_queryByPage',
		pagination: true,
		sidePagination: 'server',
		pageSize: 5,
		pageList: [5,10,15,20],
		columns: [{
			field: 'check',
			title: '选择',
			radio: true
		}, {
			field: 'id',
			title: 'ID',
			visible: false
		}, {
			field: 'name',
			title: '员工姓名',
			width: 100
		}, {
			field: 'dept',
			title: '所在部门',
			width: 120
		}],
		onCheck: function(row) {
			var devId = row.id;
			var devName = row.name;
			$('#devId').val(devId);
			$('#devName').val(devName);
		}
	});
	
	$('#bugTable').bootstrapTable({
		method: 'get',
		url: './fontend/bug/bugInfoAction_queryByPage',
		columns: [{
			field: 'operator',
			title: '操作',
			formatter: function(value, row, index) {
				return [
				        '<a class="btn btn-default btn-sm edBug mr10" href="javascript:void(0)" title="编辑BUG">',
				            '<span class="glyphicon glyphicon-edit"></span>',
				        '</a>',
				        '<a class="btn btn-default btn-sm rmBug mr10" href="javascript:void(0)" title="删除BUG">',
				            '<span class="glyphicon glyphicon-remove"></span>',
				        '</a>',
				        '<a class="btn btn-default btn-sm lookBugTrace mr10" href="javascript:void(0)" title="查看BUG跟踪">',
			            	'<span class="glyphicon glyphicon-eye-open"></span>',
			        	'</a>',
		        		'<a class="btn btn-default btn-sm endBug mr10" href="javascript:void(0)" title="结束BUG">',
	            			'<span class="glyphicon glyphicon-ok-circle"></span>',
	        			'</a>',
	        			'<a class="btn btn-default btn-sm createBugTrace mr10" href="javascript:void(0)" title="创建BUG跟踪">',
            				'<span class="glyphicon glyphicon-ok-circle"></span>',
        				'</a>'
			    ].join('');
			},
			events: {
		        'click .edBug': editBug,
		        'click .rmBug': deleteBug,
		        'click .lookBugTrace': lookBugTrace,
		        'click .endBug': endBug,
		        'click .createBugTrace': createBugTrace
			}
		}, {
			field: 'name',
			title: 'BUG编号'
		}, {
			field: 'title',
			title: 'BUG标题'
		}, {
			field: 'detail',
			title: 'BUG描述'
		}, {
			field: 'level',
			title: '级别'
		}, {
			field: 'qa',
			title: 'QA',
			formatter: function(value, row, index) {
				return row.qa.name;
			}
		}, {
			field: 'dev',
			title: 'DEV',
			formatter: function(value, row, index) {
				return row.dev.name;
			}
		}, {
			field: 'createTime',
			title: '创建时间'
		}, {
			field: 'preResolveDay',
			title: '预计解决时间'
		}, {
			field: 'realResolveDay',
			title: '实际解决时间'
		}, {
			field: 'resolveTime',
			title: '解决时间'
		}, {
			field: 'status',
			title: 'BUG状态'
		}],
		responseHandler: function(res) {
			return res;
		},
		cache: false,
		pagination: true,
		sidePagination: 'server',
		pageSize: 5,
		pageList: [5,10,15,20],
		toolbar: '#bugTable_toolbar',
		showRefresh: true
	});
});