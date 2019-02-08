<!DOCTYPE html>
<%@page import="java.util.List"%>
<%@page import="org.java.login.model.Factura"%>
<%@page import="java.text.SimpleDateFormat"%>
<html>
<head>
<script type="text/javascript" src="js/metodos.js"></script>
<link rel="stylesheet" type="text/css" href="styles/styles.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Contabilidad</title>
</head>
<body>
	<div id="titulo" align="center">
		<img src="arriba.png">
	</div>
	<div class=cuerpo align=center>
		<%
			if (request.getAttribute("logUser") != null) {
		%>
		<form name="consultaFactura" id="ConsultaFactura" action="/"
			method="post">
			<input type="hidden" name="inputCon" id="inputCon" value="U">
			<input type='hidden' name='utipoFac' id='utipoFac' value="<%=request.getAttribute("utipoFac") %>" /> <input
				type='hidden' name='ufecIni' id='ufecIni' value="<%=request.getAttribute("ufecIni") %>" /> <input type='hidden'
				name='ufecFin' id='ufecFin' value="<%=request.getAttribute("ufecFin") %>"/> <input type='hidden' name='uestado'
				id='uestado' value="<%=request.getAttribute("uestado") %>" /> <button type="submit" name="aceptar" id="aceptar"> Aceptar </button>

		</form>

		<%
			}
		%>

	</div>
</body>
</html>
