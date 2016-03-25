package aphi.constant.system;

public enum SERVLET_PATH {

	// Mapped URL for the Member List servlet
	MEMBER_LIST("/member/list"),

	// Mapped URL for the Member Edit servlet
	MEMBER_EDIT("/member/edit"),

	// Mapped URL for the Member Update servlet
	MEMBER_UPDATE("/member/update"),

	// Mapped URL for the Member Delete servlet
	MEMBER_DELETE("/member/delete"),

	// Mapped URL for the Member Add servlet
	MEMBER_ADD("/member/add"),

	// Mapped URL for the User Login servlet
	USER_LOGIN("/user/login"),

	// Mapped URL for the User Logout servlet
	USER_LOGOUT("/user/logout"),

	// Mapped URL for the User Create servlet
	USER_CREATE("/user/create"),

	;

	private String value;

	private SERVLET_PATH(String value) {
		this.value = value;
	}

	public String val() {
		return value;
	}
}
