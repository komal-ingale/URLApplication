package com.URLApplication.server.util;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * 
 * @author komal
 *
 */
public class URLUtil {
	public static final String ALPHABET = "0123456789abcdfghjkmnpqrstvwxyzABCDFGHJKLMNPQRSTVWXYZ";
	public static final int BASE = ALPHABET.length();

	public static String encode(long num) {
		StringBuilder str = new StringBuilder();
		while (num > 0) {
			int x = (int) ((num % BASE) % BASE);
			str.insert(0, ALPHABET.charAt(x));
			num = num / BASE;
		}
		return str.toString();
	}

	public static boolean isValid(String url) throws MalformedURLException, URISyntaxException {
		URL u = new URL(url);
		u.toURI();
		return true;
	}

}
