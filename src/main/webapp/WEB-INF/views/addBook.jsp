<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>录书</title>

<style type="text/css">
html {
	height: 100%
}

body {
	height: 100%;
	margin: 0px;
	padding: 0px
}

#statisticsBar {
	height: 5%
}

#mapContainer {
	height: 90%
}

#buttonBar {
	height: 5%
}
</style>

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

	<%-- 统计工具条 --%>
	<div id="statisticsBar">
		<span class="help-block">收录书籍XXX本，可借阅书籍XXX本，存书点YYY处</span>
	</div>

	<%-- 地图容器 --%>
	<div id="mapContainer"></div>

	<%-- 按钮工具条 --%>
	<div id="buttonBar" class="form-group">
		<div class="col-xs-6">
			<button id="createBookStoragePlaceButton" type="submit" class="btn btn-primary" value="创建存书点"></button>
		</div>
	</div>

	<script type="text/javascript">
		// 初始化地图
		function initMap(lng, lat) {

			// 根据wgs84坐标创建地理坐标点
			var pointWgs84 = new BMap.Point(lng, lat);

			// 创建地图实例
			var map = new BMap.Map("mapContainer");

			// 坐标转换完之后的回调函数
			translateCallback = function(data) {
				if (data.status === 0) {

					var point = data.points[0];
					var marker = new BMap.Marker(point);
					map.addOverlay(marker);
					var label = new BMap.Label("当前位置", {
						offset : new BMap.Size(20, -10)
					});
					marker.setLabel(label);
					map.centerAndZoom(point, 15);
					map.addControl(new BMap.NavigationControl());
				}
			}

			setTimeout(function() {
				var convertor = new BMap.Convertor();
				var pointArr = [];
				pointArr.push(pointWgs84);
				convertor.translate(pointArr, 1, 5, translateCallback)
			}, 0);

		}

		$(document)
				.ready(
						function() {

							// 从hidden字段获取config接口注入权限验证参数
							var appIdValue = document.getElementById("appId").value;
							var noncestrValue = document
									.getElementById("noncestr").value;
							var timestampValue = document
									.getElementById("timestamp").value;
							var signatureValue = document
									.getElementById("signature").value;

							// 从hidden字段获取图书存放点geotableId
							var storagePlaceGeotableId = document
									.getElementById("bookStoragePlaceGeotableId").value;

							// 从hidden字段获取检索图书存放点的半径(单位m)
							var searchRadius = document
									.getElementById("searchBookStoragePlaceRadius").value;

							// 通过config接口注入权限验证配置
							wx.config({
								// 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
								debug : false,
								// 必填，公众号的唯一标识
								appId : appIdValue,
								// 必填，生成签名的时间戳
								timestamp : timestampValue,
								// 必填，生成签名的随机串
								nonceStr : noncestrValue,
								// 必填，签名，见附录1
								signature : signatureValue,
								// 必填，需要使用的JS接口列表，所有JS接口列表见附录2
								jsApiList : [ 'getLocation', 'scanQRCode' ]

							});

							// 通过ready接口处理成功验证
							wx.ready(function() {

								// 获取当前地理位置
								wx.getLocation({

									// 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
									type : 'wgs84',
									success : function(res) {

										// 纬度，浮点数，范围为90 ~ -90
										var latitude = res.latitude;
										// 经度，浮点数，范围为180 ~ -180
										var longitude = res.longitude;
										// 速度，以米/每秒计
										var speed = res.speed;
										// 位置精度
										var accuracy = res.accuracy;

										// 初始化地图
										initMap(parseFloat(longitude),
												parseFloat(latitude));
									},
									cancel : function(res) {
										alert('用户拒绝授权获取地理位置');
									}
								});
							});

							// 通过error接口处理失败验证
							wx.error(function(res) {
								alert(res.errMsg);
							});
						});
	</script>
</body>
</html>
