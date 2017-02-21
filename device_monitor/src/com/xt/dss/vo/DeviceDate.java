package com.xt.dss.vo;

import java.util.Date;



public class DeviceDate {
	private String eqpid; //设备号
	private String eqptyp;//设备类型
	private String status;//状态
	private String laststatus;//状态
	private Date timeLastRecord;//开始时间
	private Date changedt; //结束时间
	private long intervaltime;//间隔时间
	public String getEqpid() {
		return eqpid;
	}
	public void setEqpid(String eqpid) {
		this.eqpid = eqpid;
	}
	public String getEqptyp() {
		return eqptyp;
	}
	public void setEqptyp(String eqptyp) {
		this.eqptyp = eqptyp;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLaststatus() {
		return laststatus;
	}
	public void setLaststatus(String laststatus) {
		this.laststatus = laststatus;
	}
	public Date getTimeLastRecord() {
		return timeLastRecord;
	}
	public void setTimeLastRecord(Date timeLastRecord) {
		this.timeLastRecord = timeLastRecord;
	}
	public Date getChangedt() {
		return changedt;
	}
	public void setChangedt(Date changedt) {
		this.changedt = changedt;
	}
	public long getIntervaltime() {
		return intervaltime;
	}
	public void setIntervaltime(long intervaltime) {
		this.intervaltime = intervaltime;
	}
	
}
