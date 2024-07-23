/////////////////////// funciones para validar fechas //////////////////

// variables globales
	function goInicio()
	{
		document.location.href='/spd/Enlaces.do?parameter=list&ACTIONTODO=INICIO';
	}
	
	function goHistorico()
	{
		var f = document.FicheroResiForm;
		document.location.href='/spd/FicheroResiCabeceraLiteHst.do?parameter=list';
		return true;
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


 // Función para convertir números de Excel en fechas dd/mm/yyyy
	//ajustado la fecha base para que sea el 30 de diciembre de 1899, que es la fecha base utilizada por Excel en sistemas Windows
	function convertirNumeroAFecha(match) {
	    const numeroExcel = parseFloat(match);
	    const fechaBase = new Date('1899-12-30'); // Fecha base de Excel (ajustada)
	    const fecha = new Date(fechaBase.getTime() + numeroExcel * 24 * 60 * 60 * 1000);
	    const dia = fecha.getDate();
	    const mes = fecha.getMonth() + 1;
	    const anio = fecha.getFullYear();
	    return `${dia.toString().padStart(2, '0')}/${mes.toString().padStart(2, '0')}/${anio}`;
	}






    function abrirVentanaRow(row, detalleRow) {
        var nuevaVentana = window.open('', 'Row', 'width=600,height=400,resizable=yes,scrollbars=yes');
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
		if(f.filtroRegistroAnterior)				f.filtroRegistroAnterior.value=false;
		if(f.filtroRegistroRobot)				f.filtroRegistroRobot.value=false;
		if(f.filtroValidacionDatos)				f.filtroValidacionDatos.value=false;
		if(f.filtroPrincipioActivo)				f.filtroPrincipioActivo.value=false;
		if(f.filtroNoSustituible)				f.filtroNoSustituible.value=false;
		if(f.filtroDiferentesGtvmp)				f.filtroDiferentesGtvmp.value=false;
		
	
		  
		f.parameter.value='list';
		f.ACTIONTODO.value='list';
		f.submit();
	}		

	

	