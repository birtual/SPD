/////////////////////// funciones para validar fechas //////////////////

// variables globales


/**************** INICIO AUTOCOMPLETAR EL GTVM**********************************************/
/*******************************************************************************************/
$(document).ready(function () {
    // Función genérica para manejar el autocompletado
    function configurarAutocomplete(inputId, hiddenId, campo) {
        $("#" + inputId).autocomplete({
            source: function (request, response) {
                $.ajax({
                    url: "/spd/Autocompletar.do",
                    dataType: 'json',
                    data: {
                        query: request.term,  // Término de búsqueda
                        campo: campo          // Campo a buscar
                    },
                    success: function (data) {
                        var mappedData = data.map(function (item) {
                            return {
                                label: item.text,  // Nombre a mostrar
                                value: item.text,  // Nombre en el campo de texto
                                id: item.id        // ID a almacenar en el campo oculto
                            };
                        });
                        response(mappedData);
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        console.error("Error en la llamada Ajax:", textStatus, errorThrown);
                        response([]);
                    }
                });
            },
            minLength: 3,
            select: function (event, ui) {
                $("#" + inputId).val(ui.item.value);  // Mostrar el nombre
                $("#" + hiddenId).val(ui.item.id);   // Almacenar el ID
                return false;  // Evitar comportamiento por defecto
            }
        });
    }

    // Configurar autocompletado para ambos conjuntos de campos
    configurarAutocomplete("filtroNombreLaboratorio", "filtroCodigoLaboratorio", "filtroNombreLaboratorio");
    configurarAutocomplete("filtroNomGtVm", "filtroCodGtVm", "filtroNomGtVm");


    // Maneja el envío del formulario
    $("#formulari").submit(function () {
        // Se puede agregar lógica adicional aquí antes de enviar el formulario

        // Devuelve true para permitir que el formulario se envíe
        return true;
    });
});

/***********************FIN AUTOCOMPLETAR EL GTVM******************************************/
/*******************************************************************************************/
	

	function reloadGtVm()
	{
		var f = document.SustXGtForm;
		f.filtroCodGtVmp.value="";
		f.filtroCodGtVmpp.value="";
		f.submit();
	}	

	function borrar(oidSustXComposicion)
	{
		var f = document.SustXGtForm;
		f.parameter.value='borrar';
		f.ACTIONTODO.value='CONFIRMAR';
		f.oidSustXComposicion.value= oidSustXComposicion;
		f.submit();
	}
	
	
	function nuevoGtVmp(oidSustXComposicion)
	{
		var f = document.SustXGtForm;
		f.parameter.value='nuevo';
		f.ACTIONTODO.value='NUEVO';
		f.oidSustXComposicion.value= oidSustXComposicion;
		f.submit();
	}	
	function editar(oidSustXComposicion)
	{
		var f = document.SustXGtForm;
		f.parameter.value='editar';
		f.ACTIONTODO.value='EDITA';
		f.oidSustXComposicion.value= oidSustXComposicion;
		f.submit();
	}
	
	function goSubmit()
	{
		var f = document.SustXGtForm;
		alert(f.verGestionados.value);
		f.submit();	
	}
	
	function checkValue()
	{
		var f = document.SustXGtForm;
		//f.parameter.value='list';
		//alert(f.filtroCheckedVerGestionados.checked);
		//alert(f.verGestionados.checked);
		//alert('1');
		f.verSoloGestionados.value=f.filtroCheckedVerGestionados.checked;
		f.submit();
	}
	
	function buscar()
	{
		var f = document.SustXGtForm;
		f.parameter.value='list';
		f.submit();	
	}
	// Alternar los hijos de un padre específico
	function toggleChildren(parentIndex) {
	    const cabecera = document.querySelector('#cabecera');    
	    const children = document.querySelectorAll(`[id^="hijo-${parentIndex}"]`);

	    // Alternar la visibilidad de los hijos
	    children.forEach(child => {
	        child.hidden = !child.hidden;  // Alterna el estado de visibilidad
	    });

	    // Verificar si hay hijos visibles
	    const allChildren = document.querySelectorAll('.hijo, .hijo-laboratorio, .hijo-laboratorio-farmacia');
	    const hayHijosVisibles = Array.from(allChildren).some(hijo => !hijo.hidden);
	    cabecera.style.display = hayHijosVisibles ? '' : 'none';
	}

	// Mostrar/Ocultar todos los hijos al marcar el checkbox
	function toggleAllChildren(checkbox) {

	    // Seleccionamos todas las filas que tengan clases específicas
	    const hijos = document.querySelectorAll('.hijo, .hijo-laboratorio, .hijo-laboratorio-farmacia');
	    const cabecera = document.querySelector('#cabecera');

	    // Mostrar/Ocultar todos los hijos
	    hijos.forEach(hijo => {
	        hijo.hidden = !checkbox.checked; // Usamos hidden en lugar de display
	    });

	    // Verificar si hay hijos visibles
	    const hayHijosVisibles = Array.from(hijos).some(hijo => !hijo.hidden);
	    cabecera.style.display = hayHijosVisibles ? '' : 'none';
	}


	
	function handleEnter(event) {
	    if (event.keyCode === 13) { // Código para Enter
	        // Llama a tu función o haz algo personalizado
	        //alert('Enter presionado.'); 
	        //event.preventDefault(); // Evitar el envío automático del formulario si lo necesitas
	        // También puedes enviar el formulario manualmente
	        document.forms[0].submit();
	    }
	}
	