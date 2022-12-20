package com.MedicalRecords.dao;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONObject;
import java.util.regex.Pattern;

import com.MedicalRecords.bean.Member;



public class Patient extends User {
	
	private String URL = "jdbc:postgresql://localhost:5432/postgres";
	private String Username = "postgres";
	private String Password = "12345";

	private static final String INSERT_USERS_SQL = "INSERT INTO users" + "  (firstname, lastname, email, password, age, gender, role) VALUES "
			+ " (?, ?, ?, ?, ?, ?, ?);";

	public Patient() {
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
		boolean b3 = Pattern.matches("^.{6,}$", member.getPassword());
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
		jo.put("result", "user added");
		}else {
			jo.put("result", "password must be 6 characters");
		}
		return jo;
	}

}
