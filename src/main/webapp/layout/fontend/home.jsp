<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<jsp:include page="/fontend_inc.jsp"></jsp:include>
<title>RDMS主界面</title>
</head>
<body id="body" style="background-color: #f5f5f5;">

	

<div class="wrapper">
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-2">
				<div id="menubar" class="menubar"
					style="background: #fff; border: 1px solid #ddd; box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.075);">
					<div class="text-center" style="margin: 20px 0 20px 0;">
						<img id="empPicImgSrc" src="${sessionScope.emp.pic}"
							style="border: 1px solid #ddd;" width="100px" height="100px" />
						<div style="margin-top:15px;">
							<h4><span class="label label-info">职位:</span> <span>${sessionScope.emp.position}</span></h4>
							<h4><span class="label label-primary">部门:</span> <span>${sessionScope.emp.dept}</span></h4>
						</div>
					</div>
					<div id="menu"></div>
				</div>
			</div>
			<div class="col-md-10">
				<div id="main-content"
					style="background-color: #fff; border: 1px solid #ddd; box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.075);">

					<div id="menuTarget" style="padding: 15px;">
							<div class="container-fluid">
								<div class="row">
									<div class="col-md-4">
										<div class="row">
											<h3>No1.关于本项目</h3>
											<p style="text-indent: 2em;">这个项目是我的毕业设计成果,仅可作为学习参考,任何人不得将其用于商业用途,有任何不足的地方请各位给我提出宝贵意见(联系邮箱见个人信息)</p>
											<h4>@依赖技术</h4>
											<p>JQuery、Bootstrap3、BootstrapTable、Java、SSH、MySQL</p>
										</div>
										<div class="row">
											<h3>No2.关于我</h3>
											<h4>@基本信息</h4>
											<p>学校: 华南师范大学</p>
											<p>学院: 计算机学院</p>
											<p>称呼: CrazyPig</p>
											<p>电子邮箱: 18825111236@163.com</p>
											<h4>@我的梦想</h4>
											<p>去发现、期待未来所有美的东西!</p>
										</div>
									</div>
									<div class="col-md-8 text-center">
										<div>
											<img src="./image/index.jpg" width="100%;" />
										</div>
										<h3>研发管理系统——提高团队效率的利器</h3>
									</div>
								</div>
							</div>
						</div>
				</div>
			</div>
		</div>

	</div>
</div>

	<jsp:include page="footer.jsp"></jsp:include>

</body>
<script>

var initLayout = function() {
	var w_h = $(window).height();
	$('#menubar').css('min-height', (w_h) + 'px');
	$('#main-content').css('min-height', (w_h) + 'px');
};

	$(document).ready(function() {
		
		initLayout();
		
		$('#menu').collapseMenu({
			indent : 15,
			speed : 200,
			dataUrl : './data/fontend/menu-data.json'
		});

	});
</script>
</html>