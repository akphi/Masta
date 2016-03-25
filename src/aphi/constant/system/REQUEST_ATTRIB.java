package aphi.constant.system;

public enum REQUEST_ATTRIB {

	// Request attribute for the user's login name
	USER_LOGIN_NAME("requestAttribUserName"),

	// Request attribute for the member list
	MEMBER_LIST("requestAttribMemberList"),

	// Request attribute for the member id
	MEMBER_ID("requestAttribId"),

	// Request attribute for the member birthday
	MEMBER_BIRTHDAY("requestAttribBirthDay"),

	// Request attribute for the member first name
	MEMBER_FIRSTNAME("requestAttribFirstName"),
	
	// Request attribute for the member last name
	MEMBER_LASTNAME("requestAttribLastName"),

	// Request attribute for the member country
	MEMBER_COUNTRY("requestAttribCountry"),
	
	// Request attribute for the member point
	MEMBER_POINT("requestAttribPoint"),
		
	;

	private String value;

	private REQUEST_ATTRIB(String value) {
		this.value = value;
	}

	public String val() {
		return value;
	}
}
