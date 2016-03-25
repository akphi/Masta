<!-- badrequest -->
<%
response.setStatus(400);
%>
<html>

<head>
<meta charset="utf-8">
<meta content="text/html" http-equiv="Content-Type">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
<title>Bad Request</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
</head>

<body bgcolor="#f0a0a0"
	onload="setTimeout(function(){document.location = '<%=request.getContextPath()%>'}, 10000)">

	<div style="margin-left: 10px">
		<h1>
			Page not found! You are being redirected in <span id="counter">10</span> seconds
		</h1>
		<div>
			You can <a href="<%=request.getContextPath()%>">click here</a> instead of waiting
		</div>
	</div>
	<script type="text/javascript">
			function countdown() {
				var i = document.getElementById('counter');
				if (parseInt(i.innerHTML) <= 0) {
					location.href = '<%=request.getContextPath()%>';
				}
				i.innerHTML = parseInt(i.innerHTML) - 1;
			}
			setInterval(function() {
				countdown();
			}, 1000);
		</script>
</body>

</html>
