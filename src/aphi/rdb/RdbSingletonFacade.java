package aphi.rdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

/**
 * Manages the connection and interactions with a relational database.
 * 
 * @author An Phi
 * @version 01.00.00
 *
 */
public class RdbSingletonFacade {
	/**
	 * Single instance of the class
	 */
	private static RdbSingletonFacade instance;

	/**
	 * Logger Instance
	 */
	private static Logger LOGGER = Logger.getLogger(RdbSingletonFacade.class);

	/**
	 * The RDB driver class name
	 */
	private String rdbDriverClassName;

	/**
	 * The RDB server URL
	 */
	private String rdbUrl;

	/**
	 * The user id to use when connecting to the RDB
	 */
	private String userId;

	/**
	 * The password to use when connecting to the RDB
	 */
	private String password;

	/**
	 * Keep track of whether a result set has been created but not yet closed
	 */
	private boolean hasOpenResultSet;

	/**
	 * Singleton - private constructor
	 */
	private RdbSingletonFacade() {

	}

	/**
	 * Obtain the Singleton instance
	 * 
	 * @return The instance
	 */
	public final static synchronized RdbSingletonFacade getInstance() {
		if (instance == null) {
			instance = new RdbSingletonFacade();
		}

		return instance;
	}

	/**
	 * Set the RDB driver class name. This method attempts to load the class
	 * using a Class.forName() call. If the class cannot be found an exception
	 * will be thrown.
	 * 
	 * @param rdbDriverClassName
	 *            The RDB driver class name
	 */
	public void setRdbDriverClassName(String rdbDriverClassName) {
		this.rdbDriverClassName = rdbDriverClassName;

		try {
			Class.forName(rdbDriverClassName);
		} catch (ClassNotFoundException cnfExc) {
			throw new IllegalArgumentException("Database driver class not found: " + rdbDriverClassName, cnfExc);
		}
	}

	/**
	 * Set the URL to the database server
	 * 
	 * @param rdbUrl
	 *            The database server URL
	 */
	public void setRdbUrl(String rdbUrl) {
		this.rdbUrl = rdbUrl;
	}

	/**
	 * Set the user id to use when connecting to the database
	 * 
	 * @param userId
	 *            The user id
	 */
	public void setUserId(String userId) {
		LOGGER.warn("inserted " + userId);
		this.userId = userId;
	}

	/**
	 * Set the password to use when connecting to the database
	 * 
	 * @param password
	 *            The password
	 */
	public void setPassword(String password) {
		LOGGER.warn("inserted " + password);
		this.password = password;
	}

	/**
	 * Execute a DDL or DML SQL statement that does not return a result set.
	 * This includes any DDL as well as DML such as INSERT, UPDATE, and DELETE
	 * statements.
	 * 
	 * @param sql
	 *            The SQL statement
	 * 
	 * @return True of the statement succeeded, false otherwise
	 */
	public synchronized boolean execute(String sql) {
		Connection connection = null;
		Statement statement = null;
		boolean result;

		validateConfiguration();

		try {
			connection = getConnection();
			statement = getStatement(connection);
			result = statement.execute(sql);
		} catch (Throwable throwable) {
			LOGGER.error("Unable to execute SQL statement: " + sql, throwable);
			throw new RuntimeException("Unable to execute SQL statement: " + sql, throwable);
		} finally {
			closeStatement(statement);
			closeConnection(connection);
		}

		return result;
	}

	/**
	 * Execute a DML SQL statement that returns a result set, such as a SELECT
	 * statement. Note that the caller is responsible for calling the
	 * cleanUp(ResultSet) method after processing the returned ResultSet
	 * instance.
	 * 
	 * @param sql
	 *            The SQL statment that returns a result set
	 * 
	 * @return The results of the query
	 * 
	 * @see #cleanUp(ResultSet)
	 */
	public synchronized ResultSet executeQuery(String sql) {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		validateConfiguration();

		try {
			connection = getConnection();
			statement = getStatement(connection);
			resultSet = statement.executeQuery(sql);
			hasOpenResultSet = true;
		} catch (Throwable throwable) {
			LOGGER.error("Unable to execute SQL statement: " + sql, throwable);
			throw new RuntimeException("Unable to execute SQL statement: " + sql, throwable);
		}

		return resultSet;
	}

	/**
	 * Close the ResultSet and underlying Statement and Connection objects. This
	 * should be called after calling the executeQuery(String) method and
	 * processing the results.
	 * 
	 * @param resultSet
	 *            The result set to close
	 * 
	 * @see #executeQuery(String)
	 */
	public synchronized void cleanUp(ResultSet resultSet) {
		Connection connection = null;
		Statement statement = null;

		try {
			statement = resultSet.getStatement();
			connection = statement.getConnection();
		} catch (Throwable throwable) {
			LOGGER.error("Failed to cleanup result set statement and connection", throwable);
		} finally {
			closeResultSet(resultSet);
			closeStatement(statement);
			closeConnection(connection);
			hasOpenResultSet = false;
		}
	}

	/**
	 * Closes a result set, reporting any exception that occurs
	 * 
	 * @param resultSet
	 *            The result set to close
	 */
	private void closeResultSet(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (Throwable throwable) {
				LOGGER.warn("Failed to close result set", throwable);
			}
		}
	}

	/**
	 * Closes a statement, reporting any exception that occurs
	 * 
	 * @param statement
	 *            The statement to close
	 */
	private void closeStatement(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (Throwable throwable) {
				LOGGER.warn("Unable to close the statement", throwable);
			}
		}
	}

	/**
	 * Closes a connection, reporting any exception that occurs
	 * 
	 * @param connection
	 *            The connection to close
	 */
	private void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (Throwable throwable) {
				LOGGER.warn("Unable to close the connection", throwable);
			}
		}
	}

	/**
	 * Validate that the required configuration information has been set. This
	 * is used prior to attempting to connect to the database.
	 */
	private void validateConfiguration() {
		StringBuffer errors = new StringBuffer();

		if (rdbDriverClassName == null) {
			errors.append("The RDB Driver Class Name has not been set. ");
		}

		if (rdbUrl == null) {
			errors.append("The RDB Url has not been set. ");
		}

		if (userId == null) {
			errors.append("The RDB User Id has not been set. ");
			LOGGER.warn("Hey" + userId);
		}

		if (password == null) {
			errors.append("The RDB Password has not been set. ");
			LOGGER.warn(password);
		}

		if (hasOpenResultSet) {
			errors.append("There is an active result set. " + "Make sure to call the cleanUp(ResultSet) method "
					+ "to release the prior result set before running " + "another query");
		}

		if (errors.length() > 0) {
			throw new IllegalStateException(errors.toString());
		}
	}

	/**
	 * Obtain a connection to the database
	 * 
	 * @return The connection
	 */
	private Connection getConnection() {
		validateConfiguration();

		try {
			return DriverManager.getConnection(rdbUrl, userId, password);
		} catch (Throwable throwable) {
			LOGGER.error("Unable to obtain a connection to the database: " + rdbUrl, throwable);
			throw new RuntimeException("Unable to obtain a connection to the database: " + rdbUrl, throwable);
		}
	}

	/**
	 * Obtain a statement for executing a database query
	 * 
	 * @param connection
	 *            The connection to the database
	 * 
	 * @return The statement
	 */
	private Statement getStatement(Connection connection) {
		try {
			return connection.createStatement();
		} catch (Throwable throwable) {
			LOGGER.error("Unable to obtain a statement", throwable);
			throw new RuntimeException("Unable to obtain a statement", throwable);

		}
	}

	/**
	 * This is a test method for demonstrating all of the steps necessary to
	 * connect to an RDB and execute a query. The query output is sent to the
	 * console.
	 * 
	 * @see #main(String[])
	 */
	private void readDatabase() {
		try {
			System.out.println("Run a database query");
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3310/aphi_tasklist", "aphi_tlweb", "3lcmj6*W");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select id, title from tasks");
			while (rs.next()) {
				System.out.println("id: " + rs.getInt(1) + " title:" + rs.getString(2));
			}
			rs.close();
			stmt.close();
			conn.close();

		} catch (Throwable throwable) {
			throwable.printStackTrace();
			LOGGER.error("Database problem", throwable);
		}
	}

	/**
	 * This is a test method for demonstrating all of the steps necessary to
	 * connect to an RDB and execute a query using a prepared statement. The
	 * query output is sent to the console.
	 * 
	 * @see #main(String[])
	 */
	private void readDatabasePreparedStatement() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			System.out.println("Run a prepared database query");
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3310/aphi_tasklist", "aphi_tlweb", "3lcmj6*W");
			ps = conn.prepareStatement("select id, title from tasks where id > ? and title like ?");
			ps.setInt(1, 0);
			ps.setString(2, "%p%");
			rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println("id: " + rs.getInt(1) + " title:" + rs.getString(2));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			LOGGER.error("Database problem", sqle);
		} catch (ClassNotFoundException cnf) {
			cnf.printStackTrace();
			LOGGER.error("Database problem", cnf);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqle) {
					sqle.printStackTrace();
					LOGGER.error("Failed to close result set", sqle);
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException sqle) {
					sqle.printStackTrace();
					LOGGER.error("Failed to close prepared statement", sqle);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqle) {
					sqle.printStackTrace();
					LOGGER.error("Failed to close connection", sqle);
				}
			}
		}
	}

	/**
	 * This is provided in order to run the readDatabase demonstration code.
	 * 
	 * @param args
	 *            Arguments to the method, not used
	 * 
	 * @see #readDatabase()
	 */
	public final static void main(String args[]) {
		System.out.println("Demonstrate use of Statement");
		new RdbSingletonFacade().readDatabase();

		System.out.println("===============================");

		System.out.println("Demonstrate use of PreparedStatement");
		new RdbSingletonFacade().readDatabasePreparedStatement();
	}
}
