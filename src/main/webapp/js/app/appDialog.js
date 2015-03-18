var appDialog = {
	createEditDialog : function(id, pageUrl, buttons, width, height, isModal, title, formName, data) {
		dialog(id, pageUrl, buttons, width, height, isModal, title, formName, data, 'edit');
	},
	createAddDialog : function(id, pageUrl, buttons, width, height, isModal, title, formName) {
		dialog(id, pageUrl, buttons, width, height, isModal, title, formName, null, 'add');
	},
	closeDialog: function(dialogName) {
		$('#' + dialogName).dialog('close');
	}	
};

var dialog = function(id, pageUrl, buttons, width, height, isModal, title, formName, data, type) {
	if(buttons == null || !(buttons instanceof Array)) {
		buttons = [];
	}
	var modal = true;
	if(typeof(isModal) == "boolean") {
		modal = isModal;
	}
	if(typeof(width) == "string") {
		width = parseInt(width);
	}
	if(typeof(height) == "string") {
		height = parseInt(height);
	}
	var $dlg = $('#' + id);
	$dlg.dialog({
		title: title,
		width: width,
		height: height,
		closed: true,
		cache: false,
		href: pageUrl,
		modal: modal,
		buttons: buttons,
		onClosed: function() {
			$(this).dialog('close');
		},
		onLoad: function() {
			if(type == 'edit') {
				$('#' + formName).form('load', data);
			}
		}
	});
	$dlg.dialog('open');
};