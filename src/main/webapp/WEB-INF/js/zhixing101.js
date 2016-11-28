/**
 * 知行101 JS
 */

function createBookStoragePlace(document) {

	// 获取根url
	var rootUrlValue = document.getElementById("rootUrl").value;
	var requestUrl = rootUrlValue + "/createBookStoragePlace";

	// 获取存书点经纬度
	var globalLongtitude = document.getElementById("globalLongtitude").value;
	var globalLatitude = document.getElementById("globalLatitude").value;

	// 获取存书点所有人微信OpenID
	var openid = document.getElementById("openid").value;

	// 从创建存书点表单获取输入值
	var bookStoragePlaceName = document.getElementById("bookStoragePlaceName").value;
	var bookStoragePlaceDescription = document
			.getElementById("bookStoragePlaceDescription").value;
	var bookStoragePlaceProvince = document
			.getElementById("bookStoragePlaceProvince").value;
	var bookStoragePlaceCity = document.getElementById("bookStoragePlaceCity").value;
	var bookStoragePlaceDistrict = document
			.getElementById("bookStoragePlaceDistrict").value;
	var bookStoragePlaceAddress = document
			.getElementById("bookStoragePlaceAddress").value;
	var bookStoragePlacePhone = document
			.getElementById("bookStoragePlacePhone").value;
	var bookStoragePlaceMobilePhone = document
			.getElementById("bookStoragePlaceMobilePhone").value;
	var bookStoragePlaceOpenTime = document
			.getElementById("bookStoragePlaceOpenTime").value;
	var bookStoragePlaceTraffic = document
			.getElementById("bookStoragePlaceTraffic").value;

	// TODO:输入值检查

	var createBookStoragePlaceRequest = {
		name : bookStoragePlaceName,
		description : bookStoragePlaceDescription,
		longtitude : globalLongtitude,
		latitude : globalLatitude,
		province : bookStoragePlaceProvince,
		city : bookStoragePlaceCity,
		district : bookStoragePlaceDistrict,
		address : bookStoragePlaceAddress,
		phone : bookStoragePlacePhone,
		mobilePhone : bookStoragePlaceMobilePhone,
		wechatOpenId : openid,
		open_time : bookStoragePlaceOpenTime,
		traffic : bookStoragePlaceTraffic
	};

	// 向后台发送请求
	$.ajax({
		url : requestUrl,
		type : "post",
		data : createBookStoragePlaceRequest,
		dataType : "text",
		success : function(res) {
			alert(res);
		},
		error : function(res) {
			alert("失败");
		}
	})
}