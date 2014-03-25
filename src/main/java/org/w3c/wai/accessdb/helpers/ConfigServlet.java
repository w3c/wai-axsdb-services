package org.w3c.wai.accessdb.helpers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.wai.accessdb.services.ConfigService;
@Deprecated
public class ConfigServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/javascript");
		PrintWriter out = response.getWriter();
		out.append("var SESSION_ID=\"" + request.getSession().getId()+ "\";\n");
		out.append(ConfigService.INSTANCE.printScript());
		out.flush();
	}
}
