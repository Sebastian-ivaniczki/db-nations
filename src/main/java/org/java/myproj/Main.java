package org.java.myproj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/db-nations";
		String user = "root";
		String password = "root";
		
		try (Connection con = DriverManager.getConnection(url, user, password)){
			//System.out.println("con");
			
			String sql = "select c.name as \"COUNTRI_NAME\", r.name as \"REGION_NAME\" ,c2.name as \"CONTINENT_NAME\"\r\n"
					+ "from countries c \r\n"
					+ "join regions r on c.region_id = r.region_id \r\n"
					+ "join continents c2 on r.continent_id = c2.continent_id \r\n"
					+ "order by c.name  asc; ";
			
			try (PreparedStatement ps = con.prepareStatement(sql)){
				try (ResultSet rs = ps.executeQuery()){
					while (rs.next()) {
				       
				        String countryName = rs.getString("COUNTRI_NAME");
				        String regionName = rs.getString("REGION_NAME");
				        String continentName = rs.getString("CONTINENT_NAME");

				       
				        System.out.println("Country Name: " + countryName + ", Region Name: " + regionName + ", Continent Name: " + continentName);
					}
				} 
		}catch (SQLException ex) {
			System.err.println("Query not well formed");
		}
			
		} catch (SQLException ex) {
			System.err.println("Error in connect to db");
		}
	}
}
