import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import java.awt.BorderLayout;

import javax.swing.SwingConstants;
import javax.swing.JPanel;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class hw3 {

	private JFrame frame;
	private JFrame reviewsWindow;
	private List<JCheckBox> main_category_checkBoxes;
	private List<JCheckBox> sub_category_checkBoxes;
	private List<JCheckBox> attribute_checkBoxes;
	private List<String> infos;
	private List<String> sub_cat_infos;
	private List<String> atb_infos;
	public static Connection conn;
	private ArrayList<String> sub_categories_list;
	private ArrayList<String> attributes_list;
	private ArrayList<String> businessName_list;
	private ArrayList<String> businessID_list;
	private ArrayList<String> state_list;
	private ArrayList<String> city_list;
	private ArrayList<String> stars_list;
	private ArrayList<String> reviewDates_list;
	private ArrayList<String> reviewStars_list;
	private ArrayList<String> reviewText_list;
	private ArrayList<String> reviewUserID_list;
	private ArrayList<String> reviewUsefulVotes_list;
	private ArrayList<String> checkIn_list;
	
	private JPanel panel ;
	private JPanel main_categories_panel;
	private JPanel sub_category_panel;
	private JPanel attributes_panel;
	private JPanel footer_panel;
	private JPanel review_panel;
	private JTable table;
	private JPanel sub_footer_panel1;
	private JPanel sub_footer_panel2;
	private JPanel sub_footer_panel3;
	private JPanel sub_footer_panel4;
	private JPanel sub_footer_panel5;
	private JPanel sub_footer_panel6;
	private JPanel sub_footer_panel7;
	private JPanel sub_footer_panel8;
	private JLabel DayOfWeek;
	private JLabel From;
	private JLabel To;
	private JLabel SearchFor;
	private JLabel SearchBy;
	private JLabel MyFilter;
	private JLabel checkinFilter;
	private JComboBox weekDaysTF;
	private JComboBox FromHoursTF;
	private JComboBox ToHoursTF;
	private JComboBox AttributesTF;
	private JComboBox LocationTF;
	private JTextArea FilterTF;
	private JComboBox checkInTF;
	private JButton SearchButton;
	private JButton BackButton;
	private int selectedTableRow;
	
	
	private DefaultTableModel dtm;
	private DefaultTableModel dtm1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					databaseConnection();
					hw3 window = new hw3();
					window.frame.setVisible(true);
					//closeDatabaseConnection();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws SQLException 
	 */
	public hw3() throws SQLException {
		
		initialize();
		
	}
	
	public static void databaseConnection(){
		try { 
		Class.forName("oracle.jdbc.OracleDriver");
		conn = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/orcl","system","oracle");}
		catch (Exception E) {
		System.err.println("Unable to load driver."); E.printStackTrace();
		}
	}
	
	public static void closeDatabaseConnection() throws SQLException{
		conn.close();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Yelp Tool");
		frame.setBounds(100, 100, 1250, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		JLabel lblYelp = new JLabel("Start");
		lblYelp.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblYelp, BorderLayout.NORTH);
		
		JPanel card_panel=new JPanel();
		frame.getContentPane().add(card_panel, BorderLayout.CENTER);
		card_panel.setLayout(new CardLayout(0, 0));
		card_panel.setVisible(true);

		panel = new JPanel();
		
		card_panel.add(panel);
		panel.setLayout(new GridLayout(0, 4, 0, 0));
		
		
		ArrayList<String> main_categories_list=new ArrayList<String>();
		main_categories_list.add("Active Life");
		main_categories_list.add("Arts & Entertainment");
		main_categories_list.add("Automotive");
		main_categories_list.add("Car Rental");
		main_categories_list.add("Cafes");
		main_categories_list.add("Beauty & Spas");
		main_categories_list.add("Convenience Stores");
		main_categories_list.add("Dentists");
		main_categories_list.add("Doctors");
		main_categories_list.add("Drugstores");
		main_categories_list.add("Department Stores");
		main_categories_list.add("Education");
		main_categories_list.add("Event Planning & Services");
		main_categories_list.add("Flowers & Gifts");
		main_categories_list.add("Food");
		main_categories_list.add("Health & Medical");
		main_categories_list.add("Home Services");
		main_categories_list.add("Home & Garden");
		main_categories_list.add("Hospitals");
		main_categories_list.add("Hotels & Travel");
		main_categories_list.add("Hardware Stores");
		main_categories_list.add("Grocery");
		main_categories_list.add("Medical Centers");
		main_categories_list.add("Nurseries & Gardening");
		main_categories_list.add("Nightlife");
		main_categories_list.add("Restaurants");
		main_categories_list.add("Shopping");
		main_categories_list.add("Transportation");
		
		
		main_categories_panel = new JPanel();	
		main_categories_panel.setLayout(new GridLayout(0, 1, 0, 0));
		main_categories_panel.setBounds(100, 100, 150, 250);
		main_categories_panel.setVisible(true);
		
		JScrollPane main_scrollPane = new JScrollPane(main_categories_panel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panel.add(main_scrollPane);
		
		
		sub_category_panel = new JPanel();
		panel.add(sub_category_panel);
		sub_category_panel.setLayout(new GridLayout(0, 1, 0, 0));
		main_categories_panel.setBounds(270, 270, 150, 250);
		sub_category_panel.setVisible(true);
		JScrollPane sub_scrollPane = new JScrollPane(sub_category_panel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panel.add(sub_scrollPane);
		
		attributes_panel = new JPanel();
		panel.add(attributes_panel);
		attributes_panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JScrollPane attribute_scrollPane = new JScrollPane(attributes_panel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panel.add(attribute_scrollPane);
		
		
		
		JPanel business_panel = new JPanel();
		panel.add(business_panel);
		business_panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		footer_panel= new JPanel();
		frame.getContentPane().add(footer_panel, BorderLayout.SOUTH);
		footer_panel.setLayout(new GridLayout(0, 8, 0, 0));
		footer_panel.setVisible(true);
		
		sub_footer_panel1=new JPanel();
		footer_panel.add(sub_footer_panel1);
		sub_footer_panel1.setLayout(new GridLayout(2, 0, 0, 0));
		DayOfWeek = new JLabel("Day of Week");
		DayOfWeek.setHorizontalAlignment(SwingConstants.LEADING);
		sub_footer_panel1.add(DayOfWeek);
		String[] weekDays={"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};		
	    weekDaysTF = new JComboBox(weekDays);
	    
		weekDaysTF.setBounds(271, 86, 134, 27);
		weekDaysTF.setEditable(false);
		sub_footer_panel1.add(weekDaysTF);
		
		sub_footer_panel2=new JPanel();
		footer_panel.add(sub_footer_panel2);
		sub_footer_panel2.setLayout(new GridLayout(2, 0, 0, 0));
		From = new JLabel("From");
		From.setHorizontalAlignment(SwingConstants.LEADING);
		sub_footer_panel2.add(From);
		String[] hours={"0:00","1:00","2:00","3:00","4:00","5:00","6:00","7:00","8:00","9:00","10:00","11:00","12:00","13:00","14:00",
				"15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00","23:00"};
		FromHoursTF = new JComboBox(hours);
		FromHoursTF.setSelectedIndex(-1);
		FromHoursTF.setBounds(271, 86, 134, 27);
		FromHoursTF.setEditable(false);
		sub_footer_panel2.add(FromHoursTF);
		
		sub_footer_panel3=new JPanel();
		footer_panel.add(sub_footer_panel3);
		sub_footer_panel3.setLayout(new GridLayout(2, 0, 0, 0));
		To = new JLabel("To");
		To.setHorizontalAlignment(SwingConstants.LEADING);
		sub_footer_panel3.add(To);		
		ToHoursTF = new JComboBox(hours);	
		ToHoursTF.setSelectedIndex(-1);
		ToHoursTF.setBounds(271, 86, 134, 27);
		ToHoursTF.setEditable(false);
		sub_footer_panel3.add(ToHoursTF);
		
		sub_footer_panel4=new JPanel();
		footer_panel.add(sub_footer_panel4);
		sub_footer_panel4.setLayout(new GridLayout(2, 0, 0, 0));
		SearchFor = new JLabel("Search for:");
		SearchFor.setHorizontalAlignment(SwingConstants.LEADING);
		sub_footer_panel4.add(SearchFor);
		String[] attribute_type={"All attributes","Any attributes"};
		AttributesTF = new JComboBox(attribute_type);
		AttributesTF.setBounds(271, 86, 134, 27);
		AttributesTF.setEditable(false);
		sub_footer_panel4.add(AttributesTF);
		
		
		sub_footer_panel5=new JPanel();
		footer_panel.add(sub_footer_panel5);
		sub_footer_panel5.setLayout(new GridLayout(2, 0, 0, 0));
		SearchBy = new JLabel("Search by:");
		SearchBy.setHorizontalAlignment(SwingConstants.LEADING);
		sub_footer_panel5.add(SearchBy);
		String[] search_by_type={"State","City","Zipcode"};
		LocationTF = new JComboBox(search_by_type);
		LocationTF.setBounds(271, 86, 134, 27);
		LocationTF.setEditable(false);
		sub_footer_panel5.add(LocationTF);
		
		sub_footer_panel6=new JPanel();
		footer_panel.add(sub_footer_panel6);
		sub_footer_panel6.setLayout(new GridLayout(2, 0, 0, 0));
		MyFilter =new JLabel("CA/Santa Clara/95051");
		MyFilter.setHorizontalAlignment(SwingConstants.LEADING);
		sub_footer_panel6.add(MyFilter);
		FilterTF=new JTextArea();
		FilterTF.setBounds(271, 86, 134, 27);
		FilterTF.setEditable(true);
		FilterTF.setBorder(BorderFactory.createLineBorder(Color.black));
		sub_footer_panel6.add(FilterTF);
		
		
		
				// create object of table and table model
				 table = new JTable();
				 dtm = new DefaultTableModel(0, 0);
		
				// add header of the table
				String header[] = new String[] { "Business", "State", "City",
				            "Stars"};
		
				// add header in table model     
				 dtm.setColumnIdentifiers(header);
				  
				       table.setModel(dtm);
		
				
				business_panel.add(table);
				
				JScrollPane business_scrollPane = new JScrollPane( table );
				business_panel.add( business_scrollPane, BorderLayout.CENTER );
				
				
				
				table.addMouseListener(new MouseAdapter() {
					  public void mouseClicked(MouseEvent e) {
					    if (e.getClickCount() == 1) {
					      JTable target = (JTable)e.getSource();
					      selectedTableRow= target.getSelectedRow();
					      dtm1.setRowCount(0);
					      panel.setVisible(false);
					      review_panel.setVisible(true);
					      SearchButton.setVisible(false);
					      BackButton.setVisible(true);
					      review_panel();
					      for (int count = 0; count < reviewDates_list.size(); count++) {
						        dtm1.addRow(new Object[] { reviewDates_list.get(count), reviewStars_list.get(count), reviewText_list.get(count),
						        		reviewUserID_list.get(count),reviewUsefulVotes_list.get(count)});
						 }
					      
					      
					    }
					  }
					});
				
				
				
				sub_footer_panel7=new JPanel();
				footer_panel.add(sub_footer_panel7);
				sub_footer_panel7.setLayout(new GridLayout(2, 0, 0, 0));
				checkinFilter = new JLabel("Filter(NEW):");
				checkinFilter.setHorizontalAlignment(SwingConstants.LEADING);
				sub_footer_panel7.add(checkinFilter);
				String[] string={"without check-in","with check-in"};
				checkInTF = new JComboBox(string);				
				checkInTF.setBounds(271, 86, 134, 27);
				checkInTF.setEditable(false);
				sub_footer_panel7.add(checkInTF);
				
				sub_footer_panel8=new JPanel();
				footer_panel.add(sub_footer_panel8);
				sub_footer_panel8.setLayout(new GridLayout(0, 2, 0, 0));
				SearchButton=new JButton("SEARCH");
				SearchButton.setHorizontalAlignment(SwingConstants.CENTER);
				sub_footer_panel8.add(SearchButton);
				BackButton=new JButton("BACK");
				BackButton.setHorizontalAlignment(SwingConstants.CENTER);
				BackButton.setVisible(false);
				sub_footer_panel8.add(BackButton);
				
				BackButton.addActionListener(new ActionListener() 
		        {
		        	public void actionPerformed(ActionEvent e) 
		        	{
		        		
		        		panel.setVisible(true);
		        		review_panel.setVisible(false);
		        		SearchButton.setVisible(true);
		        		BackButton.setVisible(false);
		        		
		        	}
		        });
				
				SearchButton.addActionListener(new ActionListener() 
		        {
		        	public void actionPerformed(ActionEvent e) 
		        	{
		        		dtm.setRowCount(0);
		        		
		        		fillBusinessTable();
		        		if(checkInTF.getSelectedItem()!="without check-in"){
		        			String header[] = new String[] { "Business", "State", "City",
				            "Stars","CheckIns"};
		
							// add header in table model     
							 dtm.setColumnIdentifiers(header);
							  
				       table.setModel(dtm);
		        		for (int count = 0; count < businessName_list.size(); count++) {
					        dtm.addRow(new Object[] { businessName_list.get(count), state_list.get(count), city_list.get(count),
					        		stars_list.get(count),checkIn_list.get(count)});
					 }
		        	}
		        		else{
		        			String header[] = new String[] { "Business", "State", "City",
						            "Stars"};
				
									// add header in table model     
									 dtm.setColumnIdentifiers(header);
									  
						       table.setModel(dtm);
				        		for (int count = 0; count < businessName_list.size(); count++) {
							        dtm.addRow(new Object[] { businessName_list.get(count), state_list.get(count), city_list.get(count),
							        		stars_list.get(count)});
							 }
		        		}
		        		}
		        });
		
		
				
						review_panel= new JPanel();
						card_panel.add(review_panel);
						review_panel.setLayout(new CardLayout(0, 0));
						review_panel.setBounds(200, 200, 500, 500);
						review_panel.setVisible(false);
												
						JTable review_table = new JTable();
						review_table.setVisible(true);
						 dtm1 = new DefaultTableModel(0, 5);
				
						String header1[] = new String[] { "Review Dates", "Stars", "Review Text",
						            "User ID","Useful Votes" };   
						 dtm1.setColumnIdentifiers(header1);				  
						 review_table.setModel(dtm1);				 
						 review_panel.add(review_table);
						
						JScrollPane reviews_scrollPane = new JScrollPane( review_table );
						review_panel.add( reviews_scrollPane, BorderLayout.CENTER );
		
		main_category_checkBoxes = new ArrayList<JCheckBox>();

		    for(String category:main_categories_list ) 
		    {
		        
		            JCheckBox check = new JCheckBox(category);
		            main_categories_panel.add(check);
		            check.setVerticalAlignment(SwingConstants.TOP);
		            this.main_category_checkBoxes.add(check);
		            check.addActionListener(new ActionListener() 
		            {
		            	public void actionPerformed(ActionEvent e) 
		            	{
		            		sub_category_panel.removeAll();
		        		    infos = new ArrayList<String>();
		        		    sub_categories_list=new ArrayList<String>();
		        		    for (JCheckBox checkBox : main_category_checkBoxes) {
		        		        if (checkBox.isSelected() ){
		        		            infos.add(checkBox.getText());
		        		        }
		        		    }
		        		    
		        		    main_cat_panel();
		        			
		        		    
		        			sub_category_checkBoxes = new ArrayList<JCheckBox>();
		        			for(String sub_category:sub_categories_list ) 
		        			{
		        		        
		        	            JCheckBox sub_check = new JCheckBox(sub_category);
		        	            sub_category_panel.add(sub_check);
		        	            sub_check.setVerticalAlignment(SwingConstants.TOP);
		        	            sub_category_checkBoxes.add(sub_check);		
		        	            sub_category_panel.revalidate();
		        	            sub_check.addActionListener(new ActionListener() 
		        	            {		        	            	
		        	            	public void actionPerformed(ActionEvent e) 
		        	            	{ 
		        	            		attributes_panel.removeAll();
		        	            		
		        	            		sub_cat_infos = new ArrayList<String>();        	            		
		        	        		    for (JCheckBox checkBox1 : sub_category_checkBoxes) {	        		    	
		        	        		        if (checkBox1.isSelected() ){	        	        		        	
		        	        		        	sub_cat_infos.add(checkBox1.getText());		        	        		            
		        	        		        }
		        	        		    }
		        	        		   
		        	        		    
		        	        		    sub_cat_panel();
		        	        		    attribute_checkBoxes = new ArrayList<JCheckBox>();
		    		        			for(String attribute:attributes_list ) 
		    		        			{
		    		        				JCheckBox atb_check = new JCheckBox(attribute);
		    		        	            attributes_panel.add(atb_check);
		    		        	            attributes_panel.revalidate();
		    		        	            attribute_checkBoxes.add(atb_check);
		    		        	            atb_check.setVerticalAlignment(SwingConstants.TOP);
		    		        	            atb_check.addActionListener(new ActionListener() 
		    		        	            {		        	            	
		    		        	            	public void actionPerformed(ActionEvent e) 
		    		        	            	{ 
		    		        	            			System.out.println("cb"+attribute_checkBoxes.size());	    		   
		    		        	            		  atb_infos=new ArrayList<String>();     	            		
		    		        	        		    for (JCheckBox checkBox1 : attribute_checkBoxes) {	        		    	
		    		        	        		        if (checkBox1.isSelected() ){	        	        		        	
		    		        	        		        	atb_infos.add(checkBox1.getText());		        	        		            
		    		        	        		        }
		    		        	        		    }
	     		        	        		    
		    		    		        			    		        	    
		    		        	        		}		        	          	
		    		        	            });		
		    		        	            
		    		        			}		    		        	    
		        	        		}		        	          	
		        	            });		        	           
		        			}				
		        		}
		            });	            
		    }	
	}
	
	public void main_cat_panel(){
		
		String query="Select Distinct (sub_category) From BusinessCategory BC WHERE sub_category<>'NULL' AND (";
		
		for(int i=0;i<infos.size();i++){
			if(i<infos.size()-1)
				query+="main_category = '"+infos.get(i) +"' OR ";
			else
				query+="main_category = '"+infos.get(i) +"')";
		}
		
		try {
			Statement s = conn.createStatement();
			s.execute(query);
			ResultSet res=s.getResultSet();
			if (res!=null) {
				while(res.next()){ 
					sub_categories_list.add(res.getString(1));
				}
			}
			
			s.close();
			

		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		
	}
	
	public void sub_cat_panel(){
		String query="Select DISTINCT (BA.attribute) from BusinessAttributes BA where BA.business_id IN (Select DISTINCT(BC.business_id) From BusinessCategory BC  Where ";
		for(int i=0;i<infos.size();i++){
			if(i<infos.size()-1)
				query+="main_category = '"+infos.get(i) +"' OR ";
			else
				query+="main_category = '"+infos.get(i) +"' AND (";
		}
		for(int i=0;i<sub_cat_infos.size();i++){
			if(i<sub_cat_infos.size()-1)
				query+="sub_category = '"+sub_cat_infos.get(i) +"' OR ";
			else
				query+="sub_category = '"+sub_cat_infos.get(i) +"'))";
		}
		
		
		attributes_list=new ArrayList<String>();
		try {
			Statement s = conn.createStatement();
			s.execute(query);
			ResultSet res=s.getResultSet();
			if (res!=null) {
				while(res.next()){ 
					attributes_list.add(res.getString(1));
				}
			}
			System.out.println(attributes_list);
			s.close();
			

		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
	}
	
	public void review_panel(){
		String query="Select R.review_date,R.stars,R.text,U.name,R.useful_votes from Users U,Reviews R where R.user_id=U.user_id  AND "
				+ "R.business_id='"+businessID_list.get(selectedTableRow)+"'";
		
		
		reviewDates_list=new ArrayList<String>();
		reviewStars_list=new ArrayList<String>();
		reviewText_list=new ArrayList<String>();
		reviewUserID_list=new ArrayList<String>();
		reviewUsefulVotes_list=new ArrayList<String>();
		System.out.println(query);
		try {
			Statement s = conn.createStatement();
			s.execute(query);
			ResultSet res=s.getResultSet();
			
			if (res!=null) {
				while(res.next()){ 
					if(!res.getString(1).isEmpty())
						reviewDates_list.add(res.getString(1));
					if(!res.getString(2).isEmpty())						
						reviewStars_list.add(res.getString(2));
					if(!res.getString(3).isEmpty())
						reviewText_list.add(res.getString(3));
					if(!res.getString(4).isEmpty())
						reviewUserID_list.add(res.getString(4));
					if(!res.getString(4).isEmpty())
						reviewUsefulVotes_list.add(res.getString(5));
				}
			}
			
			s.close();
			

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	
	
	public void fillBusinessTable(){
		
		
		String query="Select B.name,B.state,B.city,B.stars,B.business_id";
		
		if(checkInTF.getSelectedItem()!="without check-in"){
			query+=",X.checkinValue From Business B ,(select BCI.business_id as business_id"
				+ ",MAX(checkin) as checkinValue from BusinessCheckIn BCI,Business B where BCI.business_id=B.business_id and BCI.day"
				+ "='"+weekDaysTF.getSelectedItem()+"' group by BCI.business_id,BCI.day) X where X.business_id=B.business_id AND ";
		}
		
		else{
			query+=" From Business B where ";
		}
		if(LocationTF.getSelectedItem().equals("State")){
			
				if(FilterTF.getText().equals("AZ")){
					query+="B.state='AZ' AND ";
				}
				else if(FilterTF.getText()=="NV"){
					query+="B.state='NV' AND ";
				}
				else if(FilterTF.getText()=="WI"){
					query+="B.state='WI' AND ";
				}
				else if(FilterTF.getText()=="ON"){
					query+="B.state='ON' AND ";
				}	
				
		}
		else if(LocationTF.getSelectedItem()=="City"){
				if(FilterTF.getText()!="NULL"){
					query+="B.city='"+FilterTF.getText()+"' AND ";
				}
		}
		else if(LocationTF.getSelectedItem()=="Zipcode"){
			if(FilterTF.getText()!="NULL"){
				query+="B.zipcode='"+FilterTF.getText()+"' AND ";
			}
	}
		query+="B.business_id IN (Select BH.business_id from BusinessHours BH where ";
		
		
		query+="BH.day='"+weekDaysTF.getSelectedItem()+"' AND ";
		
		if(FromHoursTF.getSelectedItem()!=null || ToHoursTF.getSelectedItem()!=null){
		query+="(((BH.CLOSE<BH.OPEN) AND "; 
		}
		
		if(FromHoursTF.getSelectedItem()!=null){
		if(FromHoursTF.getSelectedItem()=="0:00"){			
			query+="(0 > BH.open) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="1:00"){			
			query+="(100 >BH.open) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="2:00"){			
			query+="(200 > BH.open) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="3:00"){			
			query+="(300 >BH.open) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="4:00"){			
			query+="(400 > BH.open) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="5:00"){			
			query+="(500 >BH.open) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="6:00"){			
			query+="(600 >BH.open) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="7:00"){			
			query+="(700 >BH.open) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="8:00"){			
			query+="(800 >BH.open) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="9:00"){			
			query+="(900> BH.open) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="10:00"){			
			query+="(1000 >BH.open) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="11:00"){			
			query+="(1100> BH.open) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="12:00"){			
			query+="(1200> BH.open) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="13:00"){			
			query+="(1300 >BH.open) AND";
		}
		else if(FromHoursTF.getSelectedItem()=="14:00"){			
			query+="(1400 >BH.open) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="15:00"){			
			query+="(1500> BH.open) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="16:00"){			
			query+="(1600> BH.open) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="17:00"){			
			query+="(1700> BH.open) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="18:00"){			
			query+="(1800 >BH.open) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="19:00"){			
			query+="(1900 >BH.open) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="20:00"){			
			query+="(2000> BH.open) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="21:00"){			
			query+="(2100 >BH.open) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="22:00"){			
			query+="(2200 >BH.open) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="23:00"){			
			query+="(2300 >BH.open) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="24:00"){			
			query+="(2400 >BH.open) AND ";
		}
		}
		
		
		
		if(ToHoursTF.getSelectedItem()!=null){
		if(ToHoursTF.getSelectedItem()=="0:00"){			
			query+="(0 > BH.close))";
		}
		else if(ToHoursTF.getSelectedItem()=="1:00"){			
			query+="(100 > BH.close))";
		}
		else if(ToHoursTF.getSelectedItem()=="2:00"){			
			query+="(200 > BH.close))";
		}
		else if(ToHoursTF.getSelectedItem()=="3:00"){			
			query+="(300 > BH.close))";
		}
		else if(ToHoursTF.getSelectedItem()=="4:00"){			
			query+="(400 > BH.close))";
		}
		else if(ToHoursTF.getSelectedItem()=="5:00"){			
			query+="(500 > BH.close))";
		}
		else if(ToHoursTF.getSelectedItem()=="6:00"){			
			query+="(600 > BH.close))";
		}
		else if(ToHoursTF.getSelectedItem()=="7:00"){			
			query+="(700 > BH.close))";
		}
		else if(ToHoursTF.getSelectedItem()=="8:00"){			
			query+="(800 > BH.close))";
		}
		else if(ToHoursTF.getSelectedItem()=="9:00"){			
			query+="(900 > BH.close))";
		}
		else if(ToHoursTF.getSelectedItem()=="10:00"){			
			query+="(1000 > BH.close))";
		}
		else if(ToHoursTF.getSelectedItem()=="11:00"){			
			query+="(1100 > BH.close))";
		}
		else if(ToHoursTF.getSelectedItem()=="12:00"){			
			query+="(1200 > BH.close))";
		}
		else if(ToHoursTF.getSelectedItem()=="13:00"){			
			query+="(1300 > BH.close))";
		}
		else if(ToHoursTF.getSelectedItem()=="14:00"){			
			query+="(1400 > BH.close))";
		}
		else if(ToHoursTF.getSelectedItem()=="15:00"){			
			query+="(1500 > BH.close))";
		}
		else if(ToHoursTF.getSelectedItem()=="16:00"){			
			query+="(1600 > BH.close))";
		}
		else if(ToHoursTF.getSelectedItem()=="17:00"){			
			query+="(1700 > BH.close))";
		}
		else if(ToHoursTF.getSelectedItem()=="18:00"){			
			query+="(1800 > BH.close))";
		}
		else if(ToHoursTF.getSelectedItem()=="19:00"){			
			query+="(1900 > BH.close))";
		}
		else if(ToHoursTF.getSelectedItem()=="20:00"){			
			query+="(2000 > BH.close))";
		}
		else if(ToHoursTF.getSelectedItem()=="21:00"){			
			query+="(2100 > BH.close))";
		}
		else if(ToHoursTF.getSelectedItem()=="22:00"){			
			query+="(2200 > BH.close))";
		}
		else if(ToHoursTF.getSelectedItem()=="23:00"){			
			query+="(2300 > BH.close))";
		}
		else if(ToHoursTF.getSelectedItem()=="24:00"){			
			query+="(2400 > BH.close))";
		}}
		
		if(FromHoursTF.getSelectedItem()!=null || ToHoursTF.getSelectedItem()!=null){
		query+=" OR ((BH.close>BH.open) AND ";
		}
		
		if(FromHoursTF.getSelectedItem()!=null){
		if(FromHoursTF.getSelectedItem()=="0:00"){			
			query+="(0 between BH.open and BH.close) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="1:00"){			
			query+="(100 between BH.open and BH.close) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="2:00"){			
			query+="(200 between BH.open and BH.close) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="3:00"){			
			query+="(300 between BH.open and BH.close) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="4:00"){			
			query+="(400 between BH.open and BH.close) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="5:00"){			
			query+="(500 between BH.open and BH.close) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="6:00"){			
			query+="(600 between BH.open and BH.close) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="7:00"){			
			query+="(700 between BH.open and BH.close) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="8:00"){			
			query+="(800 between BH.open and BH.close) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="9:00"){			
			query+="(900 between BH.open and BH.close) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="10:00"){			
			query+="(1000 between BH.open and BH.close) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="11:00"){			
			query+="(1100 between BH.open and BH.close) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="12:00"){			
			query+="(1200 between BH.open and BH.close) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="13:00"){			
			query+="(1300 between BH.open and BH.close) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="14:00"){			
			query+="(1400 between BH.open and BH.close) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="15:00"){			
			query+="(1500 between BH.open and BH.close) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="16:00"){			
			query+="(1600 between BH.open and BH.close) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="17:00"){			
			query+="(1700 between BH.open and BH.close) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="18:00"){			
			query+="(1800 between BH.open and BH.close) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="19:00"){			
			query+="(1900 between BH.open and BH.close) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="20:00"){			
			query+="(2000 between BH.open and BH.close) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="21:00"){			
			query+="(2100 between BH.open and BH.close) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="22:00"){			
			query+="(2200 between BH.open and BH.close) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="23:00"){			
			query+="(2300 between BH.open and BH.close) AND ";
		}
		else if(FromHoursTF.getSelectedItem()=="24:00"){			
			query+="(2400 between BH.open and BH.close) AND ";
		}
		}
		
		if(ToHoursTF.getSelectedItem()!=null){
		
		if(ToHoursTF.getSelectedItem()=="0:00"){			
			query+="(0 between BH.open and BH.close))) AND ";
		}
		else if(ToHoursTF.getSelectedItem()=="1:00"){			
			query+="(100 between BH.open and BH.close))) AND ";
		}
		else if(ToHoursTF.getSelectedItem()=="2:00"){			
			query+="(200 between BH.open and BH.close))) AND ";
		}
		else if(ToHoursTF.getSelectedItem()=="3:00"){			
			query+="(300 between BH.open and BH.close))) AND ";
		}
		else if(ToHoursTF.getSelectedItem()=="4:00"){			
			query+="(400 between BH.open and BH.close))) AND ";
		}
		else if(ToHoursTF.getSelectedItem()=="5:00"){			
			query+="(500 between BH.open and BH.close))) AND ";
		}
		else if(ToHoursTF.getSelectedItem()=="6:00"){			
			query+="(600 between BH.open and BH.close))) AND ";
		}
		else if(ToHoursTF.getSelectedItem()=="7:00"){			
			query+="(700 between BH.open and BH.close))) AND ";
		}
		else if(ToHoursTF.getSelectedItem()=="8:00"){			
			query+="(800 between BH.open and BH.close))) AND ";
		}
		else if(ToHoursTF.getSelectedItem()=="9:00"){			
			query+="(900 between BH.open and BH.close))) AND ";
		}
		else if(ToHoursTF.getSelectedItem()=="10:00"){			
			query+="(1000 between BH.open and BH.close))) AND ";
		}
		else if(ToHoursTF.getSelectedItem()=="11:00"){			
			query+="(1100 between BH.open and BH.close))) AND ";
		}
		else if(ToHoursTF.getSelectedItem()=="12:00"){			
			query+="(1200 between BH.open and BH.close))) AND ";
		}
		else if(ToHoursTF.getSelectedItem()=="13:00"){			
			query+="(1300 between BH.open and BH.close))) AND ";
		}
		else if(ToHoursTF.getSelectedItem()=="14:00"){			
			query+="(1400 between BH.open and BH.close))) AND ";
		}
		else if(ToHoursTF.getSelectedItem()=="15:00"){			
			query+="(1500 between BH.open and BH.close))) AND ";
		}
		else if(ToHoursTF.getSelectedItem()=="16:00"){			
			query+="(1600 between BH.open and BH.close))) AND ";
		}
		else if(ToHoursTF.getSelectedItem()=="17:00"){			
			query+="(1700 between BH.open and BH.close))) AND ";
		}
		else if(ToHoursTF.getSelectedItem()=="18:00"){			
			query+="(1800 between BH.open and BH.close))) AND ";
		}
		else if(ToHoursTF.getSelectedItem()=="19:00"){			
			query+="(1900 between BH.open and BH.close))) AND ";
		}
		else if(ToHoursTF.getSelectedItem()=="20:00"){			
			query+="(2000 between BH.open and BH.close))) AND ";
		}
		else if(ToHoursTF.getSelectedItem()=="21:00"){			
			query+="(2100 between BH.open and BH.close))) AND ";
		}
		else if(ToHoursTF.getSelectedItem()=="22:00"){			
			query+="(2200 between BH.open and BH.close))) AND ";
		}
		else if(ToHoursTF.getSelectedItem()=="23:00"){			
			query+="(2300 between BH.open and BH.close))) AND ";
		}
		else if(ToHoursTF.getSelectedItem()=="24:00"){			
			query+="(2400 between BH.open and BH.close))) AND ";
		}
		}
		
		System.out.println(atb_infos);
		query+="BH.business_id IN (Select BA.business_id from BusinessAttributes BA where ";
		System.out.println(atb_infos);
		if(atb_infos!=null){
		for(int k=0;k<atb_infos.size();k++){
			if(k==atb_infos.size()-1)
				query+="BA.attribute='"+atb_infos.get(k)+"' AND ";
			else
				query+="BA.attribute='"+atb_infos.get(k)+"' OR ";
		}
		}
		query+="BA.business_id IN (Select BC.business_id From BusinessCategory BC ";
		
		if(infos!=null){
			query+="Where (";
		for(int i=0;i<infos.size();i++){
			if(i<infos.size()-1)
				query+="main_category = '"+infos.get(i) +"' OR ";
			else
				query+="main_category = '"+infos.get(i) +"') AND (";
		}
		}
		if(sub_cat_infos!=null){
		for(int i=0;i<sub_cat_infos.size();i++){
			if(i<sub_cat_infos.size()-1)
				query+="sub_category = '"+sub_cat_infos.get(i) +"' OR "; 
			else
				query+="sub_category = '"+sub_cat_infos.get(i) +"')";
		}
		}
		query+="group by BC.business_id) group by BA.business_id ";
		
		if(atb_infos!=null){
		if(AttributesTF.getSelectedItem().equals("All attributes")){
			query+="having count(*)="+atb_infos.size()+" ))";
			
		}
		else if(AttributesTF.getSelectedItem().equals("Any attributes")){
			
			query+="having count(*)<="+atb_infos.size()+" ))";
		}
		}
		else
			query+="))";
		query+="order by B.name";
		
		businessName_list=new ArrayList<String>();
		businessID_list=new ArrayList<String>();
		state_list=new ArrayList<String>();
		city_list=new ArrayList<String>();
		stars_list=new ArrayList<String>();
		checkIn_list=new ArrayList<String>();
		System.out.println(query);
		try {
			Statement s = conn.createStatement();
			
			
			s.execute(query);
			System.out.println("query executed");
			ResultSet res=s.getResultSet();
			
			System.out.println(res);
			if(res==null)
				System.out.println("no result");
			if (res!=null) {
				while(res.next()){
					
						businessName_list.add(res.getString(1));										
						state_list.add(res.getString(2));					
						city_list.add(res.getString(3));					
						stars_list.add(res.getString(4));					
						businessID_list.add(res.getString(5));
						if(checkInTF.getSelectedItem()!="without check-in")
							checkIn_list.add(res.getString(6));
				}
			}
			
			s.close();
			dtm.setRowCount(0);
			

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	

}
