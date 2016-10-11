<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>找书</title>
    <!-- this is git test -->
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
	height: 10%
}
#searchBar {
	height: 10%
}
#mapContainer {
	height: 80%
}
</style>
<!--加载鼠标绘制工具-->
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
<span class="help-block">收录书籍XXX本，可借阅书籍XXX本，存书点XXX处</span>
</div>
<%-- 搜索工具条 --%>
<div id="searchBar" class="form-group">
<div class="col-xs-6"><input id="searchKeyword" type="text" class="form-control"></div>
<div class="col-xs-4"><button id="searchButton" type="submit" class="btn btn-primary">知行一下</button></div>
</div>
<%-- 地图容器 --%>
<div id="mapContainer"></div>

<script type="text/javascript">
	// 初始化地图
	function initMap(lng, lat, searchRadius, storagePlaceGeotableId) {
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
				map.setCenter(point);

				var options = {
					renderOptions : {
						map : map
					},
					onSearchComplete : function(results) {
						//     			alert('Search Completed');
						//可添加自定义回调函数
					}
				};
				var localSearch = new BMap.LocalSearch(map, options);
				map.addEventListener("load", function() {
					var circle = new BMap.Circle(point, searchRadius, {
						fillColor : "blue",
						strokeWeight : 1,
						fillOpacity : 0.3,
						strokeOpacity : 0.3
					});
					map.addOverlay(circle);
					localSearch.searchNearby('图书馆', point, searchRadius, {
						customData : {
							geotableId : storagePlaceGeotableId
						}
					});
				});

				// 初始化地图，设置中心点坐标和地图级别
				map.centerAndZoom(point, 12);
				map.enableScrollWheelZoom();
				//添加默认缩放平移控件
				map.addControl(new BMap.NavigationControl());

				var drawingManager = new BMapLib.DrawingManager(map, {
					isOpen : false, //是否开启绘制模式
					enableDrawingTool : false, //是否显示工具栏
					drawingToolOptions : {
						anchor : BMAP_ANCHOR_TOP_RIGHT, //位置
						offset : new BMap.Size(5, 5), //偏离值
						scale : 0.8, //工具栏缩放比例
						drawingModes : [ BMAP_DRAWING_CIRCLE ]
					}
				});
				var circle = null;
				drawingManager.addEventListener('circlecomplete', function(e,
						overlay) {
					//	circlecomplete
					map.clearOverlays();
					circle = e;
					map.addOverlay(overlay);
					var radius = parseInt(e.getRadius());
					var center = e.getCenter();
					drawingManager.close();
					localSearch.searchNearby('图书馆', center, radius, {
						customData : {
							geotableId : storagePlaceGeotableId
						}
					});
				});

				// 添加地图单击事件
				function showInfo(e) {
// 					alert(e.point.lng + ", " + e.point.lat);
					initMap(e.point.lng, e.point.lat, searchRadius, storagePlaceGeotableId)
				}
				map.addEventListener("dblclick", showInfo);

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
						var noncestrValue = document.getElementById("noncestr").value;
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
							debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
							appId : appIdValue, // 必填，公众号的唯一标识
							timestamp : timestampValue, // 必填，生成签名的时间戳
							nonceStr : noncestrValue, // 必填，生成签名的随机串
							signature : signatureValue, // 必填，签名，见附录1
							jsApiList : [ 'getLocation' ]
						// 必填，需要使用的JS接口列表，所有JS接口列表见附录2
						});

						// 通过ready接口处理成功验证
						wx.ready(function() {

							// 获取当前地理位置
							wx.getLocation({
								type : 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
								success : function(res) {
									var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
									var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180
									var speed = res.speed; // 速度，以米/每秒计
									var accuracy = res.accuracy; // 位置精度

									// 弹出警告框显示getLocation函数知行后的响应
									//alert(JSON.stringify(res));

									// 初始化地图
									initMap(parseFloat(longitude), parseFloat(latitude), searchRadius, storagePlaceGeotableId);
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
						
						// 知行一下按钮按下事件
						$("#searchButton").click(function () {
							var keyword = $("#searchKeyword").val();

							$.ajax({
							url : "http://zhixing101.zzhkll.com/wechat/findBookBiz?keyword" + keyword,
				    		type : "get",
				    		success : function(res) {
				    			alert(res);
				    		},
				    		error : function(res){
				    			alert("失败");
				    		}
							})
						});
					});
</script>
</body>
</html>