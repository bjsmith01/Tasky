package edu.wm.cs.cs435.tasky.model;

import java.util.ArrayList;

/**
 * This represents a user, which has its name, password and a list of Projects (see @Project)
 * 
 * @author Fengfeng (Mia) Liu
 *
 */
public class User
{
//	private int id;
	private String email;
	private String password;
	private ArrayList<Project> listOfProjects;

	/**
	 * Create a new user and initialize an empty lists of projects
	 * @param email
	 * @param password
	 */
	public User(String email,String password)
	{
		this.setEmail(email);
		this.setPassword(password);
		
		this.listOfProjects=new ArrayList<>();
	}

	public String getPassword()
	{
		return password;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getEmail()
	{
		return email;
	}

	public ArrayList<Project> getListOfProjects()
	{
		return listOfProjects;
	}

	public void addProject(Project project1)
	{
		this.getListOfProjects().add(project1);
	}

}
