package aphi.constant.system;

public enum JSP_PATH {
	
	// JSP for the user login page
	USER_LOGIN("/WEB-INF/hiddenjsp/User/Login.jsp"),

	// JSP for the user create page
	USER_CREATE("/WEB-INF/hiddenjsp/User/Create.jsp"),

	// JSP for the member list page
	MEMBER_LIST("/WEB-INF/hiddenjsp/Member/List.jsp"),

	// JSP for the member edit page
	MEMBER_EDIT("/WEB-INF/hiddenjsp/Member/Edit.jsp"),
	
	;

	private String value;

	private JSP_PATH(String value) {
		this.value = value;
	}

	public String val() {
		return value;
	}
}
