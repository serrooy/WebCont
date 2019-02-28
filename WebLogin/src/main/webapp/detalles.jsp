<!DOCTYPE html>
<%@page import="java.util.ArrayList"%>
<%@page import="org.hibernate.result.Output"%>
<%@page import="java.util.List"%>
<%@page import="org.java.login.model.Factura"%>
<%@page import="org.java.login.model.DetUser"%>
<%@page import="java.text.SimpleDateFormat"%>
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.7.1/css/all.css"
	integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr"
	crossorigin="anonymous">
<script type="text/javascript" src="js/metodos.js"></script>
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
			<input type="hidden" name="inputCon" id="inputCon" value="C">
			<input type="hidden" name="usuario" id="usuario" value="0"> <input
				type="hidden" name="inputDetCon" id="inputDetCon"> <input
				type='hidden' name='utipoFac' id='utipoFac'
				value="<%=request.getAttribute("utipoFac")%>" /> <input
				type='hidden' name='ufecIni' id='ufecIni'
				value="<%=request.getAttribute("ufecIni")%>" /> <input
				type='hidden' name='ufecFin' id='ufecFin'
				value="<%=request.getAttribute("ufecFin")%>" /> <input
				type='hidden' name='uestado' id='uestado'
				value="<%=request.getAttribute("uestado")%>" /> <input
				type='hidden' name=uidFac id='uidFac'
				value="<%=request.getAttribute("uidFac")%>" />
			<h2>Detalles de la Factura</h2>
			<table border='1px' width='90%' class='paleBlueRows'>
				<thead>
					<tr>
						<th>Tipo de Factura</th>
						<th>Fecha Inicio</th>
						<th>Fecha Fin</th>
						<th>Importe</th>
						<th>Pagado</th>
						<th>Adjunto</th>
				</thead>
				<%
					SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
						if (request.getAttribute("factura") != null) {

							Factura f = (Factura) request.getAttribute("factura");
							String src = "";
							if (f.getEstado() == 1) {
								src = "<span><i class='fas fa-thumbs-up'></i></span>";
							} else {
								src = "<span><i class='fas fa-thumbs-down'></i></span>";
							}

							String fac = "";
							if (f.getFactura().equalsIgnoreCase("L")) {
								fac = "Luz";
							} else if (f.getFactura().equalsIgnoreCase("A")) {
								fac = "Agua";
							} else {
								fac = "Gas";
							}

							out.print("<tr>");
							out.print("<td>" + fac + "</td><td>" + formatter.format(f.getFecIni()) + "</td><td>"
									+ formatter.format(f.getFecFin()) + "</td><td>" + f.getImporte() + " Eur</td><td>" + src
									+ "</td><td><button><i class='fas fa-file-image'></i></button></td>");

							out.print("</tr>");
						}
				%>
			</table>
			<p>
			<h2>Detalle de usuarios</h2>
			<table border='1px' width='50%' class='paleBlueRows'>
				<thead>
					<tr>
						<th>Nombre</th>
						<th>Pagado</th>
						<th>Acciones</th>
				</thead>

				<%
					Integer modoConsulta = (Integer) request.getAttribute("onlyRead");
						boolean disabled = false;
						if (modoConsulta != null && modoConsulta == 1) {
							disabled = true;
						}

						if (request.getAttribute("listaUsuarios") != null) {
							List<DetUser> listaUser = (List<DetUser>) request.getAttribute("listaUsuarios");
							for (DetUser det : listaUser) {
								out.print("<tr>");

								out.print("<td>" + det.getName() + "</td>");

								String src = "";
								String pagar="";
								if (det.getEstado() == 1) {
									src = "<span><i class='fas fa-thumbs-up'></i></span>";	
								} else {
									src = "<span><i class='fas fa-thumbs-down'></i></span>";
									pagar="<button type='submit'  onclick=\"changeDetValue('C','P','" + det.getName()
									+ "')\"><i class='far fa-money-bill-alt'></i></button>";
								}

								out.print("<td>" + src + "</td>");
								if (!disabled) {
									out.print("<td>");
									out.print(pagar);
									out.print("<button onclick=\"changeDetValue('C','D','" + det.getName()
											+ "')\" type='submit'><i class='fas fa-user-times'></i></button>");
									out.print("</td>");
								}else{
									out.print("<td></td>");
								}
								out.print("</tr>");

							}

						}
				%>

			</table>
			<p>

				<button type="submit" onclick="changeDetValue('U','V')">
					<i class="fas fa-undo"></i> Aceptar
				</button>
				<%
					if (modoConsulta != null && modoConsulta == 0) {
				%>
				<button type="submit" onclick="changeDetValue('U','D')">
					<i class="fa fa-trash"></i> Eliminar Factura
				</button>
				<%
					}
				%>
			
		</form>

		<%
			}
		%>

	</div>
</body>
</html>
