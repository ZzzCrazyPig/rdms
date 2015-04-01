
/**
 * 描述系统中发生错误的错误代码 与后台相互对应
 * @author CrazyPig
 *
 */
var ErrorCode = {
	NO_LOGIN: "NO_LOGIN",
	NO_PERMISSION: "NO_PERMISSION",
	SYS_ERROR: "SYS_ERROR"
};

var errorProcess = function(resp) {
	var errorCode = resp.errorCode;
	if(errorCode == ErrorCode.NO_LOGIN) {
		// TODO 处理登陆状态失效
		alert('登陆状态失效,请重新登陆');
		window.location.href = "./layout/fontend/login.jsp";
	}
	// 其他操作不需要跳转
};

// bootstrap notify alert 
var notifyAlert = function(targetId, msg) {
	$('#' + targetId).notify({
		type: 'bangTidy',
		message: {
			text: msg
		}
	}).show();
};