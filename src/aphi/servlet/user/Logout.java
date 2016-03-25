package aphi.servlet.user;

import java.util.Enumeration;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import aphi.constant.system.*;

/**
 * <p>
 * Title: Logout Servlet
 * </p>
 *
 * <p>
 * Description: Servlet for managing user logout
 * </p>
 *
 * @author An Phi
 * @version 01.00.00
 */
public class Logout extends HttpServlet {
	/**
	 * The internal version id of this class
	 */
	public static final long serialVersionUID = 19620501L;

	/**
	 * Servlet version
	 */
	public static final String VERSION = "01.00.00";

	/**
	 * Logger Instance
	 */
	public static final Logger LOGGER = Logger.getLogger(Logout.class);

	/**
	 * Called by container when servlet instance is created. Logs the current
	 * version of the servlet and sets up the database connection.
	 *
	 * @param config
	 *            The servlet configuration
	 */
	public void init(ServletConfig config) {
		LOGGER.warn("Servlet init.  Version: " + VERSION);
	}

	/**
	 * The constructor - no operations carried out here
	 */
	public Logout() {
	}

	/**
	 * Uses the controller method to process the request.
	 * 
	 * @see #controller
	 *
	 * @param req
	 *            The request
	 * @param resp
	 *            The response
	 *
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		controller(req, resp);
	}

	/**
	 * Uses the controller method to process the request.
	 *
	 * @see #controller
	 *
	 * @param req
	 *            The request
	 * @param resp
	 *            The response
	 *
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		controller(req, resp);
	}

	/**
	 * The controlling method for the servlet. Manages the processing of the
	 * request.
	 * 
	 * @param req
	 *            The request
	 * @param resp
	 *            The response
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	private void controller(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		LOGGER.info("Request method: " + req.getMethod());
		@SuppressWarnings("unchecked")
		Enumeration<String> paramNames = req.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			LOGGER.info("Received param: " + paramName + " with value " + req.getParameter(paramName));
		}

		req.getSession().invalidate();
		resp.sendRedirect(req.getContextPath() + SERVLET_PATH.USER_LOGIN.val());
	}
}