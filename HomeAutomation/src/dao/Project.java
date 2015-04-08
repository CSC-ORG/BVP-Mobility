package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dto.FeedObjects;


public class Project {
	
	
	public ArrayList<FeedObjects> GetFeeds(Connection connection) throws Exception
	{
		ArrayList<FeedObjects> feedData = new ArrayList<FeedObjects>();
		try
		{
			//String uname = request.getParameter("uname");
			PreparedStatement ps = connection.prepareStatement("SELECT UserName,userIMEIno,userPassword FROM user ");
			//ps.setString(1,uname);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				FeedObjects feedObject = new FeedObjects();
				feedObject.setName(rs.getString("UserName"));
				feedObject.setImei(rs.getString("userIMEIno"));
				feedObject.setPassword(rs.getString("userPassword"));
				feedData.add(feedObject);
			}
			return feedData;
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	public boolean Login(Connection connection, String user, String pass) throws Exception
	{
		ArrayList<FeedObjects> feedData = new ArrayList<FeedObjects>();
		try
		{
			//String uname = request.getParameter("uname");
			PreparedStatement ps = connection.prepareStatement("SELECT UserName,userIMEIno,userPassword FROM user WHERE UserName ='"+user+"' and userPassword='"+pass+"'");
			
			System.out.println(ps);
			//ps.setString(1,uname);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				return true;
			
			return false;
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	
	public boolean SignUp(Connection connection, String user, String pass,String imei,String access) throws Exception
	{
		try
		{
			//String uname = request.getParameter("uname");
			
		Statement stmt = connection.createStatement();
		String sql="INSERT INTO user VALUES('"+user+"',"+imei+",'"+access+"','"+pass+"')";
			stmt.executeUpdate(sql);
			
			System.out.println(sql);
			//ps.setString(1,uname);
			
			
			return true;
		}
		catch(Exception e)
		{
			
			throw e;
			
		}
	}
	
}
