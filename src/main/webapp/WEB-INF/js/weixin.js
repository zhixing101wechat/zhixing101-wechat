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
		jsApiList : [ 'getLocation', 'chooseImage', 'previewImage', 'uploadImage' ]

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

		// 5 图片接口
		// 5.1 拍照、本地选图
		var images = {
			localId : [],
			serverId : []
		};
		document.querySelector('#chooseImage').onclick = function() {
			wx.chooseImage({
				success : function(res) {
					images.localId = res.localIds;
					alert('已选择 ' + res.localIds.length + ' 张图片');
				}
			});
		};
		// 5.3 上传图片
		document.querySelector('#uploadImage').onclick = function() {
			if (images.localId.length == 0) {
				alert('请先使用 chooseImage 接口选择图片');
				return;
			}
			var i = 0, length = images.localId.length;
			images.serverId = [];
			function upload() {
				wx.uploadImage({
					localId : images.localId[i],
					success : function(res) {
						i++;
						alert('已上传：' + i + '/' + length);
						images.serverId.push(res.serverId);
						if (i < length) {
							upload();
						}
					},
					fail : function(res) {
						alert(JSON.stringify(res));
					}
				});
			}
			upload();
		};

	});

	// 通过error接口处理失败验证
	wx.error(function(res) {
		alert(res.errMsg);
	});
}

//