<%@ page language="java" contentType="text/html;"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;">
<title>Insert title here</title>
</head>
<body>
	<input id="inp" type='file'>
	<p id="b64"></p>
	<br>
	<br>
	<br>
	<br>
	<img id="img" height="150">
</body>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<script type="text/javascript">
	function readFile() {
		if (this.files && this.files[0]) {
			var FR = new FileReader();
			FR.addEventListener("load", function(e) {
				document.getElementById("img").src = e.target.result;
				document.getElementById("b64").innerHTML = e.target.result;
				callServlet(e.target.result);
			});
			FR.readAsDataURL(this.files[0]);
			console.log("image size=" + this.files[0].size);
		}
	}
	document.getElementById("inp").addEventListener("change", readFile);
	function callServlet(SRCimg) {
		var javaRoot = "${pageContext.servletContext.contextPath}";
		var myjsondata = JSON.stringify({img : SRCimg});
		
		$.ajax({
			url : javaRoot + '/getimageServlet',
			type : "POST",
			data : myjsondata,
			contentType : "application/json"
		});
		console.log("Json size=" + Object.keys(myjsondata).length);
		console.log("02callServlet----------------------");
	}
</script>
</html>