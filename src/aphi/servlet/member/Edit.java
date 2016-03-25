package aphi.servlet.member;

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
 * Title: Member Edit Servlet
 * </p>
 *
 * <p>
 * Description: Servlet for managing member edit
 * </p>
 *
 * @author An Phi
 * @version 01.00.00
 */
public class Edit extends HttpServlet {
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
	private static Logger LOGGER = Logger.getLogger(Edit.class);

	public void init(ServletConfig config) {
		LOGGER.warn("Servlet init.  Version: " + VERSION);
	}

	/**
	 * The constructor - no operations carried out here
	 */
	public Edit() {
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
			forwardPath = setupForEdit(req);
		} else {
			forwardPath = SERVLET_PATH.MEMBER_LIST.val();
		}
		
		req.getRequestDispatcher(forwardPath).forward(req, resp);

	}

	/**
	 * Setup the request with a member's data in preparation for the user editing
	 * the member
	 * 
	 * @param req
	 *            The request
	 * 
	 * @return The JSP to use for editing the member
	 */
	private String setupForEdit(HttpServletRequest req) {
		String memberId = req.getParameter(REQUEST_PARAM.MEMBER_ID.val());
		Member member = findMember(req, memberId);

		req.setAttribute(REQUEST_ATTRIB.MEMBER_ID.val(), member.getId());
		req.setAttribute(REQUEST_ATTRIB.MEMBER_FIRSTNAME.val(), member.getFirstName());
		req.setAttribute(REQUEST_ATTRIB.MEMBER_LASTNAME.val(), member.getLastName());
		req.setAttribute(REQUEST_ATTRIB.MEMBER_COUNTRY.val(), member.getCountry());
		req.setAttribute(REQUEST_ATTRIB.MEMBER_POINT.val(), member.getPoint());

		if (member.getBirthDay() != null) {
			req.setAttribute(REQUEST_ATTRIB.MEMBER_BIRTHDAY.val(), StringUtilities.formatDate(member.getBirthDay()));
		} else {
			req.setAttribute(REQUEST_ATTRIB.MEMBER_BIRTHDAY.val(), "");
		}

		return JSP_PATH.MEMBER_EDIT.val();

	}

	/**
	 * Locate a member in the member list based on the member's id
	 * 
	 * @param req
	 *            The request
	 * @param id
	 *            The member's id
	 * 
	 * @return The member with the requested id
	 */
	private Member findMember(HttpServletRequest req, String id) {
		List<Member> memberList = getMemberListFromSession(req.getSession());
		Member matchingMember = null;

		for (Member member : memberList) {
			if (member.getId().equals(id)) {
				matchingMember = member;
				break;
			}
		}

		return matchingMember;
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
