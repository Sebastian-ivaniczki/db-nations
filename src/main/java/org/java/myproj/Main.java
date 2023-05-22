package org.java.myproj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/db-nations";
		String user = "root";
		String password = "root";
		
		try (Connection con = DriverManager.getConnection(url, user, password)){
			System.out.println("con");
		} catch (SQLException ex) {
			System.err.println("Query not well formed");
		}
	}
}
