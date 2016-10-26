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
		jsApiList : [ 'getLocation' ]

	});
}