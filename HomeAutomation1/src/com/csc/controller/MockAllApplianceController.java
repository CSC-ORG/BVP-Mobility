package com.csc.controller;

import java.util.ArrayList;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;

import android.util.Log;

import com.csc.model.Appliance;

public class MockAllApplianceController
{
  private String json;
  private ArrayList<Appliance> recipes=new ArrayList<Appliance>();
 

  public MockAllApplianceController(String json)
    {
	  this.json=json;
      new ObjectMapper();
      new JsonFactory();
    }
public void init()
{
	try{
		JsonFactory jsonfactory = new JsonFactory();
 	 
     //input file
   

     JsonParser jsonParser = jsonfactory.createJsonParser(json);
    
     // Begin the parsing procedure
     while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
    	 jsonParser.nextToken();
         String token = jsonParser.getCurrentName();
         
         System.out.println("first token : "+jsonParser.getText());
        
         if ("response".equals(token)) {
        	 jsonParser.nextToken();
             // get the next token which will  be the value...
          //System.out.println("response : "+jsonParser.getText());
         }

      
          
          if ("app_list".equals(token)) {

             // System.out.println("names :");

              //the next token will be '[' that means that we have an array
              jsonParser.nextToken();

              // parse tokens until you find  ']'
            Appliance dataModel=null;
              while (jsonParser.nextToken() != JsonToken.END_ARRAY) {

             
             
             if(jsonParser.getText().equalsIgnoreCase("HomeID"))
             {
            	 dataModel=new Appliance();
            	 jsonParser.nextToken();
            	//System.out.println("name as"+jsonParser.getText());
            	 dataModel.setID(jsonParser.getText());
            	
             }
             if(jsonParser.getText().equalsIgnoreCase("AppName"))
             {
            	 
            	 jsonParser.nextToken();
            	// System.out.println("name as"+jsonParser.getText());
            	 dataModel.setappname(jsonParser.getText());
            	 
             }
             if(jsonParser.getText().equalsIgnoreCase("AppId"))
             {
            	
            	 jsonParser.nextToken();
            	// System.out.println("name as"+jsonParser.getText());
            	 dataModel.setappid(jsonParser.getText());
            	
             }
             if(jsonParser.getText().equalsIgnoreCase("AppStatus"))
             {
            	
            	 jsonParser.nextToken();
            	 //System.out.println("name as"+jsonParser.getText());
            	 dataModel.setappstatus(jsonParser.getText());
            	 recipes.add(dataModel);
             }
            
             
             
              }
              

          }
         
         
     }
	}catch(Exception e)
	{
		
	}
}
  
  public ArrayList<Appliance> findAll()
    {
	  Log.e("recipes length find all", ""+recipes.size());
      return recipes;
    }

  public Appliance findById(int id)
    {
      return recipes.get(id);
    }
}
