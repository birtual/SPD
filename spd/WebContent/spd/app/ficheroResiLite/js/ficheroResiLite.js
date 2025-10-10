//ocultación de datos personales
/*function toggleDatos() {
    let mostrar = document.getElementById("toggleDatos").innerText === "Mostrar Datos";

    if (mostrar) {
        fetch('obtenerDatosReales.do')
            .then(response => response.json())
            .then(data => {
                document.querySelectorAll("#tablaDatos tr").forEach(tr => {
                    let id = tr.getAttribute("data-id");
                    if (data[id]) {
                        tr.querySelector(".cip").innerText = data[id].resiCIP;
                        tr.querySelector(".nombre").innerText = data[id].resiApellidosNombre;
                    }
                });
            });
        document.getElementById("toggleDatos").innerText = "Ocultar Datos";
    } else {
        document.querySelectorAll("#tablaDatos tr").forEach(tr => {
            let cip = tr.querySelector(".cip").getAttribute("data-masked");
            let nombre = tr.querySelector(".nombre").getAttribute("data-masked");

            tr.querySelector(".cip").innerText = cip;
            tr.querySelector(".nombre").innerText = nombre;
        });
        document.getElementById("toggleDatos").innerText = "Mostrar Datos";
    }
}
*/

// variables globales

		
	function updateList()
	{
		var f = document.FicheroResiForm;
	 	f.submit();
	}

	function goIndexCargas()
	{
		var f = document.FicheroResiForm;
		//document.location.href='/spd/FicheroResiCabeceraLite.do?parameter=list&oidDivisionResidencia='+f.oidDivisionResidencia.value;
		document.location.href='/spd/FicheroResiCabeceraLite.do?parameter=list&oidDivisionResidenciaFiltro='+ f.oidDivisionResidenciaFiltro.value;
		return true;
	}	
	
	
	function procesar(oidFicheroResiDetalle, parameter, actiontodo)
	{
		var f = document.FicheroResiForm;
		f.parameter.value=parameter;
		f.ACTIONTODO.value=actiontodo;
		f.oidFicheroResiDetalle.value= oidFicheroResiDetalle;
		f.submit();
	}

	function borrar(oidFicheroResiDetalle)
	{
		procesar(oidFicheroResiDetalle, 'borrar', 'CONFIRMAR');
	}
	function borrarOk(oidFicheroResiDetalle)
	{
		procesar(oidFicheroResiDetalle, 'borrar', 'CONFIRMADO_OK');
	}
	function editar(oidFicheroResiDetalle)
	{
		procesar(oidFicheroResiDetalle, 'editar', 'EDITA');
	}
	function validar(oidFicheroResiDetalle)
	{
		procesar(oidFicheroResiDetalle, 'validar', 'VALIDAR');
	}
	function confirmar(oidFicheroResiDetalle)
	{
		procesar(oidFicheroResiDetalle, 'confirmar', 'CONFIRMAR');
	}
	function grabar()
	{
		var f = document.FicheroResiForm;
		var oidFicheroResiDetalle = f.oidFicheroResiDetalle.value;
		procesar(oidFicheroResiDetalle, 'editar', 'EDITA_OK');
	}	

	function reiniciar(oidFicheroResiDetalle)
	{
		procesar(oidFicheroResiDetalle, 'refrescar', 'REFRESCAR');
	}	
	
	function exportExcel()
	{
		procesar('', 'exportExcel', 'EXCEL');
	}	
	
	function exportFilasSinSust()
	{
		procesar('', 'exportFilasSinSust', 'EXCEL');
	}				

	function exportFilasConInfo()
	{
		procesar('', 'exportFilasConInfo', 'EXCEL');
	}				


	function goSubmit()
	{
		procesar('', 'list', '');
	}	

	function ordenarPor(orden)
	{
		var f = document.FicheroResiForm;
		f.parameter.value='list';
		f.ACTIONTODO.value='list';
		f.campoOrder.value=orden;
		f.submit();   	
	}
		
	function visualizarActivos()
	{
		var f = document.FicheroResiForm;
		f.parameter.value='list';
		f.ACTIONTODO.value='list';
		f.excluirNoPintar.value='SI';
		f.submit();   	
	}		
		
	function modoVisual(mode)
	{
		var f = document.FicheroResiForm;
		f.mode.value=mode;
		f.parameter.value='list';
		f.ACTIONTODO.value='list';
		f.submit();   	
	}		


    function abrirVentanaRow(row, detalleRow) {
    	
    	var anchoVentana;
	    try {
	        // Intenta obtener el ancho de la ventana del cliente
	        anchoVentana = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
	        // Si no se puede obtener el ancho, establece un valor predeterminado
	        if (!anchoVentana || anchoVentana <= 0) {
	            anchoVentana = 800; // Valor predeterminado de ancho
	        }
	    } catch (error) {
	        // Si ocurre un error, establece un valor predeterminado
	        console.error('Error al obtener el ancho de la ventana:', error);
	        anchoVentana = 800; // Valor predeterminado de ancho
	    }
   	
        var nuevaVentana = window.open('', 'Row', 'width=' + anchoVentana + ',height=400,resizable=yes,scrollbars=yes');
        // Reemplazar números de Excel de 5 dígitos con fechas en formato dd/mm/yyyy
        detalleRow = detalleRow.replace(/\b(\d{5})\b/g, convertirNumeroAFecha);
        
        // Escribir el contenido de los errores en la nueva ventana
        nuevaVentana.document.write('<html><head><title>Fila </title></head><body>');
        nuevaVentana.document.write('<script>document.write(\'<link rel="stylesheet" href="/spd/spd/css/gestio.css" media="screen" />\');</script>');
        nuevaVentana.document.write('</BR></BR></BR>');
        nuevaVentana.document.write('<h1>Contenido de la fila </h1>');
        nuevaVentana.document.write('</BR></BR></BR>');
        nuevaVentana.document.write('<div>' + detalleRow + '</div>');
        nuevaVentana.document.write('</body></html');

        // Cerrar la escritura del documento
        nuevaVentana.document.close();
    }
	
	function buscar()
	{
		procesar('', 'list', '');
	}
	
	function volver()
	{
		procesar('', 'list', '');
	}	
	
	function infoAlertas(oidFicheroResiDetalle)
	{
		var loc = '/spd/FicheroResiDetalleLite.do?parameter=infoAlertas&oidFicheroResiDetalle='+ oidFicheroResiDetalle ;		
		//Importante que se realice en ventana nueva!!!!
		window.open(loc, 'infoAlertas', 'dependent=yes,height=700,width=900,top=50,left=0,resizable=yes,scrollbars=yes' );
	}	
	

	
	function actualizaSustitucionLite(parameter, oidDivisionResidencia, idDivisionResidencia, resiCn, resiMedicamento, oidFicheroResiDetalle)
	{
    	var anchoVentana;
	    try {
	        // Intenta obtener el ancho de la ventana del cliente
	        anchoVentana = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
	        // Si no se puede obtener el ancho, establece un valor predeterminado
	        if (!anchoVentana || anchoVentana <= 0) {
	            anchoVentana = 800; // Valor predeterminado de ancho
	        }
	    } catch (error) {
	        // Si ocurre un error, establece un valor predeterminado
	        console.error('Error al obtener el ancho de la ventana:', error);
	        anchoVentana = 800; // Valor predeterminado de ancho
	    }
   	
	    
	    
		var loc = '/spd/GestSustitucionesLite.do?parameter='+parameter+'&resiCn='+ resiCn  + '&resiMedicamento='+ resiMedicamento + '&oidDivisionResidenciaFiltro='+ oidDivisionResidencia+'&oidFicheroResiDetalle='+ oidFicheroResiDetalle ;		
		//Importante que se realice en ventana nueva!!!!
		window.open(loc, 'actualizaSustitucionLite', 'dependent=yes,height=500,width='+anchoVentana+',top=50,left=0,resizable=yes,scrollbars=yes' );
	}	
 
	function desasignarTodos()
	{
		procesar(oidFicheroResiDetalle, 'borrarMasivoPorProceso', 'BORRADO_MASIVO');
	}	

	function submit2()
	{
		var f = document.FicheroResiForm;
		f.submit();
	}	
 		
 	function checkCIP(cip, nombre)
	{
		var f = document.FicheroResiForm;
		if(cip!='' && nombre!='')
 			return;
		else if(cip=='')
		{
			f.parameter.value='actualizaCipEnFichero';
			f.ACTIONTODO.value='EDIT';
			f.submit();
		}
	}	
 		
	function generarFicherosRobot()
	{
		var f = document.FicheroResiForm;
		if(f.procesoValido.value=='false'){
			alert('Existen registros no válidos o por validar'); 
			return;				
		}
		procesar('', 'generarFicherosHelium', 'GENERAR_FICHEROS');

	}
 		
		
	function enviarAPrevision()
	{
		var f = document.FicheroResiForm;
		if(f.procesoValido.value=='false'){
			alert('Existen registros no válidos o por validar'); 
			return;				
		}
		procesar('', 'actualizarPrevision', 'VALIDADO_OK');

	}
 		
	
 		
	function limpiarFiltrosResi()
	{
		var f = document.FicheroResiForm;
		if(f.oidFicheroResiDetalle)				f.oidFicheroResiDetalle.value='';
		if(f.seleccionEstado)					f.seleccionEstado.value='';
		if(f.seleccionResiCIP)					f.seleccionResiCIP.value='';
		if(f.seleccionResiNombrePaciente)		f.seleccionResiNombrePaciente.value='';
		if(f.seleccionResiApellidosNombre)		f.seleccionResiApellidosNombre.value='';
		if(f.seleccionResiCn)					f.seleccionResiCn.value='';
		if(f.seleccionResiMedicamento)			f.seleccionResiMedicamento.value='';
		if(f.seleccionResiFormaMedicacion)		f.seleccionResiFormaMedicacion.value='';
		if(f.seleccionResiObservaciones)		f.seleccionResiObservaciones.value='';
		if(f.seleccionResiComentarios)			f.seleccionResiComentarios.value='';
		if(f.seleccionResiSiPrecisa)			f.seleccionResiSiPrecisa.value='';
		if(f.seleccionResiPeriodo)				f.seleccionResiPeriodo.value='';
		if(f.seleccionResiViaAdministracion)	f.seleccionResiViaAdministracion.value='';
		if(f.seleccionResiVariante)				f.seleccionResiVariante.value='';
		if(f.seleccionSpdCnFinal)				f.seleccionSpdCnFinal.value='';
		if(f.seleccionSpdNombreBolsa)			f.seleccionSpdNombreBolsa.value='';
		if(f.seleccionSpdFormaMedicacion)		f.seleccionSpdFormaMedicacion.value='';
		if(f.seleccionSpdAccionBolsa)			f.seleccionSpdAccionBolsa.value='';
		if(f.seleccionValido)					f.seleccionValido.value='';
	//	if(f.seleccionResultLog)				f.seleccionResultLog.value='';
		if(f.seleccionMensajesInfo)				f.seleccionMensajesInfo.value='';
		if(f.seleccionMensajesAlerta)			f.seleccionMensajesAlerta.value='';
		if(f.seleccionIncidencia)				f.seleccionIncidencia.value='';
		if(f.seleccionValidar)					f.seleccionValidar.value='';
		if(f.secuenciaGuide)					f.secuenciaGuide.value='';
		if(f.excluirNoPintar)					f.excluirNoPintar.value='';
		if(f.campoOrder)						f.campoOrder.value='';
		if(f.filtroNumComprimidos)				f.filtroNumComprimidos.value=false;
		if(f.filtroRegistroAnterior)			f.filtroRegistroAnterior.value=false;
		if(f.filtroRegistroRobot)				f.filtroRegistroRobot.value=false;
		if(f.filtroValidacionDatos)				f.filtroValidacionDatos.value=false;
		if(f.filtroPrincipioActivo)				f.filtroPrincipioActivo.value=false;
		if(f.filtroNoSustituible)				f.filtroNoSustituible.value=false;
		if(f.filtroDiferentesGtvmp)				f.filtroDiferentesGtvmp.value=false;
		if(f.filtroUnicoGtvm)					f.filtroUnicoGtvm.value=false;
		
	
		  
		f.parameter.value='list';
		f.ACTIONTODO.value='list';
		f.submit();
	}		
	

	function gestionTomas()
	{
		var f = document.FicheroResiForm;
//		var loc = '/spd/CabecerasXLS.do?parameter=edicionLista&oidDivisionResidencia=' + f.oidDivisionResidencia.value + '&oidFicheroResiCabecera='+f.oidFicheroResiCabecera.value; 		
		var loc = '/spd/CabecerasXLS.do?parameter=edicionLista&oidDivisionResidencia=' + f.oidDivisionResidencia.value + '&oidFicheroResiCabecera='+f.oidFicheroResiCabecera.value+ '&oidFicheroResiDetalle=' + f.oidFicheroResiDetalle.value ;
		window.open(loc, 'gestionTomas', 'dependent=yes,height=700,width=1000,top=50,left=50,resizable=yes,scrollbars=yes' );
	}	



	function reloadCheckbox(name) {
	    var f = document.FicheroResiForm;
	    f[name].value = f[name].checked;
	    f[name].value = f[name].value;
	    f.submit();
	}
	
	
	function reloadCheckbox1()
	{
		var f = document.FicheroResiForm;
		f.submit();
	}
	
	
	function checkValue()
	{
		var f = document.FicheroResiForm;
		f.filtroCheckedMostrarGeneradosSeq.value=f.filtroMostrarGeneradosSeq.checked;
		f.filtroMostrarGeneradosSeq.value=f.filtroCheckedMostrarGeneradosSeq.value;

		
		f.submit();
	}
	
	function confirmadoMasiva(idDivisionResidencia, idProceso)
	{
		var f = document.FicheroResiForm;
		f.idDivisionResidencia.value=idDivisionResidencia;
		f.idProceso.value=idProceso;
		f.parameter.value='confirmacionMasiva';
		f.ACTIONTODO.value='CONFIRMAR';
		f.submit();
	}

	