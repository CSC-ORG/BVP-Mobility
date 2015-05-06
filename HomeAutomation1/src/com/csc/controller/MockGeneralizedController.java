package com.csc.controller;


import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;

import com.csc.model.GeneralizedModel;

public class MockGeneralizedController
{
  private String json;
  private GeneralizedModel model;

  public MockGeneralizedController(String json)
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
	    	 model= new GeneralizedModel();
	    	 jsonParser.nextToken();
		         String token = jsonParser.getCurrentName();
		         System.out.println("first token is : "+jsonParser.getCurrentName());
		 		
		         if ("response".equals(token)) {
		
		             // get the next token which will  be the value...
		          jsonParser.nextToken();
		          System.out.println("response : "+jsonParser.getText());
		
		         }
		         
			          if(jsonParser.getText().equalsIgnoreCase("true"))
			     
			          {
			        	  model.setResponse("true");
			          
			             
			          }
		         
		         else
		        	 model.setResponse("false");
		        	 jsonParser.nextToken();
		              jsonParser.nextToken();
		              model.setMessage(jsonParser.getText().toString());
		              System.out.println("message : "+jsonParser.getText());
		      		
		        	 
	         }
     
	}catch(Exception e)
	{
		
	}
}
  
  public GeneralizedModel findAll()
    {
	 // Log.e("recipes length find all", ""+recipes.size());
      return model;
    }

 
}
