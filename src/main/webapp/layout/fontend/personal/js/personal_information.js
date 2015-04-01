// 更新用户个人信息
function updateEmpInfo(formId) {
	var attr = serializeForm(formId);
	var eid = $('#login_user').attr('data');
	attr.id = eid;
	var appModel = new AppModel();
	appModel.setAttr(attr);
	$.post('./fontend/comm/employeeAction_update', appModel, function(data){
		if(data.success) {
			fillForm(formId, data.row);
		}
		// 显示提示信息
		notifyAlert('notifyMsg', data.message);
	});
};


$(document).ready(function(){
	
	// 显示日历选择器
	$('#birthDate').datepicker({
		language : 'zh-CN',
		todayHighlight : true,
		format: 'yyyy-mm-dd'
	});
	
	$("#empPic").fileinput({
        uploadUrl: './fontend/comm/empPicUploadAction_upload', // you must set a valid URL here else you will get an error
        allowedFileExtensions : ['jpg', 'png','gif'],
        overwriteInitial: false,
        maxFileSize: 1000,
        maxFilesNum: 10,
        //allowedFileTypes: ['image', 'video', 'flash'],
        slugCallback: function(filename) {
            return filename.replace('(', '_').replace(']', '_');
        }
	});
            
    $('#empPic').on('fileuploaded', function(event, data, previewId, index){
    	var resp = data.response;
    	if(resp.success) {
    		var picSrc = resp.row.pic;
    		$('#empPicImgSrc').attr('src', picSrc);
    	}
    	notifyAlert('notifyMsg', resp.message);
    });
	
});

