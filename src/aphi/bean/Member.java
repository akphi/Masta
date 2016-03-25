package aphi.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Java bean represents a member
 * 
 * @author An Phi
 * @version 01.00.00
 *
 */
public class Member implements Serializable {
	/**
	 * The internal version id of this class
	 */
	private static final long serialVersionUID = 19620501;

	/**
	 * A unique identifier for the task. In an RDB this would be the primary key
	 */
	private String id;

	private Date birthDay;
	private String firstName;
	private String lastName;
	private String country;
	private int point;

	/**
	 * Create a member (without birthDay)
	 * 
	 * @param firstName
	 *            The member's first name
	 * @param lastName
	 *            The member's last name
	 * @param country
	 *            The member's country
	 * @param point
	 *            The member's point
	 */
	public Member(String firstName, String lastName, String country, int point) {
		this(null, null, firstName, lastName, country, point);
	}

	/**
	 * Create a member (with birthDay)
	 * 
	 * @param birthDay
	 *            The member's birthday (optional)
	 * @param firstName
	 *            The member's first name
	 * @param lastName
	 *            The member's last name
	 * @param country
	 *            The member's country
	 * @param point
	 *            The member's point
	 */
	public Member(Date birthDay, String firstName, String lastName, String country, int point) {
		this(null, birthDay, firstName, lastName, country, point);
	}

	/**
	 * Create a member with optional id and birthday
	 * 
	 * @param id
	 *            The member's unique identifier (optional) - if null then one
	 *            will be generated
	 * @param birthDay
	 *            The member's birthday (optional)
	 * @param firstName
	 *            The member's first name
	 * @param lastName
	 *            The member's last name
	 * @param country
	 *            The member's country
	 * @param point
	 *            The member's point
	 */
	public Member(String id, Date birthDay, String firstName, String lastName, String country, int point) {
		if (id == null) {
			setId(UUID.randomUUID().toString());
		} else {
			setId(id);
		}

		setBirthDay(birthDay);
		setFirstName(firstName);
		setLastName(lastName);
		setCountry(country);
		setPoint(point);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String title) {
		this.firstName = title;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String description) {
		this.lastName = description;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String toString() {
		return id + ": " + firstName;
	}
}
