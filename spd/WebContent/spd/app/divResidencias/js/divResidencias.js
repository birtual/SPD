  
		function buscar()
		{
			var f = document.DivResidenciasForm;
			f.parameter.value='list';
			f.submit();	
		}
		
		function volver()
		{
			buscar();	
		}


		
		function goDetalle(oidDivisionResidencia)
		{
			 var url = '/spd/DivResidencias.do?parameter=detalle&oidDivisionResidencia=' + oidDivisionResidencia;
			 window.open(url, 'detalleDivisionResidencia', 'dependent=yes,height=650,width=700,top=50,left=0,resizable=yes,scrollbars=yes' );
		}

		function goEditar(oidDivisionResidencia) {
		    var f = document.DivResidenciasForm;

		    f.parameter.value = 'editar';
		    f.ACTIONTODO.value = 'EDITAR';
		    f.oidDivisionResidencia.value = oidDivisionResidencia.toString(); // Convertir a cadena
		    f.submit();
		}
		
		function goEditarOK()
		{
			var f = document.DivResidenciasForm;
			f.ACTIONTODO.value='EDITAR_OK';
			f.submit();
		}


		
		
