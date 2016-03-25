package aphi.constant.system;

public enum SESSION_ATTRIB {

	// Session attribute for the member list
	MEMBER_LIST("sessionAttribMemberList"),

	// Session attribute for the user's internal id
	USER("sessionAttribUser"),

	;

	private String value;

	private SESSION_ATTRIB(String value) {
		this.value = value;
	}

	public String val() {
		return value;
	}
}
