<!-- Member Edit -->
<%@ page import = "aphi.constant.system.*"%>
	
<html>

	<head>
		<title>Member Edit</title>
	</head>
	
	<body bgcolor="#a0a0f0">
		<h1>Member Edit</h1>
		
		<form method="POST" action="<%=request.getContextPath() + SERVLET_PATH.MEMBER_UPDATE.val()%>">
			<input type="hidden" name="<%=REQUEST_PARAM.MEMBER_ID.val()%>" 
					value="<%=request.getAttribute(REQUEST_ATTRIB.MEMBER_ID.val())%>"/>
			<table border="1">
				<tr>
					<td>First Name</td>
					<td><input type="text" name="<%=REQUEST_PARAM.MEMBER_FIRSTNAME.val()%>" 
						value="<%=request.getAttribute(REQUEST_ATTRIB.MEMBER_FIRSTNAME.val())%>"/></td>
				</tr>
				<tr>
					<td>Last Name</td>
					<td><input type="text" name="<%=REQUEST_PARAM.MEMBER_LASTNAME.val()%>" 
						value="<%=request.getAttribute(REQUEST_ATTRIB.MEMBER_LASTNAME.val())%>"/></td>
				</tr>
				<tr>
					<td>Birthday</td>
					<td><input type="text" name="<%=REQUEST_PARAM.MEMBER_BIRTHDAY.val()%>" 
						value="<%=request.getAttribute(REQUEST_ATTRIB.MEMBER_BIRTHDAY.val())%>"/></td>
				</tr>
				<tr>
					<td>Country</td>
					<td><input type="text" name="<%=REQUEST_PARAM.MEMBER_COUNTRY.val()%>" 
						value="<%=request.getAttribute(REQUEST_ATTRIB.MEMBER_COUNTRY.val())%>"/></td>
				</tr>
				<tr>
					<td>Point</td>
					<td><input type="text" name="<%=REQUEST_PARAM.MEMBER_POINT.val()%>" 
						value="<%=request.getAttribute(REQUEST_ATTRIB.MEMBER_POINT.val())%>"/></td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" value="Update Detail"/>
						<button type="reset">Reset</button>
						<a href="<%=request.getContextPath() + SERVLET_PATH.MEMBER_LIST.val()%>">Cancel</a>
					</td>
				</tr>
			</table>
		</form>			
	</body>
</html>
