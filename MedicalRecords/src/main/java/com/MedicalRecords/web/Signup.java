package com.MedicalRecords.web;

import jakarta.servlet.ServletException;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONObject;

import com.MedicalRecords.bean.Member;
import com.MedicalRecords.dao.Patient;
import com.MedicalRecords.dao.Pharmacist;
import com.MedicalRecords.dao.Physician;
import com.MedicalRecords.dao.Register;
import com.MedicalRecords.bean.Member;


/**
 * Servlet implementation class Signup
 */
@WebServlet("/Signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Member Member;

    /**
     * Default constructor. 
     */
    public Signup() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// TODO Auto-generated method stub
		String first_name = request.getParameter("first_name");
		String last_name = request.getParameter("last_name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		int age = Integer.parseInt(request.getParameter("age"));
		String role = request.getParameter("role");
		PrintWriter out = response.getWriter();
		JSONObject jo = new JSONObject();
		JSONObject result = null;
		try {
			if(role.equals("admin")) {
				Register register = new Register();
				Member member = new Member(first_name,last_name,email,password,age,gender,role);
				result =register.signup(member);
			}else if(role.equals("patient")){
				Patient patient = new Patient();
				Member member = new Member(first_name,last_name,email,password,age,gender,role);
				result =patient.signup(member);
			}else if(role.equals("physician")) {
				Physician physician = new Physician();
				Member member = new Member(first_name,last_name,email,password,age,gender,role);
				result =physician.signup(member);
			}else {
				Pharmacist pharmacist = new Pharmacist();
				Member member = new Member(first_name,last_name,email,password,age,gender,role);
				result =pharmacist.signup(member);
			}
			System.out.println(role);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.write(result.toString());
	}

}
