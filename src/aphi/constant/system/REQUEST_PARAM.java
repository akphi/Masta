package aphi.constant.system;

public enum REQUEST_PARAM {

	// Request parameter for the user login name
	USER_LOGIN_NAME("loginName"),

	// Request parameter for the user login password
	USER_LOGIN_PASSWORD("loginPassword"),

	// Request parameter for the member id
	MEMBER_ID("paramId"),
	
	// Request parameter for the member birthday
	MEMBER_BIRTHDAY("paramBirthDay"),

	// Request parameter for the member first name
	MEMBER_FIRSTNAME("paramFirstName"),
	
	// Request parameter for the member last name
	MEMBER_LASTNAME("paramLastName"),

	// Request parameter for the member country
	MEMBER_COUNTRY("paramCountry"),

	// Request parameter for the member point
	MEMBER_POINT("paramPoint"),

	;

	private String value;

	private REQUEST_PARAM(String value) {
		this.value = value;
	}

	public String val() {
		return value;
	}
}
