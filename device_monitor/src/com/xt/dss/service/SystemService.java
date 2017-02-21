package com.xt.dss.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import com.xt.dss.util.DBUtils;
import com.xt.dss.vo.DeviceDate;
import com.xt.dss.vo.FinalData;

public class SystemService {

	private QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
	/**
	 * @param args
	 */
	@SuppressWarnings({ "unchecked"})
	public List<DeviceDate> getDate(String eqptyp, String timeLastRecord,
			String changedt) {
		List<DeviceDate> list = new ArrayList<DeviceDate>();
		Date stime = null;
		Date etime = null;
		try {
			stime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.parse(timeLastRecord);
			etime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(changedt);
//			System.out.println("总时间："+(etime.getTime()/1000-stime.getTime()/1000));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String sql = "Select EQPID,STATUS,LASTSTATUS,TIMELASTRECORD,CHANGEDT from EQPS Where EQPTYP = '"
				+ eqptyp
				+ "' and CHANGEDT >=  to_date('"
				+ timeLastRecord
				+ "', 'yyyy-mm-dd hh24:mi:ss') and CHANGEDT <=  to_date('"
				+ changedt
				+ "', 'yyyy-mm-dd hh24:mi:ss') Order by EQPID asc,CHANGEDT";
		System.out.println(sql);
	//	System.out.println("---------------------------------------"+stime+"+++++++++++++++"+etime);
		try {
			list = (List<DeviceDate>) qr.query(sql, new ResultSetHandler() {
				@Override
				public Object handle(ResultSet rs) throws SQLException {
					List<DeviceDate> listdata = new ArrayList<DeviceDate>();
					while (rs.next()) {
						DeviceDate data = new DeviceDate();
						data.setEqpid(rs.getString("EQPID"));
						data.setStatus(rs.getString("LASTSTATUS"));
						data.setLaststatus(rs.getString("STATUS"));
						data.setTimeLastRecord(new Timestamp(rs.getTimestamp(
								"TIMELASTRECORD").getTime()));
						data.setChangedt(new Timestamp(rs.getTimestamp(
								"CHANGEDT").getTime()));
						listdata.add(data);
					}
					rs.close();
					return listdata;
				}

			});
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		System.out.println("total"+list.size());
		/**
		 * 第一个循环 就是过滤开始时间，计算经过时间，合并状态 循环一条一条（SQL结果，比如1000条） 经过时间=结束-开始
		 * 如果想要效率高，合并状态( 同一个设备，连续同种状态，把时间段合并
		 */
		
		for (int i = 0; i < list.size(); i++) {
			
//			if (i == list.size() - 1) {
//				list.get(i).setChangedt(etime);
//			}
			if (i + 1 < list.size()-1) {
				list.get(i).setChangedt(list.get(i).getChangedt());
				if (!list.get(i).getEqpid().equals(list.get(i + 1).getEqpid())) {
//					list.get(i).setChangedt(etime);
				}else{
					list.get(i).setTimeLastRecord(list.get(i).getTimeLastRecord());
				}
				if(i>0){
					if(!list.get(i).getEqpid().equals(list.get(i + 1).getEqpid())){
						list.get(i + 1).setTimeLastRecord(stime);
					}
				}
				if(i==0){
					list.get(i).setTimeLastRecord(stime);
				}
//				else{
//					list.get(i+1).setTimeLastRecord(stime);
//					list.get(i).setChangedt(list.get(i+1).getChangedt()); 
//				}
//				data=list.get(i+1);
//				data.setStatus(list.get(i).getStatus());
//				else if (!list.get(i).getStatus().equals(list.get(i + 1).getStatus())) {
//					long time=list.get(i).getChangedt().getTime()/1000-list.get(i).getTimeLastRecord().getTime()/1000;
//					if(time/60.0<4.0){
//						list.get(i+1).setStatus(list.get(i).getStatus());
//					}
//				}		
			}
//			if(i == (list.size()-1)){
//				list.get(i).setTimeLastRecord(list.get(i).getTimeLastRecord());
//				list.get(i).setChangedt(etime);
//			}
//			System.out.println("Eqpid"+i+":"+list.get(i).getEqpid());
//			System.out.println("status"+i+":"+list.get(i).getLaststatus());
//			System.out.println("TimeLastRecord"+i+":"+list.get(i).getTimeLastRecord());
//			System.out.println("Changedt"+i+":"+list.get(i).getChangedt());
		}
		List<DeviceDate> dlist = new ArrayList<DeviceDate>();
		for (int s = 0; s < list.size(); s++) {
			DeviceDate data = new DeviceDate();
			data=list.get(s);
			dlist.add(data);
//			System.out.println("Eqpid"+s+":"+dlist.get(s).getEqpid());
//			System.out.println("status"+s+":"+dlist.get(s).getStatus());
//			System.out.println("TimeLastRecord"+s+":"+dlist.get(s).getTimeLastRecord());
//			System.out.println("Changedt"+s+":"+dlist.get(s).getChangedt());
//			if (s +1 < list.size()-1) {
//				if (!list.get(s).getEqpid().equals(list.get(s + 1).getEqpid())) {				
//					data.setEqpid(list.get(s).getEqpid());
//					data.setEqptyp(list.get(s).getEqptyp());
//					data.setStatus(list.get(s).getLaststatus());
//					data.setTimeLastRecord(list.get(s).getChangedt());
//					data.setChangedt(etime);
//					dlist.add(data);
//				}
//			}
		if(s == list.size()-1||!list.get(s).getEqpid().equals(list.get(s + 1).getEqpid())){	
			DeviceDate data2 = new DeviceDate();
			data2.setEqpid(list.get(s).getEqpid());
			data2.setStatus(list.get(s).getLaststatus());
			data2.setTimeLastRecord(list.get(s).getChangedt());
			data2.setChangedt(etime);
			dlist.add(data2);
//			System.out.println("Eqpid"+s+":"+dlist.get(s).getEqpid());
//			System.out.println("status"+s+":"+dlist.get(s).getStatus());
//			System.out.println("TimeLastRecord"+s+":"+dlist.get(s).getTimeLastRecord());
//			System.out.println("Changedt"+s+":"+dlist.get(s).getChangedt());
//			System.out.println("Eqpid"+s+":"+dlist.get(s+1).getEqpid());
//			System.out.println("status"+s+":"+dlist.get(s+1).getStatus());
//			System.out.println("TimeLastRecord"+s+":"+dlist.get(s+1).getTimeLastRecord());
//			System.out.println("Changedt"+s+":"+dlist.get(s+1).getChangedt());
			}
		}
//		for (int m = 0; m < dlist.size(); m++) {
//				System.out.println("Eqpid"+m+":"+dlist.get(m).getEqpid());
//				System.out.println("status"+m+":"+dlist.get(m).getStatus());
//				System.out.println("TimeLastRecord"+m+":"+dlist.get(m).getTimeLastRecord());
//				System.out.println("Changedt"+m+":"+dlist.get(m).getChangedt());
//		}
		
		List<DeviceDate> hlist = new ArrayList<DeviceDate>();
		long itime = 0;
		Date startdate = null;
		for (int i = 0; i < dlist.size(); i++) {
			DeviceDate dDate = new DeviceDate();
			if(dlist.get(i).getChangedt().getTime() >= stime.getTime() && dlist.get(i).getTimeLastRecord().getTime()<=etime.getTime()){
				Date stattime = dlist.get(i).getTimeLastRecord();
				Date endtime = dlist.get(i).getChangedt();
				// If记录的开始时间<统计开始时间 则 开始时间= 统计开始时间；
				if (dlist.get(i).getTimeLastRecord().getTime() < stime.getTime()) {
					stattime = stime;
				}
				// If统计的结束时间>记录的结束时间 则 结束时间= 统计结束时间；
				if (dlist.get(i).getChangedt().getTime() > etime.getTime()) {
					endtime = etime;
				}
			
//				System.out.println(stattime);
//				System.out.println(endtime);
				itime = itime + (endtime.getTime() / 1000 - stattime.getTime() / 1000);
				// 合并后的数据开始时间
				if (startdate == null) {
					startdate = dlist.get(i).getTimeLastRecord();
				}
				if (i + 1 < dlist.size()) {
					//如果设备ID变化 设备未变但状态变化
					if (!dlist.get(i).getEqpid().equals(dlist.get(i + 1).getEqpid())) {
//						System.out.println(list.get(i).getEqpid()+"开始:"+list.get(i).getTimeLastRecord());
//						System.out.println(list.get(i).getEqpid()+"结束:"+list.get(i).getChangedt());
						dDate.setEqpid(dlist.get(i).getEqpid());
						dDate.setEqptyp(dlist.get(i).getEqptyp());
						dDate.setStatus(dlist.get(i).getStatus());
						dDate.setTimeLastRecord(startdate);
						dDate.setChangedt(dlist.get(i).getChangedt());
						dDate.setIntervaltime(itime);
						hlist.add(dDate);
						startdate = null;
						dDate=null;
						itime = 0;
					}else if (!dlist.get(i).getStatus().equals(dlist.get(i + 1).getStatus())) {
						dDate.setEqpid(dlist.get(i).getEqpid());
						dDate.setEqptyp(dlist.get(i).getEqptyp());
						dDate.setStatus(dlist.get(i).getStatus());
						dDate.setTimeLastRecord(startdate);
						dDate.setChangedt(dlist.get(i).getChangedt());
						dDate.setIntervaltime(itime);
						hlist.add(dDate);
						startdate = null;
						dDate=null;
						itime = 0;
					}
				}
				if (i == dlist.size() - 1) {
					dDate.setEqpid(dlist.get(i).getEqpid());
					dDate.setEqptyp(dlist.get(i).getEqptyp());
					dDate.setStatus(dlist.get(i).getStatus());
					dDate.setTimeLastRecord(startdate);
					dDate.setChangedt(dlist.get(i).getChangedt());
					dDate.setIntervaltime(itime);
					hlist.add(dDate);
					startdate = null;
					itime = 0;
					dDate = null;
				}
			}
		}
//		System.out.println("after"+hlist.size());
		return hlist;
	}

	/**
	 * 数据处理
	 * 
	 * @param data
	 * @return
	 */
	public Map<String, List<DeviceDate>> sortout(List<DeviceDate> data) {
		Map<String, List<DeviceDate>> da = new TreeMap<String, List<DeviceDate>>();
		
		List<DeviceDate> d = new ArrayList<DeviceDate>();
		for (int i = 0; i < data.size(); i++) {
			d.add(data.get(i));
			if (i + 1 < data.size()) {
				if (!data.get(i).getEqpid().equals(data.get(i + 1).getEqpid())) {
					da.put(data.get(i).getEqpid(), d);
					d = new ArrayList<DeviceDate>();
				}
			}
			if (i + 1 == data.size()) {
				da.put(data.get(i).getEqpid(), d);
			}
		}
		return da;
	}

	/**
	 * 得到最终数据
	 */

	@SuppressWarnings("deprecation")
	public FinalData datavew(Map<String, List<DeviceDate>> data,String timeLastRecord,String changedt) {
		System.out.println();
		FinalData deviceData=new FinalData();
		String dx="";
		try {
			Date stime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.parse(timeLastRecord);
			Date etime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(changedt);
			int housers=(int) ((etime.getTime()/1000/60/60)-(stime.getTime()/1000/60/60));
			int time=stime.getHours();
//			if(stime.getMinutes()>0){
//				time++;
//			}
			for(int i=0;i<=housers;i++){
				if(time>23){
					time=0;
				}
//				dx=dx+"'"+time+"点',";
				dx=dx+ time +"点,";
				time++;
			}
//			dx=dx+"'"+time+"点'";
			dx=dx+ time +"点";
			deviceData.setEqpTime(dx);
//			System.out.println("x轴："+dx);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		StringBuffer datastr = new StringBuffer(
				"{ chart: {  type: 'bar' }, title: { text: '设备状态显示图' },legend: { enabled:false }, xAxis: { categories: [");
		int max = 0;
		String dstuts="";
		for (String o : data.keySet()) {
//			dstuts=dstuts+"'"+ o + "',";
			dstuts=dstuts+ o + ",";
			if (max < data.get(o).size()) {
				max = data.get(o).size();
			}
		}
//		System.out.println("Y轴："+dstuts.substring(0, dstuts.length()-1));
		if(dstuts!=null && !"".equals(dstuts)){
			deviceData.setEqpid(dstuts.substring(0, dstuts.length()-1));
	 		datastr.append(dstuts.substring(0, dstuts.length()-1));
		}
		datastr.append("] }, yAxis: { categories: ["+dx+"], reversedStacks: false, min: 0, title: {  text: '' } },plotOptions: {  series: {  stacking: 'normal' } }, series: ");
		long[][] alldatad = new long[data.size()][max];
		String[][] allcor = new String[data.size()][max];
		int a = 0;
		for (String o : data.keySet()) {
			for (int j = 0; j < data.get(o).size(); j++) {
				alldatad[a][j] = data.get(o).get(j).getIntervaltime();
				allcor[a][j] = data.get(o).get(j).getStatus();
			}
			a++;
		}
		
		List<String> alldatab = new ArrayList<String>();
		if(alldatad!=null && alldatad.length>0){
			
			for (int i = 0; i < alldatad[0].length; i++) {
				String str = "";
				boolean b = true;
				String stuts = "";
				// 数据处理优化过的
				for (int j = 0; j < alldatad.length; j++) {
					if ("".equals(stuts)) {
						stuts = allcor[j][i];
					} else {
						if(allcor[j][i]!=null || !"".equals(allcor[j][i])){
							b = false;
							break;
						}else{
							if (!stuts.equals(allcor[j][i])) {
								b = false;
								break;
							}
						}
					}
				}
				String dcor = "";
				String dname = "";
				if (b) {
					for (int j = 0; j < alldatad.length; j++) {
						if (dcor.equals("")) {
							dname = allcor[j][i];
							if(allcor[j][i].equals("RUNNING")){
								dcor = "#00FF00";//绿色
							}else if(allcor[j][i].equals("QUAL")){
								dcor = "#FFFF37";//黄色
							}else if(allcor[j][i].equals("UALARM")){
								dcor = "#9932CC";//紫色
							}else if(allcor[j][i].equals("IDLE")){
								dcor = "#FFFFFF";// 白色
							}else if(allcor[j][i].equals("EXPPROC")){
								dcor = "#0000C6";//蓝色
							}else if(allcor[j][i].equals("NOSUPP")){
								dcor = "#FF1493";//粉红
							}else if(allcor[j][i].equals("SMMAINT")){
								dcor = "#FFA500";//橙色
							}else if(allcor[j][i].equals("UMMAINT")){
								dcor = "#FF0000";//红色
							}else if(allcor[j][i].equals("RUNCOND")){
								dcor = "#3CB370";//墨绿
							}else if(allcor[j][i].equals("UENGEVL")){
								dcor = "#00FFFF";//浅蓝
							}else if(allcor[j][i].equals("UENDATA")){
								dcor = "#7A68EF";
							}else if(allcor[j][i].equals("OFFLINE")){
								dcor = "#8B4412";//卡其
							}else if(allcor[j][i].equals("NOPROD")){
								dcor = "#FFC1CB";//浅粉
							}else if(allcor[j][i].equals("SETUP")){
								dcor = "#FF0000";//红色
							}else if(allcor[j][i].equals("EQUPGRD")){
								dcor = "#6495ED";
							}else{
								dcor="#FF0000";
							}
						}
						str = str + String.format("%.4f", alldatad[j][i]/3600.0) + ",";
					}
					str = "{name: '" + dname + "', color:'" + dcor + "', data: ["
							+ str.substring(0, str.length()-1) + "]},";
					alldatab.add(str);
				} else {
					for (int j = 0; j < alldatad.length; j++) {
						if (alldatad[j][i] != 0) {
							if(allcor[j][i].equals("RUNNING")){
								dcor = "#00FF00";//绿色
							}else if(allcor[j][i].equals("QUAL")){
								dcor = "#FFFF37";//黄色
							}else if(allcor[j][i].equals("UALARM")){
								dcor = "#9932CC";//紫色
							}else if(allcor[j][i].equals("IDLE")){
								dcor = "#FFFFFF";// 白色
							}else if(allcor[j][i].equals("EXPPROC")){
								dcor = "#0000C6";//蓝色
							}else if(allcor[j][i].equals("NOSUPP")){
								dcor = "#FF1493";//粉红
							}else if(allcor[j][i].equals("SMMAINT")){
								dcor = "#FFA500";//橙色
							}else if(allcor[j][i].equals("UMMAINT")){
								dcor = "#FF0000";//红色
							}else if(allcor[j][i].equals("RUNCOND")){
								dcor = "#3CB370";//墨绿
							}else if(allcor[j][i].equals("UENGEVL")){
								dcor = "#00FFFF";//浅蓝
							}else if(allcor[j][i].equals("UENDATA")){
								dcor = "#7A68EF";
							}else if(allcor[j][i].equals("OFFLINE")){
								dcor = "#8B4412";//卡其
							}else if(allcor[j][i].equals("NOPROD")){
								dcor = "#FFC1CB";//浅粉
							}else if(allcor[j][i].equals("SETUP")){
								dcor = "#FF0000";//红色
							}else if(allcor[j][i].equals("EQUPGRD")){
								dcor = "#6495ED";
							}else{
								dcor="#FF0000";
							}
							for (int s = 0; s < j; s++) {
								str = str + "0,";
							}
							str = str + String.format("%.4f", alldatad[j][i]/3600.0) + ",";
							for (int s = j + 1; s < alldatad.length; s++) {
								str = str + "0,";
							}
							str = "{ name: '" + allcor[j][i] + "', color:'" + dcor
									+ "', data: [" + str.substring(0, str.length()-1) + "]},";
							alldatab.add(str);
							str = "";
						}
					}
				}
			}
		}
		
		String mm="[";
		for (int dk = 0; dk < alldatab.size(); dk++) {
			if(dk==alldatab.size()-1){
				mm=mm+alldatab.get(dk).substring(0, alldatab.get(dk).length()-1);
			}else{
				mm=mm+alldatab.get(dk);
			}
		}
		mm=mm+"]";
		deviceData.setStutsData(mm);
//		System.out.println(mm);
		datastr.append(mm).append(" }");
//		System.out.println(datastr.toString());
		deviceData.setAllData(datastr.toString());
		return deviceData;
	}
}
