// 异步方式加载部门下拉选择框
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
		} else {
			// 错误处理
			errorProcess(data);
		}
		notifyAlert('notifyMsg', data.msg);
	});
};

// 异步统计部门考勤情况的回调函数
function countSpecDeptWorkLog_callback(data) {
	var empNameArr = [], workLogCountArr = [], warningEmpArr = [];
	if(data.success) {
		var rows = data.rows;
		for(var i = 0; i < rows.length; i++) {
			var row = rows[i];
			var empName = row.empName;
			var workLogCount = parseInt(row.workLogCount);
			var defaultWorkDays = parseInt(row.defaultWorkDays);
			empNameArr.push(empName);
			workLogCountArr.push(workLogCount);
			if(workLogCount < defaultWorkDays) {
				warningEmpArr.push(empName);
			}
		}
	}
	var columnChart = $('#columnChart').highcharts();
	columnChart.xAxis[0].setCategories(empNameArr, false);
	columnChart.series[0].setData(workLogCountArr);
	columnChart.redraw();
	
	// 如果发现有人考勤次数不够,则显示这些人的名字
	if(warningEmpArr.length > 0) {
		var text = "";
		for(var i = 0; i < warningEmpArr.length; i++) {
			var item = warningEmpArr[i];
			text = text + " " + item;
		}
		$('#warning').text(text).show();
	}
};

// 异步方式统计部门考勤情况
function countSpecDeptWorkLog() {
	$('#warning').hide();
	var deptName = $('#deptName').val();
	if(deptName.length == 0) {
		notifyAlert('notifyMsg', '请选择需要查询的部门');
		return ;
	}
	var startDate = $('#startDate').val();
	if(startDate.length == 0) {
		notifyAlert('notifyMsg', '请选择开始日期');
		return ;
	}
	var endDate = $('#endDate').val();
	if(endDate.length == 0) {
		notifyAlert('notifyMsg', '请选择结束日期');
		return ;
	}
	var queryParams = {
		dept: deptName,
		startDate: startDate,
		endDate: endDate
	};
	$.get('./fontend/comm/statisticsAction_countSpecDeptEachEmpWorkLog', queryParams, countSpecDeptWorkLog_callback);
};

$(function () {
	// 异步加载部门下拉选择框内容
	loadDeptSelect();
	
	$('#startDate').datepicker({
		language : 'zh-CN',
		todayHighlight : true,
		format: 'yyyy-mm-dd'
	});
	
	$('#endDate').datepicker({
		language : 'zh-CN',
		todayHighlight : true,
		format: 'yyyy-mm-dd'
	});
	
	$('#columnChart').highcharts({
		chart: {
            type: 'column'
        },
        title: {
            text: '部门考勤情况'
        },
        xAxis: {
            categories: []
        },
        yAxis: {
            min: 0,
            allowDecimals: false,
            title: {
                text: '考勤次数'
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
        	name: '考勤',
        	data: []
        }]
	});
});