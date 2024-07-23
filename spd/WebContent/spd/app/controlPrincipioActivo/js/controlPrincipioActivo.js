	

		function buscar()
		{
			var f = document.PacientesForm;
			f.parameter.value='list';
			f.submit();	
		}
		

		
		function goDetalle(oidPaciente)
		{
			
			var f = document.PacientesForm;
			//f.parameter.action='FicheroResiDetalle.do';
			f.parameter.value='detalle';
			f.oidPaciente.value= oidPaciente;
			f.submit();
		}
		