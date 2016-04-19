import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.JSONTokener;


 

public class populate{
	
	
	public static Connection conn;
	private static char dayValue='0';
    private static String day="Sunday";

    
    public static void main(String[] args) throws SQLException {
    	
    	final String user_filePath = args[0];
        final String business_filePath = args[1];
        final String review_filePath = args[2];
        final String checkIn_filePath = args[3];
    	
    	try { Class.forName("oracle.jdbc.OracleDriver");
    	conn = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/orcl","system","oracle");
    	System.out.println("connected");
    	}
    	catch (Exception E) {
    	System.err.println("Unable to load driver."); E.printStackTrace();
    	}
    	
    	
    	
    	System.out.println("start");
    	dropTables();
    	System.out.println("dropped");
    	createTables();
    	System.out.println("created");
    	UserDataFileParsing(user_filePath);
    	System.out.println("user created");
    	BusinessDataFileParsing(business_filePath);
    	System.out.println("Business created");
    	BusinessAttributesDataFileParsing(business_filePath);
    	System.out.println("Business attributes created");
    	BusinessHoursDataFileParsing(business_filePath);
    	System.out.println("Business Hours created");
    	BusinessCategoryDataFileParsing(business_filePath);
    	System.out.println("Business Category created");
    	ReviewDataFileParsing(review_filePath);
    	System.out.println("Reviews created");
    	CheckInDataFileParsing(checkIn_filePath);
    	System.out.println("Check-in created");
    	
    	conn.close();
    }
    
    public static void UserDataFileParsing(String user_filePath){
		
	try {

        FileReader reader = new FileReader(user_filePath);
        
        BufferedReader br = new BufferedReader(reader);
        String line;
        final int batchSize=1000;
        int count=0;
        PreparedStatement s = conn.prepareStatement("INSERT INTO Users VALUES(?,?,?,?,?,?,?)");
        while ((line = br.readLine()) != null) {
        	
        	JSONParser jsonParser = new JSONParser();
        	
            JSONObject jsonObject = (JSONObject) jsonParser.parse(line);
            
            JSONObject votes= (JSONObject) jsonObject.get("votes");

			
        String name = (String) jsonObject.get("name");
        String userID = (String) jsonObject.get("user_id");
        long review_count=(Long) jsonObject.get("review_count");
        double avg_stars=(Double) jsonObject.get("average_stars");
        long funny_votes=(Long) votes.get("funny");
        long useful_votes=(Long) votes.get("useful");
        long cool_votes=(Long) votes.get("cool");
        
         s.setString(1, name); 
	   	 s.setString(2, userID);  
	   	 s.setLong(3, review_count); 
	   	 s.setDouble(4, avg_stars); 
	   	 s.setLong(5, funny_votes); 
	   	 s.setLong(6, useful_votes); 
	   	 s.setLong(7, cool_votes);
	        
         s.addBatch();
	   	 count+=1;
       if(count%batchSize==0){
       	s.executeBatch();
       	count=0;
       }
       
       }
       s.executeBatch();
       s.close();
	}

    catch (FileNotFoundException ex) {

        ex.printStackTrace();

    } catch (IOException ex) {

        ex.printStackTrace();

    } catch (ParseException ex) {

        ex.printStackTrace();

    } catch (NullPointerException ex) {

        ex.printStackTrace();

    }
    catch (SQLException E) {
    	System.out.println("SQLException: " + E.getMessage());
    	System.out.println("SQLState: " + E.getSQLState());
    	System.out.println("VendorError:" + E.getErrorCode());
    	}

	
  }
	  

    public static void BusinessDataFileParsing(String business_filePath){
    	
    	try {

            FileReader reader = new FileReader(business_filePath);
            
            BufferedReader br = new BufferedReader(reader);
            String line;
            final int batchSize=1000;
            int count=0;
            PreparedStatement s = conn.prepareStatement("INSERT INTO Business VALUES(?,?,?,?,?,?,?)");
            while ((line = br.readLine()) != null) {
            	
            	JSONParser jsonParser = new JSONParser();
            	
                JSONObject jsonObject = (JSONObject) jsonParser.parse(line);
                
            String businessID = (String) jsonObject.get("business_id");
            String name=(String) jsonObject.get("name");
            String city=(String) jsonObject.get("city");
            String state=(String) jsonObject.get("state");
            long review_count=(Long) jsonObject.get("review_count");
            double stars=(Double) jsonObject.get("stars");
            String full_address=(String) jsonObject.get("full_address");
            Pattern p = Pattern.compile("\\d{5}$");
            Matcher m = p.matcher(full_address);
            String zipcode="NULL";
            while(m.find()) {
                zipcode=m.group();
            }
            
            s.setString(1, businessID); 
    	   	 s.setString(2, name);  
    	   	 s.setLong(3, review_count); 
    	   	 s.setString(4, city); 
    	   	 s.setString(5, state); 
    	   	 s.setDouble(6, stars);
    	   	s.setString(7, zipcode);
    	   	
    		   	s.addBatch();
    		   	 count+=1;
    	       if(count%batchSize==0){
    	       	s.executeBatch();
    	       	count=0;
    	       }
            }
            s.executeBatch();
            s.close();
            
    	}

        catch (FileNotFoundException ex) {

            ex.printStackTrace();

        } catch (IOException ex) {

            ex.printStackTrace();

        } catch (ParseException ex) {

            ex.printStackTrace();

        } catch (NullPointerException ex) {

            ex.printStackTrace();

        }
        catch (SQLException E) {
        	System.out.println("SQLException: " + E.getMessage());
        	System.out.println("SQLState: " + E.getSQLState());
        	System.out.println("VendorError:" + E.getErrorCode());
        	}

    	
    }

          
     public static void BusinessAttributesDataFileParsing(String business_filePath) throws SQLException{
    		
    		
    		try {

    	        FileReader reader = new FileReader(business_filePath);
    	        
    	        BufferedReader br = new BufferedReader(reader);
    	        String line;
    	        final int batchSize=1000;
    	        int count=0;
    	        PreparedStatement s = conn.prepareStatement("INSERT INTO BusinessAttributes VALUES(?,?)");
    	        while ((line = br.readLine()) != null) {
    	        	
    	        	JSONParser jsonParser = new JSONParser();
    	        	
    	            JSONObject jsonObject = (JSONObject) jsonParser.parse(line);
    	            
    	        String businessID = (String) jsonObject.get("business_id");
    	        s.setString(1, businessID);
    	        
    	        JSONObject attributes= new JSONObject((JSONObject) jsonObject.get("attributes"));
    	        
    	        
    	        Set<String> keys = attributes.keySet();
    	        Object[] a= keys.toArray();
    	        for(int i=0;i<a.length;i++){
    	        	Object json =attributes.get(a[i]);
    	        	if(json instanceof JSONObject){
    	        		JSONObject innerObject=new JSONObject((JSONObject) json);
    	        		Set<String> keys1 = innerObject.keySet();
    	                Object[] a1= keys1.toArray();
    	                for(int j=0;j<a1.length;j++){
    	        		//System.out.println(a1[j] + " json");
    	        		//System.out.println(innerObject.get(a1[j]));}
    	                	s.setString(2,a1[j]+"_"+innerObject.get(a1[j]));
    	                	s.addBatch();
    	       		   	 count+=1;
    	        	}}
    	        	else{
    	        		//System.out.println(a[i]);
    	        		//System.out.println(attributes.get(a[i]));
    	        		s.setString(2,a[i]+"_"+attributes.get(a[i]));
    	        		s.addBatch();
    	   		   	 count+=1;
    	        	}
    	        
    			  	
    		       if(count%batchSize==0){
    		     	s.executeBatch();
    		       	count=0;
    		       }
    	        
    	        }
    	        }
    	        s.executeBatch();
    	        s.close();
    	        }

    	    catch (FileNotFoundException ex) {

    	        ex.printStackTrace();

    	    } catch (IOException ex) {

    	        ex.printStackTrace();

    	    } catch (ParseException ex) {

    	        ex.printStackTrace();

    	    } catch (NullPointerException ex) {

    	        ex.printStackTrace();

    	    }

    		
    	}

    	 
    	 public static void BusinessHoursDataFileParsing(String business_filePath){
    			
    			
    			try {

    		        FileReader reader = new FileReader(business_filePath);
    		        
    		        BufferedReader br = new BufferedReader(reader);
    		        String line;
    		        final int batchSize=1000;
    		        int count=0;
    		        PreparedStatement s = conn.prepareStatement("INSERT INTO BusinessHours VALUES(?,?,?,?)");
    		        while ((line = br.readLine()) != null) {
    		        	
    		        	JSONParser jsonParser = new JSONParser();
    		        	
    		            JSONObject jsonObject = (JSONObject) jsonParser.parse(line);
    		            
    		        String businessID = (String) jsonObject.get("business_id");
    		        
    		        s.setString(1, businessID); 
    		        JSONObject hours= (JSONObject) jsonObject.get("hours");
    		        Set<String> keys = hours.keySet();
    		        Object[] a= keys.toArray();
    		        for(int i=0;i<a.length;i++){
    		        	Object json =hours.get(a[i]);
    		        	if(json instanceof JSONObject){
    		        		s.setString(2,(String) a[i]);
    		        		JSONObject innerObject=new JSONObject((JSONObject) json);
    		        		String close=(String) innerObject.get("close");
    		        		close=close.replace(":","");
    		        		int closeValue=Integer.parseInt(close);
    		        		String open=(String) innerObject.get("open");
    		        		open=open.replace(":","");
    		        		int openValue=Integer.parseInt(open);
    		        		s.setInt(3,openValue);
    		        		s.setInt(4,closeValue);
    		        		s.addBatch();
    			   		   	 count+=1;
    			   	       if(count%batchSize==0){
    			   	       	s.executeBatch();
    			   	       	count=0;
    		   	       }
    		        	}
    		        	else{
    		        		s.setString(2,"Null");
    		        		s.setString(3,"Null");
    		        		s.setString(4,"Null");
    		        	}
    		        	
    		        }	
    				   	
    		        }
    		        s.executeBatch();
    		        s.close();
    		        
    			}

    		    catch (FileNotFoundException ex) {

    		        ex.printStackTrace();

    		    } catch (IOException ex) {

    		        ex.printStackTrace();

    		    } catch (ParseException ex) {

    		        ex.printStackTrace();

    		    } catch (NullPointerException ex) {

    		        ex.printStackTrace();

    		    }
    		    catch (SQLException E) {
    		    	System.out.println("SQLException: " + E.getMessage());
    		    	System.out.println("SQLState: " + E.getSQLState());
    		    	System.out.println("VendorError:" + E.getErrorCode());
    		    	}

    			
    		}

    		
    		 
    		 public static void BusinessCategoryDataFileParsing(String business_filePath){
    				
    				
    				try {

    			        FileReader reader = new FileReader(business_filePath);
    			        
    			        int i;
    			        HashMap<String,Object> main_categories=new HashMap<String,Object>();
    			        main_categories.put("Active Life", true);
    			        main_categories.put("Arts & Entertainment", true);
    			        main_categories.put("Automotive", true);
    			        main_categories.put("Car Rental", true);
    			        main_categories.put("Cafes", true);
    			        main_categories.put("Beauty & Spas", true);
    			        main_categories.put("Convenience Stores", true);
    			        main_categories.put("Dentists", true);
    			        main_categories.put("Doctors", true);
    			        main_categories.put("Drugstores", true);
    			        main_categories.put("Department Stores", true);
    			        main_categories.put("Education", true);
    			        main_categories.put("Event Planning & Services", true);
    			        main_categories.put("Flowers & Gifts", true);
    			        main_categories.put("Food", true);
    			        main_categories.put("Health & Medical", true);
    			        main_categories.put("Home Services", true);
    			        main_categories.put("Home & Garden", true);
    			        main_categories.put("Hospitals", true);
    			        main_categories.put("Hotels & Travel", true);
    			        main_categories.put("Hardware Stores", true);
    			        main_categories.put("Grocery", true);
    			        main_categories.put("Medical Centers", true);
    			        main_categories.put("Nurseries & Gardening", true);
    			        main_categories.put("Nightlife", true);
    			        main_categories.put("Restaurants", true);
    			        main_categories.put("Shopping", true);
    			        main_categories.put("Transportation", true);
    			      
    			        BufferedReader br = new BufferedReader(reader);
    			        String line;
    			        
    			        final int batchSize=1000;
    			        int count=0;
    			        
    			        PreparedStatement s = conn.prepareStatement("INSERT INTO BusinessCategory VALUES(?,?,?)");
    			        while ((line = br.readLine()) != null) {
    			        	List<String> business_category=new ArrayList<String>();
    			            List<String> sub_category=new ArrayList<String>();
    			        	
    			        	JSONParser jsonParser = new JSONParser();
    			        	
    			            JSONObject jsonObject = (JSONObject) jsonParser.parse(line);
    			            
    			            String businessID = (String) jsonObject.get("business_id");
    			            s.setString(1, businessID); 
    			            JSONArray categories= (JSONArray) jsonObject.get("categories");
    			            for(i=0;i<categories.size();i++){
    			            	if(main_categories.containsKey(categories.get(i))){
    			            		business_category.add((String) categories.get(i));
    			            	}
    			            	else{
    			            		sub_category.add((String) categories.get(i));
    			            	}
    			            }
    			            
    			            
    						for(String category : business_category){
    			            	s.setString(2, category);
    			            	if(sub_category.isEmpty()){
    			            		s.setString(3, "NULL");
    			            		s.addBatch();
    			            		count+=1;
    			            	}
    			            	else{	
    			            	for(String sub_cat : sub_category){
    			            		s.setString(3, sub_cat);
    			            		s.addBatch();
    			            		count+=1;
    			            	}
    			            	
    			            	}
    			            }
    			   	 
    			      if(count%batchSize==0){
    			      	s.executeBatch();
    			      	count=0;
    			      }
    			   }
    			   s.executeBatch();
    			   s.close();
    			      
    			        }
    				

    			    catch (FileNotFoundException ex) {

    			        ex.printStackTrace();

    			    } catch (IOException ex) {

    			        ex.printStackTrace();

    			    } catch (ParseException ex) {

    			        ex.printStackTrace();

    			    } catch (NullPointerException ex) {

    			        ex.printStackTrace();

    			    }
    			    catch (SQLException E) {
    			    	System.out.println("SQLException: " + E.getMessage());
    			    	System.out.println("SQLState: " + E.getSQLState());
    			    	System.out.println("VendorError:" + E.getErrorCode());
    			    	}
    			}

    			
    			 
    			 public static void ReviewDataFileParsing(String review_filePath){
    					
    					
    					
    					try {

    				        FileReader reader = new FileReader(review_filePath);
    				        
    				        BufferedReader br = new BufferedReader(reader);
    				        String line;
    				        final int batchSize=1000;
    				        int count=0;
    				        PreparedStatement s = conn.prepareStatement("INSERT INTO Reviews VALUES(?,?,?,?,?,?,?,?,?)"); 
    				        while ((line = br.readLine()) != null) {
    				        	
    				        	JSONParser jsonParser = new JSONParser();
    				        	
    				            JSONObject jsonObject = (JSONObject) jsonParser.parse(line);
    				            
    				            JSONObject votes= (JSONObject) jsonObject.get("votes");

    							
    				        String reviewID = (String) jsonObject.get("review_id");
    				        String userID = (String) jsonObject.get("user_id");
    				        String businessID = (String) jsonObject.get("business_id");
    				        String text=(String) jsonObject.get("text");
    				        String review_date=(String) jsonObject.get("date");
    				        long stars=(Long) jsonObject.get("stars");
    				        long funny_votes=(Long) votes.get("funny");
    				        long useful_votes=(Long) votes.get("useful");
    				        long cool_votes=(Long) votes.get("cool");
    				        
    				        
    				        
    					   	 s.setString(1, reviewID); 
    					   	 s.setString(2, userID);  
    					   	 s.setString(3, review_date); 
    					   	 s.setString(4, text); 
    					   	 s.setString(5, businessID); 
    					   	 s.setLong(6, stars); 
    					   	 s.setLong(7, funny_votes);
    					   	 s.setLong(8, useful_votes);
    					   	 s.setLong(9, cool_votes); 
    					   	 s.addBatch();
    					   	 count+=1;
    				        if(count%batchSize==0){
    				        	s.executeBatch();
    				        	count=0;
    				        }
    				        
    				        }
    				        s.executeBatch();
    				        s.close();
    				        
    					}

    				    catch (FileNotFoundException ex) {

    				        ex.printStackTrace();

    				    } catch (IOException ex) {

    				        ex.printStackTrace();

    				    } catch (ParseException ex) {

    				        ex.printStackTrace();

    				    } catch (NullPointerException ex) {

    				        ex.printStackTrace();

    				    }
    				    catch (SQLException E) {
    				    	System.out.println("SQLException: " + E.getMessage());
    				    	System.out.println("SQLState: " + E.getSQLState());
    				    	System.out.println("VendorError:" + E.getErrorCode());
    				    	}

    					
    				}

    				
    				 
    				 public static void CheckInDataFileParsing(String checkIn_filePath){
    						
    						
    						try {

    					        FileReader reader = new FileReader(checkIn_filePath);
    					        
    					        BufferedReader br = new BufferedReader(reader);
    					        String line;
    					        final int batchSize=1000;
    					        int count=0;
    					        PreparedStatement s = conn.prepareStatement("INSERT INTO BusinessCheckIn VALUES(?,?,?,?)");
    					        while ((line = br.readLine()) != null) {
    					        	
    					        	JSONParser jsonParser = new JSONParser();
    					        	
    					            JSONObject jsonObject = (JSONObject) jsonParser.parse(line);
    					            
    					        String businessID = (String) jsonObject.get("business_id");
    					        
    					        s.setString(1, businessID); 
    					        
    					        JSONObject checkin_info= (JSONObject) jsonObject.get("checkin_info");
    					        Set<String> keys = checkin_info.keySet();
    					        Object[] a= keys.toArray();
    					        for(int i=0;i<a.length;i++){
    					        	long NumberOfCheckin = (long) checkin_info.get(a[i]);
    					        	
    					        	String string =(String) a[i];
    					        	
    					    		dayValue=string.charAt(string.length()-1);
    					    		
    					    		
    					            if(dayValue=='0')
    					            	day="Sunday";
    					            else if(dayValue=='1')
    					            	day="Monday";
    					            else if(dayValue=='2')
    					            	day="Tuesday";
    					            else if(dayValue=='3')
    					            	day="Wednesday";
    					            else if(dayValue=='4')
    					            	day="Thursday";
    					            else if(dayValue=='5')
    					            	day="Friday";
    					            else if(dayValue=='6')
    					            	day="Saturday";
    					            s.setString(2,day);
    					            s.setLong(3,NumberOfCheckin);
    					            s.setInt(4, i);
    					            
    					            
    					        	
    					        		s.addBatch();
    						   		   	 count+=1;
    						   	       if(count%batchSize==0){
    						   	       	s.executeBatch();
    						   	       	count=0;
    					   	       
    					        	}
    					        	
    					        }
    							   	
    					        }
    					        s.executeBatch();
    					        s.close();
    					        
    						}

    					    catch (FileNotFoundException ex) {

    					        ex.printStackTrace();

    					    } catch (IOException ex) {

    					        ex.printStackTrace();

    					    } catch (ParseException ex) {

    					        ex.printStackTrace();

    					    } catch (NullPointerException ex) {

    					        ex.printStackTrace();

    					    }
    					    catch (SQLException E) {
    					    	System.out.println("SQLException: " + E.getMessage());
    					    	System.out.println("SQLState: " + E.getSQLState());
    					    	System.out.println("VendorError:" + E.getErrorCode());
    					    	}

    						
    					}

    					 
    					 public static void dropTables() throws SQLException{
    						 Statement stm = conn.createStatement();
    						 stm.execute("drop table BusinessCheckIn");
    						 stm.execute("drop table Reviews");
    						 stm.execute("drop table BusinessCategory");
    						 stm.execute("drop table BusinessHours");
    						 stm.execute("drop table BusinessAttributes");
    						 stm.execute("drop table Business");
    						 stm.execute("drop table Users");
    						 stm.close();
    					 }
    					 public static void createTables() throws SQLException{
    						 Statement stm = conn.createStatement();
    						 stm.execute("CREATE TABLE Users(name VARCHAR(40),user_id VARCHAR(25),review_count INTEGER,avg_stars REAL,funny_votes INTEGER,"+
    							 		"useful_votes INTEGER,cool_votes INTEGER,PRIMARY KEY(user_id))");
    						 stm.execute("CREATE TABLE Business(business_id VARCHAR(25),name VARCHAR(70),review_count INTEGER,city VARCHAR(30),"+
    				    			 "state VARCHAR(5),stars REAL,zipcode VARCHAR(5),PRIMARY KEY(business_id))");
    						 stm.execute("CREATE TABLE Reviews(review_id VARCHAR(30),user_id VARCHAR(25),review_date VARCHAR(15),text CLOB,"+
    							 "business_id VARCHAR(30),stars REAL,funny_votes INTEGER,useful_votes INTEGER,cool_votes INTEGER,"+
    							 "PRIMARY KEY(review_id),FOREIGN KEY (business_id) REFERENCES Business(business_id),FOREIGN KEY (user_id) REFERENCES Users(user_id)"+
    							 ")");
    						 stm.execute("CREATE TABLE BusinessAttributes(business_id VARCHAR(25),attribute VARCHAR(30),PRIMARY KEY(business_id,attribute),"+
    			    				 	"FOREIGN KEY(business_id) REFERENCES Business(business_id))");
    						 stm.execute("CREATE TABLE BusinessHours(business_id VARCHAR(25),day VARCHAR(10),open INTEGER,close INTEGER,PRIMARY KEY(business_id,day),"+
    		    					 "FOREIGN KEY (business_id) REFERENCES Business(business_id))");
    						 stm.execute("CREATE TABLE BusinessCategory(business_id VARCHAR(25),main_category VARCHAR(30),sub_category VARCHAR(40),"+
    	    						 "PRIMARY KEY (business_id,main_category,sub_category),FOREIGN KEY (business_id) REFERENCES Business(business_id))");
    						 stm.execute("CREATE TABLE BusinessCheckIn(business_id VARCHAR(25),day VARCHAR(10),checkin INTEGER,"+
    								 "counter integer,PRIMARY KEY(business_id,day,checkin,counter),FOREIGN KEY (business_id) REFERENCES Business(business_id))");
    					 }

}
