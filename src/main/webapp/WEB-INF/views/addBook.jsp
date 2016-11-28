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
<%-- 加载distpicker.min.js --%>
<script type="text/javascript" src="js/distpicker.min.js"></script>

<%-- 加载鼠标绘制工具 --%>
<script type="text/javascript"
	src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>
<link rel="stylesheet"
	href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css" />

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
					<input id="bookStoragePlaceName" class="weui-input" type="text"
						placeholder="请输入名称" />
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">描述</label>
				</div>
				<div class="weui-cell__bd">
					<input id="bookStoragePlaceDescription" class="weui-input"
						type="text" placeholder="请输入描述" />
				</div>
			</div>
			<div class="weui-cell weui-cell_select">
				<div class="weui-cell__hd">
					<label class="weui-label">省市区</label>
				</div>
				<div id="distpicker" class="weui-cell__bd">
					<select id="bookStoragePlaceProvince" class="weui-select"></select>
					<select id="bookStoragePlaceCity" class="weui-select"></select> <select
						id="bookStoragePlaceDistrict" class="weui-select"></select>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">详细地址</label>
				</div>
				<div class="weui-cell__bd">
					<input id="bookStoragePlaceAddress" class="weui-input" type="text"
						placeholder="请输入详细地址" />
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">联系电话</label>
				</div>
				<div class="weui-cell__bd">
					<input id="bookStoragePlacePhone" class="weui-input" type="number"
						pattern="[0-9]{0,4}-?[0-9]*" placeholder="请输入联系电话" />
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">手机</label>
				</div>
				<div class="weui-cell__bd">
					<input id="bookStoragePlaceMobilePhone" class="weui-input"
						type="number" pattern="[0-9]{11,12}" placeholder="请输入手机号码" />
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">开放时间</label>
				</div>
				<div class="weui-cell__bd">
					<input id="bookStoragePlaceOpenTime" class="weui-input" type="text"
						placeholder="请输入开放时间" />
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">交通</label>
				</div>
				<div class="weui-cell__bd">
					<input id="bookStoragePlaceTraffic" class="weui-input" type="text"
						placeholder="请输入交通方式" />
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">图片</label>
				</div>
				<div class="weui-cell__bd">
					<button class="weui-btn weui-btn_primary" id="chooseImage">选择图片</button>
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
		$(document).ready(function() {
		
			authorityValidate(document);

			$("#distpicker").distpicker({
				province : "请选择所在省",
				city : "请选择所在市",
				district : "请选择所在区"
			});

			$("#createBookStoragePlaceButton").click(function() {
				$("#statisticsBar").css("display", "none");
				$("#mapContainer").css("display", "none");
				$("#createBookStoragePlaceDialog").css("display", "block");
				$("#buttonBar").css("display", "none");
			});

			$("#cancelCreateBookStoragePlace").click(function() {
				$("#statisticsBar").css("display", "block");
				$("#mapContainer").css("display", "block");
				$("#createBookStoragePlaceDialog").css("display", "none");
				$("#buttonBar").css("display", "block");
			});

			$("#submitCreateBookStoragePlace").click(function() {

				createBookStoragePlace(document);
			});
		});
	</script>
</body>
</html>
