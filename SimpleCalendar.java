import java.awt.*;
import javax.swing.*;
import java.util.*;
/**
*A simple GUI calendar with events. 

*This represents and manipulates a calendar (in terms of day, month, year, time, 
*etc), using the GregorianCalendar  class from the library that provides a standard calendar system. 
*The initial screen presents  the current month view and the current day view. 
*Each day on the month view is clickable. When a user clicks on a day on the month view, the day view (at 
*the right side) is changed to represent the day the user clicked on. Events of that day
*are listed in the day view. The calendar also has previous and next buttons which traverse
*the calendar by day. There is also a create button which creates events on a user defined day
*and events which are written to an events file. The quit button writes all the events in the calendar
*to and events file and is loaded when the progam is run.


@author Corey Edwards
@version 1.3 for Assignment 4, CS 151, SJSU
*/


public class SimpleCalendar {
	
	
	
public static void main(String [] args)
{
	
	GUIcalendar myCalendar = new GUIcalendar();
	
	myCalendar.Load();

	  
	  
}

}
