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

</head>
<body>

	<%-- 从后台获取config接口注入权限验证参数 --%>
	<input id="appId" type="hidden" value="${appId }" />
	<input id="timestamp" type="hidden" value="${timestamp }" />
	<input id="noncestr" type="hidden" value="${noncestr }" />
	<input id="signature" type="hidden" value="${signature }" />

	<%-- 从后台获取图书存放点geotableId --%>
	<input id="bookStoragePlaceGeotableId" type="hidden"
		value="${bookStoragePlaceGeotableId }" />
	<%-- 从后台获取检索图书存放点的半径(单位m) --%>
	<input id="searchBookStoragePlaceRadius" type="hidden"
		value="${searchBookStoragePlaceRadius }" />

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
	<div id="createBookStoragePlaceDialog" class="form-group">
		<div class="form-group">
			<label for="createBookStoragePlaceDialogName">存书点名称</label> <input
				id="createBookStoragePlaceDialogName" type="text"
				class="form-control" placeholder="请输入存书点名称">
		</div>
		<div class="form-group">
			<label for="createBookStoragePlaceDialogDesc">存书点描述</label> <input
				id="createBookStoragePlaceDialogDesc" type="text"
				class="form-control" placeholder="请输入存书点描述">
		</div>
		<div class="form-group">
			<div class="col-xs-6">
				<button id="submitCreateBookStoragePlace" type="submit"
					class="btn btn-primary">提交</button>
			</div>
			<div class="col-xs-6">
				<button id="cancelCreateBookStoragePlace" type="submit"
					class="btn btn-primary">取消</button>
			</div>
		</div>
	</div>

	<%-- 按钮工具条 --%>
	<div id="buttonBar" class="form-group">
		<div class="col-xs-6">
			<button id="createBookStoragePlaceButton" type="submit"
				class="btn btn-primary">创建存书点</button>
		</div>
		<div class="col-xs-6">
			<button id="selectBookStoragePlaceButton" type="submit"
				class="btn btn-primary">选择已有存书点</button>
		</div>
	</div>

	<script type="text/javascript">

		$(document).ready(function() {
			
// 			// 经度
// 			var globalLongtitude;
// 			// 纬度
// 			var globalLatitude;

			authorityValidate(document);

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
