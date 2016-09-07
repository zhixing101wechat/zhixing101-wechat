<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>找书</title>
<style type="text/css">
html{height:100%}
body{height:100%;margin:0px;padding:0px}
#container{height:100%}
</style>
</head>
<body>
<div id="container"></div>
<script type="text/javascript">
var map = new BMap.Map("container");          // 创建地图实例
var point = new BMap.Point(116.404, 39.915);  // 创建点坐标
map.centerAndZoom(point, 15);                 // 初始化地图，设置中心点坐标和地图级别
</script>
</body>
</html>