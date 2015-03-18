// 添加项目之后的回调函数
function addPj_callback(data) {
	if(data.success) {
		var row = data.row;
		$('#pjTable').bootstrapTable('append', row);
		$('#pjForm')[0].reset();
		$('#pjModal').modal('hide');
	}
};

// 更新项目之后的回调函数
function updatePj_callback(data) {
	if(data.success) {
		$('#pjTable').bootstrapTable('refresh', {slient: true});
		$('#pjModal').modal('hide');
	}
};

// 异步方式添加项目阶段信息的回调函数
function addPjStage_callback(data) {
	if(data.success) {
		var row = data.row;
		$('#pjStageTable').bootstrapTable('append', row);
		$('#pjStageForm')[0].reset();
		$('#pjStageModal').modal('hide');
	}
};

// 异步方式更新项目阶段信息的回调函数
function updatePjStage_callback(data) {
	if(data.success) {
		$('#pjStageTable').bootstrapTable('refresh', {slient: true});
		$('#pjStageModal').modal('hide');
	}
};

// 异步方式添加项目里程碑的回调函数
function addPjMark_callback(data) {
	if(data.success) {
		var row = data.row;
		$('#pjMarkTable').bootstrapTable('append', row);
		$('#pjMarkForm')[0].reset();
		$('#pjMarkModal').modal('hide');
	}
};

// 弹出modal dialog以实现编辑项目信息的功能
function editPj(e, value, row, index) {
	$('#pjModal').attr('type', 'update');
	fillForm('pjForm', row);
	$('#pjModal').modal({
		show: true,
		backdrop: 'static'
	});
};

// 弹出modal dialog以实现编辑项目阶段信息的功能
function editPjStage(e, value, row, index) {
	$('#pjStageModal').attr('type', 'update');
	fillForm('pjStageForm', row);
	$('#pjStageModal').modal({
		show: true,
		backdrop: 'static'
	});
};

// 异步方式删除项目
function deletePj(e, value, row, index) {
	var id = row.id;
	var appModel = new AppModel();
	appModel.setAttr({
		id: id
	});
	$.get('./fontend/comm/projectAction_delete', appModel, function(data){
		if(data.success) {
			var values = [];
			values.push(id);
			$('#pjTable').bootstrapTable('remove', {
				field: 'id',
				values: values
			});
		}
	});
};

// 异步方式删除项目阶段信息
function deletePjStage(e, value, row, index) {
	var id = row.id;
	var appModel = new AppModel();
	appModel.setAttr({
		id: id
	});
	$.get('./fontend/comm/pjStageAction_delete', appModel, function(data){
		if(data.success) {
			var values = [];
			values.push(id);
			$('#pjStageTable').bootstrapTable('remove', {
				field: 'id',
				values: values
			});
		}
	});
};

// 查看特定项目的项目阶段信息
function lookPjStage(e, value, row, index) {
	var $panel = $('#pjStagePanel');
	if(!$panel.is(':hidden')) {
		$('#pjStageTable').bootstrapTable('destroy');
		$panel.hide();
		return;
	}
	var pid = row.id;
	$panel.attr('data-pid', pid);
	$('#pjStageTable').bootstrapTable({
		method: 'get',
		url: './fontend/comm/pjStageAction_queryByPage',
		queryParams: function(params) {
			params.attr = JSON.stringify({pid: pid});
			return params;
		},
		columns: [{
			field: 'operator',
			title: '操作',
			formatter: function(value, row, index) {
				return [
				        '<a class="btn btn-default btn-sm edPjStage mr10" href="javascript:void(0)" title="编辑项目阶段">',
				            '<span class="glyphicon glyphicon-edit"></span>',
				        '</a>',
				        '<a class="btn btn-default btn-sm rmPjStage mr10" href="javascript:void(0)" title="删除项目阶段">',
				            '<span class="glyphicon glyphicon-remove"></span>',
				        '</a>'
			    ].join('');
			},
			events: {
		        'click .edPjStage': editPjStage,
		        'click .rmPjStage': deletePjStage
			}
		}, {
			field: 'id',
			title: 'ID',
			visible: false
		}, {
			field: 'name',
			title: '阶段名称'
		}, {
			field: 'createTime',
			title: '创建时间'
		}, {
			field: 'startTime',
			title: '开始时间'
		}, {
			field: 'endTime',
			title: '结束时间'
		}, {
			field: 'preCompleteDay',
			title: '预计完成时间'
		}, {
			field: 'realCompleteDay',
			title: '实际完成时间'
		}, {
			field: 'progress',
			title: '阶段进度'
		}, {
			field: 'status',
			title: '阶段状态'
		}],
		responseHandler: function(res) {
			return res;
		},
		cache: false,
		pagination: true,
		sidePagination: 'server',
		pageSize: 5,
		pageList: [5,10,15,20],
		//search: true,
		//showColumns: true,
		showRefresh: true
		//showToggle: true
	});
	$panel.show();
};

// 查看特定项目的项目里程碑信息
function lookPjMark(e, value, row, index) {
	var $panel = $('#pjMarkPanel');
	if(!$panel.is(':hidden')) {
		$('#pjMarkTable').bootstrapTable('destroy');
		$panel.hide();
		return;
	}
	var pid = row.id;
	$panel.attr('data-pid', pid);
	$('#pjMarkTable').bootstrapTable({
		method: 'get',
		url: './fontend/comm/pjMarkAction_queryByPage',
		queryParams: function(params) {
			params.attr = params.attr = JSON.stringify({pid: pid});
			return params;
		},
		columns: [{
			field: 'operator',
			title: '操作',
			formatter: function(value, row, index) {
				return [
				        '<a class="btn btn-default btn-sm edPjMark mr10" href="javascript:void(0)" title="编辑里程碑">',
				            '<span class="glyphicon glyphicon-edit"></span>',
				        '</a>',
				        '<a class="btn btn-default btn-sm rmPjMark mr10" href="javascript:void(0)" title="删除里程碑">',
				            '<span class="glyphicon glyphicon-remove"></span>',
				        '</a>'
			    ].join('');
			},
			events: {
		        'click .edPjStage': function (e, value, row, index) {
		        	// TODO 实现编辑项目阶段功能
		            console.info(row);
		        },
		        'click .rmPjStage': function (e, value, row, index) {
		        	// TODO 实现删除项目阶段功能
		            console.info(row);
		        }
			}
		}, {
			field: 'id',
			title: 'ID',
			visible: false
		}, {
			field: 'createTime',
			title: '创建时间'
		}, {
			field: 'content',
			title: '里程碑内容'
		}, {
			field: 'attachment',
			title: '附件'
		}],
		responseHandler: function(res) {
			return res;
		},
		cache: false,
		pagination: true,
		sidePagination: 'server',
		pageSize: 5,
		pageList: [5,10,15,20],
		//search: true,
		//showColumns: true,
		showRefresh: true
		//showToggle: true
	});
	$panel.show();
};

// 异步方式结束项目
function endPj(e, value, row, index) {
	var startTime = new Date(row.startTime.replace(/-/g,"/"));
	var endTime = new Date();
	var times = endTime.getTime() - startTime.getTime();
	var realCompleteDay = Math.floor(times / (1000 * 60 * 60 * 24));
	row.endTime = endTime.format('yyyy-MM-dd');
	row.realCompleteDay = realCompleteDay;
	row.status = '已完成';
	row.progress = 100;
	var appModel = new AppModel();
	appModel.setAttr(row);
	$.post('./fontend/comm/projectAction_update', appModel, function(data){
		if(data.success) {
			// 更新表格
			$('#pjTable').bootstrapTable('updateRow', {
				index: index,
				row: row
			});
		}
	});
};

$(document).ready(function(){
	
	$('#createPjBtn').click(function(){
		$('#pjForm')[0].reset();
		$('#pjModal').attr('type', 'add');
		$('#pjModal').modal({
			show: true,
			backdrop: 'static'
		});
	});
	
	$('#createPjStageBtn').click(function(){
		$('#pjStageForm')[0].reset();
		$('#pjStageModal').attr('type', 'add');
		$('#pjStageModal').modal({
			show: true,
			backdrop: 'static'
		});
	});
	
	$('#createPjMarkBtn').click(function(){
		$('#pjMarkForm')[0].reset();
		$('#pjMarkModal').modal('show');
	});
	
	// 显示日历选择器
	$('#pj_startTime').datepicker({
		language: 'zh-CN',
		todayHighlight : true,
		format: 'yyyy-mm-dd'
	});
	
	$('#pjStage_startTime').datepicker({
		language: 'zh-CN',
		todayHighlight : true,
		format: 'yyyy-mm-dd'
	});
	
	$('#savePjBtn').click(function(){
		var attr = serializeForm('pjForm');
		var appModel = new AppModel();
		appModel.setAttr(attr);
		var type = $('#pjModal').attr('type');
		if(type == "add") {
			$.post('./fontend/comm/projectAction_insert', appModel, addPj_callback);
		} else {
			$.post('./fontend/comm/projectAction_update', appModel, updatePj_callback);
		}
	});
	
	$('#savePjStageBtn').click(function(){
		var attr = serializeForm('pjStageForm');
		var pid = $('#pjStagePanel').attr('data-pid');
		attr.pid = pid;
		var appModel = new AppModel();
		appModel.setAttr(attr);
		var type = $('#pjStageModal').attr('type');
		if(type == 'add') {
			$.post('./fontend/comm/pjStageAction_insert', appModel, addPjStage_callback);
		} else {
			$.post('./fontend/comm/pjStageAction_update', appModel, updatePjStage_callback);
		}
	});
	
	$('#savePjMarkBtn').click(function(){
		var attr = serializeForm('pjMarkForm');
		var pid = $('#pjMarkPanel').attr('data-pid');
		attr.pid = pid;
		var appModel = new AppModel();
		appModel.setAttr(attr);
		$.post('./fontend/comm/pjMarkAction_insert', appModel, addPjMark_callback);
	});
	
	$('#pjGrTable').bootstrapTable({
		method: 'get',
		url: './fontend/comm/pjGroupAction_queryByPage',
		columns: [{
			field: 'checked',
			title: '选择',
			radio: true
		}, {
			field: 'id',
			title: 'ID',
			visible: false
		}, {
			field: 'name',
			title: '项目组名称'
		}],
		responseHandler: function(res) {
			return res;
		},
		cache: false,
		pagination: true,
		sidePagination: 'server',
		pageSize: 5,
		pageList: [5,10,15,20],
		//search: true,
		showRefresh: true,
		onCheck: function(row) {
			var pjGrId = row.id;
			var pjGrName = row.name;
			$('#pjGrId').val(pjGrId);
			$('#pjGrName').val(pjGrName);
		}
	});
	
	$('#pjTable').bootstrapTable({
		method: 'get',
		url: './fontend/comm/projectAction_queryByPage',
		columns: [{
			field: 'operator',
			title: '操作',
			formatter: function(value, row, index) {
				return [
				        '<a class="btn btn-default btn-sm edPj mr10" href="javascript:void(0)" title="编辑项目">',
				            '<span class="glyphicon glyphicon-edit"></span>',
				        '</a>',
				        '<a class="btn btn-default btn-sm rmPj mr10" href="javascript:void(0)" title="删除项目">',
				            '<span class="glyphicon glyphicon-remove"></span>',
				        '</a>',
				        '<a class="btn btn-default btn-sm lookPjStage mr10" href="javascript:void(0)" title="查看项目阶段信息">',
			            	'<span class="glyphicon glyphicon-eye-open"></span>',
			        	'</a>',
			        	'<a class="btn btn-default btn-sm lookPjMark mr10" href="javascript:void(0)" title="查看项目里程碑">',
		            		'<span class="glyphicon glyphicon-map-marker"></span>',
		        		'</a>',
		        		'<a class="btn btn-default btn-sm endPj mr10" href="javascript:void(0)" title="结束项目">',
	            			'<span class="glyphicon glyphicon-ok-circle"></span>',
	        			'</a>'
			    ].join('');
			},
			events: {
		        'click .edPj': editPj,
		        'click .rmPj': deletePj,
		        'click .lookPjStage': lookPjStage,
		        'click .lookPjMark': lookPjMark,
		        'click .endPj': endPj
			}
		}, {
			field: 'name',
			title: '项目名称'
		}, {
			field: 'detail',
			title: '项目简介'
		}, {
			field: 'pjGrName',
			title: '负责的项目组'
		}, {
			field: 'createTime',
			title: '创建时间'
		}, {
			field: 'startTime',
			title: '开始时间'
		}, {
			field: 'endTime',
			title: '结束时间'
		}, {
			field: 'preCompleteDay',
			title: '预计完成时间'
		}, {
			field: 'realCompleteDay',
			title: '实际完成时间'
		}, {
			field: 'progress',
			title: '项目进度'
		}, {
			field: 'status',
			title: '项目状态'
		}],
		responseHandler: function(res) {
			return res;
		},
		cache: false,
		pagination: true,
		sidePagination: 'server',
		pageSize: 5,
		pageList: [5,10,15,20],
		//search: true,
		//showColumns: true,
		showRefresh: true
	});
	
});