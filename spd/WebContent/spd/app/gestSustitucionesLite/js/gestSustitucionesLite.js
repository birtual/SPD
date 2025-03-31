/////////////////////// funciones para validar fechas //////////////////

// variables globales
var anchoMin = 600;
var altoMin = 500;
var anchoMax = 800; 
var altoMax = 600;


	function procesar(oidGestSustitucionesLite, parameter, actiontodo)
	{
		var f = document.GestSustitucionesLiteForm;
		f.parameter.value=parameter;
		f.ACTIONTODO.value=actiontodo;
		f.oidGestSustitucionesLite.value= oidGestSustitucionesLite;
		
		f.submit();
	}
	
	
	function borrar(oidGestSustitucionesLite)
	{
		procesar(oidGestSustitucionesLite, 'borrar', 'CONFIRMAR');
		window.resizeTo(anchoMax, altoMax);
	}
	
	function borrarOk(oidGestSustitucionesLite)
	{
		procesar(oidGestSustitucionesLite, 'borrar', 'CONFIRMADO_OK');
		window.resizeTo(anchoMax, altoMax);
	}	
	
	function editar(oidDivisionResidenciaFiltro, oidGestSustitucionesLite)
	{
		var f = document.GestSustitucionesLiteForm;
		//f.oidDivisionResidenciaFiltro.value=oidDivisionResidenciaFiltro;
		f.parameter.value='editar';
		f.ACTIONTODO.value='EDITA';
		f.oidGestSustitucionesLite.value= oidGestSustitucionesLite;
		f.submit();
		//procesar(oidGestSustitucionesLite, 'editar', 'EDITA');
		window.resizeTo(anchoMin, altoMin);
	}

	function buscar()	
	{
		procesar('', 'list', '');
		window.resizeTo(anchoMax, altoMax);
	}
	
	function nuevo()
	{
		procesar('', 'nuevo', 'NUEVO');
		window.resizeTo(anchoMin, altoMin);
	}	

	function duplicar(oidGestSustitucionesLite)
	{
		procesar(oidGestSustitucionesLite, 'duplicar', '');
		window.resizeTo(anchoMin, altoMin);
	}		

	function volver()
	{
		var f = document.GestSustitucionesLiteForm;
		f.resiCn.value='';
		f.resiMedicamento.value='';
		procesar('', 'list', '');
		window.resizeTo(anchoMax, altoMax);
	}	

	function grabar()
	{
		var f = document.GestSustitucionesLiteForm;
		//var selectElement = document.getElementsByName("idTipoAccion")[0]; // Accede al elemento <select>
		//var selectedValue = selectElement.value; // Obtiene el valor seleccionado
		//var resiCnValue = f.elements["resiCn"].value;

		//alert('0 ' + f.elements["oidDivisionResidenciaFiltro"].value);
		//alert('1 ' + f.elements["idTipoAccion"].value);
		//alert('2 ' + f.elements["spdCn"].value);
		//alert('3 ' + f.elements["spdNombreBolsa"].value);
		//alert('4 ' + f.elements["resiCn"].value);
		//alert('4_1 ' + f.elements["resiMedicamento"].value);
		//alert('5 ' + f.elements["parameter"].value);
		//alert('6 ' + f.elements["ACTIONTODO"].value);
		
		
		if(f.elements["idTipoAccion"].value=='')
			alert('Falta indicar la acción');
		else if(f.elements["spdCn"].value=='')
			alert('Falta indicar CN OK');
		else if(f.elements["spdNombreBolsa"].value=='')
			alert('Falta indicar el nombre corto ');
		else if(f.elements["resiCn"].value=='' && f.elements["resiMedicamento"].value=='')
			alert('Falta indicar el Código CN residencia o el nombre del medicamento');
		else
		{		
			if(f.elements["parameter"].value=='duplicar')			f.ACTIONTODO.value='DUPLICA_OK';
			if(f.elements["parameter"].value=='editar') 			f.ACTIONTODO.value='EDITA_OK';
			if(f.elements["parameter"].value=='nuevo') 				f.ACTIONTODO.value='NUEVO_OK';
				
			f.submit();
			window.resizeTo(anchoMax, altoMax);
	
		}
	}	
	
	
	function grabarExpres()
	{
	
		var f = document.GestSustitucionesLiteForm;
	//	alert(f.elements["parameter"].value);
	//	alert('1');
		if(f.elements["oidDivisionResidenciaFiltro"].value=='')
			alert('Falta indicar residencia');
		else if(f.elements["idTipoAccion"].value=='')
			alert('Falta indicar la acción');
		else if(f.elements["spdCn"].value=='')
			alert('Falta indicar CN OK');
		else if(f.elements["spdNombreBolsa"].value=='')
			alert('Falta indicar el nombre corto ');
		
		else
		{		
			if(f.elements["parameter"].value=='duplicar')			f.ACTIONTODO.value='DUPLICA_OK';
			if(f.elements["parameter"].value=='editarExpres') 			f.ACTIONTODO.value='EDITA_OK';
			if(f.elements["parameter"].value=='nuevo') 				f.ACTIONTODO.value='NUEVO_OK';
				
			f.submit();
			//window.close();
		}
	}	

	//función de carga del lookUp
	function buscaConsejoPorCodGtVm(codGtVm){				
		var loc = '/spd/LookUpBdConsejo.do?parameter=init&CallBackID=spdCn&CallBackNAME=spdNombreMedicamento&filtroGtVm='+	codGtVm	;

		window.open(loc, 'LookUpBdConsejo', 'dependent=yes,height=500,width=800,top=50,left=50,resizable=yes,scrollbars=yes' );
	}	
	

	//función de carga del lookUp
	function doLookUpBdConsejo(){				
		var loc = '/spd/LookUpBdConsejo.do?parameter=init'+ 						//url de llamanda
			'&resiCn='+document.GestSustitucionesLiteForm.resiCn.value+'&mode=showGtvmpCn'+	
			'&CallBackID=spdCn'+			  			//Nombre del campo para el valor Id
			'&CallBackNAME=spdNombreBolsa';			   		//Nombre del campo para el valor descriptivo
	
		//Importante que se realice en ventana nueva!!!!
		window.open(loc, 'LookUpBdConsejo', 'dependent=yes,height=500,width=800,top=50,left=50,resizable=yes,scrollbars=yes' );
	}	
	
	