<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>录书forPC</title>

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
			
			// 初始化地图
			// 百度地图API功能
			var map = new BMap.Map("mapContainer");
			var point = new BMap.Point(116.331398,39.897445);
			map.centerAndZoom(point,12);
		
			var geolocation = new BMap.Geolocation();
			geolocation.getCurrentPosition(function(r){
				if(this.getStatus() == BMAP_STATUS_SUCCESS){
					var mk = new BMap.Marker(r.point);
					map.addOverlay(mk);
					map.panTo(r.point);
					var pointVar = r.point;
					document.getElementById("globalLongtitude").value = pointVar.lng;
					document.getElementById("globalLatitude").value = pointVar.lat;
					document.getElementById("globalLongtitude").value = pointVar.lng.toString();
					document.getElementById("globalLatitude").value = pointVar.lat.toString();
					debugger;
					alert('您的位置：'+r.point.lng+','+r.point.lat);
				}
				else {
					alert('failed'+this.getStatus());
				}        
			},{enableHighAccuracy: true})
			//关于状态码
			//BMAP_STATUS_SUCCESS	检索成功。对应数值“0”。
			//BMAP_STATUS_CITY_LIST	城市列表。对应数值“1”。
			//BMAP_STATUS_UNKNOWN_LOCATION	位置结果未知。对应数值“2”。
			//BMAP_STATUS_UNKNOWN_ROUTE	导航结果未知。对应数值“3”。
			//BMAP_STATUS_INVALID_KEY	非法密钥。对应数值“4”。
			//BMAP_STATUS_INVALID_REQUEST	非法请求。对应数值“5”。
			//BMAP_STATUS_PERMISSION_DENIED	没有权限。对应数值“6”。(自 1.1 新增)
			//BMAP_STATUS_SERVICE_UNAVAILABLE	服务不可用。对应数值“7”。(自 1.1 新增)
			//BMAP_STATUS_TIMEOUT	超时。对应数值“8”。(自 1.1 新增)
			
			
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
