/**
 * 知行101 JS
 */

function createBookStoragePlace(document) {

	var bookStoragePlace;
	
	// 从创建存书点输入框获取参数
	bookStoragePlace.name = document.getElementById("createBookStoragePlaceDialog_Name").value;
	bookStoragePlace.description = document.getElementById("createBookStoragePlaceDialog_Desc").value;
	
	// 向后台发送请求
	$.ajax({
		url : "http://zhixing101.zzhkll.com/wechat/createBookStoragePlaceBiz",
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