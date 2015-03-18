// logChart引用
var logChart = null;
// 存放异步加载的log记录(一周)
var logArr = new Array(7);
// 存放当前选择日期,默认为today
var selectDate = new Date();

$(document).ready(function(){

	// 显示日历选择器
	$('#calendar').datepicker({
		language : 'zh-CN',
		todayHighlight : true
	}).on('changeDate', onChangeDateEvent);

	// 初始化logChart
	$('#logChart').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: '每周工作日志'
        },
        xAxis: {
            categories: [
                '周一',
                '周二',
                '周三',
                '周四',
                '周五',
                '周六',
                '周日'
            ]
        },
        yAxis: {
            min: 0,
            title: {
                text: '工作时长 (小时)'
            }
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            },
			series: {
				cursor: 'pointer',
				point: {
					events: {
						click: function() {
							// 获取当前index,得到对应日期,加载并更新下方日志记录区的记录信息
							updateLogArea(logArr[this.index]);
						}
					}
				}
			}
        },
        series: [{
			name: '工作时长',
            data: [0, 0, 0, 0, 0, 0, 0]

        }]
    });

	// 获取logChart引用
	logChart = $('#logChart').highcharts();

	// 初始化工作
	init();

	$('#addLogBtn').click(function(){
		if($('#workLogArea').children().length > 0) {
			alert('存在工作日志,无法新增');
			return ;
		}
		$('#logModal').attr('type', 'add');
		$('#logModal').modal('show');
	});
	
	$('#saveLogBtn').click(function(){
		var logAttr = serializeForm('logForm');
		// 设置work log 时间
		logAttr.createTimeUTC = selectDate.getTime();
		var type = $('#logModal').attr('type');
		if(type == "add") {
			// 新增操作
			addLog(logAttr);
		} else {
			// 更新操作
			updateLog(logAttr);
		}
	});
	
	$('#editLogBtn').click(function(){
		$('#logModal').attr('type', 'update');
		var workTimesVal = $('#workTimesVal').html();
		var contentVal = $('#contentVal').html();
		var workLogId = $('#workLogId').html();
		if(typeof(workTimesVal) != "undefined") {
			$('#workTimes').val(workTimesVal);
			$('#content').val(contentVal);
			$('#id').val(workLogId);
			$('#logModal').modal('show');
		} else {
			alert('还没有添加日志,不能修改');
		}
	});
	
	$('#delLogBtn').click(function(){
		
	});
	
});

function init() {
	// 初始化logChart
	updateLogChart(new Date());
};

/**
*	响应点击日历的事件
*	获取点击日期内的一周 startDate(周一) endDate(周日)
*   异步请求后台日志记录,更新highchart以及日志记录区
*/
function onChangeDateEvent(e) {
	var date = e.date;
	selectDate = e.date;
	updateLogChart(date);
};

/**
 * 异步方式添加work log
 */
function addLog(logAttr) {
	var appModel = new AppModel();
	appModel.setAttr(logAttr);
	$.post('./fontend/comm/workLogAction_insert', appModel, addLog_callback);
};

/**
 * 异步方式更新work log
 */
function updateLog(logAttr) {
	var appModel = new AppModel();
	appModel.setAttr(logAttr);
	$.post('./fontend/comm/workLogAction_update', appModel, updateLog_callback);
};

/**
 * 异步方式删除work log
 */
function deleteLog() {
	var workLogId = $('#workLogId').html();
	var appModel = new AppModel();
	var attr = {
		id : workLogId
	};
	appModel.setAttr(attr);
	$.get('./fontend/comm/workLogAction_delete', appModel, deleteLog_callback);
}

// 异步方式删除workLog的回调函数
function deleteLog_callback(data) {
	if(data.success) {
		$('#workLogArea').children().remove();
	}
};

// 异步方式更新workLog的回调函数
function updateLog_callback(data) {
	if(data.success) {
		var workLog = data.row;
		updateLogChart(selectDate);
		updateLogArea(workLog);
	}
	// 以DOM方式重置表单
	$('#logForm')[0].reset();
	$('#logModal').modal('hide');
};

// 异步方式添加workLog的回调函数
function addLog_callback(data) {
	if(data.success) {
		var workLog = data.row;
		updateLogChart(selectDate);
		updateLogArea(workLog);
	}
	// 以DOM方式重置表单
	$('#logForm')[0].reset();
	$('#logModal').modal('hide');
};

/**
 * 根据选择的日期查询一周内的日志记录,更新workLogChart和workLogArea
 */
function updateLogChart(selectDate) {
	var date = selectDate;
	var startDate = new Date(date);
	var endDate = new Date(date);
	var day = date.getDay();
	if(day == 0) {
		day = 7;
	}
	var start = date.getDate() - day + 1;
	startDate.setDate(start);
	var end = date.getDate() + 7 - day;
	endDate.setDate(end);
	$.get('./fontend/comm/workLogAction_queryOneWeek', {
		startDate : startDate.getTime(),
		endDate : endDate.getTime()
	}, function(data) {
		if(data.success) {
			var rows = data.rows;
			var seriesData = processLogData(rows);
			// 更新logChart
			_updateLogChart(logChart, seriesData);
			
			// 更新logArea
			var index = selectDate.getDay() - 1;
			if(index < logArr.length) {
				updateLogArea(logArr[index]);
			}
		}
	});
};

/**
 * 更新 log area
 */
function updateLogArea(workLog) {
	$('#workLogArea').children().remove();
	if(typeof(workLog) == "undefined") return;
	var currentDate = new Date(workLog.createTimeUTC);
	var year = currentDate.getFullYear();
	var month = currentDate.getMonth();
	var days = currentDate.getDate();
	var dateStr = "";
	dateStr = dateStr + year + "-";
	if(month < 10) {
		dateStr = dateStr + "0" + month + "-";
	} else {
		dateStr = dateStr + month + "-";
	}
	if(days < 10) {
		dateStr = dateStr + "0" + days;
	} else {
		dateStr = dateStr + days;
	}
	var index = currentDate.getDay() - 1;
	var dayArr = ["周一","周二","周三","周四","周五","周六","周日"];
	var id = workLog.id;
	var content = workLog.content;
	var workTimes = workLog.workTimes;
	var html = '<p><span id="workLogId" hidden>' + id + '</span>' + '日期: ' + dateStr + '(' + dayArr[index] + ')' + '</p>' + 
				'<p>' + '工作时长: ' + '<span id="workTimesVal">' + workTimes + '</span>' + ' 小时' + '</p>'
				+ '<p>' + '工作内容: ' + '<span id="contentVal">' + content + '</span>' + '</p>';
	$('#workLogArea').append(html);
};

/**
 * 根据后台返回的数据处理成highcharts能够接收的series data格式
 */
function processLogData(rows) {
	logArr = new Array(7);
	var columns = new Array(7);
	if(!(rows instanceof Array)) return [];
	for(var i = 0; i < rows.length; i++) {
		var row = rows[i];
		var createTime = new Date(row.createTimeUTC);
		var index = createTime.getDay() - 1;
		// 保存每周日志记录对象
		logArr[index] = row;
		var workTimes = row.workTimes;
		var column = {
			color : '#ff0000',
			y : workTimes
		};
		if(workTimes >= 8) {
			column.color = '#00ff00';
		}
		columns[index] = column;
	}
	for(var j = 0; j < columns.length; j++) {
		if(typeof(columns[j]) == "undefined") {
			columns[j] = {
				color : '#ff0000',
				y : 0
			};
		}
	}
	return columns;
};

// 更新logChart
function _updateLogChart(chart, data) {
	if(typeof(chart.series) == 'undefined') return ;
	if(data.length == 0) {
		data = [0,0,0,0,0,0,0];
	}
	var series = chart.series;
	while(series.length > 0) {
		series[0].remove(false);
	}
	chart.redraw();
	chart.addSeries({
		name : '工作时长',
		data : data
	}, false);
	chart.redraw();
};