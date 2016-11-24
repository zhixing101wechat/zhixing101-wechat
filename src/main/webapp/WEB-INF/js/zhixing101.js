/**
 * 知行101 JS
 */

function createBookStoragePlace(document) {

	// 获取根url
	var rootUrlValue = document.getElementById("rootUrl").value;
	var requestUrl = rootUrlValue + "/createBookStoragePlaceBiz";

	// 从创建存书点输入框获取参数
	var nameValue = document.getElementById("createBookStoragePlaceDialogName").value;
	var descriptionValue = document
			.getElementById("createBookStoragePlaceDialogDesc").value;

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

	var longtitudeValue = document.getElementById("globalLongtitude").value;
	var latitudeValue = document.getElementById("globalLatitude").value;

	var bookStoragePlace = {
		name : nameValue,
		description : descriptionValue,
		longtitude : longtitudeValue,
		latitude : latitudeValue
	};

	// 向后台发送请求
	$.ajax({
		url : requestUrl,
		type : "post",
		data : bookStoragePlace,
		dataType : "text",
		success : function(res) {
			alert(res);
		},
		error : function(res) {
			alert("失败");
		}
	})
}