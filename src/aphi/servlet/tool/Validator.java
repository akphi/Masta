package aphi.servlet.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Static utility methods for common string manipulations
 * 
 * @author An Phi
 *
 */
public class Validator {
	/**
	 * Standard date format: MM/dd/yyyy
	 */
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"MM/dd/yyyy");

	/**
	 * No instances should be created, these are static utility methods
	 */
	private Validator() {
	}

	/**
	 * Format the date as a MM/dd/yyyy String
	 * 
	 * @param date
	 *          The date
	 * 
	 * @return The formatted string
	 */
	public final static String formatDate(Date date) {
		String formatted = "";

		if (date != null) {
			formatted = dateFormat.format(date);
		}

		return formatted;
	}

	/**
	 * Parse a string in MM/dd/yyyy format into a Date object
	 * 
	 * @param date
	 *          The date string to parse
	 * 
	 * @return The parsed date
	 * 
	 * @throws ParseException
	 */
	public final static Date parseDate(String date) throws ParseException {
		return dateFormat.parse(date);
	}

	/**
	 * Assure that a value is not null. If the value is null, an empty string is
	 * returned otherwise the initial value is returned intact.
	 * 
	 * @param data
	 *          The value
	 * 
	 * @return The value or an empty string if the value was null
	 */
	public final static String noNull(String data) {
		return data == null ? "" : data;
	}

	/**
	 * A crude approach to creating a string that can be saved in the database
	 * when included as a value in a SQL statement
	 * 
	 * @param data
	 *          The information being saved in the database as part of an SQL
	 *          statement
	 * 
	 * @return The data formatted for inclusion in an SQL statement
	 */
	public final static String dbSafeString(String data) {
		if (data != null) {
			data = data.replaceAll("\n", "\n");
			data = data.replaceAll("'", "''");
		}

		return data;
	}
}
