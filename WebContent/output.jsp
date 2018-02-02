<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="com.outputVariables"
    %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
outputVariables variables=(outputVariables)request.getAttribute("variables");%>
<form action="Controller" method="post">
Latitude:  <input type="text" name="latitude" id="latitude" value="<%=variables.getLat() %>"><br>
Longitude: <input type="text" name="longitude" id="longitude" value="<%=variables.getLon() %>"><br>
<input type="submit" value="Refresh"><br><br>
Minimum Temperature : <input type="text" value="<%=variables.getMint() %>"><br>
Wind Speed : <input type="text" value="<%=variables.getWspd() %>"><br>
Wind Direction : <input type="text" value="<%=variables.getWdir()%>"><br>
Probability Of Precipitation : <input type="text" value="<%=variables.getPop12() %>"><br>
</form>
</body>
</html>