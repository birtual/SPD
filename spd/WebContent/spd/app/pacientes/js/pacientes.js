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
	   	

		    
		function buscar()
		{
			var f = document.PacientesForm;
			f.parameter.value='list';
			f.submit();	
		}
		
		function volver()
		{
			buscar();	
		}

		function goNew(parameter, oidPaciente, target, width, height ) {
		    var form = document.createElement("form");
		    form.method = "post";
		    form.action = "/spd/Pacientes.do";
		    form.target = target;

		    var filtroCheckbox = document.querySelector("input[name='filtroVerDatosPersonales']");
		    var filtroValor = (filtroCheckbox && filtroCheckbox.checked) ? "true" : "false";

		    [["parameter", parameter], 
		     ["oidPaciente", oidPaciente], 
		     ["filtroVerDatosPersonales", filtroValor]]
		    .forEach(function(item) {
		        var input = document.createElement("input");
		        input.type = "hidden";
		        input.name = item[0];
		        input.value = item[1];
		        form.appendChild(input);
		    });
		    if(width=='') width='650';if(height=='') height='700';
		    document.body.appendChild(form);
		    window.open("", target, "dependent=yes,width="+width+",height="+height+",top=50,left=0,resizable=yes,scrollbars=yes");
		    form.submit();
		    document.body.removeChild(form);
		}
		
		
		function goDetalle(oidPaciente) {
			goNew("detalle", oidPaciente, "goDetalle", 650, 700 );
		}
		
		
		
		function goBolquers(oidPaciente)
		{
			goNew("detallBolquers", oidPaciente, "goBolquers", 900, 400 );
		}		
		
 		function goDiscrepancias(oidPaciente) {
			goNew("detalleDiscrepancias", oidPaciente, "goDiscrepancias", 950, 400 );
		}
		
		function goTratamientoRct(oidPaciente) {
			goNew("detalleTratamientoRct", oidPaciente, "goTratamientoRct", 950, 400 );
		}

		function goTratamientosSPD(oidPaciente) {
			goNew("detalleTratamientoSPD", oidPaciente, "goTratamientosSPD", 950, 400 );
		}
		
		
		function goEditar(oidPaciente)
		{
			
			var f = document.PacientesForm;
			//f.parameter.action='FicheroResiDetalle.do';
			f.parameter.value='editar';
			f.ACTIONTODO.value='EDITAR';
			f.oidPaciente.value= oidPaciente;
			f.submit();
		}
		
		function goEditarOK()
		{
			var f = document.PacientesForm;
			//f.parameter.action='FicheroResiDetalle.do';
			if(!f.filtroVerDatosPersonales.checked) 
			{
				alert('Es necesario visibilizar todos los datos');
				return;
			}
			f.ACTIONTODO.value='EDITAR_OK';
			f.submit();
		}
		
		
		function goNuevoCIP()
		{
			var f = document.PacientesForm;
			f.parameter.value='nuevo';
			f.ACTIONTODO.value='NUEVO_CIP';
			f.submit();
		}
		
		
		function goNuevo()
		{
			var f = document.PacientesForm;
			if(f.CIP.value=='') 
			{
				alert('Es necesario indicar un CIP');
				return;
			}
			f.parameter.value='nuevo';
			f.ACTIONTODO.value='NUEVO';
			f.submit();
		}
		
		function goNuevoOK()
		{
			var f = document.PacientesForm;
			if(f.oidDivisionResidencia.value=='') 
			{
				alert('Es necesario indicar una residencia');
				return;
			}
			if(f.nombre.value=='') 
			{
				alert('Es necesario indicar un nombre');
				return;
			}
			if(f.apellido1.value=='') 
			{
				alert('Es necesario indicar el primer apellido');
				return;
			}			
			if(f.estatus.value=='') 
			{
				alert('Es necesario indicar un estado');
				return;
			}	
			f.parameter.value='nuevo';
			f.ACTIONTODO.value='NUEVO_OK';
			f.submit();
		}
		

		
		
		function confirmacionFicherosRobotResidente(oidPaciente, oidFicheroResiCabecera)
		{
			//var url = "/spd/PrepararGeneracion.do?parameter=confirmacionFicheros&oidPaciente="+ oidPaciente + "&oidFicheroResiCabecera=" + oidFicheroResiCabecera +  "&operation=GENERAR_FICHEROS";
			var f = document.FicheroResiForm;
			document.location.href='/spd/FicheroResiCabeceraLite.do?parameter=editar&oidPaciente='+ oidPaciente + '&oidFicheroResiCabecera=' + oidFicheroResiCabecera +  '&ACTIONTODO=FICHEROS_RESIDENTE';
				//window.open(url, 'generarFicherosDMyRX', 'dependent=yes,width=850,height=400,top=50,left=0,resizable=yes,scrollbars=yes' );
			return true;
		}	
		
		function reloadCheckbox(name, parameter) {
		    var f = document.PacientesForm;
		    f[name].value = f[name].checked;
		    f[name].value = f[name].value;
		    f.parameter.value = parameter;
		    f.submit();
		}	
    