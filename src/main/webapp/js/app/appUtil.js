/**
 * @author 孙宇
 * 
 * 增加formatString功能
 * 
 * 使用方法：formatString('字符串{0}字符串{1}字符串','第一个变量','第二个变量');
 * 
 * @returns 格式化后的字符串
 */
var formatString = function(str) {
	for ( var i = 0; i < arguments.length - 1; i++) {
		str = str.replace("{" + i + "}", arguments[i + 1]);
	}
	return str;
};



var serializeForm = function(formName) {
	var str = $('#' + formName).serialize();
	var str = str.replace(/\+/g, " "); // serialize()方法会将空格替换为+号,这里需要把+号替换为空格
	str = decodeURIComponent(str,true);
	var attr = {};
	var kvArr = str.split('&');
	for(var i = 0; i < kvArr.length; i++) {
		var kv = kvArr[i];
		var arr = kv.split('=');
		attr[arr[0]] = arr[1];
	}
	// console.info(JSON.stringify(attr));
	return attr;
};

var fillForm = function(formName, data) {
	if(typeof(data) != "object") return;
	var form = $('#' + formName);
	// 遍历对象的键值对
	for(var name in data) {
		var value = data[name];
		var rr = _checkField(formName, name, value);
		if(!rr.length) {
			$('input[name="' + name + '"]', form).val(value);
			$('textarea[name="' + name + '"]', form).val(value);
			$('select[name="' + name + '"]', form).val(value);
		}
	}
};

// 检查是否为checkbox或radio类型
var _checkField = function(formName, name, value) {
	var rr = $('#' + formName).find('input[name="'+name+'"][type=radio], input[name="'+name+'"][type=checkbox]');
	rr.each(function(){
		var f = $(this);
		// f.attr('checked', false);
		if (f.val() == String(value) || $.inArray(f.val(), $.isArray(value)? val : [value]) >= 0){
			f.attr('checked', true);
		}
	});
	return rr;
};


// 不应该写在这里 应该抽离到backend的jsp页面中
var change_theme = function(themeName) {
	var $theme = $('#theme');
	var url = $theme.attr('href');
	var href = url.substring(0, url.indexOf('themes')) + '/themes/' + themeName + '/easyui.css';
	$theme.attr('href', href);
	$.cookie("mystyle", themeName, {expires:7}); 
};

var init_theme = function() {
	var themeName = $.cookie("mystyle");
	if(themeName != null) {
		change_theme(themeName);
	}
};

//对Date的扩展，将 Date 转化为指定格式的String   
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，   
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)   
//例子：   
//(new Date()).format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423   
//(new Date()).format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18   
Date.prototype.format = function(fmt) { //author: meizz   
	var o = {
		"M+" : this.getMonth() + 1, //月份   
		"d+" : this.getDate(), //日   
		"h+" : this.getHours(), //小时   
		"m+" : this.getMinutes(), //分   
		"s+" : this.getSeconds(), //秒   
		"q+" : Math.floor((this.getMonth() + 3) / 3), //季度   
		"S" : this.getMilliseconds()
	//毫秒   
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};