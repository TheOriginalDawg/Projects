import java.awt.*;
import java.util.*;
import java.io.*;

public class Events {

	private static String event_name;
	private static String event_date;
	private static String start_time;
	private static String end_time;
	


public Events(String day, String ename, String stime,String etime)
{
	event_name = ename;
	event_date = day;
	start_time = stime;
	end_time = etime;
	
	
}



public String getTime()
{
	if (end_time == null)
	{
		end_time = " ";
	}
	return start_time + " " + end_time;
}

public String eventName()
{
	return event_name;

}

public String getDate()
{
	return event_date;
}



public String toString()
{
	return event_name + " " + event_date + " " + start_time + " "  + end_time;
}
}

