package model;

import java.sql.Connection;
import java.util.ArrayList;

import javax.ws.rs.PathParam;

import dao.Database;

import dao.Project;
import dto.FeedObjects;

public class ProjectManager {
	
	
	public ArrayList<FeedObjects> GetFeeds()throws Exception {
		ArrayList<FeedObjects> feeds = null;
		try {
			    Database database= new Database();
			    Connection connection = database.Get_Connection();
				Project project= new Project();
				feeds=project.GetFeeds(connection);
		
		} catch (Exception e) {
			throw e;
		}
		return feeds;
	}

	public boolean Login(String user ,String pass)throws Exception {
		boolean result;
		try {
			    Database database= new Database();
			    Connection connection = database.Get_Connection();
				Project project= new Project();
				
				result=project.Login(connection,user,pass);
		
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	public boolean SignUp(String user ,String pass,String imei,String access)throws Exception {
		boolean result;
		try {
			    Database database= new Database();
			    Connection connection = database.Get_Connection();
				Project project= new Project();
				
				result=project.SignUp(connection,user,pass,imei,access);
		
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

}
