package aphi.constant.system;

public enum RDB_CONF {
	
	//Servlet config init parameter name for the RDB driver class
	DRIVER_CLASS("RdbDriverClass"),

	//Servlet config init parameter name for the RDB connection URL
	URL("RdbUrl"),

	//Servlet config init parameter name for the RDB connection user id
	USER_NAME("RdbUserName"),

	//Servlet config init parameter name for the RDB connection password
	USER_PASSWORD("RdbUserPassword"),
	
	;

	private String value;

	private RDB_CONF(String value) {
		this.value = value;
	}

	public String val() {
		return value;
	}
}
