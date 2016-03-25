<!-- login -->
<%@ page import="aphi.constant.system.*"%>
<%
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP	1.1
	response.setHeader("Pragma", "no-cache"); // HTTP 1.0
	response.setDateHeader("Expires", 0); // Proxies.
%>

<html>
<head>
<title>Login Form</title>
</head>

<body bgcolor="#f0a0a0" onunload="">
	<h1>Tasklist Login</h1>
	<form method="POST" action="<%=request.getContextPath() + SERVLET_PATH.USER_LOGIN.val()%>">
		<table border="1">
			<tr>
				<td>User Id</td>
				<td><input type="text" name="<%=REQUEST_PARAM.USER_LOGIN_NAME.val()%>"
					value="<%=request.getAttribute(REQUEST_ATTRIB.USER_LOGIN_NAME.val())%>" /></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><input type="password" name="<%=REQUEST_PARAM.USER_LOGIN_PASSWORD.val()%>" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Login" /></td>
			</tr>
		</table>
	</form>
</body>

<script>
	window.onpageshow = function(event) {
		if (event.persisted) {
			window.location.reload()
		}
	};
</script>
</html>
