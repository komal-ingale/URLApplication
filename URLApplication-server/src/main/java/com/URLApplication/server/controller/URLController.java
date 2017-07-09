package com.URLApplication.server.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.URLApplication.server.service.DAOService;

/**
 * 
 * @author komal
 *
 */
public class URLController extends HttpServlet {

	private static final long serialVersionUID = 492975143843062609L;
	static Logger logger = Logger.getLogger(URLController.class.getName());

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response = crossDomain(response);
		String url = (String) request.getParameter("url");
		System.out.println(url);
		try {
			String tinyUrl = DAOService.getInstance().createTinyUrl(url);
			PrintWriter pw = response.getWriter();
			pw.write(tinyUrl);
			pw.close();

		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.log(Level.SEVERE, e.getMessage());
		} catch (URISyntaxException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response = crossDomain(response);
		String[] split = request.getRequestURI().split("/");
		if (split.length >= 3) {
			String id = split[2];
			try {
				String bigUrl = DAOService.getInstance().getBigUrl(id);
				response.sendRedirect(bigUrl);
			} catch (SQLException e) {
				logger.log(Level.SEVERE, e.getMessage());
			}
		} else {
			response.getWriter().write("Invalid Url");
		}
	}

	public HttpServletResponse crossDomain(HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		return response;
	}
}
