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


		
		function goDetalle(oidPaciente)
		{
			//f.parameter.action='FicheroResiDetalle.do';
			 var url = '/spd/Pacientes.do?parameter=detalle&oidPaciente=' + oidPaciente;
			 window.open(url, 'actualizaSustitucionLite', 'dependent=yes,height=650,width=700,top=50,left=0,resizable=yes,scrollbars=yes' );
		}
		
		function goBolquers(oidPaciente)
		{
			//f.parameter.action='FicheroResiDetalle.do';
			 var url = '/spd/Pacientes.do?parameter=detallBolquers&oidPaciente=' + oidPaciente;
			 window.open(url, 'goBolquers', 'dependent=yes,width=900,height=400,top=50,left=0,resizable=yes,scrollbars=yes' );
		}		
		
		
		function goDiscrepancias(oidPaciente)
		{
			//f.parameter.action='FicheroResiDetalle.do';
			 var url = '/spd/Pacientes.do?parameter=detalleDiscrepancias&oidPaciente=' + oidPaciente;
			 window.open(url, 'goDiscrepancias', 'dependent=yes,width=950,height=400,top=50,left=0,resizable=yes,scrollbars=yes' );
		}		
		
		function goTratamientoRct(oidPaciente)
		{
			 var url = '/spd/Pacientes.do?parameter=detalleTratamientoRct&oidPaciente=' + oidPaciente;
			 window.open(url, 'goTratamientoRct', 'dependent=yes,width=950,height=400,top=50,left=0,resizable=yes,scrollbars=yes' );
		}			
		
		function goTratamientosSPD(oidPaciente)
		{
			 var url = '/spd/Pacientes.do?parameter=detalleTratamientoSPD&oidPaciente=' + oidPaciente;
			 window.open(url, 'goTratamientosSPD', 'dependent=yes,width=950,height=400,top=50,left=0,resizable=yes,scrollbars=yes' );
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
		
		
		function abrirVentanaFlotante(oidPaciente) {
			
		   
		    // Abre una nueva ventana con la URL especificada
		    
		    
		}

    