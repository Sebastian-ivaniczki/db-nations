package org.java.myproj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;

public class Main {
	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/db-nations";
		String user = "root";
		String password = "root";

		try (Connection con = DriverManager.getConnection(url, user, password)) {
			Scanner scanner = new Scanner(System.in);
			System.out.print("Inserisci una stringa di ricerca: ");
			String searchQuery = scanner.nextLine();

			String sql = "SELECT c.country_id, c.name AS \"COUNTRY_NAME\", r.name AS \"REGION_NAME\", c2.name AS \"CONTINENT_NAME\"\r\n"
					+ "FROM countries c \r\n" + "JOIN regions r ON c.region_id = r.region_id \r\n"
					+ "JOIN continents c2 ON r.continent_id = c2.continent_id \r\n"
					+ "WHERE c.name LIKE ? OR r.name LIKE ? OR c2.name LIKE ?\r\n"
					+ "ORDER BY c.name ASC;";

			try (PreparedStatement ps = con.prepareStatement(sql)) {
				ps.setString(1, "%" + searchQuery + "%");
				ps.setString(2, "%" + searchQuery + "%");
				ps.setString(3, "%" + searchQuery + "%");

				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						int countryId = rs.getInt("country_id");
						String countryName = rs.getString("COUNTRY_NAME");
						String regionName = rs.getString("REGION_NAME");
						String continentName = rs.getString("CONTINENT_NAME");

						System.out.println("Country Id: "+ countryId + ", Country Name: " + countryName + ", Region Name: " + regionName + ", Continent Name: " + continentName);
					}
					System.out.println("scegli id della nazione");
					int Id = scanner.nextInt(); 
					
						String sqlLanguage = "SELECT l.language, c.name, cs.population, cs.gdp \r\n"
								+ "FROM languages l  \r\n"
								+ "JOIN country_languages cl ON l.language_id = cl.language_id\r\n"
								+ "join countries c on cl.country_id = c.country_id \r\n"
								+ "join country_stats cs on c.country_id = cs.country_id \r\n"
								+ "WHERE cl.country_id = ?";
		                try (PreparedStatement languagePs = con.prepareStatement(sqlLanguage)) {
		                    languagePs.setInt(1, Id);
		               

		                    try (ResultSet languageRs = languagePs.executeQuery()) {
		                    	Set<String> uniqueLanguages = new HashSet<>();
		                        
		                        String gdp = null;
		                        String population = null;
		                        while (languageRs.next()) {
		                        	 gdp = languageRs.getString("gdp");
		                        	population = languageRs.getString("population");
		                            String languageName = languageRs.getString("language");
		                            uniqueLanguages.add(languageName);
		                        }
		                        
		                        System.out.println("Lingue parlate nella country selezionata:");
		                        for (String languageName : uniqueLanguages) {
		                            System.out.println("- " + languageName);
		                        }
		                        
		                        System.out.println("\npopulation: " + population + "\ngdp: " + gdp);
		                    }
		                }
					scanner.close();
				}
			} catch (SQLException ex) {
				System.err.println("Query not well formed");
			}

		} catch (SQLException ex) {
			System.err.println("Error in connect to db");
		}
	}
}