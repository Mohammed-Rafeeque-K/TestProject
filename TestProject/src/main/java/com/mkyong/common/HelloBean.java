package com.mkyong.common;

	import javax.faces.bean.ManagedBean;
	import javax.faces.bean.SessionScoped;
	import javax.faces.event.ActionEvent;

	import java.io.Serializable;
	import java.security.MessageDigest;
	import java.security.NoSuchAlgorithmException;
	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.Statement;
	import java.sql.Timestamp;
	import java.util.ArrayList;
	import java.util.Calendar;


	@ManagedBean(name="helloBean")
	@SessionScoped
	public class HelloBean implements Serializable {

		private static final long serialVersionUID = 1L;
		public String action;
		private String contactid;
		private String name;
		private String phone;
		private String age;
		private String email;
		private String address;
		private String confirmpass;
		private String countrycode;
		private String gender;
		private String country;
		private String username;
		private String pass;
		private String dbUname;
		private String dbPass;
		private String encryptedCode;


		public String getEncryptedCode() {
			return encryptedCode;
		}
		
		

		public String getAction() {
			return action;
		}



		public void setAction(String action) {
			this.action = action;
		}



		public void setEncryptedCode(String encryptedCode) {
			this.encryptedCode = encryptedCode;
		}

		public String generatedPassword;
		
		public String getContactid() {
			return contactid;
		}

		public void setContactid(String contactid) {
			this.contactid = contactid;
		}
		
		public String getGeneratedPassword() {
			return generatedPassword;
		}

		public void setGeneratedPassword(String generatedPassword) {
			this.generatedPassword = generatedPassword;
		}

		
		Connection connection;

		public String getDbUname() {
			return dbUname;
		}

		public void setDbUname(String dbUname) {
			this.dbUname = dbUname;
		}

		public String getDbPass() {
			return dbPass;
		}

		public void setDbPass(String dbPass) {
			this.dbPass = dbPass;
		}

		ArrayList<HelloBean> userList;
		ArrayList<HelloBean> loginList;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPass() {
			return pass;
		}

		public void setPass(String pass) {
			this.pass = pass;
		}

		public ArrayList<HelloBean> getLoginList() {
			return loginList;
		}

		public void setLoginList(ArrayList<HelloBean> loginList) {
			this.loginList = loginList;
		}

		public void setUserList(ArrayList<HelloBean> userList) {
			this.userList = userList;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getAge() {
			return age;
		}

		public void setAge(String age) {
			this.age = age;
		}


		public String getConfirmpass() {
			return confirmpass;
		}

		public void setConfirmpass(String confirmpass) {
			this.confirmpass = confirmpass;
		}

		public String getCountrycode() {
			return countrycode;
		}

		public void setCountrycode(String countrycode) {
			this.countrycode = countrycode;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		// Establish connection
		public Connection getConnection() {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/sampledb", "root",
						"Password@123");
			}

			catch (Exception e) {
				System.out.println(e);
			}
			return connection;
		}
		
		public void GenerateHash(String val){
			 try {
		            MessageDigest md = MessageDigest.getInstance("MD5");
		           
		            md.update(val.getBytes());
		            
		            byte[] bytes = md.digest();
		           
		            StringBuilder sb = new StringBuilder();
		            for(int i=0; i< bytes.length ;i++)
		            {
		                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		            }
		           
		            generatedPassword = sb.toString();
		        }
		        catch (NoSuchAlgorithmException e)
		        {
		            e.printStackTrace();
		        }
		}
		
		public String addUser() {
			System.out.println("executing this page");
			
				GenerateHash(pass);
		        System.out.println(generatedPassword);
		    
			int result = 0;
			try {
				connection = getConnection();
				System.out.println(connection);
				java.sql.PreparedStatement ps = connection.prepareStatement("insert into contactform(name,ccode,phone,age,gender,address,email,country,userid,password) values(?,?,?,?,?,?,?,?,?,?)");
				ps.setString(1, name);
				ps.setString(2, countrycode);
				ps.setString(3, phone);
				ps.setString(4, age);
				ps.setString(5, gender);
				ps.setString(6, address);
				ps.setString(7, email);
				ps.setString(8, country);
				ps.setString(9, username);
				ps.setString(10, generatedPassword);
				result = ps.executeUpdate();
				connection.close();

			} catch (Exception e) {
				System.out.println(e);
			}
			if (result != 0)
				return "Login.xhtml?faces-redirect = true";
			else
				return "Registration.xhtml";
			}
			
		

		public ArrayList<HelloBean> getUserList() {
			try {
				userList = new ArrayList<HelloBean>();
				connection = getConnection();

				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("select * from contactform");
				while (rs.next()) {
					HelloBean user = new HelloBean();
					user.setName(rs.getString("name"));
					user.setCountrycode(rs.getString("ccode"));
					user.setPhone(rs.getString("phone"));
					user.setAge(rs.getString("age"));
					user.setGender(rs.getString("gender"));
					user.setAddress(rs.getString("address"));
					user.setEmail(rs.getString("email"));
					user.setCountry(rs.getString("country"));
					userList.add(user);
				}
				connection.close();
			} catch (Exception e) {
				System.out.print(e);
			}
			return userList;
		}

		public String getLogin() {
			getDb(username);
			GenerateHash(pass);
	        System.out.println(generatedPassword);
	        
			if (username.equals(dbUname) && generatedPassword.equals(dbPass)) {
				return "signin";
			} else
				return "Login";

		}

		public void getDb(String uname) {

			if (uname != null && pass != null) {
				PreparedStatement ps = null;
				ResultSet rs = null;
				try {
					connection = getConnection();
					if (connection != null) {
						String sql = "select userid,password from contactform where userid  = '"
								+ uname + "'";
						ps = connection.prepareStatement(sql);
						rs = ps.executeQuery();
						while (rs.next()) {
							dbUname = rs.getString("userid");
							dbPass = rs.getString("password");
						}
					}
				} catch (Exception e) {
					System.out.print(e);
				}

			}

		}
		

		public String sendMail() {
			
			try {
				connection = getConnection();
				if (username == null) {
					return "Login.xhtml?faces-redirect = true";
				} else{
				
				String pass = "your Login credentials: Username:" + dbUname +" Password: " +dbPass ;
				EmailUtil.send("anuupriya05@gmail.com", "", "maneshshaju@gmail.com", "Reset Password",
						pass);
				return "Login.xhtml?faces-redirect = true";

			}
			}
			catch (Exception e) {
				System.out.println(e);
			}
			return "Login.xhtml?faces-redirect = true";
		}

		public String dataNull() {
			return "signin.xhtml?faces-redirect = true";
		}

		public void getDbData(String uname) {
			connection = getConnection();
			try{
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("select email,contactid from contactform where userid  = '"
						+ uname + "'");
				while (rs.next()) {
				email =	rs.getString("email");
				contactid =	rs.getString("contactid");	
				}
				}
				catch(Exception e){
					System.out.println(e);
				}

		}
		 
		
		public void setDataToResetDb(){
			connection = getConnection();
			getDbData(username);
			java.sql.Timestamp  intime = new java.sql.Timestamp(new java.util.Date().getTime());
		    Calendar cal = Calendar.getInstance();
		    cal.setTimeInMillis(intime.getTime());
		    cal.add(Calendar.MINUTE, 20);
		    java.sql.Timestamp  exptime = new Timestamp(cal.getTime().getTime());

		    int rand_num = (int) (Math.random() * 1000000);
		    String rand = Integer.toString(rand_num);
		    String finale =(rand+""+intime); 
		    GenerateHash(finale); 
			try{
				Statement stmt = connection.createStatement();
			    String save_hash = "insert into tbl_resetpass(email,intime,expirytime,token,contactid) values('"+email+"','"+intime+"','"+exptime+"','"+generatedPassword+"',"+contactid+")";
			    int saved = stmt.executeUpdate(save_hash);
			   
			    System.out.print(saved);
			    if(saved>0)
			    {
			  String link = "http://localhost:8080/JavaServerFaces/Recover.xhtml"; 
			  String messageText = " Click <a href="+link+"?key="+generatedPassword+">Here</a>"
			  		+ " To Reset your Password."+"\n"+" You must reset your password within 20  minutes.";
			  
				EmailUtil.send("anuupriya05@gmail.com", "Dadmomappu@", email, "Reset Password",
						messageText);
		}
			}
			catch(Exception e){
				System.out.print(e);
			}
		}
		
		
		public String Reset_password(){
			setDataToResetDb();
				
				return "Login.xhtml?faces-redirect = true";
			    
			    }
		
		  public void attrListener(ActionEvent event){
			  String action;
			  action = (String)event.getComponent().getAttributes().get("action");
			  System.out.println(action);
			 
			  }
			  
		
		public String changePassword(){
			java.sql.Timestamp  curtime = new java.sql.Timestamp(new 
					java.util.Date().getTime());

					int profile_id = 0;
					java.sql.Timestamp exptime;
				

					try{
			connection = getConnection();
			Statement stmt = connection.createStatement();
			System.out.println(encryptedCode);
			
			String sql = "select contactid, expirytime from tbl_resetpass where token ='"+encryptedCode+"'";
					 ResultSet rs = stmt.executeQuery(sql);
					 if(rs.first()){
					 profile_id = rs.getInt("contactid");  
					 exptime = rs.getTimestamp("expirytime");
					 GenerateHash(pass);
					 if((curtime).before(exptime)){ 
					 String query = "update contactform set password = '"+generatedPassword+"' where contactid='"+profile_id+"'";
						stmt.executeUpdate(query);
						System.out.println("Success");
					 }else{
						 
						 System.out.println("Error changing");
						 return "RecoveryEmail.xhtml";
					 }
					}
					}
					 catch(Exception e){
						 System.out.println(e);
					 }	
			
			return "Login.xhtml";
			
		}
		
		public ArrayList<HelloBean> getList() {
			try{
			userList = new ArrayList<HelloBean>();
			connection = getConnection();
			
			 Statement stmt = connection.createStatement();
			 ResultSet rs = stmt.executeQuery("select * from contactform");
			 while(rs.next()){
				 HelloBean user = new HelloBean();
				 user.setName(rs.getString("name"));
				 user.setCountrycode(rs.getString("ccode"));
				 user.setPhone(rs.getString("phone"));
				 user.setAge(rs.getString("age"));
				 user.setGender(rs.getString("gender"));
				 user.setAddress(rs.getString("address"));
			
				 user.setEmail(rs.getString("email"));
				 user.setCountry(rs.getString("country"));
				 userList.add(user);
			 }
			 connection.close();
			}
			catch(Exception e){
				System.out.print(e);
			}
			return userList;
		}
		
		
		
					}

