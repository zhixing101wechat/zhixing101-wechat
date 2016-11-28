/**
 * 微信JS
 */

// 权限验证
function authorityValidate(document) {

	// 从hidden字段获取config接口注入权限验证参数
	var appIdValue = document.getElementById("appId").value;
	var noncestrValue = document.getElementById("noncestr").value;
	var timestampValue = document.getElementById("timestamp").value;
	var signatureValue = document.getElementById("signature").value;

	// 从hidden字段获取图书存放点geotableId
	var storagePlaceGeotableId = document
			.getElementById("bookStoragePlaceGeotableId").value;
	// 从hidden字段获取检索图书存放点的半径(单位m)
	var searchRadius = document.getElementById("searchBookStoragePlaceRadius").value;

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
		jsApiList : [ 'getLocation', 'chooseImage', 'uploadImage']

	});

	// 通过ready接口处理成功验证
	wx.ready(function() {

		// 获取地理位置
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
				initMap(parseFloat(longitude), parseFloat(latitude),
						searchRadius, storagePlaceGeotableId);
			},
			cancel : function(res) {
				alert('用户拒绝授权获取地理位置');
			}
		});

		// 拍照或从手机相册中选图
		$("#uploaderInput").click(function() {
			wx.chooseImage({
				count : 1, // 默认9
				sizeType : [ 'original', 'compressed' ], // 可以指定是原图还是压缩图，默认二者都有
				sourceType : [ 'album', 'camera' ], // 可以指定来源是相册还是相机，默认二者都有
				success : function(res) {
					var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
				}
			});
		});

		// 上传图片
	});

	// 通过error接口处理失败验证
	wx.error(function(res) {
		alert(res.errMsg);
	});
}

//