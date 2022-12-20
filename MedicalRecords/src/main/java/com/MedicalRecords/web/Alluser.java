package com.MedicalRecords.web;


import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.MedicalRecords.bean.Member;
import com.MedicalRecords.dao.Register;

/**
 * Servlet implementation class Alluser
 */
@WebServlet("/Alluser")
public class Alluser extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Alluser() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Register register = new Register();
		Connection con = register.getConnection();
		PrintWriter out = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		JSONArray array = new JSONArray();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
		
		PreparedStatement ps;
		try {
		ps = con.prepareStatement("select * from users");
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
		JSONObject jo = new JSONObject();
		String first_name = rs.getString("firstname");
		String last_name = rs.getString("lastname");
		String email = rs.getString("email");
		String password = rs.getString("password");
		int age = rs.getInt("age");
		String gender = rs.getString("gender");
		String role = rs.getString("role");
		Member member = new Member(first_name,last_name,email,password,age,gender,role);
		jo.put("firstname", member.getFirst_name());
		jo.put("lastname",  member.getLast_name());
		jo.put("email", member.getEmail());
		jo.put("password", member.getPassword());
		jo.put("age", member.getAge());
		jo.put("gender", member.getGender());
		jo.put("role", member.getRole());
		array.put(jo);
		}
		jsonObject.put("users", array);
		out.print(jsonObject);
		con.close();
	} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
