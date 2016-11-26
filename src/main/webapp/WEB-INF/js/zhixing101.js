/**
 * 知行101 JS
 */

function createBookStoragePlace(document) {

	// 获取根url
	var rootUrlValue = document.getElementById("rootUrl").value;
	var requestUrl = rootUrlValue + "/createBookStoragePlace";

	// 从创建存书点输入框获取参数
	var nameValue = document.getElementById("createBookStoragePlaceDialogName").value;
	var descriptionValue = document
			.getElementById("createBookStoragePlaceDialogDesc").value;
	var longtitudeValue = document.getElementById("globalLongtitude").value;
	var latitudeValue = document.getElementById("globalLatitude").value;
	var openidValue = document.getElementById("openid").value;

	// 检查输入项
	// TODO:检查无效
	if (nameValue == null || nameValue == undefined || nameValue == "") {
		alert("存书点名称为必填项");
		return;
	}
	if (descriptionValue == null || descriptionValue == undefined || descriptionValue == "") {
		alert("存书点描述为必填项");
		return;
	}

	var createBookStoragePlaceRequest = {
		name : nameValue,
		description : descriptionValue,
		longtitude : longtitudeValue,
		latitude : latitudeValue,
		wechatOpenId : openidValue
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