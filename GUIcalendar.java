
	import java.util.*;
	import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.*;
import java.text.SimpleDateFormat;
 import java.time.Month;
	import javax.swing.event.*;
	import javax.swing.*;
import javax.swing.border.EmptyBorder;

	
	enum MONTHS {
		January, Feburary, March, April, May, June, July, August, September, October, November, December;
	}

	enum DAYS {
		Su, Mo, Tu, We, Th, Fr, Sa;
	}
	
	
	
	
@SuppressWarnings("serial")
public class GUIcalendar extends JFrame
{
	

	public static HashMap<String, ArrayList<Events>> calendar_events = new HashMap<String, ArrayList<Events>>();

	public static ArrayList<Events> events = new ArrayList<Events>();


	static GregorianCalendar cal = new GregorianCalendar(); // capture today
	static DAYS [] arrayOfDays = DAYS.values();
	static MONTHS [] arrayOfMonths = MONTHS.values();
	JPanel stack;
	
	JTextArea todays_event = new JTextArea();
	JPanel master_panel;
	JFrame frame;
	JLabel day = new JLabel();
	
	
	final int FIELD_WIDTH;

	public GUIcalendar() 
	{
		//day.setLayout(new BorderLayout());
		
		
		FIELD_WIDTH = 20;
		frame = new JFrame();
		
	}
	
		

	

	/**
	 * Method that displays the entire month
	 */
	public void displayMonth()
	{
		master_panel = new JPanel();
		master_panel.setLayout(new BorderLayout());
		master_panel.setPreferredSize(new Dimension (800,450));
			//System.out.print(arrayOfMonths[cal.get(cal.MONTH)]);
			
			//System.out.print(" " + cal.get(cal.YEAR));
			stack = new JPanel();
			//stack.setPreferredSize(new Dimension(400,400));
			
			int som,nod;
			JPanel panel = new JPanel();
			JPanel monthPanel= new JPanel();
			
			
			JPanel day_panel = new JPanel();
			JPanel month_wrap = new JPanel();
			JLabel month_label = new JLabel();
			
			
			month_label.setText(arrayOfMonths[cal.get(Calendar.MONTH)]+ " " + cal.get(Calendar.YEAR));
			
			
			
			 nod = cal.getActualMaximum(cal.DAY_OF_MONTH);
		     som = cal.get(cal.DAY_OF_MONTH);
		    
		   
		     
			
			for (int j = 0; j < 7; j++) //days loop
			{
				day_panel.add(new JLabel ("        " + arrayOfDays[j]));	
				
			}
			
			Calendar getStart = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
	        int startDay = getStart.get(Calendar.DAY_OF_WEEK);
	        
	        for (int i = 1; i < nod + startDay; i++) 
	        {
	            if (i < startDay) 
	            {
	                final JLabel current_day = new JLabel("");
	                
	             
	                		
	                
	                monthPanel.add(current_day);
	            } 
	            else {
	                int dayNumber = i - startDay + 1;
	                
	                if (dayNumber == cal.get(Calendar.DAY_OF_MONTH))
	                {
	                	String random = String.valueOf(dayNumber);
	                 JButton current_day = new JButton("[" + dayNumber +"]"+"");
	                 current_day.addActionListener(new 
	                						ActionListener() 
	   		 	         						{ 
	                	 						
	                							public void actionPerformed(ActionEvent event) 
	                							{ 
	                								
	                								String id = event.getActionCommand();
	                								master_panel.setVisible(false);
	                								int flag = Integer.parseInt(random);
	                								cal.set(cal.DATE, flag);
	                								displayMonth();
	                								System.out.print(id);
	                							} 
	   		 	         						}); 
	                monthPanel.add(current_day);
	                }
	                else
	                {
	                	String random = String.valueOf(dayNumber);
	                JButton current_day = new JButton(random +"");
	                current_day.addActionListener(new 
    						       ActionListener() 
	         						{ 
    							  public void actionPerformed(ActionEvent event) 
    							   { 
    								  
    								  String id = event.getActionCommand();
    								  System.out.println(id);
    								 master_panel.setVisible(false);
      								int flag = Integer.parseInt(random);
      								cal.set(cal.DATE, flag);
      								displayMonth();
    								
    							} 
	         						}); 
	                monthPanel.add(current_day);
	                }
	            }
	           
	        }
			
			
			
			JButton next = new JButton(">");
			JButton previous = new JButton("<");
			JButton create = new JButton("Create");
			JButton quit = new JButton("Quit");
			
			panel.setLayout(new FlowLayout());
			
			
			
			next.addActionListener(new 
		 	         ActionListener() 
		 	         { 
		 	            public void actionPerformed(ActionEvent event) 
		 	            { 
		 	            	nextDate();
		 	            	frame.repaint();
		 	            	
		 	            	
		 	            } 
		 	         }); 
			
			previous.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent event)
						{
							previousDate();
							frame.repaint();
						}
					
				});
			
			
			create.addActionListener(new 
		 	         ActionListener() 
		 	         { 
		 	            public void actionPerformed(ActionEvent event) 
		 	            { 
		 	            	
		 	            	Create();
		 	            	
		 	            } 
		 	         }); 
			
			
			quit.addActionListener(new
							ActionListener()
							{
								public void actionPerformed(ActionEvent event)
								{
									write();
									
								}
							});
			
			
			
			    
			    monthPanel.setLayout(new GridLayout(0, 7, 5, 5));
		        monthPanel.setBorder(new EmptyBorder(0, 10, 0, 0));
		        month_wrap.add(month_label);
		        month_wrap.add(monthPanel);
		        day_panel.setLayout(new GridLayout(0,7));
		        
				
		     
		        
		        
		        JPanel button_panel = new JPanel();
		        stack.setLayout(new BoxLayout(stack, BoxLayout.Y_AXIS));
		        
		        button_panel.add(previous);
		        button_panel.add(next);
		        button_panel.add(create);
		        button_panel.add(quit);
		        
		        
		        stack.add(month_label);
		        stack.add(day_panel);
		        stack.add(month_wrap);
		        stack.add(button_panel);
		        
		        master_panel.add(stack, BorderLayout.WEST);
		        master_panel.add(drawDay(), BorderLayout.EAST);
		       
		        
		        
			  frame.add(master_panel,BorderLayout.EAST);
			  frame.setTitle("Calendar");  
			 
			  frame.setLayout(new FlowLayout());	
		      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		      frame.pack(); 
		      frame.setVisible(true); 
		}
	
	
	 /**
     *move a day by one day
     */
    public void nextDate()
    {
       cal.add(Calendar.DAY_OF_MONTH, 1);
       draw3();
       
       
       
    }
		
	
	
	
	 public void previousDate()
	    {
		 cal.add(Calendar.DAY_OF_MONTH, -1);
		 draw3();
	    }
	 
	 

	 public void draw3(){
	     String today =  arrayOfMonths[cal.get(Calendar.MONTH)]+ "/"+ String.valueOf(cal.get(Calendar.DATE))+ "/" + cal.get(Calendar.YEAR);

	     ArrayList<Events> check_event_list = Event_List(today);
	   
		 if(check_event_list == null)
		 {
			 
			 todays_event.setText("You have no events scheduled for this day");
		 }
		 else
		 {
		     todays_event.setText("");
			 for(int k = 0; k < check_event_list.size(); k++)
			 	{
				 	todays_event.append(calendar_events.get(today).get(k).toString());
				 	
			 	}
		 }
		 day.setText(today);
	 }
		
	/**
     * Populates the day panel with events that have been added to the data model
     */
    public JPanel drawDay()
    {
    	
    	JPanel dayPanel = new JPanel();
    	
        todays_event.setPreferredSize(new Dimension(250,200));
      
        dayPanel.setLayout(new BorderLayout());
        String today =  arrayOfMonths[cal.get(Calendar.MONTH)]+ "/"+ String.valueOf(cal.get(Calendar.DATE))+ "/" + cal.get(Calendar.YEAR);
        System.out.println(today);
        
        ArrayList<Events> check_event_list = Event_List(today);
		 if(check_event_list == null)
		 {
			 todays_event.setText("You have no events scheduled for this day");
		 }
		 else
		 {
			 todays_event.setText("");
			 for(int k = 0; k < check_event_list.size(); k++)
			 	{
				 	todays_event.append(calendar_events.get(today).get(k).toString());
				 	
			 	}
		 }
       
      
    	
		
       
        day.setText(today);
        
        dayPanel.add(day, BorderLayout.NORTH);
        dayPanel.add(todays_event, BorderLayout.CENTER);
        return dayPanel;
    }

			
	/**
	 * Method that reads in the date for which to view and displays it in an 
	 * entire month.
	 *

	public static void stateChanged (ChangeEvent e)
	{
			removeAll();
			displayMonth();
	}
			
		
	}
*/
    
    
  
	/**
	 * Method that returns an arraylist of events with given element
	 */
		

	public ArrayList<Events> Event_List(String list) 
	{
		
		
			ArrayList<Events> temp = calendar_events.get(list);
			if(temp == null)
			 {
				 return null;
			 }
			else 
			{
				return temp;
			}
		}
		
		
	


	public static void Create()
	{

		JTextField date;
		JTextField start;
		JTextField end;
		JTextArea title;
		JFrame frame = new JFrame();
		
		frame.setTitle("Add an event");
        JPanel container= new JPanel(new BorderLayout());
        String today = arrayOfMonths[cal.get(Calendar.MONTH)]+ "/" + cal.get(Calendar.DAY_OF_MONTH) +"/" + cal.get(Calendar.YEAR);
		
		   date = new JTextField(today + "",10);
	       start = new JTextField("Starting Time",7);
	       end = new JTextField("End time",7);
	       title = new JTextArea("Untitled Event", 10, 20);
	       JButton save= new JButton("Save");
	      
		
		save.addActionListener(new 
	 	         ActionListener() 
	 	         { 
	 	            public void actionPerformed(ActionEvent event) 
	 	            { 

	 	       		//Events must be entered by day, name of event start time and end time
	 	       		
	 	       		Events schedule = new Events(date.getText(),title.getText(),start.getText(),end.getText());
	 	       		
	 	       					add(date.getText(), schedule);
	 	            	
	 	            } 
	 	         }); 
		
		//MyCalendar.add(date, schedule);
		
		   container.add(title,BorderLayout.NORTH);
		   container.add(date,BorderLayout.WEST);
		   container.add(start, BorderLayout.CENTER);
		   container.add(end,BorderLayout.EAST);
		   container.add(save,BorderLayout.SOUTH);
		   frame.add(container);
		   frame.pack();
		   //frame.setResizable(false);
		   frame.setLocationRelativeTo(null);
		   frame.setVisible(true);

		
	}

	/**
	 * Method that takes in the key which should be date and an event object
	 * If there are no events then an event list is created and added to the map
	 * If there are events then we add the event to the mad using the date as key
	 * @param key
	 * @param e
	 */

	public static void add(String key, Events e)
	{
		
		if (calendar_events.values() == null)
		{
			ArrayList<Events> new_event = new ArrayList<Events>();
			new_event.add(e);
			calendar_events.put(key, new_event);
			
		}
		else
			
		events.add(e);
		calendar_events.put(key,events);
		
	}


	/**
	 * Method that loads in a file with existing events.txt information
	 * If there is no file then go to create to start a new event
	 */


		public void Load() {
			
			String event = null;
			String date = null;
			String s_time = null;
			String e_time = null;
						
			try {
				
				Scanner inFile = new Scanner(new File("events.txt"));
				
					
				while (inFile.hasNext())
				{
					if (inFile == null)
					{
						System.out.println("Events file is empty");
						displayMonth();
					}
					else
					{	
					inFile.useDelimiter(" ");
					
					event = inFile.next();
					
					date = inFile.next();
				
					s_time = inFile.next();
			
					e_time = inFile.next();
					
					Events setupEvent = new Events(date,event,s_time,e_time);
					add(date,setupEvent);
					}
				}
				displayMonth();
				inFile.close();
				
			}
			
			 
				catch (IOException e) {
				System.out.println("Cannot find events file");
				displayMonth();
				
				
			}
			
			//sets up the calendar view
		 
		
		}

	
		
		/**
		 * Method that prints the event on a given day. An event date is passed to access if there is a
		 * day that has an event.
		 * @param eventDate
		 */
		
		
		public void Print(String eventDate)
		{
			System.out.println("Events scheduled for: " + eventDate);
			 ArrayList<Events> check_event_list = calendar_events.get(eventDate);
			 if(check_event_list == null)
			 {
				 System.out.println("You have no events scheduled for this day");
			 }
			 else
			 {
				 for(int k = 0; k < check_event_list.size(); k++)
				 	{
					 	System.out.println(calendar_events.get(eventDate).get(k).eventName() + " "
					 			            + calendar_events.get(eventDate).get(k).getTime());
				 	}
			 }
			 System.out.println();
			 
		}
		
		
		/**
		 * Method deletes the events either with a given date
		 * or clears all the events from the calendar
		 * @value quickpick location of the event in the hash map
		 */	

		public void Delete()
		{
			Scanner quickpick = new Scanner(System.in);
			String input;
					
			System.out.println("[D]elete all Delete [S]elected");
			input = quickpick.nextLine();
			
			switch(input.toLowerCase())
			{
				case "d": calendar_events.clear();
						 displayMonth();
				
				case "s": System.out.println("Enter date to be deleted");
						   input = quickpick.nextLine();
						  calendar_events.remove(input);
						  quickpick.close();
						  displayMonth();
						  
			}
			
			
			
			
		}
		
		/**
		 * Method that creates  "events.txt" and writes all events in the
		 * hash map to file.
		 */
		
		
		public static void write()
		{
			try
			{
				PrintWriter writer = new PrintWriter(new File("events.txt"));
				
				Set<String> keys = calendar_events.keySet();
				
				
				//ArrayList<String> keys = new ArrayList<String>();
				for(String key : keys)
				{
				
				for(int i =0; i < calendar_events.get(key).size(); i++)
				{
					//ArrayList<Events> hold = calendar_events.get(keys.get(i));
					//ArrayList<Events> hold = calendar_events.get(keys.get(i));
					writer.println(calendar_events.get(key).get(i).toString());
					
				}
				}
				
				writer.close();
			}
			catch(FileNotFoundException e)
			{
				System.out.print("Error writing to file...");
			}
			System.exit(0);
		}
		

		
	

}


