package edu.wm.cs.cs435.tasky.server;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class TasksIndexServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException { 
		Query query = new Query("Task").addSort("taskID");
		
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		// Use PreparedQuery interface to retrieve results
		PreparedQuery preparedQuery = datastore.prepare(query);
		
		
		JSONArray tasks = new JSONArray(); 
		JSONObject json = new JSONObject(); 
		JSONObject task = new JSONObject(); 
		
		try { 
			for (Entity result : preparedQuery.asIterable()) {
				//Integer taskIDRetrieved = (Integer) result.getProperty("taskID");
				int taskIDRetrieved = ((Long) result.getProperty("taskID")).intValue();
				String taskDescriptionRetrieved = (String) result.getProperty("taskDescription");
				Date dueDateRetrieved = (Date) result.getProperty("dueDate");
				
				task.put("taskID", taskIDRetrieved); 
				task.put("taskDesc", taskDescriptionRetrieved); 
				task.put("taskDue", dueDateRetrieved); 
				
				tasks.put(task); 
				
			}
			json.put("tasks", tasks); 
			
		} catch (JSONException e) {}
		response.setContentType("application/json");
		response.getWriter().write(json.toString()); 
		
		
	}
}
