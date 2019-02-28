function add() {
	var divCon = document.getElementById("divCon");
	divCon.style.display = "none";
	
	var divCon = document.getElementById("botonesAdd");
	divCon.style.display = "block";

	var divAdm = document.getElementById("divAdm");
	divAdm.style.display = "none";
	
	var divAddUser = document.getElementById("divAddUser");
	divAddUser.style.display = "none";

	var divCon = document.getElementById("divRespuesta");
	if (divCon != null)
		divCon.style.display = "none";

	var divAdd = document.getElementById("divAdd");
	divAdd.style.display = 'block';

}
function con() {
	var divAdd = document.getElementById("divAdd");
	divAdd.style.display = "none";

	var divAddUser = document.getElementById("divAddUser");
	divAddUser.style.display = "none";
	
	var divCon = document.getElementById("divAdm");
	divCon.style.display = "none";

	var divCon = document.getElementById("divRespuesta");
	if (divCon != null)
		divCon.style.display = "none";

	var divCon = document.getElementById("divCon");
	divCon.style.display = 'block';
}
function adm() {
	var divAdd = document.getElementById("divAdd");
	divAdd.style.display = "none";
	
	var divAddUser = document.getElementById("divAddUser");
	divAddUser.style.display = "none";

	var divCon = document.getElementById("divRespuesta");
	if (divCon != null)
		divCon.style.display = "none";

	var divCon = document.getElementById("divAdm");
	divCon.style.display = "block";

	var divCon = document.getElementById("divCon");
	divCon.style.display = 'none';
}

function controlGeneral(v) {
	if (v == "0") { // Insertar OK
		var divAdd = document.getElementById("divAdd");
		divAdd.style.display = "none";
		
		var divAddUser = document.getElementById("divAddUser");
		divAddUser.style.display = "none";

		var divCon = document.getElementById("divAdm");
		divCon.style.display = "none";

		var divCon = document.getElementById("divRespuesta");
		if (divCon != null)
			divCon.style.display = "block";

		var divCon = document.getElementById("divCon");
		divCon.style.display = 'none';
	}
	if(v =="0.1"){//Inserta Continuar
		var divAdd = document.getElementById("divAdd");
		divAdd.style.display = "block";
		var divAdd = document.getElementById("divAddUser");
		divAdd.style.display = "block";
		var divCon = document.getElementById("botonesAdd");
		divCon.style.display = "none";
		var divCon = document.getElementById("divAdm");
		divCon.style.display = "none";

		var divCon = document.getElementById("divRespuesta");
		if (divCon != null)
			divCon.style.display = "none";

		var divCon = document.getElementById("divCon");
		divCon.style.display = 'none';
	} 
	
	if(v =="1"){//Insertar KO
		var divAdd = document.getElementById("divAdd");
		divAdd.style.display = "block";
		
		var divAdd = document.getElementById("divAddUser");
		divAdd.style.display = "none";

		var divCon = document.getElementById("divAdm");
		divCon.style.display = "none";

		var divCon = document.getElementById("divRespuesta");
		if (divCon != null)
			divCon.style.display = "none";

		var divCon = document.getElementById("divCon");
		divCon.style.display = 'none';
	} 
	
	if(v =="2"){//Consulta
		var divAdd = document.getElementById("divAdd");
		divAdd.style.display = "none";
		
		var divAdd = document.getElementById("divAddUser");
		divAdd.style.display = "none";

		var divCon = document.getElementById("divAdm");
		divCon.style.display = "none";

		var divCon = document.getElementById("divRespuesta");
		if (divCon != null)
			divCon.style.display = "none";

		var divCon = document.getElementById("divCon");
		divCon.style.display = 'block';
	} 
}

function getLoadCon(tipoFac){
	alert("entro");
	if(tipoFac=="L"){
		document.getElementById("conTipo").selectedIndex=1
	}else
	if(tipoFac=="A"){
		document.getElementById("conTipo").selectedIndex=2
	}else
	if(tipoFac=="G"){
		document.getElementById("conTipo").selectedIndex=3
	}else{
		document.getElementById("conTipo").selectedIndex=0
	}
		
}

function changeValue(valor,vFac){
	document.getElementById("inputCon").value =valor;
	if(vFac!=''){
		document.getElementById("idFac").value = vFac;
	}
	document.getElementById("utipoFac").value = document.getElementById("idConTipo").value;
	document.getElementById("ufecIni").value = document.getElementById("idConFecIni").value;
	document.getElementById("ufecFin").value = document.getElementById("idConFecFin").value;
	document.getElementById("uestado").value = document.getElementById("idConEstado").value;
}

function changeDetValue(valor1,valor,vUser){
	document.getElementById("inputDetCon").value =valor;
	document.getElementById("inputCon").value =valor1;
	document.getElementById("usuario").value =vUser;
}

function next(con){
	document.getElementById("continuar").value=con;
}

function setCancel(){
	document.getElementById("cancelar").value="cancel";
}
