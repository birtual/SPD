/////////////////////// funciones para validar fechas //////////////////

// variables globales


/**************** INICIO AUTOCOMPLETAR EL GTVM**********************************************/
/*******************************************************************************************/
$(document).ready(function () {
	
    $.ui.autocomplete.prototype._resizeMenu = function () {
        var ul = this.menu.element;
        //ul.outerWidth(this.element.outerWidth() + 50); // Ajusta el ancho
        ul.css({ "max-height": "200px", "overflow-y": "auto" }); // Forzar el scroll vertical
    };
    
    
    function configurarAutocomplete(inputId, hiddenId, campo) {
        $("#" + inputId).autocomplete({
            source: function (request, response) {
                // Verifica si el término de búsqueda es un asterisco
                var query = request.term.trim();
                if (query === "*") {
                    // Llamada Ajax para obtener todos los resultados
                    $.ajax({
                        url: "/spd/Autocompletar.do",
                        dataType: 'json',
                        data: {
                            query: "",  // Envía un término vacío o especial
                            campo: campo,
                            paramAdicional: obtenerValorCodGtVm()
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
                } else if (query.length >= 3) {
                    // Realiza la búsqueda si el término tiene 3 o más caracteres
                    $.ajax({
                        url: "/spd/Autocompletar.do",
                        dataType: 'json',
                        data: {
                            query: query,
                            campo: campo,
                            paramAdicional: obtenerValorCodGtVm()
                        },
                        success: function (data) {
                            var mappedData = data.map(function (item) {
                                return {
                                    label: item.text,
                                    value: item.text,
                                    id: item.id
                                };
                            });
                            response(mappedData);
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            console.error("Error en la llamada Ajax:", textStatus, errorThrown);
                            response([]);
                        }
                    });
                } else {
                    // Si no cumple las condiciones, devuelve una lista vacía
                    response([]);
                }
            },
            minLength: 1, // Permitir iniciar búsqueda con un solo carácter (para el asterisco)
            select: function (event, ui) {
                $("#" + inputId).val(ui.item.label); // Mostrar el nombre seleccionado
                $("#" + hiddenId).val(ui.item.id);  // Almacenar el ID seleccionado
                return false; // Evitar comportamiento por defecto
            }
        });
    }

    // Obtener dinámicamente el valor del campo del formulario para 'paramAdicional'
    function obtenerValorCodGtVm() {
    //	alert($("#codGtVm").val());
        return $("#codGtVm").val(); // Cambia "#codGtVm" al ID correcto del campo
    }

    // Configurar autocompletado para ambos conjuntos de campos
    configurarAutocomplete("filtroNombreLaboratorio", "filtroCodiLaboratorio", "filtroNombreLaboratorio");
    configurarAutocomplete("filtroNomGtVm", "filtroCodGtVm", "filtroNomGtVm");

    // Maneja el envío del formulario
    $("#formulari").submit(function () {
        // Lógica adicional si es necesario antes de enviar el formulario
        return true;
    });
});


/***********************FIN AUTOCOMPLETAR EL GTVM******************************************/
/*******************************************************************************************/
	
 	function resetFilters()
 	{
		var f = document.SustXGtForm;
		f.filtroCodGtVmp.value="";
		f.filtroNomGtVm.value="";
		f.filtroRobot.value="";
		f.filtroVerTodoConsejo.value="";
		f.filtroVerFarmacias.value="";
		f.campoGoogle.value="";
		f.submit();
	
 		
 	}
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
	
	function nuevoDesdeNivel1(codGtVm, ACTIONTODO)
	{
		var f = document.SustXGtForm;
		f.parameter.value='nuevoDesdeNivel1';
		f.ACTIONTODO.value=ACTIONTODO;
		f.filtroCodGtVm.value= codGtVm;
		f.submit();
	}		
	
	function nuevoDesdeNivel2(codGtVmp, ACTIONTODO)
	{
		var f = document.SustXGtForm;
		f.parameter.value='nuevoDesdeNivel2';
		f.ACTIONTODO.value=ACTIONTODO;
		f.filtroCodGtVmp.value= codGtVmp;
		f.submit();
	}	

	function nuevoDesdeNivel3(codGtVmpp, ACTIONTODO)
	{
		var f = document.SustXGtForm;
		f.parameter.value='nuevoDesdeNivel3';
		f.ACTIONTODO.value=ACTIONTODO;
		f.filtroCodGtVmpp.value= codGtVmpp;
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
	/*
	function checkValue(isChecked)
	{
		var f = document.SustXGtForm;
		//f.parameter.value='list';
		//alert(f.filtroCheckedVerGestionados.checked);
		//alert(f.verGestionados.checked);
		//alert('1');
		f.verSoloGestionados.value=isChecked;
		f.submit();
	}
	*/
	function buscar()
	{
		var f = document.SustXGtForm;
		f.parameter.value='list';
		f.submit();	
	}


	// Alternar los hijos de un padre específico
	function toggleChildren(parentIndex) {
	    const cabecera = document.querySelector('#cabecera');

	    // Mostrar la cabecera si está oculta
	    if (cabecera.style.display === 'none') {
	        cabecera.style.display = '';
	    }

	    // Seleccionar la fila del padre y alternar la clase de expandido
	    const parentRow = document.querySelector(`#nivel1-${parentIndex}, #nivel2-${parentIndex}`);
	    parentRow.classList.toggle('expanded');

	    // Seleccionar todos los hijos correspondientes al nivel actual
	    const children = document.querySelectorAll(`[id^="nivel2-${parentIndex}"], [id^="nivel3-${parentIndex}"]`);
	    children.forEach((child) => {
	        if (child.classList.contains('hidden')) {
	            child.classList.remove('hidden');
	        } else {
	            child.classList.add('hidden');
      }
	    });
	}

	// Alternar todos los hijos de todos los padres
	function toggleAllChildren(isChecked) {
	    const rows = document.querySelectorAll('tr[id^="nivel2-"], tr[id^="nivel3-"]');
	    rows.forEach((row) => {
	        if (isChecked) {
	            row.classList.remove('hidden');
	        } else {
	            row.classList.add('hidden');
	        }
	    });

	    const cabecera = document.querySelector('#cabecera');
	    cabecera.style.display = isChecked ? '' : 'none';
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
	