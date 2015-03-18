// 异步统计各部门项目组情况的回调函数
function countEachDeptPjGroup_callback(data) {
	var pieItemList = [], deptArr = [], pjGrNumsArr = [];
	if(data.success) {
		var rows = data.rows;
		for(var i = 0; i < rows.length; i++) {
			var row = rows[i];
			var dept = row.dept;
			var pjGrNums = row.pjGrNums;
			var pjGrRatio = row.pjGrRatio;
			
			// 组织pie chart所需要的数据
			var pieItem = [];
			pieItem.push(dept);
			pieItem.push(parseFloat(pjGrRatio));
			pieItemList.push(pieItem);
			
			// 组织column chart所需要的数据
			deptArr.push(dept);
			pjGrNumsArr.push(parseInt(pjGrNums));
		}
	}
	// 更新pie chart
	var pieChart = $('#pieChart').highcharts();
	pieChart.series[0].setData(pieItemList);
	pieChart.redraw();
	// 更新column chart
	var columnChart = $('#columnChart').highcharts();
	columnChart.xAxis[0].setCategories(deptArr, false);
	columnChart.series[0].setData(pjGrNumsArr);
	columnChart.redraw();
};

// 异步方式统计各部门项目组情况
function countEachDeptPjGroup() {
	$.get('./fontend/comm/statisticsAction_countEachDeptPjGroup', null, countEachDeptPjGroup_callback);
};

// 查看项目组负责的项目
function lookPj(e, value, row, index) {
	var pjGrId = row.id;
	$('#pjTable').bootstrapTable('destroy');
	$('#pjTable').bootstrapTable({
		method: 'get',
		url: './fontend/comm/projectAction_queryByPage',
		queryParams: function(params) {
			var attr = {
				pjGrId: pjGrId
			};
			params.attr = JSON.stringify(attr);
			return params;
		},
		columns: [{
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
	$('#pjModal').modal({
		show: true,
		backdrop: 'static'
	});
};

// 查看项目组成员
function lookPjGrMems(e, value, row, index) {
	$('#lookPjGrMemsTable').bootstrapTable('destroy');
	$('#lookPjGrMemsTable').bootstrapTable({
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
		cache: false,
		pagination: true,
		sidePagination: 'server',
		// search: false,
		showRefresh: true
	});
	$('#lookPjGrMemsModal').modal({
		show: true,
		backdrop: 'static'
	});
};

$(document).ready(function(){
	$('#pieChart').highcharts({
		chart: {
            plotBackgroundColor: null,
            plotBorderWidth: 1,//null,
            plotShadow: false
        },
        title: {
            text: '各部门创建的项目组比例'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    }
                }
            }
        },
        series: [{
            type: 'pie',
            name: '项目组比例',
            data: []
        }]
	});
	
	$('#columnChart').highcharts({
		chart: {
            type: 'column'
        },
        title: {
            text: '各部门创建的项目组情况'
        },
        xAxis: {
            categories: []
        },
        yAxis: {
        	allowDecimals: false,
            min: 0,
            title: {
                text: '项目组个数'
            }
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            },
			series: {
				cursor: 'pointer'
			}
        },
        series: [{
        	name: '项目组个数',
        	data: []
        }]
	});
	
	countEachDeptPjGroup();
	
	$('#pjGrTable').bootstrapTable({
		method: 'get',
		url: './fontend/comm/pjGroupAction_queryByPage',
		cache: false,
		pagination: true,
		sidePagination: 'server',
		pageSize: 5,
		pageList: [5,10,15,20],
		//search: true,
		//showColumns: true,
		showRefresh: true,
		columns: [{
			field: 'operator',
			title: '操作',
			width: 180,
			halign: 'center',
			align: 'center',
			valign: 'middle',
			clickToSelect: false,
			formatter: function(value, row, index) {
				return [
				        '<a class="btn btn-default btn-sm lookPj mr10" href="javascript:void(0)" title="查看项目组负责的项目">',
				            '<span class="glyphicon glyphicon-eye-open"></span>',
				        '</a>',
				        '<a class="btn btn-default btn-sm lookPjGrMems mr10" href="javascript:void(0)" title="查看项目组成员">',
			            	'<span class="glyphicon glyphicon-user"></span>',
			        	'</a>'
				    ].join('');
			},
			events: {
				'click .lookPj': lookPj,
				'click .lookPjGrMems': lookPjGrMems
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
			field: 'detail',
			title: '项目组简介',
			halign: 'center',
			align: 'center',
			valign: 'middle'
		}]
	});
	
});