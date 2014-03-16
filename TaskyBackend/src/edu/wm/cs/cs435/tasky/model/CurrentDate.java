package edu.wm.cs.cs435.tasky.model;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

/**
 * This class will return the current date and time. In addition, it will be
 * used for testing purposes
 * 
 * @author Mia
 * 
 */
public class CurrentDate
{
	private static DateTime dateTimeForDebugging = new DateTime(2014, 3, 1, 14, 25, 38);

	/**
	 * This method will allow to setup any day/time, which can be used for
	 * testing purposes
	 * 
	 * @param currentDate
	 */
	public static void setCurrentDateForDebugging(DateTime currentDate)
	{
		// System.out.println("CurrentDate.setCurrentDateForDebugging("+currentDate+")");
		dateTimeForDebugging = currentDate;
	}

	/**
	 * This method will return the current date/time which will be used for
	 * testing purposes
	 * 
	 * @return
	 */
	private static DateTime getCurrentDateForDebugging()
	{
		return dateTimeForDebugging;
	}

	public static DateTime getCurrentDate()
	{
		return getCurrentDateForDebugging();
		// return new DateTime();
	}

	/**
	 * Gets the corresponding day of the week for a given day specified as text
	 * Monday has value 1, ..., Sunday has value 7
	 * @param day
	 * @return
	 */
	public static int getDayOfWeekAsInt(String day)
	{
		switch (day.toLowerCase())
		{
			case "mon":
			case "monday":
				return DateTimeConstants.MONDAY;
				
			case "tue":
			case "tuesday":
				return DateTimeConstants.TUESDAY;
				
			case "wed":
			case "wednesday":
				return DateTimeConstants.WEDNESDAY;
				
			case "thu":
			case "thursday":
				return DateTimeConstants.THURSDAY;
				
			case "fri":
			case "friday":
				return DateTimeConstants.FRIDAY;
				
			case "sat":
			case "saturday":
				return DateTimeConstants.SATURDAY;
				
			case "sun":
			case "sunday":
				return DateTimeConstants.SUNDAY;
				
			default:
				return -1;
		}
	}
}
