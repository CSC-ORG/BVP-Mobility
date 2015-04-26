package com.example.homeautomation;

public class Appliance {

	/**
	 * @param args
	 */
	private String HomeID;
	private String AppName;
	private String AppId;
	private String LastModTime;
	private String AppStatus;

    public Appliance(){}
    
    public Appliance(String HomeID,String AppName,String AppId,String LastModTime,String AppStatus)
	{
		super();
		this.HomeID=HomeID;
		this.AppName=AppName;
		this.AppId=AppId;
		this.LastModTime=LastModTime;
		this.AppStatus=AppStatus;
		
	}

	public String getID() {
		return HomeID;
	}
	
	public void setID(String HomeID) {
		this.HomeID = HomeID;
	}
	
	public String getappname() {
		return AppName;
	}
	
	public void setappname(String AppName) {
		this.AppName = AppName;
	}

	public String getappid() {
		return AppId;
	}
    
	public void setappid(String AppId) {
		this.AppId = AppId;
	}
	
	public String getLMT() {
		return LastModTime;
	}
	
	public void setLMT(String LastModTime) {
		this.LastModTime = LastModTime;
	}
	
	public String getappstatus() {
		return AppStatus;
	}
	public void setappstatus(String AppStatus) {
		this.AppStatus = AppStatus;
	}
}
