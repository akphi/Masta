package aphi.fileio;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import aphi.bean.Member;
import aphi.rdb.RdbSingletonFacade;
import aphi.servlet.tool.StringUtilities;
import org.owasp.encoder.Encode;

/**
 * Handles the reading and writing of member lists from and to a database
 * 
 * @author An Phi
 * @version 01.00.00
 *
 */
public class MemberListPersistence {
	/**
	 * Single instance of the class
	 */
	private static MemberListPersistence instance;

	/**
	 * Logger Instance
	 */
	private static Logger LOGGER = Logger.getLogger(MemberListPersistence.class);

	/**
	 * Singleton - private constructor
	 */
	private MemberListPersistence() {

	}

	/**
	 * Get the singleton instance
	 * 
	 * @return The instance
	 */
	public static synchronized MemberListPersistence getInstance() {
		if (instance == null) {
			instance = new MemberListPersistence();
		}

		return instance;
	}

	/**
	 * Retrieve the member list from the database
	 * 
	 * @return The loaded member list
	 * 
	 */
	public List<Member> loadTaskList(String userIdentifier) {
		RdbSingletonFacade rdb = RdbSingletonFacade.getInstance();
		List<Member> memberList = new ArrayList<Member>();
		ResultSet results = null;
		userIdentifier = Encode.forJava(userIdentifier);

		try {
			results = rdb.executeQuery(
					"select id, birthDay, firstName, lastName, country, point from member where userName = '" + userIdentifier + "'");

			while (results.next()) {
				Member member = new Member(results.getString(3), results.getString(4), results.getString(5), results.getInt(6));
				member.setId(results.getString(1));
				member.setBirthDay(results.getDate(2));
				memberList.add(member);
			}
		} catch (Throwable throwable) {
			LOGGER.error("Unable to load member list from the database", throwable);
			throw new RuntimeException("Unable to load member list from the database", throwable);
		} finally {
			rdb.cleanUp(results);
		}

		return memberList;
	}

	/**
	 * Save the member list into the database
	 * 
	 * @param memberList
	 *            The member list
	 * 
	 * @throws IOException
	 */
	public void saveMemberList(String userIdentifier, List<Member> memberList) throws IOException {
		RdbSingletonFacade rdb = RdbSingletonFacade.getInstance();
		userIdentifier = Encode.forJava(userIdentifier);

		// Remove previous data for the user
		rdb.execute("delete from member where userName = '" + userIdentifier + "'");

		// Add the current set of tasks to the database for the user
		for (Member member : memberList) {
			String sql = "insert into member (userName, birthDay, firstName, lastName, country, point) values (";
			sql += "'" + userIdentifier + "',";
			if (member.getBirthDay() != null) {
				sql += "str_to_date('" + StringUtilities.formatDate(member.getBirthDay()) + "','%c/%d/%Y')";
			} else {
				sql += "null";
			}
			sql += ",";
			sql += "'" + Encode.forJava(member.getFirstName()) + "',";
			sql += "'" + Encode.forJava(member.getLastName()) + "',";
			sql += "'" + Encode.forJava(member.getCountry()) + "',";
			sql += "'" + member.getPoint() + "'";
			sql += ")";
			
			LOGGER.info("Inserting fName: " + Encode.forJava(member.getFirstName()));
			LOGGER.info("Inserting member: " + sql);
			rdb.execute(sql);
		}
	}
}
