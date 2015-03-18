var AppModel = function() {
	this.page = 0;
	this.rows = 5;
	this.sort = null;
	this.order = null;
	this.attr = null;
	
};

AppModel.prototype.setAttr = function(data) {
	if(data != null) {
		this.attr = JSON.stringify(data);
	}
};


var EmployeeModel = function() {
	this.id = null;
	this.name = null;
	this.usr = null;
	this.pwd = null;
};