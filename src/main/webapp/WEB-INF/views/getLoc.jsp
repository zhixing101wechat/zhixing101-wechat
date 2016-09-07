<!DOCTYPE html>
<%@ include file="common.jsp"%>
<html>
<head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>获取地理位置</title>
</head>
<body>
<script>
wx.config({
    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
    appId: 'wx5155a0dbe312a20e', // 必填，公众号的唯一标识
    timestamp: 1473169028193, // 必填，生成签名的时间戳
    nonceStr: 'Wm3WZYTPz0wzccnW', // 必填，生成签名的随机串
    signature: '6e6951cadf0ccdcc8600f003fb7f089b0633c2da',// 必填，签名，见附录1
    jsApiList: ['getLocation'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
});
wx.ready(function () {
      // 7.2 获取当前地理位置
      document.querySelector('#getLocation').onclick = function () {
          wx.getLocation({
                type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
                success: function (res) {
                    //var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
                    //var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
                    //var speed = res.speed; // 速度，以米/每秒计
                    //var accuracy = res.accuracy; // 位置精度
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
</script>
<span class="desc">获取地理位置接口</span>
<button id="getLocation">getLocation</button>
</body>
</html>