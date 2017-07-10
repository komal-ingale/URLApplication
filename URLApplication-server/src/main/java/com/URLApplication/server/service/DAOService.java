package com.URLApplication.server.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.URLApplication.server.util.PropertyReader;
import com.URLApplication.server.util.URLUtil;

/**
 * 
 * @author komal
 *
 */
public class DAOService {

	private static DAOService instance;

	static Connection connection = null;
	static Logger logger = Logger.getLogger(DAOService.class.getName());
	static PropertyReader propertyReader = PropertyReader.getInstance();
	static String sqlConnectionUrl = propertyReader.getPropertyValue("jdbc.url");

	public static DAOService getInstance() throws SQLException {
		if (instance == null) {
			synchronized (DAOService.class) {
				if (instance == null) {
					instance = new DAOService();
					instance.connect();
					instance.createTableUrl();

				}
			}
		}
		return instance;
	}

	private Connection connect() {
		try {
			if (connection == null || connection.isClosed()) {
				try {
					System.out.println(propertyReader.getPropertyValue("jdbc.driver"));
					Class.forName(propertyReader.getPropertyValue("jdbc.driver"));
				} catch (ClassNotFoundException e) {
					logger.log(Level.SEVERE, e.getMessage());
				}
				String username = propertyReader.getPropertyValue("jdbc.username");
				String password = propertyReader.getPropertyValue("jdbc.password");
				connection = DriverManager.getConnection(sqlConnectionUrl, username, password);
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
		return connection;
	}

	public void createTableUrl() throws SQLException {
		String sql = "CREATE TABLE IF NOT EXISTS URL_MAPPING (\n" + "	id BIGINT PRIMARY KEY AUTO_INCREMENT,\n"
				+ "	tiny_url VARCHAR(10) NOT NULL UNIQUE,\n" + "	url TEXT NOT NULL \n" + ");";
		Statement stmt = null;
		try {
			connection = this.connect();
			stmt = connection.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			stmt.close();
			connection.close();
		}
	}

	public String createTinyUrl(String url)
			throws SQLException, IllegalArgumentException, MalformedURLException, URISyntaxException {
		if (url == null || url.isEmpty()) {
			throw new IllegalArgumentException("Null or Empty Url");
		}
		if (URLUtil.isValid(url)) {
			String tinyUrl = getTinyUrl(url);
			if (!tinyUrl.isEmpty())
				return tinyUrl;
			String sql = "INSERT INTO URL_MAPPING(tiny_url,url) VALUES(?,?)";
			long max = getMaxIdFromUrl();
			tinyUrl = URLUtil.encode(max + 1);
			PreparedStatement pstmt = null;
			try {
				connection = this.connect();
				pstmt = connection.prepareStatement(sql);
				pstmt.setString(1, tinyUrl);
				pstmt.setString(2, url);
				pstmt.executeUpdate();

			} catch (SQLException e) {

				System.out.println(e.getMessage());
			} finally {
				pstmt.close();
				connection.close();
			}

			return tinyUrl;
		}
		return "";
	}

	private long getMaxIdFromUrl() throws SQLException {

		String sql = "SELECT MAX(id) AS max FROM URL_MAPPING;";
		long result = 0;
		PreparedStatement pstmt = null;
		try {
			connection = this.connect();
			pstmt = connection.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				result = rs.getLong("max");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			pstmt.close();
			connection.close();
		}
		return result;

	}

	public String getTinyUrl(String url) throws SQLException, IllegalArgumentException {
		if (url == null || url.isEmpty()) {
			throw new IllegalArgumentException("Null or Empty Url");
		}
		String sql = "SELECT tiny_url from URL_MAPPING where url=?";
		String result = "";
		PreparedStatement pstmt = null;
		try {
			connection = this.connect();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, url);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				result = rs.getString("tiny_url");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			pstmt.close();
			connection.close();
		}
		return result;

	}

	public String getBigUrl(String tinyUrl) throws SQLException, IllegalArgumentException {
		if (tinyUrl == null || tinyUrl.isEmpty()) {
			throw new IllegalArgumentException("Null or Empty Url");
		}
		String sql = "SELECT url from URL_MAPPING where tiny_url=?";
		String result = "";
		PreparedStatement pstmt = null;
		try {
			connection = this.connect();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, tinyUrl);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				result = rs.getString("url");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			pstmt.close();
			connection.close();
		}
		return result;
	}

	public static void main(String[] args)
			throws SQLException, IOException, IllegalArgumentException, URISyntaxException {
		DAOService instance2 = DAOService.getInstance();
		instance2.createTinyUrl("http://www.sqlitetutorial.net/sqlite-java/insert/");
	}
}
