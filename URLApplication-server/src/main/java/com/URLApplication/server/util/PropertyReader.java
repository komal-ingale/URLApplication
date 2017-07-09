package com.URLApplication.server.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;;

public class PropertyReader {
	private static final Logger LOGGER = Logger.getLogger(PropertyReader.class.getName());
	private Properties properties = null;
	private static String fileName = "src/main/resources/db.properties";
	private static PropertyReader instance = new PropertyReader();

	public static PropertyReader getInstance() {
		return instance;
	}

	private PropertyReader() {
		loadProperties(PropertyReader.class.getClassLoader().getResourceAsStream(fileName));

	}

	public Properties getProperties() {
		return properties;
	}

	private void loadProperties(InputStream resourceStream) {
		properties = new Properties();
		try {
			LOGGER.log(Level.INFO, "Trying to read properties from file");

			LOGGER.log(Level.INFO, "Resource stream in property reader-" + resourceStream);
			if (resourceStream != null) {
				properties.load(resourceStream);
			} else {
				LOGGER.log(Level.SEVERE, "Invalid ResourceStream");
			}
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Exception while reading properties from input stream" + e);
		}
	}

	/**
	 * 
	 * @param transactionProperty
	 * @param msgArgs
	 * @return
	 */
	public String getPropertyValue(String propertyName, String... msgArgs) {
		String value = "";
		if (propertyName != null && properties != null) {
			value = (String) properties.get(propertyName);
			if (value != null && msgArgs != null) {
				value = MessageFormat.format(value, msgArgs);
			}
		}
		return value;
	}

	/**
	 * Get system env variables
	 * 
	 * @return
	 */
	public static String getSystemPropertyValue(String variable) {

		String osName = System.getProperty("os.name");
		if (osName != null && osName.contains("Windows")) {
			return System.getProperty(variable);
		} else {
			return System.getenv(variable);
		}
	}
}
