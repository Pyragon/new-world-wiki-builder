package com.cryo.db;

import com.cryo.NewWorldWikiBuilder;
import org.postgresql.ds.PGPoolingDataSource;

import java.sql.*;
import java.util.Properties;

public class DatabaseManager {

	private static Connection connection;

	public void init() {
		try {
			Properties props = NewWorldWikiBuilder.getProperties();

			connection = DriverManager.getConnection("jdbc:postgresql://"+props.getProperty("db-host")+":5432/"+props.getProperty("database"), props.getProperty("db-user"), props.getProperty("db-pass"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertImage(String name, int length, byte[] hex) {
		try {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO assets VALUES (DEFAULT, ?, ?, '.png', 'image', 'image/png', ?, NULL, '2021-10-22T22:15:21.798Z', '2021-10-22T22:15:21.798Z', NULL, 1);");
			statement.setString(1, name);
			statement.setString(2, "hash");
			statement.setInt(3, length);

			statement.execute();

			statement = connection.prepareStatement("SELECT * FROM \"assetData\" ORDER BY id DESC LIMIT 1");
			ResultSet set = statement.executeQuery();
			if(!set.next()) {
				System.err.println("Can't find next row.");
				return;
			}
			int id = set.getInt("id");

			statement = connection.prepareStatement("INSERT INTO \"assetData\" VALUES ("+(id+1)+", ?);");
			statement.setBytes(1, hex);

			statement.execute();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
