package com.xt.dss.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xt.dss.service.SystemService;
import com.xt.dss.vo.DeviceDate;
import com.xt.dss.vo.FinalData;

/**
 * Servlet implementation class IndexController
 */
@WebServlet(description = "显示首页", urlPatterns = { "/IndexController" })
public class IndexController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	SystemService service=new SystemService();
    public IndexController() {
        super();
    }
    static int a=0;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		long bg = System.currentTimeMillis();
		FinalData deviceData=new FinalData();
//		System.out.println("设备类型"+request.getParameter("eqptyp"));
//		System.out.println("开始时间"+request.getParameter("stime"));
//		System.out.println("结束时间"+request.getParameter("etime"));
		deviceData=getPeriod(request.getParameter("eqptyp"), request.getParameter("stime"), request.getParameter("etime"));
		String json=toJson(deviceData);
		response.setContentType("text/json;charset=UTF-8");//解决乱码问题,没有这句，回调函数的内容可能乱码 
//		long ed = System.currentTimeMillis();
//		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>历时:"+((ed - bg))+"毫秒");
		response.getWriter().print(json);
	}
	private String toJson(FinalData deviceData) {
		StringBuilder db = new StringBuilder("[{");
		db.append("\"eqpid\"").append(":").append("\"").append(deviceData.getEqpid()).append("\"");
		db.append(",");
		db.append("\"ytime\"").append(":").append("\"").append(deviceData.getEqpTime()).append("\"");
		db.append(",");
		db.append("\"finaldata\"").append(":").append("\"").append(deviceData.getStutsData()).append("\"");
		db.append(",");
		db.append("\"alldata\"").append(":").append("\"").append(deviceData.getAllData()).append("\"");
		db.append("}]");
		return db.toString();
	}

	/*
	 * 选择时间段
	 */
	public FinalData getPeriod(String eqptyp,String stime,String dtime){
		List<DeviceDate> list =service.getDate(eqptyp, stime,dtime);
		FinalData deviceData=service.datavew(service.sortout(list),stime,dtime);
		return deviceData;
	}
//	/**
//	 * 得到间隔时间的下一个时间
//	 * @return
//	 */
//	public FinalData time(String eqptyp,int h){
//		//返回以毫秒为单位的当前时间
//		long currentTime = System.currentTimeMillis();
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		long m=h*60*60*1000;
//		Date date = new Date(currentTime-m);
//		String stime=formatter.format(date);
//		String etime=formatter.format(new Date());
//		stime=stime.substring(0, 13)+":00:00";
//		System.out.println(stime);
//		System.out.println(etime);
//		stime="2015-04-22 10:00:00";
//		if(a>10){
//			if(a>20){
//				a=0;
//			}
//			stime="2015-04-22 "+a+":00:00";
//		}
//		a=a+1;
//		etime="2015-04-22 22:21:2";
//		List<DeviceDate> list =service.getDate(eqptyp, stime,etime);
//		FinalData deviceData=service.datavew(service.sortout(list),stime,etime);
//		return deviceData;
//	}
}
