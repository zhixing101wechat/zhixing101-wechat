/**
 * 百度地图JS
 */

// 初始化地图
function initMap(lng, lat, searchRadius, storagePlaceGeotableId) {

	// 根据从微信JS SDK获取的wgs84坐标创建Point对象
	var pointWgs84 = new BMap.Point(lng, lat);

	// 创建Map对象
	var map = new BMap.Map("mapContainer");

	// 在地图上显示point
	function displayPoint(point) {
		// 清除地图上所有覆盖物
		map.clearOverlays();
		// 根据point对象创建图像标注
		var marker = new BMap.Marker(point);
		// 将覆盖物添加到地图中
		map.addOverlay(marker);
		// 创建文本标注
		var label = new BMap.Label("当前位置", {
			offset : new BMap.Size(20, -10)
		});
		// 为标注添加文本标注
		marker.setLabel(label);
		// 存储百度地图经纬度坐标
		setLngLat(point);
		// 初始化地图
		map.centerAndZoom(point, 15);
		// 添加平移缩放控件
		map.addControl(new BMap.NavigationControl());
	}

	// 存储百度地图经纬度坐标
	function setLngLat(point) {

		document.getElementById("globalLongtitude").value = point.lng;
		document.getElementById("globalLatitude").value = point.lat;
	}

	// 坐标转换完之后的回调函数
	translateCallback = function(data) {
		if (data.status === 0) {
			var point = data.points[0];
			displayPoint(point);
		}
	}

	// 把wgs84坐标系的坐标转换为百度地图坐标
	setTimeout(function() {
		var convertor = new BMap.Convertor();
		var pointArr = [];
		pointArr.push(pointWgs84);
		convertor.translate(pointArr, 1, 5, translateCallback)
	}, 0);

	// 单击地图上的点，重新显示标注
	function movePoint(e) {
		displayPoint(e.point);
	}

	// 添加地图单击事件
	map.addEventListener("click", movePoint);
}