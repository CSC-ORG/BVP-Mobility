package webService;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import model.ProjectManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dto.FeedObjects;

@Path("/WebService")
public class FeedService {
	
	@GET
	@Path("/GetFeeds")
	@Produces("application/json")
	public String feed()
	{
		String feeds  = null;
		return "hello2";
		/*try 
		{
			ArrayList<FeedObjects> feedData = null;
			ProjectManager projectManager= new ProjectManager();
			feedData = projectManager.GetFeeds();
			//StringBuffer sb = new StringBuffer();
			Gson gson = new Gson();
			System.out.println(gson.toJson(feedData));
			feeds = gson.toJson(feedData);

		} catch (Exception e)
		{
			System.out.println("error");
		}
		return feeds;*/
	}
	@GET
	@Path("/GetLogin")
	@Produces("application/json")
	public String login(@QueryParam("name") String UserName,@QueryParam("pass") String pass)
	{
		String user=UserName;
		
		String feeds  = null;
		try 
		{
			
			ProjectManager projectManager= new ProjectManager();
			JsonObject json=new JsonObject();
			
			
			boolean result = projectManager.Login(user,pass);
			
			json.addProperty("response", result);
			StringBuffer sb = new StringBuffer();
			Gson gson=new Gson();
			feeds=gson.toJson(json);

		} catch (Exception e)
		{
			System.out.println("error"+e);
		}
		//return user;
		return feeds;
	}
	@GET
	@Path("/SignUp")
	@Produces("application/json")
	public String signup(@QueryParam("name") String UserName,@QueryParam("pass") String pass,@QueryParam("imei") String userIMEIno,@QueryParam("access") String access)
	{
		System.out.println("Feed Service received  "+UserName+pass+userIMEIno+access);
		String user=UserName;
		
		String feeds  = null;
		try 
		{
			
			ProjectManager projectManager= new ProjectManager();
			JsonObject json=new JsonObject();
			
			
			boolean result = projectManager.SignUp(user,pass,userIMEIno,access);
			
			json.addProperty("response", result);
			StringBuffer sb = new StringBuffer();
			Gson gson=new Gson();
			feeds=gson.toJson(json);

		} catch (Exception e)
		{
			JsonObject json=new JsonObject();
			
			
				json.addProperty("response", "false");
				Gson gson=new Gson();
				feeds=gson.toJson(json);
			System.out.println("error"+e);
		}
		//return user;
		return feeds;
	}

}
