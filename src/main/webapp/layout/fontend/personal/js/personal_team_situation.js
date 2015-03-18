$(document).ready(function(){
	// 点击创建项目组按钮的响应动作
	$('#createPjGroupBtn').click(function(){
		$('#pjGroupForm')[0].reset();
		$('#pjGroupModal').attr('type', 'add');
		$('#pjGroupModal').modal('show');
	});
	
	// 保存项目组成员的响应动作
	$('#addPjGrMemBtn').click(function(){
		var data = $('#newPjGrMemsTable').bootstrapTable('getData');
		var pjGrId = $('#pjGrId').val();
		var appModel = new AppModel();
		var rows = [];
		for(var i = 0; i < data.length; i++) {
			var d = data[i];
			var row = {
				pjGrId: pjGrId,
				eid: d.memId,
				role: d.memRole
			};
			rows.push(row);
		}
		appModel.setAttr(rows);
		$.post('./fontend/comm/pjGrMemberAction_multiInsert', appModel, addPjGrMem_callback);
	});
	
	// 新建项目组的响应动作
	$('#savePjGroupBtn').click(function(){
		var attr = serializeForm('pjGroupForm');
		var appModel = new AppModel();
		appModel.setAttr(attr);
		var type = $('#pjGroupModal').attr('type');
		if(type == "add") {
			$.post('./fontend/comm/pjGroupAction_insert', appModel, addPjGroup_callback);
		} else {
			$.post('./fontend/comm/pjGroupAction_update', appModel, function(data){
				if(data.success) {
					$('#myCreatePjGroup').bootstrapTable('refresh', {slient: true});
				}
			});
		}
	});
	
	// 待添加的项目组成员列表
	$('#newPjGrMemsTable').bootstrapTable({
		pagination: false,
		search: false,
		showColumns: false,
		showRefresh: false,
		clickToSelect: false,
		idField: 'memId',
		columns: [{
			field: 'memId',
			title: 'ID',
			visible: false
		}, {
			field: 'memName',
			title: '项目成员',
			width: 100
		}, {
			field: 'memDept',
			title: '所在部门',
			width: 120
		}, {
			field: 'memRole',
			title: '成员角色',
			width: 100,
			formatter: function(value, row, index) {
				row.memRole = "开发人员";
				var html = '<select class="selMemRole form-control">' + 
								'<option value="开发人员">开发人员</option>' +
								'<option value="测试人员">测试人员</option>' +
							'</select>';
				return html;
			},
			events: {
				'change .selMemRole': function(e, value, row, index) {
					var selected = $(this).children('option:selected').val();
					row.memRole = selected;
				}
			}
		}]
	});
	
	// 供选择的员工列表
	$('#empTable').bootstrapTable({
		pagination: true,
		sidePagination: 'client',
		pageSize: 5,
		pageList: [5,10,15,20],
		search: true,
		showColumns: true,
		showRefresh: true,
		clickToSelect: true,
		columns: [{
			field: 'state',
			checkbox: true
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
			var memId = row.id;
			var memName = row.name;
			var memDept = row.dept;
			$('#newPjGrMemsTable').bootstrapTable('append', {
				memId: memId,
				memName: memName,
				memDept: memDept
			});
		},
		onUncheck: function(row) {
			var memId = row.id;
			var values = [];
			values.push(memId);
			$('#newPjGrMemsTable').bootstrapTable('remove', {
				field: 'memId',
				values: values
			});
		}
	});
	
	$('#myCreatePjGroup').bootstrapTable({
		method: 'get',
		url: './fontend/comm/pjGroupAction_queryByPage',
		queryParams: function(params) {
			var eid = $('#login_user').attr('data');
			params.attr = JSON.stringify({
				createUser: eid
			});
			return params;
		},
		cache: false,
		pagination: true,
		sidePagination: 'server',
		pageSize: 5,
		pageList: [5,10,15,20],
		toolbar: '#myCreatePjGroup_toolbar',
		//search: true,
		//showColumns: true,
		showRefresh: true,
		clickToSelect: false,
		columns: [{
			field: 'operator',
			title: '操作',
			width: 300,
			halign: 'center',
			align: 'center',
			valign: 'middle',
			clickToSelect: false,
			formatter: function(value, row, index) {
				return [
				        '<a class="btn btn-default btn-sm edPjGr mr10" href="javascript:void(0)" title="编辑项目组">',
				            '<span class="glyphicon glyphicon-edit"></span>',
				        '</a>',
				        '<a class="btn btn-default btn-sm rmPjGr mr10" href="javascript:void(0)" title="删除项目组">',
				            '<span class="glyphicon glyphicon-remove"></span>',
				        '</a>',
				        '<a class="btn btn-default btn-sm addPjGrMem mr10" href="javascript:void(0)" title="添加组成员">',
				        	'<span class="glyphicon glyphicon-user"></span>',
				    	'</a>',
				    	'<a class="btn btn-default btn-sm managePjGrMem mr10" href="javascript:void(0)" title="管理组成员">',
			        		'<span class="glyphicon glyphicon-user"></span>',
			    		'</a>'
				    ].join('');
			},
			events: {
		        'click .edPjGr': editPjGroup,
		        'click .rmPjGr': deletePjGroup,
		        'click .addPjGrMem': addPjGrMem,
		        'click .managePjGrMem': managePjGrMem
			}
		}, {
			field: 'id',
			title: 'ID',
			halign: 'center',
			align: 'center',
			valign: 'middle',
			visible: false
		}, {
			field: 'name',
			title: '项目组名称',
			width: 120,
			halign: 'center',
			align: 'center',
			valign: 'middle'
		}, {
			field: 'createTime',
			title: '创建时间',
			width: 150,
			halign: 'center',
			align: 'center',
			valign: 'middle'
		}, {
			field: 'deptName',
			title: '所属部门',
			formatter: function(value, row, index) {
				return row.emp.dept;
			},
			width: 100,
			align: 'center',
			vlign: 'center'
		}, {
			field: 'detail',
			title: '项目组简介',
			halign: 'center',
			align: 'center',
			valign: 'middle'
		}]
	});
	
	$('#myJoinPjGroup').bootstrapTable({
		method: 'get',
		url: './fontend/comm/pjGrMemberAction_queryByPage',
		queryParams: function(params) {
			var eid = $('#login_user').attr('data');
			params.attr = JSON.stringify({
				eid: eid
			});
			return params;
		},
		columns: [{
			field: 'operator',
			title: '操作',
			formatter: function(value, row, index) {
				return [
				        '<a class="btn btn-default btn-sm lookPjGrMems mr10" href="javascript:void(0)" title="查看项目组成员">',
			            	'<span class="glyphicon glyphicon-eye-open"></span>',
			        	'</a>'
			    ].join('');
			},
			events: {
		        'click .lookPjGrMems': lookPjGrMems
			} 
		}, {
			field: 'name',
			title: '项目组名称'
		}, {
			field: 'createTime',
			title: '创建时间'
		}, {
			field: 'role',
			title: '扮演角色'
		}, {
			field: 'detail',
			title: '团队简介'
		}],
		responseHandler: function(res) {
			var rows = res.rows;
			var data = [];
			for(var i = 0; i < rows.length; i++) {
				var item = {};
				item.name = rows[i].pjGroup.name;
				item.createTime = rows[i].pjGroup.createTime;
				item.detail = rows[i].pjGroup.detail;
				item.role = rows[i].role;
				item.pjGroup = rows[i].pjGroup;
				data.push(item);
			}
			res.rows = data;
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

// 以modal dialog形式显示项目组成员信息,提供删除项目成员的操作
function managePjGrMem(e, value, row, index) {
	$('#managePjGrMemsTable').bootstrapTable('destroy');
	$('#managePjGrMemsTable').bootstrapTable({
		method: 'get',
		url: './fontend/comm/pjGrMemberAction_queryByPage',
		queryParams: function(params) {
			var pjGrId = row.id;
			params.attr = JSON.stringify({
				pjGrId: pjGrId
			});
			return params;
		},
		columns: [{
			field: 'operator',
			title: '操作',
			formatter: function(value, row, index) {
				return [
				        '<a class="btn btn-default btn-sm rmPjGrMem mr10" href="javascript:void(0)" title="删除项目组成员">',
			            	'<span class="glyphicon glyphicon-remove"></span>',
			        	'</a>'
			    ].join('');
			},
			events: {
		        'click .rmPjGrMem': rmPjGrMem
			}
		}, {
			field: 'id',
			title: 'ID',
			visible: false
		}, {
			field: 'name',
			title: '项目成员',
			width: 100,
			formatter: function(value, row, index) {
				return row.emp.name;
			}
		}, {
			field: 'dept',
			title: '所在部门',
			width: 120,
			formatter: function(value, row, index) {
				return row.emp.dept;
			}
		}, {
			field: 'role',
			title: '成员角色',
			width: 100
		}],
		responseHandler: function(res) {
			return res;
		},
		cache: false,
		pagination: true,
		sidePagination: 'server',
		search: false,
		showRefresh: true
	});
	$('#managePjGrMemsModal').modal({
		show: true,
		backdrop: 'static'
	});
};

// 异步方式删除项目组成员
function rmPjGrMem(e,value, row, index) {
	var id = row.id;
	var appModel = new AppModel();
	appModel.setAttr({
		id: id
	});
	$.get('./fontend/comm/pjGrMemberAction_delete', appModel, function(data){
		if(data.success) {
			var values = [];
			values.push(id);
			$('#managePjGrMemsTable').bootstrapTable('remove', {
				field: 'id',
				values: values
			});
		}
	});
};

// 弹出modal dialog 显示项目组的成员信息
function lookPjGrMems(e, value, row, index) {
	$('#lookPjGrMemsTable').bootstrapTable('destroy');
	$('#lookPjGrMemsTable').bootstrapTable({
		method: 'get',
		url: './fontend/comm/pjGrMemberAction_queryByPage',
		queryParams: function(params) {
			var pjGrId = row.pjGroup.id;
			params.attr = JSON.stringify({
				pjGrId: pjGrId
			});
			return params;
		},
		columns: [{
			field: 'id',
			title: 'ID',
			visible: false
		}, {
			field: 'name',
			title: '项目成员',
			width: 100,
			formatter: function(value, row, index) {
				return row.emp.name;
			}
		}, {
			field: 'dept',
			title: '所在部门',
			width: 120,
			formatter: function(value, row, index) {
				return row.emp.dept;
			}
		}, {
			field: 'role',
			title: '成员角色',
			width: 100
		}],
		responseHandler: function(res) {
			return res;
		},
		cache: false,
		pagination: true,
		sidePagination: 'server',
		search: false,
		showRefresh: true
	});
	$('#lookPjGrMemsModal').modal({
		show: true,
		backdrop: 'static'
	});
};

function addPjGroup_callback(data) {
	if(data.success) {
		var row = data.row;
		$('#myCreatePjGroup').bootstrapTable('append', row);
		$('#pjGroupForm')[0].reset();
		$('#pjGroupModal').modal('hide');
	}
};

function editPjGroup(e, value, row, index) {
	$('#pjGroupModal').attr('type', 'update');
	fillForm('pjGroupForm', row);
	$('#pjGroupModal').modal({
		show: true,
		backdrop: 'static'
	});
};

// 异步方式删除项目组
function deletePjGroup(e, value, row, index) {
	var pjGrId = row.id;
	var appModel = new AppModel();
	appModel.setAttr({
		id : pjGrId
	});
	$.get('./fontend/comm/pjGroupAction_delete', appModel, function(data){
		if(data.success) {
			var values = [];
			values.push(pjGrId);
			$('#myCreatePjGroup').bootstrapTable('remove', {
				field: 'id',
				values: values
			});
		}
	});
};

/**
 * 点击添加项目组成员,弹出modal dialog并对dialog进行初始化设置
 */
function addPjGrMem(e, value, row, index) {
	var pjGrId = row.id;
	// 设置隐藏域value
	$('#pjGrId').val(pjGrId);
	
	// 加载已有的项目组成员
	/*var members = row.pjGrMems;
	var data = [];
	for(var i = 0; i < members.length; i++) {
		var mem = members[i];
		var memRole = mem.role;
		var memId = mem.emp.id;
		var memName = mem.emp.name;
		var memDept = mem.emp.dept;
		data.push({
			memId: memId,
			memName: memName,
			memDept: memDept,
			memRole: memRole
		});
	}
	$('#pjGrMemsTable').bootstrapTable('load', data);*/
	
	$('#pjGrMemsTable').bootstrapTable('destroy');
	// 已有项目组成员列表
	$('#pjGrMemsTable').bootstrapTable({
		method: 'get',
		url: './fontend/comm/pjGrMemberAction_queryByPage',
		queryParams: function(params) {
			params.attr = JSON.stringify({
				pjGrId: pjGrId
			});
			return params;
		},
		responseHandler: function(res) {
			var members = res.rows;
			var data = [];
			for(var i = 0; i < members.length; i++) {
				var mem = members[i];
				var memRole = mem.role;
				var memId = mem.emp.id;
				var memName = mem.emp.name;
				var memDept = mem.emp.dept;
				data.push({
					memId: memId,
					memName: memName,
					memDept: memDept,
					memRole: memRole
				});
			}
			res.rows = data;
			return res;
		},
		pagination: true,
		sidePagination: 'server',
		pageSize: 5,
		pageList: [5,10,15,20],
		search: false,
		showColumns: false,
		showRefresh: false,
		clickToSelect: false,
		columns: [{
			field: 'memId',
			title: 'ID',
			visible: false
		}, {
			field: 'memName',
			title: '项目成员',
			width: 100
		}, {
			field: 'memDept',
			title: '所在部门',
			width: 120
		}, {
			field: 'memRole',
			title: '成员角色',
			width: 100
		}]
	});
	
	var appModel = new AppModel();
	appModel.setAttr({
		id : pjGrId
	});
	$.get('./fontend/comm/pjGroupAction_queryEmpNoInPjGroup', appModel, function(data){
		if(data.success) {
			var rows = data.rows;
			$('#empTable').bootstrapTable('load', rows);
			// open the modal dialog
			$('#addPjGrMemModal').modal('show');
		}
	});
};


//异步添加项目组成员响应的回调函数
function addPjGrMem_callback(data) {
	if(data.success) {
		var rows = data.rows;
		var tableData = [];
		for(var i = 0; i < rows.length; i++) {
			var row = rows[i];
			var memId = row.emp.id;
			var memName = row.emp.name;
			var memRole = row.role;
			var memDept = row.emp.dept;
			var d = {
				memId: memId,
				memName: memName,
				memRole: memRole,
				memDept: memDept
			};
			tableData.push(d);
		}
		$('#pjGrMemsTable').bootstrapTable('append', tableData);
		$('#myCreatePjGroup').bootstrapTable('refresh', {silent: true});
		var sels = $('#empTable').bootstrapTable('getSelections');
		var ids = [];
		for(var k = 0; k < sels.length; k++) {
			var sel = sels[k];
			ids.push(sel.id);
		}
		$('#empTable').bootstrapTable('remove', {
			field: 'id',
			values: ids
		});
		$('#newPjGrMemsTable').bootstrapTable('remove', {
			field: 'memId',
			values: ids
		});
	}
};
