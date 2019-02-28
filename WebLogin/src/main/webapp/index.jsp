<!DOCTYPE html>
<%@page import="java.util.List"%>
<%@page import="org.java.login.model.Factura"%>
<%@page import="org.java.login.model.User"%>
<%@page import="java.text.SimpleDateFormat"%>
<html>
<head>
<script type="text/javascript" src="js/metodos.js"></script>
<link rel="stylesheet" type="text/css" href="styles/styles.css">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.7.1/css/all.css"
	integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr"
	crossorigin="anonymous">
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
					<button type="submit" id="login" name="login">
						<i class="fas fa-vote-yea"></i> Entrar
					</button>
			</form>
		</div>
		<%
			} else {
		%>
		<p>
		<div id="menu" name="menu" class=menu align=center>
			<p>
				<button onclick="add()" class=btnMenu>
					<i class="fas fa-plus-square"></i>
				</button>
			<p>
				<button onclick="con()" class=btnMenu>
					<i class="fas fa-search-plus"></i>
				</button>
			<p>
				<button onclick="adm()" class=btnMenu>
					<i class="fas fa-tools"></i>
				</button>
		</div>
		<%
			if (request.getAttribute("respuesta") != null) {
		%>
		<div id="divRespuesta" name="divRespuesta" class=divRespuesta>
			<table border='1px' width='95%' class='paleBlueRows'>
				<tr>
					<td><h2><%=request.getAttribute("respuesta")%></h2></td>
				</tr>
			</table>
		</div>
		<%
			}
		%>
		<div id="divCon" name="divCon" class=divCon>
			<form name="formConsulta" id="idFormConsulta" action="/"
				method="post">
				<table border='1px' width='95%' class='paleBlueRows'>
					<thead>
						<tr>
							<th>Tipo Factura</th>
							<th>Fecha Inicio</th>
							<th>Fecha Fin</th>
							<th>Estado</th>
						</tr>
					</thead>
					<tr>
						<td><select name="conTipo" id="idConTipo"
							placeholder="Factura" selectedIndex="2">
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

						</select></td>
						<td><input type="date" name="conFecIni" id="idConFecIni"
							placeholder="Fecha Ini"
							value="<%=request.getAttribute("conFecIni")%>" /></td>
						<td><input type="date" name="conFecFin" id="idConFecFin"
							placeholder="Fecha Fin"
							value="<%=request.getAttribute("conFecFin")%>" /></td>
						<td><select name="conEstado" id="idConEstado"
							placeholder="Estado">
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
						</select></td>
					</tr>
				</table>
				<input type='hidden' name='cancelar' id='cancelar' value="" />
				<p>
					<button type="submit">
						<i class="fas fa-search"></i>
					</button>
					<button type="submit" onclick="setCancel()">
						<i class="fas fa-minus-circle"></i>
					</button>
			</form>
			<form name="formTabla" id="idFormTabla" action="/" method="post">
				<%
					if (request.getAttribute("listaResultados") != null) {
							SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
							out.print("");
							List<Factura> lista = (List<Factura>) request.getAttribute("listaResultados");
							out.println("<table border='1px' width='100%' class='paleBlueRows'>");
							out.println(
									"<thead><tr><th>Tipo</th><th>Fecha Inicio</th><th>Fecha Fin</th><th>Importe</th><th>Pagado</th><th>Eliminar</th></tr></thead>");
							int i = 0;
							for (Factura r : lista) {
								String src = "";
								if (r.getEstado() == 1) {
									src = "<i class='fas fa-info-circle'></i>";
								} else {
									src = "<i class='fas fa-exclamation-triangle'></i>";
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
										+ " Euros</td>" + "<td>" + "<input type='hidden' name='utipoFac' id='utipoFac'/>"
										+ "<input type='hidden' name='ufecIni' id='ufecIni'/>"
										+ "<input type='hidden' name='ufecFin' id='ufecFin'/>"
										+ "<input type='hidden' name='uestado' id='uestado'/>"
										+ "<button type='submit' onclick=\"changeValue('C','" + r.getId() + "')\">" + src
										+ "</button></td>" + "<td><input type='checkbox'name='checkMod" + i + "' id='idCheckMod"
										+ i + "' value='D@@" + r.getId() + "' /></td>");
								out.println("</tr>");
								i++;
							}

							if (!lista.isEmpty()) {
								out.print("<input type='hidden' name='inputCon' id='inputCon'/>");
								out.print("<input type='hidden' name='idFac' id='idFac'/>");
								out.println(
										"<tr><td></td><td></td><td></td><td></td><td></td><td><button type='submit' onclick=\"changeValue('M','')\"><i class='fas fa-trash-alt'></i></button></td></tr>");
							}
							out.println("</table>");

						}
				%>
			</form>
		</div>
		<div id="divAdd" name="divAdd" class=divAdd>
			<form name="formAdd" id="idFormAdd" action="/" method="post">
				<table border='1px' width='95%' class='paleBlueRows'>
					<thead>
						<tr>
							<th>Tipo Factura</th>
							<th>Fecha Inicio</th>
							<th>Fecha Fin</th>
							<th>Importe</th>
							<th>Pagado</th>
						</tr>
					</thead>
					<tr>
						<td><select name="addTipo" id="idAddTipo"
							placeholder="Factura" required>
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
						</select></td>
						<td><input type="date" name="addFecIni" id="idAddFecIni"
							placeholder="Fecha Ini"
							value="<%=request.getAttribute("addFecIni")%>" required /></td>
						<td><input type="date" name="addFecFin" id="idAddFecFin"
							placeholder="Fecha Fin"
							value="<%=request.getAttribute("addFecFin")%>" required /></td>
						<td><input class="addimporte" type="number" width="70%"
							name="addImporte" step=".01" id="idAddImporte"
							value="<%=request.getAttribute("addImporte")%>" required /><label>
								Eur</label></td>
						<td><select name="addEstado" id="idAddEstado"
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
						</select></td>
					</tr>
				</table>
				<div id="botonesAdd" name="botonesAdd">
					<p>
						<button type="submit" onclick="next('S')" id="btnContinuar">
							<i class="fas fa-arrow-circle-right"></i> Continuar
						</button>
						<button type="reset" id="btnContinuarBorrar">
							<i class="fas fa-backspace"> Borrar</i>
						</button>
				</div>
				<div name="divAddUser" id="divAddUser" class="divAddUser">
					<p>
						<label>Selecciona los usuarios que deben pagar la factura
						</label>
					<p>

						<%
							out.println("<table border='1px' width='100%' class='paleBlueRows'>");
								out.println("<thead><tr><th>Nombre</th><th>Selecciona</th></tr></thead>");
								if (request.getAttribute("listaUser") != null) {
									List<User> listaUser = (List<User>) request.getAttribute("listaUser");
									int i = 0;
									for (User user : listaUser) {
										String checkUser = "<td><input type='checkbox'name='checkUser" + i + "' id='idCheckUser" + i
												+ "' value='U@@" + user.getName() + "' /></td>";
										out.print("<tr><td>" + user.getName() + "</td>" + checkUser + "</tr>");
										i++;
									}

								}
								out.print("</table>");
						%>
					
					<p>
						<label>(opcional) Imagen de la factura (*Url Externa)</label>
					<p align="center">
						<input id=idAddImg name=addImg />
					<p>
						<input type="hidden" name="continuar" id="continuar">
						<button type="submit" onclick="next('N')">
							<i class="fas fa-save"></i> Aceptar
						</button>
						<button type="submit" onclick="next('B')">
							<i class="fas fa-backspace"></i> Cancelar
						</button>
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
