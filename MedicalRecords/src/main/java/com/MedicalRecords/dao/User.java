package com.MedicalRecords.dao;


import java.sql.SQLException;



import org.json.JSONObject;

import com.MedicalRecords.bean.Member;


abstract public class User {
	
	abstract JSONObject signup (Member member)  throws SQLException;
}

