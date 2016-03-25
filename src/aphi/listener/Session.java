package aphi.listener;

import java.util.List;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import aphi.fileio.MemberListPersistence;
import aphi.bean.Member;
import aphi.constant.system.*;

/**
 * Manages the session-based member list data persistence when the session expires
 * 
 * @author An Phi
 * @version 01.00.00
 *
 */
public class Session implements HttpSessionListener{
	/**
	 * Logger Instance
	 */
	private static Logger LOGGER = Logger.getLogger(Session.class);

	@Override
	public void sessionCreated(HttpSessionEvent sessionEvent) {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		LOGGER.info("Persist the member list for: "
				+ sessionEvent.getSession().getAttribute(SESSION_ATTRIB.USER.val()));
		try {
			MemberListPersistence.getInstance()
					.saveMemberList((String) sessionEvent.getSession()
							.getAttribute(SESSION_ATTRIB.USER.val()),
					(List<Member>) sessionEvent.getSession().getAttribute(SESSION_ATTRIB.MEMBER_LIST.val()));
		} catch (Throwable throwable) {
			LOGGER.error("Unable to save member list", throwable);
		}
	}
}
