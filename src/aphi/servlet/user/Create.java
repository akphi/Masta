package aphi.servlet.user;

import java.util.Enumeration;
import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.owasp.encoder.Encode;

import aphi.fileio.MemberListPersistence;
import aphi.rdb.RdbSingletonFacade;
import aphi.constant.system.*;
import aphi.servlet.tool.StringUtilities;

/**
 * <p>
 * Title: Create Servlet
 * </p>
 *
 * <p>
 * Description: Servlet for managing user create
 * </p>
 *
 * @author An Phi
 * @version 01.00.00
 */
public class Create extends HttpServlet {
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
	public static final Logger LOGGER = Logger.getLogger(Create.class);

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
	public Create() {
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

		if (StringUtilities.noNull(req.getPathInfo()).equals("/logout")) {
			req.getSession().invalidate();
		}

		if (req.getSession().getAttribute(SESSION_ATTRIB.USER.val()) != null) {
			forwardPath = SERVLET_PATH.MEMBER_LIST.val();
		} else {
			if (req.getParameter(REQUEST_PARAM.USER_LOGIN_NAME.val()) != null) {
				String authenticatedUser = authenticateUser(req);
				if (authenticatedUser != null) {
					setupSession(authenticatedUser, req);
					forwardPath = SERVLET_PATH.MEMBER_LIST.val();
				} else {
					forwardPath = setupForLogin(req);
				}
			} else {
				forwardPath = setupForLogin(req);
			}
		}

		req.getRequestDispatcher(forwardPath).forward(req, resp);

	}

	/**
	 * Setup the session with the user's internal id and member list
	 * 
	 * @param authenticatedUser
	 *            The user's internal id
	 * @param req
	 *            The request
	 */
	private void setupSession(String authenticatedUser, HttpServletRequest req) {
		req.getSession().setAttribute(SESSION_ATTRIB.USER.val(), authenticatedUser);
		req.getSession().setAttribute(SESSION_ATTRIB.MEMBER_LIST.val(), MemberListPersistence.getInstance().loadTaskList(authenticatedUser));
	}

	/**
	 * Check the user's credentials. If correct then return the user's internal
	 * id.
	 * 
	 * @param req
	 *            The request
	 * 
	 * @return The user's internal id or null if unauthenticated
	 */
	private String authenticateUser(HttpServletRequest req) {
		String authenticatedUserId = null;
		String userId = Encode.forJava(StringUtilities.noNull(req.getParameter(REQUEST_PARAM.USER_LOGIN_NAME.val())));
		String password = Encode.forJava(StringUtilities.noNull(req.getParameter(REQUEST_PARAM.USER_LOGIN_PASSWORD.val())));

		RdbSingletonFacade rdb = RdbSingletonFacade.getInstance();
		ResultSet results = null;
		try {
			rdb.execute("insert into user (username, password) values ('" + userId + "','" + password + "')");

			// if (!results.isBeforeFirst()) {
			// LOGGER.warn("Login in as '" + userId + "' failed");
			// }
			// while (results.next()) {
			// authenticatedUserId = results.getString(1) + "";
			// LOGGER.warn("Logged in as '" + userId + " ' succeeded");
			// }

			LOGGER.warn("Registered" + authenticatedUserId);

		} catch (Throwable throwable) {
			LOGGER.error("Unable to execute query", throwable);
			throw new RuntimeException("Unable to execute query", throwable);
		} finally {
			rdb.cleanUp(results);
		}
		return authenticatedUserId;
	}

	/**
	 * Prepare data for the login screen
	 * 
	 * @param req
	 *            The request
	 * 
	 * @return The JSP for rendering the login page
	 */
	private String setupForLogin(HttpServletRequest req) {
		String loginId = StringUtilities.noNull(req.getParameter(REQUEST_PARAM.USER_LOGIN_NAME.val()));
		req.setAttribute(REQUEST_ATTRIB.USER_LOGIN_NAME.val(), loginId);

		return JSP_PATH.USER_CREATE.val();
	}
}