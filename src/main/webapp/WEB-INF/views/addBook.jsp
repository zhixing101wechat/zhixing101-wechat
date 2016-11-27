<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>录书</title>

<%-- 加载addBook.css --%>
<link rel="stylesheet" type="text/css" href="css/addBook.css" />
<%-- 加载weui.css --%>
<link rel="stylesheet" type="text/css" href="css/weui.css" />
<%-- 加载baidu.js --%>
<script type="text/javascript" src="js/baidu.js"></script>
<%-- 加载weixin.js --%>
<script type="text/javascript" src="js/weixin.js"></script>
<%-- 加载zhixing101.js --%>
<script type="text/javascript" src="js/zhixing101.js"></script>

<%-- 加载鼠标绘制工具 --%>
<script type="text/javascript"
	src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>
<link rel="stylesheet"
	href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css" />

<%-- 加载SUI Mobile --%>
<link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm.css">
<script type='text/javascript'
	src='//g.alicdn.com/sj/lib/zepto/zepto.js' charset='utf-8'></script>
<script type='text/javascript'
	src='//g.alicdn.com/msui/sm/0.6.2/js/sm.js' charset='utf-8'></script>
<!-- 如果你用到了拓展包中的组件，还需要引用下面两个 -->
<link rel="stylesheet"
	href="//g.alicdn.com/msui/sm/0.6.2/css/sm-extend.css">
<script type='text/javascript'
	src='//g.alicdn.com/msui/sm/0.6.2/js/sm-extend.js' charset='utf-8'></script>
<%-- 省市区选择器 --%>
<script type="text/javascript"
	src="//g.alicdn.com/msui/sm/0.6.2/js/sm-city-picker.min.js"
	charset="utf-8"></script>

</head>
<body>

	<%-- 从后台获取config接口注入权限验证参数 --%>
	<input id="appId" type="hidden" value="${appId }" />
	<input id="timestamp" type="hidden" value="${timestamp }" />
	<input id="noncestr" type="hidden" value="${noncestr }" />
	<input id="signature" type="hidden" value="${signature }" />
	<%-- 存储当前粉丝的openid --%>
	<input id="openid" type="hidden" value="${openid }" />

	<%-- 从后台获取图书存放点geotableId --%>
	<input id="bookStoragePlaceGeotableId" type="hidden"
		value="${bookStoragePlaceGeotableId }" />
	<%-- 从后台获取检索图书存放点的半径(单位m) --%>
	<input id="searchBookStoragePlaceRadius" type="hidden"
		value="${searchBookStoragePlaceRadius }" />

	<%-- 从后台获取根url --%>
	<input id="rootUrl" type="hidden" value="${rootUrl }" />

	<%-- 存储经度 --%>
	<input id="globalLongtitude" type="hidden" />
	<%-- 存储纬度 --%>
	<input id="globalLatitude" type="hidden" />

	<%-- 统计工具条 --%>
	<div id="statisticsBar">
		<span class="help-block">收录书籍XXX本，可借阅书籍XXX本，存书点XXX处</span>
	</div>

	<%-- 地图容器 --%>
	<div id="mapContainer"></div>

	<%-- 创建存书点对话框 --%>
	<div id="createBookStoragePlaceDialog">
		<div class="weui-cells__title">存书点信息</div>
		<div class="weui-cells weui-cells_form">
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">名称</label>
				</div>
				<div class="weui-cell__bd">
					<input id="createBookStoragePlaceDialogName" class="weui-input"
						type="text" placeholder="请输入名称" />
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">描述</label>
				</div>
				<div class="weui-cell__bd">
					<input id="createBookStoragePlaceDialogDesc" class="weui-input"
						type="text" placeholder="请输入描述" />
				</div>
			</div>
			<div class="page-group">
				<div class="page page-current">
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label">省市区</label>
						</div>
						<div class="weui-cell__bd">
							<input id="city-picker" class="weui-input" type="text"
								placeholder="请输入省市区" />
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="weui-btn-area">
			<a class="weui-btn weui-btn_primary"
				id="submitCreateBookStoragePlace">提交</a> <a
				class="weui-btn weui-btn_primary" id="cancelCreateBookStoragePlace">取消</a>
		</div>
	</div>

	<%-- 按钮工具条 --%>
	<div id="buttonBar" class="form-group">
		<div class="col-xs-6">
			<button id="createBookStoragePlaceButton" type="submit"
				class="weui-btn weui-btn_mini weui-btn_primary">创建存书点</button>
		</div>
		<div class="col-xs-6">
			<button id="selectBookStoragePlaceButton" type="submit"
				class="weui-btn weui-btn_mini weui-btn_primary">选择已有存书点</button>
		</div>
	</div>

	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							authorityValidate(document);

							$("#city-picker")
									.cityPicker(
											{
												toolbarTemplate : '<header class="bar bar-nav">\
				    <button class="button button-link pull-right close-picker">确定</button>\
				    <h1 class="title">选择省市区</h1>\
				    </header>'
											});

							$("#createBookStoragePlaceButton").click(
									function() {
										$("#statisticsBar").css("display",
												"none");
										$("#mapContainer").css("display",
												"none");
										$("#createBookStoragePlaceDialog").css(
												"display", "block");
										$("#buttonBar").css("display", "none");
									});

							$("#cancelCreateBookStoragePlace")
									.click(
											function() {
												$("#statisticsBar").css(
														"display", "block");
												$("#mapContainer").css(
														"display", "block");
												$(
														"#createBookStoragePlaceDialog")
														.css("display", "none");
												$("#buttonBar").css("display",
														"block");
											});

							$("#submitCreateBookStoragePlace").click(
									function() {

										createBookStoragePlace(document);
									});
						});
	</script>
</body>
</html>
