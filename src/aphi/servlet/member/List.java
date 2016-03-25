package aphi.servlet.member;

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
 * Title: Member List Servlet
 * </p>
 *
 * <p>
 * Description: Servlet for managing member list
 * </p>
 *
 * @author An Phi
 * @version 01.00.00
 */
public class List extends HttpServlet{

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
	public static final Logger LOGGER = Logger.getLogger(List.class);
	
	/**
	 * The constructor - no operations carried out here
	 */
	public List() {
	}
	
	public void init(ServletConfig config) {
		LOGGER.warn("Servlet init.  Version: " + VERSION);
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
		String forwardPath;

		LOGGER.info("Request method: " + req.getMethod());
		@SuppressWarnings("unchecked")
		Enumeration<String> paramNames = req.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			LOGGER.info("Received param: " + paramName + " with value " + req.getParameter(paramName));
		}

		forwardPath = setupForListDisplay(req);

		req.getRequestDispatcher(forwardPath).forward(req, resp);

	}

	/**
	 * Setup the request with data for displaying the member list
	 * 
	 * @param req
	 *            The request
	 * 
	 * @return The JSP to use for rendering the member list
	 */
	private String setupForListDisplay(HttpServletRequest req) {
		req.setAttribute(REQUEST_ATTRIB.MEMBER_LIST.val(), req.getSession().getAttribute(SESSION_ATTRIB.MEMBER_LIST.val()));
		return JSP_PATH.MEMBER_LIST.val();
	}
}
