package com.csc.model;

public class Appliance {

	/**
	 * @param args
	 */
	private String HomeID;
	private String AppName;
	private String AppId;
	private String AppStatus;

    public Appliance(){}
    
    public Appliance(String HomeID,String AppName,String AppId,String AppStatus)
	{
		super();
		this.HomeID=HomeID;
		this.AppName=AppName;
		this.AppId=AppId;
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
	
	
	
	public String getappstatus() {
		return AppStatus;
	}
	public void setappstatus(String AppStatus) {
		this.AppStatus = AppStatus;
	}
}
