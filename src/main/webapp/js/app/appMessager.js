var appMessager = {
	alert : function(title, msg) {
		$.messager.alert(title, msg, 'info');
	},
	show : function(title, msg, timeout) {
		if(timeout == null || typeof(timeout) == 'undefined') {
			timeout = 3000;
		} else if(typeof(timeout) == 'string') {
			timeout = parseInt(timeout);
		}
		$.messager.show({
			title : title,
			msg : msg,
			timeout : timeout,
			showType : 'slide'
		});
	}
};