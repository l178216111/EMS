<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Equipment Status System</title>
<link href="${pageContext.request.contextPath }/plugins/font-awesome-4.3.0/css/font-awesome.css" rel="stylesheet" />
<script type="text/javascript"
	src="${pageContext.request.contextPath }/plugins/jquery-1.11.2.js"></script>
<link
	href="${pageContext.request.contextPath }/plugins/bootstrap-3.3.4-dist/css/bootstrap.min.css"
	rel="stylesheet">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/plugins/bootstrap-3.3.4-dist/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/plugins/highcharts-4.1.3/js/highcharts.js"></script>
<link href="${pageContext.request.contextPath }/plugins/script/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath }/plugins/script/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/plugins/dds.js"></script>
</head>
<body>
<div class="panel panel-default">
   <div align="left" class="panel-body">
   		<div style="border:1px solid #ccc;">
   	<!-- <h2 style="border-bottom:1px solid #ccc;margin:0px;padding:10px 0px;background:#f5f5f5;">Equipment Status System</h2> -->	
		  <!--   <div class="form-group" style="width:578px;margin:0 auto;margin-top:5px;margin-bottom:5px;">--> 
			     <!-- <label for="name">设备类型列表</label> -->
			    <div style="text-align:center;margin-top:10px;margin-left:5px;">
			    Equipment List:&nbsp;&nbsp;
			      <select id="eqptyp" name="eqptyp" onchange="changeDevicesType()" style="width: 100px;height:28px;">
			         <option value="TS750">TS750</option>
			         <option value="TSMST">TSMST</option>
			         <option value="TS93K">TS93K</option>
			         <option value="TSCAT">TSCAT</option>
			         <option value="TSFLX">TSFLX</option>
			         <option value="BGRIND">BGRIND</option>
			         <option value="TSLTX">TSLTX</option>
			      </select>&nbsp;&nbsp;
			      <!-- <input id="way" name="checknear" type="radio" value="1" checked="checked" onclick="near_chick()">&nbsp;查看最近的 -->
				<!-- 	<div style="margin-top:10px;text-align:left;">-->
	            Data Time:&nbsp;&nbsp;<input align="center" type="text" id="stime" name="stime" placeholder="Set Date" style="width: 100px;height:28px;padding:0px;" onclick="WdatePicker({dateFmt:&#39;yyyy-MM-dd&#39;})">
	            	<!--  至  <input id="etime" name="etime" type="text" placeholder="截止时间" style="width: 200px;height:28px;padding:0px;" onclick="WdatePicker({dateFmt:&#39;yyyy-MM-dd HH:mm:ss&#39;})"> -->
			      <!-- &nbsp; &nbsp;<input id="way" name="checktime" type="radio" value="0" onclick="time_chick(this)">选择时间段查看 -->
			      &nbsp;&nbsp;
			    Interval:&nbsp;<!-- <input id="hour" name="hour" type="radio" value="8" checked="checked" onclick="data_8()">8h -->
			      <input id="hour" name="hour" type="radio" value="1">12 Hours(Night)
			      <input id="hour" name="hour" type="radio" value="2">12 Hours(Day)
			      <input id="hour" name="hour" type="radio" value="0">24 Hours
			      &nbsp;&nbsp;
			      <button style="width: 50px;height:28px;padding:0px;" onmousedown="time_chick()">submit</button>
	     	<!--        </div>    -->
	          <!-- </div>  -->   
		    </div>
	<!-- 	<div style="overflow:scroll;height:900px;">  -->
   		<div id="container" style="width:100%;height:900px;margin-top:10px;"></div>
   		<div id="loading" align="center" style="position:absolute;z-index:9999;height:30px;width:200px;top:50%;left:50%;
				margin-left:-150px;text-align:center;border: solid 2px #86a5ad"">
				<i class="fa fa-spinner fa-spin"></i>Loading......
			</div>
	<!-- </div> -->
   	  	<!-- 	<h5 align="center"><img style="height: 100%;width: 100%;" src="${pageContext.request.contextPath }/img/status.png" /></h5> -->	
   </div>
</div>
</body>
</html>