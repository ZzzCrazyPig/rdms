// 调整iframe的高度自适应页面大小
function autoSuitIframeHeight() {
	var ifm = document.getElementById("statsvn-frame");
	var subWeb = document.frames ? document.frames["statsvn-frame"].document
			: ifm.contentDocument;
	if (ifm != null && subWeb != null) {
		ifm.height = subWeb.body.scrollHeight;
	}
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

// 异步方式调用statsvn进行svn代码统计
function statSvnPj(e, value, row, index) {
	// 隐藏Modal dialog
	$('#svnPjModal').modal('hide');
	
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

// 弹出modal dialog 查看项目阶段信息
function lookPjStage(e, value, row, index) {
	var pid = row.id;
	$('#pjStageTable').bootstrapTable({
		method: 'get',
		url: './fontend/comm/pjStageAction_queryByPage',
		queryParams: function(params) {
			params.attr = JSON.stringify({pid: pid});
			return params;
		},
		columns: [{
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
		search: true,
		showColumns: true,
		showRefresh: true,
		showToggle: true
	});
	// 打开modal dialog
	$('#pjStageModal').modal({
		show: true,
		backdrop: 'static'
	});
};

// 以modal dialog方式查看项目里程碑信息
function lookPjMark(e, value, row, index) {
	var pid = row.id;
	$('#pjMarkTable').bootstrapTable('destroy');
	$('#pjMarkTable').bootstrapTable({
		method: 'get',
		url: './fontend/comm/pjMarkAction_queryByPage',
		queryParams: function(params) {
			params.attr = JSON.stringify({pid: pid});
			return params;
		},
		columns: [{
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
		search: true,
		showColumns: true,
		showRefresh: true,
		showToggle: true
	});
	// 打开modal dialog
	$('#pjMarkModal').modal({
		show: true,
		backdrop: 'static'
	});
};

// 以modal dialog方式查看项目的svnpj信息
function lookSvnPj(e, value, row, index) {
	var pid = row.id;
	$('#svnPjTable').bootstrapTable('destroy');
	// 加载svnPjTable
	$('#svnPjTable').bootstrapTable({
		method: 'get',
		url: './fontend/svn/svnProjectAction_queryByPage',
		queryParams: function(params) {
			var attr = {
				pid: pid
			};
			params.attr = JSON.stringify(attr);
			return params;
		},
		columns: [{
			field: 'operator',
			title: '操作',
			formatter: function(value, row, index) {
				return [
				        '<a class="btn btn-default btn-sm statSvnPj mr10" href="javascript:void(0)" title="SVN代码统计">',
			            	'<span class="glyphicon glyphicon-eye-open"></span>',
			        	'</a>'
			    ].join('');
			},
			events: {
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
		search: true,
		showColumns: true,
		showRefresh: true
	});
	
	// 弹出模态对话框
	$('#svnPjModal').modal('show');
};

$(document).ready(function(){
	$('#joinPjTable').bootstrapTable({
		method: 'get',
		url: './fontend/comm/projectAction_queryJoinPjByPage',
		queryParams: function(params) {
			var attr = {
				id: $('#login_user').attr('data')
			};
			params.attr = JSON.stringify(attr);
			console.info(params);
			return params;
		},
		columns: [{
			field: 'id',
			title: 'ID',
			visible: false
		}, {
			field: 'operator',
			title: '操作',
			formatter: function(value, row, index) {
				return [
				        '<a class="btn btn-default btn-sm lookPjStage mr10" href="javascript:void(0)" title="查看项目阶段信息">',
			            	'<span class="glyphicon glyphicon-eye-open"></span>',
			        	'</a>',
			        	'<a class="btn btn-default btn-sm lookPjMark mr10" href="javascript:void(0)" title="查看项目里程碑">',
		            		'<span class="glyphicon glyphicon-map-marker"></span>',
		        		'</a>',
		        		'<a class="btn btn-default btn-sm lookSvnPj mr10" href="javascript:void(0)" title="查看Svn项目信息">',
			            	'<span class="glyphicon glyphicon-eye-open"></span>',
			            '</a>'
			    ].join('');
			},
			events: {
				'click .lookPjStage': lookPjStage,
				'click .lookPjMark': lookPjMark,
		        'click .lookSvnPj': lookSvnPj
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
		cache: false,
		pagination: true,
		sidePagination: 'server',
		pageSize: 5,
		pageList: [5,10,15,20],
		showRefresh: true
	});
	
	$('#myCreatePjTable').bootstrapTable({
		method: 'get',
		url: './fontend/comm/projectAction_queryByPage',
		queryParams: function(params) {
			var attr = {
				createUser: $('#login_user').attr('data')
			};
			params.attr = JSON.stringify(attr);
			return params;
		},
		columns: [{
			field: 'id',
			title: 'ID',
			visible: false
		}, {
			field: 'operator',
			title: '操作',
			formatter: function(value, row, index) {
				return [
				        '<a class="btn btn-default btn-sm lookPjStage mr10" href="javascript:void(0)" title="查看项目阶段信息">',
			            	'<span class="glyphicon glyphicon-eye-open"></span>',
			        	'</a>',
			        	'<a class="btn btn-default btn-sm lookPjMark mr10" href="javascript:void(0)" title="查看项目里程碑">',
		            		'<span class="glyphicon glyphicon-map-marker"></span>',
		        		'</a>',
		        		'<a class="btn btn-default btn-sm lookSvnPj mr10" href="javascript:void(0)" title="查看Svn项目信息">',
			            	'<span class="glyphicon glyphicon-eye-open"></span>',
			            '</a>'
			    ].join('');
			},
			events: {
				'click .lookPjStage': lookPjStage,
				'click .lookPjMark': lookPjMark,
		        'click .lookSvnPj': lookSvnPj
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
		cache: false,
		pagination: true,
		sidePagination: 'server',
		pageSize: 5,
		pageList: [5,10,15,20],
		showRefresh: true
	});
	
});