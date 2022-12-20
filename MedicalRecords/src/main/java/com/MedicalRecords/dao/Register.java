package com.MedicalRecords.dao;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import org.json.JSONObject;

import com.MedicalRecords.bean.Member; 

public class Register  extends User {
	
	private String URL = "jdbc:postgresql://localhost:5432/postgres";
	private String Username = "postgres";
	private String Password = "12345";

	private static final String INSERT_USERS_SQL = "INSERT INTO users" + "  (firstname, lastname, email, password, age, gender, role) VALUES "
			+ " (?, ?, ?, ?, ?, ?, ?);";
	private static final String Login = "select * from users where email = ? and password =?";

	public Register() {
	}

	public Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(URL, Username, Password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	
	
	public JSONObject signup(Member member) throws SQLException {
		Connection con = getConnection();
		JSONObject jo = new JSONObject();
		boolean b3 = Pattern.matches("^.{10,}$", member.getPassword());
		if (b3 == true) {
			PreparedStatement ps = con.prepareStatement(INSERT_USERS_SQL);
			ps.setString(1, member.getFirst_name());
			ps.setString(2, member.getLast_name());
			ps.setString(3, member.getEmail());
			ps.setString(4, member.getPassword());
			ps.setInt(5, member.getAge());
			ps.setString(6, member.getGender());
			ps.setString(7, member.getRole());
			System.out.println(ps);
			ps.executeUpdate();
		    con.close();
		    jo.put("result", "User added");
		}else {
			jo.put("result", "password must be 10 characters");
		}
		return jo;
	}
	
	public JSONObject login(String email,String password) throws SQLException{
		Connection con = getConnection();
		String role = null;
		String email1 = null;
		String password1 = null;
		JSONObject jo = new JSONObject();
		JSONObject ja = new JSONObject();
		
		PreparedStatement ps;
		ps = con.prepareStatement(Login);
		ps.setString(1,email);
		ps.setString(2,password);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {;
		email1 = rs.getString("email");
		role = rs.getString("role");
		password1 = rs.getString("password");
		jo.put("User", "Logged in");
		jo.put("logged", role);
		}
		con.close();
		if(email.equals(email1) && password.equals(password1)) {
			System.out.println(email1);
			System.out.println(password1);
			return jo ;
		}else {
			return ja.put("User", "Not logged in");
		}
	}
	
//	private void printSQLException(SQLException ex) {
//		for (Throwable e : ex) {
//			if (e instanceof SQLException) {
//				e.printStackTrace(System.err);
//				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
//				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
//				System.err.println("Message: " + e.getMessage());
//				Throwable t = ex.getCause();
//				while (t != null) {
//					System.out.println("Cause: " + t);
//					t = t.getCause();
//				}
//			}
//		}
//	}

}
