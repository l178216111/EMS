var options = {
	chart : {
		type : 'bar',
		backgroundColor: '#CCCCCC',
		renderTo : 'container'
	},
	credits : { 
		enabled : false 
	},
	title : {
		text : ''
	},
	legend : {
		enabled : false
	},
	tooltip : {
		formatter : function() {
			var a = parseInt(this.y * 3600);
			// 小时：h=time/3600（整除）
			// 分钟：m=(time-h*3600)/60 （整除）
			// 秒：s=(time-h*3600) mod 60 （取余）
			var h = parseInt(a / 3600);
			var m = parseInt((a - h * 3600) / 60);
			var s = (a - h * 3600) % 60;
			var c = "";
			if (h > 0) {
				c += h.toString() + "小时";
			}
			if (m > 0) {
				c += m.toString() + "分";
			}
			if (s > 0) {
				c += s.toString() + "秒"
			}
			return '设备号:' + this.x + '<br/>' + this.series.name + ':' + c
					+ '</b>';
		}
	},
	plotOptions : {
		series : {
			stacking : 'normal',
		}
	}
};


// 选择时间段查看
function time_chick() {
	
//	var chart_line = $("#container").highcharts();  
//    var svg_line = chart_line.getSVG();  
//	alert(typeof svg_line);
//	alert("aaaaa");
//	alert(svg_line);
	var stime = $("#stime").val();
//	var etime = $("#etime").val();
	
	// 检查开始时间结束时间是否为空
	if (stime.length == 0) {
		alert("Time can not be empty！");
	}else {
		var myDate = new Date().Format("yyyy-MM-dd");
		var ntime = new Date().Format("yyyy-MM-dd HH:mm:ss");
		var status=$("[name=hour]:checked").val()
		var etime="";
		var a = stime.split("-");
		for ( var d in a) {
			etime=etime+a[d]+"/";
		}
			if(status==0 && stime < myDate){
				stime=stime+ " 08:00:00";
				var sstime=new Date(etime.substring(0, 10)+" 08:00:00");
				var newDate=(sstime.getTime()) + (24 * 60 * 60 * 1000);
				var mtime=new Date(newDate);
				var etime = mtime.Format("yyyy-MM-dd HH:mm:ss");
				data_all(stime,etime);
			}else if(status==1){
				stime=stime+" 20:00:00";
				var sstime=new Date(etime.substring(0, 10)+" 20:00:00");
				var newDate=(sstime.getTime()) + (12 * 60 * 60 * 1000);
				var mtime=new Date(newDate);
				etime = mtime.Format("yyyy-MM-dd HH:mm:ss");
				if(etime<=ntime){
					data_all(stime,etime);
				}else{
					alert("Time setting is incorrect!");
				}
			}else if(status==2){
				stime=stime+" 08:00:00";
				var sstime=new Date(etime.substring(0, 10)+" 08:00:00");
				var newDate=(sstime.getTime()) + (12 * 60 * 60 * 1000);
				var mtime=new Date(newDate);
				etime = mtime.Format("yyyy-MM-dd HH:mm:ss");
				if(etime<=ntime){
					data_all(stime,etime);
				}else{
					alert("Time setting is incorrect!");
				}
			}else{
				alert("Time setting is incorrect!");
			}
			
//			$("input[name='hour']").eq(0).removeAttr("checked");
//			$("input[name='hour']").eq(1).removeAttr("checked");
//			$("input[name='hour']").eq(2).removeAttr("checked");
//			$("input[name='hour']").eq(3).removeAttr("checked");
	}
}
/** 根据用户的选择显示不同的设备图表 */
function changeDevicesType() {
	data_time(12);
}
//默认上12小时的
$(function() {
	 data_time(12);
});

//function data_24(stime) {
//	var stime=stime+ " 00:00:00";
//	var sstime=Date.parse(stime);
//	var newDate=sstime + (24 * 60 * 60 * 1000);
//	var mtime=new Date(newDate);
//	var etime = mtime.Format("yyyy-MM-dd HH:mm:ss");
//	data_all(stime,etime);
//}

//function data_n12(stime) {
//	var stime=stime+" 20:00:00";
//	var sstime=Date.parse(stime);
//	var newDate=sstime + (12 * 60 * 60 * 1000);
//	var mtime=new Date(newDate);
//	var etime = mtime.Format("yyyy-MM-dd HH:mm:ss");
//	var myDate = new Date();
//	data_all(stime,etime);
//}
//tion data_d12(stime) {
//	var myDate = new Date();
//	var stime;
//	var etime;
//	if(myDate.getHours()>=20 && myDate.getHours()<8){
//		etime=myDate.Format("yyyy-MM-dd HH:mm:ss").substring(0, 11)+ "20:00:00";
//		var sstime=Date.parse(etime);
//		var newDate=sstime- (12 * 60 * 60 * 1000);
//		var mtime=new Date(newDate);
//		stime = mtime.Format("yyyy-MM-dd HH:mm:ss");
//	}else{
//		var mtime = (myDate.getTime()) - (24 * 60 * 60 * 1000);
//		var newtime=new Date(mtime);
//		etime=newtime.Format("yyyy-MM-dd HH:mm:ss").substring(0, 11)+ "20:00:00";
//		var sstime=Date.parse(etime);
//		var newDate=sstime- (12 * 60 * 60 * 1000);
//		mtime=new Date(newDate);
//		stime = mtime.Format("yyyy-MM-dd HH:mm:ss");
//	}
//	document.getElementById("stime").value =stime.substring(0, 10);
//	document.getElementById("etime").value = etime.substring(0, 9);
//	data_all(stime,etime);
//}
//// 查看八小时
//function data_8() {
//	data_time(8);
//}
//
//// 查看12小时
//function data_12() {
//	data_time(12);
//}
//
//// 查看24小时
//function data_24() {
//	data_time(24);
//}
//// 查看48小时
//function data_48() {
//	data_time(48);
//}

function data_time(h) {
	var myDate = new Date();
//	if(myDate.getHours()>=8){
//		document.getElementById("stime").value = newTime.Format(
//		"yyyy-MM-dd HH:mm:ss").substring(0, 11)
//		+ ":00:00";
//		stime=(new Date()).pattern(newTime.Format( "yyyy-MM-dd HH:mm:ss").substring(0, 11) + ":00:00");
//	}else{
//		document.getElementById("stime").value = newTime.Format(
//		"yyyy-MM-dd HH:mm:ss").substring(0, 11)
//		+ ":00:00";
//		stime=(new Date()).pattern(newTime.Format( "yyyy-MM-dd HH:mm:ss").substring(0, 11) + ":00:00");
//	}
//	var mtime = (myDate.getTime()) - (h * 60 * 60 * 1000);
//	var newTime = new Date(mtime);
	var stime;
	var etime;
	if(myDate.getHours()>=8 && myDate.getHours()<20){
		$("input[name='hour']").eq(0).click();
		etime=myDate.Format("yyyy/MM/dd HH:mm:ss").substring(0, 11)+ "08:00:00";
		var sstime=new Date(etime);
		var newDate=sstime- (h * 60 * 60 * 1000);
		var mtime=new Date(newDate);
		etime=myDate.Format("yyyy-MM-dd HH:mm:ss").substring(0, 11)+ "08:00:00";
		stime = mtime.Format("yyyy-MM-dd HH:mm:ss");
	}else{
		var mtime;
		//$("input[name='hour']").eq(1).attr("checked","checked");
		$("input[name='hour']").eq(1).click();
		if(myDate.getHours()<8){
			var s=myDate.getHours()+1+23-20;
			mtime = (myDate.getTime()) - (s * 60 * 60 * 1000);
		}else{
			var s=myDate.getHours()-20;
			mtime = (myDate.getTime()) - (s * 60 * 60 * 1000);
		}
		var newtime=new Date(mtime);
		etime=newtime.Format("yyyy/MM/dd HH:mm:ss").substring(0, 13)+ ":00:00";
		var sstime=new Date(etime);
		var newDate=sstime- (h * 60 * 60 * 1000);
		mtime=new Date(newDate);
		etime=newtime.Format("yyyy-MM-dd HH:mm:ss").substring(0, 13)+ ":00:00";
		stime = mtime.Format("yyyy-MM-dd HH:mm:ss");
	}
	document.getElementById("stime").value =stime.substring(0, 10);
//	document.getElementById("etime").value = etime.substring(0, 10);
	data_all(stime,etime);
}

function data_all(stime,etime) {
	document.getElementById('container').innerHTML = "";
	$("#loading").show();
	
	var eqptyp = $("#eqptyp").val();
//	var stime = $("#stime").val();
//	var etime = $("#etime").val();
	// var way = $("[id=way]:checked").val();
	$.ajax({
		async : true,
		url : "/dss/IndexController",
		data : {
			// method : "doPost",
			eqptyp : eqptyp,
			stime : stime,
			etime : etime
		},
		method : "POST",
		dataType : "json",
		success : function(arr) {
			var eqpid = new Array();
			var a = arr[0].eqpid.split(",");
			for ( var d in a) {
				eqpid.push(a[d]);
			}
			options.xAxis = {
				categories : eqpid,
				tickPixelInterval : 0,
//				tickPositioner:function(){
//					return this.value;
//					},
				labels : {
					style : {
						fontSize : 1
					// 刻度字体大小
					}
				}
			};
			var ytime = new Array();
			var b = arr[0].ytime.split(",");
			for ( var d in b) {
				ytime.push(b[d]);
			}
			options.yAxis = {
				categories : ytime,
				reversedStacks : false,
				min : 0,
				title : {
					text : ''
				}
			};
			options.series = eval(arr[0].finaldata);
			new Highcharts.Chart(options);
			$("#loading").hide();
		}
	});
}


Date.prototype.Format = function(formatStr) {
	var str = formatStr;
	var Week = [ '日', '一', '二', '三', '四', '五', '六' ];

	str = str.replace(/yyyy|YYYY/, this.getFullYear());
	str = str.replace(/yy|YY/,
			(this.getYear() % 100) > 9 ? (this.getYear() % 100).toString()
					: '0' + (this.getYear() % 100));
	var month = this.getMonth() + 1;
	str = str.replace(/MM/, month > 9 ? month.toString() : '0' + month);
	str = str.replace(/M/g, month);

	str = str.replace(/w|W/g, Week[this.getDay()]);

	str = str.replace(/dd|DD/, this.getDate() > 9 ? this.getDate().toString()
			: '0' + this.getDate());
	str = str.replace(/d|D/g, this.getDate());

	str = str.replace(/hh|HH/, this.getHours() > 9 ? this.getHours().toString()
			: '0' + this.getHours());
	str = str.replace(/h|H/g, this.getHours());
	str = str.replace(/mm/, this.getMinutes() > 9 ? this.getMinutes()
			.toString() : '0' + this.getMinutes());
	str = str.replace(/m/g, this.getMinutes());

	str = str.replace(/ss|SS/, this.getSeconds() > 9 ? this.getSeconds()
			.toString() : '0' + this.getSeconds());
	str = str.replace(/s|S/g, this.getSeconds());
	return str;
}
