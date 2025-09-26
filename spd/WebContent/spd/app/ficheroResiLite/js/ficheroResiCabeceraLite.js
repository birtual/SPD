		function avance()
		{
			var f = document.forms[0];
			f.parameter.value='list';
			f.ACTIONTODO.value='LIST';
			pageUp();
		}
		function atras()
		{
			var f = document.forms[0];
			f.parameter.value='list';
			f.ACTIONTODO.value='LIST';
			pageDown();
		}	
		function irAPagina(num,numPages) 
		{
			var f = document.forms[0];
			f.parameter.value='list';
			f.ACTIONTODO.value='LIST';
			gotoPage(num,numPages) ;
		}

		function go(url)
		{
			document.location.href=url;
		}

		function goInicio()
		{
			document.location.href='/spd/Enlaces.do?parameter=list&ACTIONTODO=INICIO';
		}
		function goIndex()
		{
			var f = document.FicheroResiForm;
			document.location.href='http://'+window.location.host+'spd/';
			return true;
		}	
		function goHistorico()
		{
			var f = document.FicheroResiForm;
			document.location.href='/spd/FicheroResiCabeceraLiteHst.do?parameter=list';
			return true;
		}	
		
		function volver()
		{
			listado();
		}	
		
		function listado()
		{
			
			var f = document.FicheroResiForm;
			f.parameter.value='list';
			f.ACTIONTODO.value='LIST';
			f.submit();
		}	
		
		//inicializamos procesos y estado al cambiar de resi
		function submitResi()
		{
			var f = document.FicheroResiForm;
			f.filtroProceso.value='';
			f.filtroEstado.value=''; 
			f.submit();				
		}
		
		function submitProceso()
		{
			var f = document.FicheroResiForm;
			f.filtroEstado.value='';
			f.submit();	
		}	
		
		function editar(oidFicheroResiCabecera)
		{
			var f = document.FicheroResiForm;
			f.parameter.value='editar';
			f.ACTIONTODO.value='CONFIRMAR';
			f.oidFicheroResiCabecera.value= oidFicheroResiCabecera;
			f.submit();
		}		
		function editarOk(oidFicheroResiCabecera)
		{
			var f = document.FicheroResiForm;
			
	        const nuevaFechaDesde = document.getElementById('nuevaFechaDesde').value;
	        const nuevaFechaHasta = document.getElementById('nuevaFechaHasta').value;
	        
            // Convertir las fechas a formato Date
            const fechaDesdeObj = new Date(nuevaFechaDesde.split('/').reverse().join('-')); // Formato de fecha desde
            const fechaHastaObj = new Date(nuevaFechaHasta.split('/').reverse().join('-')); // Formato de fecha hasta

            // Verificar si la fecha hasta es anterior a la fecha desde
            if (fechaHastaObj < fechaDesdeObj) {
                alert("La fecha 'Hasta' no puede ser anterior a la fecha 'Desde'. Por favor, revisa las fechas.");
                return; // No ejecutar la acción si las fechas no son correctas
            } else {
                // Llamada a la función editarOk si las fechas son válidas
    			f.parameter.value='editar';
    			f.ACTIONTODO.value='CONFIRMADO_OK';
    			f.oidFicheroResiCabecera.value= oidFicheroResiCabecera;
    			f.submit();
         }
		}		
           
    		function editarOkResidente(oidPaciente, oidFicheroResiCabecera)
    		{
    			var f = document.FicheroResiForm;
    			
    	        const nuevaFechaDesde = document.getElementById('nuevaFechaDesde').value;
    	        const nuevaFechaHasta = document.getElementById('nuevaFechaHasta').value;
    	        
                // Convertir las fechas a formato Date
                const fechaDesdeObj = new Date(nuevaFechaDesde.split('/').reverse().join('-')); // Formato de fecha desde
                const fechaHastaObj = new Date(nuevaFechaHasta.split('/').reverse().join('-')); // Formato de fecha hasta

                // Verificar si la fecha hasta es anterior a la fecha desde
                if (fechaHastaObj < fechaDesdeObj) {
                    alert("La fecha 'Hasta' no puede ser anterior a la fecha 'Desde'. Por favor, revisa las fechas.");
                    return; // No ejecutar la acción si las fechas no son correctas
                } else {
                    // Llamada a la función editarOk si las fechas son válidas
        			f.parameter.value='editar';
        			f.ACTIONTODO.value='CONFIRMADO_OK';
        			f.oidPaciente.value= oidPaciente;
        			f.oidFicheroResiCabecera.value= oidFicheroResiCabecera;
        			f.submit();
             }
                     
            

		}		
		
		function prevision(oidFicheroResiCabecera)
		{
			var f = document.FicheroResiForm;
			f.parameter.value='prevision';
			f.oidFicheroResiCabecera.value= oidFicheroResiCabecera;
			f.submit();
		}
		
		function compararProduccion	(idProceso, oidDivisionResidencia)
		{
			var f = document.FicheroResiForm;
			f.parameter.value='comparar';
			f.ACTIONTODO.value='SELECCIONAR';
			f.oidDivisionResidencia.value= oidDivisionResidencia;
			f.idProceso.value= idProceso;
			f.submit();
		}	
		
		function compararProduccionOk(idProceso1, idProceso2)
		{
			var f = document.FicheroResiForm;
			f.parameter.value='comparar';
			f.ACTIONTODO.value='COMPARAR';
			f.idProceso.value= idProceso1;
			f.idProcesoComparacion.value= idProceso2;
			f.submit();
		}		
				
		function borrar(oidFicheroResiCabecera)
		{
			var f = document.FicheroResiForm;
			f.parameter.value='borrar';
			f.ACTIONTODO.value='CONFIRMAR';
			f.oidFicheroResiCabecera.value= oidFicheroResiCabecera;
			f.submit();
		}

		function borrarOk(oidFicheroResiCabecera)
		{
		
			var f = document.FicheroResiForm;
			f.parameter.value='borrar';
			f.ACTIONTODO.value='CONFIRMADO_OK';
			f.oidFicheroResiCabecera.value=oidFicheroResiCabecera;
			f.submit();
		}			
		function refrescar(oidFicheroResiCabecera)
		{
			var f = document.FicheroResiForm;
			f.parameter.value='refrescar';
			f.ACTIONTODO.value='REFRESCAR';
			f.oidFicheroResiCabecera.value= oidFicheroResiCabecera;
			f.submit();
		}		
		
		function goPacientes(oidFicheroResiCabecera)
		{
			
			var f = document.FicheroResiForm;
			f.action='Pacientes.do';
			f.parameter.value='listadoProceso';
			f.oidFicheroResiCabecera.value= oidFicheroResiCabecera;
			f.submit();
		}

		function goDetalle(oidDivisionResidencia, idProceso, oidFicheroResiCabecera)
		{
			
			var f = document.FicheroResiForm;
			f.action='FicheroResiDetalleLite.do';
			f.parameter.value='list';
			f.oidDivisionResidencia.value= oidDivisionResidencia;
			f.oidFicheroResiCabecera.value= oidFicheroResiCabecera;
			
			//console.log(idProceso);
			f.filtroProceso.value= idProceso;
			f.idProceso.value= idProceso;
			
			f.submit();
		}

/*	    function abrirVentanaErrores(oidFicheroResiCabecera, contenidoErrores) {
	        // Abrir una nueva ventana emergente
		        var nuevaVentana = window.open('', 'Errores', 'width=600,height=400,resizable=yes,scrollbars=yes');
		        
		        // Escribir el contenido de los errores en la nueva ventana
		        nuevaVentana.document.write('<html><head><title>Errores</title></head><body>');
		        nuevaVentana.document.write('<h1>Contenido de Errores</h1>');
		        nuevaVentana.document.write('<div>' + contenidoErrores + '</div>');
		        nuevaVentana.document.write('</body></html>');

		        // Cerrar la escritura del documento
		        nuevaVentana.document.close();
		    }
*/
	    
	    
		  // Función para abrir una nueva ventana con el contenido de los errores
	    function abrirVentanaErrores(oidFicheroResiCabecera, idProceso) {
			var url = "/spd/FicheroResiCabeceraLite.do?parameter=abrirVentanaErrores&idProceso=" + idProceso + "&oidFicheroResiCabecera=" + oidFicheroResiCabecera;
			 
			 window.open(url, 'abrirVentanaErrores', 'dependent=yes,width=850,height=400,top=50,left=0,resizable=yes,scrollbars=yes' );
			f.submit();
	    }

		  // Función para abrir una nueva ventana con el contenido de los errores
	    function abrirResumenCIPS(oidFicheroResiCabecera,idProceso) {
			var url = "/spd/FicheroResiCabeceraLite.do?parameter=resumenCIPS&idProceso=" + idProceso  + "&oidFicheroResiCabecera=" + oidFicheroResiCabecera;
			 
			 window.open(url, 'resumenCIPS', 'dependent=yes,width=850,height=400,top=50,left=0,resizable=yes,scrollbars=yes' );
			f.submit();
	    }

	    		
	    		
	    		
	    // Agregar un controlador de eventos al enlace
	    var enlaceMostrarErrores = document.getElementById("mostrarErrores");
	    enlaceMostrarErrores.addEventListener("click", function (event) {
	        event.preventDefault(); // Evita que el enlace siga su comportamiento predeterminado
	        abrirVentanaErrores(); // Llama a la función para abrir la ventana emergente
	    });
		
				
		function ampliarRegistros2(idDivisionResidencia, idProceso)
		{
			var f = document.FicheroResiForm;
			f.action='/Iospd/Iospd.do';
			f.parameter.value='list';
			f.operation.value='FILTER';
			f.idDivisionResidencia.value= idDivisionResidencia;
			f.idProceso.value= idProceso;
			f.submit();
		}

		
		
		function exportExcel(idDivisionResidencia, idProceso)
		{
			var f = document.FicheroResiForm;
		//	alert(document.getElementById(idDivisionResidencia).value);
		//	alert(document.getElementById(idProceso).value);
			f.idDivisionResidencia.value=idDivisionResidencia;
			f.idProceso.value=idProceso;
			f.parameter.value='exportExcel';
			f.ACTIONTODO.value='EXCEL';
			f.submit();
		}
		
		
		function enviarAPrevision()
		{
			var f = document.FicheroResiForm;
			if(f.procesoValido.value=='false'){
				alert('Existen registros no válidos o por revisar'); 
				return;				
			}
			f.parameter.value='actualizarPrevision';
			f.ACTIONTODO.value='VALIDADO_OK';
			f.submit()

		}
		
		/*
		function generarFicherosRobot(oidFicheroResiCabecera)
		{
			var f = document.FicheroResiForm;

			//si llega aquí es que es válido
			f.oidFicheroResiCabecera.value=oidFicheroResiCabecera;
			f.parameter.value='generarFicherosDMyRX';
			f.ACTIONTODO.value='GENERAR_FICHEROS';
			f.submit()
		}
		*/
		
		
		function generarFicherosHelium(oidFicheroResiCabecera)
		{
			var f = document.FicheroResiForm;
			/*if(f.procesoValido.value=='false'){
				alert('Existen registros no válidos o por revisar'); 
				return;				
			}*/
			//si llega aquí es que es válido
			f.oidFicheroResiCabecera.value=oidFicheroResiCabecera;
			f.parameter.value='generarFicherosHelium';
			f.ACTIONTODO.value='GENERAR_FICHEROS';
			f.submit()
		}
		
		function addTratamientos(idDivisionResidencia, idProceso)
		{
			var f = document.FicheroResiForm;
			var url = "/spd/Iospd/Iospd.do?parameter=listAux&idProceso=" + idProceso + "&idDivisionResidencia=" + idDivisionResidencia+ "&operation=FILTER";
	 			 
			 window.open(url, 'addTratamientos', 'dependent=yes,width=850,height=400,top=50,left=0,resizable=yes,scrollbars=yes' );
			f.submit()
		}
		
		
		function confirmacionFicherosRobot(oidFicheroResiCabecera)
		{
			var f = document.FicheroResiForm;
			var url = "/spd/PrepararGeneracion.do?parameter=confirmacionFicheros&oidFicheroResiCabecera=" + oidFicheroResiCabecera +  "&operation=GENERAR_FICHEROS";
	 		window.open(url, 'generarFicherosDMyRX', 'dependent=yes,width=850,height=400,top=50,left=0,resizable=yes,scrollbars=yes' );
		}
		
		function generarFicherosRobot(oidFicheroResiCabecera)
		{
			var f = document.FicheroResiForm;
			var url = "/spd/PrepararGeneracion.do?parameter=generarFicheros&oidFicheroResiCabecera=" + oidFicheroResiCabecera +  "&operation=GENERAR_FICHEROS";
	 		window.open(url, 'generarFicherosDMyRX', 'dependent=yes,width=850,height=400,top=50,left=0,resizable=yes,scrollbars=yes' );
		}
		

		function confirmaFicherosResidente(oidFicheroResiCabecera, oidPaciente)
		{
			var f = document.FicheroResiForm;
			var url = "/spd/PrepararGeneracion.do?parameter=confirmacionFicheros&oidFicheroResiCabecera=" + oidFicheroResiCabecera +  "&oidPaciente="+oidPaciente+"=&operation=GENERAR_FICHEROS";
	 		window.open(url, 'generarFicherosDMyRX', 'dependent=yes,width=850,height=400,top=50,left=0,resizable=yes,scrollbars=yes' );
		}
		
		
		function generaFicherosResidente(oidFicheroResiCabecera, oidPaciente)
		{
			var f = document.FicheroResiForm;
			var url = "/spd/PrepararGeneracion.do?parameter=generarFicherosResidente&oidFicheroResiCabecera=" + oidFicheroResiCabecera +  "&oidPaciente="+oidPaciente+"&operation=GENERAR_FICHEROS";
	 		window.open(url, 'generarFicherosDMyRX', 'dependent=yes,width=850,height=400,top=50,left=0,resizable=yes,scrollbars=yes' );
		}
		
		function informeRobotSpd(oidFicheroResiCabecera, parameter)
		{
			var url = "/spd/InformeSpd.do?parameter="+parameter+"&oidFicheroResiCabecera=" + oidFicheroResiCabecera;
	 		window.open(url, parameter, 'dependent=yes,width=850,height=400,top=50,left=0,resizable=yes,scrollbars=yes' );
		}

		function informeRobotRD(oidFicheroResiCabecera, parameter)
		{
			var url = "/spd/InformeRD.do?parameter="+parameter+"&oidFicheroResiCabecera=" + oidFicheroResiCabecera;
	 		window.open(url, parameter, 'dependent=yes,width=850,height=400,top=50,left=0,resizable=yes,scrollbars=yes' );
		}

		