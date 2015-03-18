function loadDeptSelect() {
	$.get('./fontend/comm/departmentAction_query', null, function(data){
		if(data.success) {
			var select = $('#deptName');
			var rows = data.rows;
			select.children().remove();
			for(var i = 0; i < rows.length; i++) {
				var row = rows[i];
				var deptName = row.name;
				select.append('<option value="' + deptName + '">' + deptName + '</option>');
			}
		}
	});
};

// 隐藏panel
function collapsePanel(panelId) {
	var panel = $('#' + panelId);
	panel.slideUp();
}

// 统计每个部门组织的项目个数,占总项目的比例
function countEachDeptPj() {
	$.get('./fontend/comm/statisticsAction_countEachDeptPj', null, countEachDeptPj_callback);
};

// 以图表形式显示项目进度
function showPjProgress() {
	var deptName = $('#deptName').val(); // 获取给定的部门
	var queryParams = {
		deptName: deptName
	};
	$.get('./fontend/comm/projectAction_queryPjByDept', queryParams, showPjProgress_callback);
};

// 异步统计每个部门项目情况的回调函数
function countEachDeptPj_callback(data) {
	var rows = [];
	if(data.success) {
		rows = data.rows;
		var pieItemList = [], deptArr = [], pjNumsArr = [], columnChartSeries = [];
		var columnChartSeriesItem = {};
		for(var i = 0; i < rows.length; i++) {
			var row = rows[i];
			var dept = row.dept;
			var pjRatio = row.pjRatio;
			var pjNums = row.pjNums;
			var pieItem = [];
			
			// 组织pie chart所需要的数据
			pieItem.push(dept);
			pieItem.push(parseFloat(pjRatio));
			pieItemList.push(pieItem);
			
			// 组织column chart所需要的数据
			deptArr.push(dept);
			pjNumsArr.push(parseInt(pjNums));
		}
		columnChartSeriesItem.name = '项目个数';
		columnChartSeriesItem.data = pjNumsArr;
		columnChartSeries.push(columnChartSeriesItem);
		
		// 更新pie chart
		var pieChart = $('#pieChart').highcharts();
		pieChart.series[0].setData(pieItemList);
		pieChart.redraw();
		
		// 更新column chart
		var columnChart = $('#columnChart').highcharts();
		columnChart.xAxis[0].setCategories(deptArr, false);
		columnChart.series[0].setData(pjNumsArr);
		columnChart.redraw();
	}
};

// 异步查询项目进度的回调函数
function showPjProgress_callback(data) {
	var xAxis = [], dataSeries = [];
	if(data.success) {
		var rows = data.rows;
		for(var i = 0; i < rows.length; i++) {
			var row = rows[i];
			var pjName = row.name;
			var pjProgress = row.progress;
			xAxis.push(pjName);
			dataSeries.push(parseFloat(pjProgress));
		}
	}
	var barChart = $('#barChart').highcharts();
	barChart.xAxis[0].setCategories(xAxis, false);
	barChart.series[0].setData(dataSeries);
	barChart.redraw();
	
	var panel = $('#pjProgressPanel');
	if(panel.is(':hidden')) {
		panel.slideDown();
	}
};

$(document).ready(function(){
	// 加载部门下拉选择框
	loadDeptSelect();
	
	$('#pieChart').highcharts({
		chart: {
            plotBackgroundColor: null,
            plotBorderWidth: 1,//null,
            plotShadow: false
        },
        title: {
            text: '部门组织项目比例'
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
            name: '项目比例',
            data: []
        }]
	});
	
	$('#columnChart').highcharts({
		chart: {
            type: 'column'
        },
        title: {
            text: '各部门组织的项目情况'
        },
        xAxis: {
            categories: []
        },
        yAxis: {
            min: 0,
            allowDecimals: false,
            title: {
                text: '项目个数'
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
        	name: '项目个数',
        	data: []
        }]
	});
	
	$('#barChart').highcharts({
        chart: {
            type: 'bar'
        },
        title: {
            text: '项目进度情况'
        },
        xAxis: {
            categories: []
        },
        yAxis: {
            min: 0,
            max: 100,
            tickInterval: 20,
            title: {
                text: '进度(%)'
            }
        },
        legend: {
            backgroundColor: '#FFFFFF',
            reversed: true
        },
        plotOptions: {
            series: {
                stacking: 'normal'
            }
        },
        series: [{
            name: '进度',
            data: []
        }]
    });
	
	countEachDeptPj();
	
	$('#pjTable').bootstrapTable({
		method: 'get',
		url: './fontend/comm/projectAction_queryByPage',
		columns: [{
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
		//search: true,
		//showColumns: true,
		showRefresh: true
	});
});
