<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>获取地理位置</title>
</head>
<body>
<script>
$(document).ready(function(){
	var appIdValue = document.getElementById("appId").value;
	var noncestrValue = document.getElementById("noncestr").value;
	var timestampValue = document.getElementById("timestamp").value;
	var signatureValue = document.getElementById("signature").value;
	
	wx.config({
	    debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: appIdValue, // 必填，公众号的唯一标识
	    timestamp: timestampValue, // 必填，生成签名的时间戳
	    nonceStr: noncestrValue, // 必填，生成签名的随机串
	    signature: signatureValue,// 必填，签名，见附录1
	    jsApiList: ['getLocation'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	
	wx.ready(function () {
		  // 7.2 获取当前地理位置
		  document.querySelector('#getLocation').onclick = function () {
		    wx.getLocation({
		      success: function (res) {
		        alert(JSON.stringify(res));
		      },
		      cancel: function (res) {
		        alert('用户拒绝授权获取地理位置');
		      }
		    });
		  };
	});

	wx.error(function (res) {
	      alert(res.errMsg);
	});

});

</script>
<input id="appId" type="hidden" value="${appId }"/>
<input id="noncestr" type="hidden" value="${noncestr }"/>
<input id="timestamp" type="hidden" value="${timestamp }"/>
<input id="signature" type="hidden" value="${signature }"/>
<button class="btn btn_primary" id="getLocation">getLocation</button>
</body>
</html>