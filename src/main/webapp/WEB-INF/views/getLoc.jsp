<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>获取地理位置</title>
<style type="text/css">
html{height:100%}
body{height:100%;margin:0px;padding:0px}
#container{height:100%}
</style>
</head>
<body>
<input id="appId" type="hidden" value="${appId }" />
<input id="noncestr" type="hidden" value="${noncestr }" />
<input id="timestamp" type="hidden" value="${timestamp }" />
<input id="signature" type="hidden" value="${signature }" />
<div id="container"></div>
<script type="text/javascript">
var map = new BMap.Map("container");          // 创建地图实例
// var point = new BMap.Point(116.404, 39.915);  // 创建点坐标
// map.centerAndZoom(point, 15);                 // 初始化地图，设置中心点坐标和地图级别
var latitudeWgs84;
var longitudeWgs84;

	$(document).ready(function() {
		var appIdValue = document.getElementById("appId").value;
		var noncestrValue = document.getElementById("noncestr").value;
		var timestampValue = document.getElementById("timestamp").value;
		var signatureValue = document.getElementById("signature").value;

		wx.config({
			debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
			appId : appIdValue, // 必填，公众号的唯一标识
			timestamp : timestampValue, // 必填，生成签名的时间戳
			nonceStr : noncestrValue, // 必填，生成签名的随机串
			signature : signatureValue,// 必填，签名，见附录1
			jsApiList : [ 'getLocation' ]
		// 必填，需要使用的JS接口列表，所有JS接口列表见附录2
		});

		wx.ready(function() {
			wx.getLocation({
				type : 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
				success : function(res) {
					var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
					var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180
					var speed = res.speed; // 速度，以米/每秒计
					var accuracy = res.accuracy; // 位置精度

					latitudeWgs84 = res.latitude;
					longitudeWgs84 = res.longitude;

 					//alert(JSON.stringify(res));
					
					var point = new BMap.Point(parseFloat(longitudeWgs84), parseFloat(latitudeWgs84));  // 创建点坐标
					map.centerAndZoom(point, 15);                 // 初始化地图，设置中心点坐标和地图级别
 					
				},
				cancel : function(res) {
					alert('用户拒绝授权获取地理位置');
				}
			});
		});

// 		// 		wx.error(function(res) {
// 		// 			alert(res.errMsg);
// 		// 		});

// 	});
</script>
</body>
</html>