package edu.wm.cs.cs435.tasky.model;

public class IDs
{
//	private static int maxUserID;
	private static int maxProjectID;
	private static int maxTaskID;


	/**
	 * when the server gets initialized, this method needs to be called to restore the maximum value for
	 * maxProjectID
	 */
	public IDs()
	{
//		maxUserID=0;
		maxProjectID=0;
		maxTaskID=0;
	}

	public static int getNextAvailableProjectID()
	{
		maxProjectID++;
		
		return maxProjectID;
	}
	
	
	public static int getNextAvailableTaskID()
	{
		maxTaskID++;
		
		return maxTaskID;
	}

	
	/**
	 * For debugging purposes only and for testing
	 * @param maxProjectID
	 */
	public static void setMaxProjectID(int maxProjectID)
	{
		IDs.maxProjectID = maxProjectID;
	}
	
	/**
	 * For debugging purposes only and for testing
	 * @param maxTaskID
	 */
	public static void setMaxTaskID(int maxTaskID)
	{
		IDs.maxTaskID = maxTaskID;
	}


}
