package org.URLApplication.server;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import org.junit.Test;

import com.URLApplication.server.service.DAOService;

/**
 * 
 * @author komal
 *
 */
public class DAOServiceTest {

	@Test
	public void createTinyUrlEmpty() {
		try {
			DAOService.getInstance().createTinyUrl("");
			assert (false);
		} catch (SQLException e) {
			assert (false);
		} catch (IllegalArgumentException e) {
			assert (true);
		} catch (MalformedURLException e) {
			assert (false);
		} catch (URISyntaxException e) {
			assert (false);
		}
	}

	@Test
	public void createTinyUrlNull() {
		try {
			DAOService.getInstance().createTinyUrl(null);
			assert (false);
		} catch (SQLException e) {
			assert (false);
		} catch (IllegalArgumentException e) {
			assert (true);
		} catch (MalformedURLException e) {
			assert (false);
		} catch (URISyntaxException e) {
			assert (false);
		}
	}

	@Test
	public void createTinyUrlExitingUrl() {
		try {
			String tinyUrl1 = DAOService.getInstance().createTinyUrl("https://www.facebook.com/");
			String tinyUrl2 = DAOService.getInstance().createTinyUrl("https://www.facebook.com/");
			if (tinyUrl1.equals(tinyUrl2))
				assert (true);
			else
				assert (false);
		} catch (SQLException e) {
			assert (false);
		} catch (IllegalArgumentException e) {
			assert (false);
		} catch (MalformedURLException e) {
			assert (false);
		} catch (URISyntaxException e) {
			assert (false);
		}
	}

	@Test
	public void createTinyUrlInvalid() {
		try {
			DAOService.getInstance().createTinyUrl("www.facebook.com/");
			assert (false);
		} catch (SQLException e) {
			assert (false);
		} catch (IllegalArgumentException e) {
			assert (false);
		} catch (MalformedURLException e) {
			assert (true);
		} catch (URISyntaxException e) {
			assert (true);
		}
	}

	@Test
	public void getTinyUrlEmpty() {
		try {
			DAOService.getInstance().getTinyUrl("");
			assert (false);
		} catch (SQLException e) {
			assert (false);
		} catch (IllegalArgumentException e) {
			assert (true);
		}
	}

	@Test
	public void getTinyUrlNull() {
		try {
			DAOService.getInstance().getTinyUrl(null);
			assert (false);
		} catch (SQLException e) {
			assert (false);
		} catch (IllegalArgumentException e) {
			assert (true);
		}
	}

	@Test
	public void getTinyUrlInvalid() {
		try {
			String tinyUrl = DAOService.getInstance().getTinyUrl("a#");
			if (tinyUrl.isEmpty()) {
				assert (true);
			} else
				assert (false);
		} catch (SQLException e) {
			assert (false);
		} catch (IllegalArgumentException e) {
			assert (false);
		}
	}

	@Test
	public void getBigUrlEmpty() {
		try {
			DAOService.getInstance().getBigUrl("");
			assert (false);
		} catch (SQLException e) {
			assert (false);
		} catch (IllegalArgumentException e) {
			assert (true);
		}
	}

	@Test
	public void getBigUrlNull() {
		try {
			DAOService.getInstance().getBigUrl(null);
			assert (false);
		} catch (SQLException e) {
			assert (false);
		} catch (IllegalArgumentException e) {
			assert (true);
		}
	}
}
