<!-- Member List -->
<%@ page import="java.util.List"%>
<%@ page import="aphi.constant.system.*"%>
<%@ page import="aphi.servlet.tool.StringUtilities"%>
<%@ page import="aphi.bean.Member"%>
<%@ page import="org.owasp.encoder.Encode"%>

<%
	@SuppressWarnings("unchecked")
	List<Member> memberList = (List<Member>) session.getAttribute(SESSION_ATTRIB.MEMBER_LIST.val());
%>

<html>

<head>
<title>Member List</title>
</head>

<body bgcolor="#a0f0f0" onunload="">
	<h1>Member List</h1>

	<h2>Current Member List</h2>

	<%
		if (memberList.size() > 0) {
	%>
	<table border="1">
		<tr>
			<th>Name</th>
			<th>Birthday</th>
			<th>Country</th>
			<th>Point</th>
			<th colspan="2">Operations</th>
		</tr>
		<%
			for (Member member : memberList) {
		%>
		<tr>
			<td><%=member.getLastName()%>, <%=member.getFirstName()%></td>
			<td><%=StringUtilities.formatDate(member.getBirthDay())%></td>
			<td><%=Encode.forHtml(member.getCountry())%></td>
			<td><%=member.getPoint()%></td>
			<td><form method="POST"
					action="<%=request.getContextPath() + SERVLET_PATH.MEMBER_EDIT.val()%>">
					<input type="hidden" name="<%=REQUEST_PARAM.MEMBER_ID.val()%>" value="<%=member.getId()%>" /> <input type="submit"
						value="Edit" />
				</form></td>
			<td><form method="POST"
					action="<%=request.getContextPath() + SERVLET_PATH.MEMBER_DELETE.val()%>">
					<input type="hidden" name="<%=REQUEST_PARAM.MEMBER_ID.val()%>" value="<%=member.getId()%>" /> <input type="submit"
						value="Delete" />
				</form></td>
				<td><form method="POST"
					action="<%=request.getContextPath() + SERVLET_PATH.MEMBER_EDIT.val()%>">
					<input type="hidden" name="<%=REQUEST_PARAM.MEMBER_ID.val()%>" value="<%=member.getId()%>" /> <input type="submit"
						value="Edit" />
				</form></td>

		</tr>
		<%
			}
		%>
	</table>
	<%
		} else {
	%>
	<font size="+1">Add more members</font>
	<%
		}
	%>

	<hr />

	<h2>Session Control</h2>
	<p>Session id: <%=session.getId()%></p>
	<form method="POST" action="<%=request.getContextPath() + SERVLET_PATH.USER_LOGOUT.val()%>">
		<input type="submit" value="Logout" />
	</form>

	<hr />

	<h2>Add a New Task</h2>

	<form method="POST" action="<%=request.getContextPath() + SERVLET_PATH.MEMBER_ADD.val()%>">
		<table border="1">
			<tr>
				<td>First Name</td>
				<td><input type="text" name="<%=REQUEST_PARAM.MEMBER_FIRSTNAME.val()%>" /></td>
			</tr>
			<tr>
				<td>Last Name</td>
				<td><input type="text" name="<%=REQUEST_PARAM.MEMBER_LASTNAME.val()%>" /></td>
			</tr>
			<tr>
				<td>Birthday</td>
				<td><input type="text" name="<%=REQUEST_PARAM.MEMBER_BIRTHDAY.val()%>" /></td>
			</tr>
			<tr>
				<td>Country</td>
				<td><input type="text" name="<%=REQUEST_PARAM.MEMBER_COUNTRY.val()%>" /></td>
			</tr>
			<tr>
				<td>Point</td>
				<td><input type="text" name="<%=REQUEST_PARAM.MEMBER_POINT.val()%>" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Add Task" />
					<button type="reset">Reset Form</button></td>
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
