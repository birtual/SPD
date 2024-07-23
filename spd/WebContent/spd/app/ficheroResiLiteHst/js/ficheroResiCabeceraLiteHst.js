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
		

		function goDetalle(oidDivisionResidencia, idProceso, oidFicheroResiCabecera)
		{
			
			var f = document.FicheroResiForm;
			f.action='FicheroResiDetalleLiteHst.do';
			f.parameter.value='list';
			f.oidDivisionResidencia.value= oidDivisionResidencia;
			f.oidFicheroResiCabecera.value= oidFicheroResiCabecera;
			
			//console.log(idProceso);
			f.filtroProceso.value= idProceso;
			f.idProceso.value= idProceso;
			
			f.submit();
		}

		function verActuales()
		{
			var f = document.FicheroResiForm;
			f.action='FicheroResiCabeceraLite.do';
			f.parameter.value='list';
			f.submit();
		}

		

		  // Función para abrir una nueva ventana con el contenido de los errores
	    function abrirVentanaErrores(oidFicheroResiCabecera, contenidoErrores) {
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

	    // Agregar un controlador de eventos al enlace
	    var enlaceMostrarErrores = document.getElementById("mostrarErrores");
	    enlaceMostrarErrores.addEventListener("click", function (event) {
	        event.preventDefault(); // Evita que el enlace siga su comportamiento predeterminado
	        abrirVentanaErrores(); // Llama a la función para abrir la ventana emergente
	    });
		

		
		
	