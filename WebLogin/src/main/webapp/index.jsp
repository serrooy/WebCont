<!DOCTYPE html>
<%@page import="java.util.List"%>
<%@page import="org.java.login.model.Factura"%>
<%@page import="org.java.login.model.User"%>
<%@page import="java.text.SimpleDateFormat"%>
<html>
<head>
<script type="text/javascript" src="js/metodos.js"></script>
<link rel="stylesheet" type="text/css" href="styles/styles.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Contabilidad</title>
</head>
<body onload="controlGeneral('${ventana}')">
	<div id="titulo" align="center">
		<img src="arriba.png">
	</div>
	<div class=cuerpo align=center>
		<%
			if (request.getAttribute("logUser") == null) {
		%>
		<p>
		<div class=login align="center">
			<p>
			<form name="formLogin" id="formLogin" action="/" method="post">
				<p>
					<input type="text" placeholder="Enter Username" id="uname"
						name="uname" required><input type="password"
						placeholder="Enter Password" id="psw" name="psw" required>
				<p>
					<button type="submit" id="login" name="login">Login</button>
			</form>
		</div>
		<%
			} else {
		%>
		<p>
		<div id="menu" name="menu" class=menu align=center>
			<p>
				<img src="menu.png">
			<p>
				<button onclick="add()" class=btnMenu>Nuevo</button>
			<p>
				<button onclick="con()" class=btnMenu>Consulta</button>
			<p>
				<button onclick="adm()" class=btnMenu>Administrar</button>
		</div>
		<%
			if (request.getAttribute("respuesta") != null) {
		%>
		<div id="divRespuesta" name="divRespuesta" class=divRespuesta>
			<h2><%=request.getAttribute("respuesta")%>
		</div>
		<%
			}
		%>
		<div id="divCon" name="divCon" class=divCon>
			<form name="formConsulta" id="idFormConsulta" action="/"
				method="post">
				<select name="conTipo" id="idConTipo" placeholder="Factura"
					selectedIndex="2">
					<%
						if (request.getAttribute("conTipo") != null) {
								String tfac = (String) request.getAttribute("conTipo");
								if (tfac.equals("L")) {
									out.print("<option value='T'>Todos</option>");
									out.print("<option value='L' selected>Luz</option>");
									out.print("<option value='A'>Agua</option>");
									out.print("<option value='G'>Gas</option>");
								} else if (tfac.equals("A")) {
									out.print("<option value='T'>Todos</option>");
									out.print("<option value='L'>Luz</option>");
									out.print("<option value='A' selected>Agua</option>");
									out.print("<option value='G'>Gas</option>");
								} else if (tfac.equals("G")) {
									out.print("<option value='T'>Todos</option>");
									out.print("<option value='L'>Luz</option>");
									out.print("<option value='A'>Agua</option>");
									out.print("<option value='G' selected>Gas</option>");
								} else {
									out.print("<option value='T' selected>Todos</option>");
									out.print("<option value='L'>Luz</option>");
									out.print("<option value='A'>Agua</option>");
									out.print("<option value='G'>Gas</option>");

								}
							} else {
					%>
					<option value="T">Todos</option>
					<option value="L">Luz</option>
					<option value="A">Agua</option>
					<option value="G">Gas</option>

					<%
						}
					%>

				</select> <input type="date" name="conFecIni" id="idConFecIni"
					placeholder="Fecha Ini"
					value="<%=request.getAttribute("conFecIni")%>" /> <input
					type="date" name="conFecFin" id="idConFecFin"
					placeholder="Fecha Fin"
					value="<%=request.getAttribute("conFecFin")%>" /> <select
					name="conEstado" id="idConEstado" placeholder="Estado">
					<%
						if (request.getAttribute("conEstado") != null) {
								String conE = (String) request.getAttribute("conEstado");
								if (conE.equals("0")) {
									out.print("<option value='T'>Todos</option>");
									out.print("<option value='N' selected>No pagado</option>");
									out.print("<option value='S'>Pagado</option>");
								} else if (conE.equals("1")) {
									out.print("<option value='T'>Todos</option>");
									out.print("<option value='N'>No pagado</option>");
									out.print("<option value='S' selected>Pagado</option>");
								} else {
									out.print("<option value='T'selected>Todos</option>");
									out.print("<option value='N'>No pagado</option>");
									out.print("<option value='S' selected>Pagado</option>");
								}
							} else {
					%>
					<option value="T">Todos</option>
					<option value="S">Pagado</option>
					<option value="N">No Pagado</option>
					<%
						}
					%>
				</select>
				<p>
					<button type="submit">Buscar</button>
					<button type="reset">Borrar</button>
			</form>
			<form name="formTabla" id="idFormTabla" action="/" method="post">
				<%
					if (request.getAttribute("listaResultados") != null) {
							SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
							out.print("");
							List<Factura> lista = (List<Factura>) request.getAttribute("listaResultados");
							out.println("<table border='1px' width='100%' class='paleBlueRows'>");
							out.println(
									"<thead><tr><th>Tipo</th><th>Fecha Inicio</th><th>Fecha Fin</th><th>Importe</th><th>Pagado</th><th>Eliminar</th><th>Pagar</th></tr></thead>");
							int i = 0;
							for (Factura r : lista) {
								String checkPagar = "<td><input type='checkbox'name='checkPagar" + i + "' id='idCheckPagar" + i
										+ "' value='P@@" + r.getId() + "' /></td>";
								String src = "";
								if (r.getEstado() == 1) {
									src = "styles/S.png";
								} else {
									src = "styles/N.png";
								}
								out.println("<tr>");

								String factura = "";
								if (r.getFactura().equals("L")) {
									factura = "Luz";
								} else if (r.getFactura().equals("G")) {
									factura = "Gas";
								} else {
									factura = "Agua";
								}

								out.println("<td>" + factura + "</td>" + "<td>" + formatter.format(r.getFecIni()) + "</td>"
										+ "<td>" + formatter.format(r.getFecFin()) + "</td>" + "<td>" + r.getImporte()
										+ " Euros</td>" + "<td><img src='" + src + "' height='25' width='25'/></td>"
										+ "<td><input type='checkbox'name='checkMod" + i + "' id='idCheckMod" + i
										+ "' value='D@@" + r.getId() + "' /></td>");
								if (r.getEstado() == 0) {
									out.println(checkPagar);
								} else {
									out.print("<td></td>");
								}
								out.print("<td><input type='hidden' name='idFac' id='idFac' value='" + r.getId() + "'/>"
										+ "<input type='hidden' name='utipoFac' id='utipoFac'/>"
										+ "<input type='hidden' name='ufecIni' id='ufecIni'/>"
										+ "<input type='hidden' name='ufecFin' id='ufecFin'/>"
										+ "<input type='hidden' name='uestado' id='uestado'/>"
										+ "<button type='submit' onclick=\"changeValue('C')\">Detalles</button></td>");
								out.println("</tr>");
								i++;
							}
							out.println("</table>");
							out.print("<input type='hidden' name='inputCon' id='inputCon'/>");
							out.println("<button type='submit' onclick=\"changeValue('M')\">Aplicar cambios</button>");

						}
				%>
			</form>
		</div>
		<div id="divAdd" name="divAdd" class=divAdd>
			<form name="formAdd" id="idFormAdd" action="/" method="post">
				<select name="addTipo" id="idAddTipo" placeholder="Factura" required>
					<%
						if (request.getAttribute("addTipo") != null) {
								String tfac = (String) request.getAttribute("addTipo");
								if (tfac.equals("L")) {

									out.print("<option value='L' selected>Luz</option>");
									out.print("<option value='A'>Agua</option>");
									out.print("<option value='G'>Gas</option>");
								} else if (tfac.equals("A")) {

									out.print("<option value='L'>Luz</option>");
									out.print("<option value='A' selected>Agua</option>");
									out.print("<option value='G'>Gas</option>");
								} else if (tfac.equals("G")) {

									out.print("<option value='L'>Luz</option>");
									out.print("<option value='A'>Agua</option>");
									out.print("<option value='G' selected>Gas</option>");
								}
							} else {
					%>

					<option value="L">Luz</option>
					<option value="A">Agua</option>
					<option value="G">Gas</option>

					<%
						}
					%>
				</select> <input type="date" name="addFecIni" id="idAddFecIni"
					placeholder="Fecha Ini"
					value="<%=request.getAttribute("addFecIni")%>" required /> <input
					type="date" name="addFecFin" id="idAddFecFin"
					placeholder="Fecha Fin"
					value="<%=request.getAttribute("addFecFin")%>" required /> <input
					type="number" name="addImporte" step=".01" id="idAddImporte"
					value="<%=request.getAttribute("addImporte")%>" required /><label>
					Euros</label> <select name="addEstado" id="idAddEstado"
					placeholder="Estado" required>
					<%
						if (request.getAttribute("addEstado") != null) {
								String conE = (String) request.getAttribute("addEstado");
								if (conE.equals("N")) {
									out.print("<option value='N' selected>No pagado</option>");
									out.print("<option value='S'>Pagado</option>");
								} else if (conE.equals("S")) {
									out.print("<option value='N'>No pagado</option>");
									out.print("<option value='S' selected>Pagado</option>");
								}
							} else {
					%>
					<option value="N">No Pagado</option>
					<option value="S">Pagado</option>
					<%
						}
					%>
				</select>
				<div id="botonesAdd" name="botonesAdd">
				<p>
					<button type="submit" onclick="next('S')" id="btnContinuar">Continuar</button>
					<button type="reset" id="btnContinuarBorrar">Borrar</button>
					</div>
				<div name="divAddUser" id="divAddUser" class="divAddUser">
					<p>
						<label>Selecciona los usuarios que deben pagar la factura
						</label>
					<p>
					
							<%
							out.println("<table border='1px' width='100%' class='paleBlueRows'>");
							out.println(
									"<thead><tr><th>Nombre</th><th>Selecciona</th></tr></thead>");
								if (request.getAttribute("listaUser") != null) {
									out.pr
										List<User> listaUser = (List<User>) request.getAttribute("listaUser");
										for (User user : listaUser) {
											out.print("<option value='" + user.getName() + "'>" + user.getName() + "</option>");
										}
									}
							%>

			
						<p>
				 <input type="hidden" name="continuar" id="continuar">
						<button type="submit" onclick="next('N')">Aceptar</button>
						<button type="submit" onclick="next('B')">Cancelar</button>
			</form>


			<%
				if (request.getParameter("listaResultados") != null) {
						String x = request.getParameter("listaResultados");
			%><p><%=x%>
				<%
					}
				%>
			
		</div>
		<div id="divAdm" name="divAdm" class="divAdm">DIV adm</div>
		<p>
			<%
				}
			%>
		
	</div>



</body>
</html>
