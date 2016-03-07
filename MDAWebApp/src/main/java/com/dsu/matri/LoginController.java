package com.dsu.matri;

import java.net.UnknownHostException;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;


@Controller
public class LoginController {
	@RequestMapping(value="/",method= RequestMethod.GET)
    public String profile()
    {
    	
        // implement your own registration logic here...
         
        // for testing purpose:
        System.out.println("Requested profile page");
         
        return "MainHome";
    }
	 @RequestMapping(value="/RegisteredUserPage",method = RequestMethod.GET)
	 public String login(@RequestParam("email") String email,@RequestParam("password") String password,Model model)
	    {
		  model.addAttribute("email", email);
	        System.out.println(email);
	        model.addAttribute("password",  password);
		    MongoClient m;
			try {
				m = new MongoClient("localhost",27017);
				DB db = m.getDB("NOVAK_Matrimonial");
				DBCollection collection = db.getCollection("login");
				System.out.println("Connection successful");
				//MongoOperations mongoOps = new MongoTemplate(m,"NOVAK_Matrimonial");
				DBCursor cursor = collection.find();
				 /*BasicDBObject query = new BasicDBObject(email);
				   cursor = collection.find(query);*/
			    try {
			       while(cursor.hasNext()) {
			           System.out.println(cursor.next());
			           String logindb_email=(String)cursor.one().get("email");
			           String logindb_pswd=(String)cursor.one().get("password");
			           System.out.println(logindb_email);
			           System.out.println(logindb_pswd);
			           if((email.equals(logindb_email))&&(password.equals(logindb_pswd))){
			        	   System.out.println("login success");
			           }
			           else{
			        	   System.out.println("login failure");
			           }
			       }
			    } finally {
			       cursor.close();
			    }
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	        
	        return "RegisteredUserPage";
	  	 
	    }
}
