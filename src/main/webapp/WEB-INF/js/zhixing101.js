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
	var longtitudeValue = document.getElementById("globalLongtitude").value;
	var latitudeValue = document.getElementById("globalLatitude").value;
	alert("longtitudeValue : " + longtitudeValue);
	alert("latitudeValue : " + latitudeValue);
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