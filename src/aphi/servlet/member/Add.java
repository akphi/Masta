package aphi.servlet.member;

import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import aphi.bean.Member;
import aphi.constant.system.*;
import aphi.servlet.tool.StringUtilities;

/**
 * <p>
 * Title: Member Add Servlet
 * </p>
 *
 * <p>
 * Description: Servlet for managing member adding
 * </p>
 *
 * @author An Phi
 * @version 01.00.00
 */
public class Add extends HttpServlet {
	/**
	 * The internal version id of this class
	 */
	private static final long serialVersionUID = 19620501L;

	/**
	 * Servlet version
	 */
	private static final String VERSION = "01.00.00";

	/**
	 * Logger Instance
	 */
	private static Logger LOGGER = Logger.getLogger(Add.class);

	public void init(ServletConfig config) {
		LOGGER.warn("Servlet init.  Version: " + VERSION);
	}

	/**
	 * The constructor - no operations carried out here
	 */
	public Add() {
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

		if (req.getMethod() == "POST") {
			addMember(req);
			forwardPath = setupForListDisplay(req);
		} else {
			forwardPath = SERVLET_PATH.MEMBER_LIST.val();
		}

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

	/**
	 * Add a member to the member list using the new member data in the request
	 * 
	 * @param req
	 *            The request
	 */
	private void addMember(HttpServletRequest req) {
		LOGGER.info("Add a member");

		Member member = getMemberFromRequest(req);

		addMemberToList(req, member);
	}

	/**
	 * Create a Member instance from member data in the request. If there is no id
	 * supplied then the created member will have a new unique id.
	 * 
	 * @param req
	 *            The request
	 * 
	 * @return A Member instance with information from the request
	 */
	private Member getMemberFromRequest(HttpServletRequest req) {
		String firstName = req.getParameter(REQUEST_PARAM.MEMBER_FIRSTNAME.val());
		if (firstName.equals("")) LOGGER.warn("empty firstname");
		String lastName = req.getParameter(REQUEST_PARAM.MEMBER_LASTNAME.val());
		String country = req.getParameter(REQUEST_PARAM.MEMBER_COUNTRY.val());
		String birthDay = req.getParameter(REQUEST_PARAM.MEMBER_BIRTHDAY.val());
		int point = Integer.parseInt(req.getParameter(REQUEST_PARAM.MEMBER_POINT.val()));
		Date birthDayDate = null;
		Member member;

		if (birthDay != null && birthDay.trim().length() > 0) {
			try {
				birthDayDate = StringUtilities.parseDate(birthDay);
			} catch (Throwable throwable) {
				LOGGER.warn("Could not parse supplied date: " + birthDay);
				birthDayDate = null;
			}
		}

		String id = req.getParameter(REQUEST_PARAM.MEMBER_ID.val());
		if (id != null && id.trim().length() > 0) {
			id = id.trim();
		} else {
			id = null;
		}

		member = new Member(id, birthDayDate, firstName, lastName, country, point);

		LOGGER.info("Got member from request: " + member);

		return member;

	}

	/**
	 * Add a member to the member list that is stored in the session
	 * 
	 * @param req
	 *            The request
	 * @param member
	 *            The member to be added
	 */
	private void addMemberToList(HttpServletRequest req, Member member) {
		List<Member> memberList = getMemberListFromSession(req.getSession());

		memberList.add(member);
	}

	/**
	 * Retrieve the member list from the session
	 * 
	 * @param session
	 *            The session
	 * 
	 * @return The member list from the session
	 */
	@SuppressWarnings("unchecked")
	private List<Member> getMemberListFromSession(HttpSession session) {
		return (List<Member>) session.getAttribute(SESSION_ATTRIB.MEMBER_LIST.val());
	}
}
