<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
//to validate lat and lon fields
function validate(){
	var a=document.getElementById("latitude").value;
	var b=document.getElementById("longitude").value;
	if(a==""||b=="")
		{
			alert('latitude and logitude cant be null');
			return false;
		}
	return true;
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="Controller" method="post" onsubmit="return validate()">
Latitude:  <input type="text" name="latitude" id="latitude"><br>
Longitude: <input type="text" name="longitude" id="longitude"><br>
<input type="submit" value="submit">
</form>
</body>
</html>