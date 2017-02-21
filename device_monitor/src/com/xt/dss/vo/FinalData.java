package com.xt.dss.vo;

public class FinalData {

	private String eqpid; //设备ID
	private String eqpTime;//y轴的显示的时间
	private String stutsData;//显示的数据
	private String allData;
	public String getAllData() {
		return allData;
	}
	public void setAllData(String allData) {
		this.allData = allData;
	}
	public String getEqpid() {
		return eqpid;
	}
	public void setEqpid(String eqpid) {
		this.eqpid = eqpid;
	}
	public String getEqpTime() {
		return eqpTime;
	}
	public void setEqpTime(String eqpTime) {
		this.eqpTime = eqpTime;
	}
	public String getStutsData() {
		return stutsData;
	}
	public void setStutsData(String stutsData) {
		this.stutsData = stutsData;
	}
	
}
