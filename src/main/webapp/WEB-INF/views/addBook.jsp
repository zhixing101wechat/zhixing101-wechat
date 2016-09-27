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
	height: 10%
}
#searchBar {
	height: 10%
}
#mapContainer {
	height: 40%
}
#scanQRCode{
	height:10%;
	margin-left:10%;
}
#bookInfo{
	height:30%;
	margin:0 5%;
	overflow-y:scroll;
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
<span class="help-block">收录书籍XXX本，可借阅书籍XXX本，存书点YYY处</span>
</div>
<%-- 扫一扫--%>
<div id="scanQRCode"><button class="btn btn-primary">扫一扫</button></div>
<div id="bookInfo"></div>
<%-- 搜索工具条 --%>
<div id="searchBar" class="form-group">
<div class="col-xs-6"><input type="text" class="form-control"></div>
<div class="col-xs-4"><button type="submit" class="btn btn-primary">知行一下</button></div>
</div>
<%-- 地图容器 --%>
<div id="mapContainer"></div>


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
							jsApiList : [ 'getLocation','scanQRCode' ]
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
									initMap(parseFloat(longitude), parseFloat(latitude));
								},
								cancel : function(res) {
									alert('用户拒绝授权获取地理位置');
								}
							});
							
							//扫一扫
							$("#scanQRCode").click(function(){
								wx.scanQRCode({
								    needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
								    scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
								    success: function (res) {
								    	var result = res.resultStr.split(","); // 当needResult 为 1 时，扫码返回的结果
											var isbn = result[1];//isbn值
											var url = "http://www.mikko.tech/wechat/findBookByISBN?isbn="+isbn;
								    	$.ajax({
								    		url:"http://www.mikko.tech/wechat/findBookByISBN?isbn="+isbn,
								    		type : "get",
								    		success : function(data) {
								    			var jsonData = data;
													alert(typeof jsonData === 'object')
													alert("jsonData:"+jsonData)
													alert(jsonData.title);
								    			var bookName,bookAuthor,bookPrice,bookPublisher,bookVersion,bookIsbn10,bookIsbn13,bookSummary,bookBinding,bookImage;
								    			bookName = jsonData.title;
								    			bookAuthor = jsonData.author;
								    			bookPrice = jsonData.price;
								    			bookPublisher = jsonData.publisher;
								    			bookVersion = jsonData.version;
								    			bookIsbn10 = jsonData.isbn10;
								    			bookIsbn13 = jsonData.isbn13;
								    			bookSummary = jsonData.summary;
								    			bookBinding = jsonData.binding;
								    			bookImageUrl = jsonData.doubanImageUrl;
								    			
								    			var str = "<p>书名："+bookName+"</p>"
								    				+"<p>作者："+bookAuthor+"</p>"
								    				+"<p>价格："+bookPrice+"</p>"
								    				+"<p>出版："+bookPublisher+"</p>"
								    				+"<p>版本："+bookVersion+"</p>"
								    				+"<p>isbn10："+bookIsbn10+"</p>"
								    				+"<p>isbn13："+bookIsbn13+"</p>"
								    				+"<p>简介："+bookSummary+"</p>"
								    				+"<p>装订："+bookBinding+"</p>"
								    				+"<p>图片：<img src='"+bookImageUrl+"' /></p>";
								    				
								    			$("#bookInfo").html(str);
								    		},
								    		error : function(error) {
								    			console.log("错误:" + data )
								    		}
								    	}) 
									},cancel : function(res) {
										alert('error:'+res);
									}
								});
							})
						});

						// 通过error接口处理失败验证
						wx.error(function(res) {
							alert(res.errMsg);
						});
					});
</script>
</body>
</html>
